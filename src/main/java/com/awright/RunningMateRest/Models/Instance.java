package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
public class Instance implements Serializable{
    private static final long serialVersionUID = 2508557110934308736L;
    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany
    private List<User> usersInvolved;
    @ManyToMany
    private List<Message> textMessages;
    @ManyToMany
    private List<ImageMessage> imageMessages;
    @ManyToOne
    private Run run;

    public void addUser(User toAdd){
        this.usersInvolved.add(toAdd);
    }

    public void addTextMessage (Message toAdd){
        this.textMessages.add(toAdd);
    }

    public void addImageMessage (ImageMessage toAdd){
        this.imageMessages.add(toAdd);
    }
}
