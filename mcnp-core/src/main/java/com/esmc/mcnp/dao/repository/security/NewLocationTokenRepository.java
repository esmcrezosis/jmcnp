package com.esmc.mcnp.dao.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmc.mcnp.domain.entity.security.NewLocationToken;
import com.esmc.mcnp.domain.entity.security.UserLocation;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
