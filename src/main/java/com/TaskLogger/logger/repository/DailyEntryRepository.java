package com.TaskLogger.logger.repository;

import com.TaskLogger.logger.entity.DailyEntry;
import com.TaskLogger.logger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {
    List<DailyEntry> findByUserAndDateBetween(User userid, LocalDate start, LocalDate end);
    boolean existsByUserAndDate(User user, LocalDate date);
}
