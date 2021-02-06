package com.awright.RunningMateRest.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import com.awright.RunningMateRest.DTO.ImageDto;
import com.awright.RunningMateRest.DTO.MessageDto;
import com.awright.RunningMateRest.Models.ImageMessage;
import com.awright.RunningMateRest.Models.Instance;
import com.awright.RunningMateRest.Models.Message;
import com.awright.RunningMateRest.Repositories.ImageMessageRepository;
import com.awright.RunningMateRest.Repositories.InstanceRepo;
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
    private InstanceService instanceService;
    private UserService userService;
    private final Log log = LogFactory.getLog(MessageService.class);
    @Autowired
    private InstanceRepo instanceRepo;

    @Autowired
    public MessageService (MessageRepository repo, UserRepository userRepo, UserService userService, ImageMessageRepository imageRepo, InstanceService instanceService){
        this.messageRepo = repo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.imageRepo = imageRepo;
        this.instanceService = instanceService;
    }

    public void addImageMessage(ImageDto imageDto){
        ImageMessage message = new ImageMessage(imageDto);
        imageRepo.save(message);
        Integer code = Instance.generateInstanceCodeFromNames(imageDto.getUsersInvolved());
        Instance instance = instanceService.getInstance(code);
        instance.addImageMessage(message);
        instanceRepo.save(instance);
    }

    public void addMessage(MessageDto messageDto){
        log.info("message is : " + messageDto.toString());
        Message message = new Message (messageDto);
        Integer code = Instance.generateInstanceCodeFromNames(messageDto.getUsersInvolved());
        Instance instance = instanceService.getInstance(code);
        messageRepo.save(message);
        instance.addTextMessage(message);
        instanceRepo.save(instance);
    }

    public List<Message> getMyMessages(ChallengeDto challengeDto){
        Instance instance = instanceService.getInstance(challengeDto);
        List<Message> toSend = instance.getTextMessages();
        return toSend;
    }

    public List<ImageDto> getMyImages(ChallengeDto challengeDto){
        Instance instance = instanceService.getInstance(challengeDto);
        // UserDto issuingUser = new UserDto(challengeDto.getIssuingUser());
        List<ImageMessage> images = instance.getImageMessages();
        List<ImageDto> toSend = new ArrayList<>();

        for(ImageMessage current : images){
                String base64 = Base64.getEncoder().encodeToString(current.getBytes());
                ImageDto sendable = new ImageDto(current.getName(),base64 , new ArrayList<>(Arrays.asList(current.getSender(),current.getRecepient())));
                toSend.add(sendable);
        }
        return toSend;
    }

    public void removeMessage(MessageDto messageDto){
        Message message = new Message(messageDto);
        messageRepo.delete(message);
    }
}
