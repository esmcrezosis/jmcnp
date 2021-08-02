package com.esmc.mcnp.domain.dto.odd;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.domain.entity.odd.EuCentrales;
import com.esmc.mcnp.domain.entity.odd.EuTypeCentrale;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
public class Centrales implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @DateTimeFormat
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreationCentrales;
    private String referenceCentrale;
    private String libelleCentrale;
    private AgencesOdd agencesOdd;
    private Centres centre;
    private EuTypeCentrale euTypeCentrale;

    public Centrales(EuCentrales centrales) {
        this.id = centrales.getIdCentrales();
        this.dateCreationCentrales = centrales.getDateCreationCentrales();
        this.referenceCentrale = centrales.getReferenceCentrale();
        this.libelleCentrale = centrales.getLibelleCentrale();
        if (Objects.nonNull(centrales.getEuAgencesOdd())) {
            this.agencesOdd = new AgencesOdd(centrales.getEuAgencesOdd());
        }
        if (Objects.nonNull(centrales.getEuCentres())) {
            this.centre = new Centres(centrales.getEuCentres());
        }
        this.euTypeCentrale = centrales.getEuTypeCentrale();
    }

    public EuCentrales toEuCentrales() {
        EuCentrales centrales = new EuCentrales();
        centrales.setIdCentrales(this.id);
        centrales.setDateCreationCentrales(this.dateCreationCentrales);
        centrales.setReferenceCentrale(this.referenceCentrale);
        centrales.setLibelleCentrale(this.libelleCentrale);
        if (Objects.nonNull(this.agencesOdd)) {
            centrales.setEuAgencesOdd(this.agencesOdd.toEuAgencesOdd());
        }
        if (Objects.nonNull(this.centre)) {
            centrales.setEuCentres(this.centre.toEuCentres());
        }
        centrales.setEuTypeCentrale(this.euTypeCentrale);
        return centrales;
    }

    public EuCentrales toEuCentrales(EuCentrales centrales) {
        if (Objects.isNull(centrales)) {
            centrales = new EuCentrales();
        }
        centrales.setIdCentrales(this.id);
        centrales.setDateCreationCentrales(this.dateCreationCentrales);
        centrales.setReferenceCentrale(this.referenceCentrale);
        centrales.setLibelleCentrale(this.libelleCentrale);
        if (Objects.nonNull(this.agencesOdd)) {
            centrales.setEuAgencesOdd(this.agencesOdd.toEuAgencesOdd());
        }
        if (Objects.nonNull(this.centre)) {
            centrales.setEuCentres(this.centre.toEuCentres());
        }
        centrales.setEuTypeCentrale(this.euTypeCentrale);
        return centrales;
    }

    public static List<Centrales> toCentralesList(List<EuCentrales> centrales) {
        List<Centrales> res = Lists.newArrayList();
        centrales.forEach(c -> res.add(new Centrales(c)));
        return res;
    }
}