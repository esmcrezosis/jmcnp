package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuAvrDetailAchat;
import com.esmc.mcnp.repositories.acteurs.EuAvrDetailAchatRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuAvrDetailAchatServiceImpl extends CrudServiceImpl<EuAvrDetailAchat, Integer> implements EuAvrDetailAchatService {
    private EuAvrDetailAchatRepository avrDetAchatRepo;

    protected EuAvrDetailAchatServiceImpl(EuAvrDetailAchatRepository avrDetAchatRepo) {
        super(avrDetAchatRepo);
        this.avrDetAchatRepo = avrDetAchatRepo;
    }

    @Override
    public List<EuAvrDetailAchat> findByAvrAchat(Integer idAvrAchat) {
        return avrDetAchatRepo.findByEuAvrAchat_Id(idAvrAchat);
    }

    @Override
    public Page<EuAvrDetailAchat> findByAvrAchat(Integer idAvrAchat, Pageable pageable) {
        return avrDetAchatRepo.findByEuAvrAchat_Id(idAvrAchat, pageable);
    }

    @Override
    public List<EuAvrDetailAchat> findByMembre(String codeMembre) {
        return avrDetAchatRepo.findByEuAvrAchat_CodeMembreAcheteur(codeMembre);
    }

    @Override
    public Page<EuAvrDetailAchat> findByMembre(String codeMembre, Pageable pageable) {
        return avrDetAchatRepo.findByEuAvrAchat_CodeMembreAcheteur(codeMembre, pageable);
    }

    @Override
    public List<EuAvrDetailAchat> findByReferenceAvr(String reference) {
        return avrDetAchatRepo.findByEuAvrAchat_ReferenceAvr(reference);
    }

    @Override
    public Page<EuAvrDetailAchat> findByReferenceAvr(String reference, Pageable pageable) {
        return avrDetAchatRepo.findByEuAvrAchat_ReferenceAvr(reference, pageable);
    }
}
