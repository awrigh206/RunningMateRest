package com.awright.RunningMateRest.Services;

import java.util.HashMap;
import java.util.Map;
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
        return user.getRun().isWaiting();
    }

    public void setWaiting(String userName){
        User user = userService.fetchUser(new UserDto(userName));
        user.getRun().setWaiting(true);
        runRepo.save(user.getRun());
        userRepo.save(user);
    }

    public void createRun(ChallengeDto runDto){
        Map<String,Tracking> tracking = new HashMap<>();
        Pair pair = new Pair(runDto);
        tracking = setupTracking(tracking, pair.getIssuingUser());
        tracking = setupTracking(tracking, pair.getChallengedUser());
        Run run = new Run(pair,tracking);
        User issuingUser = userService.fetchUser(new UserDto(runDto.getIssuingUser()));
        User challengedUser = userService.fetchUser(new UserDto(runDto.getChallengedUser()));
        issuingUser.setRun(run);
        challengedUser.setRun(run);
        runRepo.save(run);
        userRepo.save(issuingUser);
        userRepo.save(challengedUser);
    }

    public Map<String,Tracking> setupTracking (Map<String,Tracking> tracking, String user){
        Tracking tracker = new Tracking();
        trackingRepo.save(tracker);
        tracking.put(user, tracker);
        return tracking;
    }

    public Tracking updateRunProgress(DistanceUpdateDto distanceUpdateDto){
        log.info("Updating the run with: " + distanceUpdateDto.toString());
        Optional<User> possibleUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getIssuingUser());
        Optional<User> possibleChallengedUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getChallengedUser());
        updateUser(possibleUser, distanceUpdateDto);
        if(possibleChallengedUser.isPresent()){
            User challenegedUser = possibleChallengedUser.get();
            return challenegedUser.getRun().getTracking().get(challenegedUser.getName());
        }
        else{
            return new Tracking(0.0, 0.0, 0.0);
        }
        //updateUser(possibleChallengedUser, distanceUpdateDto);
        
    }

    private void updateUser (Optional<User> toUpdate, DistanceUpdateDto distanceUpdateDto){
        if(toUpdate.isPresent()){
            User user = toUpdate.get();
            Run run = toUpdate.get().getRun();
            Tracking tracking = run.getTracking().get(user.getUsername());
            tracking.setDistance(tracking.getDistance() + distanceUpdateDto.getDistanceTraveled());
            tracking.setAltitude(tracking.getAltitude() + distanceUpdateDto.getHeightTraveled());
            tracking.setTime(tracking.getTime() + distanceUpdateDto.getTimeTaken());
            userRepo.save(user);
        }
    }
    
}
