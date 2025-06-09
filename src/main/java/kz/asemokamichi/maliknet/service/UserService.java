package kz.asemokamichi.maliknet.service;

import kz.asemokamichi.maliknet.data.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User save(User user);
    User findUserByEmail(String email);
    User findById(Long id);
    User loginUser(User user);
    User getCurrentUser();
    User findUserByLogin(String login);
}
