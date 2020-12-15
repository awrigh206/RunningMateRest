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

    public boolean authenticateUser(UserDto userDto){
        User user = new User (userDto);
        Optional<User> possibleUser = userRepo.findByName(user.getName());
        if(possibleUser.isPresent()){
            if(user.getPassword().equals(possibleUser.get().getPassword())){
                return true;
            }
        }
        return false;
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
