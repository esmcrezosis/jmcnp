package com.esmc.mcnp.dao.repository.cmfh;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuDepotVente;

public interface EuDepotVenteRepository extends BaseRepository<EuDepotVente, Long> {

    @Async
    CompletableFuture<List<EuDepotVente>> findByOrderByDateDepot(Pageable page);

    @EntityGraph(attributePaths = {"euSouscription"})
    List<EuDepotVente> findByCodeMembre(String codeMembre);

    @Query("select d from EuDepotVente d where d.codeMembre = :memb")
    List<EuDepotVente> findByMembre(@Param("memb") String codeMembre);

    @Query("select d from EuDepotVente d where d.codeMembre = :memb")
    Page<EuDepotVente> findByMembre(@Param("memb") String codeMembre, Pageable pageable);

    @Query("select d from EuDepotVente d join d.euSouscription s where s.souscriptionNom like :nom and s.souscriptionPrenom like :prenom")
    List<EuDepotVente> findByMembre(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("select d from EuDepotVente d join d.euSouscription s where s.souscriptionNom like :nom and s.souscriptionPrenom like :prenom")
    Page<EuDepotVente> findByMembre(@Param("nom") String nom, @Param("prenom") String prenom, Pageable pageable);
}
