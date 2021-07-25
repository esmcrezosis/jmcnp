package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.services.base.BaseService;

public interface EuOperationService extends BaseService<EuOperation, Long> {
	public Long getLastOperation();

	public List<EuOperation> getListOperationsFraisExploitation(String libOp);
}
