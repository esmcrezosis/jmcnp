/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.pc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuChargeRepository;
import com.esmc.mcnp.domain.entity.pc.EuCharge;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 * @author USER
 */
@Service("euChargeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuChargeServiceImpl extends BaseServiceImpl<EuCharge, Integer> implements EuChargeService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EuChargeRepository chargeRepository;

    @Override
    protected BaseRepository<EuCharge, Integer> getRepository() {
        return chargeRepository;
    }

    @Override
    public List<EuCharge> findByCodeTypecharge(String codeTypeCharge) {
        return chargeRepository.findByEuTypeCharge_CodeTypeCharge(codeTypeCharge);
    }

    @Override
    public List<EuCharge> findByIdTypeCharge(Integer idTypeCharge) {
        return chargeRepository.findByEuTypeCharge_IdTypeCharge(idTypeCharge);
    }

	@Override
	public EuCharge findByCode(String codeCharge) {
		return chargeRepository.findByCodeCharge(codeCharge).get();
	}
}
