package com.esmc.mcnp.components;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.ksu.EuKacm;
import com.esmc.mcnp.model.obpsd.EuBonNeutre;
import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.services.ba.EuBonNeutreService;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.common.EuKacmService;
import com.esmc.mcnp.services.obpsd.EuSmsmoneyService;

@Component
@Transactional(readOnly = false)
public class BonAchatComponent {

	private final EuCompteService compteService;
	private final EuSmsmoneyService smsmoneyService;
	private final EuBonNeutreService bonNeutreService;
	private final TransfertService transfertService;
	private final EuCapaService capaService;
	private final EuMembreService membreService;
	private final EuMembreMoraleService moraleService;
	private final EuKacmService kacmService;

	public BonAchatComponent(EuCompteService compteService, EuSmsmoneyService smsmoneyService,
			EuBonNeutreService bonNeutreService, TransfertService transfertService, EuCapaService capaService,
			EuMembreService membreService, EuMembreMoraleService moraleService, EuKacmService kacmService) {
		this.compteService = compteService;
		this.smsmoneyService = smsmoneyService;
		this.bonNeutreService = bonNeutreService;
		this.transfertService = transfertService;
		this.capaService = capaService;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.kacmService = kacmService;
	}

	public boolean souscrireBonAchat(String codeMembre, String codeBan, String typeBa, double montant)
			throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException, DataAccessException {
		if (StringUtils.isNotBlank(codeMembre)) {
			String codeCompteCapa = "NN-CAPA-" + codeMembre;
			Date date = new Date();
			EuBonNeutre bonNeutre = null;
			if (StringUtils.isNotBlank(codeBan)) {
				bonNeutre = bonNeutreService.findByCode(codeBan);
			} else {
				bonNeutre = bonNeutreService.findByMembre(codeMembre);
			}
			if (Objects.nonNull(bonNeutre)) {
				if (bonNeutre.getBonNeutreMontantSolde() >= montant) {
					String codeSMS = transfertService.doTransfertDetail("CAPA", montant);
					if (StringUtils.isNotBlank(codeSMS)) {
						EuSmsmoney smsmoney = smsmoneyService.findByCreditcode(codeSMS);
						if (Objects.nonNull(smsmoney)) {
							bonNeutreService.updateBonNeutre(bonNeutre, "Souscription au Bon d'Achat", "BA", smsmoney,
									montant);
							EuCompte comptecapa = compteService.getById(codeCompteCapa);
							if (Objects.nonNull(comptecapa)) {
								comptecapa.setSolde(comptecapa.getSolde() + montant);
								compteService.update(comptecapa);
							} else {
								EuMembre membre = null;
								EuMembreMorale morale = null;
								if (codeMembre.endsWith("P")) {
									membre = membreService.findById(codeMembre);
								} else {
									morale = moraleService.findById(codeMembre);
								}
								comptecapa = new EuCompte();
								EuCategorieCompte cat = new EuCategorieCompte();
								cat.setCodeCat("CAPA");
								EuTypeCompte typeCompte = new EuTypeCompte();
								typeCompte.setCodeTypeCompte("NN");
								comptecapa.setCodeCompte(codeCompteCapa);
								comptecapa.setEuCategorieCompte(cat);
								comptecapa.setEuTypeCompte(typeCompte);
								if (codeMembre.endsWith("M")) {
									comptecapa.setEuMembreMorale(morale);
									comptecapa.setEuMembre(null);
								} else {
									comptecapa.setEuMembre(membre);
									comptecapa.setEuMembreMorale(null);
								}
								comptecapa.setLibCompte("Compte de CAPA");
								comptecapa.setDesactiver("N");
								comptecapa.setSolde(montant);
								comptecapa.setDateAlloc(date);
								comptecapa.setCardprinteddate(null);
								comptecapa.setCardprintediddate(0);
								comptecapa.setMifarecard(null);
								comptecapa.setNumeroCarte(null);
								compteService.create(comptecapa);
							}

							EuCapa eucapa = new EuCapa();
							eucapa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
							eucapa.setDateCapa(date);
							eucapa.setCodeMembre(codeMembre);
							eucapa.setCodeProduit(typeBa);
							eucapa.setTypeCapa("BA");
							eucapa.setOrigineCapa("BAN");
							eucapa.setMontantCapa(montant);
							eucapa.setMontantUtiliser(0);
							eucapa.setMontantSolde(montant);
							eucapa.setEuCompte(comptecapa);
							eucapa.setEtatCapa("Actif");
							eucapa.setIdOperation(null);
							eucapa.setHeureCapa(date);
							capaService.add(eucapa);

							smsmoney.setDestaccount(codeCompteCapa);
							smsmoney.setDatetimeconsumed(ServerUtil.formatDate(date));
							smsmoney.setIddatetimeconsumed((int) date.getTime());
							smsmoneyService.update(smsmoney);
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public boolean souscrireBonAchat(String codeMembre, String codeBan, String typeBa, double montant, boolean kacm,
			double mont_kacm)
			throws CompteNonIntegreException, CompteNonTrouveException, SoldeInsuffisantException, DataAccessException {
		if (StringUtils.isNotBlank(codeMembre)) {
			String codeCompteCapa = "NN-CAPA-" + codeMembre;
			Date date = new Date();
			EuBonNeutre bonNeutre = null;
			if (StringUtils.isNotBlank(codeBan)) {
				bonNeutre = bonNeutreService.findByCode(codeBan);
			} else {
				bonNeutre = bonNeutreService.findByMembre(codeMembre);
			}
			if (Objects.nonNull(bonNeutre)) {
				if (bonNeutre.getBonNeutreMontantSolde() >= montant) {
					String codeSMS = StringUtils.EMPTY;
					double mont_ba = montant;
					if (kacm) {
						mont_ba = montant - mont_kacm;
					}
					codeSMS = transfertService.doTransfertDetail("CAPA", mont_ba);
					if (StringUtils.isNotBlank(codeSMS)) {
						EuSmsmoney smsmoney = smsmoneyService.findByCreditcode(codeSMS);
						if (Objects.nonNull(smsmoney)) {
							bonNeutreService.updateBonNeutre(bonNeutre, "Souscription au Bon d'Achat", "BA", smsmoney,
									mont_ba);
							if (mont_kacm > 0) {
								bonNeutreService.updateBonNeutre(bonNeutre, "Souscription au Bon d'Achat", "BA",
										mont_kacm);
								EuKacm kacmp = new EuKacm();
								kacmp.setCodeMembre(codeMembre);
								kacmp.setTypeActivite("ARL");
								kacmp.setMontKacm(mont_kacm);
								kacmp.setMontOp(montant);
								kacmService.add(kacmp);
							}
							EuCompte comptecapa = compteService.getById(codeCompteCapa);
							if (Objects.nonNull(comptecapa)) {
								comptecapa.setSolde(comptecapa.getSolde() + mont_ba);
								compteService.update(comptecapa);
							} else {
								EuMembre membre = null;
								EuMembreMorale morale = null;
								if (codeMembre.endsWith("P")) {
									membre = membreService.findById(codeMembre);
								} else {
									morale = moraleService.findById(codeMembre);
								}
								comptecapa = new EuCompte();
								EuCategorieCompte cat = new EuCategorieCompte();
								cat.setCodeCat("CAPA");
								EuTypeCompte typeCompte = new EuTypeCompte();
								typeCompte.setCodeTypeCompte("NN");
								comptecapa.setCodeCompte(codeCompteCapa);
								comptecapa.setEuCategorieCompte(cat);
								comptecapa.setEuTypeCompte(typeCompte);
								if (codeMembre.endsWith("M")) {
									comptecapa.setEuMembreMorale(morale);
									comptecapa.setEuMembre(null);
								} else {
									comptecapa.setEuMembre(membre);
									comptecapa.setEuMembreMorale(null);
								}
								comptecapa.setLibCompte("Compte de CAPA");
								comptecapa.setDesactiver("N");
								comptecapa.setSolde(mont_ba);
								comptecapa.setDateAlloc(date);
								comptecapa.setCardprinteddate(null);
								comptecapa.setCardprintediddate(0);
								comptecapa.setMifarecard(null);
								comptecapa.setNumeroCarte(null);
								compteService.create(comptecapa);
							}

							EuCapa eucapa = new EuCapa();
							eucapa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
							eucapa.setDateCapa(date);
							eucapa.setCodeMembre(codeMembre);
							eucapa.setCodeProduit(typeBa);
							eucapa.setTypeCapa("BA");
							eucapa.setOrigineCapa("BAN");
							eucapa.setMontantCapa(mont_ba);
							eucapa.setMontantUtiliser(0);
							eucapa.setMontantSolde(mont_ba);
							eucapa.setEuCompte(comptecapa);
							eucapa.setEtatCapa("Actif");
							eucapa.setIdOperation(null);
							eucapa.setHeureCapa(date);
							capaService.add(eucapa);

							smsmoney.setDestaccount(codeCompteCapa);
							smsmoney.setDatetimeconsumed(ServerUtil.formatDate(date));
							smsmoney.setIddatetimeconsumed((int) date.getTime());
							smsmoneyService.update(smsmoney);
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
