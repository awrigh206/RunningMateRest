package com.awright.RunningMateRest.DTO;

import com.awright.RunningMateRest.Models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChallengeDto {
    private User issuingUser;
    private User challengedUser;
    
}
