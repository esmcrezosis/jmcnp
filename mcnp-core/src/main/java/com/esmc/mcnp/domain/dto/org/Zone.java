package com.esmc.mcnp.dto.org;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Zone implements Serializable {
    private String codeZone;
    private Date dateCreation;
    private String nomZone;
}
