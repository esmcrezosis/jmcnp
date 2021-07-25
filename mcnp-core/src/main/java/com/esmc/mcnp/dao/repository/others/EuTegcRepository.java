package com.esmc.mcnp.repositories.others;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuTegcRepository extends BaseRepository<EuTegc, String> {
	
	@Query("select t From EuTegc t left join fetch t.euMembreMorale m left join fetch t.euMembre u where t.codeTegc like :tegc")
	EuTegc findByCodeTegc(@Param("tegc") String codeTegc);

	@Query("select t From EuTegc t join fetch t.euMembreMorale m where m.codeMembreMorale like :membre")
	public List<EuTegc> findByCodeMembre(@Param("membre") String codeMembre);

	@Query("select t From EuTegc t join fetch t.euMembre m where m.codeMembre like :membre")
	public List<EuTegc> findByCodeMembrePhysique(@Param("membre") String codeMembre);

	public List<EuTegc> findByEuMembreMorale_CodeMembreMorale(String codeMembre);

	public List<EuTegc> findByEuMembre_CodeMembre(String codeMembre);
	
	public Page<EuTegc> findByEuMembreMorale_CodeMembreMorale(String codeMembre, Pageable pageable);

	@Query(value = "select t from EuTegc t where t.euMembre.codeMembre like :membre")
	public Page<EuTegc> findByEuMembre_CodeMembre(@Param("membre") String codeMembre, Pageable pageable);

	@Query("select sum(t.soldeTegc) From EuTegc t join t.euMembreMorale m where m.codeMembreMorale like :membre")
	public Double getSoldeByCodeMembre(@Param("membre") String codeMembre);

	@Query("select sum(t.soldeTegc) From EuTegc t join t.euMembre m where m.codeMembre like :membre")
	public Double getSoldeByCodeMembrePhysique(@Param("membre") String codeMembre);

	@Query("select t From EuTegc t join t.euMembreMorale m where m.codeMembreMorale like :membre and t.codeTegc like :te")
	public List<EuTegc> fetchByMembreAndTe(@Param("membre") String codeMembre, @Param("te") String codeTegc);

	@Query("select t From EuTegc t join t.euMembre m where m.codeMembre like :membre and t.codeTegc like :te")
	public List<EuTegc> fetchByMembrePysiqueAndTe(@Param("membre") String codeMembre, @Param("te") String codeTegc);

	@Query("select t.soldeTegc From EuTegc t join t.euMembreMorale m where m.codeMembreMorale like :membre and t.codeTegc like :te")
	public Double getSoldeByMembreandTe(@Param("membre") String codeMembre, @Param("te") String codeTegc);

	@Query("select t.soldeTegc From EuTegc t join t.euMembre m where m.codeMembre like :membre and t.codeTegc like :te")
	public Double getSoldeByMembrePhysiqueandTe(@Param("membre") String codeMembre, @Param("te") String codeTegc);

	@Query("select t From EuTegc t join t.euMembreMorale m where m.codeMembreMorale like :membre and t.nomTegc like :nomtegc")
	public EuTegc findTegcByCodeMembreAndNomTegc(@Param("membre") String codeMembre, @Param("nomtegc") String nomTegc);

	@Query("select t From EuTegc t join t.euMembre m where m.codeMembre like :membre and t.nomTegc like :nomtegc")
	public EuTegc findTegcByCodeMembrePhysiqueAndNomTegc(@Param("membre") String codeMembre,
			@Param("nomtegc") String nomTegc);
}
