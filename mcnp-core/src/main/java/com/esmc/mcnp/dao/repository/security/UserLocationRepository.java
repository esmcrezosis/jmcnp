package com.esmc.mcnp.dao.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.security.UserLocation;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, EuUtilisateur user);

}
