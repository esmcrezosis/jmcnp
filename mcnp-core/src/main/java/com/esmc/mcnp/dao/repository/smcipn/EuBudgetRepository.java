package com.esmc.mcnp.dao.repository.smcipn;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.BudgetVO;
import com.esmc.mcnp.domain.entity.smcipn.EuBudget;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EuBudgetRepository extends BaseRepository<EuBudget, Long> {
    Page<EuBudget> findByUser_IdUtilisateur(Long idUuser, Pageable page);

    List<EuBudget> findByUser_IdUtilisateur(Long idUuser);

    Page<EuBudget> findByTypeBudget_Id(Long idTypeBudget, Pageable page);

    List<EuBudget> findByTypeBudget_Id(Long idTypeBudget);

    Page<EuBudget> findByTypeBudget_CodeTypeBudget(String codeTypeBudget, Pageable page);

    List<EuBudget> findByTypeBudget_CodeTypeBudget(String codeTypeBudget);

    EuBudget findByCodeBudget(String codeBudget);

    List<EuBudget> findByCentre_IdCentres(Integer idCentre);

    Page<EuBudget> findByCentre_IdCentres(Integer idCentre, Pageable pageable);

    @Query("select b from EuBudget b left join fetch b.typeBudget join b.centre c where c.user.centre.idCentres =:id")
    List<EuBudget> findByCentreUserCentre(@Param("id") Integer idCentre);

    @Query(value = "select b from EuBudget b left join fetch b.typeBudget join b.centre c where c.user.centre.idCentres =:id",
            countQuery = "select count(b) from EuBudget b left join b.typeBudget join b.centre c where c.user.centre.idCentres =:id")
    Page<EuBudget> findByCentreUserCentre(@Param("id") Integer idCentre, Pageable pageable);

    List<EuBudget> findByCentre_CentreParent_IdCentres(Integer idCentre);

    Page<EuBudget> findByCentre_CentreParent_IdCentres(Integer idCentre, Pageable pageable);

    @Query(value = "select b from EuBudget b left join fetch b.typeBudget where b.valider = :valider", countQuery = "select count(b) from EuBudget b left join b.typeBudget where b.valider = :valider")
    Page<EuBudget> findByValider(@Param("valider") Boolean valider, Pageable pageable);

    @Query("select b from EuBudget b join fetch b.typeBudget where b.id = :id")
    EuBudget findWithId(@Param("id") Long id);

    @Query("select new com.esmc.mcnp.domain.dto.projections.BudgetVO(b.id, b.codeBudget, b.nomBudget) from EuBudget b")
    List<BudgetVO> getAll();

}
