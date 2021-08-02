package com.esmc.mcnp.infrastructure.services.cmfh;

import java.util.List;

import com.esmc.mcnp.domain.entity.acteur.EuAssociation;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAssociationService extends BaseService<EuAssociation, Long> {
	public List<EuAssociation> findByAssociationNom(String associationNom);

	public EuAssociation findByCodeMembre(String codeMembre);
}
