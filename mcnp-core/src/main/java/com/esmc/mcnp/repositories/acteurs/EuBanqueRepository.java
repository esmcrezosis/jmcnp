package com.esmc.mcnp.repositories.acteurs;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuBanqueRepository extends BaseRepository<EuBanque, String> {

    public EuBanque findByCodeMembreMorale(String codeMembre);

    @Query("select b from EuBanque b where b.defaut =1")
    public EuBanque findByDefault();
}
