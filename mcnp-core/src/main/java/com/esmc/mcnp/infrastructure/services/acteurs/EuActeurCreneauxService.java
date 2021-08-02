package com.esmc.mcnp.infrastructure.services.acteurs;

import com.esmc.mcnp.domain.entity.acteur.EuActeursCreneaux;
import com.esmc.mcnp.infrastructure.services.base.BaseService;


public interface EuActeurCreneauxService extends BaseService<EuActeursCreneaux, String> {


public EuActeursCreneaux getActeurCreneauByCodemembre(String codemembre);
}
