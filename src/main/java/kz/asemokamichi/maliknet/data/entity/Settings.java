package kz.asemokamichi.maliknet.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String smtpHost;
    private Integer smtpPort;
    private String smtpUsername;
    private String smtpPassword;
    private String senderEmail;
    private String senderName;
    private Boolean useTls;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
