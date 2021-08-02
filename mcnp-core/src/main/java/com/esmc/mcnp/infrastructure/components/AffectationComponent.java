package com.esmc.mcnp.components;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.odd.EuMembreFifo;
import com.esmc.mcnp.model.odd.EuMstier;
import com.esmc.mcnp.model.odd.EuMstierListebc;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.oi.EuMembreFifoService;
import com.esmc.mcnp.services.oi.EuMstierListebcService;
import com.esmc.mcnp.services.oi.EuMstiersService;

@Component
public class AffectationComponent {

	private EuCompteService compteService;
	private EuMembreFifoService membreFifoService;
	private EuMstierListebcService mstierListebcService;
	private EuMstiersService mstierService;
	private SouscriptionBon souscriptionBon;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;
	private OpiUtility opiUtility;
	private CarteComponent carteComponent;
	private BonConsoComponent bonConsoComponent;
	private final BonAchatComponent bonAchatComponent;
	private TransfertUtility transfertUtility;

	private final String CODE_MEMBRE_ESMC = "0000000000000000007M";
	// private final String COMPTE_PC = "NR-TI-0000000000000000019M";
	private final String COMPTE_ESMC = "NR-TI-0000000000000000007M";

	@Autowired
	public AffectationComponent(EuMembreMoraleService moraleService, EuCompteService compteService,
			EuMembreFifoService membreFifoService, EuMembreService membreService, OpiUtility opiUtility,
			CarteComponent carteComponent, EuMstierListebcService mstierListebcService,
			BonConsoComponent bonConsoComponent, EuMstiersService mstierService, TransfertUtility transfertUtility,
			SouscriptionBon souscriptionBon, BonAchatComponent bonAchatComponent) {
		this.moraleService = moraleService;
		this.compteService = compteService;
		this.membreFifoService = membreFifoService;
		this.membreService = membreService;
		this.opiUtility = opiUtility;
		this.carteComponent = carteComponent;
		this.mstierListebcService = mstierListebcService;
		this.bonConsoComponent = bonConsoComponent;
		this.mstierService = mstierService;
		this.transfertUtility = transfertUtility;
		this.souscriptionBon = souscriptionBon;
		this.bonAchatComponent = bonAchatComponent;
	}

	@Transactional(rollbackFor = Exception.class)
	public void affecterBanTiers() {
		List<Integer> affecters = Arrays.asList(0, 1);
		List<EuMembreFifo> fifos = membreFifoService.findByNonAffecter(2, 0, affecters);
		if (!fifos.isEmpty()) {
			EuMembreMorale morale = moraleService.findById(CODE_MEMBRE_ESMC);
			fifos.forEach(m -> {
				EuMembre membre = membreService.findByCodeMembre(m.getCodeMembreBenef());
				if (Objects.nonNull(membre)) {
					if (membre.getDesactiver() == 2 && m.getAffecter() == 0) {
						double montban = 0;
						if (membre.getAutoEnroler().equals("O") || carteComponent.isCmfh(membre.getCodeMembre())
								|| carteComponent.isMF(membre.getCodeMembre())) {
							montban = 22000;
						} else {
							montban = 25000;
						}
						String numeroEli = "ELI-" + ServerUtil.GenerateUnicCode();
						opiUtility.doBan(new Date(), morale, null, membre.getCodeMembre(), "ELI-BC", numeroEli, null,
								montban);
						compteService.updateCompte(COMPTE_ESMC, membre.getCodeMembre(), "Affectation du BAn pr tiers",
								montban);
						m.setAffecter(1);
						membreFifoService.update(m);
					} else if (membre.getDesactiver() == 0) {
						List<EuMstier> mstiers = mstierService.findBytypeAndStatut("BC", "SansListe");
						if (!mstiers.isEmpty()) {
							String numeroEli = "ELI-" + ServerUtil.GenerateUnicCode();
							opiUtility.doBan(new Date(), morale, null, membre.getCodeMembre(), "ELI-BC", numeroEli,
									null, 672000);
							bonAchatComponent.souscrireBonAchat(membre.getCodeMembre(), "", "RPG", 672000);
							EuBon ba = transfertUtility.transfertBA(membre.getCodeMembre(), "BAN", "", 672000.0);
							if (Objects.nonNull(ba)) {
								System.out.println("Transfert BA OK Numero " + ba.getBonNumero());
								String typeBon = "RPG";
								String codeCompte = "NB-TPAGCRPG-" + membre.getCodeMembre();
								String codeCompteNn = "NN-CAPA-" + membre.getCodeMembre();
								EuCompte compte = compteService.getById(codeCompte);
								EuCompte compteNn = compteService.getById(codeCompteNn);
								CalculBonInfo bonInfo = new CalculBonInfo();
								bonInfo.setCatBon("r");
								bonInfo.setDuree(-1);
								bonInfo.setPrk(0);
								bonInfo.setTypeProduit("PS");
								bonInfo.setTypeRecurrent("illimite");
								bonConsoComponent.souscrireRpg(membre.getCodeMembre(), compte, compteNn,
										ba.getBonNumero(), typeBon, bonInfo, 672000);
								mstierService.updateMstier(mstiers, membre.getCodeMembre(), 1800000, new Date());
								m.setAffecter(2);
								membreFifoService.update(m);
								compteService.updateCompte(COMPTE_ESMC, membre.getCodeMembre(),
										"Affectation du BAn pr tiers", 672000);
							}
						}
					}
				}
			});
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void affecterBanTiersAvecListe() {
		List<EuMstierListebc> listes = mstierListebcService.findByStatut();
		if (!listes.isEmpty()) {
			EuMembreMorale morale = moraleService.findById(CODE_MEMBRE_ESMC);
			listes.forEach(m -> {
				EuMembre membre = membreService.findByCodeMembre(m.getCodeMembreBenef());
				if (Objects.nonNull(membre)) {
					if (membre.getDesactiver() == 2 && m.getStatut() == 0) {
						double montban = 0;
						if (membre.getAutoEnroler().equals("O") || carteComponent.isCmfh(membre.getCodeMembre())
								|| carteComponent.isMF(membre.getCodeMembre())) {
							montban = 22000;
						} else {
							montban = 25000;
						}
						String numeroEli = "ELI-" + ServerUtil.GenerateUnicCode();
						opiUtility.doBan(new Date(), morale, null, membre.getCodeMembre(), "ELI-BC", numeroEli, null,
								montban);
						compteService.updateCompte(COMPTE_ESMC, membre.getCodeMembre(), "Affectation du BAn pr tiers",
								montban);
						m.setStatut(1);
						mstierListebcService.update(m);
					} else if (membre.getDesactiver() == 0) {
						List<EuMstier> mstiers = mstierService.findByMembreAndTypeAndStatut(m.getCodeMembreApporteur(),
								"BC", "AvecListe");
						double montant = mstierService.sumByMembreAndTypeAndStatut(m.getCodeMembreApporteur(), "BC",
								"AvecListe");
						if (!mstiers.isEmpty() && montant > 0) {
							String numeroEli = "ELI-" + ServerUtil.GenerateUnicCode();
							opiUtility.doBan(new Date(), morale, null, membre.getCodeMembre(), "ELI-BC", numeroEli,
									null, 672000);
							bonAchatComponent.souscrireBonAchat(membre.getCodeMembre(), "", "RPG", 672000);
							EuBon ba = transfertUtility.transfertBA(membre.getCodeMembre(), "BAN", "", 672000.0);
							String typeBon = "RPG";
							String codeCompte = "NB-TPAGCRPG-" + membre.getCodeMembre();
							String codeCompteNn = "NN-CAPA-" + membre.getCodeMembre();
							EuCompte compte = compteService.getById(codeCompte);
							EuCompte compteNn = compteService.getById(codeCompteNn);
							CalculBonInfo bonInfo = new CalculBonInfo();
							bonInfo.setCatBon("r");
							bonInfo.setDuree(-1);
							bonInfo.setPrk(0);
							bonInfo.setTypeProduit("PS");
							bonInfo.setTypeRecurrent("illimite");
							bonConsoComponent.souscrireRpg(membre.getCodeMembre(), compte, compteNn, ba.getBonNumero(),
									typeBon, bonInfo, 672000);
							if (montant % 1443750 != 0) {
								mstierService.updateMstier(mstiers, membre.getCodeMembre(), 1800000, new Date());
							} else {
								mstierService.updateMstier(mstiers, membre.getCodeMembre(), 1443750, new Date());
							}
							m.setStatut(2);
							mstierListebcService.update(m);
							compteService.updateCompte(COMPTE_ESMC, membre.getCodeMembre(),
									"Affectation du BAn pr tiers", 672000);
						}
					}
				}
			});
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void affecterBan(String codeMembreEmetteur, String codeMembre, String type, double montBan) {
		EuMembre membre = null;
		EuMembreMorale morale = null;
		if (codeMembre.endsWith("P")) {
			membre = membreService.findByCodeMembre(codeMembre);
		} else {
			morale = moraleService.findById(codeMembre);
		}
		if (Objects.nonNull(membre) || Objects.nonNull(morale)) {
			double montant = montBan;
			if (type.equals("KCM")) {
				if (Objects.nonNull(membre)) {
					if (membre.getAutoEnroler().equals("O") || carteComponent.isCmfh(membre.getCodeMembre())
							|| carteComponent.isMF(membre.getCodeMembre())) {
						montant = 22000;
					} else {
						montant = 25000;
					}
				} else {
					montant = 25000;
				}
			}
			EuMembreMorale esmc = moraleService.findById(codeMembreEmetteur);
			String numeroEli = "ELI-" + ServerUtil.GenerateUnicCode();
			if (Objects.nonNull(membre)) {
				opiUtility.doBan(new Date(), esmc, null, membre.getCodeMembre(), "BUDGET", numeroEli, null, montant);
				compteService.updateCompte(COMPTE_ESMC, membre.getCodeMembre(), "Affectation du BAn ESMC", montBan);
			} else {
				opiUtility.doBan(new Date(), esmc, null, morale.getCodeMembreMorale(), "BUDGET", numeroEli, null,
						montant);
				compteService.updateCompte(COMPTE_ESMC, morale.getCodeMembreMorale(), "Affectation du BAn ESMC",
						montBan);
			}
		}
	}

}
