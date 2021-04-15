package com.awright.RunningMateRest.Repositories;

import java.util.Optional;
import com.awright.RunningMateRest.Models.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceRepo extends JpaRepository<Instance,Integer>{
    Optional<Instance> findByInstanceCode(Integer code);
    void deleteByInstanceCode(Integer code);
}
