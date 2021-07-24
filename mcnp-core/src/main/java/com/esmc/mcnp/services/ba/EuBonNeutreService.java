package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.model.obpsd.EuBonNeutre;
import com.esmc.mcnp.model.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.services.base.BaseService;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface EuBonNeutreService extends CrudService<EuBonNeutre, Integer> {

	int getLastInsertedId();

	EuBonNeutre findByMembre(String codeMembre);

	EuBonNeutre findByCode(String code);

	Page<EuBonNeutre> findByDateBetween(Date deb, Date fin, Pageable pageable);

	Page<EuBonNeutre> findByDateSup(Date deb, Pageable pageable);

	Page<EuBonNeutre> findByDateInf(Date deb, Pageable pageable);

	Page<EuBonNeutre> findByType(String typeBn, Pageable pageable);

	Page<EuBonNeutre> findByCodeMembre(String codeMembre, Pageable pageable);

	Page<EuBonNeutre> findByNomAndBonNeutrePrenom(String nom, String prenom, Pageable pageable);

	Page<EuBonNeutre> findByNom(String nom, Pageable pageable);

	Page<EuBonNeutre> findByRaisonSociale(String raison, Pageable pageable);

	boolean updateBonNeutre(EuBonNeutre bonNeutre, String operation, String type, EuSmsmoney sms, double montant);

	boolean updateBonNeutre(EuBonNeutre bonNeutre, String operation, String type, double montant);

	EuBonNeutreAppro updateBonNeutreAppro(EuBonNeutre bonNeutre, String operation, String type, String codeMembreBenef,
			double montant);

}
