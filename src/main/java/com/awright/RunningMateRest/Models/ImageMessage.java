package com.awright.RunningMateRest.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.awright.RunningMateRest.DTO.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class ImageMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    private Integer id;
    @Column(length = 50000)
    private String base64;
    private String name;
    private String sender;
    private String recepient;

    public ImageMessage (ImageDto dto){
        this.base64 = dto.getBase64();
        this.name = dto.getName();
        this.sender = dto.getSender();
        this.recepient = dto.getRecipient();
    }
}
