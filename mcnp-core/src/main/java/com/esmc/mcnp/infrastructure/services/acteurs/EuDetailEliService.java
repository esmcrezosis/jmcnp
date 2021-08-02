package com.esmc.mcnp.infrastructure.services.acteurs;

import com.esmc.mcnp.domain.entity.acteur.EuDetailEli;
import com.esmc.mcnp.domain.entity.acteur.EuEli;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

import java.util.List;

public interface EuDetailEliService extends BaseService<EuDetailEli, Integer> {
	List<EuDetailEli> findByEliAndStatut(Integer idEli, Integer statut);

	double getSommeDetailByeliAndStatut(Integer idEli, Integer statut);

	List<EuDetailEli> findAllByIdEli(Integer idEli);

	double getSommeDetailByeli(Integer idEli);
	
	EuDetailEli addDetailEli(EuEli eli, String typeBps, String libelleProduit, int qte, double prixUnit, int qteVte, double prixVte, Integer statut);
	
}
