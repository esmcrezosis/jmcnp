package com.esmc.mcnp.services.cm;

import java.util.Objects;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cm.EuCompteGeneral;
import com.esmc.mcnp.model.cm.EuCompteGeneralPK;
import com.esmc.mcnp.repositories.cm.EuCompteGeneralRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euCompteGeneralService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCompteGeneralServiceImpl extends BaseServiceImpl<EuCompteGeneral, EuCompteGeneralPK>
		implements EuCompteGeneralService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCompteGeneralRepository compteGeneRepo;

	@Override
	protected BaseRepository<EuCompteGeneral, EuCompteGeneralPK> getRepository() {
		return compteGeneRepo;
	}

	@Override
	public EuCompteGeneral findByPK(String codeCompte, String codeTypeCompte, String service) {
		return compteGeneRepo.findById(codeCompte, codeTypeCompte, service);
	}

	@Override
	public void saveCG(String codeCompte, String codeTypeCompte, String service, double montant) {
		EuCompteGeneral cg = findByPK(codeCompte, codeTypeCompte, service);
		if (Objects.nonNull(cg)) {
			cg.setSolde(cg.getSolde() + montant);
			compteGeneRepo.save(cg);
		} else {
			cg = new EuCompteGeneral();
			EuCompteGeneralPK cgpk = new EuCompteGeneralPK();
			cgpk.setCodeCompte(codeCompte);
			cgpk.setCodeTypeCompte(codeTypeCompte);
			cgpk.setService(service);
			cg.setId(cgpk);
			cg.setIntitule("Crédit en Nature Pérenne");
			cg.setSolde(montant);
			compteGeneRepo.save(cg);
		}
	}

}
