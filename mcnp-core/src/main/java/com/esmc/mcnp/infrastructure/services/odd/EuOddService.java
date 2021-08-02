package com.esmc.mcnp.infrastructure.services.odd;

import com.esmc.mcnp.domain.dto.odd.Odd;
import com.esmc.mcnp.domain.entity.odd.EuOdd;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

public interface EuOddService extends CrudService<EuOdd, Integer> {
    List<Odd> fetchAll();
}
