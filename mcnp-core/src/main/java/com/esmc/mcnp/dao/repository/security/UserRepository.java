package com.esmc.mcnp.dao.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.security.EuUser;

public interface UserRepository extends
BaseRepository<EuUser, Integer> {

	@Query("select u from EuUser u join fetch u.roleUtilisateurs ru join fetch ru.euRole r where u.login = :login")
	public Optional<EuUser> findByLogin(@Param("login") String login);
}
