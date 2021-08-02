package com.esmc.mcnp.web.mvc.rest;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esmc.mcnp.commons.model.AjaxResult;
import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.franchise.EuTypeFranchises;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.components.TransfertUtility;
import com.esmc.mcnp.infrastructure.services.bc.EuBonService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteCreditService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.franchise.EuTypeFranchiseService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.web.mvc.dto.PayerFranchiseRequest;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

@RestController
@RequestMapping(value = "franchise")
public class FranchiseController extends BaseRestController {
	private final Payement payement;
	private final TransfertUtility transfertUtility;
	private final EuTegcService tegcService;
	private final EuBonService bonService;
	private final EuMembreMoraleService moraleService;
	private final EuCompteCreditService ccService;
	private final EuParametresService parametresService;
	private final EuTypeFranchiseService typeFranchiseService;

	@Autowired
	public FranchiseController(Payement payement, EuMembreMoraleService moraleService,
			EuParametresService parametresService, EuTypeFranchiseService typeFranchiseService,
			TransfertUtility transfertUtility, EuTegcService tegcService, EuBonService bonService,
			EuCompteCreditService ccService) {
		this.payement = payement;
		this.transfertUtility = transfertUtility;
		this.tegcService = tegcService;
		this.bonService = bonService;
		this.moraleService = moraleService;
		this.ccService = ccService;
		this.parametresService = parametresService;
		this.typeFranchiseService = typeFranchiseService;
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "payer")
	public AjaxResult payerFranchise(PayerFranchiseRequest payer) {
		EuTypeFranchises typefranchise = typeFranchiseService.getById(payer.getTypeFranchise());
		if (Objects.nonNull(typefranchise)) {
			String codeMembreESMC = parametresService.getStringParam("ESMC", "GIE");
			if (StringUtils.isNotBlank(codeMembreESMC)) {
				EuMembreMorale franchise = moraleService.findById(payer.getCodeMembre());
				if (Objects.nonNull(franchise) && franchise.getDesactiver() == 0) {
					String codeCompte = "NB-TPAGCI-" + payer.getCodeMembre();
					String codeProduit = "Inr";
					if ("BCi".equals(payer.getCategorieBon())) {
						codeCompte = "NB-TBC-" + payer.getCodeMembre();
						codeProduit = payer.getCategorieBon();
					}
					Double soldeCc = ccService.getSumCreditByCompteAndCodeProduit(codeCompte, codeProduit);
					if (soldeCc >= typefranchise.getMontantFranchise()) {
						EuTegc tegc;
						if (StringUtils.isNotBlank(payer.getCodeTegc())) {
							tegc = tegcService.getById(payer.getCodeTegc());
						} else {
							tegc = tegcService.findByCodeMembre(codeMembreESMC).get(0);
						}
						if (Objects.nonNull(tegc)) {
							try {
								EuBon bl = payement.createBonLivraison(codeMembreESMC, payer.getCodeMembre(), "", null,
										0, typefranchise.getMontantFranchise());
								if (Objects.nonNull(bl)) {
									bl.setBonExprimer(1);
									bl.setBonDateExpression(new Date());
									bonService.update(bl);

									CalculBonInfo bonInfo = new CalculBonInfo();
									bonInfo.setCatBon("nr");
									bonInfo.setTypeProduit("PS");

									String typeBc = StringUtils.EMPTY;
									if ("BCnr".equals(payer.getCategorieBon())) {
										typeBc = "I";
									} else {
										typeBc = "BCi";
									}
									EuBon bon = transfertUtility.tansfertBC(payer.getCodeMembre(), typeBc, bonInfo, 8.0,
											typefranchise.getMontantFranchise());
									if (Objects.nonNull(bon)) {
										String codeCat = "TPAGCI";
										if ("BCi".equals(payer.getCategorieBon())) {
											codeCat = "TBC";
										}
										payement.makeTransaction(codeMembreESMC, payer.getCodeMembre(), codeCat, tegc,
												bon, bl, "PS", typefranchise.getMontantFranchise());
									}
								}
								return AjaxResult.success("Opération effectuée avec succès", 1);
							} catch (Exception e) {
								return AjaxResult.error(0, "Echec de l'opération");
							}
						} else {
							return AjaxResult.error(0, "Echec de l'opération : le vendeur ne dispose pas de TE");
						}
					} else {
						return AjaxResult.error(0, "Echec de l'opération : solde du compte insuffisant");
					}
				} else {
					return AjaxResult.error(0, "Echec de l'opération : le compte du franchisé n'est pas activé");
				}
			}
		}
		return AjaxResult.error(0, "Echec de l'opération");
	}
}
