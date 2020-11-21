package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.Run;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run,Integer> {
    
}
