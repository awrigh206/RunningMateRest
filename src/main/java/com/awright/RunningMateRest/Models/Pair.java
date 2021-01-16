package com.awright.RunningMateRest.Models;

import java.io.Serializable;
import javax.persistence.Embeddable;
import com.awright.RunningMateRest.DTO.ChallengeDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Embeddable
@Getter
@Setter @EqualsAndHashCode
public class Pair extends ChallengeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    public Pair (ChallengeDto challengeDto){
        super(challengeDto.getIssuingUser(),challengeDto.getChallengedUser());
    }
}
