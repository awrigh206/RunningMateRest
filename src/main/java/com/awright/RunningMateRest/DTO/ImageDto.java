package com.awright.RunningMateRest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter 
public class ImageDto {
    String name;
    String base64;
    String recipient;
    String sender;
}
