package com.esmc.mcnp.dao.repository.org;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.org.EuSecteur;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EuSecteurRepository extends BaseRepository<EuSecteur, String> {
    @Query(value = "select s.* from eu_secteur s, eu_prefecture p, eu_canton c where s.id_prefecture = p.id_prefecture and c.id_prefecture = p.id_prefecture and c.id_canton = :id", nativeQuery = true)
    EuSecteur findByCanton(@Param("id") Integer idCanton);
}
