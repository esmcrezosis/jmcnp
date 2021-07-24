package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.obps.EuCodebarre;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuCodebarreRepository;

@Service("euCodebarreService")
public class EuCodebarreServiceImpl extends BaseServiceImpl<EuCodebarre, String> implements  EuCodebarreService{

	
	private static final long serialVersionUID = 1L;
	private @Autowired EuCodebarreRepository codebarreRepo;

	@Override
	protected BaseRepository<EuCodebarre, String> getRepository() {
		return codebarreRepo;
	}

	@Override
	public List<EuCodebarre> getCodebarresByGros(String codebarGros) {
		return codebarreRepo.getCodebarresByGros(codebarGros);
	}

	@Override
	public List<EuCodebarre> getCodebarres(String codeMembreDem) {
		return codebarreRepo.getCodebarres(codeMembreDem);
	}

	@Override
	public String getLastCodebarresSemiGros() {
		return codebarreRepo.getLastCodebarresSemiGros();
	}

	@Override
	public String getLastCodebarresDetaillant() {
		return codebarreRepo.getLastCodebarresDetaillant();
	}

	@Override
	public String getLastCodebarresGros() {
		return codebarreRepo.getLastCodebarresGros();
	}
	
	
}
