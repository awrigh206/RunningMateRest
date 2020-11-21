package com.awright.RunningMateRest.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.awright.RunningMateRest.DTO.MessageDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString @EqualsAndHashCode @NoArgsConstructor
@Entity
public class Message {
    @GeneratedValue 
    @Id
    private Integer id;
    private String messageBody;
    private String timeStamp;
    private String sender;
    private String recipient;

    public Message (MessageDto messageDto){
        this.messageBody = messageDto.getMessageBody();
        this.timeStamp = messageDto.getTimeStamp();
        this.sender = messageDto.getSender();
        this.recipient = messageDto.getRecipient();
    }
}
