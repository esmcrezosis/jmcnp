package com.esmc.mcnp.infrastructure.services.common;

import com.esmc.mcnp.domain.entity.security.EuConfirmation;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuConfirmationService extends BaseService<EuConfirmation, Long> {
	EuConfirmation getConfirmationEnCours(String codeMembre);
}
