package com.esmc.mcnp.infrastructure.services.obpsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.others.EuSmsmoneyRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuSmsmoney;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euSmsmoneyService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSmsmoneyServiceImpl extends BaseServiceImpl<EuSmsmoney, Long> implements EuSmsmoneyService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuSmsmoneyRepository smsMoneyRepo;

	@Override
	protected BaseRepository<EuSmsmoney, Long> getRepository() {
		return smsMoneyRepo;
	}

	@Override
	public double findCreditAmountByCreditcode(String creditCode) {
		return smsMoneyRepo.findCreditAmountByCreditcode(creditCode);
	}

	@Override
	public EuSmsmoney findByCreditcode(String creditCode) {
		return smsMoneyRepo.findByCreditcode(creditCode);
	}

}
