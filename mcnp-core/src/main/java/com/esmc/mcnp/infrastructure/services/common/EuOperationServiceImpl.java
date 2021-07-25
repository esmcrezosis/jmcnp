package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuOperationRepository;

@Service("operationService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuOperationServiceImpl extends BaseServiceImpl<EuOperation, Long> implements EuOperationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuOperationRepository opRepo;

	@Override
	protected BaseRepository<EuOperation, Long> getRepository() {
		return opRepo;
	}

	@Override
	public Long getLastOperation() {
		return opRepo.getLastOperationInsertedId();
	}

	@Override
	public List<EuOperation> getListOperationsFraisExploitation(String libOp) {
		return opRepo.getListOperations(libOp);
	}

}
