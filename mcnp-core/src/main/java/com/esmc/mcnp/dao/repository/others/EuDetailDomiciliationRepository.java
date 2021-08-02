/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.others;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomicilie;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomiciliePK;

/**
 * @author USER
 */
public interface EuDetailDomiciliationRepository extends
BaseRepository<EuDetailDomicilie, EuDetailDomiciliePK> {

    EuDetailDomicilie findById_IdCredit(long id_credit);

    EuDetailDomicilie findById_IdCreditAndUtiliser(long id_credit,
                                                   Integer utiliser);

    @Query("select dd from EuDetailDomicilie dd join fetch dd.euMembreMorale m join fetch dd.euDomiciliation d where m.codeMembreMorale= :codeMembre and dd.consommer =NULL")
    List<EuDetailDomicilie> findByCodemembreMorale(@Param("codeMembre") String codemembre);

    @Query("select dd from EuDetailDomicilie dd join fetch dd.euMembre m join fetch dd.euDomiciliation d where m.codeMembre= :codeMembre and dd.consommer =NULL")
    List<EuDetailDomicilie> findByCodemembre(@Param("codeMembre") String codemembre);

    @Query("select dd from EuDetailDomicilie dd join fetch dd.euDomiciliation d join fetch d.euSmcipnpwi where dd.id.idCredit= :idCredit and dd.utiliser = :utiliser")
    EuDetailDomicilie findByIdCreditAndUtiliser(@Param("idCredit") Long idCredit,
                                                @Param("utiliser") Integer utiliser);

    @Query("select sum(cc.montantPlace) from EuDetailDomicilie dd join dd.euCompteCredit cc where cc.idCredit = :idCredit")
    Double findSommeCreditPlaces(@Param("idCredit") Long idCredit);

}
