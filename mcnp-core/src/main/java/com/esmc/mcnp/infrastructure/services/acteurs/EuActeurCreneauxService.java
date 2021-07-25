package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuActeursCreneaux;
import com.esmc.mcnp.services.base.BaseService;


public interface EuActeurCreneauxService extends BaseService<EuActeursCreneaux, String> {


public EuActeursCreneaux getActeurCreneauByCodemembre(String codemembre);
}
