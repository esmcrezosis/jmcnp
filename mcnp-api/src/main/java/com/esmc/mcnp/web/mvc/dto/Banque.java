package com.esmc.mcnp.web.mvc.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.domain.entity.obpsd.EuBanque;

public class Banque {
	public String codeBanque;
	public String libelleBanque;
	public String compteBanque;

	public Banque(String codeBanque, String libelleBanque, String compteBanque) {
		this.codeBanque = codeBanque;
		this.libelleBanque = libelleBanque;
		this.compteBanque = compteBanque;
	}

	public static List<Banque> toListBanque(List<EuBanque> banques) {
		List<Banque> banks = new ArrayList<>();
		if (!banques.isEmpty()) {
			banques.forEach(b -> banks.add(toBanque(b)));
		}
		return banks;
	}

	public static Banque toBanque(EuBanque banque) {
		if (Objects.nonNull(banque)) {
			return new Banque(banque.getCodeBanque(), banque.getLibelleBanque(), banque.getCompteBanque());
		}
		return null;
	}

	public String getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	public String getLibelleBanque() {
		return libelleBanque;
	}

	public void setLibelleBanque(String libelleBanque) {
		this.libelleBanque = libelleBanque;
	}

	public String getCompteBanque() {
		return compteBanque;
	}

	public void setCompteBanque(String compteBanque) {
		this.compteBanque = compteBanque;
	}
}
