package com.awright.RunningMateRest.Controllers;

import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.Models.Run;
import com.awright.RunningMateRest.Models.Tracking;
import com.awright.RunningMateRest.Services.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/run")
@CrossOrigin
public class RunController {
    private RunService runService;

    @Autowired
    public RunController (RunService runService){
        this.runService = runService;
    }

    @PostMapping
    public void newRun(@RequestBody ChallengeDto challengeDto){
        runService.createRun(challengeDto);
    }

    @PutMapping
    @RequestMapping(path = "/update")
    public Tracking updateRun(@RequestBody DistanceUpdateDto distanceDto){
        return runService.updateRunProgress(distanceDto);
    }
}
