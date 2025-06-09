package kz.asemokamichi.maliknet.service.impl;

import kz.asemokamichi.maliknet.data.entity.Ad;
import kz.asemokamichi.maliknet.data.entity.AdImage;
import kz.asemokamichi.maliknet.repository.AdImageRepository;
import kz.asemokamichi.maliknet.service.AdImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdImageServiceImpl implements AdImageService {
    private final AdImageRepository adImageRepository;

    @Override
    public void saveAdImage(List<MultipartFile> photos, Ad ad) throws IOException {
        for (MultipartFile photo : photos) {
            AdImage adImage = new AdImage();
            adImage.setImageUrl(photo.getBytes());
            adImage.setAd(ad);

            adImageRepository.save(adImage);
        }
    }
}

