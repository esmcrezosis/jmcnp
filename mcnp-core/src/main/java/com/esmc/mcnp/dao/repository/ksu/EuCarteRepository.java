/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.ksu;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.ksu.EuCarte;

/**
 * @author HP
 */
public interface EuCarteRepository extends BaseRepository<EuCarte, Long> {

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByDateDemande(LocalDate dateDemande);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByDateDemande(LocalDate dateDemande, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre"})
    List<EuCarte> findByEuMembre_CodeMembre(String codeMembre);

    @EntityGraph(attributePaths = {"euMembre"})
    Page<EuCarte> findByEuMembre_CodeMembre(String codeMembre, Pageable pageable);

    @Query("select c from EuCarte c left join fetch c.euMembre m where m.codeMembre like :code")
    EuCarte findByCodeMembre(@Param("code") String codeMembre);

    @EntityGraph(attributePaths = {"euMembre"})
    List<EuCarte> findByEuMembreMorale_CodeMembreMorale(String codeMembre);

    @EntityGraph(attributePaths = {"euMembre"})
    Page<EuCarte> findByEuMembreMorale_CodeMembreMorale(String codeMembre, Pageable pageable);

    @Query("select c from EuCarte c left join fetch c.euMembreMorale m where m.codeMembreMorale like :code")
    EuCarte findByCodeMembreMorale(@Param("code") String codeMembre);

    @Query("select c from EuCarte c left join fetch c.euMembreMorale m where m.codeMembreMorale like :code and c.imprimer = :imp")
    EuCarte findNonImprimerByMembreMorale(@Param("code") String codeMembre, @Param("imp") boolean imprimer);

    @Query("select c from EuCarte c left join fetch c.euMembre m where m.codeMembre like :code and c.imprimer = :imp")
    EuCarte findNonImprimerByMembre(@Param("code") String codeMembre, @Param("imp") boolean imprimer);

    @Query("select c from EuCarte c left join fetch c.euMembreMorale m where m.codeMembreMorale like :code and c.livrer = :livrer")
    EuCarte findNonLivrerByMembreMorale(@Param("code") String codeMembre, @Param("livrer") boolean livrer);

    @Query("select c from EuCarte c left join fetch c.euMembre m where m.codeMembre like :code and c.livrer = :livrer")
    EuCarte findNonLivrerByMembre(@Param("code") String codeMembre, @Param("livrer") boolean livrer);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByImprimer(boolean imprimer);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByImprimer(boolean imprimer, Pageable page);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByLivrer(boolean livrer);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByLivrerAndIdUtilisateur(boolean livrer, Long idUser);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByDateDemandeAndLivrer(LocalDate dateDemande, boolean livrer);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByDateDemandeAndImprimerAndIdUtilisateur(LocalDate dateDemande, boolean imprimer, Long idUser);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByDateDemandeAndLivrerAndIdUtilisateur(LocalDate dateDemande, boolean livrer, Long idUser);

    @Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date")
    List<EuCarte> findByDate(@Param("date") LocalDate date);

    @Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.imprimer = :imp")
    List<EuCarte> findByDateAndImprimer(@Param("date") LocalDate date, @Param("imp") boolean imprimer);

    @Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.livrer = :livrer")
    List<EuCarte> findByDateAndLivrer(@Param("date") LocalDate date, @Param("livrer") boolean livrer);

    @Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.idUtilisateur = :user")
    List<EuCarte> findByDate(@Param("date") LocalDate date, @Param("user") Long idUser);

    @Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.livrer = :livrer and c.idUtilisateur = :user")
    List<EuCarte> findByDateAndLivrer(@Param("date") LocalDate date, @Param("livrer") boolean livrer, @Param("user") Long idUser);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByDateDemandeBetween(LocalDate datedeb, LocalDate datefin);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByDateDemandeBetween(LocalDate datedeb, LocalDate datefin, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, LocalDate datedeb, LocalDate datefin);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByLivrerAndDateDemandeBetween(boolean livrer, LocalDate datedeb, LocalDate datefin);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByImprimerAndIdUtilisateur(boolean imprimer, Long idUser);

    @Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.imprimer = :imp and c.idUtilisateur = :user")
    List<EuCarte> findByDateAndImprimer(@Param("date") LocalDate date, @Param("imp") boolean imprimer, @Param("user") Long idUser);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByImprimerAndIdUtilisateurAndDateDemandeBetween(boolean imprimer, Long IdUtilisateur,
                                                                      LocalDate datedeb, LocalDate datefin);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByImprimerAndIdUtilisateur(boolean imprimer, Long idUser, Pageable page);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
        //@Query("select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.imprimer = :imp and c.idUtilisateur = :user")
    Page<EuCarte> findByDateDemandeAndImprimerAndIdUtilisateur(@Param("date") LocalDate date, @Param("imp") boolean imprimer, @Param("user") Long idUser, Pageable page);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByImprimerAndIdUtilisateurAndDateDemandeBetween(boolean imprimer, Long IdUtilisateur,
                                                                      LocalDate datedeb, LocalDate datefin, Pageable page);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByImprimerAndAndDateDemandeBetween(boolean imprimer,
                                                         LocalDate datedeb, LocalDate datefin, Pageable page);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    List<EuCarte> findByLivrerAndIdUtilisateurAndDateDemandeBetween(boolean livrer, Long IdUtilisateur,
                                                                    LocalDate datedeb, LocalDate datefin);

    @Query(value = "select c From EuCarte c left join fetch c.euMembre m left join fetch c.euMembreMorale o where c.dateDemande <= :date and c.imprimer = :imp", countQuery = "select count(c) " +
            "From EuCarte c left join c.euMembre m left join c.euMembreMorale o where c.dateDemande <= :date and c.imprimer = :imp")
    Page<EuCarte> findByDateAndImprimer(@Param("date") LocalDate date, @Param("imp") boolean imprimer, Pageable page);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByIdUtilisateurAndDateDemandeBetween(Long id,LocalDate datedeb, LocalDate datefin, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByDateDemandeLessThan(LocalDate dateFin, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByDateDemandeGreaterThan(LocalDate dateFin, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByIdUtilisateurAndDateDemandeLessThan(Long id, LocalDate dateFin, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByIdUtilisateurAndDateDemandeGreaterThan(Long id, LocalDate dateFin, Pageable pageable);

    @EntityGraph(attributePaths = {"euMembre", "euMembreMorale"})
    Page<EuCarte> findByIdUtilisateur(Long id, Pageable pageable);
}
