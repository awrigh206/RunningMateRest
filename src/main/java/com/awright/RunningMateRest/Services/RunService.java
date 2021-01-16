package com.awright.RunningMateRest.Services;

import java.util.Optional;

import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.Pair;
import com.awright.RunningMateRest.Models.Run;
import com.awright.RunningMateRest.Models.RunningUser;
import com.awright.RunningMateRest.Models.Tracking;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.RunRepository;
import com.awright.RunningMateRest.Repositories.RunningUserRepository;
import com.awright.RunningMateRest.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
    private RunRepository runRepo;
    private UserRepository userRepo;
    private UserService  userService;

    @Autowired
    public RunService(RunRepository runRepo, UserRepository userRepo, UserService userService){
        this.runRepo = runRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    public void createRun(ChallengeDto runDto){
        Pair pair = new Pair(runDto);
        Run run = new Run(pair);
        User issuingUser = userService.fetchUser(new UserDto(runDto.getIssuingUser()));
        User challengedUser = userService.fetchUser(new UserDto(runDto.getChallengedUser()));
        issuingUser.setRuns(run);
        challengedUser.setRuns(run);
        runRepo.save(run);
        userRepo.save(issuingUser);
        userRepo.save(challengedUser);
    }

    public void updateRunProgress(DistanceUpdateDto distanceUpdateDto){
        Optional<User> possibleUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getIssuingUser());
        Optional<User> possibleChallengedUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getChallengedUser());
        updateUser(possibleUser, distanceUpdateDto);
        updateUser(possibleChallengedUser, distanceUpdateDto);
        
    }

    private void updateUser (Optional<User> toUpdate, DistanceUpdateDto distanceUpdateDto){
        if(toUpdate.isPresent()){
            User user = toUpdate.get();
            Run run = user.getRuns();
            Tracking tracking = run.getTrackingInfo().get(user.getUsername());
            tracking.setDistance(tracking.getDistance() + distanceUpdateDto.getDistanceTraveled());
            tracking.setAltitude(tracking.getAltitude() + distanceUpdateDto.getHeightTraveled());
            tracking.setTime(tracking.getTime() + distanceUpdateDto.getTimeTaken());
            userRepo.save(user);
        }
    }
    
}
