package com.awright.RunningMateRest.Repositories;

import com.awright.RunningMateRest.Models.ImageMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMessageRepository extends JpaRepository<ImageMessage,Integer>{
    
}
