package com.esmc.mcnp.repositories.obpsd;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuBonNeutreDetailRepository extends BaseRepository<EuBonNeutreDetail, Integer> {

	@Query("select d from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreCodeMembre like :membre")
	Page<EuBonNeutreDetail> findByCodeMembre(@Param("membre") String codeMembre, Pageable pageable);

	@Query("select d from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreNom like :nom")
	Page<EuBonNeutreDetail> findByNom(@Param("nom") String nom, Pageable pageable);

	@Query("select d from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreRaison like :raison")
	Page<EuBonNeutreDetail> findByRaisonSociale(@Param("raison") String raison, Pageable pageable);

	Page<EuBonNeutreDetail> findByBonNeutreDetailType(String type, Pageable pageable);

	@Query(value = "select d from EuBonNeutreDetail d left join fetch d.euBonNeutre", countQuery = "select count(d) from EuBonNeutreDetail d left join d.euBonNeutre")
	Page<EuBonNeutreDetail> getAll(Pageable pageable);

	@Query("select d from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreNom like :nom and b.bonNeutrePrenom like :prenom")
	Page<EuBonNeutreDetail> findByNomAndPrenom(@Param("nom") String nomMembre, @Param("prenom") String prenom, Pageable pageable);

	Page<EuBonNeutreDetail> findByEuBonNeutre_BonNeutreId(Integer id, Pageable pageable);

	Page<EuBonNeutreDetail> findByBonNeutreDetailDateBetween(Date deb, Date fin, Pageable pageable);

	Page<EuBonNeutreDetail> findByBonNeutreDetailDateGreaterThan(Date deb, Pageable pageable);

	Page<EuBonNeutreDetail> findByBonNeutreDetailDateLessThanEqual(Date deb, Pageable pageable);

	Page<EuBonNeutreDetail> findByEuBonNeutre_BonNeutreCode(String code, Pageable pageable);

	@Query("select d from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreCodeMembre like :membre")
	List<EuBonNeutreDetail> findByBonNeutreCodeMembre(@Param("membre") String codeMembre);

	@Query("select d from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreCodeMembre like :membre and d.bonNeutreDetailMontantSolde > 0 ")
	List<EuBonNeutreDetail> findByCodeMembre(@Param("membre") String codeMembre);

	@Query("select sum(d.bonNeutreDetailMontantSolde) from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreCodeMembre like :membre")
	Optional<Double> getSoldeByBonNeutreCodeMembre(@Param("membre") String codeMembre);

	@Query("select sum(d.bonNeutreDetailMontantSolde) from EuBonNeutreDetail d join d.euBonNeutre b where b.bonNeutreCodeMembre like :membre and d.bonNeutreDetailMontantSolde > 0")
	Optional<Double> getSoldeByCodeMembre(@Param("membre") String codeMembre);

	EuBonNeutreDetail findByBonNeutreDetailCode(String code);

	@Query("select bnd from EuBonNeutreDetail bnd where bnd.bonNeutreDetailCode like :code and bnd.bonNeutreDetailMontantSolde > 0")
	EuBonNeutreDetail findByCode(@Param("code") String code);

	@Query("select sum(bnd.bonNeutreDetailMontantSolde) from EuBonNeutreDetail bnd where bnd.euBonNeutre.bonNeutreCode like :code and bnd.bonNeutreDetailMontantSolde > 0")
	Optional<Double> getSoldeByBonNeutreCode(@Param("code") String code);

	@Query("select sum(bnd.bonNeutreDetailMontantSolde) from EuBonNeutreDetail bnd where bnd.euBonNeutre.bonNeutreId = :id and bnd.bonNeutreDetailMontantSolde > 0")
	Optional<Double> getSoldeByBonNeutreId(@Param("id") Integer id);

	@Query("select max(bnd.bonNeutreDetailId) from EuBonNeutreDetail bnd")
	int getLastInsertedId();
	
	@Query("select sum(d.bonNeutreDetailMontantSolde) from EuBonNeutreDetail d join d.euBonNeutre b where d.bonNeutreDetailDate >= :date and  b.bonNeutreCodeMembre like :membre and d.bonNeutreDetailMontantSolde > 0")
	Optional<Double> getSumByCodeMembreAndDate(@Param("membre") String codeMembre, @Param("date") Date date);
}
