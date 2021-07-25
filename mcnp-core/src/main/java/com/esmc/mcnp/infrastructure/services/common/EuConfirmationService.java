package com.esmc.mcnp.services.common;

import com.esmc.mcnp.model.security.EuConfirmation;
import com.esmc.mcnp.services.base.BaseService;

public interface EuConfirmationService extends BaseService<EuConfirmation, Long> {
	EuConfirmation getConfirmationEnCours(String codeMembre);
}
