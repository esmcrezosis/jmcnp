package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.dao.repository.cm.EuCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("euCompteCreditService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCompteCreditServiceImpl extends CrudServiceImpl<EuCompteCredit, Long> implements EuCompteCreditService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private EuCompteCreditRepository comptecreditRepo;
    private EuCompteRepository compteRepo;

    @Autowired
    public EuCompteCreditServiceImpl(EuCompteCreditRepository comptecreditRepo, EuCompteRepository compteRepo) {
        super(comptecreditRepo);
        this.comptecreditRepo = comptecreditRepo;
        this.compteRepo = compteRepo;
    }

    @Override
    public List<EuCompteCredit> findByCodeCompte(String codeCompte) {
        return comptecreditRepo.findByEuCompte_CodeCompte(codeCompte);
    }

    @Override
    public Page<EuCompteCredit> findByCodeCompte(String codeCompte, Pageable pageable) {
        return comptecreditRepo.findByEuCompte_CodeCompte(codeCompte, pageable);
    }

    @Override
    public List<EuCompteCredit> findByCompteAndTypeCredit(String codeCompte, String typeCredit) {
        return comptecreditRepo.findByCompteAndTypeCredit(codeCompte, typeCredit);
    }

    @Override
    public List<EuCompteCredit> findByCompteAndTypeCreditAndProduit(String codeCompte, String typeCredit,
                                                                    String produit) {
        return comptecreditRepo.findByCompteAndProduitAndCredit(codeCompte, produit, typeCredit);
    }

    @Override
    public List<EuCompteCredit> fetchByCompteAndTypeCreditAndProduit(String codeCompte, String typeCredit,
                                                                     String codeProduit) {
        return comptecreditRepo.findByCompteAndProduitAndCredit(codeCompte, codeProduit, typeCredit);
    }

    @Override
    public List<EuCompteCredit> fetchByCompteAndTypeCreditAndProduitAndPrk(String codeCompte, String typeCredit,
                                                                           String codeProduit, Double prk) {
        return comptecreditRepo.findByProduitAndCreditAndPrk(codeCompte, codeProduit, typeCredit, prk);
    }

    @Override
    public boolean verifierSolde(String codeCompte) {
        if (StringUtils.isNotBlank(codeCompte)) {
            EuCompte compte = compteRepo.findOne(codeCompte);
            System.out.println("Solde : " + compte.getSolde());
            if (compte.getSolde() < 0) {
                return false;
            }

            Double solde = comptecreditRepo.getSumByCodeCompte(codeCompte);
            System.out.println("Total Crédit : " + solde);
            return solde.equals(compte.getSolde());
        }
        return false;
    }

    @Override
    public boolean verifierCompte(String codeCompte) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean verifierSolde(String codeCompte, double montant) {
        if (StringUtils.isNotBlank(codeCompte)) {
            EuCompte compte = compteRepo.findOne(codeCompte);
            if (compte.getSolde() < 0) {
                throw new CompteNonIntegreException("Le solde de votre est négatif");
            }
            List<EuCompteCredit> ccs = findByCodeCompte(codeCompte);
            if (ccs.size() > 0) {
                Double solde = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
                if (solde.equals(compte.getSolde())) {
                    return montant <= compte.getSolde();
                }
            }
        }
        return false;
    }

    @Override
    public boolean verifierSolde(String codeCompte, String typeCredit, Double montant) {
        if (StringUtils.isNotBlank(codeCompte)) {
            EuCompte compte = compteRepo.findOne(codeCompte);
            if (Objects.nonNull(compte)) {
                if (compte.getSolde() < 0) {
                    throw new CompteNonIntegreException("Le solde de votre est négatif");
                }
                List<EuCompteCredit> ccs = findByCodeCompte(codeCompte);
                if (ccs.size() > 0) {
                    Double solde = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
                    if (solde.equals(compte.getSolde()) && compte.getSolde() >= montant) {
                        ccs = findByCompteAndTypeCredit(codeCompte, typeCredit);
                        solde = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
                        return solde >= montant;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean verifierSolde(String codeCompte, String typeCredit, String produit, Double montant) {
        if (StringUtils.isNotBlank(codeCompte)) {
            EuCompte compte = compteRepo.findOne(codeCompte);
            if (Objects.nonNull(compte)) {
                List<EuCompteCredit> ccs = findByCodeCompte(codeCompte);
                if (ccs.size() > 0) {
                    Double solde = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
                    if (solde.equals(compte.getSolde()) && compte.getSolde() >= montant) {
                        ccs = findByCompteAndTypeCreditAndProduit(codeCompte, typeCredit, produit);
                        solde = ccs.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
                        return solde >= montant;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<EuCompteCredit> findbyCompteAndProduit(String codeCompte, String codeProduit) {
        return comptecreditRepo.findbyCompteAndProduit(codeCompte, codeProduit);
    }

    @Override
    public Long getLastCreditInsertedId() {
        return comptecreditRepo.getLastCreditInsertedId();
    }

    @Override
    public List<EuCompteCredit> fetchByCompteAndProduitAndPrk(String codeCompte, String codeProduit, Double prk) {
        return comptecreditRepo.fetchByCompteAndProduitAndPrk(codeCompte, codeProduit, prk);
    }

    @Override
    public List<EuCompteCredit> findByTypeRecurrentAndTypeProduit(String codeCompte, String codeProduit,
                                                                  String typeRecurrent, String typeProduit) {
        return comptecreditRepo.findByTypeRecurrentAndTypeProduit(codeCompte, codeProduit, typeRecurrent, typeProduit);
    }

    @Override
    public List<EuCompteCredit> findByTypeRecurrentAndTypeProduitAndDuree(String codeCompte, String codeProduit,
                                                                          String typeRecurrent, String typeProduit, double duree) {
        return comptecreditRepo.findByTypeRecurrentAndTypeProduitAndDuree(codeCompte, codeProduit, typeRecurrent,
                typeProduit, duree);
    }

    @Override
    public Double getSommeCreditRecurrentByType(String codeCompte, String codeProduit, String typeRecurrent,
                                                String typeProduit) {
        return comptecreditRepo.getSommeCreditRecurrentByType(codeCompte, codeProduit, typeRecurrent, typeProduit);
    }

    @Override
    public Double getSommeCreditRecurrentByTypeAndDuree(String codeCompte, String codeProduit, String typeRecurrent,
                                                        String typeProduit, double duree) {
        return comptecreditRepo.getSommeCreditRecurrentByTypeAndDuree(codeCompte, codeProduit, typeRecurrent,
                typeProduit, duree);
    }

    @Override
    public Double getSumCreditByEuCompte(String codeCompte) {
        return comptecreditRepo.getSumCreditByEuCompte(codeCompte);
    }

    @Override
    public List<EuCompteCredit> fetchByCompteAndProduitAndPrk(String codeCompte, String codeProduit, Double prk,
                                                              String typeProduit) {
        return comptecreditRepo.fetchByCompteAndProduitAndPrk(codeCompte, codeProduit, prk, typeProduit);
    }

    @Override
    public List<EuCompteCredit> findByCompteandSource(String codeCompte, String source) {
        return comptecreditRepo.findByCompteandSource(codeCompte, source);
    }

    @Override
    public Page<EuCompteCredit> findCredits(String codeMembre, String typeRecurrent, Date dateDeb, Date dateFin,
                                            Pageable pageable) {
        return comptecreditRepo.findCredits(codeMembre, typeRecurrent, dateDeb, dateFin, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByCodeMembre(String codeMembre, Pageable pageable) {
        return comptecreditRepo.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByCodeMembreAndTypeRecurrent(String codeMembre, String typeRecurrent,
                                                                 Pageable pageable) {
        return comptecreditRepo.findByCodeMembreAndTypeRecurrent(codeMembre, typeRecurrent, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByTypeRecurrent(String typeRecurrent, Pageable pageable) {
        return comptecreditRepo.findByTypeRecurrent(typeRecurrent, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByDateOctroiBetween(Date dateDeb, Date dateFin, Pageable pageable) {
        return comptecreditRepo.findByDateOctroiBetween(dateDeb, dateFin, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByDatesup(Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByDateOctroiIsAfter(dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByMembreAndDateSup(String codeMembre, Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByCodeMembreAndDateOctroiIsAfter(codeMembre, dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByDateInf(Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByDateOctroiIsBefore(dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByMembreAndDateInf(String codeMembre, Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByCodeMembreAndDateOctroiIsBefore(codeMembre, dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByMembreAndDateBetween(String codeMembre, Date dateDeb, Date fin, Pageable pageable) {
        return comptecreditRepo.findByCodeMembreAndDateOctroiBetween(codeMembre, dateDeb, fin, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByTypeRecurrentAndDateSup(String typeRecurrent, Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByTypeRecurrentAndDateOctroiIsAfter(typeRecurrent, dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByTypeRecurrentAndDateInf(String typeRecurrent, Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByTypeRecurrentAndDateOctroiIsBefore(typeRecurrent, dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByTypeRecurrentAndDateBetween(String typeRecurrent, Date dateDeb, Date fin, Pageable pageable) {
        return comptecreditRepo.findByTypeRecurrentAndDateOctroiBetween(typeRecurrent, dateDeb, fin, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByProduitAndDateSup(String codeProduit, Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByEuProduit_CodeProduitAndDateOctroiIsAfter(codeProduit, dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByProduitAndDateInf(String codeProduit, Date dateDeb, Pageable pageable) {
        return comptecreditRepo.findByEuProduit_CodeProduitAndDateOctroiIsBefore(codeProduit, dateDeb, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByProduitAndDateBetween(String codeProduit, Date dateDeb, Date fin, Pageable pageable) {
        return comptecreditRepo.findByEuProduit_CodeProduitAndDateOctroiBetween(codeProduit, dateDeb, fin, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByProduit(String produit, Pageable pageable) {
        return comptecreditRepo.findByEuProduit_CodeProduit(produit, pageable);
    }

    @Override
    public Page<EuCompteCredit> findCreditByMembreAndTypeCurrentAndDate(String codeMembre, String typeRecurrent, Date dateDeb, Date dateFin, Pageable pageable) {
        return null;
    }

    @Override
    public Page<EuCompteCredit> findCreditByMembreAndProduitAndDate(String codeMembre, String codeProduit, Date dateDeb, Date dateFin, Pageable pageable) {
        return comptecreditRepo.findCreditByMembreAndProduitAndDate(codeMembre, codeProduit, dateDeb, dateFin, pageable);
    }

    @Override
    public Page<EuCompteCredit> fetchByMembreAndProduitAndPrk(String codeMembre, String codeProduit, Double prk, Pageable pageable) {
        return comptecreditRepo.fetchByMembreAndProduitAndPrk(codeMembre, codeProduit, prk, pageable);
    }

    @Override
    public Page<EuCompteCredit> fetchByMembreAndPrk(String codeMembre, Double prk, Pageable pageable) {
        return comptecreditRepo.fetchByMembreAndPrk(codeMembre, prk, pageable);
    }

    @Override
    public Page<EuCompteCredit> findByPrk(Double prk, Pageable pageable) {
        return comptecreditRepo.findByPrk(prk, pageable);
    }

    @Override
    public Page<EuCompteCredit> findCreditByMembreAndProduit(String codeMembre, String codeProduit, Pageable pageable) {
        return comptecreditRepo.findCreditByMembreAndProduit(codeMembre, codeProduit, pageable);
    }

    @Override
    public Page<EuCompteCredit> findAll(Pageable pageable) {
        return comptecreditRepo.findAll(pageable);
    }

    @Override
    public List<EuCompteCredit> findByCompteAndType(String codeCompte, String typeRecurrent) {
        return comptecreditRepo.findByCompteAndType(codeCompte, typeRecurrent);
    }

    @Override
    public Page<EuCompteCredit> findByCompteAndType(String codeCompte, String typeRecurrent, Pageable pageable) {
        return comptecreditRepo.findByCompteAndType(codeCompte, typeRecurrent, pageable);
    }

}
