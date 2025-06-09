package kz.asemokamichi.maliknet.service.impl;

import jakarta.transaction.Transactional;
import kz.asemokamichi.maliknet.data.entity.User;
import kz.asemokamichi.maliknet.repository.UserRepository;
import kz.asemokamichi.maliknet.security.UserDetailsImpl;
import kz.asemokamichi.maliknet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl) return ((UserDetailsImpl) principal).getUser();

        return null;
    }


    public User findUserByLogin(String login){
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User loginUser(User user) {
        User currentUser = findUserByLogin(user.getLogin());

        if (currentUser != null && currentUser.getPassword().equals(user.getPassword())) return currentUser;

        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
