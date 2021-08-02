package com.esmc.mcnp.domain.dto.cm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Religion implements Serializable {
    private Integer idReligionMembre;
    private String libelleReligion;
}
