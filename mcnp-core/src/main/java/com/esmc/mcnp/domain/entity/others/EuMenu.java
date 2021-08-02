package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_menu database table.
 *
 */
@Entity
@Table(name="eu_menu")
@NamedQuery(name="EuMenu.findAll", query="SELECT e FROM EuMenu e")
public class EuMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	private double menuId;
	private String menuLibelle;
	private String menuType;
	private double ordre;
	private double publier;

	public EuMenu() {
	}

    @Id
	@Column(name="menu_id", nullable=false)
	public double getMenuId() {
		return this.menuId;
	}

	public void setMenuId(double menuId) {
		this.menuId = menuId;
	}


	@Column(name="menu_libelle", length=255)
	public String getMenuLibelle() {
		return this.menuLibelle;
	}

	public void setMenuLibelle(String menuLibelle) {
		this.menuLibelle = menuLibelle;
	}


	@Column(name="menu_type", length=255)
	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
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