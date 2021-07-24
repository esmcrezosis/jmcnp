package com.esmc.mcnp.web.mvc.rest;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.components.BonAchatComponent;
import com.esmc.mcnp.components.BonConsoComponent;
import com.esmc.mcnp.components.CreditUtility;
import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.services.ba.EuBonNeutreService;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.ba.EuCapaTsService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.common.EuCycleFormationService;
import com.esmc.mcnp.services.common.EuTypeCreditService;
import com.esmc.mcnp.web.mvc.dto.BnpParam;
import com.esmc.mcnp.web.mvc.dto.BonConso;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

@RestController
@RequestMapping(value = "bc")
public class BonController extends BaseRestController {

	private TransfertUtility transfertUtility;
	private SouscriptionBon souscriptionBon;
	private BonConsoComponent bonConsoComponent;
	private CreditUtility creditUtility;
	private EuCapaTsService capatsService;
	private EuCompteService compteService;
	private final BonAchatComponent bonAchatComponent;
	
	@Inject
	public BonController(TransfertUtility transfertUtility, EuTypeCreditService typeCreditService,
			SouscriptionBon souscriptionBon, BonConsoComponent bonConsoComponent, CreditUtility creditUtility,
			EuCapaService capaService, EuBonService bonService, EuCapaTsService capatsService,
			EuCompteService compteService, EuCompteCreditService compteCreditService,
			EuCycleFormationService cycleService, EuBonNeutreService bonNeutreService,
			EuBonNeutreDetailService bonNeutreDetailService, BonAchatComponent bonAchatComponent) {
		this.bonAchatComponent = bonAchatComponent;
		this.transfertUtility = transfertUtility;
		this.souscriptionBon = souscriptionBon;
		this.bonConsoComponent = bonConsoComponent;
		this.capatsService = capatsService;
		this.compteService = compteService;
		this.creditUtility = creditUtility;
	}
	
	@RequestMapping(value = "/calculBc", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> calculBcTiers(@RequestBody BnpParam param) {
        return new ResponseEntity<>(
                Math.floor(creditUtility.calculBcTiers(param.getTypeProduit(), param.getTypeBnp(), param.getMontBnp())),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/calculMsbc", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> calculMsBcTiers(@RequestBody BnpParam param) {
        return new ResponseEntity<>(
                Math.floor(
                        creditUtility.calculMsbcTiers(param.getTypeProduit(), param.getTypeBnp(), param.getMontBnp())),
                HttpStatus.OK);
    }

	@Transactional
	@PostMapping(value = "/bcr")
	public ResponseEntity<?> doBonConsor(BonConso bc) {
		List<EuCapaTs> capatss = capatsService.findByEuBon_BonNumero(bc.getCodeBon());
		String typeBon = "RPG";
		if (bc.getCodeMembre().endsWith("M")) {
			typeBon = "I";
		}
		String codeCompte = "NB-TPAGC" + typeBon + "-" + bc.getCodeMembre();
		String codeCompteNn = "NN-CAPA-" + bc.getCodeMembre();
		EuCompte compte = compteService.getById(codeCompte);
		EuCompte compteNn = compteService.getById(codeCompteNn);
		CalculBonInfo bonInfo = new CalculBonInfo();
		bonInfo.setCatBon(bc.getCatBonConso());
		bonInfo.setDuree(bc.getDuree());
		bonInfo.setPrk(bc.getPrk());
		if (StringUtils.isNotBlank(bc.getTypeProduit())) {
			bonInfo.setTypeProduit(bc.getTypeProduit());
		} else {
			bonInfo.setTypeProduit("PS");
		}
		bonInfo.setTypeRecurrent(bc.getTypeRecurrent());
		if (bc.getCodeMembre().endsWith("M")) {
			boolean result = bonConsoComponent.souscrireI(bc.getCodeMembre(), compte, compteNn, bc.getCodeBon(),
					capatss, typeBon, bonInfo, bc.getMontant());
			if (result) {
				return new ResponseEntity<>(
						new Result(1, "La souscription au Bon de consommation a été bien effectuée"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Result(1,
						"La souscription au Bon de consommation a échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			boolean result = bonConsoComponent.souscrireRpg(bc.getCodeMembre(), compte, compteNn, bc.getCodeBon(),
					typeBon, bonInfo, bc.getMontant());
			if (result) {
				return new ResponseEntity<>(
						new Result(1, "La souscription au Bon de consommation a été bien effectuée"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Result(1,
						"La souscription au Bon de consommation a échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Transactional
	@PostMapping(value = "/bcban")
	public ResponseEntity<Result> doBonConsoban(BonConso bc) {
		String typeBa = "RPG";
		if (bc.getCodeMembre().endsWith("M")) {
			typeBa = "I";
		}
		if (bonAchatComponent.souscrireBonAchat(bc.getCodeMembre(), bc.getCodeBon(), typeBa, bc.getMontant())) {
			EuBon bon = transfertUtility.transfertBA(bc.getCodeMembre(), "BAN", bc.getCatBonConso(), bc.getMontant());
			if (Objects.nonNull(bon)) {
				CalculBonInfo bonInfo = new CalculBonInfo();
				bonInfo.setCatBon(bc.getCatBonConso());
				bonInfo.setDuree(bc.getDuree());
				bonInfo.setPrk(bc.getPrk());
				bonInfo.setTypeProduit(bc.getTypeProduit());
				bonInfo.setTypeRecurrent(bc.getTypeRecurrent());
				int result = souscriptionBon.souscrireBonConso(bc.getCodeMembre(), bonInfo, bon.getBonNumero(),
						bon.getBonMontant());
				if (result == 0) {
					return new ResponseEntity<>(new Result(1, "Opération terminée avec succès"), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new Result(0,
							"La souscription au Bon de Consommationa échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur!!!"),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		return new ResponseEntity<>(new Result(0,
				"La souscription au Bon de Consommationa échouée; Veuillez réessayer ou contacter ESMC pour signaler l'erreur!!!"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
