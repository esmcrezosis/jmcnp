package com.esmc.mcnp.web.mvc.model.echange;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class EchBcForm {
    private String codeMembre;
    private String type;
    private Double montant;
    private List<BcEchange> details;
}
