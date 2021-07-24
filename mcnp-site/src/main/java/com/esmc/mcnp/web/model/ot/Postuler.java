package com.esmc.mcnp.web.model.ot;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Postuler {
    private Integer idPoste;
    private Integer idCandidature;
    private Integer idCandidaturePoste;
}
