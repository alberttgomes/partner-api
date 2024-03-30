package com.partner.persistence;

import com.partner.model.Benefit;
import com.partner.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Albert Gomes Cabral
 */
@Repository
public interface PlanPersistence extends JpaRepository <Plan, Long> {
    Benefit benefitByName(String benefitName);
}