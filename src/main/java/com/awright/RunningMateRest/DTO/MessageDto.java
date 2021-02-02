package com.awright.RunningMateRest.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class MessageDto {
    private String messageBody;
    private List<String> usersInvolved;
    private String timeStamp;
    
}
