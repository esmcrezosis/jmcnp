package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.dto.cm.MembreMorale;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.services.base.BaseService;

import java.util.Date;
import java.util.List;

public interface EuMembreMoraleService extends BaseService<EuMembreMorale, String> {

    String generateCodeMembre(String codeAgence);

    String generateCodeMembreOdd(Integer id);

    EuMembreMorale createMembre(String raisonSociale, String codeStatut, String numeroRegistre, Date dateCreation, String domaineActivite, String adresse, String bp, String quartier, String ville, String telephone, String portable, String email, String siteWeb, String typeFournisseur, String codeAgence, Integer idAgenceOdd);

    EuMembreMorale findByRaisonSociale(String raison_sociale);

    EuMembreMorale findByCodeMembreMorale(String codeMembre);

    List<EuMembreMorale> findByPortableAndDesactiver(String telephone, Integer desactiver);

    List<EuMembreMorale> findByEmailAndDesactiver(String email, Integer desactiver);

    List<EuMembreMorale> findByPortable(String telephone);

    List<EuMembreMorale> findByEmail(String email);

    EuMembreMorale createAdmin(MembreMorale membreMorale);

    String findMembreInfo(String codeMembre);
}
