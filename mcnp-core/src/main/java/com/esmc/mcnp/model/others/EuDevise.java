package com.esmc.mcnp.model.others;

import com.esmc.mcnp.model.org.EuZone;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_devise database table.
 * 
 */
@Entity
@Table(name="eu_devise")
@NamedQuery(name="EuDevise.findAll", query="SELECT e FROM EuDevise e")
public class EuDevise implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeDev;
	private String libDev;
	private String symboleDev;
	private List<EuCours> euCours1;
	private List<EuCours> euCours2;
	private List<EuZone> euZones;

	public EuDevise() {
	}


	@Id
	@Column(name="code_dev", unique=true, nullable=false, length=80)
	public String getCodeDev() {
		return this.codeDev;
	}

	public void setCodeDev(String codeDev) {
		this.codeDev = codeDev;
	}


	@Column(name="lib_dev", length=100)
	public String getLibDev() {
		return this.libDev;
	}

	public void setLibDev(String libDev) {
		this.libDev = libDev;
	}


	@Column(name="symbole_dev", length=20)
	public String getSymboleDev() {
		return this.symboleDev;
	}

	public void setSymboleDev(String symboleDev) {
		this.symboleDev = symboleDev;
	}


	//bi-directional many-to-one association to EuCours
	@OneToMany(mappedBy="euDevise1")
	public List<EuCours> getEuCours1() {
		return this.euCours1;
	}

	public void setEuCours1(List<EuCours> euCours1) {
		this.euCours1 = euCours1;
	}

	public EuCours addEuCours1(EuCours euCours1) {
		getEuCours1().add(euCours1);
		euCours1.setEuDevise1(this);

		return euCours1;
	}

	public EuCours removeEuCours1(EuCours euCours1) {
		getEuCours1().remove(euCours1);
		euCours1.setEuDevise1(null);

		return euCours1;
	}


	//bi-directional many-to-one association to EuCours
	@OneToMany(mappedBy="euDevise2")
	public List<EuCours> getEuCours2() {
		return this.euCours2;
	}

	public void setEuCours2(List<EuCours> euCours2) {
		this.euCours2 = euCours2;
	}

	public EuCours addEuCours2(EuCours euCours2) {
		getEuCours2().add(euCours2);
		euCours2.setEuDevise2(this);

		return euCours2;
	}

	public EuCours removeEuCours2(EuCours euCours2) {
		getEuCours2().remove(euCours2);
		euCours2.setEuDevise2(null);

		return euCours2;
	}


	//bi-directional many-to-one association to EuZone
	@OneToMany(mappedBy="euDevise")
	public List<EuZone> getEuZones() {
		return this.euZones;
	}

	public void setEuZones(List<EuZone> euZones) {
		this.euZones = euZones;
	}

	public EuZone addEuZone(EuZone euZone) {
		getEuZones().add(euZone);
		euZone.setEuDevise(this);

		return euZone;
	}

	public EuZone removeEuZone(EuZone euZone) {
		getEuZones().remove(euZone);
		euZone.setEuDevise(null);

		return euZone;
	}

}