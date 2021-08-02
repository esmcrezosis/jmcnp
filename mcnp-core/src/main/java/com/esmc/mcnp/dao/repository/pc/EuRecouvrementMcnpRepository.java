package com.esmc.mcnp.dao.repository.pc;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuRecouvrementMcnp;

/**
 * Created by USER on 23/05/2017.
 */
public interface EuRecouvrementMcnpRepository extends BaseRepository<EuRecouvrementMcnp, Integer> {
    List<EuRecouvrementMcnp> findByIdReleve(Integer idReleve);
}
