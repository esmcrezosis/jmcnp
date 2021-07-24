package com.esmc.mcnp.services.oi;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.model.odd.EuMstier;
import com.esmc.mcnp.services.base.BaseService;

/**
 * Created by mawuli on 07/06/17.
 */
public interface EuMstiersService extends BaseService<EuMstier, Integer> {

	List<EuMstier> findByMembre(String codeMembre);

	Double getSumByMembre(String codeMembre);

	List<EuMstier> findByMembreAndStatut(String codeMembre, String statut);

	Double getSumByMembreAndStatut(String codeMembre, String statut);

	List<EuMstier> findByStatut(String statut);

	Double getSumByStatut(String statut);

	List<EuMstier> findBytypeAndStatut(String type, String statut);

	List<EuMstier> findByMembreAndTypeAndStatut(String codeMembre, String type, String statut);

	Double sumBytypeAndStatut(String type, String statut);

	Double sumByMembreAndTypeAndStatut(String codeMembre, String type, String statut);

	void updateMstier(String typeMstier, String statut, String codeMembre, String codeMembreBenef, double monant,
			Date date);

	void updateMstier(List<EuMstier> mstiers, String codeMembreBenef, double monant, Date date);
}
