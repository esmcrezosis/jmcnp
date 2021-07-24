package com.esmc.mcnp.web.model.ajax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class JsonResult {

    private Integer id;
    private String valeur;

    public JsonResult() {
    }
}
