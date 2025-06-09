package kz.asemokamichi.maliknet.controller;

import kz.asemokamichi.maliknet.data.entity.Ad;
import kz.asemokamichi.maliknet.data.entity.Bid;
import kz.asemokamichi.maliknet.service.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @GetMapping
    public ResponseEntity<?> getAllAds(@RequestParam(required = false) Map<String, String> filters) {
        List<Ad> ads = adService.getAllAds(filters);
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdById(@PathVariable Long id) {
        Ad ad = adService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    @PostMapping
    public ResponseEntity<?> createAd(@ModelAttribute Ad ad) {
        try {
            adService.saveAd(ad);
            return ResponseEntity.status(HttpStatus.CREATED).body(ad);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки изображения");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAd(@PathVariable Long id, @RequestBody Ad ad) {
        try {
            ad.setId(id);
            adService.saveAd(ad);
            return ResponseEntity.ok(ad);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки изображения");
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeAdStatus(@PathVariable Long id, @RequestParam boolean active) {
        Ad ad = adService.changeAdStatus(id, active);
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.ok("Ad deleted");
    }

    @PostMapping("/{adId}/bids")
    public ResponseEntity<?> placeBid(@PathVariable Long adId, @RequestBody Bid bid) {
        Ad ad = adService.placeBid(adId, bid);
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/{adId}/bids")
    public ResponseEntity<?> getBidHistory(@PathVariable Long adId) {
        Ad ad = adService.getBidHistory(adId);
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/{adId}/current-price")
    public ResponseEntity<?> getCurrentPrice(@PathVariable Long adId) {
        BigDecimal currentPrice = adService.getCurrentPrice(adId);
        return ResponseEntity.ok(currentPrice);
    }

}

