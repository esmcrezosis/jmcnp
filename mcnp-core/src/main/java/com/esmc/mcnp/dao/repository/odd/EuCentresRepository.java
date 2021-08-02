package com.esmc.mcnp.dao.repository.odd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.CentreVO;
import com.esmc.mcnp.domain.entity.odd.EuCentres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuCentresRepository extends BaseRepository<EuCentres, Integer> {
    @Query("select new com.esmc.mcnp.domain.dto.projections.CentreVO(c.idCentres, c.libelleCentre, c.referenceCentre) from EuCentres c")
    List<CentreVO> getAllVO();

    @EntityGraph(attributePaths={"pays", "region", "prefecture", "canton"})
    List<EuCentres> getAllBy();

    @EntityGraph(attributePaths={"pays", "region", "prefecture", "canton"})
    Page<EuCentres> getAllBy(Pageable pageable);

    @Query(value = "select c from EuCentres c left join fetch c.pays left join fetch c.region left join fetch c.prefecture left join fetch c.canton where c.user.idUtilisateur = :id")
    List<EuCentres> findByUser(@Param("id") Long idUser);

    @Query(value = "select c from EuCentres c left join fetch c.pays left join fetch c.region left join fetch c.prefecture left join fetch c.canton where c.user.idUtilisateur = :id",
            countQuery="select c from EuCentres c left join c.pays left join c.region left join c.prefecture left join c.canton where c.user.idUtilisateur = :id")
    Page<EuCentres> findByUser(@Param("id") Long idUser, Pageable pageable);
}
