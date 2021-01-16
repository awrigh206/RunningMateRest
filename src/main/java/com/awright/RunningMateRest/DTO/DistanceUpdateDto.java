package com.awright.RunningMateRest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DistanceUpdateDto {
    private ChallengeDto challengeDto;
    private Double distanceTraveled;
    private Double heightTraveled;
    private Double timeTaken;
    
}
