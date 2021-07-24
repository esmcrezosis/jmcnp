package com.esmc.mcnp.services.ot;

import com.esmc.mcnp.model.ot.EuFormation;
import com.esmc.mcnp.repositories.ot.EuFormationRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EuformationServiceImpl extends CrudServiceImpl<EuFormation, Long> implements EuFormationService {

    private EuFormationRepository formationRepository;

    protected EuformationServiceImpl(EuFormationRepository formationRepository) {
        super(formationRepository);
        this.formationRepository = formationRepository;
    }

    @Override
    public List<EuFormation> findByMembre(String codeMembre) {
        return formationRepository.findByCodeMembre(codeMembre);
    }

    @Override
    public Page<EuFormation> findByMembre(String codeMembre, Pageable pageable) {
        return formationRepository.findByCodeMembre(codeMembre, pageable);
    }
}
