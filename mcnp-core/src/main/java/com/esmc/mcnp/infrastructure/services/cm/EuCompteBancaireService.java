package com.esmc.mcnp.services.cm;

import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.model.cm.EuCompteBancaire;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCompteBancaireService extends BaseService<EuCompteBancaire, Long> {
	List<EuCompteBancaire> findListCompteBancaire(String codeMembre);

	Optional<EuCompteBancaire> findByMembreAndBanque(String codeMembre, String codeBanque);

	Optional<EuCompteBancaire> findComptePrincipal(String codeMembre, int principal);

	EuCompteBancaire getCompteBancaire(String codeMembre, String codeBanque);
}
