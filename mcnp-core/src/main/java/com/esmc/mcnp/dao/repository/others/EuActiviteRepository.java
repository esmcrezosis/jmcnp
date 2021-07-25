package com.esmc.mcnp.repositories.others;

import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.others.EuActivite;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuActiviteRepository extends BaseRepository<EuActivite, String> {

}
