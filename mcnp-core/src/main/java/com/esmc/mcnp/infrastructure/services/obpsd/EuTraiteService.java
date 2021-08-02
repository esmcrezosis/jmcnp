package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuTraiteService extends BaseService<EuTraite, Long> {

	Long getLastInsertedId();

	Page<EuTraite> findall(Pageable pageable);

	Integer getTraiterByTpagcp(Long id);

	List<EuTraite> findByTpagcp(Long tpagcp);
	
	EuTraite findByNumeroTraite(String numero);

	Page<EuTraite> findByTpagcp(Long tpagcp, Pageable pageable);

	List<EuTraite> findByTpagcps(List<Long> tpagcps);

	List<EuTraite> findTraiteByIds(List<Long> ids);

	List<EuTraite> findTraiteDispoByIds(List<Long> ids);

	List<EuTraite> findByTpagcps(List<Long> tpagcps, int offset, int limit);

	List<EuTraite> findByTpagcps(List<Long> tpagcps, int disponible);

	List<EuTraite> findByTpagcps(List<Long> tpagcps, int disponible, int offset, int limit);

	List<EuTraite> findByDateFin(Date datefin);

	List<EuTraite> findAllTraiteByDate(Date datein, Date dateout);

	List<EuTraite> findAllTraiteByDateAndTpa(List<Long> tpas, Date datein, Date dateout);

	List<EuTraite> findAllTraiteByDateFinAndTpa(List<Long> tpas, Date datefin);

	List<EuTraite> findTraitesNonEchu(Date date);

	List<EuTraite> findTraitesNonEchu(Date dateDeb, Date dateFin);

	Long getNbreTraiteDisponibleByTpa(Long idTpagcp);

	Double getSumTraiteDisponibleByTpa(Long idTpagcp);

	List<EuTraite> findTraiteDisponibleByTpa(Long idTpagcp);

	List<EuTraite> findTraiteDisponible();

	List<EuTraite> findTraiteDisponible(Long idTpagcp);

	List<EuTraite> findAllTraiteByTpaAndDateFin(Long tpa, Date dateFin);
	
	List<EuTraite> findByTraitePayer(int payer);

	List<EuTraite> findAllTraiteNonPayer();

	List<EuTraite> findAllTraiteNonPayer(Long idTpagcp);
	
}
