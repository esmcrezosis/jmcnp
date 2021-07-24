package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuActeursCreneaux;
import com.esmc.mcnp.repositories.acteurs.EuActeurCreneauxRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;



@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuActeurCreneauxServiceImpl extends BaseServiceImpl<EuActeursCreneaux, String>
implements EuActeurCreneauxService {
	private static final long serialVersionUID = 1L;

    @Autowired
    private EuActeurCreneauxRepository acteurCreneauxRepo;

    @Override
    protected BaseRepository<EuActeursCreneaux, String> getRepository() {
        return acteurCreneauxRepo;
    }

	@Override
	public EuActeursCreneaux getActeurCreneauByCodemembre(String codemembre) {
		// TODO Auto-generated method stub
		return acteurCreneauxRepo.getActeurCreneauByCodemembre(codemembre);
	}
    
    
    
}
