package com.esmc.mcnp.services.smcipn;

import com.esmc.mcnp.model.smcipn.EuDetailBudget;
import com.esmc.mcnp.repositories.smcipn.EuDetailBudgetRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EuDetailBudgetServiceImplImpl extends CrudServiceImpl<EuDetailBudget, Long>
        implements EuDetailBudgetService {

    protected EuDetailBudgetRepository detBudgetRepository;

    public EuDetailBudgetServiceImplImpl(EuDetailBudgetRepository detBudgetRepository) {
        super(detBudgetRepository);
        this.detBudgetRepository = detBudgetRepository;
    }

    @Override
    public List<EuDetailBudget> findByUser(long idUtilisateur) {
        return detBudgetRepository.findByUser_IdUtilisateur(idUtilisateur);
    }

    @Override
    public Page<EuDetailBudget> findByUser(long idUtilisateur, Pageable pageable) {
        return detBudgetRepository.findByUser_IdUtilisateur(idUtilisateur, pageable);
    }

    @Override
    public List<EuDetailBudget> findByBudget(Long id) {
        return detBudgetRepository.findByBudget_id(id);
    }

    @Override
    public List<EuDetailBudget> findByBudget(String codeBudget) {
        return detBudgetRepository.findByBudget_CodeBudget(codeBudget);
    }

    @Override
    public Page<EuDetailBudget> findByBudget(Long id, Pageable pageable) {
        return detBudgetRepository.findByBudget_id(id, pageable);
    }

    @Override
    public Page<EuDetailBudget> findByBudget(String codeBudget, Pageable pageable) {
        return detBudgetRepository.findByBudget_CodeBudget(codeBudget, pageable);
    }

    @Override
    public List<EuDetailBudget> findByBudgetAndUser(String code, Long idUser) {
        return detBudgetRepository.findByBudget_CodeBudgetAndUser_IdUtilisateur(code, idUser);
    }

    @Override
    public Page<EuDetailBudget> findByBudgetAndUser(String code, Long idUser, Pageable pageable) {
        return detBudgetRepository.findByBudget_CodeBudgetAndUser_IdUtilisateur(code, idUser, pageable);
    }

    @Override
    public Optional<Double> getSumInitByBudget(String codeBudget) {
        return detBudgetRepository.getSumInitByBudget(codeBudget);
    }

    @Override
    public Optional<Double> getSumByBudget(String codeBudget) {
        return detBudgetRepository.getSumByBudget(codeBudget);
    }

    @Override
    public Optional<Double> getSumInitByBudget(Long idBudget) {
        return detBudgetRepository.getSumInitByBudget(idBudget);
    }

    @Override
    public Optional<Double> getSumByBudget(Long idBudget) {
        return detBudgetRepository.getSumByBudget(idBudget);
    }

    @Override
    public Page<EuDetailBudget> findByBudgetAndUser(Long id, Long idUser, Pageable pageable) {
        return detBudgetRepository.findByBudget_IdAndUser_IdUtilisateur(id, idUser, pageable);
    }
}
