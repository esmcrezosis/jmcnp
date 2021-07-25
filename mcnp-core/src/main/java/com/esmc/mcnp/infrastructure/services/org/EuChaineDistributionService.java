package com.esmc.mcnp.services.org;

import com.esmc.mcnp.dto.projections.ChaineDistributionVO;
import com.esmc.mcnp.model.enums.ChaineDistributionEnum;
import com.esmc.mcnp.model.obps.EuChaineDistribution;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuChaineDistributionService extends CrudService<EuChaineDistribution, Integer> {

    List<ChaineDistributionVO> getAll();

    Page<EuChaineDistribution> findAllBy(Pageable pageable);

    Page<EuChaineDistribution> findAllByTypeChaine(ChaineDistributionEnum typeChaine, Pageable pageable);

    Page<EuChaineDistribution> findAllByCanton(Integer id, Pageable pageable);

    Page<EuChaineDistribution> findAllByMembre(String id, Pageable pageable);

    Page<EuChaineDistribution> findAllByValider(Boolean valider, Pageable pageable);

    Page<EuChaineDistribution> findAllByAutonome(Boolean autonome, Pageable pageable);
}
