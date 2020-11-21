package com.awright.RunningMateRest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DistanceUpdateDto {
    private String runnerNameOne;
    private String runnerNameTwo;
    private Double distanceTraveled;
    private Double heightTraveled;
    
}
