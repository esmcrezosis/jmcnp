package com.esmc.mcnp.dto.smcipn;

import com.esmc.mcnp.model.smcipn.EuBudget;
import com.esmc.mcnp.model.smcipn.EuCategorieBudget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Budget implements Serializable {

	private Long id;
	private String codeBudget;
	private String nomBudget;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dateBudget;
	private Double montantDemande;
	private Double montantBudget;
	private Double montantUtilise;
	private Double soldeBudget;
	private String statutBudget;
	private boolean valider;
	private TypeBudget typeBudget;
	private EuCategorieBudget categorie;

}
