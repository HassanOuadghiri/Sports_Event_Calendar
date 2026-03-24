package com.sports.events.sport;

import com.sports.events.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SportRepository extends JpaRepository<SportType, Integer> {

}
