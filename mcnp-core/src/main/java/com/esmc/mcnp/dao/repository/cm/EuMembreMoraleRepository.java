package com.esmc.mcnp.dao.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

public interface EuMembreMoraleRepository extends BaseRepository<EuMembreMorale, String> {

    @Query(value = "select max(m.code_membre_morale) from eu_membre_morale m where m.code_agence like :agence", nativeQuery = true)
    String getMaxCodeByAgence(@Param("agence") String codeAgence);

    @Query(value = "select max(m.code_membre_morale) from eu_membre_morale m where m.id_agences_odd like :id", nativeQuery = true)
    String getMaxCodeByAgenceOdd(@Param("id") Integer idAgenceOdd);

    EuMembreMorale findByRaisonSociale(String raison_sociale);

    @Query("select m from EuMembreMorale m left join fetch m.euCanton c left join fetch m.euPay p left join fetch m.euRepresentations r left join fetch r.euMembre e where m.codeMembreMorale = :code ")
    EuMembreMorale findByKey(@Param("code") String id);

    @Query("select m from EuMembreMorale m left join m.euRepresentations r left join r.euMembre e where e.codeMembre = :code ")
    List<EuMembreMorale> findByEuRepresentation(@Param("code") String codeMembre);

    @Query("select m from EuMembreMorale m left join m.euRepresentations r left join r.euMembre e where e.nomMembre like :nom and e.prenomMembre like :prenom")
    List<EuMembreMorale> findByEuRepresentation_NomPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("select m from EuMembreMorale m where m.portableMembre like :tel and (m.desactiver = 0 or m.desactiver = 3)")
	List<EuMembreMorale> findByPortableMembre(@Param("tel") String telephone);

	@Query("select m from EuMembreMorale m where m.emailMembre like :mail and (m.desactiver = 0 or m.desactiver = 3)")
	List<EuMembreMorale> findByEmailMembre(@Param("mail") String email);

	List<EuMembreMorale> findByPortableMembreLikeAndDesactiver(String telephone, Integer desactiver);

	List<EuMembreMorale> findByEmailMembreLikeAndDesactiver(String email, Integer desactiver);

	@Query(value = "select max(m.code_membre_morale) from eu_membre_morale m where m.code_membre_morale like :code", nativeQuery = true)
	String findByPrimaryKey(@Param("code") String key);
}
