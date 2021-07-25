package com.esmc.mcnp.repositories.odd;

import com.esmc.mcnp.dto.projections.AgencesOddVO;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EuAgenceOddRepository extends BaseRepository<EuAgencesOdd, Integer> {
    List<EuAgencesOdd> findByEuCentre_IdCentres(Integer id);

    EuAgencesOdd findByAgencesOddSource(Boolean source);

    @Query(value = "select a from EuAgencesOdd a left join fetch a.euCentre c left join fetch c.pays left join fetch c.region left join fetch c.prefecture left join fetch c.canton left join fetch a.euCanton left join fetch a.euCanton",
            countQuery = "select a from EuAgencesOdd a left join a.euCentre c left join c.pays left join c.region left join c.prefecture left join c.canton left join a.euCanton left join a.euCanton")
    Page<EuAgencesOdd> fetchAll(Pageable pageable);

    @Query(value = "select a from EuAgencesOdd a left join fetch a.euCentre c left join fetch c.pays left join fetch c.region left join fetch c.prefecture left join fetch c.canton left join fetch a.euCanton left join fetch a.euCanton where a.user.idUtilisateur = :id",
            countQuery = "select a from EuAgencesOdd a left join a.euCentre c left join c.pays left join c.region left join c.prefecture left join c.canton left join a.euCanton left join a.euCanton where a.user.idUtilisateur = :id")
    Page<EuAgencesOdd> fetchAll(Long id, Pageable pageable);

    List<AgencesOddVO> getAllBy();
}
