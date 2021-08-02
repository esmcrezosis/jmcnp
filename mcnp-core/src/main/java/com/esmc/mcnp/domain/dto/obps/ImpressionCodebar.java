package com.esmc.mcnp.domain.dto.obps;

import java.io.Serializable;


public class ImpressionCodebar implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5961865861583088354L;
    private String typeImpression;
    private String referenceMat;
    private String codemembre;
    private String descriptionMat;
    
    public ImpressionCodebar() {
        // TODO Auto-generated constructor stub
    }

    public String getTypeImpression() {
        return typeImpression;
    }

    public void setTypeImpression(String typeImpression) {
        this.typeImpression = typeImpression;
    }

    public String getReferenceMat() {
        return referenceMat;
    }

    public void setReferenceMat(String referenceMat) {
        this.referenceMat = referenceMat;
    }

    public String getCodemembre() {
        return codemembre;
    }

    public void setCodemembre(String codemembre) {
        this.codemembre = codemembre;
    }

    public String getDescriptionMat() {
        return descriptionMat;
    }

    public void setDescriptionMat(String descriptionMat) {
        this.descriptionMat = descriptionMat;
    }

   
}
