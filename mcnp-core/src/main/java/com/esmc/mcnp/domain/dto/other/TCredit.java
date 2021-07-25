/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.other;

import java.io.Serializable;

/**
 *
 * @author Mawuli
 */
public class TCredit implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code_tcredit;
    private String lib_tcredit;
    private String code_cat;
    private String type_produit;

    public TCredit() {
    }

    public TCredit(String code_tcredit, String lib_tcredit, String code_cat, String type_produit) {
        this.code_tcredit = code_tcredit;
        this.lib_tcredit = lib_tcredit;
        this.code_cat = code_cat;
        this.type_produit = type_produit;
    }

    public String getCode_tcredit() {
        return code_tcredit;
    }

    public void setCode_tcredit(String code_tcredit) {
        this.code_tcredit = code_tcredit;
    }

    public String getLib_tcredit() {
        return lib_tcredit;
    }

    public void setLib_tcredit(String lib_tcredit) {
        this.lib_tcredit = lib_tcredit;
    }

    public String getCode_cat() {
        return code_cat;
    }

    public void setCode_cat(String code_cat) {
        this.code_cat = code_cat;
    }

    public String getType_produit() {
        return type_produit;
    }

    public void setType_produit(String type_produit) {
        this.type_produit = type_produit;
    }
}
