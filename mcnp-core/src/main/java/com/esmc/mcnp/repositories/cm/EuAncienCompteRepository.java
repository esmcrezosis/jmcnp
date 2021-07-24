package com.esmc.mcnp.repositories.cm;

import java.util.List;

import com.esmc.mcnp.model.cm.EuAncienCompte;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuAncienCompteRepository extends BaseRepository<EuAncienCompte, String> {

	List<EuAncienCompte> findByCodeMembre(String codeMembre);

	List<EuAncienCompte> findByCodeMembreAndCodeCat(String codeMembre, String codeCat);

	EuAncienCompte findByCodeMembreAndCodeCatAndEuTypeCompte_CodeTypeCompte(String codeMembre, String codeCat,
			String codeTypeCompte);
}
