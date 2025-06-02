package kz.asemokamichi.maliknet.repository;

import kz.asemokamichi.maliknet.data.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}