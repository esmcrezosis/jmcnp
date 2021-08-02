package com.esmc.mcnp.domain.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDTO implements Serializable {
    private String codeGroupe;
    private String libelleGroupe;
}
