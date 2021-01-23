package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.ImageDto;
import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.DTO.UserDto;
import com.awright.RunningMateRest.Models.ImageMessage;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Models.User;
import com.awright.RunningMateRest.Repositories.ImageMessageRepository;
import com.awright.RunningMateRest.Repositories.MessageRepository;
import com.awright.RunningMateRest.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class MessageService {
    private MessageRepository messageRepo;
    private UserRepository userRepo;
    private ImageMessageRepository imageRepo;
    private UserService userService;
    private final Log log = LogFactory.getLog(MessageService.class);

    @Autowired
    public MessageService (MessageRepository repo, UserRepository userRepo, UserService userService, ImageMessageRepository imageRepo){
        this.messageRepo = repo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.imageRepo = imageRepo;
    }

    public void addImageMessage(ImageDto imageDto){
        ImageMessage message = new ImageMessage(imageDto);
        imageRepo.save(message);
        Optional<User> sender = userRepo.findByName(imageDto.getSender());
        Optional<User> recpient = userRepo.findByName(imageDto.getRecipient());
        if(sender.isPresent()){
            sender.get().addImage(message);
            userRepo.save(sender.get());
            log.info("added to sender");
        }

        if(recpient.isPresent()){
            recpient.get().addImage(message);
            userRepo.save(recpient.get());
        }
    }

    public void addMessage(MessageDto messageDto){
        log.info("message is : " + messageDto.toString());
        Message message = new Message (messageDto);
        messageRepo.save(message);
        Optional<User> sender = userRepo.findByName(messageDto.getSender());
        Optional<User> recpient = userRepo.findByName(messageDto.getRecipient());
        if(sender.isPresent()){
            sender.get().addMessage(message);
            userRepo.save(sender.get());
            log.info("added to sender");
        }

        if(recpient.isPresent()){
            recpient.get().addMessage(message);
            userRepo.save(recpient.get());
        }
    }

    public List<Message> getMyMessages(ChallengeDto challengeDto){
        UserDto issuingUser = new UserDto(challengeDto.getIssuingUser());
        List<Message> messages = userService.fetchUser(issuingUser).getMessages();
        List<Message> toSend = new ArrayList<>();

        for(Message current : messages){
            if(current.getSender().equals(issuingUser.getUserName()) || current.getRecipient().equals(issuingUser.getUserName())){
                toSend.add(current);
            }
        }
        if(toSend.isEmpty()){
            Message toAdd = new Message();
            toAdd.setMessageBody("You currently do not have any messages");
            toAdd.setSender("The System");
            toAdd.setTimeStamp("Enternity");
            toAdd.setRecipient(challengeDto.getIssuingUser());
            toSend.add(toAdd);
        }
        return toSend;
    }

    public List<ImageDto> getMyImages(ChallengeDto challengeDto){
        UserDto issuingUser = new UserDto(challengeDto.getIssuingUser());
        List<ImageMessage> images = userService.fetchUser(issuingUser).getImages();
        List<ImageDto> toSend = new ArrayList<>();

        for(ImageMessage current : images){
            if(current.getSender().equals(issuingUser.getUserName()) || current.getRecepient().equals(issuingUser.getUserName())){
                String base64 = Base64.getEncoder().encodeToString(current.getBytes());
                ImageDto sendable = new ImageDto(current.getName(),base64 , current.getRecepient(), current.getSender());
                toSend.add(sendable);
            }
        }
        return toSend;
    }

    public void removeMessage(MessageDto messageDto){
        Message message = new Message(messageDto);
        messageRepo.delete(message);
    }
}
