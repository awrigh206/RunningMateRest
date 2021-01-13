package com.awright.RunningMateRest.Services;

import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public UserService (UserRepository userRepo){
        this.userRepo = userRepo;
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

    public void makeReady (UserDto userDto){
        User selected = fetchUser(userDto);
        selected.setReadyToRun(true);
    }

    public void notReady(UserDto userDto){
        User selected =  fetchUser(userDto);
        selected.setReadyToRun(false);
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
    
}
