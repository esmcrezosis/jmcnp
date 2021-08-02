package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.dao.repository.org.EuChaineDistributionRepository;
import com.esmc.mcnp.domain.dto.projections.ChaineDistributionVO;
import com.esmc.mcnp.domain.enums.ChaineDistributionEnum;
import com.esmc.mcnp.domain.entity.obps.EuChaineDistribution;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EuChaineDistributionServiceImplImpl extends CrudServiceImpl<EuChaineDistribution, Integer> implements EuChaineDistributionService {

    private EuChaineDistributionRepository chaineRepository;

    protected EuChaineDistributionServiceImplImpl(EuChaineDistributionRepository chaineRepository) {
        super(chaineRepository);
        this.chaineRepository = chaineRepository;
    }

    @Override
    public List<ChaineDistributionVO> getAll() {
        return chaineRepository.getAllBy();
    }

    @Override
    public Page<EuChaineDistribution> findAllBy(Pageable pageable) {
        return chaineRepository.findAllBy(pageable);
    }

    @Override
    public Page<EuChaineDistribution> findAllByTypeChaine(ChaineDistributionEnum typeChaine, Pageable pageable) {
        return chaineRepository.findAllByTypeChaine(typeChaine, pageable);
    }

    @Override
    public Page<EuChaineDistribution> findAllByCanton(Integer id, Pageable pageable) {
        return chaineRepository.findAllByCanton_IdCanton(id, pageable);
    }

    @Override
    public Page<EuChaineDistribution> findAllByMembre(String id, Pageable pageable) {
        return chaineRepository.findAllByProprio_CodeMembreMorale(id, pageable);
    }

    @Override
    public Page<EuChaineDistribution> findAllByValider(Boolean valider, Pageable pageable) {
        return chaineRepository.findAllByValider(valider, pageable);
    }

    @Override
    public Page<EuChaineDistribution> findAllByAutonome(Boolean autonome, Pageable pageable) {
        return chaineRepository.findAllByAutonome(autonome, pageable);
    }
}
