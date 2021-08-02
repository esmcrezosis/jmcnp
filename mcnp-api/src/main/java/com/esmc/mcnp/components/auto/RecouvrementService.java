package com.esmc.mcnp.components.auto;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.common.EuParametreRepository;
import com.esmc.mcnp.infrastructure.components.AffectationComponent;
import com.esmc.mcnp.infrastructure.components.OPIComponent;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.infrastructure.services.cmfh.EuDepotVenteService;
import com.esmc.mcnp.infrastructure.services.pc.EuRecouvrementMcnpService;

@Component
@Transactional
public class RecouvrementService {

	private @Autowired EuDepotVenteService depotVenteService;
	private @Autowired EuRecouvrementMcnpService recouvrementService;
	private @Autowired TransfertUtility transfertUtility;
	private @Autowired Payement payement;
	private @Autowired EuParametreRepository paramRepo;
	private @Autowired AffectationComponent affectationComponent;
	private @Autowired OPIComponent opiComponent;

	public void recouverMf() {

	}

	public void recouvrerCMFH() {
		int size = paramRepo.findByCodeAndLib("page", "nombre").intValue();
		boolean isDone = depotVenteService.recouvrerCmfh(size);
		if (isDone) {

		}
	}

	@Async
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void affecterBanTiers() {
		affectationComponent.affecterBanTiers();
	}

	@Async
	@Scheduled(cron = "0 0/15 * * * ?")
	// @Scheduled(cron = "0 0 0 * * ?")
	public void affecterBanTiersAvecListe() {
		affectationComponent.affecterBanTiersAvecListe();
	}

	@Scheduled(fixedRate = 3600000)
	@Transactional(rollbackFor = Exception.class)
	public void initialiseEcheanceOpi() {
		Calendar datedeb = new GregorianCalendar();
		datedeb.set(2018, 11, 5);
		Calendar datefin = new GregorianCalendar();
		datefin.set(2018, 04, 19);
		opiComponent.updateOpiEcheance(null, datedeb.getTime(), datefin.getTime());
	}

}
