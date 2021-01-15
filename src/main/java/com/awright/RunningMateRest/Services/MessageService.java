package com.awright.RunningMateRest.Services;

import java.util.Optional;
import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.MessageRepository;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageRepository messageRepo;
    private UserRepository userRepo;

    @Autowired
    public MessageService (MessageRepository repo, UserRepository userRepo){
        this.messageRepo = repo;
        this.userRepo = userRepo;
    }

    public void addMessage(MessageDto messageDto){
        Message message = new Message (messageDto);
        Optional<User> sender = userRepo.findByName(messageDto.getSender());
        Optional<User> recpient = userRepo.findByName(messageDto.getRecipient());
        if(sender.isPresent()){
            sender.get().addMessage(message);
            userRepo.save(sender.get());
        }

        if(recpient.isPresent()){
            recpient.get().addMessage(message);
            userRepo.save(recpient.get());
        }
    }

    public void removeMessage(MessageDto messageDto){
        Message message = new Message(messageDto);
        messageRepo.delete(message);
    }
}
