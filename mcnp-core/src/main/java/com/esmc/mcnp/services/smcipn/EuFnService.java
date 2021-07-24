package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.services.base.BaseService;

public interface EuFnService extends BaseService<EuFn, Long> {

    public Long getLastFnInsertedId();

    public List<EuFn> findByOrigineFn();
    
    public List<EuFn> findByOrigineFn(Integer origine);

    public Double findSumByOrigineFn();
    
    public Double findSumByOrigineFn(Integer origine);

    public List<EuFn> findFnByCodeSmcipn(String codeSmcipn);

}
