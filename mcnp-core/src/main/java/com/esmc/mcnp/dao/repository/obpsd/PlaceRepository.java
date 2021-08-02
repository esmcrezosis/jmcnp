package com.esmc.mcnp.dao.repository.obpsd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.Place;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRepository extends BaseRepository<Place, Long> {
	List<Place> findByMembre(String membre);

	List<Place> findByMembreAndRembourser(String membre, boolean rembourser);

	List<Place> findByLib(String lib);

	List<Place> findByMembreAndLib(String membre, String lib);
	
	List<Place> findByMembreAndLibLike(String membre, String lib);
	
	List<Place> findByMembreAndLibAndRembourser(String membre, String lib, boolean rembourser);
	
	List<Place> findByMembreAndLibLikeAndRembourser(String membre, String lib, boolean rembourser);

	Page<Place> findByMembre(String membre, Pageable pageable);

	Page<Place> findByMembreAndRembourser(String membre, boolean rembourser, Pageable pageable);

	Page<Place> findByLib(String lib, Pageable pageable);

	Page<Place> findByMembreAndLib(String membre, String lib, Pageable pageable);
	
	Page<Place> findByMembreAndLibAndRembourser(String membre, String lib, boolean rembourser, Pageable pageable);
}
