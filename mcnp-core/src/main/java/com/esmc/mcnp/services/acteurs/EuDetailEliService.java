package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuDetailEli;
import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.services.base.BaseService;

import java.util.List;

public interface EuDetailEliService extends BaseService<EuDetailEli, Integer> {
	List<EuDetailEli> findByEliAndStatut(Integer idEli, Integer statut);

	double getSommeDetailByeliAndStatut(Integer idEli, Integer statut);

	List<EuDetailEli> findAllByIdEli(Integer idEli);

	double getSommeDetailByeli(Integer idEli);
	
	EuDetailEli addDetailEli(EuEli eli, String typeBps, String libelleProduit, int qte, double prixUnit, int qteVte, double prixVte, Integer statut);
	
}
