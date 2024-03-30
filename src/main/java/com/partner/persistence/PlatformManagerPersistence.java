package com.partner.persistence;

import com.partner.model.PlatformManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Albert Gomes Cabral
 */
@Repository
public interface PlatformManagerPersistence extends JpaRepository<PlatformManager, Long> {
    Optional<PlatformManager> findByUserNameAdminAndPassword(String userNameAdmin, String password);
}
