package com.esmc.mcnp.infrastructure.services.bc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.bc.EuCreditConsommerRepository;
import com.esmc.mcnp.domain.entity.obps.EuCreditConsommer;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;


@Service("euCreditConsommerService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCreditConsommerServiceImpl extends BaseServiceImpl<EuCreditConsommer, Long> implements  EuCreditConsommerService{

	private static final long serialVersionUID = 1L;
	private @Autowired EuCreditConsommerRepository creditConsommerRepo;

	@Override
	protected BaseRepository<EuCreditConsommer, Long> getRepository() {
		return creditConsommerRepo;
	}

	@Override
	public Long getLastEuConsommationInsertedId() {
		// TODO Auto-generated method stub
		return creditConsommerRepo.getLastEuConsommationInsertedId();
	}

	@Override
	public Double getSommeDejaConsommerParTypeCreditAndMembre(String codeTypeCredit, String codeMembre) {
		if(StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(codeTypeCredit)){
			if(codeMembre.endsWith("P")){
				return creditConsommerRepo.getSommeDejaConsommerParTypeCreditAndCodeMembre(codeTypeCredit, codeMembre);
			}else{
				return creditConsommerRepo.getSommeDejaConsommerParTypeCreditAndCodeMembreMorale(codeTypeCredit, codeMembre);
			}
		}
		return null;
	}


	
	
}


