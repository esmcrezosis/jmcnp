package com.esmc.mcnp.web.model.ot;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
public class Formation {
    private Long id;
    private String codeFormation;
    private String libelleFormation;
    private String typeFormation;
    private LocalDateTime dateDeb;
    private LocalDateTime datefin;
    private Long duree;
    private LocalTime heureDeb;
    private LocalTime heureFin;
    private String codeZoom;
    private String codeMeet;
    private Boolean activer;
    private String statut;
}
