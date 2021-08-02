package com.esmc.mcnp.infrastructure.services.ba;

import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreDetailRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreUtiliseRepository;
import com.esmc.mcnp.domain.entity.obpsd.*;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;
import com.esmc.mcnp.infrastructure.services.obpsd.EuFgfnService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("euBonNeutreService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonNeutreServiceImplImpl extends CrudServiceImpl<EuBonNeutre, Integer> implements EuBonNeutreService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private EuBonNeutreRepository bonNeutreRepo;
    private EuBonNeutreDetailRepository bnDetRepo;
    private EuBonNeutreUtiliseRepository bnUtilRepo;
    private EuFgfnService fgfnService;
    private EuBonNeutreApproService bnApproService;
    private EuBonNeutreApproDetailService bnApproDetService;

    @Autowired
    public EuBonNeutreServiceImplImpl(EuBonNeutreRepository bonNeutreRepo, EuBonNeutreDetailRepository bnDetRepo,
                                      EuBonNeutreUtiliseRepository bnUtilRepo, EuFgfnService fgfnService,
                                      EuBonNeutreApproService bnApproService, EuBonNeutreApproDetailService bnApproDetService) {
        super(bonNeutreRepo);
        this.bonNeutreRepo = bonNeutreRepo;
        this.bnDetRepo = bnDetRepo;
        this.bnUtilRepo = bnUtilRepo;
        this.fgfnService = fgfnService;
        this.bnApproService = bnApproService;
        this.bnApproDetService = bnApproDetService;
    }

    @Override
    public EuBonNeutre findByMembre(String codeMembre) {
        return bonNeutreRepo.findByBonNeutreCodeMembre(codeMembre);
    }

    @Override
    public EuBonNeutre findByCode(String code) {
        return bonNeutreRepo.findByBonNeutreCode(code);
    }

    @Override
    public Page<EuBonNeutre> findByDateBetween(Date deb, Date fin, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDateBetween(deb, fin, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByDateSup(Date deb, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDateGreaterThan(deb, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByDateInf(Date deb, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDateLessThanEqual(deb, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByType(String typeBn, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreType(typeBn, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByCodeMembre(String codeMembre, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByNomAndBonNeutrePrenom(String nom, String prenom, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreNomAndBonNeutrePrenom(nom, prenom, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByNom(String nom, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreNom(nom, pageable);
    }

    @Override
    public Page<EuBonNeutre> findByRaisonSociale(String raison, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreRaison(raison, pageable);
    }

    @Override
    public boolean updateBonNeutre(EuBonNeutre bonNeutre, String operation, String type, EuSmsmoney sms, double montant)
            throws SoldeInsuffisantException, CompteNonIntegreException, CompteNonTrouveException, DataAccessException {
        if (Objects.nonNull(bonNeutre) && bonNeutre.getBonNeutreMontantSolde() >= montant) {
            List<EuBonNeutreDetail> bnDetails = bnDetRepo.findByCodeMembre(bonNeutre.getBonNeutreCodeMembre());
            double somBnDet = bnDetails.stream().mapToDouble(EuBonNeutreDetail::getBonNeutreDetailMontantSolde).sum();
            if (bonNeutre.getBonNeutreMontantSolde().equals(somBnDet)) {
                bonNeutre.setBonNeutreMontantUtilise(bonNeutre.getBonNeutreMontantUtilise() + montant);
                bonNeutre.setBonNeutreMontantSolde(bonNeutre.getBonNeutreMontantSolde() - montant);
                update(bonNeutre);

                double mont_a_deduire = montant;
                int compteur = 0;
                while (mont_a_deduire > 0 && compteur < bnDetails.size()) {
                    EuBonNeutreDetail bnDetail = bnDetails.get(compteur);
                    if (bnDetail.getBonNeutreDetailMontantSolde() >= mont_a_deduire) {
                        bnDetail.setBonNeutreDetailMontantUtilise(
                                bnDetail.getBonNeutreDetailMontantUtilise() + mont_a_deduire);
                        bnDetail.setBonNeutreDetailMontantSolde(
                                bnDetail.getBonNeutreDetailMontantSolde() - mont_a_deduire);
                        bnDetRepo.save(bnDetail);

                        EuBonNeutreUtilise bnUtilise = new EuBonNeutreUtilise();
                        bnUtilise.setBonNeutreUtiliseDate(new Date());
                        bnUtilise.setBonNeutreUtiliseLibelle(operation);
                        bnUtilise.setBonNeutreUtiliseMontant(mont_a_deduire);
                        bnUtilise.setEuBonNeutre(bonNeutre);
                        bnUtilise.setBonNeutreUtiliseType(type);
                        bnUtilise.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());
                        bnUtilRepo.save(bnUtilise);

                        fgfnService.createFgfnByBonNeutreDetail(bnDetail, sms.getCreditcode(), mont_a_deduire);

                        mont_a_deduire = 0;
                    } else {
                        mont_a_deduire -= bnDetail.getBonNeutreDetailMontantSolde();

                        EuBonNeutreUtilise bnUtilise = new EuBonNeutreUtilise();
                        bnUtilise.setBonNeutreUtiliseDate(new Date());
                        bnUtilise.setBonNeutreUtiliseLibelle(operation);
                        bnUtilise.setBonNeutreUtiliseMontant(bnDetail.getBonNeutreDetailMontantSolde());
                        bnUtilise.setEuBonNeutre(bonNeutre);
                        bnUtilise.setBonNeutreUtiliseType(null);
                        bnUtilise.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());
                        bnUtilRepo.save(bnUtilise);

                        fgfnService.createFgfnByBonNeutreDetail(bnDetail, sms.getCreditcode(),
                                bnDetail.getBonNeutreDetailMontantSolde());

                        bnDetail.setBonNeutreDetailMontantUtilise(bnDetail.getBonNeutreDetailMontantUtilise()
                                + bnDetail.getBonNeutreDetailMontantSolde());
                        bnDetail.setBonNeutreDetailMontantSolde(0);
                        compteur++;
                    }
                }
                return true;
            } else {
                throw new CompteNonIntegreException("Le solde du Bon neutre N° " + bonNeutre.getBonNeutreId() + " de "
                        + bonNeutre.getBonNeutreMontantSolde() + " est différent de la somme des détails qui est "
                        + somBnDet);
            }
        } else {
            throw new SoldeInsuffisantException("Le solde du Bon neutre N° " + bonNeutre.getBonNeutreId() + " de "
                    + bonNeutre.getBonNeutreMontantSolde() + " est insufisant pour faire cette opération de "
                    + montant);
        }
    }

    @Override
    public int getLastInsertedId() {
        return bonNeutreRepo.getLastInserted();
    }

    @Override
    public boolean updateBonNeutre(EuBonNeutre bonNeutre, String operation, String type, double montant) {
        if (Objects.nonNull(bonNeutre) && bonNeutre.getBonNeutreMontantSolde() >= montant) {
            List<EuBonNeutreDetail> bnDetails = bnDetRepo.findByCodeMembre(bonNeutre.getBonNeutreCodeMembre());
            double somBnDet = bnDetails.stream().mapToDouble(EuBonNeutreDetail::getBonNeutreDetailMontantSolde).sum();
            if (bonNeutre.getBonNeutreMontantSolde().equals(somBnDet)) {
                bonNeutre.setBonNeutreMontantUtilise(bonNeutre.getBonNeutreMontantUtilise() + montant);
                bonNeutre.setBonNeutreMontantSolde(bonNeutre.getBonNeutreMontantSolde() - montant);
                update(bonNeutre);

                double mont_a_deduire = montant;
                int compteur = 0;
                while (mont_a_deduire > 0 && compteur < bnDetails.size()) {
                    EuBonNeutreDetail bnDetail = bnDetails.get(compteur);
                    if (bnDetail.getBonNeutreDetailMontantSolde() >= mont_a_deduire) {
                        bnDetail.setBonNeutreDetailMontantUtilise(
                                bnDetail.getBonNeutreDetailMontantUtilise() + mont_a_deduire);
                        bnDetail.setBonNeutreDetailMontantSolde(
                                bnDetail.getBonNeutreDetailMontantSolde() - mont_a_deduire);
                        bnDetRepo.save(bnDetail);

                        EuBonNeutreUtilise bnUtilise = new EuBonNeutreUtilise();
                        bnUtilise.setBonNeutreUtiliseDate(new Date());
                        bnUtilise.setBonNeutreUtiliseLibelle(operation);
                        bnUtilise.setBonNeutreUtiliseMontant(mont_a_deduire);
                        bnUtilise.setEuBonNeutre(bonNeutre);
                        bnUtilise.setBonNeutreUtiliseType(type);
                        bnUtilise.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());
                        bnUtilRepo.save(bnUtilise);

                        mont_a_deduire = 0;
                    } else {
                        mont_a_deduire -= bnDetail.getBonNeutreDetailMontantSolde();

                        EuBonNeutreUtilise bnUtilise = new EuBonNeutreUtilise();
                        bnUtilise.setBonNeutreUtiliseDate(new Date());
                        bnUtilise.setBonNeutreUtiliseLibelle(operation);
                        bnUtilise.setBonNeutreUtiliseMontant(bnDetail.getBonNeutreDetailMontantSolde());
                        bnUtilise.setEuBonNeutre(bonNeutre);
                        bnUtilise.setBonNeutreUtiliseType(null);
                        bnUtilise.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());
                        bnUtilRepo.save(bnUtilise);

                        bnDetail.setBonNeutreDetailMontantUtilise(bnDetail.getBonNeutreDetailMontantUtilise()
                                + bnDetail.getBonNeutreDetailMontantSolde());
                        bnDetail.setBonNeutreDetailMontantSolde(0);
                        compteur++;
                    }
                }
                return true;
            } else {
                throw new CompteNonIntegreException("Le solde du Bon neutre N° " + bonNeutre.getBonNeutreId() + " de "
                        + bonNeutre.getBonNeutreMontantSolde() + " est différent de la somme des détails qui est "
                        + somBnDet);
            }
        } else {
            throw new SoldeInsuffisantException("Le solde du Bon neutre N° " + bonNeutre.getBonNeutreId() + " de "
                    + bonNeutre.getBonNeutreMontantSolde() + " est insufisant pour faire cette opération de "
                    + montant);
        }
    }

    @Override
    public EuBonNeutreAppro updateBonNeutreAppro(EuBonNeutre bonNeutre, String operation, String type, String codeMembreBenef,
                                                 double montant) {
        if (Objects.nonNull(bonNeutre) && bonNeutre.getBonNeutreMontantSolde() >= montant) {
            List<EuBonNeutreDetail> bnDetails = bnDetRepo.findByCodeMembre(bonNeutre.getBonNeutreCodeMembre());
            double somBnDet = bnDetails.stream().mapToDouble(EuBonNeutreDetail::getBonNeutreDetailMontantSolde).sum();
            if (bonNeutre.getBonNeutreMontantSolde().equals(somBnDet)) {

                bonNeutre.setBonNeutreMontantUtilise(bonNeutre.getBonNeutreMontantUtilise() + montant);
                bonNeutre.setBonNeutreMontantSolde(bonNeutre.getBonNeutreMontantSolde() - montant);
                update(bonNeutre);

                EuBonNeutreAppro bnAppro = new EuBonNeutreAppro();
                bnAppro.setBonNeutreApproApporteur(bonNeutre.getBonNeutreCodeMembre());
                bnAppro.setBonNeutreApproBeneficiaire(codeMembreBenef);
                bnAppro.setBonNeutreApproDate(new Date());
                bnAppro.setBonNeutreApproMontant(montant);
                bnAppro = bnApproService.add(bnAppro);

                double mont_a_deduire = montant;
                int compteur = 0;
                while (mont_a_deduire > 0 && compteur < bnDetails.size()) {
                    EuBonNeutreDetail bnDetail = bnDetails.get(compteur);
                    if (bnDetail.getBonNeutreDetailMontantSolde() >= mont_a_deduire) {
                        bnDetail.setBonNeutreDetailMontantUtilise(
                                bnDetail.getBonNeutreDetailMontantUtilise() + mont_a_deduire);
                        bnDetail.setBonNeutreDetailMontantSolde(
                                bnDetail.getBonNeutreDetailMontantSolde() - mont_a_deduire);
                        bnDetRepo.save(bnDetail);

                        EuBonNeutreUtilise bnUtilise = new EuBonNeutreUtilise();
                        bnUtilise.setBonNeutreUtiliseDate(new Date());
                        bnUtilise.setBonNeutreUtiliseLibelle(operation);
                        bnUtilise.setBonNeutreUtiliseMontant(mont_a_deduire);
                        bnUtilise.setEuBonNeutre(bonNeutre);
                        bnUtilise.setBonNeutreUtiliseType(type);
                        bnUtilise.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());
                        bnUtilRepo.save(bnUtilise);

                        EuBonNeutreApproDetailPK bnApproDetId = new EuBonNeutreApproDetailPK();
                        bnApproDetId.setBonNeutreApproId(bnAppro.getBonNeutreApproId());
                        bnApproDetId.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());

                        EuBonNeutreApproDetail bnApproDet = new EuBonNeutreApproDetail();
                        bnApproDet.setId(bnApproDetId);
                        bnApproDet.setBonNeutreApproDetailDate(new Date());
                        bnApproDet.setBonNeutreApproDetailBanque(bnDetail.getBonNeutreDetailBanque());
                        bnApproDet.setBonNeutreApproDetailMontant(mont_a_deduire);
                        bnApproDet.setBonNeutreApproDetailMontUtilise(mont_a_deduire);
                        bnApproDet.setBonNeutreApproDetailSolde(0.0);
                        bnApproDetService.add(bnApproDet);

                        mont_a_deduire = 0;
                    } else {
                        mont_a_deduire -= bnDetail.getBonNeutreDetailMontantSolde();

                        EuBonNeutreUtilise bnUtilise = new EuBonNeutreUtilise();
                        bnUtilise.setBonNeutreUtiliseDate(new Date());
                        bnUtilise.setBonNeutreUtiliseLibelle(operation);
                        bnUtilise.setBonNeutreUtiliseMontant(bnDetail.getBonNeutreDetailMontantSolde());
                        bnUtilise.setEuBonNeutre(bonNeutre);
                        bnUtilise.setBonNeutreUtiliseType(null);
                        bnUtilise.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());
                        bnUtilRepo.save(bnUtilise);

                        bnDetail.setBonNeutreDetailMontantUtilise(bnDetail.getBonNeutreDetailMontantUtilise()
                                + bnDetail.getBonNeutreDetailMontantSolde());
                        bnDetail.setBonNeutreDetailMontantSolde(0);

                        EuBonNeutreApproDetailPK bnApproDetId = new EuBonNeutreApproDetailPK();
                        bnApproDetId.setBonNeutreApproId(bnAppro.getBonNeutreApproId());
                        bnApproDetId.setBonNeutreDetailId(bnDetail.getBonNeutreDetailId());

                        EuBonNeutreApproDetail bnApproDet = new EuBonNeutreApproDetail();
                        bnApproDet.setId(bnApproDetId);
                        bnApproDet.setBonNeutreApproDetailDate(new Date());
                        bnApproDet.setBonNeutreApproDetailBanque(bnDetail.getBonNeutreDetailBanque());
                        bnApproDet.setBonNeutreApproDetailMontant(bnDetail.getBonNeutreDetailMontantSolde());
                        bnApproDet.setBonNeutreApproDetailMontUtilise(bnDetail.getBonNeutreDetailMontantSolde());
                        bnApproDet.setBonNeutreApproDetailSolde(0.0);
                        bnApproDetService.add(bnApproDet);

                        compteur++;
                    }
                }
                return bnAppro;
            } else {
                throw new CompteNonIntegreException("Le solde du Bon neutre N° " + bonNeutre.getBonNeutreId() + " de "
                        + bonNeutre.getBonNeutreMontantSolde() + " est différent de la somme des détails qui est "
                        + somBnDet);
            }
        } else {
            throw new SoldeInsuffisantException("Le solde du Bon neutre N° " + bonNeutre.getBonNeutreId() + " de "
                    + bonNeutre.getBonNeutreMontantSolde() + " est insufisant pour faire cette opération de "
                    + montant);
        }
    }

}
