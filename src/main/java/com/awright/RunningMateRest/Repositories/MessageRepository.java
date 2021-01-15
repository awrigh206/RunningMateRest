package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    
}
