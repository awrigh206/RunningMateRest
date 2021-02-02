package com.awright.RunningMateRest.Controllers;

import java.util.List;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.ImageDto;
import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Services.MessageService;
import com.awright.RunningMateRest.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@RequestMapping(path = "/message")
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private UserService userService;
    private final Log log = LogFactory.getLog(MessageController.class);
    
    @Autowired
    public MessageController(MessageService messageService, UserService userService){
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMessage(@RequestBody MessageDto messageDto){
        messageService.addMessage(messageDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/image")
    public void createImageMessage(@RequestBody ImageDto messageDto){
        log.info("Adding image message with the name: " + messageDto.getName());
        ChallengeDto challenge = new ChallengeDto(messageDto.getUsersInvolved());
        messageService.addImageMessage(messageDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/images")
    public List<ImageDto> getMyImages(@RequestBody ChallengeDto messageDto){
        return messageService.getMyImages(messageDto);
    }


    @PutMapping
    public List<Message> getMyMessages(@RequestBody ChallengeDto challengeDto){
        return messageService.getMyMessages(challengeDto);
    }

    @DeleteMapping
    public void removeMessage(@RequestBody MessageDto messageDto){
        messageService.removeMessage(messageDto);
    }
}
