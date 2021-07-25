package com.esmc.mcnp.dto.smcipn;

import com.esmc.mcnp.model.smcipn.EuTypeBudget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TypeBudget implements Serializable {

	private Long id;
	private String codeTypeBudget;
	private String libelleBudget;
	private String typeDuree;
	private Integer dureeBudget;
	private String natureBudget;
	@JsonFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateSoumission;
	@JsonFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateValidation;
	@JsonFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateDebut;
	@JsonFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateFin;
	private String statut;
}
