/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.services.security;

import com.esmc.mcnp.dto.security.Login;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author USER
 */
public interface EuUtilisateurService extends CrudService<EuUtilisateur, Long> {

	List<EuUtilisateur> loadAll();

	Page<EuUtilisateur> loadAll(Pageable pageable);

	List<EuUtilisateur> loadAllByParent(Long id);

	Page<EuUtilisateur> loadAllByParent(Long id, Pageable pageable);

	EuUtilisateur findByIdUtilisateur(Long id);

	Login authenticateUser(Login c_login);

	Login authenticateUser(String login, String password);

	List<EuUtilisateur> findAllWithGroupeAndAgence();

	List<EuUtilisateur> getAll();

	Page<EuUtilisateur> getAll(Pageable pageable);

	List<EuUtilisateur> findByParent(Long id);

	Page<EuUtilisateur> findByParent(Long id, Pageable pageable);

	EuUtilisateur findByLogin(String login);

	EuUtilisateur getOne(Long id);

	EuUtilisateur saveUser(@NotNull(message = "{validate.authenticate.saveUser}") @Valid EuUtilisateur principal,
			String newPassword);
}
