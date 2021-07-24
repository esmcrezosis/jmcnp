package com.esmc.mcnp.services.cmfh;

import java.util.List;

import com.esmc.mcnp.model.acteur.EuAssociation;
import com.esmc.mcnp.services.base.BaseService;

public interface EuAssociationService extends BaseService<EuAssociation, Long> {
	public List<EuAssociation> findByAssociationNom(String associationNom);

	public EuAssociation findByCodeMembre(String codeMembre);
}
