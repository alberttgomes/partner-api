package com.eco.persistence;

import com.eco.model.PlatformManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformManagerPersistence extends JpaRepository<PlatformManager, Long> {
    Optional<PlatformManager> findByUserNameAdminAndPassword(String userNameAdmin, String password);
}
