package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.model.obpsd.Place;
import com.esmc.mcnp.repositories.obpsd.PlaceRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlaceServiceImpl extends CrudServiceImpl<Place, Long> implements PlaceService {

    private final PlaceRepository placeRepository;

    protected PlaceServiceImpl(PlaceRepository placeRepository, PlaceRepository placeRepository1) {
        super(placeRepository);
        this.placeRepository = placeRepository1;
    }

    @Override
    public List<Place> findByMembre(String membre) {
        return placeRepository.findByMembre(membre);
    }

    @Override
    public List<Place> findByLib(String lib) {
        return placeRepository.findByLib(lib);
    }

    @Override
    public List<Place> findByMembreAndLib(String membre, String lib) {
        return placeRepository.findByMembreAndLib(membre, lib);
    }

	@Override
	public Page<Place> findByMembre(String membre, Pageable pageable) {
		return placeRepository.findByMembre(membre, pageable);
	}

	@Override
	public Page<Place> findByLib(String lib, Pageable pageable) {
		return placeRepository.findByLib(lib, pageable);
	}

	@Override
	public Page<Place> findByMembreAndLib(String membre, String lib, Pageable pageable) {
		return placeRepository.findByMembreAndLib(membre, lib, pageable);
	}
}
