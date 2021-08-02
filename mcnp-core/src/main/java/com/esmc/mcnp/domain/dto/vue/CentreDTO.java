package com.esmc.mcnp.domain.dto.vue;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CentreDTO implements Serializable {
    private Integer idCentres;
    private String referenceCentre;
    private String libelleCentre;
}
