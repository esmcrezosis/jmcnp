package com.esmc.mcnp.services.org;

import com.esmc.mcnp.model.org.EuSecteur;
import com.esmc.mcnp.services.base.CrudService;

public interface EuSecteurService extends CrudService<EuSecteur, String> {
    EuSecteur findByCanton(Integer idCanton);
}
