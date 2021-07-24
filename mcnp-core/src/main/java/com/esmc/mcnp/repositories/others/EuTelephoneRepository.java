package com.esmc.mcnp.repositories.others;

import java.util.List;

import com.esmc.mcnp.model.cm.EuTelephone;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuTelephoneRepository extends BaseRepository<EuTelephone, Integer> {

	public List<EuTelephone> findByCodeMembreLike(String codeMembre);

	public List<EuTelephone> findByCodeMembreLikeAndCompagnieTelephoneLike(String codeMembre, String compagnie);
}
