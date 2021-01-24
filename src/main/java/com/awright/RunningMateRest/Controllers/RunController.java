package com.awright.RunningMateRest.Controllers;

import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.Models.Tracking;
import com.awright.RunningMateRest.Services.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@RequestMapping(path = "/run")
@CrossOrigin
public class RunController {
    private RunService runService;
    private final Log log = LogFactory.getLog(RunController.class);

    @Autowired
    public RunController (RunService runService){
        this.runService = runService;
    }

    @GetMapping
    public ResponseEntity<Boolean> setWaiting(@RequestParam String name){
        log.info("Setting: " + name + " to waiting");
        boolean found = runService.setWaiting(name);
        if(found){
            return ResponseEntity.ok(found);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping 
    @RequestMapping(path = "/challenger")
    public boolean challenegedWaiting(@RequestParam String name){
        log.info("checking if: " + name + " is waiting, he is: " + runService.isChallengedWaiting(name));
        return runService.isChallengedWaiting(name);
    }

    @PostMapping
    public ResponseEntity<Boolean> beginRun(@RequestBody ChallengeDto challengeDto){
        boolean result = runService.beginRun(challengeDto);
        if(result){
            return ResponseEntity.ok(result);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    @RequestMapping(path = "/update")
    public Tracking updateRun(@RequestBody DistanceUpdateDto distanceDto){
        return runService.updateRunProgress(distanceDto);
    }
}
