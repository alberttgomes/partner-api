package com.eco.persistence;

import com.eco.model.Benefit;
import com.eco.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanPersistence extends JpaRepository <Plan, Long> {
    Benefit benefitByName(String benefitName);
}