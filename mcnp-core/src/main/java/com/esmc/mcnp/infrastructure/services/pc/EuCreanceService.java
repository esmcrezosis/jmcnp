package com.esmc.mcnp.infrastructure.services.pc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.pc.EuCreance;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCreanceService extends BaseService<EuCreance, Long> {

	public List<EuCreance> findByCrediteur(String codeMemreCrediteur);

	public Page<EuCreance> findByCrediteur(String codeMemreCrediteur, Pageable page);

	public List<EuCreance> findByDebiteur(String codeMembreDebiteur);

	public Page<EuCreance> findByDebiteur(String codeMembreDebiteur, Pageable page);

	public List<EuCreance> findByCrediteurAndDebiteur(String codeMembreCred, String codeMembreDeb);

	public Page<EuCreance> findByCrediteurAndDebiteur(String codeMembreCred, String codeMembreDeb, Pageable page);
}
