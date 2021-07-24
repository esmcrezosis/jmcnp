package com.esmc.mcnp.repositories.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuNnRepository extends BaseRepository<EuNn, Integer> {

	public List<EuNn> findByEuMembreMorale_codeMembreMorale(String codeMembre);

	@Query("select n from EuNn n join n.euMembreMorale e where n.soldeNn > 0 and e.codeMembreMorale = :codeMembre")
	public List<EuNn> findNnByEmetteur(@Param("codeMembre") String codeMembre);

	@Query("select n from EuNn n where n.soldeNn > 0 and n.euMembreMorale is null and n.euTypeNn.codeTypeNn like :typeNn")
	public List<EuNn> findSourceNns(@Param("typeNn") String type_nn);

	@Query("select sum(n.soldeNn) from EuNn n where n.euTypeNn.codeTypeNn like :typeNn")
	public Double getSumNnByTypeNn(@Param("typeNn") String type_nn);

	@Query("select max(n.idNn) from EuNn n")
	public Long getLastInsertId();

	@Query("select n from EuNn n join n.euMembreMorale e join n.euTypeNn t where t.codeTypeNn like :typeNn  and e.codeMembreMorale = :codeMembre and n.typeEmission like :typeEmission")
	public List<EuNn> getListNnByTypeNnAndCodeMembreAndTypeEmission(@Param("typeNn") String type_nn,
			@Param("codeMembre") String codeMembre, @Param("typeEmission") String typeEmission);
}
