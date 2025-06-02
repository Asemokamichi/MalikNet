package kz.asemokamichi.maliknet.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ad_bidding_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdBiddingSession {

    @Id
    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Column(nullable = false)
    private LocalDateTime startedAt;
}

