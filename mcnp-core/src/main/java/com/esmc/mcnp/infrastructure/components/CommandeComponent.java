package com.esmc.mcnp.infrastructure.components;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.dto.obps.ArticleVendu;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.obps.EuAnnulationCommande;
import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommande;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.ba.EuCapaService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteCreditService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.obps.EuAnnulationCommandeService;
import com.esmc.mcnp.infrastructure.services.obps.EuDetailCommandeService;
import com.esmc.mcnp.infrastructure.services.obps.EuPrkService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.google.common.collect.Lists;

@Component
public class CommandeComponent {

	private final EuAnnulationCommandeService annulCommService;
	private final EuBonNeutreService bnService;
	private final EuBonNeutreDetailService bnDetailService;
	private final EuCompteService compteService;
	private final EuCompteCreditService compteCreditService;
	private final EuCapaService capaService;
	private final EuTegcService tegcService;
	private final EuDetailCommandeService detCdeService;
	private final SouscriptionBon souscriptionBon;
	private final BonAchatComponent bonAchatComponent;
	private final TransfertUtility transfertUtility;
	private final EuPrkService prkService;
	private final QuotaUtility quotaUtility;
	private final CreditUtility creditUtility;
	private final ReglementAchat reglementAchat;
	private final EchangeService echangeService;
	private final Payement payement;

	public CommandeComponent(EuAnnulationCommandeService annulCommService, EuBonNeutreService bnService,
			EuBonNeutreDetailService bnDetailService, EuCompteService compteService,
			EuCompteCreditService compteCreditService, EuCapaService capaService, EuTegcService tegcService,
			EuDetailCommandeService detCdeService, SouscriptionBon souscriptionBon, TransfertUtility transfertUtility,
			EuPrkService prkService, QuotaUtility quotaUtility, CreditUtility creditUtility,
			ReglementAchat reglementAchat, EchangeService echangeService, Payement payement, BonAchatComponent bonAchatComponent) {
		this.annulCommService = annulCommService;
		this.bnService = bnService;
		this.bnDetailService = bnDetailService;
		this.compteService = compteService;
		this.compteCreditService = compteCreditService;
		this.capaService = capaService;
		this.tegcService = tegcService;
		this.detCdeService = detCdeService;
		this.souscriptionBon = souscriptionBon;
		this.bonAchatComponent = bonAchatComponent;
		this.transfertUtility = transfertUtility;
		this.prkService = prkService;
		this.quotaUtility = quotaUtility;
		this.creditUtility = creditUtility;
		this.reglementAchat = reglementAchat;
		this.echangeService = echangeService;
		this.payement = payement;
	}

	@Transactional
	public boolean annulerCommande(EuCommande commande) {
		List<EuAnnulationCommande> cdeAnnules = annulCommService.findByComande(commande.getCodeCommande().intValue());
		Double montCde = cdeAnnules.stream().mapToDouble(EuAnnulationCommande::getMontant).sum();
		if (montCde > 0 && !cdeAnnules.isEmpty()) {
			if (commande.getTypeBon().equals("BAN")) {
				cdeAnnules.forEach(c -> {
					EuBonNeutreDetail bnDet = bnDetailService.getById(Integer.parseInt(c.getIdDetail()));
					if (Objects.nonNull(bnDet)) {
						bnDet.setBonNeutreDetailMontant(bnDet.getBonNeutreDetailMontant() + c.getMontant());
						bnDet.setBonNeutreDetailMontantUtilise(
								bnDet.getBonNeutreDetailMontantUtilise() - c.getMontant());
						bnDet.setBonNeutreDetailMontantSolde(bnDet.getBonNeutreDetailMontantSolde() - c.getMontant());
						bnDetailService.update(bnDet);
					}
				});
				EuBonNeutre bn = bnService.findByMembre(commande.getCodeMembreAcheteur());
				if (Objects.nonNull(bn)) {
					bn.setBonNeutreMontant(bn.getBonNeutreMontant() + montCde);
					bn.setBonNeutreMontantUtilise(bn.getBonNeutreMontantUtilise() - montCde);
					bn.setBonNeutreMontantSolde(bn.getBonNeutreMontantSolde() + montCde);
					bnService.update(bn);
					return true;
				}
			} else if (commande.getTypeBon().equals("BC")) {
				cdeAnnules.forEach(c -> {
					EuCompteCredit cc = compteCreditService.getById(Long.parseLong(c.getIdDetail()));
					if (Objects.nonNull(cc)) {
						cc.setMontantCredit(cc.getMontantCredit() + c.getMontant());
						compteCreditService.update(cc);
					}
				});
				String codeCompte = StringUtils.EMPTY;
				if (commande.getCodeMembreAcheteur().endsWith("P")) {
					codeCompte = "NB-TPAGCRPG-" + commande.getCodeMembreAcheteur();
				} else {
					codeCompte = "NB-TPAGCI-" + commande.getCodeMembreAcheteur();
				}
				EuCompte compte = compteService.getById(codeCompte);
				compte.setSolde(compte.getSolde() + montCde);
				compteService.update(compte);
				return true;
			} else {
				cdeAnnules.forEach(c -> {
					EuCapa capa = capaService.findById(c.getIdDetail());
					if (Objects.nonNull(capa)) {
						capa.setMontantCapa(capa.getMontantCapa() + c.getMontant());
						capa.setMontantUtiliser(capa.getMontantUtiliser() - c.getMontant());
						capa.setMontantSolde(capa.getMontantSolde() + c.getMontant());
						capaService.update(capa);
					}
				});
				EuCompte compte = compteService.getById("NN-CAPA-" + commande.getCodeMembreAcheteur());
				compte.setSolde(compte.getSolde() + montCde);
				compteService.update(compte);
				return true;
			}
		}
		return false;
	}

	@Transactional
	public boolean executeCommandeLivrer(EuCommande commande, String codeTegcLivreur, Long idUtilisateur) {
		if (Objects.nonNull(commande) && StringUtils.isNotBlank(commande.getTypeBon())
				&& StringUtils.isNotBlank(commande.getCodeMembreAcheteur())
				&& StringUtils.isNotBlank(commande.getCodeTegcVendeur())) {
			double montantAchat = commande.getMontantCommande() + commande.getMontantLivraison();
			EuTegc tegcVendeur = tegcService.getById(commande.getCodeTegcVendeur());
			if (Objects.isNull(tegcVendeur)) {
				throw new CompteNonTrouveException("Le TEGC du " + commande.getCodeMembreVendeur() + " non trouvé");
			}
			// retrouver la liste des articles
			List<ArticleVendu> articleVendus = Lists.newArrayList();
			List<EuDetailCommande> ListDetailsCommande = ListUtils
					.emptyIfNull(detCdeService.findDetailsCommandeByCodeCommande(commande.getCodeCommande()));

			if (!ListDetailsCommande.isEmpty()) {
				ListDetailsCommande.forEach((euDetailCommande) -> {
					ArticleVendu articleVendu = new ArticleVendu();
					articleVendu.setCodeBarre(euDetailCommande.getCodeBarre());
					articleVendu.setCodeMembreAcheteur(commande.getCodeMembreAcheteur());
					articleVendu.setCodeTegc(tegcVendeur.getCodeTegc());
					articleVendu.setDesignation(euDetailCommande.getDesignation());
					articleVendu.setPrix(euDetailCommande.getPrixUnitaire());
					articleVendu.setQuantite(euDetailCommande.getQte());
					articleVendu.setReference(euDetailCommande.getReference());
					articleVendus.add(articleVendu);
				});
				try {
					if (commande.getTypeBon().equals("BAN")) {
						String typeBa = "RPG";
						if (commande.getCodeMembreAcheteur().endsWith("M")) {
							typeBa = "I";
						}
						EuBonNeutre bonNeutre = bnService.findByMembre(commande.getCodeMembreAcheteur());
						boolean result = bonAchatComponent.souscrireBonAchat(commande.getCodeMembreAcheteur(),
								bonNeutre.getBonNeutreCode(), typeBa, montantAchat);
						if (!result) {
							throw new RuntimeException("Echec de la souscription au Bon d'Achat");
						}
					}
					String codeTypeCredit = StringUtils.EMPTY;
					double duree = 0;
					String periode = commande.getPeriodeRecurrent();
					String typeRecurrent = StringUtils.EMPTY;
					if (periode.equals("illimite")) {
						duree = 0d;
						typeRecurrent = "illimite";
					} else if ((periode.equals("limite 11.2"))) {
						duree = 11.2;
						typeRecurrent = "limite";
					} else if (periode.equals("limite 22.4")) {
						duree = 22.4;
						typeRecurrent = "limite";
					} else if (periode.equals("limite 1")) {
						duree = 1d;
						typeRecurrent = "limite";
					}
					double prk = prkService.findPrkByTegc(tegcVendeur.getCodeTegc()).getValeur();
					CalculBonInfo calculBonInfo = new CalculBonInfo();
					if (commande.getTypeRecurrent().equals("nr")) {
						codeTypeCredit = prkService.findPrkByTegc(tegcVendeur.getCodeTegc()).getEuTypeCredit()
								.getCodeTypeCredit();
						calculBonInfo.setCatBon(commande.getTypeRecurrent());
						calculBonInfo.setTypeRecurrent(typeRecurrent);
						calculBonInfo.setTypeProduit("PS");
						calculBonInfo.setPrk(prk);
						calculBonInfo.setDuree(duree);

					} else {
						codeTypeCredit = prkService.findPrkByTegc(tegcVendeur.getCodeTegc()).getEuTypeCredit()
								.getCodeTypeCredit();
						calculBonInfo.setCatBon(commande.getTypeRecurrent());
						calculBonInfo.setTypeRecurrent(typeRecurrent);
						calculBonInfo.setTypeProduit("PS");
						calculBonInfo.setPrk(0);
						calculBonInfo.setDuree(duree);
					}

					if (commande.getTypeBon().equals("BAN") || commande.getTypeBon().equals("BA")) {
						EuBon ba = null;
						if (commande.getTypeBon().equals("BAN")) {
							ba = transfertUtility.transfertBA(commande.getCodeMembreAcheteur(), "BAN", "",
									montantAchat);
						} else {
							ba = transfertUtility.transfertBA(commande.getCodeMembreAcheteur(), "BAi", "",
									montantAchat);
						}
						souscriptionBon.souscrireBonConso(commande.getCodeMembreAcheteur(), calculBonInfo,
								ba.getBonNumero(), ba.getBonMontant());
					} else {
						Double montantBc = creditUtility.calculBonConso(new CalculBonInfo(commande.getTypeRecurrent(),
								typeRecurrent, "PS", duree, prk, commande.getFrequence(), commande.getBps()),
								Math.floor(montantAchat));
						echangeService.echangeGCP(commande.getCodeMembreAcheteur(), "NB/NB", montantBc, "Inr", null);
					}

					Double montantBonConsommation = creditUtility.calculBonConso(
							new CalculBonInfo(commande.getTypeRecurrent(), typeRecurrent, "PS", duree, prk,
									commande.getFrequence(), commande.getBps()),
							Math.floor(commande.getMontantCommande()));
					boolean verifyQuota = quotaUtility.verifyQuota(codeTypeCredit, commande.getCodeMembreAcheteur(),
							montantBonConsommation, commande.getTypeRecurrent());
					if (verifyQuota) {
						String typeBC = "";
						String nomProduit = "";
						Integer subvention = 0;
						String compteVendeur = "NB-TPAGCP-" + commande.getCodeMembreVendeur();
						if (commande.getCodeMembreAcheteur().endsWith("P")) {
							typeBC = "RPG";
						} else {
							typeBC = "I";
						}

						if (tegcVendeur != null) {
							nomProduit = tegcVendeur.getNomProduit();
							subvention = tegcVendeur.getSubvention();
						}
						EuBon bc = transfertUtility.tansfertBC(commande.getCodeMembreAcheteur(), typeBC, calculBonInfo,
								prk, Math.floor(montantBonConsommation));

						if (bc != null) {
							String reponse = "";
							// rechercher si le vendeur est beneficiaire d'une subvention
							if (subvention == 1) {
								// payement au gcsc
								reponse = reglementAchat.saveReglementParBonAuGcs(tegcVendeur.getCodeTegc(),
										compteVendeur, bc.getBonNumero(), commande.getTypeRecurrent(), "PS",
										codeTypeCredit, nomProduit, idUtilisateur, articleVendus);
							} else {
								// payement au tegc
								reponse = reglementAchat.saveReglementSimpleParBon("", 0, tegcVendeur.getCodeTegc(),
										compteVendeur, bc.getBonNumero(), commande.getTypeRecurrent(), "PS",
										codeTypeCredit, nomProduit, idUtilisateur, articleVendus);
							}
							reglerPrestataireService(commande.getCodeMembreAcheteur(), commande.getCodeMembreLivreur(),
									codeTegcLivreur, "Livraison de commande N°" + commande.getCodeCommande(), bc,
									commande.getMontantLivraison());
							if (StringUtils.isNotBlank(reponse) && reponse.startsWith("BL")) {
								return true;
							}
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new CompteNonTrouveException(
						"Pas de détails de la commande " + commande.getCodeCommande() + " trouvés");
			}
		}
		return false;
	}

	@Transactional
	public boolean reglerPrestataireService(String codeMembreAcheteur, String codeMembrePrestataire, String codeTegc,
			String libellePrestation, EuBon bc, double montant) {
		if (StringUtils.isNotBlank(codeMembreAcheteur) && StringUtils.isNotBlank(codeMembrePrestataire)
				&& StringUtils.isNotBlank(codeTegc) && Objects.nonNull(bc)) {
			String codeCat = StringUtils.EMPTY;
			if (codeMembreAcheteur.endsWith("P")) {
				codeCat = "TPAGCRPG";
			} else {
				codeCat = "TPAGCI";
			}
			EuTegc tegc = tegcService.findByCodeTegc(codeMembrePrestataire);
			if (Objects.nonNull(tegc)) {
				try {
					EuBon bl = payement.createBonLivraison(codeMembrePrestataire, codeMembreAcheteur, "PS", montant);
					payement.makeTransaction(codeMembrePrestataire, codeMembreAcheteur, codeCat, tegc, bc, bl, "PS",
							montant);
					return true;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return false;
	}
}
