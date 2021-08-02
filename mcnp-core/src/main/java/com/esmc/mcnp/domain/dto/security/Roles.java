package com.esmc.mcnp.domain.dto.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {
    private Integer id;
    private String codeRoles;
    private String libelleRoles;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateRoles;
    private Roles roleParent;
    private GroupeRoles groupeRoles;
}
