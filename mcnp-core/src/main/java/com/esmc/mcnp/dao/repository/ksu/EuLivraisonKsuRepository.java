package com.esmc.mcnp.dao.repository.ksu;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.ksu.EuCarte;
import com.esmc.mcnp.domain.entity.ksu.EuLivraisonKsu;

public interface EuLivraisonKsuRepository extends BaseRepository<EuLivraisonKsu, Long> {
	
	@Query("select l.cartes from EuLivraisonKsu l where l.id = :id")
	List<EuCarte> findByLivraison(@Param("id") Long id);

	List<EuLivraisonKsu> findByDateDemande(LocalDate dateDemande);

	List<EuLivraisonKsu> findByDateDemandeBetween(LocalDate deb, LocalDate fin);

	Page<EuLivraisonKsu> findByDateDemande(LocalDate dateDemande, Pageable page);

	Page<EuLivraisonKsu> findByDateDemandeBetween(LocalDate deb, LocalDate fin, Pageable page);

	List<EuLivraisonKsu> findByUser_IdUtilisateur(Long id);

	List<EuLivraisonKsu> findByUser_IdUtilisateurAndDateDemande(Long id, LocalDate dateDemande);
	
	List<EuLivraisonKsu> findByUser_IdUtilisateurAndDateDemandeBetween(Long id, LocalDate deb, LocalDate fin);

	EuLivraisonKsu findByCodeSuivi(String code);

	Page<EuLivraisonKsu> findByUser_IdUtilisateur(Long id, Pageable page);

	Page<EuLivraisonKsu> findByUser_IdUtilisateurAndDateDemande(Long id, LocalDate dateDemande, Pageable page);
	
	Page<EuLivraisonKsu> findByUser_IdUtilisateurAndDateDemandeBetween(Long id, LocalDate deb, LocalDate fin, Pageable page);
}
