package com.awright.RunningMateRest.Controllers;

import java.util.ArrayList;
import java.util.List;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        log.info("making ready");
        userService.makeReady(userDto);
    }

    @PutMapping
    @RequestMapping(path = "/not_ready")
    public void notReady(@RequestBody UserDto userDto) {
        userService.notReady(userDto);
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
