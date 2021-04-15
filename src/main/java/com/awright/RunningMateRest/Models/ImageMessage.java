package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import java.util.Base64;

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
    @Column(length = 10000000)
    private byte[] bytes;
    private String name;
    private String sender;
    private String recepient;

    public ImageMessage (ImageDto dto){
        this.bytes = Base64.getDecoder().decode(dto.getBase64());
        this.name = dto.getName();
        this.sender = dto.getUsersInvolved().get(0);
        this.recepient = dto.getUsersInvolved().get(1);
    }
}
