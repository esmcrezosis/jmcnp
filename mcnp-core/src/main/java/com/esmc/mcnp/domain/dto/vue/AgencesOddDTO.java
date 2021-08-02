package com.esmc.mcnp.domain.dto.vue;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AgencesOddDTO implements Serializable {
    private Integer idAgencesOdd;
    private String codeAgencesOdd;
    private String libelleAgencesOdd;
}
