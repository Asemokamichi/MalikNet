package kz.asemokamichi.maliknet.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String description;

    @Column(nullable = false)
    private BigDecimal minPrice;

    private BigDecimal currentPrice;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    private Integer biddingDurationMinutes;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<AdImage> images;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToOne(mappedBy = "ad", cascade = CascadeType.ALL)
    private AdBiddingSession biddingSession;
}