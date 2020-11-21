package com.awright.RunningMateRest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class MessageDto {
    private String messageBody;
    private String sender;
    private String recipient;
    private String timeStamp;
    
}
