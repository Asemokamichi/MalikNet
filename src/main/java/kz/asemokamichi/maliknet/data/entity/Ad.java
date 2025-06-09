package kz.asemokamichi.maliknet.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

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
    @JsonIgnore
    private User user;

    private String title;
    private String description;

    @Column(nullable = false)
    private BigDecimal minPrice;

    private BigDecimal currentPrice;

    private Boolean status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime closedAt;

    private Integer biddingDurationMinutes;

    @Transient
    @JsonIgnore
    private Long userID;

    @Transient
    @JsonIgnore
    private List<MultipartFile> photos;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<AdImage> images;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Bid> bids;
}