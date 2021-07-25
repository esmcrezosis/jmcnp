package com.esmc.mcnp.repositories.oksu;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.oksu.EuRevendeur;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuRevendeurRepository extends BaseRepository<EuRevendeur, Long> {

    @Query("select max(r.idRevendeur) from EuRevendeur r")
    public Long getLastRevendeurInsertedId();

    @Query("select distinct r.nomProduit from EuRevendeur r")
    public List<String> getListeProduitsRevendeur();

}
