/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import com.esmc.mcnp.dao.repository.ba.EuCapaRepository;
import com.esmc.mcnp.dao.repository.ba.EuCapaTsRepository;
import com.esmc.mcnp.dao.repository.bc.EuBonRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.ba.EuCapaTs;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditTs;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrateur
 */
public class DebitCreditImpl implements DebitCredit {

    @Autowired
    private EuCompteRepository compteRepo;
    @Autowired
    private EuCompteCreditTsRepository creditTsRepo;
    @Autowired
    private EuCompteCreditRepository creditRepo;
    @Autowired
    private EuCapaTsRepository capaTsRepo;
    @Autowired
    private EuCapaRepository capaRepo;
    @Autowired
    private EuBonRepository bonRepo;

    @Override
    public String OperationDebitCredit(String codecompte,
            double montantATransferer, String TypedebitCredit,
            String TypeRecurrent, String produit) {
        String reponse = "KO";
        String codeCompteprincipal;
        EuCompte comptePrincipal;
        EuCompte compteTransaction;
        double sommeDesCredits;
        List<EuCompteCredit> listEuCompteCredits;

        if (!codecompte.isEmpty()) {
            compteTransaction = compteRepo.findOne(codecompte);
            String categorie_compte = compteTransaction.getEuCategorieCompte()
                    .getCodeCat();
            int index = codecompte.indexOf("-", 3);
            String categorie = "";
            switch (categorie_compte) {

                case "TSCNCS":
                    categorie = "TCNCS";
                    break;
                case "TSCNCSEI":
                    categorie = "TCNCSEI";
                    break;
                case "TSGCI":
                    categorie = "TPAGCI";
                    break;
                case "TSRPG":
                    categorie = "TPAGCRPG";
                    break;
                case "TSPaR":
                    categorie = "TPaR";
                    break;
                case "TSPaNu":
                    categorie = "TPaNu";
                    break;
                case "TSI":
                    categorie = "TI";
                    break;
                case "TSPN":
                    categorie = "TPN";
                    break;
                case "TSR":
                    categorie = "TR";
                    break;
                case "TSCAPA":
                    categorie = "CAPA";
                    break;
                case "TSGCP":
                    categorie = "TPAGCP";
                    break;
                case "TSFS":
                    categorie = "TFS";
                    break;

            }

            codeCompteprincipal = codecompte.substring(0, 3) + categorie
                    + codecompte.substring(index, codecompte.length());
            // retrouver le compte principal

            comptePrincipal = compteRepo.findOne(codeCompteprincipal);

            switch (TypedebitCredit) {

                case "R": // debit credit rouge

                    listEuCompteCredits = creditRepo.findByProduit(
                            codeCompteprincipal, produit);

                    sommeDesCredits = creditRepo.getSommeCreditByCompte2(
                            codeCompteprincipal, produit);

                    if ((sommeDesCredits < montantATransferer)) {
                        if (sommeDesCredits == 0.0) {
                            reponse = "Vérifier le type de credit à transferer";
                            break;
                        }
                        reponse = "Le montant a transferer depasse le solde du compte";
                        break;
                    } else {
                        double reste = montantATransferer;
                        //creation du bon rouge correspondant
                        String bonNumero = "bonNumero";

                        EuBon euBon = new EuBon();

                        euBon.setBonCodeBarre("");
                        euBon.setBonCodeMembreDistributeur("");
                        if (comptePrincipal.getCodeCompte().endsWith("P")) {
                            euBon.setBonCodeMembreEmetteur(comptePrincipal.getEuMembre().getCodeMembre());
                        } else {
                            euBon.setBonCodeMembreEmetteur(comptePrincipal.getEuMembreMorale().getCodeMembreMorale());
                        }
                        euBon.setBonDate(new Date());
                        euBon.setBonMontant(montantATransferer);
                        euBon.setBonNumero(bonNumero);
                        euBon.setBonType("BS");
                        bonRepo.save(euBon);

                        for (EuCompteCredit eucompteCredit : listEuCompteCredits) {

                            if (eucompteCredit.getMontantCredit() > reste) {
                                if (reste == 0.0) {
                                    break;

                                } else {

                                    EuCompteCreditTs compteCreditTs = new EuCompteCreditTs();
                                    compteCreditTs.setDateCreditTs(new Date());
                                    compteCreditTs.setEuCompteCredit(eucompteCredit);
                                    compteCreditTs.setMontant(reste);
                                    compteCreditTs.setCompteSource(eucompteCredit.getCompteSource());
                                    compteCreditTs.setEuCompte(compteTransaction);
                                    compteCreditTs.setEuBon(euBon);
                                    creditTsRepo.save(compteCreditTs);

                                    //mise a jour eucomptecredit
                                    eucompteCredit.setMontantCredit(eucompteCredit
                                            .getMontantCredit() - reste);
                                    creditRepo.save(eucompteCredit);

                                    reste = 0.0;
                                    break;
                                }
                            } else if (eucompteCredit.getMontantCredit() <= reste) {
                                if (reste == 0.0) {
                                    break;

                                } else {

                                    EuCompteCreditTs compteCreditTs = new EuCompteCreditTs();
                                    compteCreditTs.setDateCreditTs(new Date());
                                    compteCreditTs.setEuCompteCredit(eucompteCredit);
                                    compteCreditTs.setMontant(eucompteCredit.getMontantCredit());
                                    compteCreditTs.setCompteSource(eucompteCredit.getCompteSource());
                                    compteCreditTs.setEuCompte(compteTransaction);
                                    compteCreditTs.setEuBon(euBon);
                                    creditTsRepo.save(compteCreditTs);

                                    //mise a jour eucomptecredit
                                    eucompteCredit.setMontantCredit(0.0);
                                    creditRepo.save(eucompteCredit);

                                    reste = reste
                                            - eucompteCredit.getMontantCredit();

                                }

                            }
                        }

                        //mise a jour des tables comptes
                        comptePrincipal.setSolde(comptePrincipal.getSolde()
                                - montantATransferer);

                        compteTransaction.setSolde(compteTransaction.getSolde()
                                + montantATransferer);

                        compteRepo.save(comptePrincipal);
                        compteRepo.save(compteTransaction);

                        reponse = "OK";
                    }

                    break;

                case "B": // debit credit bleu

                    String codeTypeCredit = "";

                    if (TypeRecurrent.equals("rea")) {
                        codeTypeCredit = "REAPPRO";
                    } else {
                        codeTypeCredit = "CNPG";
                    }

                    System.out.println("codeproduit: " + produit);
                    System.out.println("codeTypeCredit: " + codeTypeCredit);

                    listEuCompteCredits = creditRepo.findByProduitAndCredit(
                            codeCompteprincipal, produit, codeTypeCredit);

                    sommeDesCredits = creditRepo.getSommeCreditByCompte(
                            codeCompteprincipal, produit, codeTypeCredit);
                    System.out.println("sommeDesCredits= " + sommeDesCredits);

                    if ((sommeDesCredits < montantATransferer)) {
                        if (sommeDesCredits == 0.0) {
                            reponse = "Vérifier le type de credit à transferer";
                            break;
                        }
                        reponse = "Le montant a transferer depasse le solde du compte";
                        break;
                    } else {
                        double reste = montantATransferer;

                        //creation du bon correspondant
                        String bonNumero = "bonNumero";

                        EuBon euBon = new EuBon();

                        euBon.setBonCodeBarre("");
                        euBon.setBonCodeMembreDistributeur("");
                        if (comptePrincipal.getCodeCompte().endsWith("P")) {
                            euBon.setBonCodeMembreEmetteur(comptePrincipal.getEuMembre().getCodeMembre());
                        } else {
                            euBon.setBonCodeMembreEmetteur(comptePrincipal.getEuMembreMorale().getCodeMembreMorale());
                        }
                        euBon.setBonDate(new Date());
                        euBon.setBonMontant(montantATransferer);
                        euBon.setBonNumero(bonNumero);
                        euBon.setBonType("BC");
                        bonRepo.save(euBon);

                        for (EuCompteCredit eucompteCredit : listEuCompteCredits) {

                            if (eucompteCredit.getMontantCredit() > reste) {
                                if (reste == 0.0) {
                                    break;

                                } else {
                                    // si ListeCreditsTs est vide

                                    EuCompteCreditTs compteCreditTs = new EuCompteCreditTs();
                                    compteCreditTs.setDateCreditTs(new Date());
                                    compteCreditTs.setEuCompteCredit(eucompteCredit);
                                    compteCreditTs.setMontant(reste);
                                    compteCreditTs.setCompteSource(eucompteCredit.getCompteSource());
                                    compteCreditTs.setEuCompte(compteTransaction);
                                    compteCreditTs.setEuBon(euBon);
                                    creditTsRepo.save(compteCreditTs);

                                    eucompteCredit.setMontantCredit(eucompteCredit
                                            .getMontantCredit() - reste);

                                    reste = 0.0;
                                    creditRepo.save(eucompteCredit);
                                    break;
                                }
                            } else if (eucompteCredit.getMontantCredit() <= reste) {
                                if (reste == 0.0) {
                                    break;

                                } else {

                                    EuCompteCreditTs compteCreditTs = new EuCompteCreditTs();

                                    compteCreditTs.setMontant(eucompteCredit
                                            .getMontantCredit());
                                    compteCreditTs.setDateCreditTs(new Date());
                                    compteCreditTs.setEuCompteCredit(eucompteCredit);
                                    compteCreditTs.setMontant(eucompteCredit.getMontantCredit());
                                    compteCreditTs.setCompteSource(eucompteCredit.getCompteSource());
                                    compteCreditTs.setEuCompte(compteTransaction);
                                    compteCreditTs.setEuBon(euBon);
                                    creditTsRepo.save(compteCreditTs);

                                    reste = reste
                                            - eucompteCredit.getMontantCredit();

                                    eucompteCredit.setMontantCredit(0.0);
                                    creditRepo.save(eucompteCredit);
                                    System.out.println("reste partie<= " + reste);

                                }
                            }
                        }

                        // mise a jour des comptes 
                        comptePrincipal.setSolde(comptePrincipal.getSolde()
                                - montantATransferer);

                        compteTransaction.setSolde(compteTransaction.getSolde()
                                + montantATransferer);

                        compteRepo.save(comptePrincipal);
                        compteRepo.save(compteTransaction);
                    }
                    reponse = "OK";

                    break;
            }
            return reponse;
        } else {
            return reponse;
        }
    }

    @Override
    public String OperationDebitCreditNoir(String codecompte,
            double montantATransferer, String codeProduit) {

        String reponse = "KO";
        String codeCompteprincipal = "";
        EuCompte comptePrincipal = null;
        EuCompte compteTransaction;
        if (!codecompte.isEmpty()) {

            compteTransaction = compteRepo.findOne(codecompte);
            String categorie_compte = compteTransaction.getEuCategorieCompte()
                    .getCodeCat();

            if (categorie_compte.equals("TSCAPA")) {
                int index = codecompte.indexOf("-", 3);
                String categorie = "CAPA";
                codeCompteprincipal = codecompte.substring(0, 3) + categorie
                        + codecompte.substring(index, codecompte.length());
                // recuperer le compte principal
                System.out
                        .println("codeCompteprincipal " + codeCompteprincipal);
                comptePrincipal = compteRepo.findOne(codeCompteprincipal);

            }

            double sommeDesCreditsCapa = 0.0;

            List<EuCapa> listEuCapa = capaRepo.findCapaByProduit(codeCompteprincipal,
                    codeProduit);

            sommeDesCreditsCapa = capaRepo.getSommeByCompteAndProduit(
                    codeCompteprincipal, codeProduit);
            if ((sommeDesCreditsCapa < montantATransferer)) {
                if (sommeDesCreditsCapa == 0.0) {
                    reponse = "Vérifier le produit du NN à transferer";

                }
                reponse = "Le montant a transferer depasse le solde disponible du compte";
                // break;
            } else {
                double reste = montantATransferer;

                for (EuCapa euCapa : listEuCapa) {

                    if (euCapa.getMontantSolde() > reste) {
                        if (reste == 0.0) {
                            break;
                        } else {
                            // si ListeCreditsTs est vide

                            EuCapaTs capaTs = new EuCapaTs();
                            capaTs.setDateCapaTs(new Date());
                            capaTs.setMontant(reste);
                            capaTs.setMontantSolde(Double.valueOf("0"));
                            capaTs.setMontantUtiliser(Double.valueOf("0"));
                            capaTs.setEuCompte(compteTransaction);
                            capaTsRepo.save(capaTs);

                            //  mise a jour de eucapa
                            euCapa.setMontantUtiliser(euCapa
                                    .getMontantUtiliser() + reste);

                            euCapa.setMontantSolde(euCapa.getMontantSolde()
                                    - reste);

                            capaRepo.save(euCapa);
                            break;
                        }
                    } else if (euCapa.getMontantSolde() <= reste) {
                        if (reste == 0.0) {
                            break;
                        } else {

                            EuCapaTs capaTs = new EuCapaTs();
                            capaTs.setDateCapaTs(new Date());
                            capaTs.setMontant(euCapa.getMontantSolde());
                            capaTs.setMontantSolde(Double.valueOf("0"));
                            capaTs.setMontantUtiliser(Double.valueOf("0"));
                            capaTs.setEuCompte(compteTransaction);
                            capaTsRepo.save(capaTs);

                        }
                        // mise a jour de eucapa
                        euCapa.setMontantUtiliser(euCapa.getMontantSolde() + reste);
                        euCapa.setMontantSolde(0);

                        capaRepo.save(euCapa);

                        reste = reste - euCapa.getMontantSolde();

                    }
                }

            }

            if (categorie_compte.equals("TSCAPA")) {
                comptePrincipal.setSolde(comptePrincipal.getSolde()
                        - montantATransferer);

                compteTransaction.setSolde(compteTransaction.getSolde()
                        + montantATransferer);
                // enregistrement en base des capa Ts et capa et comptes
                compteRepo.save(comptePrincipal);
                compteRepo.save(compteTransaction);

            }
            reponse = "OK";
//            else {
//                compteTransaction.setSolde(compteTransaction.getSolde()
//                        + montantATransferer);
//                // enregistrement en base des capa Ts et capa et comptes
//                compteRepo.save(compteTransaction);
//
//                reponse = "OK";
//            }
        }

        return reponse;
    }

}
