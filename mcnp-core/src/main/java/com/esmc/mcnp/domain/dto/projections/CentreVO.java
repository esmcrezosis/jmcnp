package com.esmc.mcnp.domain.dto.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CentreVO {
    private Integer id;
    private String libelleCentre;
    private String referenceCentre;
}
