package com.awright.RunningMateRest.Controllers;

import java.util.List;

import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Services.MessageService;
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
@RequestMapping(path = "/message")
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    
    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMessage(@RequestBody MessageDto messageDto){
        messageService.addMessage(messageDto);
    }

    @GetMapping
    public List<Message> getMyMessages(@RequestParam String userName){
        return messageService.getUsersMessages(userName);
    }

    @GetMapping
    @RequestMapping(path = "/sent")
    public List<Message> getSentMessages(@RequestParam String userName){
        return messageService.getSentMessages(userName);
    }
}
