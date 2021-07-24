package com.esmc.mcnp.repositories.acteurs;

import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.acteur.EuLiasonCompte;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuLiasonCompteRepository extends BaseRepository<EuLiasonCompte, Integer> {
	EuLiasonCompte findByCodeCompteAdmin(String codeCompteAdmin);
	EuLiasonCompte findByCodeCompteAnim(String codeCompteAnim);
}
