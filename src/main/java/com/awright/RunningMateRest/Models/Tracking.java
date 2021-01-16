package com.awright.RunningMateRest.Models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode @Embeddable
public class Tracking implements Serializable{
    private static final long serialVersionUID = 1L;
    private double distance;
    private double altitude;
    private double time;

    public Tracking(){
        this.distance =0.0;
        this.altitude =0.0;
        this.time=0.0;
    }
}
