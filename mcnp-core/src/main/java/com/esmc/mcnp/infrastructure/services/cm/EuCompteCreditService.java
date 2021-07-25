package com.esmc.mcnp.services.cm;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.services.base.BaseService;

import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EuCompteCreditService extends CrudService<EuCompteCredit, Long> {
    Long getLastCreditInsertedId();

    List<EuCompteCredit> fetchByCompteAndTypeCreditAndProduit(String codeCompte, String typeCredit, String codeProduit);

    List<EuCompteCredit> fetchByCompteAndTypeCreditAndProduitAndPrk(String codeCompte, String typeCredit,
                                                                    String codeProduit, Double prk);

    List<EuCompteCredit> findByCodeCompte(String codeCompte);

    Page<EuCompteCredit> findByCodeCompte(String codeCompte, Pageable pageable);

    List<EuCompteCredit> findByCompteAndType(String codeCompte, String typeRecurrent);

    Page<EuCompteCredit> findByCompteAndType(String codeCompte, String typeRecurrent, Pageable pageable);

    List<EuCompteCredit> findByCompteandSource(String codeCompte, String source);

    List<EuCompteCredit> findByCompteAndTypeCredit(String codeCompte, String typeCredit);

    List<EuCompteCredit> findbyCompteAndProduit(String codeCompte, String codeProduit);

    List<EuCompteCredit> findByCompteAndTypeCreditAndProduit(String codeCompte, String typeCredit, String produit);

    boolean verifierSolde(String codeCompte);

    boolean verifierCompte(String codeCompte);

    boolean verifierSolde(String codeCompte, double montant);

    boolean verifierSolde(String codeCompte, String typeCredit, Double montant);

    boolean verifierSolde(String codeCompte, String typeCredit, String produit, Double montant);

    List<EuCompteCredit> fetchByCompteAndProduitAndPrk(String codeCompte, String codeProduit, Double prk);

    List<EuCompteCredit> fetchByCompteAndProduitAndPrk(String codeCompte, String codeProduit, Double prk,
                                                       String typeProduit);

    List<EuCompteCredit> findByTypeRecurrentAndTypeProduit(String codeCompte, String codeProduit, String typeRecurrent,
                                                           String typeProduit);

    List<EuCompteCredit> findByTypeRecurrentAndTypeProduitAndDuree(String codeCompte, String codeProduit,
                                                                   String typeRecurrent, String typeProduit, double duree);

    Double getSommeCreditRecurrentByType(String codeCompte, String codeProduit, String typeRecurrent,
                                         String typeProduit);

    Double getSommeCreditRecurrentByTypeAndDuree(String codeCompte, String codeProduit, String typeRecurrent,
                                                 String typeProduit, double duree);

    Double getSumCreditByEuCompte(String codeCompte);

    Page<EuCompteCredit> findCredits(String codeMembre, String typeRecurrent, Date dateDeb, Date dateFin,
                                     Pageable pageable);

    Page<EuCompteCredit> findByCodeMembre(String codeMembre, Pageable pageable);

    Page<EuCompteCredit> findByCodeMembreAndTypeRecurrent(String codeMembre, String typeRecurrent, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrent(String typeRecurrent, Pageable pageable);

    Page<EuCompteCredit> findByDateOctroiBetween(Date dateDeb, Date dateFin, Pageable pageable);

    Page<EuCompteCredit> findByDatesup(Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByDateInf(Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByMembreAndDateSup(String codeMembre, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByMembreAndDateInf(String codeMembre, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByMembreAndDateBetween(String codeMembre, Date dateDeb, Date fin, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrentAndDateSup(String typeRecurrent, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrentAndDateInf(String typeRecurrent, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrentAndDateBetween(String typeRecurrent, Date dateDeb, Date fin, Pageable pageable);

    Page<EuCompteCredit> findByProduitAndDateSup(String codeProduit, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByProduitAndDateInf(String codeProduit, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByProduitAndDateBetween(String codeProduit, Date dateDeb, Date fin, Pageable pageable);

    Page<EuCompteCredit> findByProduit(String produit, Pageable pageable);

    Page<EuCompteCredit> findCreditByMembreAndTypeCurrentAndDate(String codeMembre, String typeRecurrent,
                                                             Date dateDeb, Date dateFin, Pageable pageable);

    Page<EuCompteCredit> findCreditByMembreAndProduitAndDate(String codeMembre, String codeProduit, Date dateDeb, Date dateFin, Pageable pageable);

    Page<EuCompteCredit> fetchByMembreAndProduitAndPrk(String codeMembre,
                                                       String codeProduit, Double prk, Pageable pageable);

    Page<EuCompteCredit> fetchByMembreAndPrk(String codeMembre, Double prk, Pageable pageable);

    Page<EuCompteCredit> findByPrk(Double prk, Pageable pageable);

    Page<EuCompteCredit> findCreditByMembreAndProduit(String codeMembre, String codeProduit, Pageable pageable);

    Page<EuCompteCredit> findAll(Pageable pageable);
}
