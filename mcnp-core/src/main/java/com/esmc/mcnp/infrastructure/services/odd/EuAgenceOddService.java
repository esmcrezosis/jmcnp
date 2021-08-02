package com.esmc.mcnp.infrastructure.services.odd;

import com.esmc.mcnp.domain.dto.projections.AgencesOddVO;
import com.esmc.mcnp.domain.entity.odd.EuAgencesOdd;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuAgenceOddService extends CrudService<EuAgencesOdd, Integer> {
    List<EuAgencesOdd> findByCentres(Integer id);

    EuAgencesOdd findBySource(Boolean source);

    Page<EuAgencesOdd> fetchAll(Pageable pageable);

    Page<EuAgencesOdd> fetchAll(Long id, Pageable pageable);

    List<AgencesOddVO> getAll();
}
