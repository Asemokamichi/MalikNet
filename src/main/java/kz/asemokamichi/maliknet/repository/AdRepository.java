package kz.asemokamichi.maliknet.repository;

import jakarta.persistence.LockModeType;
import kz.asemokamichi.maliknet.data.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Ad a WHERE a.id = :id")
    Optional<Ad> findByIdForUpdate(@Param("id") Long id);

    @Query("""
    SELECT a FROM Ad a
    LEFT JOIN FETCH a.bids b
    LEFT JOIN FETCH b.user
    LEFT JOIN FETCH a.user
    WHERE a.id = :id
""")
    Optional<Ad> findByIdWithBidsAndUsers(@Param("id") Long id);

}