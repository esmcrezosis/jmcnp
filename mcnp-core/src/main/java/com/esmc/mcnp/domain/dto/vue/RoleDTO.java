package com.esmc.mcnp.dto.vue;

import com.esmc.mcnp.model.security.EuRoles;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable {
    private Integer idRoles;
    private String codeRoles;
    private String libelleRoles;

    public static List<RoleDTO> toRoles(List<EuRoles> roles) {
        List<RoleDTO> results = Lists.newArrayList();
        if (!roles.isEmpty()) {
            roles.forEach(role -> {
                results.add(new RoleDTO().setIdRoles(role.getIdRoles()).setCodeRoles(role.getCodeRoles()).setLibelleRoles(role.getLibelleRoles()));
            });
        }
        return results;
    }
}
