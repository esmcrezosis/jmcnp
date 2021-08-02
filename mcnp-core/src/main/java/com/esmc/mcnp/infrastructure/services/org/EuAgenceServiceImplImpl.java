package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.dao.repository.org.EuAgenceRepository;
import com.esmc.mcnp.domain.entity.org.EuAgence;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuAgenceServiceImplImpl extends CrudServiceImpl<EuAgence, String> implements EuAgenceService {

    private EuAgenceRepository agenceRepository;

    protected EuAgenceServiceImplImpl(EuAgenceRepository agenceRepository) {
        super(agenceRepository);
        this.agenceRepository = agenceRepository;
    }

    @Override
    public EuAgence findByTypeGac(String typeGac) {
        return agenceRepository.findByTypeGac(typeGac);
    }
}
