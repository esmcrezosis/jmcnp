package com.esmc.mcnp.dao.repository.acteurs;

import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuLiasonCompte;

@Repository
public interface EuLiasonCompteRepository extends BaseRepository<EuLiasonCompte, Integer> {
	EuLiasonCompte findByCodeCompteAdmin(String codeCompteAdmin);
	EuLiasonCompte findByCodeCompteAnim(String codeCompteAnim);
}
