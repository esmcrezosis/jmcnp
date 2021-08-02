package com.esmc.mcnp.infrastructure.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
