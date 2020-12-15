package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.List;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.WaitingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaitingService {
    @Autowired
    private WaitingRepo waitingRepo;

    public void addUserToWaiting(User toAdd){
        waitingRepo.save(toAdd);
    }

    public List<String> getWaitingList(){
        List<User> userList = waitingRepo.findAll();
        List<String> names = new ArrayList<>();

        for (User current : userList){
            names.add(current.getName());
        }
        return names;
    }

    public void removeFromWaiting (String userName){
        waitingRepo.deleteByName(userName);
    }
}
