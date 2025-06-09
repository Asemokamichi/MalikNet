package kz.asemokamichi.maliknet.service;

import kz.asemokamichi.maliknet.data.entity.Ad;
import kz.asemokamichi.maliknet.data.entity.Bid;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface AdService {
    List<Ad> getAllAds(Map<String, String> filters);

    Ad getAdById(Long id);

    Ad saveAd(Ad adDto) throws IOException;

    Ad changeAdStatus(Long id, boolean active);

    void deleteAd(Long id);

    Ad placeBid(Long adId, Bid bid);

    Ad getBidHistory(Long adId);

    BigDecimal getCurrentPrice(Long adId);
}