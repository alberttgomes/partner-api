package com.eco.persistence;

import com.eco.model.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnershipPersistence extends JpaRepository<Partnership, Long> {
    void delete(Optional<Partnership> partnership);

    @Query(value = "select _firstName from Partnership where _status = ?1")
    List<Partnership> getPartnershipActives(int status);
}
