package com.esmc.mcnp.web.controller.bc;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.http.ResponseEntity;

import com.esmc.mcnp.components.CreditComponent;
import com.esmc.mcnp.core.utils.SecurityUtils;
import com.esmc.mcnp.core.utils.StringUtils;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuOperationService;
import com.esmc.mcnp.util.Reponse;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.model.echange.BcEchange;

public class ApproBonConsoController extends BaseController {

	private final EuCompteService compteService;
	private final EuCompteCreditService compteCreditService;
	private final EuMembreService membreService;
	private final EuMembreMoraleService moraleService;
	private final EuOperationService opService;
	private final CreditComponent creditComponent;

	public ApproBonConsoController(EuCompteService compteService, EuCompteCreditService compteCreditService,
			EuMembreService membreService, EuMembreMoraleService moraleService, EuOperationService opService,
			CreditComponent creditComponent) {

		this.compteService = compteService;
		this.compteCreditService = compteCreditService;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.opService = opService;
		this.creditComponent = creditComponent;
	}

	public ResponseEntity<?> approBc(BcEchange bcEchange) {
		if (bcEchange.getBc() > 0 && StringUtils.isNotBlank(bcEchange.getCodeMembre())
				&& StringUtils.isNotBlank(bcEchange.getCodeMembreDest())) {
			EuMembre membre = null;
			EuMembreMorale morale = null;
			if (bcEchange.getCodeMembre().endsWith("P")) {
				membre = membreService.findById(bcEchange.getCodeMembre());
			} else {
				morale = moraleService.findById(bcEchange.getCodeMembre());
			}
			if ((Objects.nonNull(membre) && membre.getDesactiver() == 0)
					|| (Objects.nonNull(morale) && morale.getDesactiver() == 0)) {
				var codeProduit = "BCi";
				var codeCategorie = "TBC";
				var codeCompteOrg = "NB-" + codeCategorie + "-" + bcEchange.getCodeMembre();
				var compteOrg = compteService.getById(codeCompteOrg);
				List<EuCompteCredit> credits = null;
				if ("BCi".equalsIgnoreCase(bcEchange.getTypeBc())) {
					
				} else {
					if (bcEchange.getCatBon().equalsIgnoreCase("r")) {
						if (StringUtils.isNotBlank(bcEchange.getTypeRecurrent()) && bcEchange.getDuree() != null) {
							credits = ListUtils.emptyIfNull(
									compteCreditService.findByTypeRecurrentAndTypeProduitAndDuree(codeCompteOrg,
											codeProduit, bcEchange.getTypeRecurrent(), "PS", bcEchange.getDuree()));
						} else if (StringUtils.isNotBlank(bcEchange.getTypeRecurrent())) {
							credits = ListUtils.emptyIfNull(compteCreditService.findByTypeRecurrentAndTypeProduit(
									codeCompteOrg, codeProduit, bcEchange.getTypeRecurrent(), "PS"));
						} else {
							credits = ListUtils.emptyIfNull(
									compteCreditService.findbyCompteAndProduit(codeCompteOrg, codeProduit));
						}
					} else {
						if (bcEchange.getPrk() != null) {
							credits = ListUtils.emptyIfNull(compteCreditService.fetchByCompteAndProduitAndPrk(
									codeCompteOrg, codeProduit, bcEchange.getPrk(), "PS"));
						} else {
							credits = ListUtils.emptyIfNull(
									compteCreditService.findbyCompteAndProduit(codeCompteOrg, codeProduit));
						}
					}
				}
				if (!credits.isEmpty()) {
					var date = new Date();
					double soldeCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
					if (soldeCredit >= bcEchange.getBc()) {
						List<Long> ids = credits.stream().map(EuCompteCredit::getIdCredit).collect(Collectors.toList());
						compteService.updateCompteWithCnp(compteOrg, bcEchange.getCodeMembre(), ids, "Appro de BCi",
								bcEchange.getBc());
						var codeCompteDest = "NB-TBC-" + bcEchange.getCodeMembreDest();
						EuCategorieCompte categorie = new EuCategorieCompte(codeCategorie);
						EuOperation op = new EuOperation();
						op.setEuCategorieCompte(categorie);
						op.setCodeProduit("BC");
						op.setDateOp(date);
						op.setLibOp("Appro de Bon de Consommation");
						op.setTypeOp("AP");
						op.setMontantOp(bcEchange.getBc());
						if (bcEchange.getCodeMembre().endsWith("P")) {
							op.setEuMembre(compteOrg.getEuMembre());
						} else {
							op.setEuMembreMorale(compteOrg.getEuMembreMorale());
						}
						op.setEuUtilisateur(SecurityUtils.getPrincipal());
						op.setHeureOp(date);
						opService.add(op);

						EuCompte compteDest = compteService.createOrUpdate("NB", "TBC", bcEchange.getCodeMembreDest(),
								bcEchange.getBc());
						CalculBonInfo bonInfo = new CalculBonInfo();
						bonInfo.setCatBon("nr");
						bonInfo.setPrk(5.6);
						bonInfo.setDuree(5.6);
						bonInfo.setTypeProduit("PS");
						creditComponent.createCredit(compteDest, new EuProduit("BCi"), bcEchange.getBc(),
								bcEchange.getBc(), false, null, op, codeCompteDest, bcEchange.getCodeMembreDest(),
								bonInfo);
					} else {
						return ResponseEntity.badRequest().body(new Reponse("Echec de l'opération: Solde insuffisant"));
					}
				} else {
					return ResponseEntity.badRequest()
							.body(new Reponse("Echec de l'opération: Aucune ligne de BC correspondante trouvée"));
				}
			} else {
				return ResponseEntity.badRequest()
						.body(new Reponse("Echec de l'opération: Votre compte n'est pas activé"));
			}
		}
		return ResponseEntity.badRequest().body(new Reponse("Echec de l'opération: Donées insuffisantes"));
	}
}
