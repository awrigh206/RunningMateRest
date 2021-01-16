package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import antlr.collections.List;
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
    @Embedded
    private Tracking tracking;
    @Id
    @GeneratedValue
    private Integer id;
    @Embedded
    private Pair pair;
    public Run (Pair pair){
        this.pair = pair;
        this.tracking = new Tracking();
    }
}
