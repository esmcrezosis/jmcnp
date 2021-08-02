package com.esmc.mcnp.domain.dto.bc;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain = true)
public class CompteCredit implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long idCredit;
    private String codeMembre;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOctroi;
    private String source;
    private Double montantPlace;
    private Double montantCredit;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date datedeb;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date datefin;
    private String compteSource;
    private String codeTypeCredit;
    private String typeProduit;
    private String typeRecurrent;
    private Double duree;
    private Double prk;
    private Integer frequenceCumul;
    private Integer affecter;
    private String renouveller;
    private Integer nbreRenouvel;
    private Boolean activer;
    private Integer domicilier;
    private String krr;
    private Integer idBps;
    private Integer bnp;
    private String codeBnp;
}
