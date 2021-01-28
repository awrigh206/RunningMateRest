package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.Instance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceRepo extends JpaRepository<Integer,Instance>{
    
}
