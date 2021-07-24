package com.esmc.mcnp.services.bc;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.bc.EuDomiciliation;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.bc.EuCnpRepository;

@Service("euCnpService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCnpServiceImpl extends BaseServiceImpl<EuCnp, Long> implements EuCnpService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCnpRepository cnpRepo;

	@Override
	public Long getLastCnpInsertedId() {
		return cnpRepo.getLastCnpInsertedId();
	}

	@Override
	public EuCnp findBySourceCredit(Long id, String source) {
		return cnpRepo.findBySourceCredit(id, source);
	}

	@Override
	public List<EuCnp> findByIdCredit(Long idCredit) {
		return cnpRepo.findByIdCredit(idCredit);
	}

	@Override
	public EuCnp findByIdGcp(Long id) {
		return cnpRepo.findByEuGcp_IdGcp(id);
	}

	@Override
	public void createCnp(EuCompteCredit cc, EuCapa capa, EuDomiciliation domiciliation, String source, Date date, double mont_credit) {
		Long idCnp = cnpRepo.getLastCnpInsertedId();
		if (idCnp == null) {
			idCnp = 1L;
		} else {
			idCnp += 1;
		}
		EuCnp cnp = new EuCnp();
		cnp.setIdCnp(idCnp);
		cnp.setDateCnp(date);
		cnp.setEuCapa(capa);
		cnp.setEuCompteCredit(cc);
		cnp.setEuDomiciliation(domiciliation);
		cnp.setMontCredit(0);
		cnp.setMontDebit(mont_credit);
		cnp.setOrigineCnp("FG" + cc.getEuProduit().getCodeProduit());
		cnp.setSoldeCnp(mont_credit);
		cnp.setSourceCredit(source);
		cnp.setTypeCnp(cc.getEuProduit().getCodeProduit());
		cnp.setTransfertGcp(0);
		cnpRepo.save(cnp);
	}

	@Override
	public void updateCnp(Long idCredit, String source, double mont_credit){
        EuCnp cnp = cnpRepo.findBySourceCredit(idCredit, source);
        if (Objects.nonNull(cnp)) {
            updateCnp(cnp, mont_credit);
        }
    }

	@Override
    public void updateCnp(EuCnp cnp, double mont_credit){
        cnp.setMontDebit(cnp.getMontDebit() + mont_credit);
        cnp.setSoldeCnp(cnp.getSoldeCnp() + mont_credit);
        cnpRepo.save(cnp);
    }

	@Override
	protected BaseRepository<EuCnp, Long> getRepository() {
		return cnpRepo;
	}

	@Override
	public EuCnp findCnpBySourceCredit(Long id, String source) {
		return cnpRepo.findCnpBySourceCredit(id, source);
	}

}
