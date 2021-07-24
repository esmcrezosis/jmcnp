/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.model.pc.EuCharge;
import com.esmc.mcnp.services.base.BaseService;

import java.util.List;

/**
 *
 * @author USER
 */
public interface EuChargeService extends BaseService<EuCharge, Integer> {
	List<EuCharge> findByCodeTypecharge(String codeTypeCharge);

	List<EuCharge> findByIdTypeCharge(Integer idTypeCharge);

	EuCharge findByCode(String codeCharge);
}
