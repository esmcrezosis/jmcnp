package com.esmc.mcnp.infrastructure.services.acteurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.security.EuLiaisonUserRepository;
import com.esmc.mcnp.domain.entity.acteur.EuLiaisonUser;
import com.esmc.mcnp.domain.entity.org.EuDivisionGac;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuLiaisonUserServiceImpl extends BaseServiceImpl<EuLiaisonUser, Integer> implements EuLiaisonUserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuLiaisonUserRepository liaisonUserRepo;

	@Override
	protected BaseRepository<EuLiaisonUser, Integer> getRepository() {
		return liaisonUserRepo;
	}

	@Override
	public EuDivisionGac findByUtilisateur(Long idUser) {
		return liaisonUserRepo.findByUtilisateur_id(idUser);
	}

}
