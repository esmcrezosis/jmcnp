package com.esmc.mcnp.dao.repository.obpsd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EuTraiteRepository extends BaseRepository<EuTraite, Long> {

	@Query("select max(t.traiteId) From EuTraite t")
	Long getLastInsertedId();

	@Query("select max(t.traiter) From EuTraite t where t.traiteTegcp = :id")
	Integer getTraiterByTpagcp(@Param("id") Long id);

	List<EuTraite> findByTraiteDisponible(Integer dispo);

	EuTraite findByTraiteNumero(String numero);

	@Query("select t From EuTraite t where t.traiteTegcp = :id and t.traiteDisponible = :dispo and (t.traiteImprimer = 0 or t.traiteImprimer > 1)")
	List<EuTraite> findByTpagcpAndDispo(@Param("id") Long idTpagcp, @Param("dispo") Integer dispo);

	@Query("select t From EuTraite t where t.traiteTegcp = :id order by t.traiter")
	List<EuTraite> findByTpagcp(@Param("id") Long idTpagcp);

	@Query("select t From EuTraite t where t.traiteTegcp = :id order by t.traiter")
	Page<EuTraite> findByTpagcp(@Param("id") Long idTpagcp, Pageable pageable);

	@Query("select t from  EuTraite t where (t.traitePayer is null or t.traitePayer = 0) and  (t.traiteImprimer is null or t.traiteImprimer = 0) and t.traiteTegcp in (:tpas) order by t.traiteTegcp asc, t.traiteDateFin desc")
	List<EuTraite> findByTraiteTegcs(@Param("tpas") List<Long> tpas);

	@Query("select t from EuTraite t where t.traiteId in (:ids)")
	List<EuTraite> findTraiteByIds(@Param("ids") List<Long> ids);

	@Query("select t from EuTraite t where t.traiteId in (:ids) and (t.traitePayer = 0 or t.traitePayer is null) and  (t.traiteImprimer is null or t.traiteImprimer = 0)")
	List<EuTraite> findTraiteDispoByIds(@Param("ids") List<Long> ids);

	@Query("select t from  EuTraite t where (t.traitePayer is null or t.traitePayer = 0) and  (t.traiteImprimer is null or t.traiteImprimer = 0) and t.traiteTegcp in (:tpas) order by t.traiteTegcp asc, t.traiteDateFin desc")
	Page<EuTraite> findByTraiteTegcs(@Param("tpas") List<Long> tpas, Pageable pageable);

	@Query("select t from  EuTraite t where (t.traitePayer is null or t.traitePayer = 0) and  (t.traiteImprimer is null or t.traiteImprimer = 0) and t.traiteTegcp in (:tpas)and t.traiteDisponible = :dispo order by t.traiteTegcp asc, t.traiteDateFin desc")
	List<EuTraite> findByTraiteTegcs(@Param("tpas") List<Long> tpas, @Param("dispo") int disponible);

	@Query("select t from  EuTraite t where (t.traitePayer is null or t.traitePayer = 0) and  (t.traiteImprimer is null or t.traiteImprimer = 0) and t.traiteTegcp in (:tpas)and t.traiteDisponible = :dispo order by t.traiteTegcp asc, t.traiteDateFin desc")
	Page<EuTraite> findByTraiteTegcs(@Param("tpas") List<Long> tpas, @Param("dispo") int disponible, Pageable pageable);

	@Query("select t from EuTraite t where t.traiteImprimer = 0 and t.traiteNumero is null and  t.traiteDateFin > :date ")
	List<EuTraite> findAllByTraiteNonEchu(@Param("date") Date datefin);

	@Query("select t from EuTraite t where t.traiteImprimer = 0 and t.traiteNumero is null and  t.traiteDateFin between :datein and :dateout ")
	List<EuTraite> findAllByTraiteNonEchu(@Param("datein") Date datein, @Param("dateout") Date dateout);

	@Query("select t from EuTraite t where t.traiteDateFin between :datein and :dateout ")
	List<EuTraite> findAllTraiteByDate(@Param("datein") Date datein, @Param("dateout") Date dateout);

	@Query("select t from EuTraite t where t.traiteImprimer = 0 and t.traiteDisponible=0 and t.traiteNumero is null and  t.traiteDateFin <= :date ")
	List<EuTraite> findAllByDateFin(@Param("date") Date datefin);

	@Query("select t from EuTraite t where t.traiteTegcp in (:tpas) and t.traiteDateFin between :datein and :dateout")
	List<EuTraite> findAllByDateAndTpa(@Param("tpas") List<Long> tpas, @Param("datein") Date datein,
			@Param("dateout") Date datefin);

	@Query("select t from EuTraite t where t.traiteTegcp = :tpa and t.traiteDateFin between :datein and :dateout order by t.traiter")
	List<EuTraite> findAllTraiteByTpaAndDate(@Param("tpa") Long tpa, @Param("datein") Date dateDeb,
			@Param("dateout") Date datefin);

	@Query("select t from EuTraite t where t.traiteTegcp in (:tpas) and t.traiteDateFin >= :date and (t.traitePayer = 0 or t.traitePayer is null) order by t.traiter")
	List<EuTraite> findAllTraiteByDateFinAndTpa(@Param("tpas") List<Long> tpas, @Param("date") Date datefin);

	@Query("select t from EuTraite t where t.traiteTegcp = :tpa and t.traiteDateFin >= :date and (t.traitePayer = 0 or t.traitePayer is null) order by t.traiter")
	List<EuTraite> findAllTraiteByTpaAndDateFin(@Param("tpa") Long tpa, @Param("date") Date datefin);

	@Query("select count (t) from EuTraite t where t.traiteDisponible = 2 and  (t.traiteImprimer is null or t.traiteImprimer = 0) and (t.traitePayer = 0 or t.traitePayer is null ) and t.traiteTegcp = :tpagcp")
	Optional<Long> getNbreTraiteDisponibleByTpa(@Param("tpagcp") Long idTpagcp);

	@Query("select sum (t.traiteMontant) from EuTraite t where t.traiteDisponible = 2 and (t.traitePayer = 0 or t.traitePayer is null ) and t.traiteTegcp = :tpagcp")
	Optional<Double> getSumTraiteDisponibleByTpa(@Param("tpagcp") Long idTpagcp);

	@Query("select t from EuTraite t where t.traiteDisponible = 2 and  (t.traiteImprimer is null or t.traiteImprimer = 0) and (t.traitePayer = 0 or t.traitePayer is null ) and t.traiteTegcp = :tpagcp")
	List<EuTraite> findTraiteDisponibleByTpa(@Param("tpagcp") Long idTpagcp);

	@Query("select t from EuTraite t where t.traiteDisponible = 1 and  (t.traiteImprimer is null or t.traiteImprimer = 0) and (t.traitePayer = 0 or t.traitePayer is null )")
	List<EuTraite> findTraiteDisponible();

	@Query("select t from EuTraite t where t.traiteDisponible = 1 and (t.traitePayer = 0 or t.traitePayer is null ) and  (t.traiteImprimer is null or t.traiteImprimer = 0) and t.traiteTegcp = :idTpagcp")
	List<EuTraite> findTraiteDisponible(@Param("idTpagcp") Long idTpagcp);

	List<EuTraite> findByTraitePayer(Integer payer);

	Page<EuTraite> getByTraitePayer(Integer payer, Pageable pageable);
	
	Page<EuTraite> findByTraiteTegcp(Long traiteTegcp, Pageable pageable);

	Page<EuTraite> getByTraitePayerAndTraiteDateFinLessThan(Integer payer, Date date, Pageable pageable);

	@Query("select t from EuTraite t where t.traitePayer = 0 or t.traitePayer is null")
	List<EuTraite> findAllTraiteNonPayer();

	@Query("select t from EuTraite t where (t.traitePayer = 0 or t.traitePayer is null) and t.traiteTegcp = :idTpagcp")
	List<EuTraite> findAllTraiteNonPayer(@Param("idTpagcp") Long idTpagcp);

	Page<EuTraite> findByTpagcp_CodeMembre(String codeMembre, Pageable pageable);

	Page<EuTraite> findByTpagcp_CodeMembreAndTraitePayer(String codeMembre, Integer payer, Pageable pageable);

	Page<EuTraite> findByTraiteDateDebutBetween(Date  deb, Date fin, Pageable pageable);

	Page<EuTraite> findByTraitePayerAndTraiteDateDebutBetween(Integer payer, Date  deb, Date fin, Pageable pageable);

	@Query("select t from EuTraite t where t.traitePayer = 1")
	Page<EuTraite> findTraitePayer(Pageable pageable);

	@Query("select t from EuTraite t where t.tpagcp.codeMembre like :code and t.traiteDateFin <= :date and t.traitePayer = 0 or t.traitePayer is null")
	Page<EuTraite> findTraiteEchuNonPayeByMembre(@Param("code") String codeMembre, @Param("date") Date date, Pageable pageable);

	@Query("select t from EuTraite t where t.traiteDateDebut between :deb and :fin and t.traiteDateFin <= :date and t.traitePayer = 0 or t.traitePayer is null")
	Page<EuTraite> findTraiteEchuNonPayeByDate(@Param("deb") Date deb, @Param("fin") Date fin, @Param("date") Date date,  Pageable pageable);

	@Query("select t from EuTraite t where t.traitePayer = 0 or t.traitePayer is null")
	Page<EuTraite> findTraiteNonPayer(Pageable pageable);

	Page<EuTraite> findByTraiteDateFinBetween(Date  deb, Date fin, Pageable pageable);

	Page<EuTraite> findByTraitePayerAndTraiteDateFinBetween(Integer payer, Date  deb, Date fin, Pageable pageable);

	Page<EuTraite> findByTraiteDateFinGreaterThan(Date datefin, Pageable pageable);

	Page<EuTraite> findByTraiteDateFinLessThanEqual(Date datefin, Pageable pageable);

	@Query("select t from EuTraite t where t.tpagcp.codeMembre like :code and  t.traiteDateFin > :date ")
	Page<EuTraite> findTraiteNonEchuByMembre(@Param("code") String codeMembre, @Param("date") Date datefin, Pageable pageable);

	@Query("select t from EuTraite t where t.tpagcp.codeMembre like :code and  t.traiteDateFin <= :date ")
	Page<EuTraite> findTraiteEchuByMembre(@Param("code") String codeMembre, @Param("date") Date datefin, Pageable pageable);

	@Query("select t from EuTraite t where t.tpagcp.codeMembre like :code and  t.traitePayer = :payer ")
	Page<EuTraite> findTraitePayerByMembre(@Param("code") String codeMembre, @Param("payer") Integer payer, Pageable pageable);

	@Query("select t from EuTraite t where t.traiteDateDebut between :deb and :fin and  t.traiteDateFin > :date ")
	Page<EuTraite> findTraiteNonEchuByDate(@Param("deb") Date deb, @Param("fin") Date fin, @Param("date") Date datefin, Pageable pageable);

	@Query("select t from EuTraite t where t.traiteDateDebut between :deb and :fin and  t.traiteDateFin <= :date ")
	Page<EuTraite> findTraiteEchuByDate(@Param("deb") Date deb, @Param("fin") Date fin, @Param("date") Date datefin, Pageable pageable);
}
