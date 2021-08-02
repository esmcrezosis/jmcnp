package com.esmc.mcnp.infrastructure.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.security.EuUtilisateurRepository;
import com.esmc.mcnp.domain.entity.security.EuRolePermission;
import com.esmc.mcnp.domain.entity.security.EuUserRolesPermission;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private EuUtilisateurRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    public MyUserDetailsService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            final EuUtilisateur user = userRepository.findUserByLogin(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }

            boolean enabled = true;
            if (user.getEnabled() != null) {
                enabled = user.getEnabled();
            }
            return new User(user.getLogin(), user.getPwd(), enabled, true, true, true, getAuthorities(user));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    // UTIL

    private Collection<? extends GrantedAuthority> getAuthorities(final EuUtilisateur user) {
        System.out.println("Getting Authorities");
        return getGrantedAuthorities(getPrivileges(user));
    }

    private List<String> getPrivileges(final EuUtilisateur user) {
        System.out.println("Getting Privileges");
        final List<String> privileges = new ArrayList<>();
        final List<EuRolePermission> collection = new ArrayList<>();
        if (Objects.nonNull(user.getEuUserGroup())) {
            privileges.add(user.getEuUserGroup().getCodeGroupe());
        }
        for (final EuUserRolesPermission role : user.getPermissions()) {
            privileges.add(role.getEuRole().getCodeRoles());
            collection.addAll(role.getEuRole().getRolePermissions());
        }
        for (final EuRolePermission item : collection) {
            privileges.add(item.getPermission().getLibellePermissions());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

}
