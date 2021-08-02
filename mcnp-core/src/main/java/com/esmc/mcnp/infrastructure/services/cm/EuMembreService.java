package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.domain.dto.cm.Membre;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

import java.util.Date;
import java.util.List;

public interface EuMembreService extends BaseService<EuMembre, String> {

    String genererCodeMembre(String codeAgence);

    String genererCodeMembreOdd(Integer id);

    EuMembre findByCodeMembre(String codeMembre);

    EuMembre createMembre(String nom, String prenom, Date dateNais, String sexe, String lieuNais, String sitFam, Integer nbreEnf, String adresse, String quartier, String ville, String telephone, String bp, String email, String profession, String codeAgence, Integer idAgenceOdd);

    EuMembre createMembre(String nom, String prenom, Date dateNais, String lieuNais, String sexe, String sitFam, Integer nbreEnf, String adresse, String quartier, String ville, String telephone,String bp, String email, String profession, String nomMere, String nomPere, String codeAgence, Integer idAgenceOdd);

    List<EuMembre> findByPortableAndDesactiver(String telephone, Integer desactiver);

    List<EuMembre> findByEmailAndDesactiver(String email, Integer desactiver);

    List<EuMembre> findByPortable(String telephone);

    List<EuMembre> findByEmail(String email);

    List<EuMembre> getListActivatedMembre();

    List<byte[]> getListActivatedEmpreintes();

    EuMembre createAdmin(Membre membre);

    String findMembreInfo(String codeMembre);

	String[] getMembreInfo(String codeMembre);
}
