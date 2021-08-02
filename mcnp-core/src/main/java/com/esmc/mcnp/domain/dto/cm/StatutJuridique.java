package com.esmc.mcnp.domain.dto.cm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatutJuridique implements Serializable {
    private String codeStatut;
    private String libelleStatut;
    private String typeStatut;
}
