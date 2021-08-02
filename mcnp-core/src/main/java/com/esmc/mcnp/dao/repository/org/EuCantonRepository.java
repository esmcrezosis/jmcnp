package com.esmc.mcnp.dao.repository.org;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.CantonVO;
import com.esmc.mcnp.domain.entity.org.EuCanton;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuCantonRepository extends BaseRepository<EuCanton, Integer> {
    List<CantonVO> getAllBy();

    List<CantonVO> getAllByEuPrefecture_IdPrefecture(Integer id);

    @Query("select new com.esmc.mcnp.domain.dto.projections.CantonVO(c.idCanton, c.nomCanton) from EuCanton  c where c.euPrefecture.euRegion.euPay.idPays = :id")
    List<CantonVO> getAllByPays(@Param("id") Integer idPays);

    @Query("select new com.esmc.mcnp.domain.dto.projections.CantonVO(c.idCanton, c.nomCanton) from EuCanton  c where c.euPrefecture.euRegion.idRegion = :id")
    List<CantonVO> getAllByRegion(@Param("id") Integer idRegion);
}
