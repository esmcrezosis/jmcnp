/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuParametreRepository;
import com.esmc.mcnp.domain.entity.config.EuParametres;
import com.esmc.mcnp.domain.entity.config.EuParametresPK;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
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
            return 0;
        }
    }

    @Override
    public int getParam(String codeParam, String libParam) {
        try {
            return (int) Math.floor(paramRepo.findByCodeAndLib(codeParam, libParam));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String getStringParam(String code, String lib) {
        try {
            return paramRepo.getValeurByCodeAndLib(code, lib);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Double getParamConso() {
        try {
            return paramRepo.getMontantTauxOperation();
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    protected BaseRepository<EuParametres, EuParametresPK> getRepository() {
        return paramRepo;
    }

}
