package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.repositories.others.EuSmsmoneyRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
