package kz.asemokamichi.maliknet.repository;

import kz.asemokamichi.maliknet.data.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
}
