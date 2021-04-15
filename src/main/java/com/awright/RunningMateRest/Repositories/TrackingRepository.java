package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<Tracking, Integer> {
    
}
