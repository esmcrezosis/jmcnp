package com.esmc.mcnp.web.mvc.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.model.odd.EuMstierListecm;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.common.EuReligionService;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.odd.EuOddService;
import com.esmc.mcnp.services.oi.EuMstierListebcService;
import com.esmc.mcnp.services.oi.EuMstierListecmService;
import com.esmc.mcnp.services.org.EuCantonService;
import com.esmc.mcnp.services.org.EuPaysService;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

@RestController
@RequestMapping(value = "odd")
public class OddController extends BaseRestController {
	private EuOddService oddService;
	private EuAgenceOddService agenceOddService;
	private EuMstierListecmService mstierListecmService;
	private EuMstierListebcService msListebcService;
	private EuCantonService cantonService;
	private EuReligionService religionService;
	private EuPaysService paysService;

	@Autowired
	public OddController(EuOddService oddService, EuAgenceOddService agenceOddService,
			EuMstierListecmService mstierListecmService,EuMstierListebcService msListebcService, EuCantonService cantonService,
			EuReligionService religionService, EuPaysService paysService) {
		this.oddService = oddService;
		this.agenceOddService = agenceOddService;
		this.mstierListecmService = mstierListecmService;
		this.cantonService = cantonService;
		this.religionService = religionService;
		this.paysService = paysService;
		this.msListebcService = msListebcService;
	}

	@Transactional
	@PostMapping(value = "fichecm")
	public Result saveFicheOddCm(EuMstierListecm mstierListecm) {
		if (Objects.nonNull(mstierListecm)) {
			try {
				List<EuMstierListecm> listes = mstierListecmService.findDoublonByNom(mstierListecm.getNomMembre(),
						mstierListecm.getPrenomMembre());
				long compte = 0;
				if (listes.size() > 1) {
					compte = listes.stream()
							.filter(l -> (l.getDateNaisMembre().isEqual(mstierListecm.getDateNaisMembre())
									&& l.getLieuNaisMembre().equalsIgnoreCase(mstierListecm.getLieuNaisMembre())))
							.count();
				}
				if (compte <= 1) {
					EuUtilisateur user = getCurrentUser();
					mstierListecm.setUser(user);
					mstierListecm.setDateListecm(LocalDateTime.now());
					mstierListecm.setAgence(user.getEuAgence());
					mstierListecm.setStatut(0);
					if (listes.size() > 1) {
						mstierListecm.setDoublon(1);
					} else {
						mstierListecm.setDoublon(0);
					}
					mstierListecm.setCodesecret(RandomStringUtils.randomAlphanumeric(8));
					if (Objects.nonNull(user.getAgencesOdd())) {
						mstierListecm.setAgenceOdd(user.getAgencesOdd());
					}
					mstierListecmService.create(mstierListecm);
					return new Result(0, "Fiche ODD bien enregistrée");
				} else {
					return new Result(0, "Cette personne est déjà enregistrée");
				}
			} catch (Exception e) {
				return new Result(0, "Echec de l'opération: " + e.getMessage());
			}
		} else {
			return new Result(0, "Veuillez renseigner tous les champs obligatoires");
		}
	}
	
	@Transactional
	@PostMapping(value = "fichebc")
	public Result saveFicheOddBc(EuMstierListecm mstierListecm) {
		if (Objects.nonNull(mstierListecm)) {
			try {
				List<EuMstierListecm> listes = mstierListecmService.findDoublonByNom(mstierListecm.getNomMembre(),
						mstierListecm.getPrenomMembre());
				long compte = 0;
				if (listes.size() > 1) {
					compte = listes.stream()
							.filter(l -> (l.getDateNaisMembre().isEqual(mstierListecm.getDateNaisMembre())
									&& l.getLieuNaisMembre().equalsIgnoreCase(mstierListecm.getLieuNaisMembre())))
							.count();
				}
				if (compte <= 1) {
					EuUtilisateur user = getCurrentUser();
					mstierListecm.setUser(user);
					mstierListecm.setDateListecm(LocalDateTime.now());
					mstierListecm.setAgence(user.getEuAgence());
					mstierListecm.setStatut(0);
					if (listes.size() > 1) {
						mstierListecm.setDoublon(1);
					} else {
						mstierListecm.setDoublon(0);
					}
					mstierListecm.setCodesecret(RandomStringUtils.randomAlphanumeric(8));
					if (Objects.nonNull(user.getAgencesOdd())) {
						mstierListecm.setAgenceOdd(user.getAgencesOdd());
					}
					mstierListecmService.create(mstierListecm);
					return new Result(0, "Fiche ODD bien enregistrée");
				} else {
					return new Result(0, "Cette personne est déjà enregistrée");
				}
			} catch (Exception e) {
				return new Result(0, "Echec de l'opération: " + e.getMessage());
			}
		} else {
			return new Result(0, "Veuillez renseigner tous les champs obligatoires");
		}
	}
}
