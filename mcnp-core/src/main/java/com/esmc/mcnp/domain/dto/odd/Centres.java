package com.esmc.mcnp.domain.dto.odd;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.domain.dto.org.*;
import com.esmc.mcnp.domain.entity.odd.EuCentres;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the eu_centres database table.
 */
@Data
@NoArgsConstructor
public class Centres implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCentres;
    private String referenceCentre;
    private String libelleCentre;
    private String descriptionCentre;
    private Double surfaceCentre;
    private String telephoneCentre;
    private String bpCentre;
    private String adresseCentre;
    private String typeCentre;
    private String codeMembre;
    private Canton canton;
    private Prefecture prefecture;
    private Region region;
    private Pays pays;
    private Zone zone;

    public Centres(EuCentres centre) {
        this.id = centre.getIdCentres();
        this.libelleCentre = centre.getLibelleCentre();
        this.referenceCentre = centre.getReferenceCentre();
        this.dateCentres = centre.getDateCentres();
        this.surfaceCentre = centre.getSurfaceCentre();
        this.telephoneCentre = centre.getTelephoneCentre();
        this.bpCentre = centre.getBpCentre();
        this.adresseCentre = centre.getAdresseCentre();
        this.typeCentre = centre.getTypeCentre();
        if (Objects.nonNull(centre.getMembre())) {
            this.codeMembre = centre.getMembre().getCodeMembreMorale();
        }
    }

    public EuCentres toEuCentres() {
        EuCentres centre = new EuCentres();
        centre.setIdCentres(this.id);
        centre.setDateCentres(this.dateCentres);
        centre.setReferenceCentre(this.referenceCentre);
        centre.setLibelleCentre(this.libelleCentre);
        centre.setSurfaceCentre(this.surfaceCentre);
        centre.setTelephoneCentre(this.telephoneCentre);
        centre.setBpCentre(this.bpCentre);
        centre.setAdresseCentre(this.adresseCentre);
        centre.setTypeCentre(this.typeCentre);
        return centre;
    }

    public EuCentres toEuCentres(EuCentres centre) {
        if (centre == null) {
            centre = new EuCentres();
        }
        centre.setIdCentres(this.id);
        centre.setDateCentres(this.dateCentres);
        centre.setReferenceCentre(this.referenceCentre);
        centre.setLibelleCentre(this.libelleCentre);
        centre.setSurfaceCentre(this.surfaceCentre);
        centre.setTelephoneCentre(this.telephoneCentre);
        centre.setBpCentre(this.bpCentre);
        centre.setAdresseCentre(this.adresseCentre);
        centre.setTypeCentre(this.typeCentre);
        return centre;
    }

    public static List<Centres> toListCentres(List<EuCentres> centres) {
        List<Centres> res = Lists.newArrayList();
        if (!centres.isEmpty()) {
            centres.forEach(t -> res.add(new Centres(t)));
        }
        return res;
    }

}