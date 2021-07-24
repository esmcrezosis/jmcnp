package com.esmc.mcnp.services.security;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.esmc.mcnp.model.security.EuUser;

public interface UserService {

	public EuUser findByLogin(String login);

	public EuUser findById(Long id);

	public List<EuUser> findAll();

	public List<EuUser> findByRoles(String role);

	public boolean removeUtilisateur(EuUser user);

	public boolean removeById(Long id);

	public EuUser updateUser(EuUser user);

	public EuUser saveUser(
			@NotNull(message = "{validate.authenticate.saveUser}") @Valid EuUser principal,
			String newPassword);
}
