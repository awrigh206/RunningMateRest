package com.awright.RunningMateRest.Controllers;

import java.util.List;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.WaitingRepo;
import com.awright.RunningMateRest.Services.WaitingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/waiting")
@CrossOrigin
public class WaitingController {
    @Autowired
    private WaitingService waitingService;
    private final Log log = LogFactory.getLog(WaitingController.class);

    @PostMapping
    public void addToWaiting(@RequestBody UserDto userDto){
        User user = new User(userDto);
        waitingService.addUserToWaiting(user);
    }

    @GetMapping
    public ArrayNode getWaitingList(){
        log.info("waiting: " + waitingService.getWaitingList());
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode node = mapper.createArrayNode();
        
        for(String current : waitingService.getWaitingList()){
            node.add(current);
        }
        return node;
    }

    @DeleteMapping
    public void removeFromWaiting(@RequestAttribute String name){
        waitingService.removeFromWaiting(name);
    }
}
