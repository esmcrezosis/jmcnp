package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.model.acteur.EuDepotVente;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuDepotVenteService extends CrudService<EuDepotVente, Long> {

	List<EuDepotVente> findByCodeMembre(String codeMembre);

	Page<EuDepotVente> findByMembre(String codeMembre, Pageable pageable);

	List<EuDepotVente> findByMembre(String nom, String prenom);

	Page<EuDepotVente> findByMembre(String nom, String prenom, Pageable pageable);

	List<EuDepotVente> fetchAllByDate(int min, int limit);

	boolean recouvrerCmfh(int size);

	boolean recouvrerCmfh(EuDepotVente depot);
	
	boolean recouvrerCmfh(String codeMembre, Double montant);

	List<EuDepotVente> findByMembre(String codeMembre);

	boolean recouvrerCmfh(List<EuDepotVente> depots, Double montant);
}
