package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_access_kids database table.
 *
 */
@Entity
@Table(name="eu_access_kids")
@NamedQuery(name="EuAccessKids.findAll", query="SELECT e FROM EuAccessKids e")
public class EuAccessKids implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long neng;
	private String datetime;
	private Integer disabled;
	private Integer iddatetime;
	private String serialnumber;
	private String terminalsn;
	private String terminaltype;

	public EuAccessKids() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public Long getNeng() {
		return this.neng;
	}

	public void setNeng(Long neng) {
		this.neng = neng;
	}


	@Column(length=100)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public Integer getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}


	public Integer getIddatetime() {
		return this.iddatetime;
	}

	public void setIddatetime(Integer iddatetime) {
		this.iddatetime = iddatetime;
	}


	@Column(length=200)
	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}


	@Column(length=200)
	public String getTerminalsn() {
		return this.terminalsn;
	}

	public void setTerminalsn(String terminalsn) {
		this.terminalsn = terminalsn;
	}


	@Column(nullable=false, length=40)
	public String getTerminaltype() {
		return this.terminaltype;
	}

	public void setTerminaltype(String terminaltype) {
		this.terminaltype = terminaltype;
	}

}