package com.esmc.mcnp.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeRoles implements Serializable {
    private Integer id;
    private String libelleGroupeRoles;
}
