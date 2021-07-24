package com.esmc.mcnp.repositories.acteurs;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.acteur.EuActeursCreneaux;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuActeurCreneauxRepository extends BaseRepository<EuActeursCreneaux, String> {

	@Query("select e from EuActeursCreneaux e join fetch e.euTypeActeur t join e.euMembreMorale m where m.codeMembreMorale = :code")
	public EuActeursCreneaux getActeurCreneauByCodemembre(@Param("code") String codemembre);
}
