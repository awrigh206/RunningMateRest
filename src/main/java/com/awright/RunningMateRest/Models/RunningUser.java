package com.awright.RunningMateRest.Models;

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

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class RunningUser {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Run run;
    private String name;
    private Double totalDistanceTravelled;    

    public RunningUser(Run run, String name){
        this.run = run;
        this.name = name;
        totalDistanceTravelled = 0.0;
    }
}
