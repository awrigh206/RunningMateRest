package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.Models.Instance;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.InstanceRepo;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstanceService {
    @Autowired
    private InstanceRepo instanceRepo;
    @Autowired
    private UserRepository userRepo;

    public List<User> getUsersFromInstance(Integer instanceCode){
        Optional<Instance> instance = instanceRepo.findByInstanceCode(instanceCode);
        if(instance.isPresent()){
            return instance.get().getUsersInvolved();
        }
        else{
            return new ArrayList<>();
        }
    }

    public void createNewInstance (ChallengeDto challengeDto){
        Instance instance = new Instance();
        instance.addManyUsers(getInvolvedUsers(challengeDto));
        instanceRepo.save(instance);
    }

    public List<User> getInvolvedUsers(ChallengeDto challengeDto){
        List<User> involvedUsers = new ArrayList<>();
        for(String currentName : challengeDto.getInvolvedUsers()){
            Optional<User> currentUser = userRepo.findByName(currentName);
            if(currentUser.isPresent()){
                involvedUsers.add(currentUser.get());
            }
        }
        return involvedUsers;
    }

    public Instance getInstance (ChallengeDto challengeDto){
        Integer instanceCode = Instance.generateInstanceCode(getInvolvedUsers(challengeDto));
        Optional<Instance> instance = instanceRepo.findByInstanceCode(instanceCode);
        if(instance.isPresent()){
            return instance.get();
        }
        return null;
    }

    public Instance getInstance (Integer instanceCode){
        Optional<Instance> instance = instanceRepo.findByInstanceCode(instanceCode);
        if(instance.isPresent()){
            return instance.get();
        }
        return null;
    }

    public void removeInstance (ChallengeDto challengeDto){
        Integer instanceCode = Instance.generateInstanceCodeFromNames(challengeDto.getInvolvedUsers());
        Optional<Instance> instance = instanceRepo.findByInstanceCode(instanceCode);
        if(instance.isPresent()){
            removeFromUsers(challengeDto, instance.get());
            instanceRepo.delete(instance.get());
        }
    }

    private void removeFromUsers(ChallengeDto challengeDto, Instance instance){
        for(User current : instance.getUsersInvolved()){
            Optional<User> userFromRepo = userRepo.findByName(current.getName());
            if(userFromRepo.isPresent()){
                User gotUser = userFromRepo.get();
                gotUser.removeInstance(instance);
                userRepo.save(gotUser);
            }
        }
    }
}
