package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.dao.repository.org.EuSecteurRepository;
import com.esmc.mcnp.domain.entity.org.EuSecteur;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuSecteurServiceImplImpl extends CrudServiceImpl<EuSecteur, String> implements EuSecteurService {

    private final EuSecteurRepository secteurRepository;

    protected EuSecteurServiceImplImpl(EuSecteurRepository secteurRepository) {
        super(secteurRepository);
        this.secteurRepository = secteurRepository;
    }

    @Override
    public EuSecteur findByCanton(Integer idCanton) {
        return secteurRepository.findByCanton(idCanton);
    }
}
