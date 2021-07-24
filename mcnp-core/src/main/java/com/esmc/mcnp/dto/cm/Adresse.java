package com.esmc.mcnp.dto.cm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
    private String rue;
    private String codePostal;
    private String quartier;
    private String ville;
}
