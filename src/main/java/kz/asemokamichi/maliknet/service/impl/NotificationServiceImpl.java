package kz.asemokamichi.maliknet.service.impl;

import kz.asemokamichi.maliknet.data.entity.User;
import kz.asemokamichi.maliknet.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyUser(User user, String message) {
        String notificationLog = String.format(
                "\uD83D\uDFE2\uD83D\uDEA8 [УВЕДОМЛЕНИЕ] Пользователь: %s %s (ID: %d) — %s \uD83D\uDD34",
                user.getFirstName(),
                user.getLastName(),
                user.getId(),
                message
        );

        log.info(notificationLog);
    }
}

