package com.esmc.mcnp.repositories.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.sms.EuSmsConnexion;
import com.esmc.mcnp.repositories.base.BaseRepository;


public interface EuSmsConnexionRepository extends BaseRepository<EuSmsConnexion, Long> {

	@Query("select max(sm.smsConnexionId) from EuSmsConnexion sm")
	public Long getLastInsertId();
	
	@Query("select sm from EuSmsConnexion sm where sm.smsConnexionCodeEnvoi like :code")
	public EuSmsConnexion getSmsConnexionByCodeEnvoi( @Param("code") String codeEnvoi);
	
	@Query("select sm.smsConnexionCodeRecu from EuSmsConnexion sm where sm.smsConnexionCodeMembre like :membre and sm.smsConnexionCodeEnvoi like :code and sm.smsConnexionUtilise=0")
	public String getCodeRecuSmsConnexionByCodeEnvoi( @Param("membre") String codeMembre, @Param("code") String codeEnvoi);
}
