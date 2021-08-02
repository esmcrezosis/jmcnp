/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.ksu;

import com.esmc.mcnp.domain.entity.ksu.EuCarte;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * @author HP
 */
public interface EuCarteService extends CrudService<EuCarte, Long> {

    List<EuCarte> findByCodeMembre(String codeMembre);

    Page<EuCarte> findByCodeMembre(String codeMembre, Pageable pageable);

    EuCarte findByMembre(String codeMembre);

    List<EuCarte> findKsuByMembre(String codeMembre);

    Page<EuCarte> findKsuByMembre(String codeMembre, Pageable pageable);

    List<EuCarte> findByCodeMembreMorale(String codeMembre);

    Page<EuCarte> findByCodeMembreMorale(String codeMembre, Pageable pageable);

    EuCarte findByMembreMorale(String codeMembre);

    List<EuCarte> findByImprimer(boolean imprimer);

    List<EuCarte> findByLivrer(boolean livrer);

    List<EuCarte> findByLivrer(boolean livrer, Long idUser);

    List<EuCarte> findByDateDemande(LocalDate dateDemande);

    Page<EuCarte> findByDateDemande(LocalDate dateDemande, Pageable pageable);

    List<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer);

    List<EuCarte> findByDateDemandeAndLivrer(LocalDate dateDemande, boolean livrer);

    List<EuCarte> findByDateDemandeAndLivrer(LocalDate dateDemande, boolean livrer, Long idUser);

    List<EuCarte> findByDate(LocalDate date);

    List<EuCarte> findByDateAndImprimer(LocalDate date, boolean imprimer);

    List<EuCarte> findByDateAndLivrer(LocalDate date, boolean livrer);

    List<EuCarte> findByDate(LocalDate date, Long idUser);

    List<EuCarte> findByDateAndImprimer(LocalDate date, boolean imprimer, Long idUser);

    List<EuCarte> findByDateAndLivrer(LocalDate date, boolean livrer, Long idUser);

    List<EuCarte> findByDateDemandeBetween(LocalDate datedeb, LocalDate datefin);

    Page<EuCarte> findByDateDemandeBetween(LocalDate datedeb, LocalDate datefin, Pageable pageable);

    List<EuCarte> findByLivrerAndDateDemandeBetween(boolean livrer, LocalDate datedeb, LocalDate datefin);

    List<EuCarte> findByLivrerAndDateDemandeBetween(boolean livrer, Long idUser, LocalDate datedeb, LocalDate datefin);

    EuCarte findNonImprimerByMembreMorale(String codeMembre, boolean imprimer);

    EuCarte findNonImprimerByMembre(String codeMembre, boolean imprimer);

    EuCarte findNonLivrerByMembreMorale(String codeMembre, boolean livrer);

    EuCarte findNonLivrerByMembre(String codeMembre, boolean livrer);

    List<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, LocalDate datedeb, LocalDate datefin);

    List<EuCarte> findByImprimer(boolean imprimer, Long idUser);

    List<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer, Long idUser);

    List<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, Long idUser, LocalDate datedeb,
                                                      LocalDate datefin);

    Page<EuCarte> findByImprimer(boolean imprimer, Long idUser, Pageable page);

    Page<EuCarte> findByImprimer(boolean imprimer, Pageable page);

    Page<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer, Long idUser, Pageable page);

    Page<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer, Pageable page);

    Page<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, Long idUser, LocalDate datedeb,
                                                      LocalDate datefin, Pageable page);

    Page<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, LocalDate datedeb,
                                                      LocalDate datefin, Pageable page);

    Page<EuCarte> findByUtilisateurAndDateIn(Long id, LocalDate datedeb, LocalDate datefin, Pageable pageable);

    Page<EuCarte> findByDateInf(LocalDate dateFin, Pageable pageable);

    Page<EuCarte> findByDateSup(LocalDate dateFin, Pageable pageable);

    Page<EuCarte> findByUtilisateurAndDateInf(Long id, LocalDate dateFin, Pageable pageable);

    Page<EuCarte> findByUtilisateurAndDateSup(Long id, LocalDate dateFin, Pageable pageable);

    Page<EuCarte> findByUser(Long idUser, Pageable pageable);
}
