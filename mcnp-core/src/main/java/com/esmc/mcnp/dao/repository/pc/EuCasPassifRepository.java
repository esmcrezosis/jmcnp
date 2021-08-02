package com.esmc.mcnp.dao.repository.pc;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuCasPassif;

import java.util.List;

public interface EuCasPassifRepository extends BaseRepository<EuCasPassif, Integer> {
    List<EuCasPassif> findByEntreprise(String entreprise);
}
