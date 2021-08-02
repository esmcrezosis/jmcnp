package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.domain.entity.cm.EuRepresentation;
import com.esmc.mcnp.domain.entity.cm.EuRepresentationPK;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuRepresentationService extends BaseService<EuRepresentation, EuRepresentationPK> {

	EuRepresentation getRepresentantPrincipal(String codeMembreMorale);

	EuRepresentation findByMembreAndMorale(String morale, String membre);
	
	EuRepresentation findByMoraleAndEtat(String morale, String etat);
}
