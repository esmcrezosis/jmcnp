package com.esmc.mcnp.dao.repository.cm;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompte;

public interface EuAncienCompteRepository extends BaseRepository<EuAncienCompte, String> {

	List<EuAncienCompte> findByCodeMembre(String codeMembre);

	List<EuAncienCompte> findByCodeMembreAndCodeCat(String codeMembre, String codeCat);

	EuAncienCompte findByCodeMembreAndCodeCatAndEuTypeCompte_CodeTypeCompte(String codeMembre, String codeCat,
			String codeTypeCompte);
}
