package com.esmc.mcnp.infrastructure.services.odd;

import com.esmc.mcnp.domain.dto.projections.CentreVO;
import com.esmc.mcnp.domain.entity.odd.EuCentres;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuCentresService extends CrudService<EuCentres, Integer> {

    List<CentreVO> getAllVO();

    List<EuCentres> getAll();

    Page<EuCentres> getAll(Pageable pageable);

    List<EuCentres> findByUser(Long idUser);

    Page<EuCentres> findByUser(Long idUser, Pageable pageable);
}
