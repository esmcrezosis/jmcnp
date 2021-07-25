package com.esmc.mcnp.repositories.pc;

import com.esmc.mcnp.model.pc.EuCasPassif;
import com.esmc.mcnp.repositories.base.BaseRepository;

import java.util.List;

public interface EuCasPassifRepository extends BaseRepository<EuCasPassif, Integer> {
    List<EuCasPassif> findByEntreprise(String entreprise);
}
