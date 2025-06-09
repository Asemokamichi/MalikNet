package kz.asemokamichi.maliknet.service;

import kz.asemokamichi.maliknet.data.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    void notifyUser(User user, String message);
}

