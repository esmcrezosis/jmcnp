package com.esmc.mcnp.dto.odd;

import com.esmc.mcnp.dto.cm.MembreMorale;
import com.esmc.mcnp.dto.org.Canton;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.odd.EuOdd;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class AgencesOdd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codeAgencesOdd;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateAgencesOdd;
    private String libelleAgencesOdd;
    private String referenceAgencesOdd;
    private String adresseAgencesOdd;
    private String bpAgencesOdd;
    private String telephoneAgencesOdd;
    private Boolean agencesOddSource;
    private Centres centre;
    private EuOdd euOdd;
    private Canton canton;
    private MembreMorale membreMorale;

    public AgencesOdd(EuAgencesOdd agencesOdd) {
        Preconditions.checkNotNull(agencesOdd);
        this.id = agencesOdd.getIdAgencesOdd();
        this.codeAgencesOdd = agencesOdd.getCodeAgencesOdd();
        this.dateAgencesOdd = agencesOdd.getDateAgencesOdd();
        this.libelleAgencesOdd = agencesOdd.getLibelleAgencesOdd();
        this.referenceAgencesOdd = agencesOdd.getReferenceAgencesOdd();
        this.adresseAgencesOdd = agencesOdd.getAdresseAgencesOdd();
        this.bpAgencesOdd = agencesOdd.getBpAgencesOdd();
        this.telephoneAgencesOdd = agencesOdd.getTelephoneAgencesOdd();
        this.agencesOddSource = agencesOdd.getAgencesOddSource();
        if (Objects.nonNull(agencesOdd.getEuCentre())) {
            this.centre = new Centres(agencesOdd.getEuCentre());
        }
        if (Objects.nonNull(agencesOdd.getEuCanton())) {
            this.canton = new Canton(agencesOdd.getEuCanton());
        }
        if (Objects.nonNull(agencesOdd.getMembre())) {
            this.membreMorale = new MembreMorale(agencesOdd.getMembre().getCodeMembreMorale());
        }
        this.euOdd = agencesOdd.getEuOdd();
    }

    public EuAgencesOdd toEuAgencesOdd() {
        EuAgencesOdd agencesOdd = new EuAgencesOdd();
        agencesOdd.setIdAgencesOdd(this.id)
                .setCodeAgencesOdd(this.codeAgencesOdd)
                .setDateAgencesOdd(this.dateAgencesOdd)
                .setLibelleAgencesOdd(this.libelleAgencesOdd)
                .setReferenceAgencesOdd(this.referenceAgencesOdd)
                .setAdresseAgencesOdd(this.adresseAgencesOdd)
                .setBpAgencesOdd(this.bpAgencesOdd)
                .setTelephoneAgencesOdd(this.telephoneAgencesOdd)
                .setAgencesOddSource(this.agencesOddSource);
        if (Objects.nonNull(this.centre)) {
            agencesOdd.setEuCentre(this.centre.toEuCentres());
        }
        if (Objects.nonNull(this.canton)) {
            agencesOdd.setEuCanton(this.canton.toEuCanton());
        }
        if (Objects.nonNull(agencesOdd.getMembre())) {
            agencesOdd.setMembre(new EuMembreMorale(this.membreMorale.getCodeMembreMorale()));
        }
        agencesOdd.setEuOdd(this.euOdd);
        return agencesOdd;
    }

    public EuAgencesOdd toEuAgencesOdd(EuAgencesOdd agencesOdd) {
        agencesOdd.setIdAgencesOdd(this.id)
                .setCodeAgencesOdd(this.codeAgencesOdd)
                .setDateAgencesOdd(this.dateAgencesOdd)
                .setLibelleAgencesOdd(this.libelleAgencesOdd)
                .setReferenceAgencesOdd(this.referenceAgencesOdd)
                .setAdresseAgencesOdd(this.adresseAgencesOdd)
                .setBpAgencesOdd(this.bpAgencesOdd)
                .setTelephoneAgencesOdd(this.telephoneAgencesOdd)
                .setAgencesOddSource(this.agencesOddSource);
        if (Objects.nonNull(this.centre)) {
            agencesOdd.setEuCentre(this.centre.toEuCentres());
        }
        if (Objects.nonNull(this.canton)) {
            agencesOdd.setEuCanton(this.canton.toEuCanton());
        }
        if (Objects.nonNull(agencesOdd.getMembre())) {
            agencesOdd.setMembre(new EuMembreMorale(this.membreMorale.getCodeMembreMorale()));
        }
        agencesOdd.setEuOdd(this.euOdd);
        return agencesOdd;
    }

    public static List<AgencesOdd> toAgencesOddList(List<EuAgencesOdd> agencesOdds) {
        List<AgencesOdd> res = Lists.newArrayList();
        agencesOdds.forEach(a -> res.add(new AgencesOdd(a)));
        return res;
    }
}