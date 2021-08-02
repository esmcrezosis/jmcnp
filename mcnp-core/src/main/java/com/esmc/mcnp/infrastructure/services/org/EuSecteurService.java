package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.domain.entity.org.EuSecteur;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface EuSecteurService extends CrudService<EuSecteur, String> {
    EuSecteur findByCanton(Integer idCanton);
}
