package com.esmc.mcnp.repositories.acteurs;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.acteur.EuDetailEli;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDetailEliRepository extends BaseRepository<EuDetailEli, Integer> {

    List<EuDetailEli> findAllByIdEliAndStatut(Integer idEli, Integer statut);
    
    List<EuDetailEli> findAllByIdEli(Integer idEli);

    @Query("select sum(d.montantProduit) from EuDetailEli d where d.idEli = :eli and d.statut = :statut")
    double getSumByEliAndStatut(@Param("eli") Integer idEli, @Param("statut") Integer statut);
    
    @Query("select sum(d.montantProduit) from EuDetailEli d where d.idEli = :eli")
    double getSumByEli(@Param("eli") Integer idEli);
}
