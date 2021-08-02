/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.desendettement;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;
    private String num_appel;
    private String type_nn;
    private String code_compte_nn;
    private Double mont_nn;

    public Collect() {
    }

    public Collect(String num_appel, String type_nn, String code_compte_nn, Double mont_nn) {
        this.num_appel = num_appel;
        this.type_nn = type_nn;
        this.code_compte_nn = code_compte_nn;
        this.mont_nn = mont_nn;
    }

    public String getNum_appel() {
        return num_appel;
    }

    public void setNum_appel(String num_appel) {
        this.num_appel = num_appel;
    }

    public String getType_nn() {
        return type_nn;
    }

    public void setType_nn(String type_nn) {
        this.type_nn = type_nn;
    }

    public String getCode_compte_nn() {
        return code_compte_nn;
    }

    public void setCode_compte_nn(String code_compte_nn) {
        this.code_compte_nn = code_compte_nn;
    }

    public Double getMont_nn() {
        return mont_nn;
    }

    public void setMont_nn(Double mont_nn) {
        this.mont_nn = mont_nn;
    }

}
