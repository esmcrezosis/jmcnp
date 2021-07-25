package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.dto.projections.BudgetVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.smcipn.EuBudget;
import com.esmc.mcnp.repositories.smcipn.EuBudgetRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuBudgetServiceImplImpl extends CrudServiceImpl<EuBudget, Long> implements EuBudgetService {

    protected EuBudgetRepository budgetRepository;

    public EuBudgetServiceImplImpl(EuBudgetRepository budgetRepository) {
        super(budgetRepository);
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<EuBudget> findByCentre_IdCentres(Integer idCentre) {
        return budgetRepository.findByCentre_IdCentres(idCentre);
    }

    @Override
    public Page<EuBudget> findByCentre_IdCentres(Integer idCentre, Pageable pageable) {
        return budgetRepository.findByCentre_IdCentres(idCentre, pageable);
    }

    @Override
    public List<EuBudget> findByCentreUserCentre(Integer idCentre) {
        return budgetRepository.findByCentreUserCentre(idCentre);
    }

    @Override
    public Page<EuBudget> findByCentreUserCentre(Integer idCentre, Pageable pageable) {
        return findByCentreUserCentre(idCentre, pageable);
    }

    @Override
    public List<EuBudget> findByCentre_CentreParent(Integer idCentre) {
        return budgetRepository.findByCentre_CentreParent_IdCentres(idCentre);
    }

    @Override
    public Page<EuBudget> findByCentre_CentreParent(Integer idCentre, Pageable pageable) {
        return budgetRepository.findByCentre_CentreParent_IdCentres(idCentre, pageable);
    }

    @Override
    public Page<EuBudget> findByUser(Long idUuser, Pageable page) {
        return budgetRepository.findByUser_IdUtilisateur(idUuser, page);
    }

    @Override
    public List<EuBudget> findByUser(Long idUuser) {
        return budgetRepository.findByUser_IdUtilisateur(idUuser);
    }

    @Override
    public Page<EuBudget> findByTypeBudget(Long idTypeBudget, Pageable page) {
        return budgetRepository.findByTypeBudget_Id(idTypeBudget, page);
    }

    @Override
    public List<EuBudget> findByTypeBudget(Long idTypeBudget) {
        return budgetRepository.findByTypeBudget_Id(idTypeBudget);
    }

    @Override
    public Page<EuBudget> findByTypeBudget(String codeTypeBudget, Pageable page) {
        return budgetRepository.findByTypeBudget_CodeTypeBudget(codeTypeBudget, page);
    }

    @Override
    public Page<EuBudget> findByValider(Boolean valider, Pageable pageable) {
        return budgetRepository.findByValider(valider, pageable);
    }

    @Override
    public List<EuBudget> findByTypeBudget(String codeTypeBudget) {
        return budgetRepository.findByTypeBudget_CodeTypeBudget(codeTypeBudget);
    }

    @Override
    public EuBudget findByCodeBudget(String codeBudget) {
        return budgetRepository.findByCodeBudget(codeBudget);
    }

    @Override
    public EuBudget findWithId(Long id) {
        return budgetRepository.findWithId(id);
    }

    @Override
    public List<BudgetVO> getAll() {
        return budgetRepository.getAll();
    }

}
