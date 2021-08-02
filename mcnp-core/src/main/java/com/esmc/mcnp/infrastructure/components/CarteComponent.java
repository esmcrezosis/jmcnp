package com.esmc.mcnp.infrastructure.components;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.cmfh.EuMembreFondateurMf107Repository;
import com.esmc.mcnp.domain.entity.acteur.EuCodeActivation;
import com.esmc.mcnp.domain.entity.acteur.EuSouscription;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.infrastructure.services.cmfh.EuCodeActivationService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuDepotVenteService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuMembreFondateur11000Service;
import com.esmc.mcnp.infrastructure.services.cmfh.EuSouscriptionService;

@Component
@Transactional(readOnly = true)
public class CarteComponent {

	private @Autowired EuCodeActivationService codeActivationService;
	private @Autowired EuSouscriptionService souscriptionService;
	private @Autowired EuDepotVenteService depotVenteService;
	private @Autowired EuMembreFondateur11000Service membreFondateur11000Service;
	private @Autowired EuMembreFondateurMf107Repository mf107Repository;

	public double calculKacm(EuMembre membre) {
		if (membre.getEtatMembre().equals("A")) {
			if (isMF(membre.getCodeMembre()) || isCmfh(membre.getCodeMembre())) {
				return 0;
			} else {
				return 22000;
			}
		} else {
			if (membre.getAutoEnroler().equals("O")) {
				if (isCmfh(membre.getCodeMembre()) || (getFS(membre.getCodeMembre()) == 70000)) {
					return 0;
				} else {
					return 22000;
				}
			} else {
				if (isCmfh(membre.getCodeMembre())) {
					return 0;
				} else {
					return 25000;
				}
			}
		}
	}

	public double calculKacm(EuMembreMorale morale) {
		if (isMF(morale.getCodeMembreMorale()) || isCmfh(morale.getCodeMembreMorale())) {
			return 0;
		} else {
			return 22000;
		}
	}

	public boolean isMF(String codeMembre) {
		if (membreFondateur11000Service.findByMembre(codeMembre) != null
				&& mf107Repository.findByCodeMembre(codeMembre) != null) {
			return true;
		}
		return false;
	}

	public boolean isCmfh(String codeMembre) {
		return (!depotVenteService.findByMembre(codeMembre).isEmpty());
	}

	public double getFS(String codeMembre) {
		EuCodeActivation codeActivation = codeActivationService.findByMembre(codeMembre);
		if (Objects.nonNull(codeActivation)) {
			if (codeActivation.getMontantSouscrit() != null) {
				return codeActivation.getMontantSouscrit();
			} else {
				EuSouscription souscription = souscriptionService.findById(codeActivation.getSouscriptionId());
				return souscription.getSouscriptionMontant();
			}
		}
		return 0;
	}
}
