package com.esmc.mcnp.dao.repository.cm;

import java.util.List;

import net.sf.ehcache.search.parser.MValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuMembre;

public interface EuMembreRepository extends BaseRepository<EuMembre, String> {

    @Query(value = "select max(m.code_membre) from eu_membre m where m.code_agence like :agence", nativeQuery = true)
    String getMaxCodeByAgence(@Param("agence") String codeAgence);

    @Query(value = "select max(m.code_membre) from eu_membre m where m.id_agences_odd like :id", nativeQuery = true)
    String getMaxCodeByAgenceOdd(@Param("id") Integer idAgenceOdd);

    EuMembre findByNomMembreAndPrenomMembre(String nomMembre, String prenomMmebre);

    @Query("select m from EuMembre m left join fetch m.euCanton c left join fetch m.euPay p where m.codeMembre = :code")
    EuMembre findByCodeMembre(@Param("code") String codeMembre);

    @Query("select m from EuMembre m where m.portableMembre like :tel and (m.desactiver = 0 or m.desactiver = 3)")
    List<EuMembre> findByPortableMembre(@Param("tel") String telephone);

    @Query("select m from EuMembre m where m.emailMembre like :mail and (m.desactiver = 0 or m.desactiver = 3)")
    List<EuMembre> findByEmailMembre(@Param("mail") String email);

    List<EuMembre> findByPortableMembreLikeAndDesactiver(String telephone, Integer desactiver);

    List<EuMembre> findByEmailMembreLikeAndDesactiver(String email, Integer desactiver);

    @Query("select m from EuMembre m where m.desactiver = 0")
    List<EuMembre> getListActivatedMembre();

    @Query("select m.empreinte1 from EuMembre m where m.desactiver = 0")
    List<byte[]> getListActivatedEmpreintes();

    @Query(value = "select max(m.code_membre) from eu_membre m where m.code_membre like :code", nativeQuery = true)
    String findMaxCodeByKey(@Param("code") String codeMembre);
}
