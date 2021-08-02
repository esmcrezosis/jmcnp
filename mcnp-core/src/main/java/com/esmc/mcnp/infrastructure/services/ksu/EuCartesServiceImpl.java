/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.ksu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.ksu.EuCartesRepository;
import com.esmc.mcnp.domain.entity.ksu.EuCartes;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 *
 * @author HP
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCartesServiceImpl extends BaseServiceImpl<EuCartes, Long> implements EuCartesService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired
    EuCartesRepository carteRepo;

    @Override
    protected BaseRepository<EuCartes, Long> getRepository() {
        return carteRepo;
    }

}
