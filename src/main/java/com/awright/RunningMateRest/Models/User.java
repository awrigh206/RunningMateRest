package com.awright.RunningMateRest.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.awright.RunningMateRest.DTO.UserDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @EqualsAndHashCode
@Entity
public class User {
    @GeneratedValue
    @Id
    private Integer id;
    private String userName;
    private String password;
    private String email;

    public User (UserDto userDto){
        this.userName = userDto.getUserName();
        this.password = userDto.getPassword();
        this.email = userDto.getEmail();
    }
}
