package com.esmc.mcnp.domain.dto.org;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Prefecture implements Serializable {
    private int idPrefecture;
    private int idRegion;
    private String nomPrefecture;
}
