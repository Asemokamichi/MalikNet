package kz.asemokamichi.maliknet.repository;

import kz.asemokamichi.maliknet.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
    User findUserByEmail(String email);
}
