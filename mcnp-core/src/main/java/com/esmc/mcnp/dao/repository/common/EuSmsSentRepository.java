package com.esmc.mcnp.dao.repository.common;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.sms.EuSmsSent;

public interface EuSmsSentRepository extends BaseRepository<EuSmsSent, Long>{

	 @Query("select max(s.neng) from EuSmsSent s")
	 public Long findMaxInsertedIdSmsSent();
}
