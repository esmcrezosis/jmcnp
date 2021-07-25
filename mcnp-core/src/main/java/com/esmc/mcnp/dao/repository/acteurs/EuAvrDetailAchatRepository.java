package com.esmc.mcnp.repositories.acteurs;

import com.esmc.mcnp.model.acteur.EuAvrDetailAchat;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface EuAvrDetailAchatRepository extends BaseRepository<EuAvrDetailAchat, Integer> {

    List<EuAvrDetailAchat> findByEuAvrAchat_Id(Integer idAvrAchat);

    @EntityGraph(attributePaths = {"euAvrAchat"})
    Page<EuAvrDetailAchat> findByEuAvrAchat_Id(Integer idAvrAchat, Pageable pageable);

    List<EuAvrDetailAchat> findByEuAvrAchat_CodeMembreAcheteur(String codeMembre);

    @EntityGraph(attributePaths = {"euAvrAchat"})
    Page<EuAvrDetailAchat> findByEuAvrAchat_CodeMembreAcheteur(String codeMembre, Pageable pageable);

    List<EuAvrDetailAchat> findByEuAvrAchat_ReferenceAvr(String reference);

    @EntityGraph(attributePaths = {"euAvrAchat"})
    Page<EuAvrDetailAchat> findByEuAvrAchat_ReferenceAvr(String reference, Pageable pageable);
}
