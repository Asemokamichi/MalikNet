package kz.asemokamichi.maliknet.repository;

import kz.asemokamichi.maliknet.data.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
