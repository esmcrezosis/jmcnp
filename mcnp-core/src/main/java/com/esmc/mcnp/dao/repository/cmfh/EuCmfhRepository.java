/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.cmfh;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cmfh.EuCmfh;

/**
 * Objet d'accés aux données de la table eu_cmfh représentée par l'entité
 * com.esmc.mcnp.model.cmfh.EuCmfh
 *
 * @author Mawuli AKLASSOU
 */
public interface EuCmfhRepository extends BaseRepository<EuCmfh, Long> {

	List<EuCmfh> findAllByCodeMembre(String codeMembre);

	EuCmfh findByCodeMembre(String codeMembre);

	List<EuCmfh> findByRembourser(Integer rembourser);

	EuCmfh findByCodeMembreAndRembourser(String codeMembre, Integer rembourser);
}
