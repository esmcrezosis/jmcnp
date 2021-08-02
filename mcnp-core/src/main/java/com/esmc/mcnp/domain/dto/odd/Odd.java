package com.esmc.mcnp.domain.dto.odd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Odd {
    private Integer idOdd;
    private String codeOdd;
    private String titre;
}
