package com.eco.persistence;

import com.eco.model.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Albert Gomes Cabral
 */
@Repository
public interface NotifyPersistence extends JpaRepository<Notify, Long> {
}
