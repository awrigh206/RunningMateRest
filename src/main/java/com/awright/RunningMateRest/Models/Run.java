package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode @ToString
@Entity
public class Run implements Serializable{
    static final long serialVersionUID = 71061845565127165L;
    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany
    private Map<String,Tracking> tracking;
    private String issuingUser;
    private String challengedUser;
    private boolean ready;
    public Run (Pair pair, Map<String,Tracking> tracking){
        this.issuingUser = pair.getIssuingUser();
        this.challengedUser = pair.getChallengedUser();
        this.tracking = new HashMap<>();
        this.tracking = tracking;
        this.ready = false;
    }
}
