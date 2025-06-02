package kz.asemokamichi.maliknet.repository;

import kz.asemokamichi.maliknet.data.entity.AdBiddingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdBiddingSessionRepository extends JpaRepository<AdBiddingSession, Long> {
}

