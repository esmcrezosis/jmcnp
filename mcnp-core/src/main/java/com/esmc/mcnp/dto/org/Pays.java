package com.esmc.mcnp.dto.org;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pays implements Serializable {
    private Integer idPays;
    private String codePays;
    private String codeTelephonique;
    private String libellePays;
    private String nationalite;
}
