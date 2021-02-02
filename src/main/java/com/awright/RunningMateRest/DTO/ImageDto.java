package com.awright.RunningMateRest.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter 
public class ImageDto {
    String name;
    String base64;
    List<String> usersInvolved;
}
