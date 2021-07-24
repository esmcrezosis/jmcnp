/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.obps;

import java.io.Serializable;
//import java.util.Date;


/**
 *
 * @author Administrateur
 */
public class ArticleStockeCategorie implements Serializable{
        private static final long serialVersionUID = 1L;
        private Long idCategorie;
	    private String nomCategorie;
	   
	

    public ArticleStockeCategorie() {
    }



	public Long getIdCategorie() {
		return idCategorie;
	}



	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}



	public String getNomCategorie() {
		return nomCategorie;
	}



	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

   
   
}