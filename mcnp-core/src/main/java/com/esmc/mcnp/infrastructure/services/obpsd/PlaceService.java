package com.esmc.mcnp.infrastructure.services.obpsd;

import com.esmc.mcnp.domain.entity.obpsd.Place;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService extends CrudService<Place, Long> {
    List<Place> findByMembre(String membre);

    List<Place> findByLib(String lib);

    List<Place> findByMembreAndLib(String membre, String lib);
    
    Page<Place> findByMembre(String membre, Pageable pageable);

    Page<Place> findByLib(String lib, Pageable pageable);

    Page<Place> findByMembreAndLib(String membre, String lib, Pageable pageable);
}
