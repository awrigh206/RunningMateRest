package com.awright.RunningMateRest.Repositories;

import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
    Optional <User> findByName(String name);
    Optional<List<User>> findByReady(boolean state);
}
