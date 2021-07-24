/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.smcipn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.model.smcipn.EuServir;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuServirRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;

/**
 *
 * @author mawuli
 */
@Service("euServirService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuServirServiceImpl extends BaseServiceImpl<EuServir, Long> implements EuServirService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired
    EuServirRepository servirRepo;

    @Override
    protected BaseRepository<EuServir, Long> getRepository() {
        return servirRepo;
    }

    @Override
    public List<EuFn> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn) {
        return servirRepo.findByEuSmcipnpwi_CodeSmcipn(codeSmcipn);
    }

    @Override
    public Long getLastEuServirInsertedId() {
        return servirRepo.getLastEuServirInsertedId();
    }

}
