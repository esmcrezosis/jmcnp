package com.esmc.mcnp.dto.org;

import com.esmc.mcnp.model.org.EuCanton;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Canton implements Serializable {
    private Integer idCanton;
    private String nomCanton;
    private Integer idVille;

    public Canton(EuCanton euCanton) {
        this.idCanton = euCanton.getIdCanton();
        this.nomCanton = euCanton.getNomCanton();
        this.idVille = euCanton.getIdVille();
    }

    public EuCanton toEuCanton() {
        return new EuCanton().setIdCanton(this.idCanton)
                .setNomCanton(this.nomCanton)
                .setIdVille(this.idVille);
    }

    public static List<Canton> fromEuCantons(List<EuCanton> euCantons) {
        List<Canton> cantons = Lists.newArrayList();
        euCantons.forEach(c -> {
            cantons.add(new Canton(c));
        });
        return cantons;
    }
}
