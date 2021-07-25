package com.esmc.mcnp.dto.obps;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GenererCode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String typeOperation; 
    private int nbreCode;
    private String codeMembreFour;
    private String raisonSocialeFour;
    private List<String> listCodeBarGros;
    private List<String> listCodeBarSemiGros;
    private List<String> listCodeBarDetail;
    private Long idUtilisateur;
    private Long idFiliereTechno;
    private Date dateFourniture;
    private String codeMembreDem;
    private String typeDemandeur;
    
    
    public GenererCode() {
        // TODO Auto-generated constructor stub
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public int getNbreCode() {
        return nbreCode;
    }

    public void setNbreCode(int nbreCode) {
        this.nbreCode = nbreCode;
    }

    public String getCodeMembreFour() {
        return codeMembreFour;
    }

    public void setCodeMembreFour(String codeMembreFour) {
        this.codeMembreFour = codeMembreFour;
    }

    public String getRaisonSocialeFour() {
        return raisonSocialeFour;
    }

    public void setRaisonSocialeFour(String raisonSocialeFour) {
        this.raisonSocialeFour = raisonSocialeFour;
    }

    public List<String> getListCodeBarGros() {
        return listCodeBarGros;
    }

    public void setListCodeBarGros(List<String> listCodeBarGros) {
        this.listCodeBarGros = listCodeBarGros;
    }

    public List<String> getListCodeBarSemiGros() {
        return listCodeBarSemiGros;
    }

    public void setListCodeBarSemiGros(List<String> listCodeBarSemiGros) {
        this.listCodeBarSemiGros = listCodeBarSemiGros;
    }

    public List<String> getListCodeBarDetail() {
        return listCodeBarDetail;
    }

    public void setListCodeBarDetail(List<String> listCodeBarDetail) {
        this.listCodeBarDetail = listCodeBarDetail;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Long getIdFiliereTechno() {
        return idFiliereTechno;
    }

    public void setIdFiliereTechno(Long idFiliereTechno) {
        this.idFiliereTechno = idFiliereTechno;
    }

   

    public Date getDateFourniture() {
        return dateFourniture;
    }

    public void setDateFourniture(Date dateFourniture) {
        this.dateFourniture = dateFourniture;
    }

    public String getCodeMembreDem() {
        return codeMembreDem;
    }

    public void setCodeMembreDem(String codeMembreDem) {
        this.codeMembreDem = codeMembreDem;
    }

    public String getTypeDemandeur() {
        return typeDemandeur;
    }

    public void setTypeDemandeur(String typeDemandeur) {
        this.typeDemandeur = typeDemandeur;
    }

  

   
}
