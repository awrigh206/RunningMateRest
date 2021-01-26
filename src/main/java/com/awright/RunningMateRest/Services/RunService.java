package com.awright.RunningMateRest.Services;

import java.util.Optional;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.Pair;
import com.awright.RunningMateRest.Models.Run;
import com.awright.RunningMateRest.Models.Tracking;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.RunRepository;
import com.awright.RunningMateRest.Repositories.TrackingRepository;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
    private RunRepository runRepo;
    private UserRepository userRepo;
    private UserService  userService;
    private TrackingRepository trackingRepo;
    private final Log log = LogFactory.getLog(RunService.class);

    @Autowired
    public RunService(RunRepository runRepo, UserRepository userRepo, UserService userService, TrackingRepository trackingRepo){
        this.runRepo = runRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.trackingRepo = trackingRepo;
    }

    public boolean isChallengedWaiting(String name){
        User user =  userService.fetchUser(new UserDto(name));
        return user.isReadyToRun();
    }

    public boolean setReadyToRun(String userName){
        User user = userService.fetchUser(new UserDto(userName));
        if(user != null){
            user.setReadyToRun(true);
            userRepo.save(user);
            return true;
        }
        else{
            return false;
        }
        
    }

    public boolean beginRun(ChallengeDto challengeDto){
        User issuingUser = userService.fetchUser(new UserDto(challengeDto.getIssuingUser()));
        User challengedUser = userService.fetchUser(new UserDto(challengeDto.getChallengedUser()));

        if(issuingUser.getRun() == null || challengedUser.getRun() == null)
            return createRun(challengeDto);
        if(issuingUser.getRun().getChallengedUser().equals(challengedUser.getName()))
            return true;
        else{
            return createRun(challengeDto);
        }
    }

    private boolean createRun(ChallengeDto runDto){
        Pair pair = new Pair(runDto);
        Tracking tracking = setupTracking(pair.getIssuingUser());
        Run run = new Run(pair,tracking);
        User issuingUser = userService.fetchUser(new UserDto(runDto.getIssuingUser()));
        User challengedUser = userService.fetchUser(new UserDto(runDto.getChallengedUser()));

        if(issuingUser == null || challengedUser  == null){
            return false;
        }
        else{
            issuingUser.setRun(run);
            challengedUser.setRun(run);
            runRepo.save(run);
            userRepo.save(issuingUser);
            userRepo.save(challengedUser);
            return true;
        }

    }

    public Tracking setupTracking (String user){
        Tracking tracker = new Tracking();
        trackingRepo.save(tracker);
        return tracker;
    }

    public Tracking updateRunProgress(DistanceUpdateDto distanceUpdateDto){
        Optional<User> possibleUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getIssuingUser());
        Optional<User> possibleChallengedUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getChallengedUser());
        updateUser(possibleUser, distanceUpdateDto);
        if(possibleChallengedUser.isPresent()){
            User challenegedUser = possibleChallengedUser.get();
            return challenegedUser.getRun().getTracking();
        }
        else{
            return new Tracking(0.0, 0.0, 0.0);
        }
    }

    private void updateUser (Optional<User> toUpdate, DistanceUpdateDto distanceUpdateDto){
        if(toUpdate.isPresent()){
            User user = toUpdate.get();
            Run run = toUpdate.get().getRun();
            Tracking tracking = run.getTracking();
            tracking.setDistance(tracking.getDistance() + distanceUpdateDto.getDistanceTraveled());
            tracking.setAltitude(tracking.getAltitude() + distanceUpdateDto.getHeightTraveled());
            tracking.setTime(tracking.getTime() + distanceUpdateDto.getTimeTaken());
            userRepo.save(user);
        }
    }
    
}
