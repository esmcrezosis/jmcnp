package com.esmc.mcnp.dao.repository.obps;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuGcp;

@Repository
public interface EuGcpRepository extends BaseRepository<EuGcp, Long> {

	@Query("select max(e.idGcp) from EuGcp e")
	Long getLastEuGcpInsertedId();

	@Query("select g from EuGcp g join fetch g.euCompteCredit where g.reste > 0 and g.codeMembre like :membre")
	List<EuGcp> findGcpByCodeMembre(@Param("membre") String codeMembre);

	@Query(value = "select g from EuGcp g join fetch g.euCompteCredit where g.codeMembre like :membre")
	List<EuGcp> findGcpByMembre(@Param("membre") String codeMembre);

	@Query(value = "select g from EuGcp g left join fetch g.euMontantReduit where g.codeMembre like :membre", 
			countQuery = "select count(g) from EuGcp g left join g.euCompteCredit where g.codeMembre like :membre")
	Page<EuGcp> findByMembre(@Param("membre") String codeMembre, Pageable pageable);

	@Query(value = "select g from EuGcp g left join fetch g.euMontantReduit where g.codeMembre like :membre and g.typeGcp like :type",
			countQuery = "select count(g) from EuGcp g left join g.euMontantReduit where g.codeMembre like :membre and g.typeGcp like :type")
	Page<EuGcp> findByMembreAndTypeGcp(@Param("membre") String codeMembre, @Param("type") String typeGcp,
			Pageable pageable);

	@Query(value = "select g from EuGcp g left join fetch g.euMontantReduit where g.codeMembre like :membre and g.dateConso between :deb and :fin",
			countQuery = "select count(g) from EuGcp g left join g.euMontantReduit where g.codeMembre like :membre and g.dateConso between :deb and :fin")
	Page<EuGcp> findByMembreAndDateConso(@Param("membre") String codeMembre, @Param("deb") Date deb,
			@Param("fin") Date fin, Pageable pageable);

	@Query(value = "select g from EuGcp g left join fetch g.euMontantReduit where g.dateConso between :deb and :fin", 
			countQuery = "select count(g) from EuGcp g left join g.euMontantReduit where g.dateConso between :deb and :fin")
	Page<EuGcp> findByDateConso(@Param("deb") Date deb, @Param("fin") Date fin, Pageable pageable);

	@EntityGraph(attributePaths = {"euMontantReduit"})
	Page<EuGcp> findByDateConsoGreaterThan(Date dateConso, Pageable pageable);

	@EntityGraph(attributePaths = {"euMontantReduit"})
	Page<EuGcp> findByDateConsoLessThanEqual(Date dateConso, Pageable pageable);

	@EntityGraph(attributePaths = {"euMontantReduit"})
	Page<EuGcp> findByCodeMembreAndDateConsoGreaterThan(String codeMembre, Date dateConso, Pageable pageable);

	@EntityGraph(attributePaths = {"euMontantReduit"})
	Page<EuGcp> findByCodeMembreAndDateConsoLessThanEqual(String codeMembre, Date dateConso, Pageable pageable);

	List<EuGcp> findByEuBon_BonNumero(String numero);

	@Query("select sum(g.reste) From EuGcp g where g.codeMembre like :membre")
	Optional<Double> getSoldeByMembre(@Param("membre") String codeMembre);

	@Query("select g From EuGcp g where g.reste > 0 and g.codeMembre like :membre and g.typeGcp like :type")
	List<EuGcp> findGcpByMembreAndType(@Param("membre") String codeMembre, @Param("type") String typeGcp);

	@Query("select sum(g.reste) From EuGcp g where g.reste > 0 and g.codeMembre like :membre and g.typeGcp like :type")
	Optional<Double> getSoldeByMembreAndType(@Param("membre") String codeMembre, @Param("type") String typeGcp);

	@Query("select g from EuGcp g join g.euBon b where g.reste > 0 and b.bonNumero like :bon")
	List<EuGcp> findByNumeroBon(@Param("bon") String numeroBon);

	List<EuGcp> findByEuTegc_codeTegc(String codetegc);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :te")
	List<EuGcp> findByTe(@Param("te") String codeTegc);

	@Query("select g From EuGcp g join g.euTegc t  where (g.reste > 0) and (g.typeGcp = 'SMCIPN' or g.typeGcp is null) and (t.codeTegc like :te)")
	List<EuGcp> findByTeWithoutCr(@Param("te") String codeTegc);

	@Query("select sum(g.reste) From EuGcp g join g.euTegc t  where (g.reste > 0) and (g.typeGcp = 'SMCIPN' or g.typeGcp is null) and (t.codeTegc like :te)")
	Optional<Double> getSoldeByTeWithoutCr(@Param("te") String codeTegc);

	@Query("select g From EuGcp g where (g.reste > 0) and (g.typeGcp = 'SMCIPN' or g.typeGcp is null) and (g.codeMembre like :membre)")
	List<EuGcp> findByMembreWithoutCr(@Param("membre") String codeMembre);

	@Query("select sum(g.reste) From EuGcp g where (g.reste > 0) and (g.typeGcp = 'SMCIPN' or g.typeGcp is null) and (g.codeMembre like :membre)")
	Optional<Double> getSoldeByMembreWithoutCr(@Param("membre") String codeMembre);

	@Query("select g From EuGcp g join fetch g.euMontantReduit m join g.euTegc t  where (g.reste > 0) and (g.typeGcp = 'CR') and (t.codeTegc like :te)")
	List<EuGcp> findByTeWithCr(@Param("te") String codeTegc);

	@Query("select sum(g.reste) From EuGcp g join g.euTegc t  where (g.reste > 0) and (g.typeGcp = 'CR') and (t.codeTegc like :te)")
	Optional<Double> getSoldeByTeWithCr(@Param("te") String codeTegc);

	@Query("select g From EuGcp g join fetch g.euMontantReduit m  where (g.reste > 0) and (g.typeGcp = 'CR') and (g.codeMembre like :membre)")
	List<EuGcp> findByMembreWithCr(@Param("membre") String codeMembre);

	@Query("select sum(g.reste) From EuGcp g where (g.reste > 0) and (g.typeGcp = 'CR') and (g.codeMembre like :membre)")
	Optional<Double> getSoldeByMembreWithCr(@Param("membre") String codeMembre);

	@Query("select sum(g.reste) From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc")
	Optional<Double> getSoldeByCodeTegc(@Param("tegc") String codetegc);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc")
	List<EuGcp> findByCodeTegc(@Param("tegc") String codetegc);

	@Query("select sum(g.reste) From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc")
	Optional<Double> getSoldeByTegc(@Param("tegc") String codetegc);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc")
	List<EuGcp> findByTegc(@Param("tegc") String codetegc);

	@Query("select sum(g.reste) From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc and g.typeGcp like :type")
	Optional<Double> getSoldeByTeAndTypeGcp(@Param("tegc") String codeTegc, @Param("type") String typeGcp);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc and g.typeGcp like :type")
	List<EuGcp> findByTeAndTypeGcp(@Param("tegc") String codeTegc, @Param("type") String typeGcp);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc and g.typeGcp like :type")
	Page<EuGcp> findByTeAndTypeGcp(@Param("tegc") String codeTegc, @Param("type") String typeGcp, Pageable pageable);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and g.codeMembre like :membre and t.codeTegc like :tegc and g.typeGcp like :type")
	List<EuGcp> findByMembreAndTeAndTypeGcp(@Param("membre") String codeMembre, @Param("tegc") String codeTegc,
			@Param("type") String typeGcp);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and g.codeMembre like :membre and t.codeTegc like :tegc and g.typeGcp like :type")
	Page<EuGcp> findByMembreAndTeAndTypeGcp(@Param("membre") String codeMembre, @Param("tegc") String codeTegc,
			@Param("type") String typeGcp, Pageable pageable);

	@Query("select g From EuGcp g join g.euTegc t  where g.reste > 0 and t.codeTegc like :tegc and g.typeGcp is null")
	List<EuGcp> findByTegcAndTypeGcpNull(@Param("tegc") String codetegc);

	@Query("select g From EuGcp g where g.reste > 0 and g.codeMembre like :membre and g.typeGcp is null")
	List<EuGcp> findByMembreAndTypeGcpNull(@Param("membre") String codeMembre);
}
