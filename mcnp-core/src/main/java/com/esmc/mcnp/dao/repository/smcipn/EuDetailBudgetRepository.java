package com.esmc.mcnp.dao.repository.smcipn;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuDetailBudget;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EuDetailBudgetRepository extends BaseRepository<EuDetailBudget, Long> {

    List<EuDetailBudget> findByUser_IdUtilisateur(long idUtilisateur);

    Page<EuDetailBudget> findByUser_IdUtilisateur(long idUtilisateur, Pageable pageable);

    List<EuDetailBudget> findByBudget_id(Long id);

    List<EuDetailBudget> findByBudget_CodeBudget(String codeBudget);

    @Query("select sum(d.montantInitial) from EuDetailBudget d where d.budget.codeBudget like :code")
    Optional<Double> getSumInitByBudget(@Param("code") String codeBudget);

    @Query("select sum(d.montantBudget) from EuDetailBudget d where d.budget.codeBudget like :code")
    Optional<Double> getSumByBudget(@Param("code") String codeBudget);

    @Query("select sum(d.montantInitial) from EuDetailBudget d where d.budget.id = :id")
    Optional<Double> getSumInitByBudget(@Param("id") Long idBudget);

    @Query("select sum(d.montantBudget) from EuDetailBudget d where d.budget.id = :id")
    Optional<Double> getSumByBudget(@Param("id") Long idBudget);

    Page<EuDetailBudget> findByBudget_id(Long id, Pageable pageable);

    Page<EuDetailBudget> findByBudget_CodeBudget(String codeBudget, Pageable pageable);

    List<EuDetailBudget> findByBudget_CodeBudgetAndUser_IdUtilisateur(String code, Long idUser);

    Page<EuDetailBudget> findByBudget_CodeBudgetAndUser_IdUtilisateur(String code, Long idUser, Pageable pageable);

    Page<EuDetailBudget> findByBudget_IdAndUser_IdUtilisateur(Long id, Long idUser, Pageable pageable);
}
