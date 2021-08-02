package com.esmc.mcnp.dao.repository.cmfh;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuAssociation;

public interface EuAssociationRepository extends BaseRepository<EuAssociation, Long> {
	public List<EuAssociation> findByAssociationNomLike(String associationNom);

	public EuAssociation findByCodeMembre(String codeMembre);
}
