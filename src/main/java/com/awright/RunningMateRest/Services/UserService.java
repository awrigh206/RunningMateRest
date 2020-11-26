package com.awright.RunningMateRest.Services;

import java.util.List;
import java.util.Optional;

import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepo;

    @Autowired
    public UserService (UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public void addUser(UserDto userDto){
        User user = new User(userDto);
        userRepo.save(user);
    }

    public List<User> getAllUsers (){
        return userRepo.findAll();
    }

    public void removeUser(UserDto userDto){
        User user = new User(userDto);
        userRepo.delete(user);
    }

    public boolean authenticateUser(UserDto userDto){
        User user = new User (userDto);
        Optional<User> possibleUser = userRepo.findByUserName(user.getUserName());
        if(possibleUser.isPresent()){
            if(user.getPassword().equals(possibleUser.get().getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean doesUserExist(UserDto userDto){
        User user = new User(userDto);
        Optional<User> possibleUser = userRepo.findByUserName(user.getUserName());
        return possibleUser.isPresent();
    }
    
}
