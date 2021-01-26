package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class UserService implements UserDetailsService{
    private UserRepository userRepo;
    private final Log log = LogFactory.getLog(UserService.class);
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public UserService (UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public boolean removeChallenge(ChallengeDto challengeDto){
        boolean removed = false;
        UserDto userDto = new UserDto(challengeDto.getIssuingUser());
        User user = fetchUser(userDto);
        List<User> toRemove = new ArrayList<>();

        for(User current : user.getChallenges()){
            if(current.getName().equals(challengeDto.getChallengedUser())){
                toRemove.add(current);
                removed = true;
            }
        }
        user.getChallenges().removeAll(toRemove);
        userRepo.save(user);
        return removed;
    }

    public void addUser(UserDto userDto){
        User user = new User(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> getAllUsers (){
        return userRepo.findAll();
    }

    public void removeUser(UserDto userDto){
        User user = new User(userDto);
        userRepo.delete(user);
    }

    public void waiting (UserDto userDto){
        User selected = fetchUser(userDto);
        selected.setWaiting(true);
        userRepo.save(selected);
    }

    public void notWaiting(UserDto userDto){
        User selected =  fetchUser(userDto);
        selected.setWaiting(false);
        userRepo.save(selected);
    }

    public void setRunReady (UserDto userDto, boolean value){
        User selected =  fetchUser(userDto);
        selected.setReadyToRun(value);
        userRepo.save(selected);
    }

    public List<User> findReady(){
        Optional<List<User>> readyUsers = userRepo.findByReadyToRun(true);
        if(readyUsers.isPresent()){
            return readyUsers.get();
        }
        else{
            return new ArrayList<>();
        }
    }

    public void createChallenge(ChallengeDto challengeDto){
        log.info("Creating challenge for: " +challengeDto.toString());
        Optional<User> issuingUser = userRepo.findByName(challengeDto.getIssuingUser());
        Optional<User> challengedUser = userRepo.findByName(challengeDto.getChallengedUser());
        if(issuingUser.isPresent() && challengedUser.isPresent()){
            User issuing = issuingUser.get();
            User challenged = challengedUser.get();
            issuing.addChallenege(challenged);
            userRepo.save(issuing);
            challenged.addChallenege(issuing);
            userRepo.save(challenged);
            log.info("Did the thing");
        }
    }

    public User fetchUser(UserDto userDto){
        Optional<User> user = userRepo.findByName(userDto.getUserName());
        if(user.isPresent()){
            return user.get();
        }
        else{
            return null;
        }
    }

    public boolean doesUserExist(UserDto userDto){
        User user = new User(userDto);
        Optional<User> possibleUser = userRepo.findByName(user.getName());
        return possibleUser.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> possibleuser = userRepo.findByName(username);
        if(possibleuser.isPresent()){
            return possibleuser.get();
        }
        else{
            throw new UsernameNotFoundException("Could not find that user name");
        }
    }

    public User getEmail(UserDto userDto){
        User user = fetchUser(userDto);
        User toSend = new User();
        toSend.setEmail(user.getEmail());
        toSend.setName(user.getName());
        return toSend;
    }
    
}
