package com.esmc.mcnp.repositories.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.security.EuUser;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface UserRepository extends
BaseRepository<EuUser, Integer> {

	@Query("select u from EuUser u join fetch u.roleUtilisateurs ru join fetch ru.euRole r where u.login = :login")
	public Optional<EuUser> findByLogin(@Param("login") String login);
}
