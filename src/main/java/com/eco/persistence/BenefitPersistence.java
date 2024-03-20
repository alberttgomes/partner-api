package com.eco.persistence;

import com.eco.model.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Albert Gomes Cabral
 */
@Repository
public interface BenefitPersistence extends JpaRepository<Benefit, Long> {
    Optional<Benefit> findBenefitByBenefitName(String benefitName);
}