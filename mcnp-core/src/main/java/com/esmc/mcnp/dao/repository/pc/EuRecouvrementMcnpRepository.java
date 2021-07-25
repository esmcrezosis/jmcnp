package com.esmc.mcnp.repositories.pc;

import java.util.List;

import com.esmc.mcnp.model.pc.EuRecouvrementMcnp;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 * Created by USER on 23/05/2017.
 */
public interface EuRecouvrementMcnpRepository extends BaseRepository<EuRecouvrementMcnp, Integer> {
    List<EuRecouvrementMcnp> findByIdReleve(Integer idReleve);
}
