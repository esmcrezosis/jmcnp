/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.smcipn;

import com.esmc.mcnp.domain.entity.smcipn.EuFn;
import com.esmc.mcnp.domain.entity.smcipn.EuServir;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

import java.util.List;

/**
 *
 * @author mawuli
 */
public interface EuServirService extends BaseService<EuServir, Long> {

    public List<EuFn> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn);

    public Long getLastEuServirInsertedId();
}
