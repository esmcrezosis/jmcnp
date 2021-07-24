package com.esmc.mcnp.dto.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CentreVO {
    private Integer id;
    private String libelleCentre;
    private String referenceCentre;
}
