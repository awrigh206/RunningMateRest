package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Instance implements Serializable{
    private static final long serialVersionUID = 2508557110934308736L;
    @Id
    // @GeneratedValue
    // private Integer id;
    private Integer instanceCode;
    @ManyToMany
    private List<User> usersInvolved;
    @ManyToMany
    private List<Message> textMessages;
    @ManyToMany
    private List<ImageMessage> imageMessages;
    @OneToMany
    private Map<String,Tracking> trackings;

    public Instance(){
        this.imageMessages = new ArrayList<>();
        this.textMessages = new ArrayList<>();
        this.trackings = new HashMap<>();
        this.usersInvolved = new ArrayList<>();
    }

    public void addUser(User toAdd){
        this.usersInvolved.add(toAdd);
        instanceCode = generateInstanceCode(this.usersInvolved);
    }

    public void addManyUsers(List<User> toAdd){
        usersInvolved.addAll(toAdd);
        instanceCode = generateInstanceCode(this.usersInvolved);
    }

    public void addTextMessage (Message toAdd){
        this.textMessages.add(toAdd);
    }

    public void addImageMessage (ImageMessage toAdd){
        this.imageMessages.add(toAdd);
    }
    
    public static Integer generateInstanceCodeFromNames(List<String> userNames){
        StringBuilder builder = new StringBuilder();
        for (String current : userNames){
            builder.append(current);
        }
        return generateInstanceCode(builder.toString());
    }

    public static Integer generateInstanceCode(List<User> usersInvolved){
        StringBuilder builder = new StringBuilder();
        for (User current : usersInvolved){
            builder.append(current.getName());
        }
        return generateInstanceCode(builder.toString());
    }

    public static Integer generateInstanceCode(String string){
        StringBuilder builder = new StringBuilder(string);
        String textCode = builder.toString().toUpperCase();
        int intCode = 0;
        for(char currentChar : textCode.toCharArray()){
            intCode+=currentChar;
        }
        return intCode;
    }
}
