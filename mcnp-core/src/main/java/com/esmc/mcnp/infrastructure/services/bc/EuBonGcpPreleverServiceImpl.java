package com.esmc.mcnp.infrastructure.services.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuBonGcpPreleverRepository;
import com.esmc.mcnp.domain.entity.obps.EuBonGcpPrelever;
import com.esmc.mcnp.domain.entity.obps.EuBonGcpPreleverPK;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euBonGcpPreleverService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonGcpPreleverServiceImpl extends BaseServiceImpl<EuBonGcpPrelever, EuBonGcpPreleverPK>
		implements EuBonGcpPreleverService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuBonGcpPreleverRepository bonGcpRepo;

	@Override
	public List<EuBonGcpPrelever> findByBonId(Integer id) {
		return bonGcpRepo.findByBonId(id);
	}

	@Override
	public List<EuBonGcpPrelever> findByBonNumero(String numero) {
		return bonGcpRepo.findByBonNumero(numero);
	}

	@Override
	protected BaseRepository<EuBonGcpPrelever, EuBonGcpPreleverPK> getRepository() {
		return bonGcpRepo;
	}

	@Override
	public Integer countTpagcpByBonNumero(String numero) {
		return bonGcpRepo.countTpagcpByBonNumero(numero);
	}

	@Override
	public List<Long> findIdTpagcpByBonNumero(String numero) {
		return bonGcpRepo.findIdTpagcpByBonNumero(numero);
	}

	@Override
	public List<EuBonGcpPrelever> findByIdTpagcp(Long idTpagcp) {
		return bonGcpRepo.findByIdTpagcp(idTpagcp);
	}

}
