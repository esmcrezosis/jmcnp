package com.esmc.mcnp.services.ksu;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.ksu.EuCarte;
import com.esmc.mcnp.model.ksu.EuLivraisonKsu;
import com.esmc.mcnp.services.base.CrudService;

public interface EuLivraisonKsuService extends CrudService<EuLivraisonKsu, Long> {

	List<EuLivraisonKsu> findByUser(Long id);

	List<EuLivraisonKsu> findByUser(Long id, LocalDate dateDemande);

	Page<EuLivraisonKsu> findByUser(Long id, Pageable page);

	Page<EuLivraisonKsu> findByUser(Long id, LocalDate dateDemande, Pageable page);
	
	List<EuLivraisonKsu> findByUserAndDateDemande(Long id, LocalDate deb, LocalDate fin);
	
	List<EuLivraisonKsu> findByDateDemande(LocalDate dateDemande);

	List<EuLivraisonKsu> findByDateDemandeBetween(LocalDate deb, LocalDate fin);

	Page<EuLivraisonKsu> findByDateDemande(LocalDate dateDemande, Pageable page);

	Page<EuLivraisonKsu> findByDateDemandeBetween(LocalDate deb, LocalDate fin, Pageable page);

	EuLivraisonKsu findByCodeSuivi(String code);
	
	List<EuCarte> findCarteByLivraison(Long idLivraison);
	
	Page<EuLivraisonKsu> findByUserAndDateDemande(Long id, LocalDate deb, LocalDate fin, Pageable page);
}
