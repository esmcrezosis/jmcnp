package com.esmc.mcnp.domain.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Serializable {
    private Integer id;
    private String libellePermissions;
}
