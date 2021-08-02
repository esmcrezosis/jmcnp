package com.esmc.mcnp.dao.repository.odd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.odd.Odd;
import com.esmc.mcnp.domain.entity.odd.EuOdd;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EuOddRepository extends BaseRepository<EuOdd, Integer> {
    @Query("select new com.esmc.mcnp.domain.dto.odd.Odd(o.idOdd, o.codeOdd, o.titre) from EuOdd o")
    List<Odd> getAll();
}
