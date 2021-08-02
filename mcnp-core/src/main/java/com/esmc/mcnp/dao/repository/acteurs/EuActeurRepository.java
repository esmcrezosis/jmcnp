package com.esmc.mcnp.dao.repository.acteurs;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;

@Repository
public interface EuActeurRepository extends BaseRepository<EuActeur, Integer> {

	public EuActeur findByCodeMembre(String code_membre);

	@Query("select ac from EuActeur ac where ac.typeActeur ='gac_surveillance' and ac.codeActivite = 'SOURCE'")
	public EuActeur findSurveillanceSource();

	@Query("select ac from EuActeur ac where ac.typeActeur = :type")
	public EuActeur findSurveillance(@Param("type") String typeActeur);

	@Query("select ac from EuActeur ac where ac.typeActeur ='gac_executante' and ac.codeActivite = 'SOURCE'")
	public EuActeur findExecutanteSource();

	@Query("select ac from EuActeur ac where ac.codeMembre like :codeMembre and (ac.typeActeur ='ACNEV' or ac.codeActivite = 'ACNEV')")
	public EuActeur findACNEVByCodeMembre(@Param("codeMembre") String codeMembre);
	
	@Query("select a from EuActeur a where a.codeMembre like :codeMembre and a.typeActeur like :type and a.codeActivite like :activite")
	public EuActeur findActeurByTypeAndActivite(@Param("codeMembre") String codeMembre, @Param("type") String type,
			@Param("activite") String activite);

	@Query("select a from EuActeur a where a.codeActeur like :code")
	public EuActeur findActeurByCode(@Param("code") String code);

	@Query("select a from EuActeur a where a.typeActeur like :type and a.codeActivite like :activite")
	public EuActeur findActeurByTypeAndActivite(@Param("type") String type, @Param("activite") String activite);

	@Query("select a from EuActeur a where a.typeActeur like :type and a.codeActivite like :activite and a.idCanton = :canton")
	public EuActeur findByTypeAndActiviteAndCanton(@Param("type") String type, @Param("activite") String activite,
			@Param("canton") Long canton);

	@Query("select a from EuActeur a where a.typeActeur like :type and a.codeActivite like :activite and a.idPrefecture = :pref")
	public EuActeur findByTypeAndActiviteAndPref(@Param("type") String type, @Param("activite") String activite,
			@Param("pref") Long pref);

	@Query("select a from EuActeur a where a.typeActeur like :type and a.codeActivite like :activite and a.idRegion = :region")
	public EuActeur findByTypeAndActiviteAndRegion(@Param("type") String type, @Param("activite") String activite,
			@Param("region") Integer region);

	public EuActeur findByCodeMembreAndTypeActeur(String codeMembre, String type);
	
	public EuActeur findByCodeActeurAndTypeActeurAndCodeActivite(String code, String type, String activite);

	@Query("select a from EuActeur a where a.codeMembre like :membre and a.typeActeur in (:types)")
	public EuActeur findByCodeMembreAndTypeActeurs(@Param("membre") String codeMembre,
			@Param("types") List<String> typeActeurs);
}
