package com.partner.persistence;

import com.partner.model.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Albert Gomes Cabral
 */
@Repository
public interface PartnershipPersistence extends JpaRepository<Partnership, Long> {
    void delete(Optional<Partnership> partnership);
}
