package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuRepresentation;
import com.esmc.mcnp.model.cm.EuRepresentationPK;
import com.esmc.mcnp.services.base.BaseService;

public interface EuRepresentationService extends BaseService<EuRepresentation, EuRepresentationPK> {

	EuRepresentation getRepresentantPrincipal(String codeMembreMorale);

	EuRepresentation findByMembreAndMorale(String morale, String membre);
	
	EuRepresentation findByMoraleAndEtat(String morale, String etat);
}
