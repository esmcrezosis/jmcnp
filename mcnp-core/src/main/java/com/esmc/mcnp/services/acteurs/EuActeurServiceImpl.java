package com.esmc.mcnp.services.acteurs;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.repositories.acteurs.EuActeurRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euActeurService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuActeurServiceImpl extends BaseServiceImpl<EuActeur, Integer> implements EuActeurService {

    /**
     *
     */
    private static final long serialVersionUID = 6300883036087525245L;
    private @Autowired
    EuActeurRepository acteurRepo;

    @Override
    protected BaseRepository<EuActeur, Integer> getRepository() {
        return acteurRepo;
    }

    @Override
    public EuActeur findByCodeMembre(String code_membre) {
        return acteurRepo.findByCodeMembre(code_membre);
    }

    @Override
    public EuActeur findSurveillanceSource() {
        return acteurRepo.findExecutanteSource();
    }

    @Override
    public EuActeur findSurveillance(String typeActeur) {
        return acteurRepo.findSurveillance(typeActeur);
    }

    @Override
    public EuActeur findExecutanteSource() {
        return acteurRepo.findSurveillanceSource();
    }

    @Override
    public EuActeur findActeurByCode(String code) {
        return acteurRepo.findActeurByCode(code);
    }

    @Override
    public EuActeur findActeurByTypeAndActivite(String type, String activite) {
        return acteurRepo.findActeurByTypeAndActivite(type, activite);
    }

    @Override
    public EuActeur findByTypeAndActiviteAndCanton(String type, String activite, Long canton) {
        return acteurRepo.findByTypeAndActiviteAndCanton(type, activite, canton);
    }

    @Override
    public EuActeur findByTypeAndActiviteAndPref(String type, String activite, Long pref) {
        return acteurRepo.findByTypeAndActiviteAndPref(type, activite, pref);
    }

    @Override
    public EuActeur findByTypeAndActiviteAndCRegion(String type, String activite, Integer region) {
        return acteurRepo.findByTypeAndActiviteAndRegion(type, activite, region);
    }

    @Override
    public EuActeur findByMembreAndType(String codeMembre, String typeacteur) {
        return acteurRepo.findByCodeMembreAndTypeActeur(codeMembre, typeacteur);
    }

    @Override
    public EuActeur findByMembreAndTypes(String codeMembre, List<String> typeacteurs) {
        return acteurRepo.findByCodeMembreAndTypeActeurs(codeMembre, typeacteurs);
    }

	@Override
	public EuActeur findAcnevByMembre(String codeMembre) {
		return acteurRepo.findACNEVByCodeMembre(codeMembre);
	}

	@Override
	public EuActeur findActeurByTypeAndActivite(String codeMembre, String type, String activite) {
		return acteurRepo.findActeurByTypeAndActivite(codeMembre, type, activite);
	}

	@Override
	public EuActeur findByActeurAndTypeAndActivite(String codeActeur, String type, String activite) {
		return acteurRepo.findByCodeActeurAndTypeActeurAndCodeActivite(codeActeur, type, activite);
	}

}
