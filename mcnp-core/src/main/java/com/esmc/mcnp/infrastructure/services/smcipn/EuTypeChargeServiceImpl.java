/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.smcipn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuTypeChargeRepository;
import com.esmc.mcnp.domain.entity.pc.EuTypeCharge;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 *
 * @author USER
 */
@Service("euTypeChargeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTypeChargeServiceImpl extends BaseServiceImpl<EuTypeCharge, Integer> implements EuTypeChargeService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EuTypeChargeRepository typeChargeRepository;

    @Override
    protected BaseRepository<EuTypeCharge, Integer> getRepository() {
        return typeChargeRepository;
    }

}
