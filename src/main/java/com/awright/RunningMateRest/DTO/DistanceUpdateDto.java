package com.awright.RunningMateRest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class DistanceUpdateDto {
    private ChallengeDto challengeDto;
    private Double distanceTraveled;
    private Double heightTraveled;
    private Double timeTaken;
    
}
