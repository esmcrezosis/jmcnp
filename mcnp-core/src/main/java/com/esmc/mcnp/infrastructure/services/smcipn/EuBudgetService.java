package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.dto.projections.BudgetVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.smcipn.EuBudget;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EuBudgetService extends CrudService<EuBudget, Long> {

    List<EuBudget> findByCentre_IdCentres(Integer idCentre);

    Page<EuBudget> findByCentre_IdCentres(Integer idCentre, Pageable pageable);

    List<EuBudget> findByCentreUserCentre(Integer idCentre);

    Page<EuBudget> findByCentreUserCentre(Integer idCentre, Pageable pageable);

    List<EuBudget> findByCentre_CentreParent(Integer idCentre);

    Page<EuBudget> findByCentre_CentreParent(Integer idCentre, Pageable pageable);

    Page<EuBudget> findByUser(Long idUuser, Pageable page);

    List<EuBudget> findByUser(Long idUuser);

    Page<EuBudget> findByTypeBudget(Long idTypeBudget, Pageable page);

    List<EuBudget> findByTypeBudget(Long idTypeBudget);

    Page<EuBudget> findByTypeBudget(String codeTypeBudget, Pageable page);

    Page<EuBudget> findByValider(Boolean valider, Pageable pageable);

    List<EuBudget> findByTypeBudget(String codeTypeBudget);

    EuBudget findByCodeBudget(String codeBudget);

    EuBudget findWithId(Long id);

    List<BudgetVO> getAll();
}
