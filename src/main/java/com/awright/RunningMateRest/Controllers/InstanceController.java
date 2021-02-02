package com.awright.RunningMateRest.Controllers;

import java.util.List;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.Models.Instance;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Services.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/instance")
@CrossOrigin
public class InstanceController {
    @Autowired
    private InstanceService instanceService;

    @GetMapping
    public List<User> getInvolvedUsers(@RequestParam("code") String code){
        Integer intCode = Instance.generateInstanceCode(code);
        return instanceService.getUsersFromInstance(intCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewInstance (@RequestBody ChallengeDto challengeDto){
        instanceService.createNewInstance(challengeDto);
    }
}
