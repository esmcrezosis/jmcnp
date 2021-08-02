package com.esmc.mcnp.dao.repository.others;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuTelephone;

public interface EuTelephoneRepository extends BaseRepository<EuTelephone, Integer> {

	public List<EuTelephone> findByCodeMembreLike(String codeMembre);

	public List<EuTelephone> findByCodeMembreLikeAndCompagnieTelephoneLike(String codeMembre, String compagnie);
}
