package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageRepository messageRepo;

    @Autowired
    public MessageService (MessageRepository repo){
        this.messageRepo = repo;
    }

    public void addMessage(MessageDto messageDto){
        Message message = new Message (messageDto);
        messageRepo.save(message);
    }

    public List<Message> getUsersMessages(String recipient){
        Optional <List<Message>> result = messageRepo.findByRecipient(recipient);
        if(result.isEmpty()){
            return new ArrayList<> ();
        }
        else{
            return result.get();
        }
    }

    public List<Message> getSentMessages(String sender){
        Optional <List<Message>> result = messageRepo.findBySender(sender);
        if(result.isEmpty()){
            return new ArrayList<> ();
        }
        else{
            return result.get();
        }
    }

    public void removeMessage(MessageDto messageDto){
        Message message = new Message(messageDto);
        messageRepo.delete(message);
    }
}
