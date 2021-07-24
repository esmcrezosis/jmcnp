package com.esmc.mcnp.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.UserSecurity;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.security.EuUtilisateurService;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private EuUtilisateurService userService;

	@Override
	public UserSecurity loadUserByUsername(String username) {
		EuUtilisateur user = userService.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("Ce nom d'utilisateur " + username + " n'est pas trouvé");
		}
		return new UserSecurity(user);
	}

}
