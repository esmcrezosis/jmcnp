package com.esmc.mcnp.web.model.budget;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.esmc.mcnp.model.smcipn.EuTypeBudget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

	public EuTypeBudget toTypeBudget() {
		EuTypeBudget typeBudget = new EuTypeBudget();
		typeBudget.setId(this.id)
		          .setCodeTypeBudget(this.codeTypeBudget)
                  .setLibelleBudget(this.libelleBudget)
		          .setDateDebut(this.dateDebut)
		          .setDateFin(this.dateFin)
		          .setDateSoumission(this.dateSoumission)
		          .setDateValidation(this.dateValidation)
		          .setDureeBudget(this.dureeBudget)
		          .setTypeDuree(this.typeDuree)
		          .setNatureBudget(this.natureBudget)
		          .setStatut(this.statut);
		return typeBudget;
	}
	
	public EuTypeBudget toTypeBudget(EuTypeBudget typeBudget) {
		if (Objects.isNull(typeBudget)) {
			typeBudget = new EuTypeBudget();
		}
		typeBudget.setId(this.id)
		          .setCodeTypeBudget(this.codeTypeBudget)
                  .setLibelleBudget(this.libelleBudget)
		          .setDateDebut(this.dateDebut)
		          .setDateFin(this.dateFin)
		          .setDateSoumission(this.dateSoumission)
		          .setDateValidation(this.dateValidation)
		          .setDureeBudget(this.dureeBudget)
		          .setTypeDuree(this.typeDuree)
		          .setNatureBudget(this.natureBudget)
		          .setStatut(this.statut);
		return typeBudget;
	}
	
	public TypeBudget(EuTypeBudget typeBudget) {
		this.id = typeBudget.getId();
		this.codeTypeBudget = typeBudget.getCodeTypeBudget();
		this.libelleBudget = typeBudget.getLibelleBudget();
		this.typeDuree = typeBudget.getTypeDuree();
		this.dureeBudget = typeBudget.getDureeBudget();
		this.natureBudget = typeBudget.getNatureBudget();
		this.dateSoumission = typeBudget.getDateSoumission();
		this.dateValidation = typeBudget.getDateValidation();
		this.dateDebut = typeBudget.getDateDebut();
		this.dateFin = typeBudget.getDateFin();
		this.statut = typeBudget.getStatut();
	}
	
	public static List<TypeBudget> toListTypes(List<EuTypeBudget> typeBudgets) {
		List<TypeBudget> types = Lists.newArrayList();
		typeBudgets.forEach(t -> types.add(new TypeBudget(t)));
		return types;
	}
}
