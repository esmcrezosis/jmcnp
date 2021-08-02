package com.esmc.mcnp.dao.repository.oksu;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.oksu.EuRevendeur;

public interface EuRevendeurRepository extends BaseRepository<EuRevendeur, Long> {

    @Query("select max(r.idRevendeur) from EuRevendeur r")
    public Long getLastRevendeurInsertedId();

    @Query("select distinct r.nomProduit from EuRevendeur r")
    public List<String> getListeProduitsRevendeur();

}
