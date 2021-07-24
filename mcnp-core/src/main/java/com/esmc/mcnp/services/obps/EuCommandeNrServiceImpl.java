package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.obps.EuCommandeNr;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuCommandeNrRepository;

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
