package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.List;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.DistanceUpdateDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.Instance;
import com.awright.RunningMateRest.Models.Tracking;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.TrackingRepository;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
    private UserRepository userRepo;
    private UserService  userService;
    private TrackingRepository trackingRepo;
    private final Log log = LogFactory.getLog(RunService.class);
    private InstanceService instanceService;

    @Autowired
    public RunService( UserRepository userRepo, UserService userService, TrackingRepository trackingRepo, InstanceService instanceService){
        this.userRepo = userRepo;
        this.userService = userService;
        this.trackingRepo = trackingRepo;
        this.instanceService = instanceService;
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
        // User issuingUser = userService.fetchUser(new UserDto(challengeDto.getIssuingUser()));
        // User challengedUser = userService.fetchUser(new UserDto(challengeDto.getChallengedUser()));

        // if(instance.getRun() == null)
            return createRun(challengeDto);
        // if(issuingUser.getRun().getChallengedUser().equals(challengedUser.getName()))
        //     return true;
        // else{
        //     return createRun(challengeDto);
        // }
    }

    private boolean createRun(ChallengeDto challengeDto){
        // Pair pair = new Pair(runDto);
        Instance instance = new Instance();
        instance = setupTracking(challengeDto.getInvolvedUsers(), instance);
        List<User> users = fetchUsers(challengeDto.getInvolvedUsers());
        instance.addManyUsers(users);
        userRepo.saveAll(users);
        instanceService.createNewInstance(challengeDto);
        return true;

    }

    public Instance setupTracking (List<String> users, Instance instance){
        for(String current : users){
            Tracking tracker = new Tracking();
            trackingRepo.save(tracker);
            instance.getTrackings().put(current, tracker);
        }
        return instance;
    }

    public void updateRunProgress(DistanceUpdateDto distanceUpdateDto){
        ChallengeDto challengeDto = distanceUpdateDto.getChallengeDto();
        Instance instance = instanceService.getInstance(challengeDto);
        // Optional<User> possibleUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getIssuingUser());
        // Optional<User> possibleChallengedUser = userRepo.findByName(distanceUpdateDto.getChallengeDto().getChallengedUser());

        for(User currentUser : instance.getUsersInvolved()){
            updateUser(currentUser, distanceUpdateDto,instance);
        }
    }

    private void updateUser (User user, DistanceUpdateDto distanceUpdateDto, Instance instance){
        Tracking tracking = instance.getTrackings().get(user.getUsername());
        if(tracking == null){
            tracking = new Tracking();
        }
    
        tracking.setDistance(tracking.getDistance() + distanceUpdateDto.getDistanceTraveled());
        log.info(tracking.getDistance());
        tracking.setAltitude(tracking.getAltitude() + distanceUpdateDto.getHeightTraveled());
        tracking.setTime(tracking.getTime() + distanceUpdateDto.getTimeTaken());
        if(!instance.getTrackings().containsKey(user.getUsername())){
            instance.getTrackings().put(user.getUsername(), tracking);
        }
        trackingRepo.save(tracking);
        userRepo.save(user);
    }

    private List<User> fetchUsers(List<String> names){
        List<User> users = new ArrayList<>();
        for(String current : names){
            User currentUser = userService.fetchUser(new UserDto(current));
            users.add(currentUser);
        }
        return users;
    }
    
}
