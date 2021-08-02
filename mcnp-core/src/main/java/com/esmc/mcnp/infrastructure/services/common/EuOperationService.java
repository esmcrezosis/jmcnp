package com.esmc.mcnp.infrastructure.services.common;

import java.util.List;

import com.esmc.mcnp.domain.entity.others.EuOperation;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuOperationService extends BaseService<EuOperation, Long> {
	public Long getLastOperation();

	public List<EuOperation> getListOperationsFraisExploitation(String libOp);
}
