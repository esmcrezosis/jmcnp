package com.esmc.mcnp.repositories.odd;

import com.esmc.mcnp.dto.odd.Odd;
import com.esmc.mcnp.model.odd.EuOdd;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EuOddRepository extends BaseRepository<EuOdd, Integer> {
    @Query("select new com.esmc.mcnp.dto.odd.Odd(o.idOdd, o.codeOdd, o.titre) from EuOdd o")
    List<Odd> getAll();
}
