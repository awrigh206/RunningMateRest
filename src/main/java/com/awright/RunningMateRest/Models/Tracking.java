package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor
@Entity
public class Tracking implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private Integer id;
    private double distance;
    private double altitude;
    private double time;

    public Tracking(double distance, double altitude, double time){
        this.distance = distance;
        this.altitude = altitude;
        this.time = time;
    }

    public Tracking(){
        this.distance =0.0;
        this.altitude =0.0;
        this.time=0.0;
    }
}
