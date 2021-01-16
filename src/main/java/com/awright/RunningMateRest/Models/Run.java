package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
public class Run implements Serializable{
    static final long serialVersionUID = 71061845565127165L;
    @Embedded
    private Map<String,Tracking> trackingInfo;
    @Id
    @GeneratedValue
    private Integer id;
    @Embedded
    private Pair pair;
    public Run (Pair pair){
        this.pair = pair;
        this.trackingInfo = new HashMap<>();
        trackingInfo.put(pair.getIssuingUser(), new Tracking());
        trackingInfo.put(pair.getChallengedUser(), new Tracking());
    }
}
