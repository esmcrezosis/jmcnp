package com.esmc.mcnp.web.model.budget;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.esmc.mcnp.model.smcipn.EuBudget;
import com.esmc.mcnp.model.smcipn.EuCategorieBudget;
import com.esmc.mcnp.model.smcipn.EuTypeBudget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Double montantValide;
    private Double montantBudget;
    private Double montantUtilise;
    private Double soldeBudget;
    private String statutBudget;
    private boolean valider;
    private TypeBudget typeBudget;
    private EuCategorieBudget categorie;

    public Budget(EuBudget budget) {
        Preconditions.checkNotNull(budget);
        this.id = budget.getId();
        this.categorie = budget.getCategorieBudget();
        this.typeBudget = new TypeBudget(budget.getTypeBudget());
        this.codeBudget = budget.getCodeBudget();
        this.nomBudget = budget.getNomBudget();
        this.dateBudget = budget.getDateBudget();
        this.montantDemande = budget.getMontantDemande();
        this.montantBudget = budget.getMontantBudget();
        this.montantUtilise = budget.getMontantUtilise();
        this.montantValide = budget.getMontantValide();
        this.soldeBudget = budget.getSoldeBudget();
        this.statutBudget = budget.getStatutBudget();
        this.valider = budget.isValider();
    }

    public EuBudget toEuBudget() {
        EuBudget budget = new EuBudget();
        budget.setCategorieBudget(this.categorie)
                .setId(this.id)
                .setCodeBudget(this.codeBudget)
                .setNomBudget(this.nomBudget)
                .setTypeBudget(this.typeBudget.toTypeBudget())
                .setMontantDemande(this.montantDemande)
                .setMontantValide(this.montantValide)
                .setMontantBudget(this.montantBudget)
                .setMontantUtilise(this.montantUtilise)
                .setSoldeBudget(this.soldeBudget)
                .setDateBudget(this.dateBudget)
                .setStatutBudget(this.statutBudget)
                .setValider(this.valider);
        return budget;
    }

    public EuBudget toEuBudget(EuBudget budget) {
        budget.setCategorieBudget(this.categorie)
                .setId(this.id)
                .setCodeBudget(this.codeBudget)
                .setNomBudget(this.nomBudget)
                .setTypeBudget(this.typeBudget.toTypeBudget())
                .setMontantDemande(this.montantDemande)
                .setMontantValide(this.montantValide)
                .setMontantBudget(this.montantBudget)
                .setMontantUtilise(this.montantUtilise)
                .setSoldeBudget(this.soldeBudget)
                .setDateBudget(this.dateBudget)
                .setStatutBudget(this.statutBudget)
                .setValider(this.valider);
        return budget;
    }

    public static List<Budget> toListBudget(List<EuBudget> budgets) {
        List<Budget> res = Lists.newArrayList();
        budgets.forEach(t -> res.add(new Budget(t)));
        return res;
    }

}
