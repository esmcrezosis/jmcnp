/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.setting;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.config.EuParametres;
import com.esmc.mcnp.model.config.EuParametresPK;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuParametreRepository;

/**
 *
 * @author USER
 */
@Service("euParametreService")
@Transactional
public class EuParametresServiceImpl extends BaseServiceImpl<EuParametres, EuParametresPK>
		implements EuParametresService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuParametreRepository paramRepo;

	@Override
	public double getParametre(String codeParam, String libParam) {
		EuParametresPK pk = new EuParametresPK();
		pk.setCodeParam(codeParam);
		pk.setLibParam(libParam);
		try {
			return paramRepo.findOne(pk).getMontant();
		} catch (Exception e) {
			System.out.println("Echec de la récuperation du paramètre :" + codeParam + " = >" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int getParam(String codeParam, String libParam) {
		EuParametresPK pk = new EuParametresPK();
		pk.setCodeParam(codeParam);
		pk.setLibParam(libParam);
		try {
			return (int) Math.floor(paramRepo.findOne(pk).getMontant());
		} catch (Exception e) {
			System.out.println("Echec de la récuperation du paramètre :" + e.getMessage());
			return 0;
		}
	}
	
	@Override
	public Double getParamConso() {
		
		try {
			return paramRepo.getMontantTauxOperation();
		} catch (Exception e) {
			System.out.println("Echec de la récuperation du paramètre de prelevement consommation :" + e.getMessage());
			return 0.0;
		}
	}

	@Override
	protected BaseRepository<EuParametres, EuParametresPK> getRepository() {
		return paramRepo;
	}

}
