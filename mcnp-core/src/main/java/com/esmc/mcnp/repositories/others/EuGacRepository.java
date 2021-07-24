package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.acteur.EuGac;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuGacRepository extends BaseRepository<EuGac, String> {

	public EuGac findByEuMembre_codeMembre(String codeMembre);

	public EuGac findByEuMembreMorale_codeMembreMorale(String codeMembre);

	@Query("select g from EuGac g join fetch g.euMembreMorale m where g.codeGac = :codeGac")
	public EuGac findByCodeGac(@Param("codeGac") String codeGac);

}
