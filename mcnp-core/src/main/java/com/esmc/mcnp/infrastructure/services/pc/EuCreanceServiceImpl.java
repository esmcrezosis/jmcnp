package com.esmc.mcnp.infrastructure.services.pc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuCreanceRepository;
import com.esmc.mcnp.domain.entity.pc.EuCreance;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCreanceService")
@Transactional
public class EuCreanceServiceImpl extends BaseServiceImpl<EuCreance, Long> implements EuCreanceService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private @Autowired
    EuCreanceRepository creanceRepo;

    @Override
    protected BaseRepository<EuCreance, Long> getRepository() {
        return creanceRepo;
    }

    @Override
    public List<EuCreance> findByCrediteur(String codeMemreCrediteur) {
        return creanceRepo.findByCodeMembreCred(codeMemreCrediteur);
    }

    @Override
    public List<EuCreance> findByDebiteur(String codeMembreDebiteur) {
        return creanceRepo.findByCodeMembreDeb(codeMembreDebiteur);
    }

    @Override
    public List<EuCreance> findByCrediteurAndDebiteur(String codeMembreCred, String codeMembreDeb) {
        return creanceRepo.findByCodeMembreCredAndCodeMembreDeb(codeMembreCred, codeMembreDeb);
    }

    @Override
    public Page<EuCreance> findByCrediteur(String codeMemreCrediteur, Pageable page) {
        return creanceRepo.findByCodeMembreCred(codeMemreCrediteur, page);
    }

    @Override
    public Page<EuCreance> findByDebiteur(String codeMembreDebiteur, Pageable page) {
        return creanceRepo.findByCodeMembreDeb(codeMembreDebiteur, page);
    }

    @Override
    public Page<EuCreance> findByCrediteurAndDebiteur(String codeMembreCred, String codeMembreDeb, Pageable page) {
        return creanceRepo.findByCodeMembreCredAndCodeMembreDeb(codeMembreCred, codeMembreDeb, page);
    }

}
