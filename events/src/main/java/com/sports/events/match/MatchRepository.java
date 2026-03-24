package com.sports.events.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match , Integer> {
    List<Match> findByDateVenueBetween(LocalDateTime start, LocalDateTime end);
    List<Match> findByDateVenueBetweenAndSportTypeId(LocalDateTime start, LocalDateTime end, Long sportTypeId);
    List<Match> findBySportTypeId(Long sportTypeId);
}
