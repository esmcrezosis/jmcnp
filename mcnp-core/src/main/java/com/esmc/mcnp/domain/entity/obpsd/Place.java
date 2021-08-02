package com.esmc.mcnp.domain.entity.obpsd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import com.esmc.mcnp.commons.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "place")
@NoArgsConstructor
@Accessors(chain = true)
public class Place implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private String membre;
    private String montant;
    private String lib;
    private String datedepot;
    private String agence;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime heureid;
    private String cais;
    private boolean rembourser;
    
    @PostLoad
    public void convertDateDepot() {
    	Date date = DateUtils.parseDate(datedepot);
    	datedepot = DateUtils.format(date, "dd/MM/yyyy");
    }
}
