package com.esmc.mcnp.model.op;

import com.esmc.mcnp.model.odd.EuTypeCentrale;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "eu_vente_op")
public class EuVenteOp implements Serializable {

    @Id
    @Column(name = "id_vente_op")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenteOp;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    @Column(name = "reference_op")
    private String referenceOp;
    @Column(name = "libelle_vente_op")
    private String libelleVenteOp;
    @Column(name = "description_vente_op")
    private String descriptionVenteOp;
    @Column(name = "montant_total")
    private Double montantTotal;
    @Column(name = "montant_total_finale")
    private Double montantTotalFinal;
    @Column(name = "code_membre_apporteur")
    private String codeMembreApporteur;
    @Column(name = "valid_op")
    private Boolean validOp;
    private Boolean paye;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_centrale")
    private EuTypeCentrale typeCentrale;

}
