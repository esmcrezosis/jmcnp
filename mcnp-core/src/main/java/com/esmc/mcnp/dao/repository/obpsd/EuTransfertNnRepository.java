package com.esmc.mcnp.repositories.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obpsd.EuTransfertNn;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuTransfertNnRepository extends BaseRepository<EuTransfertNn, Long> {

    public List<EuTransfertNn> findByEuCompteDist_CodeCompte(String codeCompte);

    @Query("select t from EuTransfertNn t where t.soldeTransfert > 0 and t.euCompteDist.codeCompte = :code")
    public List<EuTransfertNn> findTransfertbyCodeCompte(@Param("code") String codeCompte);

    @Query("select sum(t.soldeTransfert) from EuTransfertNn t where t.soldeTransfert > 0 and t.euCompteDist.codeCompte = :code and t.euTypeNn.codeTypeNn = :type")
    public Double FindSoldeNnByTypeNn(@Param("code") String codeCompte, @Param("type") String TypeNn);

    @Query("select sum(t.soldeTransfert) from EuTransfertNn t where t.soldeTransfert > 0 and t.euCompteDist.codeCompte = :code")
    public Double FindSoldeNnByCompte(@Param("code") String codeCompte);

    @Query("select t from EuTransfertNn t where t.soldeTransfert > 0 and t.euCompteDist.codeCompte = :code and t.euTypeNn.codeTypeNn = :type")
    public List<EuTransfertNn> findByTypeNnAndCompte(@Param("code") String codeCompte, @Param("type") String typeNn);

    @Query("select max(t.idTransfertNn) from EuTransfertNn t")
    public Long getLastTransfertInsertId();
}
