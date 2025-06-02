package kz.asemokamichi.maliknet.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ad_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Column(nullable = false)
    private String imageUrl;
}

