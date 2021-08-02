package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.domain.dto.projections.CantonVO;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

public interface EuCantonService extends CrudService<EuCanton, Integer> {
    List<CantonVO> getAll();

    List<CantonVO> getAllByPrefecture(Integer id);

    List<CantonVO> getAllByPays(Integer idPays);

    List<CantonVO> getAllByRegion(Integer idRegion);
}
