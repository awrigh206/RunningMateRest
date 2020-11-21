package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
}
