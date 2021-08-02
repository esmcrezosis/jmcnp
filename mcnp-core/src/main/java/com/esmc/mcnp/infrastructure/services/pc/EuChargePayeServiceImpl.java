/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuChargePayeRepository;
import com.esmc.mcnp.domain.entity.pc.EuChargePaye;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 *
 * @author USER
 */
@Service("euChargePayeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuChargePayeServiceImpl extends BaseServiceImpl<EuChargePaye, Long> implements EuChargePayeService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EuChargePayeRepository chargePayeRepository;

    @Override
    protected BaseRepository<EuChargePaye, Long> getRepository() {
        return chargePayeRepository;
    }
}
