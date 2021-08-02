/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.security;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.UserGroupVO;
import com.esmc.mcnp.domain.dto.security.UserGroupDTO;
import com.esmc.mcnp.domain.entity.security.EuUserGroup;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
