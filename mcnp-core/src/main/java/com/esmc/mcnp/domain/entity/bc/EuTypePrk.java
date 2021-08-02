package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuTypePrk
 *
 */
@Entity
@Table(name="eu_type_prk")
public class EuTypePrk implements Serializable {

	private int idTypePrk;
	private double valeurPrk;
	private static final long serialVersionUID = 1L;

	public EuTypePrk() {
		super();
	}
	
	@Id
	@Column(name = "id_type_prk")
	public int getIdTypePrk() {
		return this.idTypePrk;
	}

	public void setIdTypePrk(int idTypePrk) {
		this.idTypePrk = idTypePrk;
	}   
	
	@Column(name = "valeur_prk")
	public double getValeurPrk() {
		return this.valeurPrk;
	}

	public void setValeurPrk(double valeurPrk) {
		this.valeurPrk = valeurPrk;
	}
   
}
