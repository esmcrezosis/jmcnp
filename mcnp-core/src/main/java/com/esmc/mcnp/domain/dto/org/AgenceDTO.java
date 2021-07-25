package com.esmc.mcnp.dto.org;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDTO implements Serializable {
    private String codeAgence;
    private String codeMembre;
    private Date dateCreation;
    private String libelleAgence;
    private Integer partenaire;
    private String typeGac;
}
