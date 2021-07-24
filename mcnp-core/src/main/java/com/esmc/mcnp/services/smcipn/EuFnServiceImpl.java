package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuFnRepository;

@Service("euFnService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuFnServiceImpl extends BaseServiceImpl<EuFn, Long> implements EuFnService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private
    @Autowired
    EuFnRepository fnRepo;

    @Override
    protected BaseRepository<EuFn, Long> getRepository() {
        return fnRepo;
    }

    @Override
    public Long getLastFnInsertedId() {
        return fnRepo.getLastFnInsertedId();
    }

    @Override
    public List<EuFn> findByOrigineFn() {
        return fnRepo.findByOrigineFn();
    }

    @Override
    public Double findSumByOrigineFn() {
        return fnRepo.findSumByOrigineFn();
    }

    @Override
    public List<EuFn> findFnByCodeSmcipn(String codeSmcipn) {
        return fnRepo.findFnByCodeSmcipn(codeSmcipn);
    }

	@Override
	public List<EuFn> findByOrigineFn(Integer origine) {
		return fnRepo.findByOrigineFn(origine);
	}

	@Override
	public Double findSumByOrigineFn(Integer origine) {
		return fnRepo.findSumByOrigineFn(origine);
	}


}
