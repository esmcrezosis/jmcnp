package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuAvrAchat;
import com.esmc.mcnp.repositories.acteurs.EuAvrAchatRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuAvrAchatServiceImpl extends CrudServiceImpl<EuAvrAchat, Integer> implements EuAvrAchatService {
    private final EuAvrAchatRepository avrAchatRepository;

    protected EuAvrAchatServiceImpl(EuAvrAchatRepository avrAchatRepository) {
        super(avrAchatRepository);
        this.avrAchatRepository = avrAchatRepository;
    }

    @Override
    public List<EuAvrAchat> findByMembre(String codeMembre) {
        return avrAchatRepository.findByCodeMembreAcheteur(codeMembre);
    }

    @Override
    public Page<EuAvrAchat> findByMembre(String codeMembre, Pageable pageable) {
        return avrAchatRepository.findByCodeMembreAcheteur(codeMembre, pageable);
    }

    @Override
    public EuAvrAchat findByReference(String reference) {
        return avrAchatRepository.findByReferenceAvr(reference);
    }
}
