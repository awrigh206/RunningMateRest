package com.awright.RunningMateRest.Repositories;

import java.util.Optional;
import com.awright.RunningMateRest.Models.Run;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run,Integer> {
    Optional<Run> findByIssuingUser (String name);
    Optional<Run> findByChallengedUser (String name);
}
