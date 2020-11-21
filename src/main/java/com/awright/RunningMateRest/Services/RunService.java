package com.awright.RunningMateRest.Services;

import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.Models.Run;
import com.awright.RunningMateRest.Repositories.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
    private RunRepository runRepo;

    @Autowired
    public RunService(RunRepository runRepo){
        this.runRepo = runRepo;
    }

    public void createRun(Run run){
        runRepo.save(run);
    }

    public void updateRunProgress(DistanceUpdateDto distanceUpdateDto){

    }
    
}
