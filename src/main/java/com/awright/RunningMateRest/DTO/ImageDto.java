package com.awright.RunningMateRest.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter 
public class ImageDto {
    private String name;
    private String base64;
    private List<String> usersInvolved;
}
