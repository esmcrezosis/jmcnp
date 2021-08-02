package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.others.EuInformationAdditif;

/**
 * Created by USER on 23/05/2017.
 */
public interface EuInformationAdditifRepository extends BaseRepository<EuInformationAdditif, Long> {
	
	@Query("select a from EuInformationAdditif a where codeMembre like :codeMembre and reference like :reference")
	public List<EuInformationAdditif> findInformationAdditifByMembre(@Param("codeMembre") String codeMembre, @Param("reference") String reference);
}
