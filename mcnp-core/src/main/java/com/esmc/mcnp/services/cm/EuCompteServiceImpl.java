package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.components.CreditComponent;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.bc.EuCreditUtiliser;
import com.esmc.mcnp.model.cm.*;
import com.esmc.mcnp.repositories.bc.EuCreditUtiliserRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("euCompteService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCompteServiceImpl extends CrudServiceImpl<EuCompte, String> implements EuCompteService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final EuCompteRepository compteRepo;
    private final EuMembreRepository membreRepo;
    private final EuMembreMoraleRepository moraleRepo;
    private final EuCompteCreditRepository ccRepo;
    private final EuCreditUtiliserRepository ccUtilrepo;
    private final CreditComponent creditComponent;

    @Autowired
    public EuCompteServiceImpl(EuCompteRepository compteRepo, EuMembreRepository membreRepo,
                               EuMembreMoraleRepository moraleRepo, EuCompteCreditRepository ccRepo,
                               EuCreditUtiliserRepository ccUtilrepo, CreditComponent creditComponent) {
        super(compteRepo);
        this.compteRepo = compteRepo;
        this.membreRepo = membreRepo;
        this.moraleRepo = moraleRepo;
        this.ccRepo = ccRepo;
        this.ccUtilrepo = ccUtilrepo;
		this.creditComponent = creditComponent;
    }

    @Override
    public Page<EuCompte> findAll(Pageable pageable) {
        return compteRepo.findAll(pageable);
    }

    @Override
    public List<EuCompte> findCompteByMembre(String codeMembre) {
        if (codeMembre.endsWith("P")) {
            return compteRepo.findByEuMembre_CodeMembre(codeMembre);
        } else {
            return compteRepo.findByEuMembreMorale_CodeMembreMorale(codeMembre);
        }
    }

    @Override
    public Page<EuCompte> findCompteByMembre(String codeMembre, Pageable pageable) {
        if (codeMembre.endsWith("P")) {
            return compteRepo.findByEuMembre_CodeMembre(codeMembre, pageable);
        } else {
            return compteRepo.findByEuMembreMorale_CodeMembreMorale(codeMembre, pageable);
        }
    }

    @Override
    public EuCompte findCompteByMembreAndCategorie(String codeMembre, String codeCategorie) {
        if (codeMembre.endsWith("P")) {
            return compteRepo.findByEuMembre_CodeMembreAndEuCategorieCompte_CodeCat(codeMembre, codeCategorie);
        } else {
            return compteRepo.findByEuMembreMorale_CodeMembreMoraleAndEuCategorieCompte_CodeCat(codeMembre, codeCategorie);
        }
    }

    @Override
    public Page<EuCompte> findCompteByMembreAndCategorie(String codeMembre, String codeCategorie, Pageable pageable) {
        if (codeMembre.endsWith("P")) {
            return compteRepo.findByEuMembre_CodeMembreAndEuCategorieCompte_CodeCat(codeMembre, codeCategorie, pageable);
        } else {
            return compteRepo.findByEuMembreMorale_CodeMembreMoraleAndEuCategorieCompte_CodeCat(codeMembre, codeCategorie, pageable);
        }
    }

    @Override
    public Page<EuCompte> findCompteByCategorie(String codeCategorie, Pageable pageable) {
        return compteRepo.findByEuCategorieCompte_CodeCat(codeCategorie, pageable);
    }

    @Override
    public EuCompte findCompteByMembreAndType(String codeMemre, String typeCompte) {
        if (codeMemre.endsWith("P")) {
            return compteRepo.findByEuMembre_CodeMembreAndEuTypeCompte_codeTypeCompte(codeMemre, typeCompte);
        } else {
            return compteRepo.findByEuMembreMorale_CodeMembreMoraleAndEuTypeCompte_codeTypeCompte(codeMemre, typeCompte);
        }
    }

    @Override
    public Page<EuCompte> findCompteByType(String typeCompte, Pageable pageable) {
        return compteRepo.findByEuTypeCompte_CodeTypeCompte(typeCompte, pageable);
    }

    @Override
    public Page<EuCompte> findCompteByMembreAndType(String codeMemre, String typeCompte, Pageable pageable) {
        if (codeMemre.endsWith("P")) {
            return compteRepo.findByEuMembre_CodeMembreAndEuTypeCompte_codeTypeCompte(codeMemre, typeCompte, pageable);
        } else {
            return compteRepo.findByEuMembreMorale_CodeMembreMoraleAndEuTypeCompte_codeTypeCompte(codeMemre, typeCompte, pageable);
        }
    }

    @Override
    public EuCompte findCompteById(String id) {
        return compteRepo.findCompteById(id);
    }

    @Override
    public EuCompte createOrUpdate(String typeCompte, String catCompte, String codeMembre) {
        return createOrUpdate(typeCompte, catCompte, codeMembre, 0.0);
    }

    @Override
    public EuCompte createOrUpdate(String typeCompte, String catCompte, String codeMembre, Double montant) {
        String codeCompte = typeCompte + "-" + catCompte + "-" + codeMembre;
        EuCompte compte = compteRepo.findOne(codeCompte);
        if (Objects.isNull(compte)) {
            EuMembre membre = null;
            EuMembreMorale morale = null;
            if (codeMembre.endsWith("P")) {
                membre = membreRepo.findOne(codeMembre);
            } else {
                morale = moraleRepo.findOne(codeMembre);
            }
            compte = new EuCompte();
            EuCategorieCompte cat = new EuCategorieCompte();
            cat.setCodeCat(catCompte);
            EuTypeCompte tCompte = new EuTypeCompte(typeCompte);
            compte.setCodeCompte(codeCompte);
            compte.setEuCategorieCompte(cat);
            compte.setEuTypeCompte(tCompte);
            if (codeMembre.endsWith("M")) {
                compte.setEuMembreMorale(morale);
                compte.setEuMembre(null);
            } else {
                compte.setEuMembre(membre);
                compte.setEuMembreMorale(null);
            }
            compte.setLibCompte("Compte de " + catCompte);
            compte.setDesactiver("N");
            compte.setSolde(montant);
            compte.setDateAlloc(new Date());
            compte.setCardprinteddate(null);
            compte.setCardprintediddate(0);
            compte.setMifarecard(null);
            compte.setNumeroCarte(null);
            compte = create(compte);
            return compte;
        } else {
            if (montant > 0) {
                compte.setSolde(compte.getSolde() + montant);
                compte = update(compte);
            }
            return compte;
        }
    }

    @Override
    public void updateCompte(String codeCompte, String codeMembreBenef, String libelleOp, double montant) {
        if (StringUtils.isNotBlank(codeCompte)) {
            updateCompte(findCompteById(codeCompte), codeMembreBenef, libelleOp, montant);
        }
    }

    @Override
    public void updateCompte(String codeCompte, String codeMembre, List<Long> credits, String libelleOp, double montant) {
        if (StringUtils.isNotBlank(codeCompte)) {
            EuCompte compte = getById(codeCompte);
            updateCompte(compte, codeMembre, credits, libelleOp, montant);
        }
    }

    @Override
    public void updateCompte(EuCompte compte, String codeMembre, List<Long> credits, String libelleOp, double montant) {
        if (Objects.nonNull(compte) && StringUtils.isNotBlank(codeMembre) && !credits.isEmpty()) {
            if (compte.getSolde() >= montant) {
                compte.setSolde(compte.getSolde() - montant);
                update(compte);
                List<EuCompteCredit> ccredits = ccRepo.findAllByIdIn(credits, Sort.unsorted());
                int i = 0;
                while (i < credits.size() && montant > 0) {
                    EuCompteCredit cc = ccredits.get(i);
                    if (cc.getMontantCredit() >= montant) {
                        cc.setMontantCredit(cc.getMontantCredit() - montant);
                        ccRepo.save(cc);

                        EuCreditUtiliser ccUtil = new EuCreditUtiliser();
                        ccUtil.setCodeCompte(compte.getCodeCompte());
                        if (compte.getEuMembre() != null) {
                            ccUtil.setCodeMembre(compte.getEuMembre().getCodeMembre());
                        } else {
                            ccUtil.setCodeMembre(compte.getEuMembreMorale().getCodeMembreMorale());
                        }
                        ccUtil.setCodeMembreBenef(codeMembre);
                        ccUtil.setMontant(montant);
                        ccUtil.setLibelleOperation(libelleOp);
                        ccUtil.setDateOperation(LocalDateTime.now());
                        ccUtilrepo.save(ccUtil);

                        montant = 0;
                    } else {
                        EuCreditUtiliser ccUtil = new EuCreditUtiliser();
                        ccUtil.setCodeCompte(compte.getCodeCompte());
                        if (compte.getEuMembre() != null) {
                            ccUtil.setCodeMembre(compte.getEuMembre().getCodeMembre());
                        } else {
                            ccUtil.setCodeMembre(compte.getEuMembreMorale().getCodeMembreMorale());
                        }
                        ccUtil.setCodeMembreBenef(codeMembre);
                        ccUtil.setMontant(cc.getMontantCredit());
                        ccUtil.setLibelleOperation(libelleOp);
                        ccUtil.setDateOperation(LocalDateTime.now());
                        ccUtilrepo.save(ccUtil);

                        montant -= cc.getMontantCredit();

                        cc.setMontantCredit(0.0);
                        ccRepo.save(cc);
                        i++;
                    }
                }
            } else {
                throw new SoldeInsuffisantException("Votre solde est insuffisant pour effectuer cette opération");
            }
        }
    }
    
    @Override
    public void updateCompteWithCnp(EuCompte compte, String codeMembre, List<Long> credits, String libelleOp, double montant) {
        if (Objects.nonNull(compte) && StringUtils.isNotBlank(codeMembre) && !credits.isEmpty()) {
            if (compte.getSolde() >= montant) {
                compte.setSolde(compte.getSolde() - montant);
                update(compte);
                List<EuCompteCredit> ccredits = ccRepo.findAllByIdIn(credits, Sort.unsorted());
                int i = 0;
                while (i < credits.size() && montant > 0) {
                    EuCompteCredit cc = ccredits.get(i);
                    if (cc.getMontantCredit() >= montant) {
                        cc.setMontantCredit(cc.getMontantCredit() - montant);
                        ccRepo.save(cc);

                        EuCreditUtiliser ccUtil = new EuCreditUtiliser();
                        ccUtil.setCodeCompte(compte.getCodeCompte());
                        if (compte.getEuMembre() != null) {
                            ccUtil.setCodeMembre(compte.getEuMembre().getCodeMembre());
                        } else {
                            ccUtil.setCodeMembre(compte.getEuMembreMorale().getCodeMembreMorale());
                        }
                        ccUtil.setCodeMembreBenef(codeMembre);
                        ccUtil.setMontant(montant);
                        ccUtil.setLibelleOperation(libelleOp);
                        ccUtil.setDateOperation(LocalDateTime.now());
                        ccUtilrepo.save(ccUtil);
                        
                        creditComponent.updateCnpByCredit(cc.getIdCredit(), montant);

                        montant = 0;
                    } else {
                        EuCreditUtiliser ccUtil = new EuCreditUtiliser();
                        ccUtil.setCodeCompte(compte.getCodeCompte());
                        if (compte.getEuMembre() != null) {
                            ccUtil.setCodeMembre(compte.getEuMembre().getCodeMembre());
                        } else {
                            ccUtil.setCodeMembre(compte.getEuMembreMorale().getCodeMembreMorale());
                        }
                        ccUtil.setCodeMembreBenef(codeMembre);
                        ccUtil.setMontant(cc.getMontantCredit());
                        ccUtil.setLibelleOperation(libelleOp);
                        ccUtil.setDateOperation(LocalDateTime.now());
                        ccUtilrepo.save(ccUtil);

                        montant -= cc.getMontantCredit();
                        
                        creditComponent.updateCnpByCredit(cc.getIdCredit(), cc.getMontantCredit());

                        cc.setMontantCredit(0.0);
                        ccRepo.save(cc);
                        i++;
                    }
                }
            } else {
                throw new SoldeInsuffisantException("Votre solde est insuffisant pour effectuer cette opération");
            }
        }
    }

    @Override
    public void updateCompte(EuCompte compte, String codeMembreBenef, String libelleOp, double montant) {
        if (Objects.nonNull(compte) && compte.getSolde() >= montant) {
            compte.setSolde(compte.getSolde() - montant);
            update(compte);
            List<EuCompteCredit> credits = ccRepo.findByEuCompte_CodeCompte(compte.getCodeCompte());
            int i = 0;
            while (i < credits.size() && montant > 0) {
                EuCompteCredit cc = credits.get(i);
                if (cc.getMontantCredit() >= montant) {
                    cc.setMontantCredit(cc.getMontantCredit() - montant);
                    ccRepo.save(cc);

                    EuCreditUtiliser ccUtil = new EuCreditUtiliser();
                    ccUtil.setCodeCompte(compte.getCodeCompte());
                    if (compte.getEuMembre() != null) {
                        ccUtil.setCodeMembre(compte.getEuMembre().getCodeMembre());
                    } else {
                        ccUtil.setCodeMembre(compte.getEuMembreMorale().getCodeMembreMorale());
                    }
                    ccUtil.setCodeMembreBenef(codeMembreBenef);
                    ccUtil.setMontant(montant);
                    ccUtil.setLibelleOperation(libelleOp);
                    ccUtil.setDateOperation(LocalDateTime.now());
                    ccUtilrepo.save(ccUtil);

                    montant = 0;
                } else {
                    EuCreditUtiliser ccUtil = new EuCreditUtiliser();
                    ccUtil.setCodeCompte(compte.getCodeCompte());
                    if (compte.getEuMembre() != null) {
                        ccUtil.setCodeMembre(compte.getEuMembre().getCodeMembre());
                    } else {
                        ccUtil.setCodeMembre(compte.getEuMembreMorale().getCodeMembreMorale());
                    }
                    ccUtil.setCodeMembreBenef(codeMembreBenef);
                    ccUtil.setMontant(cc.getMontantCredit());
                    ccUtil.setLibelleOperation(libelleOp);
                    ccUtil.setDateOperation(LocalDateTime.now());
                    ccUtilrepo.save(ccUtil);

                    montant -= cc.getMontantCredit();

                    cc.setMontantCredit(0.0);
                    ccRepo.save(cc);
                    i++;
                }
            }
        }
    }

}
