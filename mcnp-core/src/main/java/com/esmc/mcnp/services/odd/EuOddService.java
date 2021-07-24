package com.esmc.mcnp.services.odd;

import com.esmc.mcnp.dto.odd.Odd;
import com.esmc.mcnp.model.odd.EuOdd;
import com.esmc.mcnp.services.base.CrudService;

import java.util.List;

public interface EuOddService extends CrudService<EuOdd, Integer> {
    List<Odd> fetchAll();
}
