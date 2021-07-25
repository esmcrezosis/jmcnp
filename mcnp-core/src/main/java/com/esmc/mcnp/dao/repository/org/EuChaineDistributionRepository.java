package com.esmc.mcnp.repositories.org;

import com.esmc.mcnp.dto.projections.ChaineDistributionVO;
import com.esmc.mcnp.model.enums.ChaineDistributionEnum;
import com.esmc.mcnp.model.obps.EuChaineDistribution;
import com.esmc.mcnp.repositories.base.BaseRepository;
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
