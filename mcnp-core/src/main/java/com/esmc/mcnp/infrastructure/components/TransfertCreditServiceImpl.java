/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.acteurs.EuActeurRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuSmcipnwiRepository;
import com.esmc.mcnp.domain.dto.smcipn.Transfert;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditTs;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author USER
 */
@Service
@Transactional
public class TransfertCreditServiceImpl implements TransfertCreditService {

    @Autowired
    private EuCompteRepository compteDao;
    @Autowired
    private EuCompteCreditRepository ccreditRepo;
    @Autowired
    EuMembreRepository membreRepo;
    @Autowired
    EuMembreMoraleRepository moralRepo;
    @Autowired
    private EuSmcipnwiRepository smcipnRepo;
    @Autowired
    private EuAppelOffreRepository offreRepo;
    @Autowired
    private EuActeurRepository acteurRepo;
    @Autowired
    EuCompteCreditTsRepository cctsRepo;

    SimpleDateFormat formatter = null;

    /**
     * Transfert de Numérique rouge salaire du TPN vers TCNCS
     *
     * @param transfert Objet du type Transfert comportant les infos de
     * transfert
     * @return String chaîne de caractères expliquant si l'opération a été bien
     * effectuée
     */
    @Override
    public String transfertCredit(Transfert transfert) {
        if (transfert.getNumeroAppel() != null
                && !transfert.getNumeroAppel().isEmpty()) {
            String catDest = "";
            String catTransfert = "";
            String typeNumDest = "";
            String codeMembreDest = "";
            String codeCompteDest = "";
            String codeProduit = "";
            String codeMembreTransfert = "";
            Date date = new Date();

            // EuAppelNn appelNn = appelRepo.findByNumeroAppel(transfert.getNumeroAppel());
            EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(transfert
                    .getNumeroAppel());
            if (smcipn != null) {
                switch (transfert.getTypeTransfert()) {
                    case "TI":
                        codeProduit = "I" + smcipn.getTypeNr();
                        break;
                    case "TPN":
                        codeProduit = "CNCS" + smcipn.getTypeNr();
                        break;
                }
                double restei = smcipn.getMontInvestis()
                        - smcipn.getInvestisAlloue();
                double reste_cncs = smcipn.getMontSalaire()
                        - smcipn.getSalaireAlloue();
                if (codeProduit.equals("InrPRE")) {
                    if (restei < transfert.getMontTransfert()) {
                        return "Votre solde TI est insuffisant!";
                    }
                } else {
                    if (reste_cncs < transfert.getMontTransfert()) {
                        return "Votre solde TPN est insuffisant!";
                    }
                }
                codeMembreTransfert = smcipn.getCodeMembre();
                if (smcipn.getTypeSmcipn().equals("SMCIPNP")) {
                    catTransfert = "TESMCIPNP";
                } else {
                    catTransfert = "TESMCIPNWI";
                }
                String codeCompteTransfert = "NR-" + catTransfert + "-"
                        + codeMembreTransfert;
                EuCompte compteTransfert = compteDao
                        .findOne(codeCompteTransfert);
                if (compteTransfert != null) {
                    if (compteTransfert.getSolde() >= transfert
                            .getMontTransfert()) {
                        if (!transfert.getCodeCompteDest().isEmpty()) {
                            codeCompteDest = transfert.getCodeCompteDest();
                            catDest = ServerUtil.getTypeCompte(codeCompteDest);
                            typeNumDest = ServerUtil
                                    .getTypeNumerique(codeCompteDest);
                            codeMembreDest = ServerUtil
                                    .getCodeMembre(codeCompteDest);

                            if (!typeNumDest.equals("NR")) {
                                return "Il faut une carte Numérique Rouge(NR)";
                            }
                            if (!catDest.equals("TSI")
                                    && !catDest.equals("TSPN")
                                    && !catDest.equals("TSCNCSEI")) {
                                return "IL faut un compte TSPN ou TCNCSEI ou TI";
                            }
                            if (transfert.getTypeTransfert().equals("TI")
                                    && !catDest.equals("TSI")) {
                                return "IL faut un compte  TI";
                            }
                            if (transfert.getTypeTransfert().equals("TPN")
                                    && (!catDest.equals("TSPN")
                                    && !catDest.equals("TSCNCSEI"))) {
                                return "IL faut un compte  Salaire TPN, TCNCSEI";
                            }
                            switch (catDest) {
                                case "TSI":
                                    codeCompteDest = "NR-TI-" + codeMembreDest;
                                    break;
                                case "TSPN":
                                    codeCompteDest = "NR-TPN-" + codeMembreDest;
                                    break;
                                default:
                                    codeCompteDest = "NR-TCNCSEI-"
                                            + codeMembreDest;
                                    break;
                            }
                            EuCompte compteDest = compteDao
                                    .findOne(codeCompteDest);
                            if (compteDest != null) {
                                compteDest.setSolde(compteDest.getSolde()
                                        + transfert.getMontTransfert());
                                compteDao.save(compteDest);
                            } else {
                                compteDest = new EuCompte();
                                EuCategorieCompte cat = new EuCategorieCompte();
                                cat.setCodeCat(catDest);
                                EuTypeCompte typeCompte = new EuTypeCompte();
                                typeCompte.setCodeTypeCompte(typeNumDest);
                                compteDest.setCodeCompte(codeCompteDest);
                                compteDest.setEuCategorieCompte(cat);
                                compteDest.setEuTypeCompte(typeCompte);
                                if (codeMembreDest.endsWith("P")) {
                                    compteDest.setEuMembre(membreRepo
                                            .findOne(codeMembreDest));
                                } else {
                                    compteDest.setEuMembreMorale(moralRepo
                                            .findOne(codeMembreDest));
                                }
                                compteDest.setDesactiver("N");
                                compteDest.setSolde(transfert
                                        .getMontTransfert());
                                compteDest.setDateAlloc(date);
                                compteDao.save(compteDest);
                            }

                            EuProduit produit = new EuProduit();
                            produit.setCodeProduit(codeProduit);
                            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                            EuCompteCredit cc = new EuCompteCredit();
                            Long idCredit = ccreditRepo
                                    .getLastCreditInsertedId();
                            if (idCredit == null) {
                                idCredit = 0L;
                            }
                            cc.setIdCredit(idCredit + 1);
                            cc.setCodeMembre(codeMembreDest);
                            cc.setMontantPlace(transfert.getMontTransfert());
                            cc.setMontantCredit(transfert.getMontTransfert());
                            cc.setEuOperation(null);
                            cc.setEuProduit(produit);
                            cc.setCodeTypeCredit(null);
                            cc.setSource(codeMembreDest
                                    + formatter.format(date));
                            cc.setCompteSource(smcipn.getCodeSmcipn());
                            cc.setDateOctroi(date);
                            cc.setDatedeb(date);
                            Date datefin = ServerUtil.ajouterJours(date, 30);
                            cc.setDatefin(datefin);
                            cc.setRenouveller("N");
                            cc.setNbreRenouvel(1);
                            cc.setBnp(0);
                            cc.setKrr("N");
                            cc.setEuCompte(compteDest);
                            cc.setDomicilier(0);
                            cc.setPrk((double) 1);
                            cc.setAffecter(0);
                            cc.setCodeBnp(null);
                            ccreditRepo.save(cc);

                            if (transfert.getTypeTransfert().equals("TI")) {
                                smcipn.setInvestisAlloue(smcipn
                                        .getInvestisAlloue()
                                        + transfert.getMontTransfert());
                            } else {
                                smcipn.setSalaireAlloue(smcipn
                                        .getSalaireAlloue()
                                        + transfert.getMontTransfert());
                            }
                            smcipnRepo.save(smcipn);

                            compteTransfert.setSolde(compteTransfert.getSolde()
                                    - transfert.getMontTransfert());
                            compteDao.save(compteTransfert);
                        } else {
                            return "Les 2 comptes de transfert doivent etre fournis";
                        }
                    } else {
                        return "Votre solde est insufissant pour effectuer cette opération";
                    }
                }
            }
            return "L'opération a été effectuée avec succès!";
        } else {
            return "Les 2 comptes de transfert doivent être fournis";
        }
    }

    /**
     * Transfert de Numérique rouge salaire du TPN vers TCNCS
     *
     * @param transfert Objet du type Transfert comportant les infos de
     * transfert
     * @return String chaîne de caractères expliquant si l'opération a été bien
     * effectuée
     */
    @Override
    public String transfertCNCS(Transfert transfert) {
        if (!transfert.getCodeCompteTransfert().isEmpty()
                && !transfert.getCodeCompteDest().isEmpty()
                && !transfert.getNumeroAppel().isEmpty()) {

            String codeCompteDest = transfert.getCodeCompteDest();
            String codeCompteTransfert = transfert.getCodeCompteTransfert();
            String codeMembreDest = ServerUtil.getCodeMembre(codeCompteDest);
            String codeMembreTransfert = ServerUtil.getCodeMembre(codeCompteTransfert);
            String typeNumDest = ServerUtil.getTypeNumerique(codeCompteDest);
            String typeNumTransfert = ServerUtil.getTypeNumerique(codeCompteTransfert);

            EuActeur acteur_tr = acteurRepo.findByCodeMembre(codeMembreTransfert);
            //Récupération du numéro d'appel d'offre
            EuAppelOffre offre = offreRepo.findAppelOffresByNumero(transfert.getNumeroAppel());
            if (offre != null) {
                //Récupération du SMCIPN concerné par le transfert du salaire
                EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(transfert.getNumeroAppel());
                if (Objects.nonNull(smcipn) && acteur_tr.getTypeActeur().equals("gac_surveillance") && typeNumDest.equals("NR") && typeNumTransfert.equals("NR")) {
                    String catTransfert = ServerUtil
                            .getTypeCompte(codeCompteTransfert);
                    if (catTransfert.equals("TSPN")) {
                        String catDest = ServerUtil.getTypeCompte(codeCompteDest);
                        String codeProduit = "";
                        Date date = new Date();
                        codeCompteTransfert = "NR-TPN-" + codeMembreTransfert;
                        System.out.println("Compte transfert:" + codeCompteTransfert);
                        EuCompte compteTransfert = compteDao.findOne(codeCompteTransfert);
                        if (compteTransfert != null) {
                            if (compteTransfert.getSolde() >= transfert.getMontTransfert()) {
                                List<EuCompteCredit> credit_s = ccreditRepo
                                        .findByEuCompte_CodeCompte(compteTransfert.getCodeCompte());
                                if (credit_s.isEmpty()) {
                                    return "Il n'y a pas de crédits correspondant à ce compte TR";
                                } else {
                                    Double somme_credit = credit_s.stream().mapToDouble((c) -> c.getMontantCredit()).sum();
                                    if (compteTransfert.getSolde() != somme_credit) {
                                        return "Votre compte n'est pas intègre!!!";
                                    }
                                }
                                List<EuCompteCredit> credits = ccreditRepo.findByCompteandSource(codeCompteTransfert, smcipn.getCodeSmcipn());
                                if (credits == null || credits.isEmpty()) {
                                    return "Il n'y a pas de crédits correspondant à ce compte";
                                } else {
                                    Double somme_credit = credits.stream().mapToDouble((c) -> c.getMontantCredit()).sum();
                                    if (transfert.getMontTransfert() > somme_credit) {
                                        return "Votre compte est insuffisant!!!";
                                    }
                                }

                                //Création ou mise à jour du compte bénéficiaire du transfert
                                codeCompteDest = transfert.getCodeCompteDest();
                                catDest = ServerUtil.getTypeCompte(codeCompteDest);
                                typeNumDest = ServerUtil.getTypeNumerique(codeCompteDest);
                                codeMembreDest = ServerUtil.getCodeMembre(codeCompteDest);
                                if (!catDest.equals("TSCNCS") && !catDest.equals("TSPN")) {
                                    return "IL faut un compte TCNCS ou TPN";
                                }
                                switch (catDest) {
                                    case "TSCNCS":
                                        codeCompteDest = "NR-TCNCS-" + codeMembreDest;
                                        break;
                                    case "TSCNCSEI":
                                        codeCompteDest = "NR-TCNCSEI-" + codeMembreDest;
                                        break;
                                    default:
                                        codeCompteDest = "NR-TPN-" + codeMembreDest;
                                        break;
                                }
                                codeProduit = "CNCSnr";
                                EuCompte compteDest = compteDao
                                        .findOne(codeCompteDest);
                                if (compteDest != null) {
                                    compteDest.setSolde(compteDest.getSolde() + transfert.getMontTransfert());
                                    compteDao.save(compteDest);
                                } else {
                                    compteDest = new EuCompte();
                                    EuCategorieCompte cat = new EuCategorieCompte();
                                    cat.setCodeCat(catDest);
                                    EuTypeCompte typeCompte = new EuTypeCompte();
                                    typeCompte.setCodeTypeCompte(typeNumDest);
                                    compteDest.setCodeCompte(codeCompteDest);
                                    compteDest.setEuCategorieCompte(cat);
                                    compteDest.setEuTypeCompte(typeCompte);
                                    compteDest.setEuMembre(membreRepo
                                            .findOne(codeMembreDest));
                                    compteDest.setEuMembreMorale(null);
                                    compteDest.setDesactiver("N");
                                    compteDest.setSolde(transfert
                                            .getMontTransfert());
                                    compteDest.setDateAlloc(date);
                                    compteDao.save(compteDest);
                                }

                                //Enregistrement du transfert sur le compte crédit du bénéficiaire
                                EuProduit produit = new EuProduit();
                                produit.setCodeProduit(codeProduit);
                                formatter = new SimpleDateFormat(
                                        "yyyyMMddHHmmss");
                                EuCompteCredit cc = new EuCompteCredit();
                                Long idCredit = ccreditRepo
                                        .getLastCreditInsertedId();
                                if (idCredit == null) {
                                    idCredit = 0L;
                                }
                                cc.setIdCredit(idCredit + 1);
                                cc.setCodeMembre(codeMembreDest);
                                cc.setMontantPlace(transfert.getMontTransfert());
                                cc.setMontantCredit(transfert
                                        .getMontTransfert());
                                cc.setEuOperation(null);
                                cc.setEuProduit(produit);
                                cc.setCodeTypeCredit(null);
                                cc.setSource(codeMembreDest
                                        + formatter.format(date));
                                cc.setCompteSource(codeCompteTransfert);
                                cc.setDateOctroi(date);
                                cc.setDatedeb(date);
                                Date datefin = ServerUtil
                                        .ajouterJours(date, 30);
                                cc.setDatefin(datefin);
                                cc.setRenouveller("N");
                                cc.setNbreRenouvel(1);
                                cc.setBnp(0);
                                cc.setKrr("N");
                                cc.setEuCompte(compteDest);
                                cc.setDomicilier(0);
                                cc.setPrk((double) 1);
                                cc.setAffecter(0);
                                cc.setCodeBnp(null);
                                ccreditRepo.save(cc);

                                double mont_deduire = transfert
                                        .getMontTransfert();
                                int i = 0;
                                while (mont_deduire > 0) {
                                    EuCompteCredit c = credits.get(i);
                                    if (c.getMontantCredit() >= mont_deduire) {
                                        c.setMontantCredit(c.getMontantCredit()
                                                - mont_deduire);
                                        ccreditRepo.saveAndFlush(c);
                                        mont_deduire = 0;
                                    } else {
                                        mont_deduire -= c.getMontantCredit();
                                        c.setMontantCredit(0.0);
                                        ccreditRepo.saveAndFlush(c);
                                        i++;
                                    }
                                }

                                compteTransfert.setSolde(compteTransfert
                                        .getSolde()
                                        - transfert.getMontTransfert());
                                compteDao.save(compteTransfert);
                                return "L'opération a été effectuée avec succés!";
                            } else {
                                return "Votre solde est insufissant pour effectuer cette opération";
                            }
                        } else {
                            return "Le compte de transfert n'existe pas";
                        }
                    } else {
                        return "Il faut un compte de transfert TPN";
                    }
                } else {
                    return "Il faut deux comptes numériques rouges \n ou le code d'offre d'offre n'est pas correct!";
                }
            } else {
                return "Cette offre n'existe pas";
            }

        } else {
            return "Le compte de transfert et du bénéficiaire sont obligatoires!";
        }
    }

    /**
     * Transfert de Numérique rouge salaire du TPN vers TCNCS
     *
     * @param transfert Objet du type Transfert comportant les infos de
     * transfert
     * @return String chaîne de caractères expliquant si l'opération a été bien
     * effectuée
     */
    @Override
    public String transfertNr(Transfert transfert) {
        if (!transfert.getCodeCompteTransfert().isEmpty() && !transfert.getCodeCompteDest().isEmpty()) {
            String codeCompteTransfert = transfert.getCodeCompteTransfert();
            String typeNumDest = ServerUtil.getTypeNumerique(transfert.getCodeCompteDest());
            String typeNumTransfert = ServerUtil.getTypeNumerique(codeCompteTransfert);
            if (typeNumDest.equals("NR") && typeNumTransfert.equals("NR")) {
                String catTransfert = ServerUtil.getTypeCompte(codeCompteTransfert);
                if (catTransfert.equals("TSPN") || catTransfert.equals("TSCNCSEI")) {
                    String codeProduit = "";
                    String codeMembreDest = ServerUtil.getCodeMembre(transfert.getCodeCompteDest());
                    String codeMembreTransfert = ServerUtil.getCodeMembre(codeCompteTransfert);
                    Date date = new Date();
                    if (catTransfert.equals("TSPN")) {
                        codeCompteTransfert = "NR-TSPN-" + codeMembreTransfert;
                    } else {
                        codeCompteTransfert = "NR-TCNCSEI-" + codeMembreTransfert;
                    }
                    System.out.println("Compte transfert:" + codeCompteTransfert);
                    EuCompte compteTransfert = compteDao.findOne(codeCompteTransfert);
                    EuActeur acteur_tr = acteurRepo
                            .findByCodeMembre(codeMembreTransfert);
                    if (acteur_tr != null && compteTransfert != null) {
                        if (compteTransfert.getSolde() >= transfert.getMontTransfert()) {
                            List<EuCompteCreditTs> credit_s = cctsRepo
                                    .findByEuCompte_CodeCompte(compteTransfert.getCodeCompte());
                            if (credit_s.isEmpty()) {
                                return "Il n'y a pas de crédits correspondant à ce compte TR";
                            } else {
                                Double somme_credit = credit_s.stream().mapToDouble((c) -> c.getMontant()).sum();
                                if (compteTransfert.getSolde() != somme_credit) {
                                    return "Votre compte n'est pas intègre!!!";
                                }
                                if (transfert.getMontTransfert() > somme_credit) {
                                    return "Votre compte est insuffisant!!!";
                                }
                            }

                            //Création ou mise à jour du compte bénéficiaire du transfert
                            String catDest = ServerUtil
                                    .getTypeCompte(transfert.getCodeCompteDest());
                            if (!catDest.equals("TSCNCS")
                                    && !catDest.equals("TSPN")) {
                                return "IL faut un compte TCNCS ou TPN";
                            }
                            String codeCompteDest = StringUtils.EMPTY;
                            switch (catDest) {
                                case "TSCNCS":
                                    codeCompteDest = "NR-TCNCS-"
                                            + codeMembreDest;
                                    break;
                                case "TSCNCSEI":
                                    codeCompteDest = "NR-TCNCSEI-"
                                            + codeMembreDest;
                                    break;
                                default:
                                    codeCompteDest = "NR-TPN-" + codeMembreDest;
                                    break;
                            }
                            codeProduit = "CNCSnr";
                            EuCompte compteDest = compteDao.findOne(codeCompteDest);
                            if (compteDest != null) {
                                compteDest.setSolde(compteDest.getSolde()
                                        + transfert.getMontTransfert());
                                compteDao.save(compteDest);
                            } else {
                                compteDest = new EuCompte();
                                EuCategorieCompte cat = new EuCategorieCompte();
                                cat.setCodeCat(catDest);
                                EuTypeCompte typeCompte = new EuTypeCompte();
                                typeCompte.setCodeTypeCompte(typeNumDest);
                                compteDest.setCodeCompte(codeCompteDest);
                                compteDest.setEuCategorieCompte(cat);
                                compteDest.setEuTypeCompte(typeCompte);
                                compteDest.setEuMembre(membreRepo
                                        .findOne(codeMembreDest));
                                compteDest.setEuMembreMorale(null);
                                compteDest.setDesactiver("N");
                                compteDest.setSolde(transfert
                                        .getMontTransfert());
                                compteDest.setDateAlloc(date);
                                compteDao.save(compteDest);
                            }

                            //Enregistrement du transfert sur le compte crédit du bénéficiaire
                            EuProduit produit = new EuProduit();
                            produit.setCodeProduit(codeProduit);
                            formatter = new SimpleDateFormat(
                                    "yyyyMMddHHmmss");
                            EuCompteCredit cc = new EuCompteCredit();
                            Long idCredit = ccreditRepo
                                    .getLastCreditInsertedId();
                            if (idCredit == null) {
                                idCredit = 0L;
                            }
                            cc.setIdCredit(idCredit + 1);
                            cc.setCodeMembre(codeMembreDest);
                            cc.setMontantPlace(transfert.getMontTransfert());
                            cc.setMontantCredit(transfert
                                    .getMontTransfert());
                            cc.setEuOperation(null);
                            cc.setEuProduit(produit);
                            cc.setCodeTypeCredit(null);
                            cc.setSource(codeMembreDest
                                    + formatter.format(date));
                            cc.setCompteSource(codeCompteTransfert);
                            cc.setDateOctroi(date);
                            cc.setDatedeb(date);
                            Date datefin = ServerUtil
                                    .ajouterJours(date, 30);
                            cc.setDatefin(datefin);
                            cc.setRenouveller("N");
                            cc.setNbreRenouvel(1);
                            cc.setBnp(0);
                            cc.setKrr("N");
                            cc.setEuCompte(compteDest);
                            cc.setDomicilier(0);
                            cc.setPrk((double) 1);
                            cc.setAffecter(0);
                            cc.setCodeBnp(null);
                            ccreditRepo.save(cc);

                            double mont_deduire = transfert
                                    .getMontTransfert();
                            int i = 0;
                            while (mont_deduire > 0) {
                                EuCompteCreditTs c = credit_s.get(i);
                                if (c.getMontant() >= mont_deduire) {
                                    c.setMontant(c.getMontant()
                                            - mont_deduire);
                                    cctsRepo.saveAndFlush(c);
                                    mont_deduire = 0;
                                } else {
                                    mont_deduire -= c.getMontant();
                                    c.setMontant(0d);
                                    cctsRepo.saveAndFlush(c);
                                    i++;
                                }
                            }

                            compteTransfert.setSolde(compteTransfert.getSolde()
                                    - transfert.getMontTransfert());
                            compteDao.save(compteTransfert);
                            return "L'opération a été effectuée avec succés!";
                        } else {
                            return "Votre solde est insufissant pour effectuer cette opération";
                        }
                    } else {
                        return "Le compte de transfert n'existe pas";
                    }
                } else {
                    return "Il faut un compte de transfert TPN ou TCNCSEI!";
                }
            } else {
                return "Il faut deux comptes numériques rouges!";
            }

        } else {
            return "Le compte de transfert et du bénéficiaire sont obligatoires!";
        }
    }

}
