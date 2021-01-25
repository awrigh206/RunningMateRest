package com.awright.RunningMateRest.Controllers;

import java.util.ArrayList;
import java.util.List;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin
public class UserController {
    private UserService userService;
    private final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto userDto) {
        log.info(userDto.toString());
        userService.addUser(userDto);
    }

    @GetMapping
    @RequestMapping(path="/email")
    public ResponseEntity<User> getEmail(@RequestParam("name") String name){
        UserDto userDto = new UserDto();
        userDto.setUserName(name);
        User user = userService.fetchUser(userDto);
        User toSend = new User();
        toSend.setEmail(user.getEmail());
        toSend.setName(user.getName());
        if(user != null){
            return ResponseEntity.ok(toSend);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @RequestMapping(path = "/exists")
    public boolean userExists(@RequestBody UserDto userDto) {
        log.info(userDto.toString());
        return userService.doesUserExist(userDto);
    }

    @GetMapping
    @RequestMapping(path = "/auth")
    public boolean auth() {
        return true;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping
    public void removeUser(@RequestBody UserDto userDto) {
        userService.removeUser(userDto);
    }

    @PutMapping
    @RequestMapping(path = "/make_ready")
    public void makeUserReady(@RequestBody UserDto userDto) {
        userService.waiting(userDto);
    }

    @PutMapping
    @RequestMapping(path = "/not_ready")
    public void notReady(@RequestBody UserDto userDto) {
        userService.notWaiting(userDto);
    }

    @PutMapping
    @RequestMapping(path = "/challenge")
    public void createChallenge(@RequestBody ChallengeDto challengeDto) {
        userService.createChallenge(challengeDto);
    }

    @PutMapping
    @RequestMapping(path="/challenges/remove")
    public ResponseEntity<Boolean> removeChallenge(@RequestBody ChallengeDto challengeDto){
        return ResponseEntity.ok(userService.removeChallenge(challengeDto));
    }

    @PutMapping
    @RequestMapping(path ="/run")
    public void setReadyToRun(@RequestBody UserDto userDto){
        userService.setRunReady(userDto, true);
    }

    @PutMapping
    @RequestMapping(path ="/run/no")
    public void setNotReadyToRun(@RequestBody UserDto userDto){
        userService.setRunReady(userDto, false);
    }

    @GetMapping
    @RequestMapping(path = "/challenges")
    public List<String> createChallenge(@RequestParam("name") String name) {
        //Returns the names of the users who have challenged the given user
        UserDto userDto = new UserDto(name);
        User user = userService.fetchUser(userDto);
        List<String> names = new ArrayList<>();

        for (User current : user.getChallenges()){
            names.add(current.getName());
        }
        return names;
    }

    @GetMapping
    @RequestMapping(path = "/ready")
    public List<String> findReadyUsers() {
        List<String> namesOnly = new ArrayList<>();
        List<User>  users =  userService.findReady();

        for(User current : users){
            namesOnly.add(current.getName());
        }
        return namesOnly;
    }
}
