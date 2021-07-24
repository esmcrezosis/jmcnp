package com.esmc.mcnp.mapper.security;

import com.esmc.mcnp.dto.security.User;
import com.esmc.mcnp.model.security.EuUtilisateur;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "userGroup", target = "euUserGroup")
    EuUtilisateur toEuUtilisateur(User user);

    @Mapping(source = "euUserGroup", target = "userGroup")
    @InheritInverseConfiguration
    User fromEuUtilisateur(EuUtilisateur utilisateur);

    List<User> fromUtilisateurs(List<EuUtilisateur> utilisateurs);
}
