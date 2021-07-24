package com.esmc.mcnp.web.mvc.dto;

import java.io.Serializable;

public class Empreinte implements Serializable {

private static final long serialVersionUID =1L;
private String codeMembre;
private String stringTemplate;



public Empreinte() {
	
}

public Empreinte(String codeMembre, String stringTemplate) {
	super();
	this.codeMembre = codeMembre;
	this.stringTemplate = stringTemplate;
}

public String getCodeMembre() {
	return codeMembre;
}
public void setCodeMembre(String codeMembre) {
	this.codeMembre = codeMembre;
}
public String getStringTemplate() {
	return stringTemplate;
}
public void setStringTemplate(String stringTemplate) {
	this.stringTemplate = stringTemplate;
}




}
