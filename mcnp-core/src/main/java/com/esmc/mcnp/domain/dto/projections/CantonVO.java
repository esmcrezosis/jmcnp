package com.esmc.mcnp.domain.dto.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class CantonVO {
    private Integer idCanton;
    private String nomCanton;
}
