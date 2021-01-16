package com.awright.RunningMateRest.Repositories;

import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<List<Message>> findBySender(String sender);
}
