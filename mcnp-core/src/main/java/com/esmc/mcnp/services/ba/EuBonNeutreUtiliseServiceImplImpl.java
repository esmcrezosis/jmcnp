package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.model.obpsd.EuBonNeutreUtilise;
import com.esmc.mcnp.repositories.obpsd.EuBonNeutreUtiliseRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonNeutreUtiliseServiceImplImpl extends CrudServiceImpl<EuBonNeutreUtilise, Integer>
        implements EuBonNeutreUtiliseService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private EuBonNeutreUtiliseRepository bonNeutreDet;

    protected EuBonNeutreUtiliseServiceImplImpl(EuBonNeutreUtiliseRepository bonNeutreDet) {
        super(bonNeutreDet);
        this.bonNeutreDet = bonNeutreDet;
    }

    @Override
    public int getLastInsertedId() {
        return bonNeutreDet.getLastInsertedId();
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreCode(String code, Pageable pageable) {
        return bonNeutreDet.findByEuBonNeutre_bonNeutreCode(code, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreCodeMembre(String codeMembre, Pageable pageable) {
        return bonNeutreDet.findByEuBonNeutre_bonNeutreCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreDetailId(Integer id, Pageable pageable) {
        return bonNeutreDet.findByBonNeutreDetailId(id, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreId(Integer id, Pageable pageable) {
        return bonNeutreDet.findByEuBonNeutre_BonNeutreId(id, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateBetween(Date deb, Date fin, Pageable pageable) {
        return bonNeutreDet.findByBonNeutreUtiliseDateBetween(deb, fin, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateBefore(Date date, Pageable pageable) {
        return bonNeutreDet.findByBonNeutreUtiliseDateBefore(date, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateAfter(Date date, Pageable pageable) {
        return bonNeutreDet.findByBonNeutreUtiliseDateAfter(date, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByMembreAndDateSup(String codeMembre, Date date, Pageable pageable) {
        return bonNeutreDet.findByMembreAndDateSup(codeMembre, date, pageable);
    }

    @Override
    public Page<EuBonNeutreUtilise> findByMembreAndDateInf(String codeMembre, Date date, Pageable pageable) {
        return bonNeutreDet.findByMembreAndDateInf(codeMembre, date, pageable);
    }

}
