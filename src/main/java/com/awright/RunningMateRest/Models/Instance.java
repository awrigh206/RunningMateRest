package com.awright.RunningMateRest.Models;

import java.util.List;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class Instance {
    @OneToMany
    private List<User> usersInvolved;
    @ManyToMany
    private List<Message> textMessages;
    @ManyToMany
    private List<ImageMessage> imageMessages;

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
