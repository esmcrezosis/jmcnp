package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuCommandeNrRepository;
import com.esmc.mcnp.domain.entity.obps.EuCommandeNr;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCommandeNrService")
public class EuCommandeNrServiceImpl extends BaseServiceImpl<EuCommandeNr, Long> implements EuCommandeNrService{
	
		private static final long serialVersionUID = 1L;
		private @Autowired EuCommandeNrRepository euCommandeNrRepo;

		@Override
		protected BaseRepository<EuCommandeNr, Long> getRepository() {
				return euCommandeNrRepo;
		}

		@Override
		public EuCommandeNr findCommandeNrByCodeCommande(String codeCommande) {
			return euCommandeNrRepo.findCommandeNrByCodeCommande(codeCommande);
		}

		
}
