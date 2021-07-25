package com.esmc.mcnp.services.obpsd;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.repositories.obpsd.EuTraiteRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.google.common.collect.Lists;

@Service("euTraiteService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTraiteServiceImpl extends BaseServiceImpl<EuTraite, Long> implements EuTraiteService {

	private static final long serialVersionUID = 1L;
	private @Autowired EuTraiteRepository traiteRepo;

	@Override
	public Long getLastInsertedId() {
		return traiteRepo.getLastInsertedId();
	}

	@Override
	protected BaseRepository<EuTraite, Long> getRepository() {
		return traiteRepo;
	}

	@Override
	public Integer getTraiterByTpagcp(Long id) {
		return traiteRepo.getTraiterByTpagcp(id);
	}

	@Override
	public List<EuTraite> findByTpagcp(Long tpagcp) {
		return traiteRepo.findByTpagcp(tpagcp);
	}

	@Override
	public List<EuTraite> findByTpagcps(List<Long> tpagcps) {
		return traiteRepo.findByTraiteTegcs(tpagcps);
	}

	@Override
	public List<EuTraite> findTraiteByIds(List<Long> ids) {
		return traiteRepo.findTraiteByIds(ids);
	}

	@Override
	public List<EuTraite> findByTpagcps(List<Long> tpagcps, int disponible) {
		return traiteRepo.findByTraiteTegcs(tpagcps, disponible);
	}

	@Override
	public List<EuTraite> findByTpagcps(List<Long> tpagcps, int offset, int limit) {
		Page<EuTraite> page = traiteRepo.findByTraiteTegcs(tpagcps, PageRequest.of(offset, limit));
		return Lists.newArrayList(page.iterator());
	}

	@Override
	public List<EuTraite> findByTpagcps(List<Long> tpagcps, int disponible, int offset, int limit) {
		return Lists.newArrayList(traiteRepo.findByTraiteTegcs(tpagcps, disponible, PageRequest.of(offset, limit)));
	}

	@Override
	public Page<EuTraite> findByTpagcp(Long tpagcp, Pageable pageable) {
		return traiteRepo.findByTpagcp(tpagcp, pageable);
	}

	@Override
	public Page<EuTraite> findall(Pageable pageable) {
		return traiteRepo.findAll(pageable);
	}

	@Override
	public List<EuTraite> findByDateFin(Date datefin) {
		return traiteRepo.findAllByDateFin(datefin);
	}

	@Override
	public List<EuTraite> findTraitesNonEchu(Date date) {
		return traiteRepo.findAllByTraiteNonEchu(date);
	}

	@Override
	public Long getNbreTraiteDisponibleByTpa(Long idTpagcp) {
		return traiteRepo.getNbreTraiteDisponibleByTpa(idTpagcp).orElse(0L);
	}

	@Override
	public Double getSumTraiteDisponibleByTpa(Long idTpagcp) {
		return traiteRepo.getSumTraiteDisponibleByTpa(idTpagcp).orElse(0.0);
	}

	@Override
	public List<EuTraite> findTraiteDisponibleByTpa(Long idTpagcp) {
		return traiteRepo.findTraiteDisponibleByTpa(idTpagcp);
	}

	@Override
	public List<EuTraite> findTraiteDisponible() {
		return traiteRepo.findTraiteDisponible();
	}

	@Override
	public List<EuTraite> findTraitesNonEchu(Date dateDeb, Date dateFin) {
		return traiteRepo.findAllByTraiteNonEchu(dateDeb, dateFin);
	}

	@Override
	public List<EuTraite> findTraiteDispoByIds(List<Long> ids) {
		return traiteRepo.findTraiteDispoByIds(ids);
	}

	@Override
	public List<EuTraite> findTraiteDisponible(Long idTpagcp) {
		return traiteRepo.findTraiteDisponible(idTpagcp);
	}

	@Override
	public List<EuTraite> findAllTraiteByDate(Date datein, Date dateout) {
		return traiteRepo.findAllTraiteByDate(datein, dateout);
	}

	@Override
	public List<EuTraite> findAllTraiteByDateAndTpa(List<Long> tpas, Date datein, Date dateout) {
		return traiteRepo.findAllByDateAndTpa(tpas, datein, dateout);
	}

	@Override
	public List<EuTraite> findAllTraiteByDateFinAndTpa(List<Long> tpas, Date datefin) {
		return traiteRepo.findAllTraiteByDateFinAndTpa(tpas, datefin);
	}

	@Override
	public List<EuTraite> findAllTraiteByTpaAndDateFin(Long tpa, Date dateFin) {
		return traiteRepo.findAllTraiteByTpaAndDateFin(tpa, dateFin);
	}

	@Override
	public EuTraite findByNumeroTraite(String numero) {
		return traiteRepo.findByTraiteNumero(numero);
	}

	@Override
	public List<EuTraite> findByTraitePayer(int payer) {
		return traiteRepo.findByTraitePayer(payer);
	}

	@Override
	public List<EuTraite> findAllTraiteNonPayer() {
		return traiteRepo.findAllTraiteNonPayer();
	}

	@Override
	public List<EuTraite> findAllTraiteNonPayer(Long idTpagcp) {
		return traiteRepo.findAllTraiteNonPayer(idTpagcp);
	}

}
