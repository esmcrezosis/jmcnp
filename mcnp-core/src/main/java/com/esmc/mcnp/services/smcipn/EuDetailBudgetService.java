package com.esmc.mcnp.services.smcipn;

import com.esmc.mcnp.model.smcipn.EuDetailBudget;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EuDetailBudgetService extends CrudService<EuDetailBudget, Long> {

    List<EuDetailBudget> findByUser(long idUtilisateur);

    Page<EuDetailBudget> findByUser(long idUtilisateur, Pageable pageable);

    List<EuDetailBudget> findByBudget(Long id);

    List<EuDetailBudget> findByBudget(String codeBudget);

    Page<EuDetailBudget> findByBudget(Long id, Pageable pageable);

    Page<EuDetailBudget> findByBudget(String codeBudget, Pageable pageable);

    List<EuDetailBudget> findByBudgetAndUser(String code, Long idUser);

    Page<EuDetailBudget> findByBudgetAndUser(String code, Long idUser, Pageable pageable);

    Optional<Double> getSumInitByBudget(String codeBudget);

    Optional<Double> getSumByBudget(String codeBudget);

    Optional<Double> getSumInitByBudget(Long idBudget);

    Optional<Double> getSumByBudget(Long idBudget);

    Page<EuDetailBudget> findByBudgetAndUser(Long id, Long idUser, Pageable pageable);
}
