package com.esmc.mcnp.dao.repository.org;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.ChaineDistributionVO;
import com.esmc.mcnp.domain.enums.ChaineDistributionEnum;
import com.esmc.mcnp.domain.entity.obps.EuChaineDistribution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface EuChaineDistributionRepository extends BaseRepository<EuChaineDistribution, Integer> {
    List<ChaineDistributionVO> getAllBy();

    @EntityGraph(attributePaths = {"proprio", "parent", "proprio.euStatutJuridique", "canton"})
    Page<EuChaineDistribution> findAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"proprio", "parent", "proprio.euStatutJuridique", "canton"})
    Page<EuChaineDistribution> findAllByTypeChaine(ChaineDistributionEnum typeChaine, Pageable pageable);

    @EntityGraph(attributePaths = {"proprio", "parent", "proprio.euStatutJuridique", "canton"})
    Page<EuChaineDistribution> findAllByCanton_IdCanton(Integer id, Pageable pageable);

    @EntityGraph(attributePaths = {"proprio", "parent", "proprio.euStatutJuridique", "canton"})
    Page<EuChaineDistribution> findAllByProprio_CodeMembreMorale(String id, Pageable pageable);

    @EntityGraph(attributePaths = {"proprio", "parent", "proprio.euStatutJuridique", "canton"})
    Page<EuChaineDistribution> findAllByValider(Boolean valider, Pageable pageable);

    @EntityGraph(attributePaths = {"proprio", "parent", "proprio.euStatutJuridique", "canton"})
    Page<EuChaineDistribution> findAllByAutonome(Boolean autonome, Pageable pageable);
}
