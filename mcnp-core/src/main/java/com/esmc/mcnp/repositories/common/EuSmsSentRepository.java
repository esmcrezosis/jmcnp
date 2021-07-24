package com.esmc.mcnp.repositories.common;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.sms.EuSmsSent;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuSmsSentRepository extends BaseRepository<EuSmsSent, Long>{

	 @Query("select max(s.neng) from EuSmsSent s")
	 public Long findMaxInsertedIdSmsSent();
}
