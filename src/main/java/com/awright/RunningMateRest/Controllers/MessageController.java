package com.awright.RunningMateRest.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Services.MessageService;
import com.awright.RunningMateRest.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(path = "/message")
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private UserService userService;
    
    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMessage(@RequestBody MessageDto messageDto){
        messageService.addMessage(messageDto);
    }

    @PutMapping
    public List<Message> getMyMessages(@RequestBody ChallengeDto challengeDto){
        UserDto issuingUser = new UserDto(challengeDto.getIssuingUser());
        List<Message> messages = userService.fetchUser(issuingUser).getMessages();
        List<Message> toSend = new ArrayList<>();

        for(Message current : messages){
            if(current.getSender().equals(challengeDto.getChallengedUser())){
                toSend.add(current);
            }
        }
        return toSend;
    }

    //I don't think I need  this
    // @PutMapping
    // @RequestMapping(path = "/sent")
    // public List<Message> getSentMessages(@RequestBody ChallengeDto challengeDto){
    //     UserDto issuingUser = new UserDto(challengeDto.getIssuingUser());
    //     List<Message> messages = userService.fetchUser(issuingUser).getMessages();
    //     List<Message> toSend = new ArrayList<>();

    //     for(Message current : messages){
    //         if(current.getSender().equals(challengeDto.getChallengedUser())){
    //             toSend.add(current);
    //         }
    //     }
    //     return toSend;
    // }

    @DeleteMapping
    public void removeMessage(@RequestBody MessageDto messageDto){
        messageService.removeMessage(messageDto);
    }
}
