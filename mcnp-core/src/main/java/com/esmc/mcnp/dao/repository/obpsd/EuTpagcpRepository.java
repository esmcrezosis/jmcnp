package com.esmc.mcnp.dao.repository.obpsd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EuTpagcpRepository extends BaseRepository<EuTpagcp, Long> {

    @Query("select max(t.idTpagcp) From EuTpagcp t")
    Long getLastInsertedId();

    List<EuTpagcp> findByEuCompte_CodeCompte(String codeCompte);

    Page<EuTpagcp> findByEuCompte_CodeCompte(String codeCompte, Pageable pageable);

    @Query("select t from EuTpagcp t where t.codeMembre like :codeMembre and t.resteNtf > 0")
    List<EuTpagcp> findByCodeMembre(@Param("codeMembre") String codeMembre);

    @Query("select t from EuTpagcp t where t.codeMembre like :codeMembre and t.resteNtf > 0")
    Page<EuTpagcp> findByCodeMembre(@Param("codeMembre") String codeMembre, Pageable pageable);

    @Query("select t from EuTpagcp t where t.codeMembre like :codeMembre and t.ntf >= :ntf")
    List<EuTpagcp> findByCodeMembreAndNtf(@Param("codeMembre") String codeMembre, @Param("ntf") Integer ntf);

    @Query("select tg from EuTpagcp tg join tg.euCompte c where tg.solde > 0 and c.codeCompte like :compte")
    List<EuTpagcp> findByCodeCompte(@Param("compte") String codeCompte);

    @Query("select t from EuTpagcp t where t.solde > 0 and t.resteNtf > 0")
    List<EuTpagcp> getAll();

    @Query("select t from EuTpagcp t where t.solde > 0 and t.resteNtf > 0")
    Page<EuTpagcp> getAll(Pageable pageable);

    @Query("select  t from EuTpagcp t left join fetch t.membre left join fetch t.membreMorale where t.idTpagcp = :id")
    EuTpagcp getById(@Param("id") Long id);

    @Query("select sum(tg.solde) from EuTpagcp tg join tg.euCompte c where tg.solde > 0 and c.codeCompte like :codeCompte")
    double getSumSoldeTpagcpbyCompte(@Param("codeCompte") String codeCompte);

    @Query("select t From EuTpagcp t where t.idTpagcp in (:ids)")
    List<EuTpagcp> findbyIds(@Param("ids") List<Long> ids);

    @Query("select sum(t.solde) From EuTpagcp t where t.idTpagcp in (:ids)")
    Double getSumByIds(@Param("ids") List<Long> ids);

    @Query("select t.idTpagcp from EuTpagcp t where t.codeMembre like :membre")
    List<Long> findAllByCodeMembre(@Param("membre") String codeMembre);

    @Query("select sum(tg.montEchu) from EuTpagcp tg join tg.euCompte c where tg.montEchu > 0 and c.codeCompte like :compte")
    double getSumEchuTpagcpbyCompte(@Param("compte") String codeCompte);

    @Query("select t from EuTpagcp t  where t.solde > 0 and t.codeMembre like :membre and t.modeReglement like :mode")
    List<EuTpagcp> findByMembreAndModeReg(@Param("membre") String codeMembre, @Param("mode") String modeReglement);

    @Query("select t from EuTpagcp t  where t.solde > 0 and t.codeMembre like :membre and t.modeReglement like :mode")
    Page<EuTpagcp> findByMembreAndModeReg(@Param("membre") String codeMembre, @Param("mode") String modeReglement,
                                          Pageable pageable);

    @Query("select t from EuTpagcp t  where t.montEchu > 0 and t.codeMembre like :membre and t.modeReglement like :mode")
    List<EuTpagcp> findEchuByMembreAndModeReg(@Param("membre") String codeMembre, @Param("mode") String modeReglement);

    List<EuTpagcp> findAllByReinjecter(Integer reinjecter);

    List<EuTpagcp> findByDateDeb(LocalDate datedeb);

    @Query("SELECT t FROM EuTpagcp t WHERE t.dateDeb > :date")
    List<EuTpagcp> findByDateDebSup(@Param("date") LocalDate datedeb);

    @Query("SELECT t FROM EuTpagcp t WHERE t.dateDeb  < :date")
    List<EuTpagcp> findByDateDebInf(@Param("date") LocalDate datedeb);

    @Query("SELECT t FROM EuTpagcp t WHERE t.dateDeb > :date")
    Page<EuTpagcp> findByDateDebSup(@Param("date") LocalDate datedeb, Pageable pageable);

    @Query("SELECT t FROM EuTpagcp t WHERE t.dateDeb  < :date")
    Page<EuTpagcp> findByDateDebInf(@Param("date") LocalDate datedeb, Pageable pageable);

    @Query("select t from EuTpagcp t where t.dateDeb >= :deb and t.dateDeb <= :fin")
    List<EuTpagcp> findByDate(@Param("deb") LocalDate datedeb, @Param("fin") LocalDate dateFin);

    List<EuTpagcp> findByDateDebBetween(LocalDate datedeb, LocalDate dateFin);

    Page<EuTpagcp> findByDateDebBetween(LocalDate datedeb, LocalDate dateFin, Pageable pageable);

    Page<EuTpagcp> findByCodeMembreAndDateDebBetween(String codeMembre, LocalDate datedeb, LocalDate dateFin, Pageable pageable);

	Page<EuTpagcp> findByCodeMembreAndDateDebGreaterThan(String codeMembre, LocalDate datedeb, Pageable pageable);

	Page<EuTpagcp> findByCodeMembreAndDateDebLessThanEqual(String codeMembre, LocalDate datedeb, Pageable pageable);

    List<EuTpagcp> findByDateFin(LocalDate dateFin);

    @Query("select t from EuTpagcp t where t.dateFin >= :deb and t.dateDeb <= :fin")
    List<EuTpagcp> findByDateFin(@Param("deb") LocalDate datedeb, @Param("fin") LocalDate dateFin);

    List<EuTpagcp> findByDateFinBetween(LocalDate datedeb, LocalDate dateFin);

    @Query("SELECT t FROM EuTpagcp t WHERE t.dateFin > :date")
    List<EuTpagcp> findByDateFinSup(@Param("date") LocalDate dateFin);

    @Query("SELECT t FROM EuTpagcp t WHERE t.dateFin < :date")
    List<EuTpagcp> findByDateFinInf(@Param("date") LocalDate dateFin);

    @Query("select t from EuTpagcp t where t.dateDeb < :deb and t.dateFin > :fin order by t.dateFin")
    List<EuTpagcp> findByDateDebAndFin(@Param("deb") LocalDate dateDeb, @Param("fin") LocalDate dateFin);

    @Query("select t from EuTpagcp t where t.dateDeb < :deb and t.dateFin > :fin order by t.dateFin")
    List<EuTpagcp> findByDateDebAndFin(@Param("deb") LocalDate dateDeb, @Param("fin") LocalDate dateFin, Pageable page);

    @Query("select t from EuTpagcp t where t.dateDeb < :deb and t.dateFin > :fin and t.ntf >= :ntf")
    List<EuTpagcp> findByDateDebAndFinAndNtf(@Param("deb") LocalDate dateDeb, @Param("fin") LocalDate dateFin, @Param("ntf") Integer ntf);

    @Query("select t from EuTpagcp t where t.dateDeb < :deb and t.dateFin > :fin and t.ntf >= :ntf order by t.dateFin")
    List<EuTpagcp> findByDateDebAndFinAndNtf(@Param("deb") LocalDate dateDeb, @Param("fin") LocalDate dateFin, @Param("ntf") Integer ntf, Pageable page);

}
