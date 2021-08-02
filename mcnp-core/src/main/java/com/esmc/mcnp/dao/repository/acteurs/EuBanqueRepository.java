package com.esmc.mcnp.dao.repository.acteurs;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;

public interface EuBanqueRepository extends BaseRepository<EuBanque, String> {

    public EuBanque findByCodeMembreMorale(String codeMembre);

    @Query("select b from EuBanque b where b.defaut =1")
    public EuBanque findByDefault();
}
