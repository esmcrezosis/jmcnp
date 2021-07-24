/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.security;

import java.util.List;

import com.esmc.mcnp.dto.projections.UserGroupVO;
import com.esmc.mcnp.dto.security.UserGroupDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.security.EuUserGroup;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 * @author USER
 */
public interface EuUserGroupRepository extends BaseRepository<EuUserGroup, String> {
    public List<EuUserGroup> findByEuUserGroupParent_CodeGroupe(String codeGroupe);

    @Query("select g.euUserGroupParent from EuUserGroup g where g.codeGroupe = :codeGroup")
    public EuUserGroup findParentByCodeGroupe(@Param("codeGroup") String codeGroupe);

    @Query("select u from EuUserGroup u")
    List<UserGroupDTO> loadAll();

    List<UserGroupVO> getAllBy();
}
