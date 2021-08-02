package com.esmc.mcnp.infrastructure.services.acteurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.acteurs.EuActeurCreneauxRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuActeursCreneaux;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;



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
