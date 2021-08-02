/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.acteurs.EuActeurRepository;
import com.esmc.mcnp.dao.repository.ba.EuCapaRepository;
import com.esmc.mcnp.dao.repository.ba.EuCapaTsRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.common.EuFraisRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTransfertNnRepository;
import com.esmc.mcnp.dao.repository.others.EuAppelNnRepository;
import com.esmc.mcnp.dao.repository.others.EuDetailAppelNnRepository;
import com.esmc.mcnp.dao.repository.others.EuGacRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuPropositionRepository;
import com.esmc.mcnp.domain.dto.desendettement.Collect;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.acteur.EuGac;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.ba.EuCapaTs;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.obpsd.EuTransfertNn;
import com.esmc.mcnp.domain.entity.op.EuAppelNn;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.op.EuDetailAppelNn;
import com.esmc.mcnp.domain.entity.others.EuFrais;
import com.esmc.mcnp.domain.entity.others.EuProposition;

/**
 *
 * @author USER
 */
@Repository
@Service("collectNnService")
@Transactional
public class CollectNnServiceImpl implements CollectNnService {

    @Autowired
    private EuCompteRepository compteRepo;
    @Autowired
    private EuAppelNnRepository appelRepo;
    @Autowired
    private EuDetailAppelNnRepository detailAppelRepo;
    @Autowired
    private EuFraisRepository fraisRepo;
    @Autowired
    private EuAppelOffreRepository offreRepo;
    @Autowired
    private EuPropositionRepository propositionRepo;
    @Autowired
    private EuGacRepository gacRepo;
    @Autowired
    private EuCapaRepository capaRepo;
    @Autowired
    private EuCapaTsRepository capaTsRepo;
    @Autowired
    private EuTransfertNnRepository transfertDao;
    @Autowired
    private EuActeurRepository acteurDao;

    SimpleDateFormat formatter = null;

    @Override
    public String collect_nn(Collect collect) {
        String reponse = "";
        Date date = new Date();
        String codeCompteNN = "";
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        if (collect != null
                && collect.getCode_compte_nn().startsWith("NN-TSCAPA")) {
            if (!collect.getCode_compte_nn().isEmpty()
                    && collect.getMont_nn() != null) {
                codeCompteNN = collect.getCode_compte_nn();
                EuCompte compteNn = compteRepo.findOne(codeCompteNN);
                if (compteNn != null) {
                    System.out.println("Solde du compte NN" + compteNn.getSolde());
                    if (compteNn.getSolde() >= collect.getMont_nn()) {
                        List<EuCapaTs> capas = capaTsRepo
                                .findCapaTsByCompteType(codeCompteNN,
                                        collect.getType_nn());
                        Double solde_nn = capaTsRepo.getSumCapaTsByCompteType(
                                codeCompteNN, collect.getType_nn());
                        System.out.println("Type NN PRE :"
                                + collect.getType_nn());
                        if (capas.size() > 0
                                && solde_nn >= collect.getMont_nn()) {
                            EuAppelOffre offre = offreRepo
                                    .findAppelOffresByNumero(collect
                                            .getNum_appel());
                            if (offre != null) {
                                EuAppelNn appel_nn = appelRepo
                                        .findAppelNnByNumeroOffre(collect
                                                .getNum_appel());
                                if (appel_nn != null) {
                                    if (appel_nn.getDateFin().after(date)) {
                                        Double somme_nn_col = detailAppelRepo
                                                .sumMontNnCollectByAppelNn(appel_nn
                                                        .getIdAppelNn());
                                        if (!somme_nn_col.equals(Double.NaN)
                                                && Objects.equals(somme_nn_col,
                                                        appel_nn.getCumulNn())
                                                && (appel_nn.getMontantNn() > appel_nn
                                                .getCumulNn())) {
                                            double mont_apport = collect
                                                    .getMont_nn();
                                            double reste = appel_nn
                                                    .getMontantNn()
                                                    - appel_nn.getCumulNn();
                                            if (mont_apport > reste) {
                                                mont_apport = reste;
                                            }
                                            appel_nn.setCumulNn(appel_nn
                                                    .getCumulNn() + mont_apport);
                                            appelRepo.save(appel_nn);

                                            Long idDetailAppelNn = detailAppelRepo
                                                    .findLastDetailAppelNnInsertId();
                                            if (idDetailAppelNn != null) {
                                                idDetailAppelNn += 1;
                                            } else {
                                                idDetailAppelNn = 1L;
                                            }
                                            EuDetailAppelNn detAppelNn = new EuDetailAppelNn();
                                            detAppelNn.setEuAppelNn(appel_nn);
                                            detAppelNn
                                                    .setCodeCompte("NN-CAPA-"
                                                            + ServerUtil
                                                            .getCodeMembre(collect
                                                                    .getCode_compte_nn()));
                                            detAppelNn
                                                    .setCodeMembre(ServerUtil
                                                            .getCodeMembre(collect
                                                                    .getCode_compte_nn()));
                                            detAppelNn.setDateApport(date);
                                            detAppelNn.setHeureApport(date);
                                            detAppelNn.setPayer(0);
                                            detAppelNn
                                                    .setMontantApport(mont_apport);
                                            detAppelNn
                                                    .setIdDetailAppelNn(idDetailAppelNn);
                                            detailAppelRepo.save(detAppelNn);

                                            compteNn.setSolde(compteNn
                                                    .getSolde() - mont_apport);
                                            compteRepo.save(compteNn);

                                            double mont_deduire = mont_apport;
                                            int i = 0;
                                            while (mont_deduire > 0) {
                                                EuCapaTs capa = capas.get(i);
                                                if (mont_deduire > capa
                                                        .getMontantSolde()) {
                                                    System.out.println("cas 1");
                                                    mont_deduire -= capa
                                                            .getMontantSolde();

                                                    capa.setMontantUtiliser(capa
                                                            .getMontantUtiliser()
                                                            + capa.getMontantSolde());
                                                    capa.setMontantSolde(0.0);
                                                    capaTsRepo.save(capa);
                                                    i++;

                                                } else {
                                                    System.out.println("cas 2");
                                                    capa.setMontantUtiliser(capa
                                                            .getMontantUtiliser()
                                                            + mont_deduire);
                                                    capa.setMontantSolde(capa
                                                            .getMontantSolde()
                                                            - mont_deduire);
                                                    capaTsRepo.save(capa);

                                                    mont_deduire = 0;
                                                }
                                            }
                                            EuCompte compteNnDest = compteRepo
                                                    .findOne(appel_nn
                                                            .getCodeCompte());
                                            if (compteNnDest != null) {
                                                compteNnDest
                                                        .setSolde(compteNnDest
                                                                .getSolde()
                                                                + mont_apport);
                                                compteRepo.save(compteNnDest);

                                                EuCapa eucapa = new EuCapa();

                                                eucapa.setCodeCapa("CAPAI"
                                                        + formatter
                                                        .format(date));
                                                eucapa.setDateCapa(date);
                                                eucapa.setCodeMembre(compteNnDest
                                                        .getEuMembreMorale()
                                                        .getCodeMembreMorale());
                                                eucapa.setCodeProduit("InrPRE");
                                                eucapa.setTypeCapa("I");
                                                eucapa.setOrigineCapa("NN");
                                                eucapa.setMontantCapa(collect
                                                        .getMont_nn());
                                                eucapa.setMontantUtiliser(0);
                                                eucapa.setMontantSolde(mont_apport);
                                                eucapa.setEuCompte(compteNnDest);
                                                eucapa.setEtatCapa("Actif");
                                                eucapa.setIdOperation(null);
                                                eucapa.setHeureCapa(date);
                                                capaRepo.save(eucapa);
                                            }
                                            reponse = "L'opération a été effectuée avec succès";
                                        } else {
                                            reponse = "Le montant de l'appel NN pour cette offre est atteint!!";
                                        }
                                    } else {
                                        reponse = "L'appel NN pour cette offre est clôturé";
                                    }
                                } else {
                                    EuProposition proposition = propositionRepo
                                            .findPropositionByNumeroAppelOffre(offre
                                                    .getIdAppelOffre());
                                    if (proposition != null
                                            && proposition.getDisponible() == 1) {
                                        EuFrais frais = fraisRepo
                                                .findByIdProposition(proposition
                                                        .getIdProposition());
                                        if (frais != null
                                                && frais.getDisponible() == 1) {
                                            double mont_nn_collect = Math
                                                    .ceil((frais
                                                            .getMontProjet() * 5.6 / offre
                                                            .getDureeProjet()
                                                            .intValue()));
                                            if (collect.getMont_nn() <= mont_nn_collect) {
                                                EuGac gac = gacRepo.findByCodeGac(frais.getCodeGac());
                                                if (gac != null && gac.getEuTypeGac().getCodeTypeGac().equals("gac_surveillance")) {
                                                    Long idAppelNn = appelRepo
                                                            .findLastAppelNnInsertId();
                                                    if (idAppelNn != null) {
                                                        idAppelNn += 1;
                                                    } else {
                                                        idAppelNn = 1L;
                                                    }
                                                    appel_nn = new EuAppelNn();
                                                    appel_nn.setIdAppelNn(idAppelNn);
                                                    appel_nn.setAllouer(0);
                                                    appel_nn.setCodeMembreMorale(gac
                                                            .getEuMembreMorale()
                                                            .getCodeMembreMorale());
                                                    appel_nn.setCodeCompte("NN-CAPA-"
                                                            + gac.getEuMembreMorale()
                                                            .getCodeMembreMorale());
                                                    appel_nn.setCumulNn(collect
                                                            .getMont_nn());
                                                    appel_nn.setMontantNn(mont_nn_collect);
                                                    appel_nn.setDateAppel(date);
                                                    appel_nn.setDateFin(ServerUtil
                                                            .ajouterJours(date,
                                                                    30));
                                                    appel_nn.setEuProposition(proposition);
                                                    appel_nn.setDisponible(0);
                                                    appel_nn.setDesignationAppel("Collecte NN "
                                                            + offre.getNumeroOffre());
                                                    appelRepo.save(appel_nn);

                                                    compteNn.setSolde(compteNn
                                                            .getSolde()
                                                            - collect
                                                            .getMont_nn());
                                                    compteRepo.save(compteNn);

                                                    Long idDetailAppelNn = detailAppelRepo
                                                            .findLastDetailAppelNnInsertId();
                                                    if (idDetailAppelNn != null) {
                                                        idDetailAppelNn += 1;
                                                    } else {
                                                        idDetailAppelNn = 1L;
                                                    }
                                                    EuDetailAppelNn detAppelNn = new EuDetailAppelNn();
                                                    detAppelNn
                                                            .setEuAppelNn(appel_nn);
                                                    detAppelNn
                                                            .setCodeCompte("NN-CAPA-"
                                                                    + ServerUtil
                                                                    .getCodeMembre(collect
                                                                            .getCode_compte_nn()));
                                                    detAppelNn
                                                            .setCodeMembre(ServerUtil
                                                                    .getCodeMembre(collect
                                                                            .getCode_compte_nn()));
                                                    detAppelNn
                                                            .setDateApport(date);
                                                    detAppelNn
                                                            .setHeureApport(date);
                                                    detAppelNn.setPayer(0);
                                                    detAppelNn
                                                            .setMontantApport(collect
                                                                    .getMont_nn());
                                                    detAppelNn
                                                            .setIdDetailAppelNn(idDetailAppelNn);
                                                    detailAppelRepo
                                                            .save(detAppelNn);

                                                    double mont_deduire = collect
                                                            .getMont_nn();
                                                    int i = 0;
                                                    while (mont_deduire > 0) {
                                                        EuCapaTs capa = capas
                                                                .get(i);
                                                        if (mont_deduire > capa
                                                                .getMontantSolde()) {
                                                            System.out
                                                                    .println("cas 1");
                                                            mont_deduire -= capa
                                                                    .getMontantSolde();

                                                            capa.setMontantUtiliser(capa
                                                                    .getMontantUtiliser()
                                                                    + capa.getMontantSolde());
                                                            capa.setMontantSolde(0.0);
                                                            capaTsRepo
                                                                    .save(capa);
                                                            i++;

                                                        } else {
                                                            System.out
                                                                    .println("cas 2");
                                                            capa.setMontantUtiliser(capa
                                                                    .getMontantUtiliser()
                                                                    + mont_deduire);
                                                            capa.setMontantSolde(capa
                                                                    .getMontantSolde()
                                                                    - mont_deduire);
                                                            capaTsRepo
                                                                    .save(capa);

                                                            mont_deduire = 0;
                                                        }
                                                    }
                                                    EuCompte compteNnDest = compteRepo
                                                            .findOne(appel_nn
                                                                    .getCodeCompte());
                                                    if (compteNnDest != null) {
                                                        compteNnDest
                                                                .setSolde(compteNnDest
                                                                        .getSolde()
                                                                        + collect
                                                                        .getMont_nn());
                                                        compteRepo
                                                                .save(compteNnDest);

                                                    } else {
                                                        compteNnDest = new EuCompte();
                                                        EuCategorieCompte cat = new EuCategorieCompte();
                                                        cat.setCodeCat("CAPA");
                                                        EuTypeCompte typeCompte = new EuTypeCompte();
                                                        typeCompte
                                                                .setCodeTypeCompte("NN");
                                                        compteNnDest
                                                                .setCodeCompte(appel_nn
                                                                        .getCodeCompte());
                                                        compteNnDest
                                                                .setEuCategorieCompte(cat);
                                                        compteNnDest
                                                                .setEuTypeCompte(typeCompte);
                                                        compteNnDest
                                                                .setEuMembreMorale(gac
                                                                        .getEuMembreMorale());
                                                        compteNnDest
                                                                .setEuMembre(null);

                                                        compteNnDest
                                                                .setDesactiver("N");
                                                        compteNnDest
                                                                .setSolde(collect
                                                                        .getMont_nn());
                                                        compteNnDest
                                                                .setDateAlloc(date);
                                                    }
                                                    EuCapa eucapa = new EuCapa();

                                                    eucapa.setCodeCapa("CAPAI"
                                                            + formatter
                                                            .format(date));
                                                    eucapa.setDateCapa(date);
                                                    eucapa.setCodeMembre(compteNnDest
                                                            .getEuMembreMorale()
                                                            .getCodeMembreMorale());
                                                    eucapa.setCodeProduit("InrPRE");
                                                    eucapa.setTypeCapa("I");
                                                    eucapa.setOrigineCapa("NN");
                                                    eucapa.setMontantCapa(collect
                                                            .getMont_nn());
                                                    eucapa.setMontantUtiliser(0);
                                                    eucapa.setMontantSolde(collect
                                                            .getMont_nn());
                                                    eucapa.setEuCompte(compteNnDest);
                                                    eucapa.setEtatCapa("Actif");
                                                    eucapa.setIdOperation(null);
                                                    eucapa.setHeureCapa(date);
                                                    capaRepo.save(eucapa);
                                                    reponse = "L'op�ration a �t� effectu�e avec succ�s";
                                                } else {
                                                    reponse = "La GAC n'existe pas ou n'est pas une GAC Surveillance";
                                                }
                                            } else {
                                                reponse = "Ce montant est superieur au montant du collect";
                                            }
                                        } else {
                                            reponse = "Cette offre n'est pas encore r�gl�e";
                                        }
                                    } else {
                                        reponse = "Il n'y a pas de proposition valide pour cette offre!!";
                                    }
                                }
                            } else {
                                reponse = "L'offre que vous demandez n'existe pas!!!";
                            }
                        } else {
                            reponse = "Le solde de votre compte "
                                    + collect.getType_nn()
                                    + " est insuffisant pour effectuer cette opération!!!";
                        }
                    } else {
                        reponse = "Le solde de votre compte est insuffisant pour effectuer cette opération!!!";
                    }
                } else {
                    reponse = "Ce compte n'existe pas!!!";
                }
            } else {
                reponse = "Veuillez renseigner le code compte et le montant!!!";
            }
        } else {
            reponse = "Veuillez renseigner les infos!!!";
        }
        return reponse;
    }

    @Override
    public String collect_nn_det(Collect collect) {
        String reponse = "";
        Date date = new Date();
        String codeCompteNN = "";
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        if (collect != null && collect.getCode_compte_nn().startsWith("NN-TR")) {
            codeCompteNN = collect.getCode_compte_nn();
            EuCompte compteNn = compteRepo.findOne(codeCompteNN);
            if (compteNn != null) {
                EuActeur acteur_a = acteurDao.findByCodeMembre(compteNn
                        .getEuMembreMorale().getCodeMembreMorale());
                if (!acteur_a.getTypeActeur().equals("gac_detentrice")) {
                    return "Seule la GAC D�tentrice peut completer une collecte par un compte de transfert!";
                }
                if (!collect.getNum_appel().isEmpty()) {
                    System.out.println("Type NN PRE :" + collect.getType_nn());
                    List<EuTransfertNn> capas = transfertDao
                            .findByTypeNnAndCompte(codeCompteNN,
                                    collect.getType_nn());
                    Double solde_nn = transfertDao.FindSoldeNnByTypeNn(
                            codeCompteNN, collect.getType_nn());
                    EuAppelOffre offre = offreRepo
                            .findAppelOffresByNumero(collect.getNum_appel());
                    if (offre != null
                            && offre.getTypeAppelOffre().equalsIgnoreCase(
                                    "inrpre")) {
                        EuAppelNn appel_nn = appelRepo
                                .findAppelNnByNumeroOffre(collect
                                        .getNum_appel());
                        if (appel_nn != null) {
                            double reste_nn = appel_nn.getMontantNn()
                                    - appel_nn.getCumulNn();
                            if (capas.size() > 0 && solde_nn >= reste_nn) {
                                if (compteNn.getSolde() >= reste_nn) {
                                    Double somme_nn_col = detailAppelRepo
                                            .sumMontNnCollectByAppelNn(appel_nn
                                                    .getIdAppelNn());
                                    if (somme_nn_col.equals(Double.NaN)
                                            || (Objects.equals(somme_nn_col,
                                                    appel_nn.getCumulNn()) && (appel_nn
                                            .getMontantNn() > appel_nn
                                            .getCumulNn()))) {
                                        double mont_apport = reste_nn;
                                        appel_nn.setCumulNn(appel_nn
                                                .getCumulNn() + mont_apport);
                                        appelRepo.save(appel_nn);

                                        Long idDetailAppelNn = detailAppelRepo
                                                .findLastDetailAppelNnInsertId();
                                        if (idDetailAppelNn != null) {
                                            idDetailAppelNn += 1;
                                        } else {
                                            idDetailAppelNn = 1L;
                                        }
                                        EuDetailAppelNn detAppelNn = new EuDetailAppelNn();
                                        detAppelNn.setEuAppelNn(appel_nn);
                                        detAppelNn
                                                .setCodeCompte("NN-CAPA-"
                                                        + ServerUtil
                                                        .getCodeMembre(collect
                                                                .getCode_compte_nn()));
                                        detAppelNn.setCodeMembre(ServerUtil
                                                .getCodeMembre(collect
                                                        .getCode_compte_nn()));
                                        detAppelNn.setDateApport(date);
                                        detAppelNn.setHeureApport(date);
                                        detAppelNn.setPayer(0);
                                        detAppelNn
                                                .setMontantApport(mont_apport);
                                        detAppelNn
                                                .setIdDetailAppelNn(idDetailAppelNn);
                                        detailAppelRepo.save(detAppelNn);

                                        compteNn.setSolde(compteNn.getSolde()
                                                - mont_apport);
                                        compteRepo.save(compteNn);

                                        double mont_deduire = mont_apport;
                                        int i = 0;
                                        while (mont_deduire > 0) {
                                            EuTransfertNn capa = capas.get(i);
                                            if (mont_deduire > capa
                                                    .getSoldeTransfert()) {
                                                System.out.println("cas 1");
                                                mont_deduire -= capa
                                                        .getSoldeTransfert();

                                                capa.setMontVendu(capa
                                                        .getMontVendu() + capa
                                                        .getSoldeTransfert());
                                                capa.setSoldeTransfert(0);
                                                transfertDao.save(capa);
                                                i++;

                                            } else {
                                                System.out.println("cas 2");
                                                capa.setMontVendu(capa
                                                        .getMontVendu() + mont_deduire);
                                                capa.setSoldeTransfert(capa
                                                        .getSoldeTransfert() - mont_deduire);
                                                transfertDao.save(capa);

                                                mont_deduire = 0;
                                            }
                                        }
                                        EuCompte compteNnDest = compteRepo
                                                .findOne(appel_nn
                                                        .getCodeCompte());
                                        if (compteNnDest != null) {
                                            compteNnDest.setSolde(compteNnDest
                                                    .getSolde() + mont_apport);
                                            compteRepo.save(compteNnDest);

                                            EuCapa eucapa = new EuCapa();

                                            eucapa.setCodeCapa("CAPAI"
                                                    + formatter.format(date));
                                            eucapa.setDateCapa(date);
                                            eucapa.setCodeMembre(compteNnDest
                                                    .getEuMembreMorale()
                                                    .getCodeMembreMorale());
                                            eucapa.setCodeProduit("InrPRE");
                                            eucapa.setTypeCapa("I");
                                            eucapa.setOrigineCapa("NN");
                                            eucapa.setMontantCapa(reste_nn);
                                            eucapa.setMontantUtiliser(0);
                                            eucapa.setMontantSolde(mont_apport);
                                            eucapa.setEuCompte(compteNnDest);
                                            eucapa.setEtatCapa("Actif");
                                            eucapa.setIdOperation(null);
                                            eucapa.setHeureCapa(date);
                                            capaRepo.save(eucapa);
                                        }
                                        reponse = "L'op�ration a �t� effectu�e avec succ�s";
                                    } else {
                                        reponse = "Le montant de l'appel NN pour cette offre est atteint!!";
                                    }
                                } else {
                                    reponse = "L'appel NN pour cette offre est cl�tur�e";
                                }
                            } else {
                                reponse = "Le solde de votre compte "
                                        + collect.getType_nn()
                                        + " est insuffisant pour effectuer cette op�ration!!!";
                            }
                        } else {
                            EuProposition proposition = propositionRepo
                                    .findPropositionByNumeroAppelOffre(offre
                                            .getIdAppelOffre());
                            if (proposition != null
                                    && proposition.getDisponible() == 1) {
                                EuFrais frais = fraisRepo
                                        .findByIdProposition(proposition
                                                .getIdProposition());
                                if (frais != null && frais.getDisponible() == 1) {
                                    double mont_nn_collect = Math.ceil((frais
                                            .getMontProjet() * 5.6 / offre
                                            .getDureeProjet()));
                                    EuGac gac = gacRepo.findByCodeGac(frais
                                            .getCodeGac());
                                    if (gac != null
                                            && gac.getEuTypeGac()
                                            .getCodeTypeGac()
                                            .equals("gac_surveillance")) {
                                        Long idAppelNn = appelRepo
                                                .findLastAppelNnInsertId();
                                        if (idAppelNn != null) {
                                            idAppelNn += 1;
                                        } else {
                                            idAppelNn = 1L;
                                        }
                                        appel_nn = new EuAppelNn();
                                        appel_nn.setIdAppelNn(idAppelNn);
                                        appel_nn.setAllouer(0);
                                        appel_nn.setCodeMembreMorale(gac
                                                .getEuMembreMorale()
                                                .getCodeMembreMorale());
                                        appel_nn.setCodeCompte("NN-CAPA-"
                                                + gac.getEuMembreMorale()
                                                .getCodeMembreMorale());
                                        appel_nn.setCumulNn(mont_nn_collect);
                                        appel_nn.setMontantNn(mont_nn_collect);
                                        appel_nn.setDateAppel(date);
                                        appel_nn.setDateFin(ServerUtil
                                                .ajouterJours(date, 30));
                                        appel_nn.setEuProposition(proposition);
                                        appel_nn.setDisponible(1);
                                        appel_nn.setDesignationAppel("Collecte NN "
                                                + offre.getNumeroOffre());
                                        appelRepo.save(appel_nn);

                                        compteNn.setSolde(compteNn.getSolde()
                                                - mont_nn_collect);
                                        compteRepo.save(compteNn);

                                        Long idDetailAppelNn = detailAppelRepo
                                                .findLastDetailAppelNnInsertId();
                                        if (idDetailAppelNn != null) {
                                            idDetailAppelNn += 1;
                                        } else {
                                            idDetailAppelNn = 1L;
                                        }
                                        EuDetailAppelNn detAppelNn = new EuDetailAppelNn();
                                        detAppelNn.setEuAppelNn(appel_nn);
                                        detAppelNn
                                                .setCodeCompte("NN-CAPA-"
                                                        + ServerUtil
                                                        .getCodeMembre(collect
                                                                .getCode_compte_nn()));
                                        detAppelNn.setCodeMembre(ServerUtil
                                                .getCodeMembre(collect
                                                        .getCode_compte_nn()));
                                        detAppelNn.setDateApport(date);
                                        detAppelNn.setHeureApport(date);
                                        detAppelNn.setPayer(0);
                                        detAppelNn
                                                .setMontantApport(mont_nn_collect);
                                        detAppelNn
                                                .setIdDetailAppelNn(idDetailAppelNn);
                                        detailAppelRepo.save(detAppelNn);

                                        double mont_deduire = mont_nn_collect;
                                        int i = 0;
                                        while (mont_deduire > 0) {
                                            EuTransfertNn capa = capas.get(i);
                                            if (mont_deduire > capa
                                                    .getSoldeTransfert()) {
                                                System.out.println("cas 1");
                                                mont_deduire -= capa
                                                        .getSoldeTransfert();

                                                capa.setMontVendu(capa
                                                        .getMontVendu() + capa
                                                        .getSoldeTransfert());
                                                capa.setSoldeTransfert(0);
                                                transfertDao.save(capa);
                                                i++;

                                            } else {
                                                System.out.println("cas 2");
                                                capa.setMontVendu(capa
                                                        .getMontVendu() + mont_deduire);
                                                capa.setSoldeTransfert(capa
                                                        .getSoldeTransfert() - mont_deduire);
                                                transfertDao.save(capa);

                                                mont_deduire = 0;
                                            }
                                        }
                                        EuCompte compteNnDest = compteRepo
                                                .findOne(appel_nn
                                                        .getCodeCompte());
                                        if (compteNnDest != null) {
                                            compteNnDest.setSolde(compteNnDest
                                                    .getSolde()
                                                    + mont_nn_collect);
                                            compteRepo.save(compteNnDest);

                                        } else {
                                            compteNnDest = new EuCompte();
                                            EuCategorieCompte cat = new EuCategorieCompte();
                                            cat.setCodeCat("CAPA");
                                            EuTypeCompte typeCompte = new EuTypeCompte();
                                            typeCompte.setCodeTypeCompte("NN");
                                            compteNnDest.setCodeCompte(appel_nn
                                                    .getCodeCompte());
                                            compteNnDest
                                                    .setEuCategorieCompte(cat);
                                            compteNnDest
                                                    .setEuTypeCompte(typeCompte);
                                            compteNnDest.setEuMembreMorale(gac
                                                    .getEuMembreMorale());
                                            compteNnDest.setEuMembre(null);

                                            compteNnDest.setDesactiver("N");
                                            compteNnDest
                                                    .setSolde(mont_nn_collect);
                                            compteNnDest.setDateAlloc(date);
                                        }
                                        EuCapa eucapa = new EuCapa();

                                        eucapa.setCodeCapa("CAPAI"
                                                + formatter.format(date));
                                        eucapa.setDateCapa(date);
                                        eucapa.setCodeMembre(compteNnDest
                                                .getEuMembreMorale()
                                                .getCodeMembreMorale());
                                        eucapa.setCodeProduit("InrPRE");
                                        eucapa.setTypeCapa("I");
                                        eucapa.setOrigineCapa("NN");
                                        eucapa.setMontantCapa(mont_nn_collect);
                                        eucapa.setMontantUtiliser(0);
                                        eucapa.setMontantSolde(mont_nn_collect);
                                        eucapa.setEuCompte(compteNnDest);
                                        eucapa.setEtatCapa("Actif");
                                        eucapa.setIdOperation(null);
                                        eucapa.setHeureCapa(date);
                                        capaRepo.save(eucapa);
                                        reponse = "L'op�ration a �t� effectu�e avec succ�s";
                                    } else {
                                        reponse = "La GAC n'existe pas ou n'est pas une GAC Surveillance";
                                    }
                                } else {
                                    reponse = "Cette offre n'est pas encore r�gl�e";
                                }
                            } else {
                                reponse = "Il n'y a pas de proposition valide pour cette offre!!";
                            }
                        }
                    } else {
                        reponse = "L'offre que vous demandez n'existe pas ou n'est pas une offre INRPRE!!!";
                    }
                } else {
                    reponse = "Veuillez renseigner le num�ro d'appel d'offre!!!";
                }
            } else {
                reponse = "Le Compte NN n'existe pas!!!";
            }
        } else {
            reponse = "Veuillez renseigner le code compte et le type NN!!!";
        }
        return reponse;
    }

}
