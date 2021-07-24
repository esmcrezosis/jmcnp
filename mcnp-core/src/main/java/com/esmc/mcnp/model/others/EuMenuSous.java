package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_menu_sous database table.
 * 
 */
@Entity
@Table(name="eu_menu_sous")
@NamedQuery(name="EuMenuSous.findAll", query="SELECT e FROM EuMenuSous e")
public class EuMenuSous implements Serializable {
	private static final long serialVersionUID = 1L;
	private double menuSousId;
	private String menuSousLibelle;
	private double menuSousMenu;
	private String menuSousUrl;
	private double ordre;
	private double publier;

	public EuMenuSous() {
	}


	@Id
	@Column(name="menu_sous_id", unique=true, nullable=false)
	public double getMenuSousId() {
		return this.menuSousId;
	}

	public void setMenuSousId(double menuSousId) {
		this.menuSousId = menuSousId;
	}


	@Column(name="menu_sous_libelle", length=255)
	public String getMenuSousLibelle() {
		return this.menuSousLibelle;
	}

	public void setMenuSousLibelle(String menuSousLibelle) {
		this.menuSousLibelle = menuSousLibelle;
	}


	@Column(name="menu_sous_menu")
	public double getMenuSousMenu() {
		return this.menuSousMenu;
	}

	public void setMenuSousMenu(double menuSousMenu) {
		this.menuSousMenu = menuSousMenu;
	}


	@Column(name="menu_sous_url", length=255)
	public String getMenuSousUrl() {
		return this.menuSousUrl;
	}

	public void setMenuSousUrl(String menuSousUrl) {
		this.menuSousUrl = menuSousUrl;
	}


	public double getOrdre() {
		return this.ordre;
	}

	public void setOrdre(double ordre) {
		this.ordre = ordre;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}