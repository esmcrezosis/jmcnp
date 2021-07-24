/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.model.bc.EuCnpEntree;
import com.esmc.mcnp.repositories.bc.EuCnpEntreeRepository;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.Apa;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.model.mprg.EuDetailKrr;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.obpsd.EuUtiliserNn;
import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.smcipn.EuServir;
import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.model.smcipn.EuUtiliser;
import com.esmc.mcnp.repositories.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.common.EuParametreRepository;
import com.esmc.mcnp.repositories.mprg.EuDetailKrrRepository;
import com.esmc.mcnp.repositories.mprg.EuKrrRepository;
import com.esmc.mcnp.repositories.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.repositories.ba.EuCapaRepository;
import com.esmc.mcnp.repositories.ba.EuCapaTsRepository;
import com.esmc.mcnp.repositories.bc.EuCnpRepository;
import com.esmc.mcnp.repositories.smcipn.EuFnRepository;
import com.esmc.mcnp.repositories.smcipn.EuServirRepository;
import com.esmc.mcnp.repositories.smcipn.EuSmcRepository;
import com.esmc.mcnp.repositories.smcipn.EuUtiliserRepository;
import com.esmc.mcnp.services.obpsd.EuNnService;

/**
 * @author USER
 */
@Component
@Transactional
public class CreditComponent {

    private final EuCompteCreditRepository ccreditRepo;
    private final EuCnpRepository cnpRepo;
    private final EuCnpEntreeRepository cnpEntreeRepository;
    private final EuCompteCreditCapaRepository ccCapaRepo;
    private final EuFnRepository fnRepo;
    private final EuCapaRepository capaRepo;
    private final EuCapaTsRepository capaTsRepo;
    private final EuUtiliserRepository utiliserRepo;
    private final EuUtiliserNnRepository utiliserNnRepo;
    private final EuNnService nnService;
    private final EuSmcRepository smcRepo;
    private final EuCompteCreditRepository creditRepo;
    private final EuCompteRepository compteRepo;
    private final EuKrrRepository krrRepo;
    private final EuDetailKrrRepository detKrrRepo;
    private final CreditUtility creditUtility;
    private final EuParametreRepository paramRepo;
    private final EuServirRepository servirRepo;

    private final Logger logger = LogManager.getLogger(CreditComponent.class);

    SimpleDateFormat formatter = null;

    public CreditComponent(EuCompteCreditRepository ccreditRepo, EuCnpRepository cnpRepo,
                           EuCnpEntreeRepository cnpEntreeRepository, EuCompteCreditCapaRepository ccCapaRepo, EuFnRepository fnRepo,
                           EuCapaRepository capaRepo, EuCapaTsRepository capaTsRepo, EuUtiliserRepository utiliserRepo,
                           EuSmcRepository smcRepo, EuCompteCreditRepository creditRepo, EuCompteRepository compteRepo,
                           EuDetailKrrRepository detKrrRepo, CreditUtility creditUtility, EuParametreRepository paramRepo,
                           EuServirRepository servirRepo, EuUtiliserNnRepository utiliserNnRepo, EuNnService nnService, EuKrrRepository krrRepo) {
        this.ccreditRepo = ccreditRepo;
        this.cnpRepo = cnpRepo;
        this.cnpEntreeRepository = cnpEntreeRepository;
        this.ccCapaRepo = ccCapaRepo;
        this.fnRepo = fnRepo;
        this.capaRepo = capaRepo;
        this.capaTsRepo = capaTsRepo;
        this.utiliserRepo = utiliserRepo;
        this.utiliserNnRepo = utiliserNnRepo;
        this.nnService = nnService;
        this.smcRepo = smcRepo;
        this.creditRepo = creditRepo;
        this.compteRepo = compteRepo;
        this.krrRepo = krrRepo;
        this.detKrrRepo = detKrrRepo;
        this.creditUtility = creditUtility;
        this.paramRepo = paramRepo;
        this.servirRepo = servirRepo;
    }

    public List<EuCompteCredit> findListCreditByCompte(String codeCompte) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<EuCompteCredit> findListCreditByCompteAndProduit(String codeCompte, String codeProduit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EuCompteCredit createCredit(EuCompte compte, EuProduit produit, double montant, double credit, boolean isBnp,
                                       String codeBnp, EuOperation op, double prk, String compteSource, Apa apa) {
        try {
            Date date = new Date();
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            EuCompteCredit cc = new EuCompteCredit();
            Long idCredit = ccreditRepo.getLastCreditInsertedId();
            if (idCredit == null) {
                idCredit = 1L;
            } else {
                idCredit += 1;
            }
            cc.setIdCredit(idCredit);
            cc.setCodeMembre(apa.getCodeMembre());
            cc.setMontantPlace(montant);
            cc.setMontantCredit(credit);
            cc.setEuOperation(op);
            cc.setEuProduit(produit);
            cc.setCodeTypeCredit(null);
            cc.setSource(apa.getCodeMembre() + formatter.format(date));
            cc.setCompteSource(compteSource);
            cc.setDateOctroi(date);
            cc.setDatedeb(date);
            Date datefin = ServerUtil.ajouterJours(date, 30);
            cc.setDatefin(datefin);
            if (apa.getCatBon().equals("nr")) {
                cc.setRenouveller("N");
                cc.setNbreRenouvel(0);
            } else {
                cc.setRenouveller("O");
                if (produit.getCodeProduit().equals("Ir") || produit.getCodeProduit().endsWith("nrPRE")) {
                    cc.setNbreRenouvel(apa.getDureeInvest());
                } else {
                    cc.setNbreRenouvel(-1);
                }
            }
            if (isBnp) {
                cc.setBnp(1);
            } else {
                cc.setBnp(0);
            }
            cc.setKrr("N");
            cc.setEuCompte(compte);
            cc.setDomicilier(0);
            cc.setPrk(prk);
            cc.setAffecter(1);
            cc.setCodeBnp(codeBnp);
            ccreditRepo.saveAndFlush(cc);

            System.out.println("Jusqu'ici tout va bien 0 : creation du CNP");
            Long idCnp = cnpRepo.getLastCnpInsertedId();
            if (idCnp == null) {
                idCnp = 1L;
            } else {
                idCnp += 1;
            }
            double creditTot = creditUtility.calculBcStandard(montant);
            EuCnp cnp = new EuCnp();
            cnp.setDateCnp(date);
            cnp.setEuCapa(null);
            cnp.setEuDomiciliation(null);
            cnp.setTypeCnp(produit.getCodeProduit());
            cnp.setSourceCredit(cc.getSource());
            cnp.setOrigineCnp("FG" + produit.getCodeProduit());
            cnp.setMontDebit(creditTot);
            cnp.setMontCredit(0);
            cnp.setSoldeCnp(creditTot);
            cnp.setTransfertGcp(0);
            cnp.setEuCompteCredit(cc);
            cnp.setIdCnp(idCnp);
            cnpRepo.save(cnp);
            return cc;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de céation de compte credit", e);
            return null;
        }
    }

    public EuCompteCredit createCredit(EuCompte compte, EuProduit produit, double montant, double credit, boolean isBnp,
                                       String codeBnp, EuOperation op, String compteSource, String codeMembre, CalculBonInfo bonInfo)
            throws DataAccessException, NullPointerException {
        try {
            Date date = new Date();
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            int periode = paramRepo.findByCodeAndLib("periode", "valeur").intValue();
            EuCompteCredit cc = new EuCompteCredit();
            cc.setCodeMembre(codeMembre);
            cc.setMontantPlace(montant);
            cc.setMontantCredit(credit);
            cc.setEuOperation(op);
            cc.setEuProduit(produit);
            cc.setCodeTypeCredit(null);
            cc.setSource(codeMembre + formatter.format(date));
            cc.setCompteSource(compteSource);
            cc.setDateOctroi(date);
            cc.setDatedeb(date);
            if (bonInfo.getCatBon().endsWith("nr")) {
                cc.setRenouveller("N");
                cc.setNbreRenouvel(((int) Math.ceil(bonInfo.getPrk())) - 1);
                cc.setPrk(bonInfo.getPrk());
                cc.setDuree(bonInfo.getPrk());
                cc.setTypeProduit(bonInfo.getTypeProduit());
                cc.setDatefin(ServerUtil.ajouterJours(date, 90));
            } else {
                if (bonInfo.getDuree() == 1) {
                    if (bonInfo.getFrequenceCumul() > 1) {
                        cc.setDatefin(ServerUtil.ajouterJours(date, bonInfo.getFrequenceCumul()));
                    } else {
                        cc.setDatefin(ServerUtil.ajouterJours(date, 1));
                    }
                } else {
                    cc.setDatefin(ServerUtil.ajouterJours(date, 30));
                }
                cc.setRenouveller("O");
                if ("limite".equals(bonInfo.getTypeRecurrent())) {
                    cc.setNbreRenouvel(((int) Math.ceil(bonInfo.getDuree())) - 1);
                    if (bonInfo.getDuree() == 1) {
                        cc.setFrequenceCumul(bonInfo.getFrequenceCumul());
                        cc.setNbreRenouvel(periode - 1);
                    }
                } else {
                    cc.setNbreRenouvel(paramRepo.findByCodeAndLib("periode", "RPGr").intValue() - 1);
                }
                cc.setPrk(0.0);
                cc.setTypeProduit(bonInfo.getTypeProduit());
                cc.setTypeRecurrent(bonInfo.getTypeRecurrent());
                cc.setDuree(bonInfo.getDuree());
            }
            if (isBnp) {
                cc.setBnp(1);
            } else {
                cc.setBnp(0);
            }
            cc.setFrequenceCumul(0);
            cc.setKrr("N");
            cc.setEuCompte(compte);
            cc.setDomicilier(0);
            cc.setAffecter(0);
            cc.setCodeBnp(codeBnp);
            cc = ccreditRepo.save(cc);

            EuCnp cnp = new EuCnp();
            cnp.setDateCnp(date);
            cnp.setEuCapa(null);
            cnp.setEuDomiciliation(null);
            cnp.setTypeCnp(produit.getCodeProduit());
            cnp.setSourceCredit(cc.getSource());
            cnp.setOrigineCnp("FG" + produit.getCodeProduit());
            cnp.setMontDebit(credit);
            cnp.setMontCredit(0);
            cnp.setSoldeCnp(credit);
            cnp.setTransfertGcp(0);
            cnp.setEuCompteCredit(cc);
            cnpRepo.save(cnp);
            return cc;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de céation de compte credit", e);
            return null;
        }
    }

    public EuCompteCredit createCreditPre(EuCompte compte, EuProduit produit, double montant, double credit,
                                          boolean isBnp, String codeBnp, EuOperation op, double prk, String compteSource, Apa apa) {
        try {
            Date date = new Date();
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            EuCompteCredit cc = new EuCompteCredit();
            cc.setCodeMembre(apa.getCodeMembre());
            cc.setMontantPlace(montant);
            cc.setMontantCredit(0.0);
            cc.setEuOperation(op);
            cc.setEuProduit(produit);
            cc.setCodeTypeCredit("CNPG");
            cc.setSource(apa.getCodeMembre() + formatter.format(date));
            cc.setCompteSource(compteSource);
            cc.setDateOctroi(date);
            cc.setDatedeb(date);
            Date datefin = ServerUtil.ajouterJours(date, 30);
            cc.setDatefin(datefin);
            if (produit.getCodeProduit().endsWith("nr")) {
                cc.setRenouveller("N");
            } else {
                cc.setRenouveller("O");
            }
            cc.setNbreRenouvel(Double.valueOf(prk).intValue());
            cc.setBnp(0);
            cc.setKrr("N");
            cc.setEuCompte(compte);
            cc.setDomicilier(1);
            cc.setPrk(prk);
            cc.setAffecter(0);
            cc.setCodeBnp(codeBnp);
            ccreditRepo.save(cc);

            EuCnp cnp = new EuCnp();
            cnp.setDateCnp(date);
            cnp.setEuCapa(null);
            cnp.setEuDomiciliation(null);
            cnp.setTypeCnp(produit.getCodeProduit());
            cnp.setSourceCredit(cc.getSource());
            cnp.setOrigineCnp("FG" + produit.getCodeProduit());
            cnp.setMontDebit(credit);
            cnp.setMontCredit(credit);
            cnp.setSoldeCnp(0);
            cnp.setTransfertGcp(0);
            cnp.setEuCompteCredit(cc);
            cnpRepo.save(cnp);
            return cc;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de céation de compte credit PRE", e);
            return null;
        }
    }

    public boolean updateListCapa(List<EuCapa> capas, EuCompteCredit cc, double capa, Date date, String typeR) {
        if (!capas.isEmpty()) {
            try {
                double mont_capa = capa;
                int i = 0;
                while (mont_capa > 0) {
                    EuCapa c = capas.get(i);
                    if (mont_capa > c.getMontantSolde()) {
                        EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
                        EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
                        ccCapaPK.setCodeCapa(c.getCodeCapa());
                        ccCapaPK.setIdCredit(cc.getIdCredit());
                        ccCapa.setId(ccCapaPK);
                        ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
                        ccCapa.setMontant(c.getMontantSolde());
                        ccCapaRepo.save(ccCapa);

                        EuFn fn = new EuFn();
                        fn.setTypeFn("I" + typeR);
                        fn.setDateFn(date);
                        fn.setEuCapa(c);
                        fn.setMontant(c.getMontantSolde());
                        fn.setSortie(0);
                        fn.setEntree(0);
                        fn.setSolde(0);
                        fn.setOrigineFn(1);
                        fn.setMtSolde(c.getMontantSolde());
                        fnRepo.save(fn);

                        mont_capa -= c.getMontantSolde();

                        c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
                        c.setMontantSolde(0);
                        capaRepo.save(c);
                        i++;

                    } else {
                        EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
                        EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
                        ccCapaPK.setCodeCapa(c.getCodeCapa());
                        ccCapaPK.setIdCredit(cc.getIdCredit());
                        ccCapa.setId(ccCapaPK);
                        ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
                        ccCapa.setMontant(mont_capa);
                        ccCapaRepo.save(ccCapa);

                        EuFn fn = new EuFn();
                        fn.setTypeFn("I" + typeR);
                        fn.setDateFn(date);
                        fn.setEuCapa(c);
                        fn.setMontant(mont_capa);
                        fn.setSortie(0);
                        fn.setEntree(0);
                        fn.setSolde(0);
                        fn.setMtSolde(mont_capa);
                        fn.setOrigineFn(1);
                        fnRepo.save(fn);

                        c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
                        c.setMontantSolde(c.getMontantSolde() - mont_capa);
                        capaRepo.save(c);

                        mont_capa = 0;
                    }
                }
                return true;
            } catch (Exception e) {
                logger.error("Erreur d'execution  de mise à jour des CAPAs", e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean createCapaCredit(EuCompteCredit cc, double capa, Date date, String typeR) {
        try {
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            EuCapa eucapa = new EuCapa();
            String type = "";
            if (cc.getEuProduit().getCodeProduit().startsWith("RPG")) {
                type = "RPG";
            } else if (cc.getEuProduit().getCodeProduit().startsWith("I")) {
                type = "I";
            } else {
                type = "CNCS";
            }
            String codeCompteCapa = "NN-CAPA" + cc.getCodeMembre();
            EuCompte compteCapa = compteRepo.findCompteById(codeCompteCapa);
            eucapa.setCodeCapa("CAPA" + type + formatter.format(date));
            eucapa.setDateCapa(date);
            eucapa.setCodeMembre(cc.getCodeMembre());
            eucapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
            eucapa.setTypeCapa(type);
            eucapa.setOrigineCapa("NN");
            eucapa.setMontantCapa(capa);
            eucapa.setMontantUtiliser(capa);
            eucapa.setMontantSolde(0);
            eucapa.setEuCompte(compteCapa);
            eucapa.setEtatCapa("Actif");
            eucapa.setIdOperation(cc.getEuOperation().getIdOperation());
            eucapa.setHeureCapa(date);
            capaRepo.save(eucapa);

            EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
            EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
            ccCapaPK.setCodeCapa(eucapa.getCodeCapa());
            ccCapaPK.setIdCredit(cc.getIdCredit());
            ccCapa.setId(ccCapaPK);
            ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
            ccCapa.setMontant(cc.getMontantCredit());
            ccCapaRepo.save(ccCapa);

            EuFn fn = new EuFn();
            fn.setTypeFn("I" + typeR);
            fn.setDateFn(date);
            fn.setEuCapa(eucapa);
            fn.setMontant(capa);
            fn.setSortie(0);
            fn.setEntree(0);
            fn.setSolde(0);
            fn.setMtSolde(capa);
            fn.setOrigineFn(0);
            fn.setCodeDomicilier(null);
            fn.setCodeSmcipn(null);
            fnRepo.save(fn);
            return true;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de céation des CAPAs", e);
            return false;
        }
    }

    public boolean updateListFn(List<EuCompteCredit> credits, EuCompte compteDest, EuOperation op, double montant) {
        Date date = new Date();
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            double mont_credit = montant;
            int i = 0;
            while (mont_credit > 0 && i < credits.size()) {
                EuCompteCredit c = credits.get(i);
                if (mont_credit <= c.getMontantCredit()) {
                    double mont_fn = mont_credit;
                    c.setMontantCredit(c.getMontantCredit() - mont_credit);
                    creditRepo.save(c);
                    List<EuFn> fns = fnRepo.findFnByCodeSmcipn(c.getCompteSource());
                    if (!fns.isEmpty()) {
                        updateFns(fns, mont_fn);
                    }
                    mont_credit = 0;
                } else {
                    double mont_fn = c.getMontantCredit();
                    List<EuFn> fns = fnRepo.findFnByCodeSmcipn(c.getCompteSource());
                    if (!fns.isEmpty()) {
                        updateFns(fns, mont_fn);
                    }
                    mont_credit -= c.getMontantCredit();
                    c.setMontantCredit(0.0);
                    creditRepo.save(c);
                    i++;
                }
            }

            EuCapa eucapa = new EuCapa();
            eucapa.setCodeCapa("CAPAI" + formatter.format(date));
            eucapa.setDateCapa(date);
            eucapa.setCodeMembre(compteDest.getEuMembreMorale().getCodeMembreMorale());
            eucapa.setCodeProduit("InrPRE");
            eucapa.setTypeCapa("I");
            eucapa.setOrigineCapa("NN");
            eucapa.setMontantCapa(montant);
            eucapa.setMontantUtiliser(montant);
            eucapa.setMontantSolde(0);
            eucapa.setEuCompte(compteDest);
            eucapa.setEtatCapa("Actif");
            if (op != null) {
                eucapa.setIdOperation(op.getIdOperation());
            } else {
                eucapa.setIdOperation(null);
            }
            eucapa.setHeureCapa(date);
            capaRepo.save(eucapa);
            return true;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de mise à jour des FNs", e);
            return false;
        }
    }

    public boolean updateListFns(List<EuCompteCredit> credits, EuOperation op, double montant) {
        try {
            double mont_credit = montant;
            int i = 0;
            while (mont_credit > 0 && i < credits.size()) {
                EuCompteCredit c = credits.get(i);
                if (mont_credit <= c.getMontantCredit()) {
                    double mont_fn = mont_credit;
                    c.setMontantCredit(c.getMontantCredit() - mont_credit);
                    List<EuFn> fns = fnRepo.findFnByCodeSmcipn(c.getCompteSource());
                    if (!fns.isEmpty()) {
                        updateFns(fns, mont_fn);
                    }
                    creditRepo.save(c);
                    mont_credit = 0;
                } else {
                    double mont_fn = c.getMontantCredit();
                    List<EuFn> fns = fnRepo.findFnByCodeSmcipn(c.getCompteSource());
                    if (!fns.isEmpty()) {
                        updateFns(fns, mont_fn);
                    }
                    mont_credit -= c.getMontantCredit();
                    c.setMontantCredit(0.0);
                    creditRepo.save(c);
                    i++;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de mise à jour des FNs", e);
            return false;
        }
    }

    public boolean updateListSmc(List<EuCompteCredit> credits, EuCompte compteDest, EuOperation op, double montant) {
        Date date = new Date();
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            double mont_credit = montant;
            int i = 0;
            while (mont_credit > 0) {
                EuCompteCredit c = credits.get(i);
                if (mont_credit <= c.getMontantCredit()) {
                    c.setMontantCredit(c.getMontantCredit() - mont_credit);
                    List<EuSmc> smcs = smcRepo.findSmcByCodeSmcipn(c.getCompteSource());
                    if (!smcs.isEmpty()) {
                        updateSmc(smcs, mont_credit);
                    }
                    creditRepo.save(c);
                    mont_credit = 0;
                } else {
                    mont_credit = mont_credit - c.getMontantCredit();
                    List<EuSmc> smcs = utiliserRepo.findByEuSmcipnpwi_CodeSmcipn(c.getCompteSource());
                    if (!smcs.isEmpty()) {
                        updateSmc(smcs, c.getMontantCredit());
                    }
                    c.setMontantCredit(0.0);
                    creditRepo.save(c);
                    i++;
                }
            }

            EuCapa eucapa = new EuCapa();
            eucapa.setCodeCapa("CAPACNCS" + formatter.format(date));
            eucapa.setDateCapa(date);
            eucapa.setCodeMembre(compteDest.getEuMembreMorale().getCodeMembreMorale());
            eucapa.setCodeProduit("CNCSnrPRE");
            eucapa.setTypeCapa("CNCS");
            eucapa.setOrigineCapa("NN");
            eucapa.setMontantCapa(montant);
            eucapa.setMontantUtiliser(montant);
            eucapa.setMontantSolde(0);
            eucapa.setEuCompte(compteDest);
            eucapa.setEtatCapa("Actif");
            if (op != null) {
                eucapa.setIdOperation(op.getIdOperation());
            } else {
                eucapa.setIdOperation(null);
            }
            eucapa.setHeureCapa(date);
            capaRepo.save(eucapa);

            return true;
        } catch (Exception e) {
            logger.error("Erreur d'execution  de mise à jour des SMCs", e);
            return false;
        }
    }

    public boolean createCreditNR(String codeMembre, String codeCat, String typeCompte, String codeProduit,
                                  double montant, double credit, boolean isBnp, String codeBnp, String codeSmcipn, EuOperation op,
                                  EuCapa capa) {
        Date date = new Date();
        EuMembre membre = null;
        EuMembreMorale morale = null;
        if (codeMembre.endsWith("P")) {
            membre = new EuMembre();
            membre.setCodeMembre(codeMembre);
        } else {
            morale = new EuMembreMorale();
            morale.setCodeMembreMorale(codeMembre);
        }
        String codeCompteTe = typeCompte + "-" + codeCat + "-" + codeMembre;

        EuTypeCompte t_compte = new EuTypeCompte(typeCompte);
        EuCompte compteTe = compteRepo.findOne(codeCompteTe);
        if (compteTe != null) {
            compteTe.setSolde(compteTe.getSolde() + credit);
        } else {
            EuCategorieCompte cat = new EuCategorieCompte();
            cat.setCodeCat(codeCat);

            System.out.println("Debut TSI");
            compteTe = new EuCompte();
            compteTe.setCodeCompte(codeCompteTe);
            compteTe.setDateAlloc(date);
            compteTe.setDesactiver("N");
            compteTe.setEuCategorieCompte(cat);
            if (codeMembre.endsWith("P")) {
                compteTe.setEuMembre(membre);
            } else {
                compteTe.setEuMembreMorale(morale);
            }
            compteTe.setSolde(credit);
            compteTe.setEuTypeCompte(t_compte);
            compteTe.setLibCompte("Compte " + codeCat);
            compteTe.setEuMembre(null);
            compteTe.setCardprinteddate(null);
            compteTe.setCardprintediddate(0);
            compteTe.setMifarecard(null);
            compteTe.setNumeroCarte(null);
            compteRepo.save(compteTe);
        }

        EuProduit produit = new EuProduit(codeProduit);
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        EuCompteCredit cc = new EuCompteCredit();
        Long idCredit = ccreditRepo.getLastCreditInsertedId();
        if (idCredit == null) {
            idCredit = 1L;
        } else {
            idCredit += 1;
        }
        cc.setIdCredit(idCredit);
        cc.setCodeMembre(codeMembre);
        cc.setMontantPlace(montant);
        cc.setMontantCredit(credit);
        cc.setEuOperation(op);
        cc.setEuProduit(produit);
        cc.setCodeTypeCredit(null);
        cc.setSource(codeMembre + formatter.format(date));
        cc.setCompteSource(codeSmcipn);
        cc.setDateOctroi(date);
        cc.setDatedeb(date);
        Date datefin = ServerUtil.ajouterJours(date, 30);
        cc.setDatefin(datefin);
        cc.setRenouveller("N");
        cc.setNbreRenouvel(1);
        cc.setBnp(0);
        cc.setKrr("N");
        cc.setEuCompte(compteTe);
        cc.setDomicilier(1);
        cc.setPrk(1.0);
        cc.setAffecter(0);
        cc.setCodeBnp(codeBnp);
        ccreditRepo.save(cc);

        EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
        EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
        ccCapaPK.setCodeCapa(capa.getCodeCapa());
        ccCapaPK.setIdCredit(cc.getIdCredit());
        ccCapa.setId(ccCapaPK);
        ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
        ccCapa.setMontant(montant);
        ccCapaRepo.save(ccCapa);

        return true;
    }

    public EuCompte saveCompte(Object membre, String codeCat, String codeTypeCompte, double montant) {
        Date date = new Date();
        EuMembre physique = null;
        EuMembreMorale morale = null;
        String codeCompte = null;
        EuCompte compte = null;
        if (Objects.nonNull(membre) && StringUtils.isNotBlank(codeCat) && StringUtils.isNotBlank(codeTypeCompte)) {
            if (membre instanceof EuMembre) {
                physique = (EuMembre) membre;
                codeCompte = codeTypeCompte + "-" + codeCat + "-" + physique.getCodeMembre();
            } else {
                morale = (EuMembreMorale) membre;
                codeCompte = codeTypeCompte + "-" + codeCat + "-" + morale.getCodeMembreMorale();
            }
            compte = compteRepo.findOne(codeCompte);
            if (compte != null) {
                if (montant > 0) {
                    compte.setSolde(compte.getSolde() + montant);
                    compteRepo.save(compte);
                }
            } else {
                compte = new EuCompte();
                EuCategorieCompte cat = new EuCategorieCompte(codeCat);
                EuTypeCompte typeCompte = new EuTypeCompte(codeTypeCompte);
                compte.setCodeCompte(codeCompte);
                compte.setEuCategorieCompte(cat);
                compte.setEuTypeCompte(typeCompte);
                if (membre instanceof EuMembreMorale) {
                    compte.setEuMembreMorale(morale);
                    compte.setEuMembre(null);
                } else {
                    compte.setEuMembre(physique);
                    compte.setEuMembreMorale(null);
                }
                compte.setLibCompte(codeTypeCompte);
                compte.setDesactiver("N");
                compte.setSolde(montant);
                compte.setDateAlloc(date);
                compte.setCardprinteddate(null);
                compte.setCardprintediddate(0);
                compte.setMifarecard(null);
                compte.setNumeroCarte(null);
                compteRepo.save(compte);
            }
        }
        return compte;
    }

    public boolean updateListCapaTs(List<EuCapaTs> capas, EuCompteCredit cc, double capa, Date date, String typeR,
                                    EuBon bon) {
        if (!capas.isEmpty()) {
            try {
                double mont_capa = capa;
                int i = 0;
                while (mont_capa > 0) {
                    EuCapaTs c = capas.get(i);
                    if (c.getMontantSolde() > 0) {
                        if (mont_capa > c.getMontantSolde()) {
                            EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
                            EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
                            ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
                            ccCapaPK.setIdCredit(cc.getIdCredit());
                            ccCapa.setId(ccCapaPK);
                            ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
                            ccCapa.setMontant(c.getMontantSolde());
                            ccCapa.setEuBon(bon);
                            ccCapaRepo.save(ccCapa);

                            EuFn fn = new EuFn();
                            fn.setTypeFn("I" + typeR);
                            fn.setDateFn(date);
                            fn.setEuCapa(c.getEuCapa());
                            fn.setMontant(c.getMontantSolde());
                            fn.setSortie(0);
                            fn.setEntree(0);
                            fn.setSolde(0);
                            fn.setOrigineFn(1);
                            fn.setMtSolde(c.getMontantSolde());
                            fnRepo.save(fn);

                            mont_capa -= c.getMontantSolde();

                            c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
                            c.setMontantSolde(0.0);
                            capaTsRepo.save(c);
                            i++;

                        } else {

                            EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
                            EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
                            ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
                            ccCapaPK.setIdCredit(cc.getIdCredit());
                            ccCapa.setId(ccCapaPK);
                            ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
                            ccCapa.setMontant(mont_capa);
                            ccCapa.setEuBon(bon);
                            ccCapaRepo.save(ccCapa);

                            EuFn fn = new EuFn();
                            fn.setTypeFn("I" + typeR);
                            fn.setDateFn(date);
                            fn.setEuCapa(c.getEuCapa());
                            fn.setMontant(mont_capa);
                            fn.setSortie(0);
                            fn.setEntree(0);
                            fn.setSolde(0);
                            fn.setMtSolde(mont_capa);
                            fn.setOrigineFn(1);
                            fnRepo.save(fn);

                            c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
                            c.setMontantSolde(c.getMontantSolde() - mont_capa);
                            capaTsRepo.save(c);

                            mont_capa = 0;
                        }
                    }
                }
                return true;
            } catch (Exception e) {
                logger.error("Erreur d'execution  de mise à jour des CAPA TS", e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateListCapaTs(List<EuCapaTs> capas, EuCompteCredit cc, double capa, Date date, String typeR) {
        if (!capas.isEmpty()) {
            try {
                double mont_capa = capa;
                int i = 0;
                while (mont_capa > 0) {
                    EuCapaTs c = capas.get(i);
                    if (mont_capa > c.getMontantSolde()) {
                        EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
                        EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
                        ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
                        ccCapaPK.setIdCredit(cc.getIdCredit());
                        ccCapa.setId(ccCapaPK);
                        ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
                        ccCapa.setMontant(c.getMontantSolde());
                        ccCapaRepo.save(ccCapa);

                        EuFn fn = new EuFn();
                        fn.setTypeFn("I" + typeR);
                        fn.setDateFn(date);
                        fn.setEuCapa(c.getEuCapa());
                        fn.setMontant(c.getMontantSolde());
                        fn.setSortie(0);
                        fn.setEntree(0);
                        fn.setSolde(0);
                        fn.setOrigineFn(1);
                        fn.setMtSolde(c.getMontantSolde());
                        fnRepo.save(fn);

                        mont_capa -= c.getMontantSolde();

                        c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
                        c.setMontantSolde(0.0);
                        capaTsRepo.save(c);
                        i++;

                    } else {

                        EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
                        EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
                        ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
                        ccCapaPK.setIdCredit(cc.getIdCredit());
                        ccCapa.setId(ccCapaPK);
                        ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
                        ccCapa.setMontant(mont_capa);
                        ccCapaRepo.save(ccCapa);

                        EuFn fn = new EuFn();
                        fn.setTypeFn("I" + typeR);
                        fn.setDateFn(date);
                        fn.setEuCapa(c.getEuCapa());
                        fn.setMontant(mont_capa);
                        fn.setSortie(0);
                        fn.setEntree(0);
                        fn.setSolde(0);
                        fn.setMtSolde(mont_capa);
                        fn.setOrigineFn(1);
                        fnRepo.save(fn);

                        c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
                        c.setMontantSolde(c.getMontantSolde() - mont_capa);
                        capaTsRepo.save(c);

                        mont_capa = 0;
                    }
                }
                return true;
            } catch (Exception e) {
                logger.error("Erreur d'execution  de mise à jour des CAPA TS ", e);
                return false;
            }
        } else {
            return false;
        }
    }

    public void updateCnpByCredit(Long idCredit, double montant) {
        if (idCredit != null && montant > 0) {
            List<EuCnp> cnps = cnpRepo.findByIdCredit(idCredit);
            if (!cnps.isEmpty()) {
                int c = 0;
                while (montant > 0 && c < cnps.size()) {
                    EuCnp cnp = cnps.get(c);
                    if (montant <= cnp.getSoldeCnp()) {
                        cnp.setSoldeCnp(cnp.getSoldeCnp() - montant);
                        cnp.setMontCredit(cnp.getMontCredit() + montant);

                        EuCnpEntree cnpEntree = new EuCnpEntree();
                        cnpEntree.setEuCnp(cnp);
                        cnpEntree.setDateEntree(new Date());
                        cnpEntree.setTypeCnpEntree("BC");
                        cnpEntree.setMontCnpEntree(montant);
                        cnpEntreeRepository.save(cnpEntree);
                        montant = 0;
                    } else {
                        montant = montant - cnp.getSoldeCnp();
                        EuCnpEntree cnpEntree = new EuCnpEntree();
                        cnpEntree.setEuCnp(cnp);
                        cnpEntree.setDateEntree(new Date());
                        cnpEntree.setTypeCnpEntree("BC");
                        cnpEntree.setMontCnpEntree(montant);
                        cnpEntreeRepository.save(cnpEntree);

                        cnp.setMontCredit(cnp.getMontCredit() + cnp.getSoldeCnp());
                        cnp.setSoldeCnp(0);
                        c++;
                    }
                }
            }
        }
    }

    public int updateCnp(long idKrr, double montant) {
        List<EuDetailKrr> detKrrs = ListUtils.emptyIfNull(detKrrRepo.findByIdKrr(idKrr));
        int rep = 0;
        if (!detKrrs.isEmpty()) {
            int i = 0;
            while (montant > 0 && i < detKrrs.size()) {
                EuDetailKrr detKrr = detKrrs.get(i);
                EuCnp cnp = cnpRepo.findBySourceCredit(detKrr.getIdCredit(), detKrr.getSourceCredit());
                if (Objects.nonNull(cnp)) {
                    if (cnp.getSoldeCnp() >= detKrr.getMontCredit()) {
                        cnp.setMontCredit(cnp.getMontCredit() - detKrr.getMontCredit());
                        cnpRepo.save(cnp);

                        detKrr.setAnnuler(1);
                        detKrrRepo.save(detKrr);

                        montant -= detKrr.getMontCredit();
                    } else {
                        rep = 1;
                    }
                } else {
                    rep = 2;
                }
                i++;
            }
        }
        return rep;
    }

    public void updateFns(List<EuFn> fns, double montant) {
        int j = 0;
        while (montant > 0) {
            EuFn fn = fns.get(j);
            if (fn.getSolde() > montant) {
                fn.setEntree(fn.getEntree() + montant);
                fn.setSolde(fn.getSolde() - fn.getEntree());
                fnRepo.save(fn);
                montant = 0;
            } else {
                montant -= fn.getSolde();
                fn.setEntree(fn.getEntree() + fn.getSolde());
                fn.setSolde(0);
                fnRepo.save(fn);
                j++;
            }
        }
    }

    public void updateSmc(List<EuSmc> smcs, double montant) {
        int j = 0;
        while (montant > 0 && j < smcs.size()) {
            EuSmc smc = smcs.get(j);
            if (smc.getSolde() > montant) {
                smc.setEntree(smc.getEntree() + montant);
                smc.setSolde(smc.getSolde() - smc.getEntree());
                smcRepo.save(smc);
                montant = 0;
            } else {
                montant -= smc.getSolde();
                smc.setEntree(smc.getEntree() + smc.getSolde());
                smc.setSolde(0);
                smcRepo.save(smc);
                j++;
            }
        }
    }

    public boolean updateSmcipnSmcs(List<EuSmc> smcs, Date date, EuSmcipnpwi smcipn, long idCredit, double mont_smc) {
        if (smcs.size() > 0 && Objects.nonNull(smcipn)) {
            int i = 0;
            double montant = mont_smc;
            while (montant > 0) {
                EuSmc smc = smcs.get(i);
                if (smc.getMontantSolde() > montant) {
                    smc.setSortie(smc.getSortie() + montant);
                    smc.setSolde(smc.getSolde() + montant);
                    smc.setMontantSolde(smc.getMontantSolde() - montant);
                    smcRepo.save(smc);

                    Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
                    if (idUtiliser != null) {
                        idUtiliser++;
                    } else {
                        idUtiliser = 1L;
                    }
                    EuUtiliser utiliser = new EuUtiliser();
                    utiliser.setIdUtiliser(idUtiliser);
                    utiliser.setEuSmc(smc);
                    utiliser.setMontantAllouer(montant);
                    utiliser.setEuSmcipnpwi(smcipn);
                    utiliser.setIdCredit(idCredit);
                    utiliser.setDateCreation(date);
                    utiliserRepo.save(utiliser);

                    montant = 0;
                } else {
                    montant -= smc.getMontantSolde();

                    Long idUtiliser = utiliserRepo.findLastUtiliserInsertedId();
                    if (idUtiliser != null) {
                        idUtiliser++;
                    } else {
                        idUtiliser = 1L;
                    }
                    EuUtiliser utiliser = new EuUtiliser();
                    utiliser.setIdUtiliser(idUtiliser);
                    utiliser.setEuSmc(smc);
                    utiliser.setMontantAllouer(smc.getMontantSolde());
                    utiliser.setEuSmcipnpwi(smcipn);
                    utiliser.setIdCredit(idCredit);
                    utiliser.setDateCreation(date);
                    utiliserRepo.save(utiliser);

                    smc.setSortie(smc.getSortie() + smc.getMontantSolde());
                    smc.setSolde(smc.getSolde() + smc.getMontantSolde());
                    smc.setMontantSolde(0);
                    smcRepo.save(smc);
                    i++;
                }
            }
            return true;
        } else {
            throw new SoldeInsuffisantException(
                    "La ressource SMC disponible est insuffisant pour accorder cette subvention!");
        }
    }

    public boolean updateSmcipnFns(List<EuFn> fns, Date date, EuSmcipnpwi smcipn, double mont_fn) {
        if (fns.size() > 0 && Objects.nonNull(smcipn)) {
            int i = 0;
            double montant = mont_fn;
            while (montant > 0 && i < fns.size()) {
                EuFn fn = fns.get(i);
                if (fn.getMtSolde() >= montant) {
                    // System.out.println("Mise à jour des FNS CAS 1");
                    fn.setSortie(fn.getSortie() + montant);
                    fn.setSolde(fn.getSolde() + montant);
                    fn.setMtSolde(fn.getMtSolde() - montant);
                    fnRepo.save(fn);

                    Long idServir = servirRepo.getLastEuServirInsertedId();
                    if (idServir != null) {
                        idServir = idServir + 1;
                    } else {
                        idServir = 1L;
                    }
                    EuServir servir = new EuServir();
                    servir.setIdServir(idServir);
                    servir.setDateCreation(date);
                    servir.setEuFn(fn);
                    servir.setEuSmcipnpwi(smcipn);
                    servir.setMontantAllouer(montant);
                    servirRepo.save(servir);

                    montant = 0;
                } else {
                    // System.out.println("Mise à jour des FNS CAS 2");
                    montant -= fn.getMtSolde();

                    Long idServir = servirRepo.getLastEuServirInsertedId();
                    if (idServir != null) {
                        idServir = idServir + 1;
                    } else {
                        idServir = 1L;
                    }

                    EuServir servir = new EuServir();
                    servir.setIdServir(idServir);
                    servir.setDateCreation(date);
                    servir.setEuFn(fn);
                    servir.setEuSmcipnpwi(smcipn);
                    servir.setMontantAllouer(fn.getMtSolde());
                    servirRepo.save(servir);

                    fn.setSortie(fn.getSortie() + fn.getMtSolde());
                    fn.setSolde(fn.getSolde() + fn.getMtSolde());
                    fn.setMtSolde(0);
                    fnRepo.save(fn);

                    i++;
                }
            }
            return true;
        } else {
            throw new SoldeInsuffisantException(
                    "La ressource FN disponible est insuffisant pour accorder cette subvention!");
        }
    }

    public void reconstituerCapaCreditRec(EuKrr krr) {
        Date now = new Date();
        if (Objects.nonNull(krr)) {
            if (krr.getMontKrr() == krr.getMontReconst()) {
                double pck = paramRepo.findByCodeAndLib("pck", "nr");
                double prk_rec = paramRepo.findByCodeAndLib("PRK", "RCAPA");
                double mont_capa = krr.getMontReconst() * pck / prk_rec;
                EuCompteCredit cc = ccreditRepo.findById(krr.getIdCredit());
                if (Objects.nonNull(cc)) {
                    EuNn nn = nnService.EmettreNn(cc.getEuProduit().getCodeProduit().toUpperCase(), mont_capa, "Auto");
                    if (Objects.nonNull(nn)) {
                        List<EuCompteCreditCapa> ccCapa = ccCapaRepo.findCreditCapaByIdCredit(krr.getIdCredit());
                        if (!ccCapa.isEmpty()) {
                            ccCapa.forEach(cp -> {
                                if (Objects.nonNull(cp.getEuCapa()) && cp.getEuCapa().getEtatCapa().equals("Actif")) {
                                    EuCapa capa = cp.getEuCapa();
                                    capa.setEtatCapa("Inactif");
                                    capaRepo.save(capa);
                                }
                            });
                        }
                        if (createCapaCredit(cc, mont_capa, new Date(), "r")) {
                            EuUtiliserNn utilNn = new EuUtiliserNn();
                            utilNn.setCodeMembreNb(cc.getCodeMembre());
                            utilNn.setCodeMembreNn("Source");
                            utilNn.setCodeProduit(cc.getEuProduit().getCodeProduit());
                            utilNn.setCodeSms(null);
                            utilNn.setDateTransfert(new Date());
                            utilNn.setIdOperation(null);
                            utilNn.setIdUtilisateur(null);
                            utilNn.setMontTransfert(mont_capa);
                            utilNn.setNumBon(null);
                            utiliserNnRepo.save(utilNn);

                            updateCnp(krr.getIdKrr(), krr.getMontReconst());
                            if (!krr.getTypeKrr().equals("krrKBNP")) {
                                EuKrr euKrr = new EuKrr();
                                euKrr.setDateDemande(now);
                                euKrr.setDateEchue(DateUtils.addDays(now, (int) Math.ceil(22.4 * 30)));
                                euKrr.setDateRenouveller(null);
                                euKrr.setEuMembre(null);
                                euKrr.setEuMembreMorale(null);
                                euKrr.setEuProduit(cc.getEuProduit());
                                euKrr.setIdCredit(krr.getIdCredit());
                                euKrr.setMontCapa(krr.getMontCapa());
                                euKrr.setMontKrr(krr.getMontKrr());
                                euKrr.setMontReconst(0);
                                euKrr.setPayer("N");
                                euKrr.setReconstituer("N");
                                euKrr.setTypeKrr("krrBCRI");
                                krrRepo.save(euKrr);
                            }
                        }
                    }
                }
            }
        }
    }

}
