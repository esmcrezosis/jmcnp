package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.services.base.BaseService;

import java.util.List;

public interface EuActeurService extends BaseService<EuActeur, Integer> {

    EuActeur findByCodeMembre(String code_membre);

    EuActeur findByMembreAndType(String codeMembre, String typeacteur);

    EuActeur findByMembreAndTypes(String codeMembre, List<String> typeacteurs);

    EuActeur findSurveillanceSource();

    EuActeur findSurveillance(String typeActeur);

    EuActeur findExecutanteSource();

    EuActeur findActeurByCode(String code);

    EuActeur findActeurByTypeAndActivite(String type, String activite);
    
    EuActeur findByActeurAndTypeAndActivite(String codeActeur, String type, String activite);

    EuActeur findByTypeAndActiviteAndCanton(String type, String activite, Long canton);

    EuActeur findByTypeAndActiviteAndPref(String type, String activite, Long pref);

    EuActeur findByTypeAndActiviteAndCRegion(String type, String activite, Integer region);
    
    EuActeur findAcnevByMembre(String codeMembre);
    
    EuActeur findActeurByTypeAndActivite(String codeMembre, String type, String activite);
}
