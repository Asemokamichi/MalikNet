package kz.asemokamichi.maliknet.service.impl;

import jakarta.transaction.Transactional;
import kz.asemokamichi.maliknet.advice.exceptions.InvalidRequest;
import kz.asemokamichi.maliknet.advice.exceptions.ResourceNotFound;
import kz.asemokamichi.maliknet.data.entity.Ad;
import kz.asemokamichi.maliknet.data.entity.Bid;
import kz.asemokamichi.maliknet.data.entity.User;
import kz.asemokamichi.maliknet.repository.AdRepository;
import kz.asemokamichi.maliknet.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;
    private final AdImageService adImageService;
    private final NotificationService notificationService;

    private final BidService bidService;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public List<Ad> getAllAds(Map<String, String> filters) {
        return adRepository.findAll();
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено"));
    }

    @Override
    public Ad saveAd(Ad ad) throws IOException {
        User user = userService.getCurrentUser();
        ad.setUser(user);
        ad.setCreatedAt(LocalDateTime.now());
        ad.setStatus(true);

        adRepository.save(ad);

        if (ad.getPhotos() != null && !ad.getPhotos().isEmpty()) {
            adImageService.saveAdImage(ad.getPhotos(), ad);
        }

        return ad;
    }

    @Override
    public Ad changeAdStatus(Long id, boolean active) {
        Ad ad = getAdById(id);
        ad.setStatus(active);
        ad.setClosedAt(active ? null : LocalDateTime.now());

        return adRepository.save(ad);
    }

    @Override
    public void deleteAd(Long id) {
        Ad ad = getAdById(id);
        adRepository.delete(ad);
    }

    @Override
    @Transactional
    public Ad placeBid(Long adId, Bid newBid) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new ResourceNotFound("Объявление не найдено"));

        if (!Boolean.TRUE.equals(ad.getStatus())) {
            throw new InvalidRequest("Аукцион закрыт для ставок");
        }

        BigDecimal currentPrice = ad.getCurrentPrice() != null ? ad.getCurrentPrice() : ad.getMinPrice();
        if (newBid.getAmount().compareTo(currentPrice) <= 0) {
            throw new InvalidRequest("Ставка должна быть выше текущей цены");
        }

        User currentUser = userService.getCurrentUser();

        Bid previousLastBid = ad.getBids().stream()
                .max(Comparator.comparing(Bid::getCreatedAt))
                .orElse(null);

        if (previousLastBid != null && previousLastBid.getUser().getId().equals(currentUser.getId())) {
            throw new InvalidRequest("Вы уже являетесь последним участником с максимальной ставкой");
        }

        if (previousLastBid != null) {
            notificationService.notifyUser(
                    previousLastBid.getUser(),
                    "❗Ваша ставка по объявлению \"" + ad.getTitle() + "\" была перебита пользователем " + currentUser.getFirstName()
            );
        }

        // Сохраняем новую ставку
        newBid.setAd(ad);
        newBid.setUser(currentUser);
        newBid.setCreatedAt(LocalDateTime.now());

        newBid = bidService.createBid(newBid);

        ad.setCurrentPrice(newBid.getAmount());
        ad.getBids().add(newBid);

        adRepository.save(ad);

        // Запускаем таймер на завершение
        scheduleClosing(ad, newBid);

        return ad;
    }

    private void scheduleClosing(Ad ad, Bid bid) {
        int minutes = ad.getBiddingDurationMinutes();

        scheduler.schedule(() -> {
            try {
                Ad freshAd = adRepository.findByIdWithBidsAndUsers(ad.getId())
                        .orElseThrow(() -> new ResourceNotFound("Объявление не найдено"));

                if (!Boolean.TRUE.equals(freshAd.getStatus())) {
                    throw new InvalidRequest("Объявление ID " + ad.getId() + " уже закрыто вручную");
                }

                Bid latestBid = freshAd.getBids().stream()
                        .max(Comparator.comparing(Bid::getCreatedAt))
                        .orElse(null);

                if (latestBid == null || !latestBid.getId().equals(bid.getId())) {
                    throw new InvalidRequest(
                            String.format("Ставка ID %d перебита. Последняя ставка — ID %s",
                                    bid.getId(), latestBid != null ? latestBid.getId().toString() : "null")
                    );
                }

                freshAd.setStatus(false);
                freshAd.setClosedAt(LocalDateTime.now());

                User buyer = latestBid.getUser();
                User seller = freshAd.getUser();

                notificationService.notifyUser(buyer, "✅ Вы выиграли аукцион по объявлению: " + freshAd.getTitle());
                if (!Objects.equals(buyer.getId(), seller.getId())) notificationService.notifyUser(seller, "📦 Объявление \"" + freshAd.getTitle() + "\" завершилось. Победитель: " + buyer.getFirstName());

                adRepository.save(freshAd);
                log.info("Аукцион ID {} завершён", ad.getId());
            } catch (Exception e) {
                log.error("Ошибка при завершении аукциона ID " + ad.getId(), e);
            }
        }, minutes, TimeUnit.MINUTES);
    }

    @Override
    public Ad getBidHistory(Long adId) {
        Ad ad = getAdById(adId);
        ad.getBids().sort((b1, b2) -> b2.getCreatedAt().compareTo(b1.getCreatedAt()));

        return ad;
    }

    @Override
    public BigDecimal getCurrentPrice(Long adId) {
        Ad ad = getAdById(adId);
        return ad.getCurrentPrice();
    }

}