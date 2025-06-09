package kz.asemokamichi.maliknet.service;

import kz.asemokamichi.maliknet.data.entity.Ad;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface AdImageService {
    void saveAdImage(List<MultipartFile> photos, Ad ad) throws IOException;
}

