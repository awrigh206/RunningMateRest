package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.RunningUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningUserRepository extends  JpaRepository<RunningUser,Integer>{
    RunningUser  findByName(String name);
}
