/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.acteurs.EuActeurRepository;
import com.esmc.mcnp.dao.repository.bc.EuCnpEntreeRepository;
import com.esmc.mcnp.dao.repository.bc.EuCnpRepository;
import com.esmc.mcnp.dao.repository.bc.EuCreditEchangeRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpPreleverRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuDetailGcpPbfRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuEchangeRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuEscompteRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuGcpPbfRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTpagcpRepository;
import com.esmc.mcnp.dao.repository.others.EuCompensationRepository;
import com.esmc.mcnp.domain.dto.desendettement.Compensation;
import com.esmc.mcnp.domain.dto.echange.Escompte;
import com.esmc.mcnp.domain.dto.obps.LGcp;
import com.esmc.mcnp.domain.dto.obps.TpaGcpView;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.bc.EuCnp;
import com.esmc.mcnp.domain.entity.bc.EuCnpEntree;
import com.esmc.mcnp.domain.entity.bc.EuCreditEchange;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.obps.EuGcp;
import com.esmc.mcnp.domain.entity.obps.EuGcpPrelever;
import com.esmc.mcnp.domain.entity.obpsd.EuCompensation;
import com.esmc.mcnp.domain.entity.obpsd.EuDetailGcpPbf;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.obpsd.EuEscompte;
import com.esmc.mcnp.domain.entity.obpsd.EuGcpPbf;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;

/**
 *
 * @author USER
 */
@Service("gcpService")
@Transactional
public class GcpServiceImpl implements GcpService {

    @Autowired
    private EuTpagcpRepository tpagcpRepo;
    @Autowired
    private EuCompteRepository compteRepo;
    @Autowired
    private EuEchangeRepository echRepo;
    @Autowired
    private EuEscompteRepository escRepo;
    @Autowired
    private EuParametresService paramService;
    @Autowired
    private EuGcpPreleverRepository gcp_prelRepo;
    @Autowired
    private EuCnpRepository cnpRepo;
    @Autowired
    private EuCnpEntreeRepository cnpeRepo;
    @Autowired
    private EuCreditEchangeRepository cechRepo;
    @Autowired
    private EuGcpPbfRepository gcppbfRepo;
    @Autowired
    private EuDetailGcpPbfRepository detpbfRepo;
    @Autowired
    private EuActeurRepository acteurRepo;
    @Autowired
    private EuCompensationRepository compensRepo;
    @Autowired
    private EuMembreMoraleRepository moralRepo;

    @Override
    @Transactional(readOnly = true)
    public List<EuTpagcp> getTpaGcpByCompte(String codeCompte) {
        try {
            return tpagcpRepo.findByCodeCompte(codeCompte);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String doEscompteGcp(LGcp gcp) {
        String rep = "Echec de l'opération";
        if (gcp != null) {
            if (gcp.getCodeCompte() != null && gcp.getCodeComptepbf() != null) {
                String codeComptePbf = "NB-TPAGCP-" + ServerUtil.getCodeMembre(gcp.getCodeComptepbf());
                String codeCompteGcp = "NB-TPAGCP-" + ServerUtil.getCodeMembre(gcp.getCodeCompte());
                EuActeur acteur = acteurRepo.findByCodeMembre(ServerUtil.getCodeMembre(gcp.getCodeComptepbf()));
                if (acteur != null && acteur.getTypeActeur().equalsIgnoreCase("PBF")) { 
                    EuCompte compteGcp = compteRepo.findOne(codeCompteGcp);
                    EuCompte comptePbf = compteRepo.findOne(codeComptePbf);
                    List<TpaGcpView> gcps = gcp.getGcps();
                    if (gcps.size() > 0) {
                        LocalDate ldate = LocalDate.now();
                        Date date = new Date();
                        Integer periode = paramService.getParam("periode", "valeur");
                        double agio = Math.floor((gcp.getSolde() * gcp.getTx_agio()) / 100);
                        double montant = gcp.getSolde() - agio;
                        Long id_echange = echRepo.findLastEchangeInsertedId();
                        if (id_echange == null) {
                            id_echange = 0L;
                        }
                        EuEchange ech = new EuEchange();
                        ech.setIdEchange(id_echange + 1);
                        ech.setCatEchange("GCP");
                        ech.setTypeEchange("NB/NN");
                        ech.setMontantEchange(gcp.getSolde());
                        ech.setMontant(montant);
                        ech.setCompenser(0);
                        ech.setRegler(1);
                        ech.setEuCompte(compteGcp);
                        ech.setCodeCompteObt(null);
                        ech.setDateEchange(date);
                        ech.setDateReglement(date);
                        ech.setEuMembreMorale(compteGcp.getEuMembreMorale());
                        ech.setEuMembre(null);
                        ech.setCodeProduit("GCP");
                        ech.setAgio(agio);
                        echRepo.save(ech);

                        if (gcp.getType_escompte().equals("Partiel")) {
                            System.out.println("Escompte partiel");
                            for (TpaGcpView g : gcps) {
                                EuTpagcp tpagcp = tpagcpRepo.findOne(g.getIdTpagcp());
                                if (tpagcp != null) {
                                    if (tpagcp.getResteNtf().intValue() >= gcp.getNbre_ntf()) {

                                        List<EuGcpPrelever> gcps_prels = gcp_prelRepo
                                                .findGcpPreleverByIdTpagcp(tpagcp.getIdTpagcp());
                                        if (gcps_prels.size() > 0) {

                                            double mont_escompte = tpagcp.getMontTranche() * gcp.getNbre_ntf();
                                            LocalDate date_deb = tpagcp.getDateDebTranche().plusDays(periode * gcp.getNbre_ntf());
                                            LocalDate date_fin = tpagcp.getDateDebTranche().plusDays(periode);
                                            EuEscompte escompte = new EuEscompte();
                                            Long id_escompte = escRepo.getLastInsertedId();
                                            if (id_escompte != null) {
                                                id_escompte++;
                                            } else {
                                                id_escompte = 1L;
                                            }
                                            escompte.setIdEscompte(id_escompte);
                                            escompte.setIdEchange(ech.getIdEchange());
                                            escompte.setCodeMembre(ServerUtil.getCodeMembre(gcp.getCodeCompte()));
                                            escompte.setCodeMembreBenef(
                                                    ServerUtil.getCodeMembre(gcp.getCodeComptepbf()));
                                            escompte.setDateDeb(tpagcp.getDateDebTranche());
                                            escompte.setDateDebTranche(tpagcp.getDateDebTranche());
                                            escompte.setDateEscompte(ldate);
                                            escompte.setDateFin(date_deb);
                                            escompte.setDateFinTranche(tpagcp.getDateFinTranche());
                                            escompte.setEuCompte(compteGcp);
                                            escompte.setMontEchu(0);
                                            escompte.setMontEchuTransferer(0);
                                            escompte.setMontTranche(tpagcp.getMontTranche());
                                            escompte.setMontant(mont_escompte);
                                            escompte.setNtf(gcp.getNbre_ntf());
                                            escompte.setPeriode(tpagcp.getPeriode());
                                            escompte.setResteNtf(gcp.getNbre_ntf());
                                            escompte.setSolde(mont_escompte);
                                            escompte.setTxAgioApplique(gcp.getTx_agio());
                                            escRepo.save(escompte);

                                            String code_gcp_pbf = "GCP-GCP-"
                                                    + ServerUtil.getCodeMembre(comptePbf.getCodeCompte());
                                            EuGcpPbf gcp_pbf = gcppbfRepo.findOne(code_gcp_pbf);
                                            if (gcp_pbf != null) {
                                                gcp_pbf.setMontGcp(gcp_pbf.getMontGcp() + gcp.getSolde());
                                                gcp_pbf.setMontAgio(gcp_pbf.getMontAgio() + agio);
                                                gcp_pbf.setMontGcpReel(gcp_pbf.getMontGcpReel() + montant);
                                                gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() + agio);
                                                gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() + montant);
                                                gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() + gcp.getSolde());
                                            } else {
                                                gcp_pbf = new EuGcpPbf();
                                                gcp_pbf.setCodeGcpPbf(code_gcp_pbf);
                                                gcp_pbf.setCodeMembre(
                                                        ServerUtil.getCodeMembre(comptePbf.getCodeCompte()));
                                                gcp_pbf.setEuCompte(comptePbf);
                                                gcp_pbf.setAgioConsomme(0);
                                                gcp_pbf.setGcpCompense(0);
                                                gcp_pbf.setMontGcp(gcp.getSolde());
                                                gcp_pbf.setMontAgio(agio);
                                                gcp_pbf.setTypeCapa("FGGCP");
                                                gcp_pbf.setMontGcpReel(montant);
                                                gcp_pbf.setSoldeAgio(agio);
                                                gcp_pbf.setSoldeGcpReel(montant);
                                                gcp_pbf.setSoldeGcp(gcp.getSolde());
                                            }
                                            gcppbfRepo.save(gcp_pbf);

                                            double mont_deduire = tpagcp.getMontTranche() * gcp.getNbre_ntf();
                                            int i = 0;
                                            System.out.println("EP: Nbre de GCP prelever :" + gcps_prels.size()
                                                    + " Montant à déduire = " + mont_deduire);
                                            while (mont_deduire > 0) {
                                                EuGcpPrelever gcp_p = gcps_prels.get(i);
                                                if (gcp_p.getSoldePrelevement() < mont_deduire) {
                                                    System.out.println("EP : Cas 1");
                                                    EuGcp eugcp = gcp_p.getEuGcp();
                                                    EuCnp cnp = cnpRepo.findBySourceCredit(
                                                            eugcp.getEuCompteCredit().getIdCredit(), eugcp.getSource());
                                                    if (cnp != null) {
                                                        cnp.setMontCredit(
                                                                cnp.getMontCredit() + gcp_p.getSoldePrelevement());
                                                        cnp.setSoldeCnp(
                                                                cnp.getSoldeCnp() - gcp_p.getSoldePrelevement());
                                                        cnpRepo.save(cnp);
                                                        EuCnpEntree cnp_e = new EuCnpEntree();
                                                        Long idCnpEntree = cnpeRepo.getLastInsertedId();
                                                        if (idCnpEntree != null) {
                                                            idCnpEntree += 1;
                                                        } else {
                                                            idCnpEntree = 1L;
                                                        }
                                                        cnp_e.setIdCnpEntree(idCnpEntree);
                                                        cnp_e.setEuCnp(cnp);
                                                        cnp_e.setTypeCnpEntree("GCP");
                                                        cnp_e.setMontCnpEntree(gcp_p.getSoldePrelevement());
                                                        cnp_e.setDateEntree(date);
                                                        cnpeRepo.save(cnp_e);
                                                        Long idCreditEchange = cechRepo.getLastInsertedId();
                                                        if (idCreditEchange != null) {
                                                            idCreditEchange++;
                                                        } else {
                                                            idCreditEchange = 1L;
                                                        }
                                                        EuCreditEchange credEch = new EuCreditEchange();
                                                        credEch.setIdCreditEchange(idCreditEchange);
                                                        credEch.setIdCredit(
                                                                gcp_p.getEuGcp().getEuCompteCredit().getIdCredit());
                                                        credEch.setSourceCredit(gcp_p.getSourceCredit());
                                                        credEch.setMontEchange(gcp_p.getSoldePrelevement());
                                                        credEch.setIdEchange(id_echange);
                                                        cechRepo.save(credEch);

                                                        double agio_p = Math.floor(
                                                                (gcp_p.getSoldePrelevement() * gcp.getTx_agio()) / 100);
                                                        EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
                                                        Long idGcppbf = detpbfRepo.getLastInsertedId();
                                                        if (idGcppbf != null) {
                                                            idGcppbf++;
                                                        } else {
                                                            idGcppbf = 1L;
                                                        }
                                                        detGcpPbf.setAgio(agio_p);
                                                        detGcpPbf.setEuGcpPbf(gcp_pbf);
                                                        detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
                                                        detGcpPbf.setMontGcpPbf(gcp_p.getSoldePrelevement());
                                                        detGcpPbf.setMontPreleve(0);
                                                        detGcpPbf.setTypeCapa("FGGCP");
                                                        detGcpPbf.setSourceCredit(eugcp.getSource());
                                                        detGcpPbf.setIdGcpPbf(idGcppbf);
                                                        detGcpPbf.setSoldeGcpPbf(gcp_p.getSoldePrelevement());
                                                        detGcpPbf.setIdEchange(id_echange);
                                                        detGcpPbf.setIdEscompte(id_escompte);
                                                        detpbfRepo.save(detGcpPbf);

                                                        mont_deduire -= gcp_p.getSoldePrelevement();

                                                        gcp_p.setMontRapprocher(gcp_p.getMontRapprocher()
                                                                + gcp_p.getSoldePrelevement());
                                                        gcp_p.setSoldePrelevement(0);
                                                        gcp_prelRepo.save(gcp_p);
                                                        i++;
                                                    } else {
                                                        return "Tous les prelevements GCp n'ont pas de CNP valide correspondant";
                                                    }
                                                } else {
                                                    System.out.println("EP : Cas 2");
                                                    EuGcp eugcp = gcp_p.getEuGcp();
                                                    EuCnp cnp = cnpRepo.findBySourceCredit(
                                                            eugcp.getEuCompteCredit().getIdCredit(), eugcp.getSource());
                                                    if (cnp != null) {
                                                        cnp.setMontCredit(cnp.getMontCredit() + mont_deduire);
                                                        cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduire);
                                                        cnpRepo.save(cnp);
                                                        EuCnpEntree cnp_e = new EuCnpEntree();
                                                        Long idCnpEntree = cnpeRepo.getLastInsertedId();
                                                        if (idCnpEntree != null) {
                                                            idCnpEntree += 1;
                                                        } else {
                                                            idCnpEntree = 1L;
                                                        }
                                                        cnp_e.setIdCnpEntree(idCnpEntree);
                                                        cnp_e.setEuCnp(cnp);
                                                        cnp_e.setTypeCnpEntree("GCP");
                                                        cnp_e.setMontCnpEntree(mont_deduire);
                                                        cnp_e.setDateEntree(date);
                                                        cnpeRepo.save(cnp_e);
                                                        Long idCreditEchange = cechRepo.getLastInsertedId();
                                                        if (idCreditEchange != null) {
                                                            idCreditEchange++;
                                                        } else {
                                                            idCreditEchange = 1L;
                                                        }
                                                        EuCreditEchange credEch = new EuCreditEchange();
                                                        credEch.setIdCreditEchange(idCreditEchange);
                                                        credEch.setIdCredit(
                                                                gcp_p.getEuGcp().getEuCompteCredit().getIdCredit());
                                                        credEch.setSourceCredit(gcp_p.getSourceCredit());
                                                        credEch.setMontEchange(mont_deduire);
                                                        credEch.setIdEchange(id_echange);
                                                        cechRepo.save(credEch);

                                                        double agio_p = Math
                                                                .floor((mont_deduire * gcp.getTx_agio()) / 100);
                                                        EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
                                                        Long idGcppbf = detpbfRepo.getLastInsertedId();
                                                        if (idGcppbf != null) {
                                                            idGcppbf++;
                                                        } else {
                                                            idGcppbf = 1L;
                                                        }
                                                        detGcpPbf.setAgio(agio_p);
                                                        detGcpPbf.setEuGcpPbf(gcp_pbf);
                                                        detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
                                                        detGcpPbf.setMontGcpPbf(mont_deduire);
                                                        detGcpPbf.setMontPreleve(0);
                                                        detGcpPbf.setTypeCapa("FGGCP");
                                                        detGcpPbf.setSourceCredit(eugcp.getSource());
                                                        detGcpPbf.setIdGcpPbf(idGcppbf);
                                                        detGcpPbf.setSoldeGcpPbf(mont_deduire);
                                                        detGcpPbf.setIdEchange(id_echange);
                                                        detGcpPbf.setIdEscompte(id_escompte);
                                                        detpbfRepo.save(detGcpPbf);

                                                        gcp_p.setMontRapprocher(
                                                                gcp_p.getMontRapprocher() + mont_deduire);
                                                        gcp_p.setSoldePrelevement(
                                                                gcp_p.getSoldePrelevement() - mont_deduire);
                                                        gcp_prelRepo.save(gcp_p);

                                                        mont_deduire = 0;
                                                    } else {
                                                        return "EP == \nTous les prelevements GCp n'ont pas de CNP valide correspondant";
                                                    }
                                                }
                                            }

                                            tpagcp.setMontEscompte(tpagcp.getMontEscompte()
                                                    + (tpagcp.getMontTranche() * gcp.getNbre_ntf()));
                                            tpagcp.setResteNtf(tpagcp.getResteNtf() - gcp.getNbre_ntf());
                                            tpagcp.setSolde(
                                                    tpagcp.getSolde() - (tpagcp.getMontTranche() * gcp.getNbre_ntf()));
                                            tpagcp.setDateDebTranche(date_deb);
                                            tpagcp.setDateFinTranche(date_fin);
                                            tpagcpRepo.save(tpagcp);

                                            comptePbf.setSolde(comptePbf.getSolde()
                                                    + (tpagcp.getMontTranche() * gcp.getNbre_ntf()));
                                            compteRepo.save(comptePbf);

                                        } else {
                                            return "EP == \nIl n'y a pas de prelevements associés à ce GCp n° :"
                                                    + g.getIdTpagcp();
                                        }
                                    } else {
                                        return "Le reste NTF du GCp " + g.getIdTpagcp()
                                                + " est inferieur au nombre demandé!";
                                    }
                                } else {
                                    rep = "Ce Gcp n° " + g.getIdTpagcp() + "n'existe pas";
                                }
                            }
                        } else {
                            System.out.println("Escompte total");
                            for (TpaGcpView g : gcps) {
                                EuTpagcp tpagcp = tpagcpRepo.findOne(g.getIdTpagcp());
                                if (tpagcp != null) {
                                    List<EuGcpPrelever> gcps_prels = gcp_prelRepo
                                            .findGcpPreleverByIdTpagcp(tpagcp.getIdTpagcp());
                                    if (gcps_prels.size() > 0) {
                                        EuEscompte escompte = new EuEscompte();
                                        Long id_escompte = escRepo.getLastInsertedId();
                                        if (id_escompte != null) {
                                            id_escompte++;
                                        } else {
                                            id_escompte = 1L;
                                        }
                                        escompte.setIdEscompte(id_escompte);
                                        escompte.setIdEchange(ech.getIdEchange());
                                        escompte.setCodeMembre(ServerUtil.getCodeMembre(gcp.getCodeCompte()));
                                        escompte.setCodeMembreBenef(ServerUtil.getCodeMembre(gcp.getCodeComptepbf()));
                                        escompte.setDateDeb(tpagcp.getDateDebTranche());
                                        escompte.setDateDebTranche(tpagcp.getDateDebTranche());
                                        escompte.setDateEscompte(ldate);
                                        escompte.setDateFin(tpagcp.getDateFin());
                                        escompte.setDateFinTranche(tpagcp.getDateFinTranche());
                                        escompte.setEuCompte(compteGcp);
                                        escompte.setMontEchu(0);
                                        escompte.setMontEchuTransferer(0);
                                        escompte.setMontTranche(tpagcp.getMontTranche());
                                        escompte.setMontant(tpagcp.getSolde());
                                        escompte.setNtf(tpagcp.getResteNtf());
                                        escompte.setPeriode(tpagcp.getPeriode());
                                        escompte.setResteNtf(tpagcp.getResteNtf());
                                        escompte.setSolde(tpagcp.getSolde());
                                        escompte.setTxAgioApplique(gcp.getTx_agio());
                                        escRepo.save(escompte);

                                        String code_gcp_pbf = "GCP-GCP-"
                                                + ServerUtil.getCodeMembre(comptePbf.getCodeCompte());
                                        EuGcpPbf gcp_pbf = gcppbfRepo.findOne(code_gcp_pbf);
                                        if (gcp_pbf != null) {
                                            gcp_pbf.setMontGcp(gcp_pbf.getMontGcp() + gcp.getSolde());
                                            gcp_pbf.setMontAgio(gcp_pbf.getMontAgio() + agio);
                                            gcp_pbf.setMontGcpReel(gcp_pbf.getMontGcpReel() + montant);
                                            gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() + agio);
                                            gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() + montant);
                                            gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() + gcp.getSolde());
                                        } else {
                                            gcp_pbf = new EuGcpPbf();
                                            gcp_pbf.setCodeGcpPbf(code_gcp_pbf);
                                            gcp_pbf.setCodeMembre(ServerUtil.getCodeMembre(comptePbf.getCodeCompte()));
                                            gcp_pbf.setEuCompte(comptePbf);
                                            gcp_pbf.setAgioConsomme(0);
                                            gcp_pbf.setGcpCompense(0);
                                            gcp_pbf.setMontGcp(gcp.getSolde());
                                            gcp_pbf.setMontAgio(agio);
                                            gcp_pbf.setTypeCapa("FGGCP");
                                            gcp_pbf.setMontGcpReel(montant);
                                            gcp_pbf.setSoldeAgio(agio);
                                            gcp_pbf.setSoldeGcpReel(montant);
                                            gcp_pbf.setSoldeGcp(gcp.getSolde());
                                        }
                                        gcppbfRepo.save(gcp_pbf);

                                        double mont_deduire = tpagcp.getSolde();
                                        int i = 0;
                                        System.out.println("ET: Nbre de GCP prelever :" + gcps_prels.size());
                                        while (mont_deduire > 0) {
                                            EuGcpPrelever gcp_p = gcps_prels.get(i);
                                            if (gcp_p.getSoldePrelevement() < mont_deduire) {
                                                System.out.println("ET : Cas 1");
                                                EuGcp eugcp = gcp_p.getEuGcp();
                                                EuCnp cnp = cnpRepo.findBySourceCredit(
                                                        eugcp.getEuCompteCredit().getIdCredit(), eugcp.getSource());
                                                if (cnp != null) {
                                                    cnp.setMontCredit(
                                                            cnp.getMontCredit() + gcp_p.getSoldePrelevement());
                                                    cnp.setSoldeCnp(cnp.getSoldeCnp() - gcp_p.getSoldePrelevement());
                                                    cnpRepo.save(cnp);
                                                    EuCnpEntree cnp_e = new EuCnpEntree();
                                                    Long idCnpEntree = cnpeRepo.getLastInsertedId();
                                                    if (idCnpEntree != null) {
                                                        idCnpEntree += 1;
                                                    } else {
                                                        idCnpEntree = 1L;
                                                    }
                                                    cnp_e.setIdCnpEntree(idCnpEntree);
                                                    cnp_e.setEuCnp(cnp);
                                                    cnp_e.setTypeCnpEntree("GCP");
                                                    cnp_e.setMontCnpEntree(gcp_p.getSoldePrelevement());
                                                    cnp_e.setDateEntree(date);
                                                    cnpeRepo.saveAndFlush(cnp_e);
                                                    Long idCreditEchange = cechRepo.getLastInsertedId();
                                                    if (idCreditEchange != null) {
                                                        idCreditEchange++;
                                                    } else {
                                                        idCreditEchange = 1L;
                                                    }
                                                    EuCreditEchange credEch = new EuCreditEchange();
                                                    credEch.setIdCreditEchange(idCreditEchange);
                                                    credEch.setIdCredit(
                                                            gcp_p.getEuGcp().getEuCompteCredit().getIdCredit());
                                                    credEch.setSourceCredit(gcp_p.getSourceCredit());
                                                    credEch.setMontEchange(gcp_p.getSoldePrelevement());
                                                    credEch.setIdEchange(id_echange);
                                                    cechRepo.saveAndFlush(credEch);

                                                    double agio_p = Math.floor(
                                                            (gcp_p.getSoldePrelevement() * gcp.getTx_agio()) / 100);
                                                    EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
                                                    Long idGcppbf = detpbfRepo.getLastInsertedId();
                                                    if (idGcppbf != null) {
                                                        idGcppbf++;
                                                    } else {
                                                        idGcppbf = 1L;
                                                    }
                                                    detGcpPbf.setAgio(agio_p);
                                                    detGcpPbf.setEuGcpPbf(gcp_pbf);
                                                    detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
                                                    detGcpPbf.setMontGcpPbf(gcp_p.getSoldePrelevement());
                                                    detGcpPbf.setMontPreleve(0);
                                                    detGcpPbf.setTypeCapa("FGGCP");
                                                    detGcpPbf.setSourceCredit(eugcp.getSource());
                                                    detGcpPbf.setIdGcpPbf(idGcppbf);
                                                    detGcpPbf.setSoldeGcpPbf(gcp_p.getSoldePrelevement());
                                                    detGcpPbf.setIdEchange(id_echange);
                                                    detGcpPbf.setIdEscompte(id_escompte);
                                                    detpbfRepo.save(detGcpPbf);

                                                    mont_deduire -= gcp_p.getSoldePrelevement();

                                                    gcp_p.setMontRapprocher(
                                                            gcp_p.getMontRapprocher() + gcp_p.getSoldePrelevement());
                                                    gcp_p.setSoldePrelevement(0);
                                                    gcp_prelRepo.saveAndFlush(gcp_p);
                                                    i++;
                                                } else {
                                                    return "Tous les prelevements GCp n'ont pas de CNP valide correspondant";
                                                }
                                            } else {
                                                System.out.println("ET : Cas 2");
                                                EuGcp eugcp = gcp_p.getEuGcp();
                                                EuCnp cnp = cnpRepo.findBySourceCredit(
                                                        eugcp.getEuCompteCredit().getIdCredit(), eugcp.getSource());
                                                if (cnp != null) {
                                                    cnp.setMontCredit(cnp.getMontCredit() + mont_deduire);
                                                    cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduire);
                                                    cnpRepo.save(cnp);
                                                    EuCnpEntree cnp_e = new EuCnpEntree();
                                                    Long idCnpEntree = cnpeRepo.getLastInsertedId();
                                                    if (idCnpEntree != null) {
                                                        idCnpEntree += 1;
                                                    } else {
                                                        idCnpEntree = 1L;
                                                    }
                                                    cnp_e.setIdCnpEntree(idCnpEntree);
                                                    cnp_e.setEuCnp(cnp);
                                                    cnp_e.setTypeCnpEntree("GCP");
                                                    cnp_e.setMontCnpEntree(mont_deduire);
                                                    cnp_e.setDateEntree(date);
                                                    cnpeRepo.save(cnp_e);
                                                    Long idCreditEchange = cechRepo.getLastInsertedId();
                                                    if (idCreditEchange != null) {
                                                        idCreditEchange++;
                                                    } else {
                                                        idCreditEchange = 1L;
                                                    }
                                                    EuCreditEchange credEch = new EuCreditEchange();
                                                    credEch.setIdCreditEchange(idCreditEchange);
                                                    credEch.setIdCredit(
                                                            gcp_p.getEuGcp().getEuCompteCredit().getIdCredit());
                                                    credEch.setSourceCredit(gcp_p.getSourceCredit());
                                                    credEch.setMontEchange(mont_deduire);
                                                    credEch.setIdEchange(id_echange);
                                                    cechRepo.save(credEch);

                                                    double agio_p = Math.floor((mont_deduire * gcp.getTx_agio()) / 100);
                                                    EuDetailGcpPbf detGcpPbf = new EuDetailGcpPbf();
                                                    Long idGcppbf = detpbfRepo.getLastInsertedId();
                                                    if (idGcppbf != null) {
                                                        idGcppbf++;
                                                    } else {
                                                        idGcppbf = 1L;
                                                    }
                                                    detGcpPbf.setAgio(agio_p);
                                                    detGcpPbf.setEuGcpPbf(gcp_pbf);
                                                    detGcpPbf.setIdCredit(eugcp.getEuCompteCredit().getIdCredit());
                                                    detGcpPbf.setMontGcpPbf(mont_deduire);
                                                    detGcpPbf.setMontPreleve(0);
                                                    detGcpPbf.setTypeCapa("FGGCP");
                                                    detGcpPbf.setSourceCredit(eugcp.getSource());
                                                    detGcpPbf.setIdGcpPbf(idGcppbf);
                                                    detGcpPbf.setSoldeGcpPbf(mont_deduire);
                                                    detGcpPbf.setIdEchange(id_echange);
                                                    detGcpPbf.setIdEscompte(id_escompte);
                                                    detpbfRepo.save(detGcpPbf);

                                                    gcp_p.setMontRapprocher(gcp_p.getMontRapprocher() + mont_deduire);
                                                    gcp_p.setSoldePrelevement(
                                                            gcp_p.getSoldePrelevement() - mont_deduire);
                                                    gcp_prelRepo.save(gcp_p);

                                                    mont_deduire = 0;
                                                } else {
                                                    return "ET == \nTous les prelevements GCp n'ont pas de CNP valide correspondant";
                                                }
                                            }
                                        }

                                        tpagcp.setMontEscompte(tpagcp.getMontEscompte() + tpagcp.getSolde());
                                        tpagcp.setResteNtf(0);
                                        tpagcp.setSolde(0);
                                        tpagcp.setDateFinTranche(tpagcp.getDateFin());
                                        tpagcpRepo.save(tpagcp); 

                                        comptePbf.setSolde(comptePbf.getSolde() + gcp.getSolde());
                                        compteRepo.save(comptePbf);
                                    } else {
                                        return "ET == \nIl n'y a pas de prelevements associés à ce GCp n° :"
                                                + g.getIdTpagcp();
                                    }
                                } else {
                                    rep = "Ce Gcp n° " + g.getIdTpagcp() + "n'existe pas";
                                }
                            }
                        }
                        rep = "L'opération a été effectuée avec succès";
                    } else {
                        rep = "Aucune GCp n'est sélectionnés";
                    }
                } else {
                    rep = "Cet acteur n'est pas un PBF pour effectuer un escompte!!!";
                }
            }
        } else {
            rep = "Aucune n'est information n'est envoyée";
        }
        return rep;
    }

    @Override
    public LGcp getLGcpByCompte(String codeCompte) {
        LGcp gcp_l = null;
        if (codeCompte.startsWith("NB-TSGCP")) {
            String codeComptegcp = "NB-TPAGCP-" + ServerUtil.getCodeMembre(codeCompte);
            List<EuTpagcp> gcps = getTpaGcpByCompte(codeComptegcp);
            if (gcps != null) {
                gcp_l = new LGcp();
                double sum_gcp = 0;
                TpaGcpView gcp = null;
                for (EuTpagcp g : gcps) {
                    gcp = new TpaGcpView();
                    gcp.setCodeCompte(g.getEuCompte().getCodeCompte());
                    gcp.setDateDebut(g.getDateDeb());
                    gcp.setDateFin(g.getDateFin());
                    gcp.setDateDebTranche(g.getDateDebTranche());
                    gcp.setDateFinTranche(g.getDateFinTranche());
                    gcp.setIdTpagcp(g.getIdTpagcp());
                    gcp.setMontGcp(g.getMontGcp());
                    gcp.setMontTranche(g.getMontTranche());
                    gcp.setResteNtf(g.getResteNtf().intValue());
                    gcp.setSolde(g.getSolde());
                    gcp_l.getGcps().add(gcp);
                    sum_gcp += g.getSolde();
                }
                gcp_l.setCodeCompte(codeComptegcp);
                gcp_l.setSolde(sum_gcp);
            }
        }
        return gcp_l;
    }

    @Override
    public String doMprg(Compensation compens) {
        String rep = "Echec de l'opération";
        LocalDate date = LocalDate.now();
        Date ldate = new Date();
        try {
            if (compens != null && compens.getTypeCompensation().equals("M") && compens.getEscomptes().size() > 0) {
                String catDest = ServerUtil.getTypeCompte(compens.getCodeComptepbf());
                String typeNumDest = ServerUtil.getTypeNumerique(compens.getCodeComptepbf());
                String codeMembreDest = ServerUtil.getCodeMembre(compens.getCodeComptepbf());
                EuActeur acteur_acnev = acteurRepo.findByCodeMembre(codeMembreDest);
                if (acteur_acnev == null || (acteur_acnev.getTypeActeur().equalsIgnoreCase("PBF")
                        && acteur_acnev.getCodeActivite().equalsIgnoreCase("ACNEV"))) {
                    return "Seul l'ACNEV PBF peut faire cette opération!!";
                }
                EuActeur acteur = acteurRepo.findByCodeMembre(ServerUtil.getCodeMembre(compens.getCodeCompte()));
                if (acteur != null && acteur.getTypeActeur().equalsIgnoreCase("PBF")) {
                    String codeCompte = "NB-TPAGCP-" + ServerUtil.getCodeMembre(compens.getCodeCompte());
                    EuCompte comptepbf = compteRepo.findOne(codeCompte);
                    if (comptepbf != null && comptepbf.getSolde() >= compens.getMontant()) {
                        Double som_gcp = gcppbfRepo.getSumGcpPbfByCompte(comptepbf.getCodeCompte());
                        Double som_esc = escRepo
                                .getSommeEscompteEchuByCompte(ServerUtil.getCodeMembre(comptepbf.getCodeCompte()));
                        if (som_gcp >= compens.getMontant() && som_esc >= compens.getMontant()) {
                            List<Escompte> escs = compens.getEscomptes();
                            // Mise à jour des escomptes et des GCps PBFs
                            for (Escompte esc : escs) {
                                EuEscompte e = escRepo.findOne(esc.getIdEscompte());
                                double mont_escompte = e.getMontEchu();
                                e.setMontEchu(0);
                                e.setMontEchuTransferer(mont_escompte);
                                escRepo.save(e);

                                List<EuDetailGcpPbf> gcp_pbfs = detpbfRepo.findDetailByEscompte(e.getIdEscompte());
                                int j = 0;
                                double mont_e = mont_escompte;
                                while (mont_e > 0) {
                                    EuDetailGcpPbf detgcp = gcp_pbfs.get(j);
                                    if (mont_e <= detgcp.getSoldeGcpPbf()) {

                                        detgcp.setMontPreleve(detgcp.getMontPreleve() + mont_e);
                                        detgcp.setSoldeGcpPbf(detgcp.getSoldeGcpPbf() - mont_e);
                                        detpbfRepo.save(detgcp);

                                        double agio_deduire = Math.round((mont_e * e.getTxAgioApplique()) / 100);

                                        EuGcpPbf gcp_pbf = gcppbfRepo.findOne(detgcp.getEuGcpPbf().getCodeGcpPbf());
                                        gcp_pbf.setAgioConsomme(gcp_pbf.getAgioConsomme() + agio_deduire);
                                        gcp_pbf.setGcpCompense(gcp_pbf.getGcpCompense() + mont_e);
                                        gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() - agio_deduire);
                                        gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() - (mont_e - agio_deduire));
                                        gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() - mont_e);
                                        gcppbfRepo.save(gcp_pbf);

                                        mont_e = 0;
                                    } else {

                                        mont_e -= detgcp.getSoldeGcpPbf();

                                        double agio_deduire = Math
                                                .round((detgcp.getSoldeGcpPbf() * e.getTxAgioApplique()) / 100);

                                        EuGcpPbf gcp_pbf = gcppbfRepo.findOne(detgcp.getEuGcpPbf().getCodeGcpPbf());
                                        gcp_pbf.setAgioConsomme(gcp_pbf.getAgioConsomme() + agio_deduire);
                                        gcp_pbf.setGcpCompense(gcp_pbf.getGcpCompense() + detgcp.getSoldeGcpPbf());
                                        gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() - agio_deduire);
                                        gcp_pbf.setSoldeGcpReel(
                                                gcp_pbf.getSoldeGcpReel() - (detgcp.getSoldeGcpPbf() - agio_deduire));
                                        gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() - detgcp.getSoldeGcpPbf());
                                        gcppbfRepo.save(gcp_pbf);

                                        detgcp.setMontPreleve(detgcp.getMontPreleve() + detgcp.getSoldeGcpPbf());
                                        detgcp.setSoldeGcpPbf(0);
                                        detpbfRepo.save(detgcp);
                                        j++;
                                    }
                                }

                            }

                            EuMembreMorale pbf_acnev = moralRepo.findOne(codeMembreDest);
                            EuCompte compteDest = compteRepo.findOne(compens.getCodeComptepbf());
                            if (compteDest != null) {
                                compteDest.setSolde(compteDest.getSolde() + compens.getMontant());
                                compteRepo.save(compteDest);
                            } else {
                                compteDest = new EuCompte();
                                EuCategorieCompte cat = new EuCategorieCompte();
                                cat.setCodeCat(catDest);
                                EuTypeCompte typeCompte = new EuTypeCompte();
                                typeCompte.setCodeTypeCompte(typeNumDest);
                                compteDest.setCodeCompte(compens.getCodeComptepbf());
                                compteDest.setEuCategorieCompte(cat);
                                compteDest.setEuTypeCompte(typeCompte);
                                compteDest.setEuMembre(null);
                                compteDest.setEuMembreMorale(pbf_acnev);
                                compteDest.setDesactiver("N");
                                compteDest.setSolde(compens.getMontant());
                                compteDest.setDateAlloc(ldate);
                                compteRepo.save(compteDest);
                            }

                            Long idCompens = compensRepo.getLastCompensationInserted();
                            if (idCompens == null) {
                                idCompens = 1L;
                            } else {
                                idCompens++;
                            }

                            EuCompensation comp = new EuCompensation();
                            comp.setIdCompens(idCompens);
                            comp.setMontCompens(compens.getMontant());
                            comp.setDateCompens(date);
                            comp.setDateDeb(date);
                            comp.setDateFin(date.plusMonths(8));
                            comp.setDateDebTranche(date);
                            comp.setDateFinTranche(date.plusDays(30));
                            comp.setMontTranche(compens.getMontant() / 8);
                            comp.setMontEchu(0);
                            comp.setNtf(8);
                            comp.setResteNtf(8);
                            comp.setCodeMembreBenef(codeMembreDest);
                            comp.setEuCompte(compteDest);
                            comp.setEuMembreMorale(comptepbf.getEuMembreMorale());
                            comp.setPeriode(30);
                            compensRepo.save(comp);

                            comptepbf.setSolde(comptepbf.getSolde() - compens.getMontant());
                            compteRepo.save(comptepbf);
                            rep = "L'opération a été effectuée avec succès";

                        } else {
                            rep = "La somme des escomptes echus n'atteind pas le montant demande";
                        }
                    } else {
                        rep = "Le compte GCp du PBF n'existe pas ou son solde est insuffisant!!!";
                    }
                } else {
                    rep = "Ce membre n'est pas un PBF!!!";
                }
            } else {
                rep = "Pas d'escomptes sélectionnés";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rep = "Une erreur inattendue est survenue :" + e.getLocalizedMessage();
        }
        return rep;
    }

    @Override
    public String doCompensation(Compensation compens) {
        String rep = "Echec de l'opération";
        Date ldate = new Date();
        LocalDate date = LocalDate.now();
        try {
            if (compens != null && compens.getTypeCompensation().equals("C") && compens.getEscomptes().size() > 0) {
                String catDest = ServerUtil.getTypeCompte(compens.getCodeComptepbf());
                String typeNumDest = ServerUtil.getTypeNumerique(compens.getCodeComptepbf());
                String codeMembreDest = ServerUtil.getCodeMembre(compens.getCodeComptepbf());
                EuActeur acteur_acnev = acteurRepo.findByCodeMembre(codeMembreDest);
                if (acteur_acnev == null || (acteur_acnev.getTypeActeur().equalsIgnoreCase("PBF")
                        && acteur_acnev.getCodeActivite().equalsIgnoreCase("ACNEV"))) {
                    return "Seul l'ACNEV PBF peut faire cette opération!!";
                }
                EuActeur acteur = acteurRepo.findByCodeMembre(ServerUtil.getCodeMembre(compens.getCodeCompte()));
                if (acteur != null && acteur.getTypeActeur().equalsIgnoreCase("PBF")) {
                    String codeCompte = "NB-TPAGCP-" + ServerUtil.getCodeMembre(compens.getCodeCompte());
                    EuCompte comptepbf = compteRepo.findOne(codeCompte);
                    if (comptepbf != null && comptepbf.getSolde() >= compens.getMontant()) {
                        Double som_gcp = gcppbfRepo.getSumGcpPbfByCompte(comptepbf.getCodeCompte());
                        Double som_esc = escRepo
                                .getSommeEscompteByCompte(ServerUtil.getCodeMembre(comptepbf.getCodeCompte()));
                        if (som_gcp >= compens.getMontant() && som_esc >= compens.getMontant()) {
                            List<Escompte> escs = compens.getEscomptes();
                            // Mise à jour des escomptes et des GCps PBFs
                            for (Escompte esc : escs) {
                                EuEscompte e = escRepo.findOne(esc.getIdEscompte());
                                double mont_escompte = e.getSolde();
                                e.setSolde(0);
                                e.setResteNtf(0);
                                escRepo.save(e);

                                List<EuDetailGcpPbf> gcp_pbfs = detpbfRepo.findDetailByEscompte(e.getIdEscompte());
                                double mont_e = mont_escompte;
                                int j = 0;
                                while (mont_e > 0) {
                                    EuDetailGcpPbf detgcp = gcp_pbfs.get(j);
                                    if (mont_e <= detgcp.getSoldeGcpPbf()) {

                                        detgcp.setMontPreleve(detgcp.getMontPreleve() + mont_e);
                                        detgcp.setSoldeGcpPbf(detgcp.getSoldeGcpPbf() - mont_e);
                                        detpbfRepo.save(detgcp);

                                        double agio_deduire = Math.round((mont_e * e.getTxAgioApplique()) / 100);

                                        EuGcpPbf gcp_pbf = gcppbfRepo.findOne(detgcp.getEuGcpPbf().getCodeGcpPbf());
                                        gcp_pbf.setAgioConsomme(gcp_pbf.getAgioConsomme() + agio_deduire);
                                        gcp_pbf.setGcpCompense(gcp_pbf.getGcpCompense() + mont_e);
                                        gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() - agio_deduire);
                                        gcp_pbf.setSoldeGcpReel(gcp_pbf.getSoldeGcpReel() - (mont_e - agio_deduire));
                                        gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() - mont_e);
                                        gcppbfRepo.save(gcp_pbf);

                                        mont_e = 0;
                                    } else {

                                        mont_e -= detgcp.getSoldeGcpPbf();

                                        double agio_deduire = Math
                                                .round((detgcp.getSoldeGcpPbf() * e.getTxAgioApplique()) / 100);

                                        EuGcpPbf gcp_pbf = gcppbfRepo.findOne(detgcp.getEuGcpPbf().getCodeGcpPbf());
                                        gcp_pbf.setAgioConsomme(gcp_pbf.getAgioConsomme() + agio_deduire);
                                        gcp_pbf.setGcpCompense(gcp_pbf.getGcpCompense() + detgcp.getSoldeGcpPbf());
                                        gcp_pbf.setSoldeAgio(gcp_pbf.getSoldeAgio() - agio_deduire);
                                        gcp_pbf.setSoldeGcpReel(
                                                gcp_pbf.getSoldeGcpReel() - (detgcp.getSoldeGcpPbf() - agio_deduire));
                                        gcp_pbf.setSoldeGcp(gcp_pbf.getSoldeGcp() - detgcp.getSoldeGcpPbf());
                                        gcppbfRepo.save(gcp_pbf);

                                        detgcp.setMontPreleve(detgcp.getMontPreleve() + detgcp.getSoldeGcpPbf());
                                        detgcp.setSoldeGcpPbf(0);
                                        detpbfRepo.save(detgcp);
                                        j++;
                                    }
                                }
                            }

                            EuMembreMorale pbf_acnev = moralRepo.findOne(codeMembreDest);
                            EuCompte compteDest = compteRepo.findOne(compens.getCodeComptepbf());
                            if (compteDest != null) {
                                compteDest.setSolde(compteDest.getSolde() + compens.getMontant());
                                compteRepo.save(compteDest);
                            } else {
                                compteDest = new EuCompte();
                                EuCategorieCompte cat = new EuCategorieCompte();
                                cat.setCodeCat(catDest);
                                EuTypeCompte typeCompte = new EuTypeCompte();
                                typeCompte.setCodeTypeCompte(typeNumDest);
                                compteDest.setCodeCompte(compens.getCodeComptepbf());
                                compteDest.setEuCategorieCompte(cat);
                                compteDest.setEuTypeCompte(typeCompte);
                                compteDest.setEuMembre(null);
                                compteDest.setEuMembreMorale(pbf_acnev);
                                compteDest.setDesactiver("N");
                                compteDest.setSolde(compens.getMontant());
                                compteDest.setDateAlloc(ldate);
                                compteRepo.save(compteDest);
                            }

                            Long idCompens = compensRepo.getLastCompensationInserted();
                            if (idCompens == null) {
                                idCompens = 1L;
                            } else {
                                idCompens++;
                            }

                            EuCompensation comp = new EuCompensation();
                            comp.setIdCompens(idCompens);
                            comp.setMontCompens(compens.getMontant());
                            comp.setDateCompens(date);
                            comp.setDateDeb(date);
                            comp.setDateFin(date.plusMonths(8));
                            comp.setDateDebTranche(date);
                            comp.setDateFinTranche(date.plusDays(30));
                            comp.setMontTranche(compens.getMontant() / 8);
                            comp.setMontEchu(0);
                            comp.setNtf(8);
                            comp.setResteNtf(8);
                            comp.setCodeMembreBenef(codeMembreDest);
                            comp.setEuCompte(compteDest);
                            comp.setEuMembreMorale(comptepbf.getEuMembreMorale());
                            comp.setPeriode(30);
                            compensRepo.save(comp);

                            comptepbf.setSolde(comptepbf.getSolde() - compens.getMontant());
                            compteRepo.save(comptepbf);
                            rep = "L'op�ration a �t� effectu�e avec succ�s";
                        } else {
                            rep = "Le solde des escomptes n'atteind pas le montant demande";
                        }
                    } else {
                        rep = "Le compte GCp du PBF n'existe pas ou son solde est insuffisant!!!";
                    }
                } else {
                    rep = "Ce membre n'est pas un PBF!!!";
                }
            } else {
                rep = "Pas d'escomptes s�lectionn�s";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rep = "Une erreur inattendue est survenue :" + e.getLocalizedMessage();
        }
        return rep;
    }

    @Override
    public Compensation getCompensationByCompte(String codeCompte) {
        Compensation compens = null;
        if (codeCompte.startsWith("NB-TSGCP")) {
            try {
                String codeMembre = ServerUtil.getCodeMembre(codeCompte);
                List<EuEscompte> escomptes = escRepo.findEscompteByMembre(codeMembre);
                if (escomptes != null && escomptes.size() > 0) {
                    compens = new Compensation();
                    Escompte escompte = null;
                    for (EuEscompte g : escomptes) {
                        escompte = new Escompte();
                        escompte.setCodeMembre(g.getCodeMembreBenef());
                        escompte.setIdEscompte(g.getIdEscompte());
                        escompte.setMontant(g.getSolde());
                        escompte.setNtf(g.getResteNtf().intValue());
                        escompte.setMont_tranche(g.getMontTranche());
                        escompte.setDatedeb(g.getDateDebTranche());
                        escompte.setDatefin(g.getDateFin());
                        compens.getEscomptes().add(escompte);
                    }
                    compens.setCodeCompte(codeCompte);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return compens;
    }

    @Override
    public Compensation getMprgByCompte(String codeCompte) {
        Compensation compens = null;
        if (codeCompte.startsWith("NB-TSGCP")) {
            try {
                String codeMembre = ServerUtil.getCodeMembre(codeCompte);
                List<EuEscompte> escomptes = escRepo.findEscompteEchuByCompte(codeMembre);
                if (escomptes != null && escomptes.size() > 0) {
                    compens = new Compensation();
                    Escompte escompte = null;
                    for (EuEscompte g : escomptes) {
                        escompte = new Escompte();
                        escompte.setCodeMembre(g.getCodeMembreBenef());
                        escompte.setIdEscompte(g.getIdEscompte());
                        escompte.setMontant(g.getMontEchu());
                        escompte.setNtf(g.getResteNtf().intValue());
                        escompte.setMont_tranche(g.getMontTranche());
                        escompte.setDatedeb(g.getDateDebTranche());
                        escompte.setDatefin(g.getDateFin());
                        compens.getEscomptes().add(escompte);
                    }
                    compens.setCodeCompte(codeCompte);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return compens;
    }

}
