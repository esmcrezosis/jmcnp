/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.obps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class LGcp implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeCompte;
    private List<TpaGcpView> gcps;
    private Double solde;
    private int nbre_ntf;
    private float tx_agio;
    private String type_escompte;
    private int op;
    private String codeComptepbf;

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public List<TpaGcpView> getGcps() {
        if (gcps == null) {
            gcps = new ArrayList<>();
        }
        return gcps;
    }

    public void setGcps(List<TpaGcpView> gcps) {
        this.gcps = gcps;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public int getNbre_ntf() {
        return nbre_ntf;
    }

    public void setNbre_ntf(int nbre_ntf) {
        this.nbre_ntf = nbre_ntf;
    }

    public float getTx_agio() {
        return tx_agio;
    }

    public void setTx_agio(float tx_agio) {
        this.tx_agio = tx_agio;
    }

    public String getType_escompte() {
        return type_escompte;
    }

    public void setType_escompte(String type_escompte) {
        this.type_escompte = type_escompte;
    }

    public String getCodeComptepbf() {
        return codeComptepbf;
    }

    public void setCodeComptepbf(String codeComptepbf) {
        this.codeComptepbf = codeComptepbf;
    }

}
