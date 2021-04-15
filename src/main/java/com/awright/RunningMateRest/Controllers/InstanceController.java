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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@RequestMapping(path = "/instance")
@CrossOrigin
public class InstanceController {
    @Autowired
    private InstanceService instanceService;
    private final Log log = LogFactory.getLog(InstanceController.class);

    @GetMapping
    public List<User> getInvolvedUsers(@RequestParam("code") String code){
        Integer intCode = Instance.generateInstanceCode(code);
        List<User> users = instanceService.getUsersFromInstance(intCode);
        log.info(users.toString());
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewInstance (@RequestBody ChallengeDto challengeDto){
        instanceService.createNewInstance(challengeDto);
    }
}
