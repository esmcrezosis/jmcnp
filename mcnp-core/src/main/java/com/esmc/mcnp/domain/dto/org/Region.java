package com.esmc.mcnp.domain.dto.org;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Region implements Serializable {
    private Integer idRegion;
    private String nomRegion;
}
