package com.awright.RunningMateRest.Services;

import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.DTO.RunDto;
import com.awright.RunningMateRest.Models.Run;
import com.awright.RunningMateRest.Models.RunningUser;
import com.awright.RunningMateRest.Repositories.RunRepository;
import com.awright.RunningMateRest.Repositories.RunningUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
    private RunRepository runRepo;

    @Autowired
    public RunService(RunRepository runRepo){
        this.runRepo = runRepo;
    }

    public void createRun(RunDto runDto){
        Run run = new Run();
        run.setRunnerOne(new RunningUser(run, runDto.getPlayerOne()));
        run.setRunnerTwo(new RunningUser(run, runDto.getPlayerTwo()));
        runRepo.save(run);
    }

    public void updateRunProgress(DistanceUpdateDto distanceUpdateDto){
        Run run = runRepo.findByInstanceId(distanceUpdateDto.getRunnerNameOne()+distanceUpdateDto.getRunnerNameTwo());
    }
    
}
