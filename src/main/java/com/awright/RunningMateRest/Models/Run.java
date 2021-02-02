package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
    @OneToOne
    private Tracking tracking;
    private String issuingUser;
    private String challengedUser;
    public Run (Pair pair, Tracking tracking){
        this.issuingUser = pair.getInvolvedUsers().get(0);
        this.challengedUser = pair.getInvolvedUsers().get(1);
        this.tracking = tracking;
    }
}
