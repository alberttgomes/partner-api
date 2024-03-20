package com.eco.persistence;

import com.eco.model.PlatformAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformAdminPersistence extends JpaRepository<PlatformAdmin, Long> {
    Optional<PlatformAdmin> login(
            String email, String userNameAdmin, String password);

    Optional<PlatformAdmin> findByUserNameAdminAndPassword(String userNameAdmin, String password);
}
