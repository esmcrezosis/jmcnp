package com.esmc.mcnp.infrastructure.services.ba;

import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreDetailRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonNeutreDetailServiceImplImpl extends CrudServiceImpl<EuBonNeutreDetail, Integer>
        implements EuBonNeutreDetailService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private  EuBonNeutreDetailRepository bonNeutreRepo;

    protected EuBonNeutreDetailServiceImplImpl(EuBonNeutreDetailRepository bonNeutreRepo) {
        super(bonNeutreRepo);
        this.bonNeutreRepo = bonNeutreRepo;
    }

    @Override
    public List<EuBonNeutreDetail> findByMembre(String codeMembre) {
        return bonNeutreRepo.findByCodeMembre(codeMembre);
    }

    @Override
    public EuBonNeutreDetail findByCode(String code) {
        return bonNeutreRepo.findByCode(code);
    }

    @Override
    public Boolean verifierSoldebyMembre(String codeMembre, Double montant) {
        List<EuBonNeutreDetail> bons = findByMembre(codeMembre);
        double solde = 0;
        if (!bons.isEmpty()) {
            solde = bons.stream().mapToDouble(EuBonNeutreDetail::getBonNeutreDetailMontantSolde).sum();
        }
        return solde >= montant;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean updateBon(List<EuBonNeutreDetail> bons, Double montant) {
        if (!bons.isEmpty() && montant > 0) {
            try {
                double mont = montant;
                int compteur = 0;
                while (mont > 0 && compteur < bons.size()) {
                    EuBonNeutreDetail bon = bons.get(compteur);
                    if (bon.getBonNeutreDetailMontantSolde() < mont) {
                        mont -= bon.getBonNeutreDetailMontantSolde();
                        bon.setBonNeutreDetailMontantUtilise(
                                bon.getBonNeutreDetailMontantUtilise() + bon.getBonNeutreDetailMontantSolde());
                        bon.setBonNeutreDetailMontantSolde(0);
                        update(bon);
                        compteur++;
                    } else {
                        bon.setBonNeutreDetailMontantUtilise(bon.getBonNeutreDetailMontantUtilise() + mont);
                        bon.setBonNeutreDetailMontantSolde(bon.getBonNeutreDetailMontantSolde() - mont);
                        update(bon);
                        mont = 0;
                    }
                }
                return true;
            } catch (RuntimeException e) {
                return false;
            }

        }
        return false;
    }

    @Override
    public Boolean verifierSoldeByCode(String code, Double montant) {
        try {
            EuBonNeutreDetail bon = findByCode(code);
            if (Objects.nonNull(bon)) {
                return bon.getBonNeutreDetailMontantSolde() >= montant;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Page<EuBonNeutreDetail> getAll(Pageable pageable) {
        return bonNeutreRepo.getAll(pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByCodeMembre(String codeMembre, Pageable pageable) {
        return bonNeutreRepo.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByNom(String nom, Pageable pageable) {
        return bonNeutreRepo.findByNom(nom, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByNomAndPrenom(String nomMembre, String prenom, Pageable pageable) {
        return bonNeutreRepo.findByNomAndPrenom(nomMembre, prenom, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByRaisonSociale(String raison, Pageable pageable) {
        return bonNeutreRepo.findByRaisonSociale(raison, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByType(String type, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDetailType(type, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByBonNeutreCode(String code, Pageable pageable) {
        return bonNeutreRepo.findByEuBonNeutre_BonNeutreCode(code, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByBonNeutreId(Integer id, Pageable pageable) {
        return bonNeutreRepo.findByEuBonNeutre_BonNeutreId(id, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByDate(Date deb, Date fin, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDetailDateBetween(deb, fin, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByDateSup(Date deb, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDetailDateGreaterThan(deb, pageable);
    }

    @Override
    public Page<EuBonNeutreDetail> findByDateInf(Date deb, Pageable pageable) {
        return bonNeutreRepo.findByBonNeutreDetailDateLessThanEqual(deb, pageable);
    }

    @Override
    public boolean verifierSoldeByCode(String code) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean verifierSoldeByMembre(String codeMembre) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getLastInsertedId() {
        return bonNeutreRepo.getLastInsertedId();
    }

    @Override
    public double getSoldeByMembre(String codeMembre) {
        return bonNeutreRepo.getSoldeByCodeMembre(codeMembre).orElse(0.0);
    }

    @Override
    public double getSoldeByCode(String code) {
        return bonNeutreRepo.getSoldeByBonNeutreCode(code).orElse(0.0);
    }

    @Override
    public double getSoldeByBonNeutreId(Integer id) {
        return bonNeutreRepo.getSoldeByBonNeutreId(id).orElse(0.0);
    }

    @Override
    public Double getSumByCodeMembreAndDate(String codeMembre, Date date) {
        Optional<Double> opSum = bonNeutreRepo.getSumByCodeMembreAndDate(codeMembre, date);
        if (opSum.isPresent()) {
            return opSum.get();
        } else {
            return 0.0;
        }
    }

}
