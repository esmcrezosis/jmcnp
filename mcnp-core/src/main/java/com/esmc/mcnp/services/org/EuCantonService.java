package com.esmc.mcnp.services.org;

import com.esmc.mcnp.dto.projections.CantonVO;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.services.base.CrudService;

import java.util.List;

public interface EuCantonService extends CrudService<EuCanton, Integer> {
    List<CantonVO> getAll();

    List<CantonVO> getAllByPrefecture(Integer id);

    List<CantonVO> getAllByPays(Integer idPays);

    List<CantonVO> getAllByRegion(Integer idRegion);
}
