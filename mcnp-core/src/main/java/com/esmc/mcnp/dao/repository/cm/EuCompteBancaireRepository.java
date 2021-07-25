package com.esmc.mcnp.repositories.cm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.cm.EuCompteBancaire;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuCompteBancaireRepository extends BaseRepository<EuCompteBancaire, Long> {

	@Query("select c from EuCompteBancaire c where c.codeMembre like :code")
	public List<EuCompteBancaire> findByCodeMembre(@Param("code") String codeMembre);
	
	@Query("select c from EuCompteBancaire c where c.codeMembreMorale like :code")
	public List<EuCompteBancaire> findByCodeMembreMorale(@Param("code") String codeMembreMorale);
	
	@Query("select c from EuCompteBancaire c where c.codeMembre like :membre and c.codeBanque like :bank")
	public EuCompteBancaire findByCodeMembreAndCodeBanque(@Param("membre") String codeMembre, @Param("bank") String codeBanque);
	
	@Query("select c from EuCompteBancaire c where c.codeMembreMorale like :membre and c.codeBanque like :bank")
	public EuCompteBancaire findByCodeMembreMoraleAndCodeBanque(@Param("membre") String codeMembre, @Param("bank") String codeBanque);

	EuCompteBancaire findByCodeMembreAndPrincipal(String codeMembre, int principal);

	EuCompteBancaire findByCodeMembreMoraleAndPrincipal(String codeMembre, int principal);
}
