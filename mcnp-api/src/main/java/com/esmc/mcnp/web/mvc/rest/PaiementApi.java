package com.esmc.mcnp.web.mvc.rest;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.pc.EuCharge;
import com.esmc.mcnp.domain.entity.pc.EuChargePaye;
import com.esmc.mcnp.infrastructure.components.CommandeComponent;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.infrastructure.services.bc.EuBonService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.obps.EuCommandeService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargePayeService;
import com.esmc.mcnp.infrastructure.services.pc.EuChargeService;
import com.esmc.mcnp.web.mvc.dto.CommandeRequest;
import com.esmc.mcnp.web.mvc.dto.NCreance;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

public class PaiementApi extends BaseRestController {

	private SmcipnComponent smcipnComp;
	private TransfertUtility transfertUtility;
	private EuTegcService tegcService;
	private EuBonService bonService;
	private Payement payement;
	private EuChargeService chargeService;
	private EuChargePayeService chargePayeService;
	private EuCompteService compteService;
	private CommandeComponent commandeCompo;
	private EuCommandeService commandeService;

	@Autowired
	public PaiementApi(SmcipnComponent smcipnComp, TransfertUtility transfertUtility, Payement payement,
			EuTegcService tegcService, EuBonService bonService, EuCompteService compteService,
			EuChargeService chargeService, EuChargePayeService chargePayeService, CommandeComponent commandeCompo,
			EuCommandeService commandeService) {
		this.smcipnComp = smcipnComp;
		this.transfertUtility = transfertUtility;
		this.payement = payement;
		this.tegcService = tegcService;
		this.bonService = bonService;
		this.chargeService = chargeService;
		this.chargePayeService = chargePayeService;
		this.compteService = compteService;
		this.commandeCompo = commandeCompo;
		this.commandeService = commandeService;
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/annulerCommande")
	public ResponseEntity<Result> annulerCommande(@RequestBody CommandeRequest commandeRequest) {
		if (commandeRequest.getCodeCommande() != null) {
			EuCommande commande = commandeService.findById(commandeRequest.getCodeCommande());
			if (Objects.nonNull(commande)) {
				boolean res = commandeCompo.annulerCommande(commande);
				if (res) {
					return new ResponseEntity<>(new Result(1, "L'opération a été effectuée avec succès"),
							HttpStatus.CREATED);
				} else {
					return new ResponseEntity<>(new Result(0, "Echec de l'annulation de la commande"),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(new Result(0,
						"Echec de l'opération : La commande N° " + commandeRequest.getCodeCommande() + " n'existe pas"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(new Result(0, "Echec de l'opération : Veuillez fournir un numéro de commande!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/livrerCommande")
	public ResponseEntity<Result> payerCommande(@RequestBody CommandeRequest commande) {
		if (Objects.nonNull(commande)) {
			if (StringUtils.isNotBlank(commande.getCodeConfirmation())
					&& StringUtils.isNotBlank(commande.getCodeTegcLivreur()) && commande.getIdUtilisateur() != null) {
				EuCommande com = commandeService.findByCodeConfirmation(commande.getCodeConfirmation());
				if (Objects.nonNull(com)) {
					EuTegc tegc = tegcService.findByCodeTegc(commande.getCodeTegcLivreur());
					if (Objects.nonNull(tegc)) {
						com.setCodeMembreLivreur(tegc.getEuMembreMorale().getCodeMembreMorale());
						commandeService.update(com);
						boolean res = commandeCompo.executeCommandeLivrer(com, tegc.getCodeTegc(),
								commande.getIdUtilisateur());
						if (res) {
							return new ResponseEntity<>(new Result(1, "L'opération a été effectuée avec succès"),
									HttpStatus.CREATED);
						} else {
							return new ResponseEntity<>(new Result(0, "Echec de l'opération de confirmation"),
									HttpStatus.INTERNAL_SERVER_ERROR);
						}
					} else {
						return new ResponseEntity<>(
								new Result(0, "Echec de l'opération : Le Livreur n'a pas de terminal d'echange"),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					return new ResponseEntity<>(new Result(0, "Echec de l'opération :Code de Confirmation non valide"),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(
						new Result(0, "Echec de l'opération : Veuillez fournir toutes les informations nécéssaires"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(
					new Result(0, "Echec de l'opération : Veuillez fournir toutes les informations nécéssaires"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@PostMapping(value = "/reglement")
	public ResponseEntity<Result> doPayercharge(@RequestBody NCreance creance) {
		final String comptePC = "NR-TI-0000000000000000019M";
		final String codeMembreSmc = "0000000000000000019M";
		if (StringUtils.isNotBlank(creance.getCodeMembreCreancier()) && StringUtils.isNotBlank(creance.getCodeTegc())
				&& creance.getMontantOp() > 0) {
			EuTegc tegc = tegcService.getById(creance.getCodeTegc());
			if (Objects.nonNull(tegc)) {
				try {
					EuCompte compteDeb = compteService.getById(comptePC);
					if (compteDeb.getSolde() >= creance.getMontantOp()) {
						EuBon bl = payement.createBonLivraison(creance.getCodeMembreCreancier(), codeMembreSmc, "",
								null, 0, creance.getMontantOp());
						if (Objects.nonNull(bl)) {

							bl.setBonExprimer(1);
							bl.setBonDateExpression(new Date());
							bonService.update(bl);

							CalculBonInfo bonInfo = new CalculBonInfo();
							bonInfo.setCatBon("nr");
							bonInfo.setTypeProduit("PS");
							smcipnComp.echangeSmcipn(codeMembreSmc, "TPN", "PS", creance.getMontantOp(), 8.0);

							EuBon bon = transfertUtility.tansfertBC(codeMembreSmc, "TI", bonInfo, 8.0,
									creance.getMontantOp());
							if (Objects.nonNull(bon)) {
								payement.makeTransaction(creance.getCodeMembreCreancier(), codeMembreSmc, "TI", tegc,
										bon, bl, "PS", creance.getMontantOp());
								payement.creerMarge(new Date(), creance.getMontantOp());
							}

							EuCharge tcharge = chargeService.findByCode("CF");
							EuChargePaye chargePaye = new EuChargePaye();
							chargePaye.setEuSmcipnpwi(null);
							chargePaye.setEuCharge(tcharge);
							chargePaye.setNumDoc(null);
							chargePaye.setCodeMembreDebiteur(creance.getCodeMembreDebiteur());
							chargePaye.setCodeMembreCreancier(creance.getCodeMembreCreancier());
							chargePaye.setDateCharge(new Date());
							chargePaye.setLibelleCharge("Achat en central d'achat");
							chargePaye.setMontantCharge(creance.getMontantOp());
							chargePaye.setOrigineCharge("ESMC");
							chargePayeService.add(chargePaye);
						}
						return new ResponseEntity<>(new Result(1, "L'opération a été effectuée avec succès"),
								HttpStatus.CREATED);
					} else {
						return new ResponseEntity<>(
								new Result(0, "Echec de l'opération : Le quota autorisé est dépassé"),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} catch (IllegalArgumentException | CompteNonTrouveException | NullPointerException e) {
					return new ResponseEntity<>(new Result(0, "Echec de l'opération : Le quota autorisé est dépassé"),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(new Result(0, "Echec de l'opération : Le quota autorisé est dépassé"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(new Result(0, "Echec de l'opération : Le quota autorisé est dépassé"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
