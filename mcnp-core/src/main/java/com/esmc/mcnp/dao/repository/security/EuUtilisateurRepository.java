/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.security;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author USER
 */
public interface EuUtilisateurRepository extends BaseRepository<EuUtilisateur, Long> {

    EuUtilisateur findByLoginAndPasswordHash(String login, String pwd);

    @Query("select u from EuUtilisateur u left join fetch u.centre left join fetch u.agencesOdd join fetch u.euUserGroup g left join fetch g.euUserGroupParent gp left join fetch u.permissions p left join fetch u.centre left join fetch u.agencesOdd where u.login like :login and u.passwordHash like :password")
    EuUtilisateur findUserByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query("select u from EuUtilisateur u left join fetch u.centre left join fetch u.agencesOdd left join fetch u.euUserGroup g left join g.euUserGroupParent gp left join fetch u.permissions p left join fetch u.centre left join fetch u.agencesOdd where u.login like :login")
    EuUtilisateur findUserByLogin(@Param("login") String login);

    @Query("select u from EuUtilisateur u where u.codeMembre = :codeMembre")
    EuUtilisateur findUserByCodeMembre(@Param("codeMembre") String codeMembre);

    @Query("select u from EuUtilisateur u left join fetch u.euUserGroup left join fetch u.euAgence")
    List<EuUtilisateur> findAllWithGroupeAndAgence();

    @EntityGraph(attributePaths = {"euAgence"})
    EuUtilisateur findByIdUtilisateur(Long id);

    @EntityGraph(attributePaths = {"euUserGroup", "centre", "agencesOdd"})
    EuUtilisateur getByIdUtilisateur(Long id);
    
    @EntityGraph(attributePaths = {"euUserGroup", "centre", "agencesOdd"})
    EuUtilisateur findByEmail(String email);
    
    EuUtilisateur findByLogin(String login);

    @Query("select u from EuUtilisateur  u left join fetch u.euUserGroup left join fetch u.centre c left join fetch c.pays left join fetch c.region left join c.prefecture left join fetch c.canton left join fetch u.agencesOdd a left join fetch a.euCanton")
    List<EuUtilisateur> loadAll();

    @Query(value = "select u from EuUtilisateur  u left join fetch u.euUserGroup left join fetch u.centre c left join fetch c.pays left join fetch c.region left join fetch c.prefecture left join fetch c.canton left join fetch u.agencesOdd a left join fetch a.euCanton",
            countQuery = "select count(u) from EuUtilisateur  u left join u.euUserGroup left join u.centre c left join c.pays left join c.region left join c.prefecture left join c.canton left join u.agencesOdd a left join a.euCanton")
    Page<EuUtilisateur> loadAll(Pageable pageable);

    @Query("select u from EuUtilisateur  u left join fetch u.euUserGroup left join fetch u.centre c left join fetch c.pays left join fetch c.region left join c.prefecture left join fetch c.canton left join fetch u.agencesOdd a left join fetch a.euCanton where u.idUtilisateurParent = :id")
    List<EuUtilisateur> loadAllByParent(@Param("id") Long id);

    @Query(value = "select u from EuUtilisateur  u left join fetch u.euUserGroup left join fetch u.centre c left join fetch c.pays left join fetch c.region left join fetch c.prefecture left join fetch c.canton left join fetch u.agencesOdd a left join fetch a.euCanton where u.idUtilisateurParent = :id",
            countQuery = "select count(u) from EuUtilisateur  u left join u.euUserGroup left join u.centre c left join c.pays left join c.region left join c.prefecture left join c.canton left join u.agencesOdd a left join a.euCanton where u.idUtilisateurParent = :id")
    Page<EuUtilisateur> loadAllByParent(@Param("id") Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"euUserGroup", "centre", "agencesOdd"})
    List<EuUtilisateur> getAllBy();

    @EntityGraph(attributePaths = {"euUserGroup", "centre", "agencesOdd"})
    Page<EuUtilisateur> getAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"euUserGroup", "centre", "agencesOdd"})
    List<EuUtilisateur> findByIdUtilisateurParent(Long id);

    @EntityGraph(attributePaths = {"euUserGroup", "centre", "agencesOdd"})
    Page<EuUtilisateur> findByIdUtilisateurParent(Long id, Pageable pageable);
}
