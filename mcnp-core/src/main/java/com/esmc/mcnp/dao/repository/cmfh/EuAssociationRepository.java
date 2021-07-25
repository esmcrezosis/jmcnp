package com.esmc.mcnp.repositories.cmfh;

import java.util.List;

import com.esmc.mcnp.model.acteur.EuAssociation;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuAssociationRepository extends BaseRepository<EuAssociation, Long> {
	public List<EuAssociation> findByAssociationNomLike(String associationNom);

	public EuAssociation findByCodeMembre(String codeMembre);
}
