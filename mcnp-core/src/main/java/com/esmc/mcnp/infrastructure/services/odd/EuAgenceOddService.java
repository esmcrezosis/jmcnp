package com.esmc.mcnp.services.odd;

import com.esmc.mcnp.dto.projections.AgencesOddVO;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.services.base.CrudService;
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
