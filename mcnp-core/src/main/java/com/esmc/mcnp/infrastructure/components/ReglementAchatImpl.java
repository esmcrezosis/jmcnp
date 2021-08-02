package com.esmc.mcnp.infrastructure.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.acteurs.EuActeurRepository;
import com.esmc.mcnp.dao.repository.bc.EuBonRepository;
import com.esmc.mcnp.dao.repository.bc.EuCnpEntreeRepository;
import com.esmc.mcnp.dao.repository.bc.EuCnpRepository;
import com.esmc.mcnp.dao.repository.bc.EuCreditConsommerRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreRepository;
import com.esmc.mcnp.dao.repository.common.EuOperationRepository;
import com.esmc.mcnp.dao.repository.common.EuParametreRepository;
import com.esmc.mcnp.dao.repository.common.EuSmsRepository;
import com.esmc.mcnp.dao.repository.obps.EuAchatInterimRepository;
import com.esmc.mcnp.dao.repository.obps.EuArticleStockesRepository;
import com.esmc.mcnp.dao.repository.obps.EuArticlesVenduRepository;
import com.esmc.mcnp.dao.repository.obps.EuDetailGcscRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcscRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuNnRepository;
import com.esmc.mcnp.dao.repository.oksu.EuRevendeurRepository;
import com.esmc.mcnp.dao.repository.others.EuDetailDomiciliationRepository;
import com.esmc.mcnp.dao.repository.others.EuDomiciliationRepository;
import com.esmc.mcnp.dao.repository.others.EuTegcRepository;
import com.esmc.mcnp.dao.repository.security.EuUtilisateurRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuSmcRepository;
import com.esmc.mcnp.domain.dto.obps.ArticleVendu;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.ba.EuNn;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.bc.EuCnp;
import com.esmc.mcnp.domain.entity.bc.EuCnpEntree;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomicilie;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomiciliePK;
import com.esmc.mcnp.domain.entity.bc.EuDomiciliation;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditTs;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockes;
import com.esmc.mcnp.domain.entity.obps.EuArticlesVendu;
import com.esmc.mcnp.domain.entity.obps.EuCreditConsommer;
import com.esmc.mcnp.domain.entity.obps.EuDetailGcsc;
import com.esmc.mcnp.domain.entity.obps.EuGcp;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuTypeNn;
import com.esmc.mcnp.domain.entity.oksu.EuRevendeur;
import com.esmc.mcnp.domain.entity.others.EuAchatInterim;
import com.esmc.mcnp.domain.entity.others.EuOperation;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuGcsc;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.domain.entity.sms.EuSms;
import com.google.common.collect.Lists;

/**
 *
 * @author Administrateur
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReglementAchatImpl implements ReglementAchat {

	/*
	 * @Autowired private EuParametresService paramService;
	 */
	@Autowired
	private EuActeurRepository acteurRepo;
	@Autowired
	private EuBonRepository bonRepo;
	@Autowired
	private EuCreditConsommerRepository creditConsoRepo;
	@Autowired
	private EuParametreRepository parametreRepo;
	@Autowired
	private EuCompteRepository compteRepo;
	@Autowired
	private EuOperationRepository operationRepo;
	@Autowired
	private EuUtilisateurRepository utilisateurRepo;
	@Autowired
	private EuTegcRepository tegcRepo;
	@Autowired
	private EuCompteCreditTsRepository compteCreditTsRepo;
	@Autowired
	private EuSmcRepository smcRepo;
	@Autowired
	private EuSmsRepository smsRepo;
	@Autowired
	private EuGcpRepository gcpRepo;
	@Autowired
	private EuMembreRepository membreRepo;
	@Autowired
	private EuMembreMoraleRepository membreMoraleRepo;
	@Autowired
	private EuArticlesVenduRepository articleVenduRepo;
	@Autowired
	private EuArticleStockesRepository articleStockesRepo;
	@Autowired
	private EuCnpRepository cnpRepo;
	@Autowired
	private EuCnpEntreeRepository cnpEntreeRepo;
	/*
	 * @Autowired private EuAppelOffreRepository appelOffreDao;
	 * 
	 * @Autowired private EuFraisRepository fraisRepo;
	 */
	@Autowired
	private EuGcscRepository gcscRepo;
	@Autowired
	private EuNnRepository nnRepo;
	@Autowired
	private EuDetailGcscRepository detailGcscRepo;
	@Autowired
	private EuRevendeurRepository revendeurRepo;
	@Autowired
	private EuDomiciliationRepository domicilieRepo;
	@Autowired
	private EuDetailDomiciliationRepository detailDomicilieRepo;
	@Autowired
	private EuAchatInterimRepository achatInterimRepo;

	/*
	 * public Double RetrouverFraisSms(){ Double fraisSms =
	 * parametreRepo.findByCodeAndLib("FSMS", "Frais de SMS"); return fraisSms; }
	 */

	public Boolean verifierQuantiteEnStock(String reference, Integer qteAchete) {
		if (Objects.nonNull(reference) && Objects.nonNull(qteAchete)) {
			Integer qteEnStock = articleStockesRepo.findByReference(reference).getQteSolde();
			if (qteEnStock >= qteAchete) {
				return true;
			}
			return false;
		}
		return false;
	}

	// retrouver le codemembre de la surveillance source
	private EuCompte retrouverCompteNnSurveillanceSource() {
		EuCompte compteNnMargeSurveillance = null;
		String codeMembreSurveillance = "";
		EuActeur acteurSurveillance = acteurRepo.findSurveillanceSource();
		if (acteurSurveillance != null) {
			codeMembreSurveillance = acteurSurveillance.getCodeMembre();
			compteNnMargeSurveillance = compteRepo.findCompteById("NN-TMARGE-" + codeMembreSurveillance);
		}
		return compteNnMargeSurveillance;
	}

	/*
	 * // retrouver le montant du GCP en nr private double calculerMontantGcp(double
	 * montant) { Double pck = parametreRepo.findByCodeAndLib("pck", "nr"); Double
	 * prk = parametreRepo.findByCodeAndLib("prk", "nr"); Double txEsc =
	 * parametreRepo.findByCodeAndLib("taux", "escompte"); return ((montant * prk) /
	 * (pck * (1 + txEsc / 100))); }
	 */

	/*
	 * // retrouver le codemembre de l'éxecutante source private String
	 * retrouverCodeMembreExecutanteSource() { String codeMembreExecutante = "";
	 * EuActeur acteurExecutante = acteurRepo.findExecutanteSource(); if
	 * (acteurExecutante != null) { codeMembreExecutante =
	 * acteurExecutante.getCodeMembre(); } return codeMembreExecutante; }
	 */
	// reglement par bon de consommation d achat par codebarre et d'achat par
	// factures
	@Override
	public String saveReglementSimpleParBon(String codeAchat, Integer domiciliation, String codeTegc,
			String compteVendeur, String CodeBon, String typeR, String typeProduit, String codeTypeCredit,
			String nomProduit, Long idUtilisateur, List<ArticleVendu> ListArticlesVendu) {
		EuMembre membreAcheteur = null;
		EuMembreMorale membreMoraleAcheteur = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		EuBon euBonLivraison = null;
		String reponse = "KO";
		String codeProduit = "";
		// EuCompte comptePrincipalSurveillance;
		EuMembreMorale membreMoraleSurveillance;
		EuCompte compteNnMargeSurveillance;
		EuCompte compteTransactionAcheteur;
		String codeCompteTransactionAcheteur;
		// EuCompte comptePrincipalVendeur;
		// EuMembreMorale membreMoraleVendeur;
		EuTegc euTegc = null;
		EuUtilisateur euUtilisateur;
		List<EuCompteCreditTs> listCompteCreditTs;
		EuBon bon;
		EuCnp euCnp;
		double reste;
		String typeOperation = "";
		String typesmc = "";
		EuSmc euSmc;
		EuGcp euGcp = null;
		/*
		 * String messageacheteur; String messagevendeur;
		 */
		// double soldeVendeur;
		double soldeAcheteur;
		// double soldeSurv;
		double montantFraisExploitation;
		double montantBon;
		double tauxprelever = 0.0;
		double montantGCP = 0.0;
		double montantPrelevementBL;
		double tauxFournisseurprelever = 0.0;
		double montantPrixEli = 0.0;
		/*
		 * double tauxTaxeAPrelever = 0.0; double montantTaxe = 0.0; String typeTaxe =
		 * "";
		 */
		List<String> ListTegcVendeurs = Lists.newArrayList();
		List<ArticleVendu> ListArticlesVendusParTegc = Lists.newArrayList();
		Boolean qteAvailable = false;

		for (ArticleVendu articleVendu : ListArticlesVendu) {
			if (verifierQuantiteEnStock(articleVendu.getReference(), articleVendu.getQuantite())) {
				qteAvailable = true;
			} else {
				qteAvailable = false;
				break;

			}

		}

		if (qteAvailable) {
			// retrouver euUtilisateur
			euUtilisateur = utilisateurRepo.findOne(idUtilisateur);
			// retrouver compte surveillance
			compteNnMargeSurveillance = retrouverCompteNnSurveillanceSource();
			membreMoraleSurveillance = compteNnMargeSurveillance.getEuMembreMorale();

			// comptePrincipalSurveillance =
			// compteRepo.findCompteSurveillanceByCodeMembreMorale("TPAGCP",
			// membreMoraleSurveillance.getCodeMembreMorale());

			/*
			 * // retrouver le compte et la personne morale du vendeur
			 * comptePrincipalVendeur = compteRepo.findOne(compteVendeur);
			 * membreMoraleVendeur = comptePrincipalVendeur.getEuMembreMorale();
			 */
			// retrouver le bon de consommation
			bon = bonRepo.findBon(CodeBon);
			montantBon = bon.getBonMontant();

			// recherche du taux de prelevement des frais d'exploitation
			tauxprelever = parametreRepo.getMontantTauxOperation();
			montantFraisExploitation = Math.floor(montantBon * tauxprelever / 100);

			String codeTegcUtilisateur = euUtilisateur.getCodeTegc();
			EuTegc euTegcUtilisateur = tegcRepo.findByCodeTegc(codeTegcUtilisateur);
			if (euTegcUtilisateur.getTypeTegc().equalsIgnoreCase("INTERIM")) {
				// recherche du taux de prelevement des frais d'exploitation
				tauxFournisseurprelever = parametreRepo.findByCodeAndLib("coefficientventecentrale",
						"taux prelevé aux fournisseurs en centrale de vente");

			} else if (euTegcUtilisateur.getTypeTegc().equalsIgnoreCase("DISTRIBUTEUR")) {
				// recherche du taux de prelevement des frais d'exploitation
				tauxFournisseurprelever = parametreRepo.findByCodeAndLib("coefficientventepoursoi",
						"taux prelevé aux fournisseurs pour soi");

			}

			// creation des frais d'exploitation
			if (montantFraisExploitation > 0) {
				// creation et utilisation de la source NN
				/*
				 * Long id = nnRepo.getLastInsertId(); if (id == null) { id = 0L; }
				 */
				EuTypeNn typeNn = new EuTypeNn();
				typeNn.setCodeTypeNn("NNMARGE");
				EuNn eunn = new EuNn();
				eunn.setDateEmission(new Date());
				// eunn.setIdNn(id + 1);
				eunn.setEuMembreMorale(membreMoraleSurveillance);
				eunn.setEuTypeNn(typeNn);
				eunn.setMontantEmis(montantFraisExploitation);
				eunn.setMontantRemb(montantFraisExploitation);
				eunn.setSoldeNn(0.0);
				eunn.setTypeEmission("Auto");
				eunn.setIdUtilisateur(euUtilisateur.getIdUtilisateur());
				nnRepo.save(eunn);

				// mise à jour du compte surveillance
				double soldeSurv = compteNnMargeSurveillance.getSolde() + montantFraisExploitation;
				compteNnMargeSurveillance.setSolde(soldeSurv);
				compteRepo.save(compteNnMargeSurveillance);

				// euoperation
				/*
				 * Long idOperation3 = operationRepo.getLastOperationInsertedId();
				 * idOperation3++;
				 */
				EuOperation euoperation3 = new EuOperation();
				// euoperation3.setIdOperation(idOperation3);
				euoperation3.setDateOp(new Date());
				euoperation3.setHeureOp(new Date());
				euoperation3.setMontantOp(montantFraisExploitation);
				euoperation3.setEuMembreMorale(membreMoraleSurveillance);
				euoperation3.setEuUtilisateur(euUtilisateur);
				euoperation3.setCodeProduit("NN");
				euoperation3.setLibOp("CREATION DE FRAIS EXPLOITATION");
				euoperation3.setEuCategorieCompte(compteNnMargeSurveillance.getEuCategorieCompte());
				euoperation3.setTypeOp("BL");
				operationRepo.save(euoperation3);

			}

			// retrouver le code membre acheteur et le compte acheteur
			String codemembreAcheteur = bon.getBonCodeMembreEmetteur();
			if (codemembreAcheteur.endsWith("P")) {
				membreAcheteur = membreRepo.findOne(codemembreAcheteur);
				codeCompteTransactionAcheteur = "NB-TSRPG-" + membreAcheteur.getCodeMembre();
				compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
			} else {
				membreMoraleAcheteur = membreMoraleRepo.findOne(codemembreAcheteur);
				codeCompteTransactionAcheteur = "NB-TSGCI-" + membreMoraleAcheteur.getCodeMembreMorale();
				compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
			}
			if (codeCompteTransactionAcheteur.endsWith("P")) {
				switch (typeR) {
				case "r":
					codeProduit = "RPGr";
					typeOperation = "BCr";
					break;
				case "nr":
					codeProduit = "RPGnr";
					typeOperation = "BCnr";
					break;
				}
			} else {
				if (codeCompteTransactionAcheteur.startsWith("NB-TSI")) {
					typeR = "pre";
					codeProduit = "InrPRE";
					typeOperation = "InrPRE";
				} else {
					switch (typeR) {
					case "r":
						codeProduit = "Ir";
						typeOperation = "BCr";
						break;
					case "nr":
						codeProduit = "Inr";
						typeOperation = "BCnr";
						break;
					case "rea":
						codeProduit = "Inr";
						typeOperation = "BC";
						break;
					}
				}
			}

			listCompteCreditTs = new ArrayList<>();

			listCompteCreditTs = compteCreditTsRepo.findByEuBon_BonNumero(bon.getBonNumero());
			if (listCompteCreditTs.isEmpty()) {
				reponse = "KO1";
			} else {

				// recherche tegc de la surveillance source
				/*
				 * EuTegc tegcSurveillance
				 * =tegcRepo.findTegcByCodeMembreAndNomTegc("0010010010010000002M",
				 * "SURVEILLANCE SOURCE");
				 * 
				 * Double fraisSms = RetrouverFraisSms(); //ajout des frais de sms a la liste
				 * d'articles vendus ArticleVendu articleSmsVendu = new ArticleVendu();
				 * articleSmsVendu.setCodeMembreAcheteur(codemembreAcheteur);
				 * if(tegcSurveillance!=null){
				 * articleSmsVendu.setCodeTegc(tegcSurveillance.getCodeTegc()); }
				 * articleSmsVendu.setCodeBarre("FSMS");
				 * articleSmsVendu.setDesignation("Frais de SMS");
				 * articleSmsVendu.setReference("FSMS"); articleSmsVendu.setQuantite(1);
				 * articleSmsVendu.setPrix(fraisSms); ListArticlesVendu.add(articleSmsVendu);
				 */

				// mise a jour du Bon de consommation
				// bon.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				bon.setBonDateExpression(new Date());
				bon.setBonExprimer(1);
				bonRepo.save(bon);

				// mise à jour du comptes ACHETEUR
				soldeAcheteur = compteTransactionAcheteur.getSolde() - montantBon;
				compteTransactionAcheteur.setSolde(soldeAcheteur);
				compteRepo.save(compteTransactionAcheteur);

				if (!ListArticlesVendu.isEmpty()) {
					ListTegcVendeurs = ListArticlesVendu.stream().map(articleVendu -> articleVendu.getCodeTegc())
							.distinct().collect(Collectors.toList());
				}

				for (String codeTegcVendeur : ListTegcVendeurs) {

					ListArticlesVendusParTegc = ListArticlesVendu.stream()
							.filter(articleVendu -> articleVendu.getCodeTegc().equals(codeTegcVendeur))
							.collect(Collectors.toList());

					if (!ListArticlesVendusParTegc.isEmpty()) {

						// retrouver le montant correspondant à ces articles
						double montantAchatParTe = ListArticlesVendusParTegc.stream()
								.map(articleVendu -> articleVendu.getPrix() * articleVendu.getQuantite())
								.reduce(Double::sum).get();
						montantPrixEli = 0.0;
						// calcul du prix eli
						for (ArticleVendu articleVendu : ListArticlesVendusParTegc) {
							EuArticleStockes articleStocke = articleStockesRepo
									.findByReference(articleVendu.getReference());
							if (articleStocke != null) {
								montantPrixEli += articleStocke.getPrixEli() * articleVendu.getQuantite();
							}
						}
						reste = montantAchatParTe;

						// retrouver le TEGC
						euTegc = tegcRepo.findOne(codeTegcVendeur);
						if (euTegc != null) {
							// retrouver le vendeur
							EuMembreMorale membreMoraleVendeur = euTegc.getEuMembreMorale();
							if (membreMoraleVendeur != null) {
								compteVendeur = "NB-TPAGCP-" + membreMoraleVendeur.getCodeMembreMorale();
							}
							EuCompte comptePrincipalVendeur = compteRepo.findOne(compteVendeur);

							// Enregistrement des operations
							EuOperation euoperation = new EuOperation();
							// euoperation.setIdOperation(idOperation);
							euoperation.setDateOp(new Date());
							euoperation.setHeureOp(new Date());
							euoperation.setMontantOp(montantAchatParTe);
							if (codeCompteTransactionAcheteur.endsWith("P")) {
								euoperation.setEuMembre(membreAcheteur);
							} else {
								euoperation.setEuMembreMorale(membreMoraleAcheteur);
							}
							euoperation.setEuUtilisateur(euUtilisateur);
							euoperation.setCodeProduit(codeProduit);
							if (typeR.equals("rea")) {
								euoperation.setLibOp("REAPPRO " + nomProduit);
							} else {
								euoperation.setLibOp(nomProduit);
							}
							euoperation.setEuCategorieCompte(compteTransactionAcheteur.getEuCategorieCompte());
							euoperation.setTypeOp(typeOperation);
							operationRepo.save(euoperation);

							// creer le bon de livraison (autoincrement)

							Long IdBon = bonRepo.findByMaxIdInserted();

							euBonLivraison = new EuBon();
							euBonLivraison.setBonCodeBarre("");
							euBonLivraison.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
							euBonLivraison.setBonCodeMembreEmetteur(codemembreAcheteur);
							euBonLivraison.setBonDate(new Date());
							euBonLivraison.setBonMontant(montantAchatParTe);
							euBonLivraison.setBonNumero("BL" + StringUtils.leftPad(String.valueOf(IdBon + 1), 8, '0'));
							euBonLivraison.setBonType("BL");
							euBonLivraison.setBonExprimer(1);
							bonRepo.saveAndFlush(euBonLivraison);

							// mise a jour Achat interim

							if (codemembreAcheteur.equals("0000000000000000003M")) {
								// mise a jour de achat interim

								EuAchatInterim euAchatInterim = achatInterimRepo.findByCodeAchat(codeAchat);
								if (Objects.nonNull(euAchatInterim)) {
									euAchatInterim.setStatus(1);
									euAchatInterim.setEuBon(euBonLivraison);
									achatInterimRepo.save(euAchatInterim);
								}
							}

							// mettre a jour les articles vendus

							for (ArticleVendu articleVendu : ListArticlesVendusParTegc) {
								EuArticlesVendu article = new EuArticlesVendu();
								article.setCodeBarre(articleVendu.getCodeBarre());
								if (membreMoraleAcheteur != null) {
									article.setCodeMembreAcheteur(membreMoraleAcheteur.getCodeMembreMorale());
								} else {
									article.setCodeMembreAcheteur(membreAcheteur.getCodeMembre());
								}
								article.setDateVente(new Date());
								if (!StringUtils.isWhitespace(articleVendu.getDesignation())) {
									article.setDesignation(articleVendu.getDesignation());
								}
								article.setQuantite(articleVendu.getQuantite());
								article.setPrixUnitaire(articleVendu.getPrix());
								article.setCodeMembreVendeur(membreMoraleVendeur.getCodeMembreMorale());
								article.setDateVente(new Date());
								if (!StringUtils.isWhitespace(articleVendu.getReference())) {
									article.setReference(articleVendu.getReference());
								}
								article.setEuBon(euBonLivraison);
								articleVenduRepo.save(article);

							}

							for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {

								if (domiciliation == 1) {
									// Enregistrement de domiciliation et detail domiliciation
									int duree = compteCreditTs.getEuCompteCredit().getDuree().intValue();
									String codeDomicilier = typeR + new Date().toString();

									EuDomiciliation euDomiciliation = new EuDomiciliation();
									euDomiciliation.setAccorder("O");
									euDomiciliation.setCatRessource(typeR);
									euDomiciliation.setCodeDomicilier(codeDomicilier);
									euDomiciliation.setDateDomiciliation(new Date());
									Date date_fin = ServerUtil.ajouterMois(new Date(), duree);
									if (date_fin != null) {
										euDomiciliation.setDateEchue(date_fin);
									}
									euDomiciliation.setDomicilier("O");
									euDomiciliation.setDureeRenouvellement(30);
									euDomiciliation.setEuMembreMorale(membreMoraleVendeur);
									euDomiciliation.setTypeDomiciliation(typeR);
									euDomiciliation.setEuUtilisateur(euUtilisateur);
									euDomiciliation
											.setMontantSubvent(compteCreditTs.getEuCompteCredit().getMontantCredit());
									euDomiciliation
											.setMontantDomicilier(compteCreditTs.getEuCompteCredit().getMontantPlace());

									domicilieRepo.saveAndFlush(euDomiciliation);

									EuDetailDomiciliePK pk = new EuDetailDomiciliePK();
									pk.setCodeDomicilier(euDomiciliation.getCodeDomicilier());
									pk.setIdCredit(compteCreditTs.getEuCompteCredit().getIdCredit());

									EuDetailDomicilie detailDomiciliation = new EuDetailDomicilie();
									detailDomiciliation.setId(pk);
									detailDomiciliation
											.setMontantCredit(compteCreditTs.getEuCompteCredit().getMontantCredit());
									if (codemembreAcheteur.endsWith("P")) {
										detailDomiciliation.setEuMembre(membreAcheteur);
									} else {
										detailDomiciliation.setEuMembreMorale(membreMoraleAcheteur);
									}
									detailDomiciliation.setUtiliser(1);
									detailDomiciliation.setDureeRenouvellement(30);
									detailDomiciliation.setResteDuree(duree - 1);
									detailDomicilieRepo.save(detailDomiciliation);
								}

								if (compteCreditTs.getMontant() > (reste)) {
									// enregistrememnt euSMC
									typesmc = "";

									if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit()
											.equals("RPGr")) {
										typesmc = "CNCSr";
									} else {
										typesmc = "CNCSnr";
									}

									/*
									 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
									 * 1L; } else {
									 * 
									 * idsmc++; }
									 */
									euSmc = new EuSmc();
									// euSmc.setIdSmc(idsmc);
									// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
									euSmc.setDateSmc(new Date());
									euSmc.setMontant(reste);
									euSmc.setMontantSolde(reste);
									euSmc.setEntree(Double.valueOf("0"));
									euSmc.setSolde(Double.valueOf("0"));
									euSmc.setSortie(Double.valueOf("0"));
									euSmc.setOrigineSmc(0);
									euSmc.setSourceCredit(compteCreditTs.getCompteSource());
									euSmc.setTypeSmc(typesmc);
									euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									smcRepo.save(euSmc);

									// enregistrememnt euGCP vendeur
									/*
									 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
									 * 1L; } else { idgcp++; }
									 */
									montantPrelevementBL = Math
											.floor(montantAchatParTe * tauxFournisseurprelever / 100);
									montantGCP = montantAchatParTe - montantPrelevementBL;

									if (euTegc.getSubvention() == 2) {

										// mise à jour du compte vendeur
										comptePrincipalVendeur
												.setSolde(comptePrincipalVendeur.getSolde() + montantPrixEli);
										compteRepo.saveAndFlush(comptePrincipalVendeur);

										// mise a jour du te
										euTegc.setMontant(euTegc.getMontant() + montantPrixEli);
										euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantPrixEli);
										tegcRepo.saveAndFlush(euTegc);

										// creation du gcp
										euGcp = new EuGcp();
										// euGcp.setIdGcp(idgcp);
										euGcp.setEuTegc(euTegc);
										euGcp.setCodeMembre(
												comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
										euGcp.setDateConso(new Date());
										euGcp.setReste(montantPrixEli);
										euGcp.setMontGcp(montantPrixEli);
										euGcp.setMontPreleve(0);
										euGcp.setTypeGcp("DIST");
										euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
										euGcp.setSource(compteCreditTs.getEuCompteCredit().getSource());
										euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
										euGcp.setEuBon(euBonLivraison);
										gcpRepo.saveAndFlush(euGcp);

										if ((montantGCP - montantPrixEli) > 0) {
											// eu-gcsc et detail gcsc
											// enregistrement du euGcsc
											EuGcsc euGcsc = new EuGcsc();
											euGcsc.setCodeDomicilier(null);
											euGcsc.setCodeMembre(
													comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
											euGcsc.setCodeTegc(codeTegcVendeur);
											euGcsc.setCredit(montantGCP - montantPrixEli);
											euGcsc.setDebit(0);
											euGcsc.setSolde(montantGCP - montantPrixEli);
											euGcsc.setEuSmcipnpwi(null);
											euGcsc.setDateGcsc(new Date());
											gcscRepo.saveAndFlush(euGcsc);

											EuDetailGcsc euDetailGcsc = new EuDetailGcsc();
											// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
											euDetailGcsc.setCodeMembre(
													comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
											euDetailGcsc.setMontGcsc(montantGCP - montantPrixEli);
											euDetailGcsc.setDateConso(new Date());
											euDetailGcsc.setEuGcsc(euGcsc);
											euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
											euDetailGcsc.setSource(compteCreditTs.getCompteSource());
											euDetailGcsc.setEuBon(euBonLivraison);
											detailGcscRepo.saveAndFlush(euDetailGcsc);
										}

									} else {
										// mise à jour du compte vendeur
										comptePrincipalVendeur.setSolde(comptePrincipalVendeur.getSolde() + montantGCP);
										compteRepo.saveAndFlush(comptePrincipalVendeur);

										// mise a jour du te
										euTegc.setMontant(euTegc.getMontant() + montantGCP);
										euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantGCP);
										tegcRepo.saveAndFlush(euTegc);

										// creation du GCP
										euGcp = new EuGcp();
										// euGcp.setIdGcp(idgcp);
										euGcp.setEuTegc(euTegc);
										euGcp.setCodeMembre(membreMoraleVendeur.getCodeMembreMorale());
										euGcp.setDateConso(new Date());
										euGcp.setReste(montantGCP);
										euGcp.setMontGcp(montantGCP);
										euGcp.setMontPreleve(0);
										euGcp.setTypeGcp("DIST");
										euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
										euGcp.setSource(compteCreditTs.getCompteSource());
										euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
										euGcp.setEuBon(euBonLivraison);
										gcpRepo.saveAndFlush(euGcp);
									}

									/*
									 * gcp tegc compte
									 */

									// enregistrement des creditsconsommer avec les
									// credits
									/*
									 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
									 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
									 * idcreditconsommer++; }
									 */

									EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
									// eucreditConsommer.setIdConsommation(idcreditconsommer);
									eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
									if (membreMoraleAcheteur != null) {
										eucreditConsommer.setCodeMembre(null);
										eucreditConsommer
												.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
									} else {
										eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
										eucreditConsommer.setCodeMembreMorale(null);
									}
									eucreditConsommer.setDateConsommation(new Date());
									eucreditConsommer.setHeureConsommation(new Date());
									eucreditConsommer.setMontConsommation(reste);
									eucreditConsommer.setEuOperation(euoperation);
									eucreditConsommer.setCodeMembreDist(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
									eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
									eucreditConsommer.setTypeProduit(typeProduit);
									eucreditConsommer.setEuBon(bon);
									creditConsoRepo.save(eucreditConsommer);

									// annulation du CNP
									euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
											compteCreditTs.getCompteSource());
									if (euCnp != null) {
										euCnp.setMontDebit(euCnp.getMontDebit() - reste);
										euCnp.setMontCredit(euCnp.getMontCredit() + reste);
										euCnp.setSoldeCnp(euCnp.getSoldeCnp() + reste);
									}

									// domiciliation
									if (domiciliation == 1) {

									}

									// Mise à jour des eucomptecredit

									compteCreditTs.setMontant(compteCreditTs.getMontant() - reste);

									break;
								} else if (compteCreditTs.getMontant() <= reste) {

									// enregistrememnt euSMC
									typesmc = "";

									if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit()
											.equals("RPGr")) {
										typesmc = "CNCSr";
									} else {
										typesmc = "CNCSnr";
									}

									/*
									 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
									 * 1L; } else {
									 * 
									 * idsmc++; }
									 */
									euSmc = new EuSmc();
									// euSmc.setIdSmc(idsmc);

									// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
									euSmc.setDateSmc(new Date());
									euSmc.setMontant(compteCreditTs.getMontant());
									euSmc.setMontantSolde(compteCreditTs.getMontant());
									euSmc.setEntree(Double.valueOf("0"));
									euSmc.setSolde(Double.valueOf("0"));
									euSmc.setSortie(Double.valueOf("0"));
									euSmc.setOrigineSmc(0);
									euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
									euSmc.setTypeSmc(typesmc);
									euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									smcRepo.save(euSmc);

									/*
									 * double montantTaxeCredit = 0.0; montantTaxeCredit =
									 * Math.floor(compteCreditTs.getMontant() * tauxTaxeAPrelever)/100;
									 */// enregistrememnt euGCP
										// double montGCPnr2 = calculerMontantGcp(compteCreditTs.getMontant());
									/*
									 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
									 * 1L; } else {
									 * 
									 * idgcp++; }
									 */

									montantPrelevementBL = Math
											.floor(compteCreditTs.getMontant() * tauxFournisseurprelever / 100);
									montantGCP = compteCreditTs.getMontant() - montantPrelevementBL;

									if (euTegc.getSubvention() == 2) {
										// mise à jour du compte vendeur
										comptePrincipalVendeur
												.setSolde(comptePrincipalVendeur.getSolde() + montantPrixEli);
										compteRepo.saveAndFlush(comptePrincipalVendeur);

										// mise a jour du te
										euTegc.setMontant(euTegc.getMontant() + montantPrixEli);
										euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantPrixEli);
										tegcRepo.saveAndFlush(euTegc);

										// creation du gcp
										euGcp = new EuGcp();
										// euGcp.setIdGcp(idgcp);
										euGcp.setEuTegc(euTegc);
										euGcp.setCodeMembre(
												comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
										euGcp.setDateConso(new Date());
										euGcp.setReste(montantPrixEli);
										euGcp.setMontGcp(montantPrixEli);
										euGcp.setMontPreleve(0);
										euGcp.setTypeGcp("DIST");
										euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
										euGcp.setSource(compteCreditTs.getEuCompteCredit().getSource());
										euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
										euGcp.setEuBon(euBonLivraison);
										gcpRepo.saveAndFlush(euGcp);

										if ((montantGCP - montantPrixEli) > 0) {
											// eu-gcsc et detail gcsc
											// enregistrement du euGcsc
											EuGcsc euGcsc = new EuGcsc();
											euGcsc.setCodeDomicilier(null);
											euGcsc.setCodeMembre(
													comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
											euGcsc.setCodeTegc(codeTegcVendeur);
											euGcsc.setCredit(montantGCP - montantPrixEli);
											euGcsc.setDebit(0);
											euGcsc.setSolde(montantGCP - montantPrixEli);
											euGcsc.setEuSmcipnpwi(null);
											euGcsc.setDateGcsc(new Date());
											gcscRepo.saveAndFlush(euGcsc);

											EuDetailGcsc euDetailGcsc = new EuDetailGcsc();
											// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
											euDetailGcsc.setCodeMembre(
													comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
											euDetailGcsc.setMontGcsc(montantGCP - montantPrixEli);
											euDetailGcsc.setDateConso(new Date());
											euDetailGcsc.setEuGcsc(euGcsc);
											euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
											euDetailGcsc.setSource(compteCreditTs.getCompteSource());
											euDetailGcsc.setEuBon(euBonLivraison);
											detailGcscRepo.saveAndFlush(euDetailGcsc);
										}
									} else {
										// mise à jour du compte vendeur
										comptePrincipalVendeur.setSolde(comptePrincipalVendeur.getSolde() + montantGCP);
										compteRepo.saveAndFlush(comptePrincipalVendeur);

										// mise a jour du te
										euTegc.setMontant(euTegc.getMontant() + montantGCP);
										euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantGCP);
										tegcRepo.saveAndFlush(euTegc);

										// creation du GCP
										euGcp = new EuGcp();
										// euGcp.setIdGcp(idgcp);
										euGcp.setEuTegc(euTegc);
										euGcp.setCodeMembre(
												comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
										euGcp.setDateConso(new Date());
										euGcp.setReste(montantGCP);
										euGcp.setMontGcp(montantGCP);
										euGcp.setMontPreleve(0);
										euGcp.setTypeGcp("DIST");
										euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
										euGcp.setSource(compteCreditTs.getEuCompteCredit().getSource());
										euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
										euGcp.setEuBon(euBonLivraison);
										gcpRepo.saveAndFlush(euGcp);
									}

									// enregistrement des creditsconsommer avec les
									// credits
									/*
									 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
									 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
									 * 
									 * idcreditconsommer++; }
									 */
									EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
									// eucreditConsommer.setIdConsommation(idcreditconsommer);
									eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
									if (membreMoraleAcheteur != null) {
										eucreditConsommer.setCodeMembre(null);
										eucreditConsommer
												.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
									} else {
										eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
										eucreditConsommer.setCodeMembreMorale(null);
									}
									eucreditConsommer.setDateConsommation(new Date());
									eucreditConsommer.setHeureConsommation(new Date());
									eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
									eucreditConsommer.setEuOperation(euoperation);
									eucreditConsommer.setCodeMembreDist(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
									eucreditConsommer.setEuBon(bon);
									eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
									eucreditConsommer.setTypeProduit(typeProduit);
									creditConsoRepo.save(eucreditConsommer);

									reste = (reste) - compteCreditTs.getMontant();

									// annulation du CNP
									euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
											compteCreditTs.getCompteSource());
									if (euCnp != null) {
										// euCnp.setMontDebit(euCnp.getMontDebit() - compteCreditTs.getMontant());
										euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
										euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
										cnpRepo.save(euCnp);
									}

									// ajout dans Eu_cnp_entree
									EuCnpEntree euCnpEntree = new EuCnpEntree();
									/*
									 * Long idCnpEntree = cnpEntreeRepo.getLastInsertedId(); if (idCnpEntree ==
									 * null) { idCnpEntree = 1L; } else { idCnpEntree += 1; }
									 */
									// euCnpEntree.setIdCnpEntree(idCnpEntree);
									euCnpEntree.setDateEntree(new Date());
									euCnpEntree.setEuCnp(euCnp);
									euCnpEntree.setMontCnpEntree(compteCreditTs.getMontant());
									euCnpEntree.setTypeCnpEntree("GCP");
									cnpEntreeRepo.save(euCnpEntree);

									// mise a jour comptecreditTs
									compteCreditTs.setMontant(0.0);

								}
							}

							// creation de gcp dans EuOp;
							/*
							 * Long idOperation2 = operationRepo.getLastOperationInsertedId();
							 * idOperation2++;
							 */
							EuOperation euoperation2 = new EuOperation();
							// euoperation2.setIdOperation(idOperation2);
							euoperation2.setDateOp(new Date());
							euoperation2.setHeureOp(new Date());
							euoperation2.setMontantOp(montantAchatParTe);
							euoperation2.setEuMembreMorale(membreMoraleVendeur);
							euoperation2.setEuUtilisateur(euUtilisateur);
							euoperation2.setCodeProduit(codeProduit);
							euoperation2.setLibOp("CREATION DE GCP VENDEUR");
							euoperation2.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
							euoperation2.setTypeOp("BL");
							operationRepo.save(euoperation2);

							// création de la source GCP AU CNP
							/*
							 * Long idCnp = cnpRepo.getLastCnpInsertedId(); idCnp += 1;
							 */
							EuCnp cnp = new EuCnp();
							cnp.setDateCnp(new Date());
							cnp.setEuCapa(null);
							cnp.setTypeCnp("Inr");
							cnp.setSourceCredit(
									membreMoraleVendeur.getCodeMembreMorale() + formatter.format(new Date()));
							cnp.setOrigineCnp(codeProduit);
							cnp.setMontDebit(montantAchatParTe);
							cnp.setMontCredit(0);
							cnp.setSoldeCnp(montantAchatParTe);
							cnp.setTransfertGcp(0);
							cnp.setEuGcp(euGcp);
							cnp.setEuCompteCredit(null);
							// cnp.setIdCnp(idCnp);

							cnpRepo.save(cnp);

							/*
							 * // Ecriture de deux sms non envoyes String messageacheteur = montantBon +
							 * " ONT ETE RETIRES DU COMPTE :" + compteTransactionAcheteur.getCodeCompte();
							 * String messagevendeur = montantBon + " ONT ETE AJOUTES AU COMPTE :" +
							 * comptePrincipalVendeur.getCodeCompte();
							 * 
							 * // 2 enregistrements dans la table eu_sms SimpleDateFormat formater = new
							 * SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());
							 * 
							 * // acheteur Long idsms = smsRepo.getLastSmsInserted(); if (idsms == null) {
							 * idsms = 1L; } else {
							 * 
							 * idsms++; } EuSms eusms = new EuSms(); // eusms.setNeng(idsms); if
							 * (membreMoraleAcheteur != null) {
							 * eusms.setRecipient(membreMoraleAcheteur.getPortableMembre()); } else {
							 * eusms.setRecipient(membreAcheteur.getPortableMembre()); }
							 * eusms.setSmsbody(messageacheteur); eusms.setDatetime(formater.format(new
							 * Date())); smsRepo.save(eusms);
							 */

						}
					}

				}

				// renvoi de la reponse
				if (codeTypeCredit.equals("CNPGRE")) {
					reponse = euBonLivraison.getBonNumero();
					return reponse;
				} else {
					reponse = "OK";
					return reponse;
				}

			}
			return reponse;
		}
		return reponse;
	}

	@Override
	public String saveReglementFactureParBon(String codeTegc, String compteVendeur, String CodeBon, String typeR,
			String typeProduit, String codeTypeCredit, String nomProduit, String referenceFacture, String periode,
			Long idUtilisateur) {
		EuMembre membreAcheteur = null;
		EuMembreMorale membreMoraleAcheteur = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		EuBon euBonLivraison = null;
		String reponse = "KO";
		String codeProduit = "";
		String codeCompteTransactionAcheteur;
		// EuCompte comptePrincipalSurveillance;
		EuMembreMorale membreMoraleSurveillance;
		EuCompte compteNnMargeSurveillance;
		EuCompte compteTransactionAcheteur;
		EuCompte comptePrincipalVendeur;
		EuMembreMorale membreMoraleVendeur;
		EuTegc euTegc = null;
		EuUtilisateur euUtilisateur;
		List<EuCompteCreditTs> listCompteCreditTs;
		EuBon bon;
		EuCnp euCnp;
		double reste;
		// Long idOperation;
		String typeOperation = "";
		String typesmc = "";
		EuSmc euSmc;
		EuGcp euGcp = null;
		String messageacheteur;
		String messagevendeur;
		double soldeVendeur;
		double soldeSurv;
		double soldeAcheteur;
		double montantFraisExploitation;
		double montantBon;
		double tauxprelever = 0.0;

		/*
		 * double tauxTaxeAPrelever = 0.0; double montantTaxe = 0.0; String typeTaxe =
		 * "";
		 */
		// recuperation de l'utilisateur
		euUtilisateur = utilisateurRepo.findOne(idUtilisateur);

		// retrouver compte surveillance
		compteNnMargeSurveillance = retrouverCompteNnSurveillanceSource();
		System.out.println("compteNnMargeSurveillance = " + compteNnMargeSurveillance.getCodeCompte());
		membreMoraleSurveillance = compteNnMargeSurveillance.getEuMembreMorale();
		// comptePrincipalSurveillance =
		// compteRepo.findCompteSurveillanceByCodeMembreMorale("TPAGCP",
		// membreMoraleSurveillance.getCodeMembreMorale());

		// retrouver le compte et la personne morale du vendeur
		comptePrincipalVendeur = compteRepo.findOne(compteVendeur);
		membreMoraleVendeur = comptePrincipalVendeur.getEuMembreMorale();

		// retrouver le bon de consommation
		bon = bonRepo.findBon(CodeBon);
		if (bon != null) {

			montantBon = bon.getBonMontant();

			// recherche du taux de prelevement des frais d'exploitation
			tauxprelever = parametreRepo.getMontantTauxOperation();
			montantFraisExploitation = Math.floor(montantBon * tauxprelever / 100);

			// retrouver le code membre acheteur et le compte acheteur
			String codemembreAcheteur = bon.getBonCodeMembreEmetteur();
			if (codemembreAcheteur.endsWith("P")) {
				membreAcheteur = membreRepo.findOne(codemembreAcheteur);
				codeCompteTransactionAcheteur = "NB-TSRPG-" + membreAcheteur.getCodeMembre();
				compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
			} else {
				membreMoraleAcheteur = membreMoraleRepo.findOne(codemembreAcheteur);
				codeCompteTransactionAcheteur = "NB-TSGCI-" + membreMoraleAcheteur.getCodeMembreMorale();
				compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
			}
			if (codeCompteTransactionAcheteur.endsWith("P")) {
				switch (typeR) {
				case "r":
					codeProduit = "RPGr";
					typeOperation = "BCr";
					break;
				case "nr":
					codeProduit = "RPGnr";
					typeOperation = "BCnr";
					break;
				}
			} else {
				if (codeCompteTransactionAcheteur.startsWith("NB-TSI")) {
					typeR = "pre";
					codeProduit = "InrPRE";
					typeOperation = "InrPRE";
				} else {
					switch (typeR) {
					case "r":
						codeProduit = "Ir";
						typeOperation = "BCr";
						break;
					case "nr":
						codeProduit = "Inr";
						typeOperation = "BCnr";
						break;
					case "rea":
						codeProduit = "Inr";
						typeOperation = "BC";
						break;

					}
				}
			}

			listCompteCreditTs = new ArrayList<>();

			listCompteCreditTs = compteCreditTsRepo.findByEuBon_BonNumero(bon.getBonNumero());
			System.out.println("listCompteCreditTs size= " + listCompteCreditTs.size());
			System.out.println("listCompteCreditTs size= " + listCompteCreditTs.size());

			if (listCompteCreditTs.isEmpty()) {
				reponse = "KO1";
			} else {

				reste = montantBon;

				// enregistrement dans eu operation
				/*
				 * idOperation = operationRepo.getLastOperationInsertedId(); if (idOperation ==
				 * null) { idOperation = 1L; } else {
				 * 
				 * idOperation++; }
				 */

				EuOperation euoperation = new EuOperation();
				// euoperation.setIdOperation(idOperation);
				euoperation.setDateOp(new Date());
				euoperation.setHeureOp(new Date());
				euoperation.setMontantOp(montantBon);
				if (codeCompteTransactionAcheteur.endsWith("P")) {
					euoperation.setEuMembre(membreAcheteur);

				} else {
					euoperation.setEuMembreMorale(membreMoraleAcheteur);
				}
				euoperation.setEuUtilisateur(euUtilisateur);
				euoperation.setCodeProduit(codeProduit);
				euoperation.setLibOp(nomProduit);
				if (euUtilisateur != null) {
					euoperation.setEuUtilisateur(euUtilisateur);
				}
				euoperation.setEuCategorieCompte(compteTransactionAcheteur.getEuCategorieCompte());
				euoperation.setTypeOp(typeOperation);
				operationRepo.save(euoperation);

				/*
				 * //calcul du montant GCP nr montGCPnr = calculerMontantGcp(montantBon);
				 * System.out.println("montGCPnr = " + montGCPnr);
				 */
				// enregistrement dans la table eu_tegc

				if (StringUtils.isNotBlank(codeTegc)) {
					euTegc = tegcRepo.findOne(codeTegc);
					if (euTegc != null) {
						/*
						 * //te avec regime Tva if((euTegc.getRegimeTva()!=
						 * null)&&(euTegc.getRegimeTva()==1)){ typeTaxe = "tva"; tauxTaxeAPrelever =
						 * parametreRepo.findByCodeAndLib("taxe", "TVA"); montantTaxe =
						 * Math.floor(montantBon*tauxTaxeAPrelever)/100;
						 * 
						 * System.out.println("tauxTaxeAPrelever = " + tauxTaxeAPrelever); } //te formel
						 * if((euTegc.getFormel()!= null)&& (euTegc.getFormel()==1)){ typeTaxe = "rsps";
						 * tauxTaxeAPrelever = parametreRepo.findByCodeAndLib("RSPS",
						 * "retenue formelle"); montantTaxe =
						 * Math.floor(montantBon*tauxTaxeAPrelever)/100; }else if((euTegc.getFormel()!=
						 * null)&& (euTegc.getFormel()==0)){ typeTaxe = "rsps"; //te informel
						 * tauxTaxeAPrelever = parametreRepo.findByCodeAndLib("RSPS",
						 * "retenue informelle"); montantTaxe =
						 * Math.floor(montantBon*tauxTaxeAPrelever)/100; }
						 */
						euTegc.setMontant(euTegc.getMontant() + montantBon);
						euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantBon);
						tegcRepo.save(euTegc);
					}
				}
				// creer le bon de livraison (autoincrement)

				Long IdBon = bonRepo.findByMaxIdInserted();

				euBonLivraison = new EuBon();
				euBonLivraison.setBonCodeBarre("");
				euBonLivraison.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				euBonLivraison.setBonCodeMembreEmetteur(codemembreAcheteur);
				euBonLivraison.setBonDate(new Date());
				euBonLivraison.setBonMontant(montantBon);
				euBonLivraison.setBonNumero("BL" + StringUtils.leftPad(String.valueOf(IdBon + 1), 8, '0'));
				euBonLivraison.setBonType("BL");
				euBonLivraison.setBonExprimer(1);
				bonRepo.saveAndFlush(euBonLivraison);

				for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {
					// prk = compteCreditTs.getEuCompteCredit().getPrk();

					if (compteCreditTs.getMontant() > (reste)) {
						// enregistrememnt euSMC
						typesmc = "";

						if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().endsWith("nr")) {
							typesmc = "CNCSnr";
						} else {
							typesmc = "CNCSr";
						}

						euSmc = new EuSmc();
						euSmc.setDateSmc(new Date());
						euSmc.setMontant(reste);
						euSmc.setMontantSolde(reste);
						euSmc.setEntree(Double.valueOf("0"));
						euSmc.setSolde(Double.valueOf("0"));
						euSmc.setSortie(Double.valueOf("0"));
						euSmc.setOrigineSmc(0);
						euSmc.setSourceCredit(compteCreditTs.getCompteSource());
						euSmc.setTypeSmc(typesmc);
						euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						smcRepo.save(euSmc);

						// enregistrememnt euGCP vendeur
						/*
						 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
						 * 1L; } else { idgcp++; }
						 */
						euGcp = new EuGcp();
						// euGcp.setIdGcp(idgcp);
						euGcp.setEuTegc(euTegc);
						euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						euGcp.setDateConso(new Date());
						euGcp.setReste(montantBon);
						euGcp.setMontGcp(montantBon);
						euGcp.setMontPreleve(0);
						euGcp.setTypeGcp("DIST");
						euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						euGcp.setSource(compteCreditTs.getCompteSource());
						euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
						euGcp.setEuBon(euBonLivraison);
						gcpRepo.saveAndFlush(euGcp);

						// enregistrement des creditsconsommer avec les
						// credits

						EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
						// eucreditConsommer.setIdConsommation(idcreditconsommer);
						eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
						if (membreMoraleAcheteur != null) {
							eucreditConsommer.setCodeMembre(null);
							eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
						} else {
							eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
							eucreditConsommer.setCodeMembreMorale(null);
						}
						eucreditConsommer.setDateConsommation(new Date());
						eucreditConsommer.setHeureConsommation(new Date());
						eucreditConsommer.setMontConsommation(reste);
						eucreditConsommer.setEuOperation(euoperation);
						eucreditConsommer
								.setCodeMembreDist(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
						eucreditConsommer.setEuBon(bon);
						eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
						eucreditConsommer.setTypeProduit(typeProduit);
						creditConsoRepo.save(eucreditConsommer);

						// annulation du CNP
						euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
								compteCreditTs.getCompteSource());
						if (euCnp != null) {
							euCnp.setMontDebit(euCnp.getMontDebit() - reste);
							euCnp.setMontCredit(euCnp.getMontCredit() + reste);
							euCnp.setSoldeCnp(euCnp.getSoldeCnp() + reste);
						}
						/*
						 * if(domiciliation==1){ //Enregistrement de domiciliation et detail
						 * domiliciation int duree =
						 * compteCreditTs.getEuCompteCredit().getDuree().intValue(); String
						 * codeDomicilier = typeR+new Date().toString();
						 * 
						 * EuDomiciliation euDomiciliation = new EuDomiciliation();
						 * euDomiciliation.setAccorder("O"); euDomiciliation.setCatRessource(typeR);
						 * euDomiciliation.setCodeDomicilier(codeDomicilier);
						 * euDomiciliation.setDateDomiciliation(new Date()); Date date_fin =
						 * ServerUtil.ajouterMois(new Date(), duree); if (date_fin != null) {
						 * euDomiciliation.setDateEchue(date_fin); } euDomiciliation.setDomicilier("O");
						 * euDomiciliation.setDureeRenouvellement(30);
						 * euDomiciliation.setEuMembreMorale(membreMoraleVendeur);
						 * euDomiciliation.setTypeDomiciliation(typeR);
						 * euDomiciliation.setEuUtilisateur(euUtilisateur);
						 * euDomiciliation.setMontantSubvent(compteCreditTs.getEuCompteCredit().
						 * getMontantCredit());
						 * euDomiciliation.setMontantDomicilier(compteCreditTs.getEuCompteCredit().
						 * getMontantPlace());
						 * 
						 * domicilieRepo.saveAndFlush(euDomiciliation);
						 * 
						 * EuDetailDomiciliePK pk = new EuDetailDomiciliePK();
						 * pk.setCodeDomicilier(euDomiciliation.getCodeDomicilier());
						 * pk.setIdCredit(compteCreditTs.getEuCompteCredit().getIdCredit());
						 * 
						 * EuDetailDomicilie detailDomiciliation = new EuDetailDomicilie();
						 * detailDomiciliation.setId(pk);
						 * detailDomiciliation.setMontantCredit(compteCreditTs.getEuCompteCredit().
						 * getMontantCredit()); if(codemembreAcheteur.endsWith("P")){
						 * detailDomiciliation.setEuMembre(membreAcheteur); }else{
						 * detailDomiciliation.setEuMembreMorale(membreMoraleAcheteur); }
						 * detailDomiciliation.setUtiliser(1);
						 * detailDomiciliation.setDureeRenouvellement(30);
						 * detailDomiciliation.setResteDuree(duree - 1);
						 * detailDomicilieRepo.save(detailDomiciliation); }
						 */

						// Mise à jour des eucomptecredit

						compteCreditTs.setMontant(compteCreditTs.getMontant() - reste);

						break;
					} else if (compteCreditTs.getMontant() <= reste) {
						// enregistrememnt euSMC
						typesmc = "";

						if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
							typesmc = "CNCSr";
						} else {
							typesmc = "CNCSnr";
						}

						/*
						 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
						 * 1L; } else {
						 * 
						 * idsmc++; }
						 */ euSmc = new EuSmc();
						// euSmc.setIdSmc(idsmc);

						// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
						euSmc.setDateSmc(new Date());
						euSmc.setMontant(compteCreditTs.getMontant());
						euSmc.setMontantSolde(compteCreditTs.getMontant());
						euSmc.setEntree(Double.valueOf("0"));
						euSmc.setSolde(Double.valueOf("0"));
						euSmc.setSortie(Double.valueOf("0"));
						euSmc.setOrigineSmc(0);
						euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
						euSmc.setTypeSmc(typesmc);
						euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						smcRepo.save(euSmc);

						// double montantTaxeCredit = 0.0;
						// montantTaxeCredit = (compteCreditTs.getMontant() * tauxTaxeAPrelever)/100;
						// double montGCPnr2 = calculerMontantGcp(compteCreditTs.getMontant());
						// enregistrememnt euGCP
						/*
						 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
						 * 1L; } else {
						 * 
						 * idgcp++; }
						 */
						euGcp = new EuGcp();
						// euGcp.setIdGcp(idgcp);
						euGcp.setEuTegc(euTegc);
						euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						euGcp.setDateConso(new Date());
						euGcp.setReste(compteCreditTs.getMontant());
						euGcp.setMontGcp(compteCreditTs.getMontant());
						euGcp.setMontPreleve(0);
						euGcp.setTypeGcp("DIST");
						euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						euGcp.setSource(compteCreditTs.getEuCompteCredit().getSource());
						euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
						euGcp.setEuBon(euBonLivraison);
						gcpRepo.saveAndFlush(euGcp);

						// EuCompteCreditTs eccts = new EuCompteCreditTs();
						// eccts.setIdCredit(CompteCredit.getIdCredit());
						// enregistrement des creditsconsommer avec les
						// credits
						/*
						 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
						 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
						 * 
						 * idcreditconsommer++; }
						 */
						EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
						// eucreditConsommer.setIdConsommation(idcreditconsommer);
						eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
						if (membreMoraleAcheteur != null) {
							eucreditConsommer.setCodeMembre(null);
							eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
						} else {
							eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
							eucreditConsommer.setCodeMembreMorale(null);
						}
						eucreditConsommer.setDateConsommation(new Date());
						eucreditConsommer.setHeureConsommation(new Date());
						eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
						eucreditConsommer.setEuOperation(euoperation);
						eucreditConsommer
								.setCodeMembreDist(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
						eucreditConsommer.setEuBon(bon);
						eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
						eucreditConsommer.setTypeProduit(typeProduit);
						creditConsoRepo.save(eucreditConsommer);

						reste = reste - compteCreditTs.getMontant();

						// annulation du CNP
						euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
								compteCreditTs.getCompteSource());
						if (euCnp != null) {
							// euCnp.setMontDebit(euCnp.getMontDebit() - compteCreditTs.getMontant());
							euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
							euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
							cnpRepo.save(euCnp);
						}

						// ajout dans Eu_cnp_entree
						EuCnpEntree euCnpEntree = new EuCnpEntree();
						/*
						 * Long idCnpEntree = cnpEntreeRepo.getLastInsertedId(); if (idCnpEntree ==
						 * null) { idCnpEntree = 1L; } else { idCnpEntree += 1; }
						 * euCnpEntree.setIdCnpEntree(idCnpEntree);
						 */ euCnpEntree.setDateEntree(new Date());
						euCnpEntree.setEuCnp(euCnp);
						euCnpEntree.setMontCnpEntree(compteCreditTs.getMontant());
						euCnpEntree.setTypeCnpEntree("GCP");
						cnpEntreeRepo.save(euCnpEntree);

						// mise a jour comptecreditTs
						compteCreditTs.setMontant(0.0);

					}
				}

				//
				// EuCompte compte, EuProduit produit, double montant,
				// double credit, boolean isBnp, String codeBnp, EuOperation
				// op, double prk, String compteSource, String codeMembre);
				/*
				 * Long idOperation2 = operationRepo.getLastOperationInsertedId();
				 * idOperation2++;
				 */
				EuOperation euoperation2 = new EuOperation();
				// euoperation2.setIdOperation(idOperation2);
				euoperation2.setDateOp(new Date());
				euoperation2.setHeureOp(new Date());
				euoperation2.setMontantOp(montantBon);
				euoperation2.setEuMembreMorale(membreMoraleVendeur);
				euoperation2.setEuUtilisateur(euUtilisateur);
				euoperation2.setCodeProduit(codeProduit);
				euoperation2.setLibOp("CREATION DE GCP VENDEUR");
				euoperation2.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
				euoperation2.setTypeOp("BL");
				operationRepo.save(euoperation2);

				// création de la source GCP AU CNP
				/*
				 * Long idCnp = cnpRepo.getLastCnpInsertedId(); idCnp += 1;
				 */
				EuCnp cnp = new EuCnp();
				cnp.setDateCnp(new Date());
				cnp.setEuCapa(null);
				// cnp.setEuDomiciliation(null);
				cnp.setTypeCnp("Inr");
				cnp.setSourceCredit(membreMoraleVendeur.getCodeMembreMorale() + formatter.format(new Date()));
				cnp.setOrigineCnp(codeProduit);
				cnp.setMontDebit(montantBon);
				cnp.setMontCredit(0);
				cnp.setSoldeCnp(montantBon);
				cnp.setTransfertGcp(0);
				cnp.setEuGcp(euGcp);
				cnp.setEuCompteCredit(null);
				// cnp.setIdCnp(idCnp);

				cnpRepo.save(cnp);

				// Articles vendus
				// concernant l acheteur
				EuArticlesVendu article = new EuArticlesVendu();
				/*
				 * Long idArticleVendu = articleVenduRepo.getLastEuArticleVenduInsertedId(); if
				 * (idArticleVendu == null) { idArticleVendu = 1L; } else {
				 * 
				 * idArticleVendu++; } article.setIdArticleVendu(idArticleVendu);
				 */ article.setCodeBarre("");
				if (membreMoraleAcheteur != null) {
					article.setCodeMembreAcheteur(membreMoraleAcheteur.getCodeMembreMorale());
				} else {
					article.setCodeMembreAcheteur(membreAcheteur.getCodeMembre());
				}
				article.setDateVente(new Date());
				article.setDesignation(nomProduit);
				article.setQuantite(1);
				article.setPrixUnitaire(montantBon);
				article.setCodeMembreVendeur(membreMoraleVendeur.getCodeMembreMorale());
				if (StringUtils.isWhitespace(referenceFacture)) {
					referenceFacture = " ";
				} else if (StringUtils.isWhitespace(periode)) {
					periode = " ";
				} else {
					article.setReference(referenceFacture + " periode: " + periode);
				}
				article.setEuBon(euBonLivraison);
				articleVenduRepo.save(article);

				// mise a jour du Bon de consommation
				bon.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				bon.setBonDateExpression(new Date());
				bon.setBonExprimer(1);
				bonRepo.save(bon);

				// envoi de 2 sms
				messageacheteur = montantBon + " ONT ETE RETIRES DE VOTRE COMPTE :"
						+ compteTransactionAcheteur.getCodeCompte();
				messagevendeur = montantBon + " ONT ETE AJOUTES A VOTRE COMPTE :"
						+ comptePrincipalVendeur.getCodeCompte();

				// 2 enregistrements dans la table eu_sms
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());

				// acheteur
				/*
				 * Long idsms = smsRepo.getLastSmsInserted(); if (idsms == null) { idsms = 1L; }
				 * else {
				 * 
				 * idsms++; }
				 */ EuSms eusms = new EuSms();
				// eusms.setNeng(idsms);
				if (membreMoraleAcheteur != null) {
					eusms.setRecipient(membreMoraleAcheteur.getPortableMembre());
				} else {
					eusms.setRecipient(membreAcheteur.getPortableMembre());
				}
				eusms.setSmsbody(messageacheteur);
				eusms.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms);

				// vendeur
				/*
				 * Long idsms2 = smsRepo.getLastSmsInserted(); if (idsms2 == null) { idsms2 =
				 * 1L; } else {
				 * 
				 * idsms2++; }
				 */ EuSms eusms2 = new EuSms();
				// eusms2.setNeng(idsms2);
				eusms2.setRecipient(comptePrincipalVendeur.getEuMembreMorale().getPortableMembre());
				eusms2.setSmsbody(messagevendeur);
				eusms2.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms2);

				// mise à jour du comptes vendeur
				soldeVendeur = comptePrincipalVendeur.getSolde() + montantBon;
				comptePrincipalVendeur.setSolde(soldeVendeur);
				compteRepo.save(comptePrincipalVendeur);
				// mise à jour du comptes ACHETEUR
				soldeAcheteur = compteTransactionAcheteur.getSolde() - montantBon;
				compteTransactionAcheteur.setSolde(soldeAcheteur);
				compteRepo.save(compteTransactionAcheteur);

				/*
				 * if(montantTaxe>0){ //mise à jour du compte surveillance soldeSurv =
				 * comptePrincipalSurveillance.getSolde() + montantTaxe;
				 * comptePrincipalSurveillance.setSolde(soldeSurv);
				 * 
				 * //euoperation Long idOperation3 = operationRepo.getLastOperationInsertedId();
				 * idOperation3++; EuOperation euoperation3 = new EuOperation();
				 * euoperation3.setIdOperation(idOperation3); euoperation3.setDateOp(new
				 * Date()); euoperation3.setHeureOp(new Date());
				 * euoperation3.setMontantOp(montantTaxe);
				 * euoperation3.setEuMembreMorale(membreMoraleSurveillance);
				 * euoperation3.setEuUtilisateur(euUtilisateur);
				 * euoperation3.setCodeProduit(codeProduit); if(typeTaxe.equals("tva")){
				 * euoperation3.setLibOp("TVA"); }else if (typeTaxe.equals("rsps")){
				 * euoperation3.setLibOp("RSPS"); }
				 * euoperation3.setEuCategorieCompte(comptePrincipalSurveillance.
				 * getEuCategorieCompte()); euoperation3.setTypeOp("BL");
				 * operationRepo.save(euoperation3);
				 * 
				 * //mise a jour du tegc surveillance EuTegc euTegc2 =
				 * tegcRepo.findTegcByCodeMembreAndNomTegc(membreMoraleSurveillance.
				 * getCodeMembreMorale(), "TE ENCAISSEMENT BLEU"); if(euTegc2!=null){
				 * euTegc2.setMontant(euTegc2.getMontant() + montantTaxe);
				 * euTegc2.setSoldeTegc(euTegc2.getSoldeTegc() + montantTaxe);
				 * tegcRepo.save(euTegc2); } //creation du Bon de livraison correspondant
				 * 
				 * Long IdBon2 = bonRepo.findByMaxIdInserted(); EuBon euBonLivraison2 = new
				 * EuBon(); euBonLivraison2.setBonCodeBarre("");
				 * euBonLivraison2.setBonCodeMembreDistributeur(membreMoraleSurveillance.
				 * getCodeMembreMorale()); euBonLivraison2.setBonCodeMembreEmetteur(null);
				 * euBonLivraison2.setBonDate(new Date());
				 * euBonLivraison2.setBonMontant(montantTaxe); euBonLivraison2.setBonNumero("BL"
				 * + StringUtils.leftPad(String.valueOf(IdBon2+1), 8, '0'));
				 * euBonLivraison2.setBonType("BL"); bonRepo.saveAndFlush(euBonLivraison2);
				 * 
				 * //création du gcp correspondant // enregistrememnt euGCP Long idgcp2 =
				 * gcpRepo.getLastEuGcpInsertedId(); if (idgcp2 == null) { idgcp2 = 1L; } else {
				 * 
				 * idgcp2++; } EuGcp euGcp2 = new EuGcp(); euGcp2.setIdGcp(idgcp2);
				 * euGcp2.setEuTegc(euTegc2);
				 * euGcp2.setCodeMembre(membreMoraleSurveillance.getCodeMembreMorale());
				 * euGcp2.setDateConso(new Date()); euGcp2.setReste(montantTaxe);
				 * euGcp2.setMontGcp(montantTaxe); euGcp2.setMontPreleve(0);
				 * euGcp2.setEuCompteCredit(null);
				 * euGcp2.setSource(membreMoraleSurveillance.getCodeMembreMorale() +
				 * formatter.format(new Date()));
				 * euGcp2.setEuCategorieCompte(comptePrincipalSurveillance.getEuCategorieCompte(
				 * )); euGcp2.setEuBon(euBonLivraison2); gcpRepo.saveAndFlush(euGcp2);
				 * 
				 * //creation des frais d'exploitation au cnp // création de la source GCP AU
				 * CNP Long idCnp2 = cnpRepo.getLastCnpInsertedId(); idCnp2 += 1; EuCnp cnp2 =
				 * new EuCnp(); cnp2.setIdCnp(idCnp2); cnp2.setDateCnp(new Date());
				 * cnp2.setEuCapa(null); cnp2.setEuDomiciliation(null); cnp2.setTypeCnp("Inr");
				 * cnp2.setSourceCredit(membreMoraleSurveillance.getCodeMembreMorale() +
				 * formatter.format(new Date())); cnp2.setOrigineCnp(codeProduit);
				 * cnp2.setMontDebit(montantTaxe); cnp2.setMontCredit(0);
				 * cnp2.setSoldeCnp(montantTaxe); cnp2.setTransfertGcp(0); cnp2.setEuGcp(euGcp);
				 * cnp2.setEuCompteCredit(null);
				 * 
				 * cnpRepo.save(cnp2); }
				 */
				// creation des frais d'exploitation
				if (montantFraisExploitation > 0) {
					// creation et utilisation de la source NN
					/*
					 * Long id = nnRepo.getLastInsertId(); if (id == null) { id = 0L; }
					 */
					EuTypeNn typeNn = new EuTypeNn();
					typeNn.setCodeTypeNn("NNMARGE");
					EuNn eunn = new EuNn();
					eunn.setDateEmission(new Date());
					// eunn.setIdNn(id + 1);
					eunn.setEuMembreMorale(membreMoraleSurveillance);
					eunn.setEuTypeNn(typeNn);
					eunn.setMontantEmis(montantFraisExploitation);
					eunn.setMontantRemb(montantFraisExploitation);
					eunn.setSoldeNn(0.0);
					eunn.setTypeEmission("Auto");
					eunn.setIdUtilisateur(euUtilisateur.getIdUtilisateur());
					nnRepo.save(eunn);

					// mise à jour du compte surveillance
					soldeSurv = compteNnMargeSurveillance.getSolde() + montantFraisExploitation;
					compteNnMargeSurveillance.setSolde(soldeSurv);
					compteRepo.save(compteNnMargeSurveillance);

					// euoperation
					/*
					 * Long idOperation3 = operationRepo.getLastOperationInsertedId();
					 * idOperation3++;
					 */ EuOperation euoperation3 = new EuOperation();
					// euoperation3.setIdOperation(idOperation3);
					euoperation3.setDateOp(new Date());
					euoperation3.setHeureOp(new Date());
					euoperation3.setMontantOp(montantFraisExploitation);
					euoperation3.setEuMembreMorale(membreMoraleSurveillance);
					euoperation3.setEuUtilisateur(euUtilisateur);
					euoperation3.setCodeProduit("NN");
					euoperation3.setLibOp("CREATION DE FRAIS EXPLOITATION");
					euoperation3.setEuCategorieCompte(compteNnMargeSurveillance.getEuCategorieCompte());
					euoperation3.setTypeOp("BL");
					operationRepo.save(euoperation3);

				}
				// renvoi de la reponse
				reponse = euBonLivraison.getBonNumero();

				return reponse;

			}
			return reponse;

		} else {
			reponse = "KO3";

		}
		return reponse;
	}

	@Override
	public String saveReglementBySmcipn(String compteVendeur, Double montantSouscription, String CodeBon,
			String codeTegc, String typeR, String codeTypeCredit, Long idUtilisateur,
			List<ArticleVendu> ListArticlesVendu) {
		EuMembre membreAcheteur = null;
		EuMembreMorale membreMoraleAcheteur = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		EuBon euBonLivraison = null;
		String reponse = "KO";
		String codeProduit = "";
		String codeCompteTransactionDemandeur;
		EuCompte compteTransactionDemandeur;
		EuCompte comptePrincipalVendeur;
		EuMembreMorale membreMoraleVendeur = null;
		EuMembre membreVendeur = null;
		// EuCompte comptePrincipalSurveillance;
		// EuMembreMorale membreMoraleSurveillance;
		EuCompte compteNnMargeSurveillance;
		EuTegc euTegc = null;
		EuUtilisateur euUtilisateur;
		List<EuCompteCreditTs> listCompteCreditTs;
		EuBon bon;
		EuCnp euCnp;
		double reste;
		// Long idOperation;
		String typeOperation = "";
		String typesmc = "";
		EuSmc euSmc;
		EuGcp euGcp = null;
		String messageacheteur;
		String messagevendeur;
		double soldeVendeur;
		double soldeAcheteur;
		double montantFraisExploitation;
		double montantBon;
		double tauxprelever = 0.0;
		EuRevendeur revendeur;
		String nomProduit = "";
		// double montGCPnr = 0.0;

		/*
		 * double tauxTaxeAPrelever = 0.0; double montantTaxe = 0.0; String typeTaxe =
		 * "";
		 */

		/*
		 * montantSouscription = ListArticlesVendu.stream().map(c ->
		 * c.getPrix()*c.getQuantite()).reduce(Double::sum).get();
		 * System.out.println("montantSouscription = "+ montantSouscription);
		 */
		for (ArticleVendu articleVendu : ListArticlesVendu) {
			nomProduit = articleVendu.getDesignation();
		}

		// retrouver euUtilisateur
		euUtilisateur = utilisateurRepo.findOne(idUtilisateur);

		// retrouver compte surveillance
		compteNnMargeSurveillance = retrouverCompteNnSurveillanceSource();
		System.out.println("compteNnMargeSurveillance = " + compteNnMargeSurveillance.getCodeCompte());

		// retrouver le compte et la personne morale du vendeur
		comptePrincipalVendeur = compteRepo.findOne(compteVendeur);
		if (compteVendeur.endsWith("M")) {
			membreMoraleVendeur = comptePrincipalVendeur.getEuMembreMorale();
		} else {
			membreVendeur = comptePrincipalVendeur.getEuMembre();
		}

		// retrouver le bon de consommation
		bon = bonRepo.findBon(CodeBon);
		if (bon != null) {

			montantBon = bon.getBonMontant();

			// recherche du taux de prelevement des frais d'exploitation
			tauxprelever = parametreRepo.getMontantTauxOperation();
			montantFraisExploitation = Math.floor(montantBon * tauxprelever / 100);
			System.out.println("montantPrelever = " + montantFraisExploitation);

			// retrouver le code membre acheteur et le compte acheteur
			String codemembreDemandeur = bon.getBonCodeMembreEmetteur();
			if (codemembreDemandeur.endsWith("P")) {
				membreAcheteur = membreRepo.findOne(codemembreDemandeur);
				codeCompteTransactionDemandeur = "NB-TSCNCS-" + membreAcheteur.getCodeMembre();
				compteTransactionDemandeur = compteRepo.findOne(codeCompteTransactionDemandeur);

			} else {
				membreMoraleAcheteur = membreMoraleRepo.findOne(codemembreDemandeur);
				codeCompteTransactionDemandeur = "NB-TSI-" + membreMoraleAcheteur.getCodeMembreMorale();
				compteTransactionDemandeur = compteRepo.findOne(codeCompteTransactionDemandeur);
			}
			switch (typeR) {
			case "r":
				codeProduit = "Ir";
				typeOperation = "BCr";
				break;
			case "nr":
				codeProduit = "Inr";
				typeOperation = "BCnr";
				break;
			}

			/*
			 * // mettre a jour eu appelOffre EuAppelOffre appelOffre =
			 * appelOffreDao.findAppelOffresByNumero(numeroAppelOffre );
			 * 
			 * if (appelOffre != null) {
			 * 
			 * appelOffre.setMembreMoraleExecutante(membreMoraleVendeur
			 * .getCodeMembreMorale()); appelOffreDao.save(appelOffre);
			 * 
			 * EuProposition euProposition = appelOffreDao
			 * .findPropositionByNumero(numeroAppelOffre); if (euProposition != null) {
			 * 
			 * EuFrais euFrais = fraisRepo.findByIdProposition(euProposition
			 * .getIdProposition());
			 * 
			 * euFrais.setDisponible(1); fraisRepo.save(euFrais);
			 * 
			 * 
			 * } }
			 */
			listCompteCreditTs = new ArrayList<>();

			listCompteCreditTs = compteCreditTsRepo.findByEuBon_BonNumero(bon.getBonNumero());
			System.out.println("listCompteCreditTs size= " + listCompteCreditTs.size());

			if (listCompteCreditTs.isEmpty()) {
				reponse = "KO1";
			} else {

				reste = montantBon;
				// enregistrement dans eu operation

				/*
				 * idOperation = operationRepo.getLastOperationInsertedId(); if (idOperation ==
				 * null) { idOperation = 1L; } else {
				 * 
				 * idOperation++; }
				 */
				EuOperation euoperation = new EuOperation();
				// euoperation.setIdOperation(idOperation);
				euoperation.setDateOp(new Date());
				euoperation.setHeureOp(new Date());
				euoperation.setMontantOp(montantBon);
				euoperation.setEuMembreMorale(membreMoraleAcheteur);
				euoperation.setEuUtilisateur(euUtilisateur);
				euoperation.setCodeProduit(codeProduit);
				euoperation.setLibOp("Reglement par SMCIPNWI");
				euoperation.setEuCategorieCompte(compteTransactionDemandeur.getEuCategorieCompte());
				euoperation.setTypeOp(typeOperation);
				operationRepo.save(euoperation);

				/*
				 * //calcul du montant GCP nr montGCPnr = calculerMontantGcp(montantBon);
				 * System.out.println("montGCPnr = " + montGCPnr);
				 */
				// enregistrement dans la table eu_tegc
				if (StringUtils.isNotBlank(codeTegc)) {
					euTegc = tegcRepo.findOne(codeTegc);

					if (euTegc != null) {
						// euTegc.setSubvention(1);
						// te avec regime Tva
						/*
						 * if((euTegc.getRegimeTva()!= null)&&(euTegc.getRegimeTva()==1)){ typeTaxe =
						 * "tva"; tauxTaxeAPrelever = parametreRepo.findByCodeAndLib("taxe", "TVA");
						 * montantTaxe = Math.floor(montantBon*tauxTaxeAPrelever)/100;
						 * 
						 * System.out.println("tauxTaxeAPrelever = " + tauxTaxeAPrelever); } //te formel
						 * if((euTegc.getFormel()!= null)&& (euTegc.getFormel()==1)){ typeTaxe = "rsps";
						 * tauxTaxeAPrelever = parametreRepo.findByCodeAndLib("RSPS",
						 * "retenue formelle"); montantTaxe =
						 * Math.floor(montantBon*tauxTaxeAPrelever)/100; }else if((euTegc.getFormel()!=
						 * null)&& (euTegc.getFormel()==0)){ typeTaxe = "rsps"; //te informel
						 * tauxTaxeAPrelever = parametreRepo.findByCodeAndLib("RSPS",
						 * "retenue informelle"); montantTaxe =
						 * Math.floor(montantBon*tauxTaxeAPrelever)/100; }
						 */
						euTegc.setMontant(euTegc.getMontant() + montantBon);
						euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantBon);
						tegcRepo.save(euTegc);
					}
				}

				// creer le bon de livraison (autoincrement)
				Long IdBon = bonRepo.findByMaxIdInserted();

				euBonLivraison = new EuBon();
				euBonLivraison.setBonCodeBarre("");
				if (membreMoraleVendeur != null) {
					euBonLivraison.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				} else {
					euBonLivraison.setBonCodeMembreDistributeur(membreVendeur.getCodeMembre());

				}
				euBonLivraison.setBonCodeMembreEmetteur(codemembreDemandeur);
				euBonLivraison.setBonDate(new Date());
				euBonLivraison.setBonMontant(montantBon);
				euBonLivraison.setBonNumero("BL" + StringUtils.leftPad(String.valueOf(IdBon + 1), 8, '0'));
				euBonLivraison.setBonType("BL");
				euBonLivraison.setBonExprimer(1);
				bonRepo.saveAndFlush(euBonLivraison);

				for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {
					// prk = compteCreditTs.getEuCompteCredit().getPrk();

					if (compteCreditTs.getMontant() > (reste)) {
						// enregistrememnt euSMC
						typesmc = "";

						if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
							typesmc = "CNCSr";
						} else {
							typesmc = "CNCSnr";
						}

						/*
						 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
						 * 1L; } else {
						 * 
						 * idsmc++;
						 * 
						 * } euSmc.setIdSmc(idsmc);
						 */ euSmc = new EuSmc();

						// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
						euSmc.setDateSmc(new Date());
						euSmc.setMontant(reste);
						euSmc.setMontantSolde(reste);
						euSmc.setEntree(Double.valueOf("0"));
						euSmc.setSolde(Double.valueOf("0"));
						euSmc.setSortie(Double.valueOf("0"));
						euSmc.setOrigineSmc(0);
						euSmc.setSourceCredit(compteCreditTs.getCompteSource());
						euSmc.setTypeSmc(typesmc);
						euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						smcRepo.save(euSmc);

						// enregistrememnt euGCP vendeur
						/*
						 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
						 * 1L; } else { idgcp++; }
						 */
						euGcp = new EuGcp();
						// euGcp.setIdGcp(idgcp);
						euGcp.setEuTegc(euTegc);
						if (membreMoraleVendeur != null) {
							euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						} else {
							euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembre().getCodeMembre());
						}
						euGcp.setDateConso(new Date());
						euGcp.setReste(montantBon);
						euGcp.setMontGcp(montantBon);
						euGcp.setMontPreleve(0);
						euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						euGcp.setTypeGcp("SMCIPN");
						euGcp.setSource(compteCreditTs.getCompteSource());
						euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
						euGcp.setEuBon(euBonLivraison);
						gcpRepo.saveAndFlush(euGcp);

						// enregistrement dans euRevendeur
						/*
						 * Long idRevendeur = revendeurRepo.getLastRevendeurInsertedId(); if
						 * (idRevendeur == null) { idRevendeur = 1L; } else { idRevendeur++; }
						 */
						revendeur = new EuRevendeur();
						// revendeur.setIdRevendeur(idRevendeur);
						if (membreMoraleVendeur != null) {
							revendeur.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						} else {
							revendeur.setCodeMembre(comptePrincipalVendeur.getEuMembre().getCodeMembre());
						}
						revendeur.setNomProduit(nomProduit);
						revendeur.setEuGcp(euGcp);
						revendeur.setMontantSouscription(montantSouscription);
						revendeurRepo.saveAndFlush(revendeur);

						// enregistrement des creditsconsommer avec les
						// credits
						/*
						 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
						 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
						 * idcreditconsommer++; }
						 */

						EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
						// eucreditConsommer.setIdConsommation(idcreditconsommer);
						eucreditConsommer.setCodeCompte(compteTransactionDemandeur.getCodeCompte());
						if (membreMoraleAcheteur != null) {
							eucreditConsommer.setCodeMembre(null);
							eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
						} else {
							eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
							eucreditConsommer.setCodeMembreMorale(null);
						}
						eucreditConsommer.setDateConsommation(new Date());
						eucreditConsommer.setHeureConsommation(new Date());
						eucreditConsommer.setMontConsommation(reste);
						eucreditConsommer.setEuOperation(euoperation);
						if (membreMoraleVendeur != null) {
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						} else {
							eucreditConsommer.setCodeMembreDist(comptePrincipalVendeur.getEuMembre().getCodeMembre());
						}
						eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
						eucreditConsommer.setEuBon(bon);
						eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
						eucreditConsommer.setTypeProduit(compteCreditTs.getEuCompteCredit().getTypeProduit());
						creditConsoRepo.save(eucreditConsommer);

						// annulation du CNP
						euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
								compteCreditTs.getCompteSource());
						if (euCnp != null) {
							euCnp.setMontDebit(euCnp.getMontDebit() - reste);
							euCnp.setMontCredit(euCnp.getMontCredit() + reste);
							euCnp.setSoldeCnp(euCnp.getSoldeCnp() + reste);
						}
						// Mise à jour des eucomptecredit

						compteCreditTs.setMontant(compteCreditTs.getMontant() - reste);

						break;
					} else if (compteCreditTs.getMontant() <= reste) {
						// enregistrememnt euSMC
						typesmc = "";

						if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
							typesmc = "CNCSr";
						} else {
							typesmc = "CNCSnr";
						}

						/*
						 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
						 * 1L; } else {
						 * 
						 * idsmc++; }
						 */ euSmc = new EuSmc();
						// euSmc.setIdSmc(idsmc);

						// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
						euSmc.setDateSmc(new Date());
						euSmc.setMontant(compteCreditTs.getMontant());
						euSmc.setMontantSolde(compteCreditTs.getMontant());
						euSmc.setEntree(Double.valueOf("0"));
						euSmc.setSolde(Double.valueOf("0"));
						euSmc.setSortie(Double.valueOf("0"));
						euSmc.setOrigineSmc(0);
						euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
						euSmc.setTypeSmc(typesmc);
						euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						smcRepo.save(euSmc);

						// double montGCPnr2 = calculerMontantGcp(compteCreditTs.getMontant());
						// enregistrememnt euGCP
						/*
						 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
						 * 1L; } else {
						 * 
						 * idgcp++; }
						 */ euGcp = new EuGcp();
						// euGcp.setIdGcp(idgcp);
						euGcp.setEuTegc(euTegc);
						if (membreMoraleVendeur != null) {
							euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						} else {
							euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembre().getCodeMembre());
						}
						euGcp.setDateConso(new Date());
						euGcp.setReste(compteCreditTs.getMontant());
						euGcp.setMontGcp(compteCreditTs.getMontant());
						euGcp.setMontPreleve(0);
						euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						euGcp.setTypeGcp("SMCIPN");
						euGcp.setSource(compteCreditTs.getEuCompteCredit().getSource());
						euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
						euGcp.setEuBon(euBonLivraison);
						gcpRepo.saveAndFlush(euGcp);

						// enregistrement dans euRevendeur
						/*
						 * Long idRevendeur = revendeurRepo.getLastRevendeurInsertedId(); if
						 * (idRevendeur == null) { idRevendeur = 1L; } else { idRevendeur++; }
						 */
						revendeur = new EuRevendeur();
						// revendeur.setIdRevendeur(idRevendeur);
						if (membreMoraleVendeur != null) {
							revendeur.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						} else {
							revendeur.setCodeMembre(comptePrincipalVendeur.getEuMembre().getCodeMembre());
						}
						revendeur.setNomProduit(nomProduit);
						revendeur.setEuGcp(euGcp);
						revendeur.setMontantSouscription(montantSouscription);
						revendeurRepo.saveAndFlush(revendeur);

						// enregistrement des creditsconsommer avec les
						// credits
						/*
						 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
						 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
						 * 
						 * idcreditconsommer++; }
						 */ EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
						// eucreditConsommer.setIdConsommation(idcreditconsommer);
						eucreditConsommer.setCodeCompte(compteTransactionDemandeur.getCodeCompte());
						if (membreMoraleAcheteur != null) {
							eucreditConsommer.setCodeMembre(null);
							eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
						} else {
							eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
							eucreditConsommer.setCodeMembreMorale(null);
						}
						eucreditConsommer.setDateConsommation(new Date());
						eucreditConsommer.setHeureConsommation(new Date());
						eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
						eucreditConsommer.setEuOperation(euoperation);
						if (membreMoraleVendeur != null) {
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
						} else {
							eucreditConsommer.setCodeMembreDist(comptePrincipalVendeur.getEuMembre().getCodeMembre());
						}
						eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
						eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
						eucreditConsommer.setEuBon(bon);
						eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
						eucreditConsommer.setTypeProduit(compteCreditTs.getEuCompteCredit().getTypeProduit());
						creditConsoRepo.save(eucreditConsommer);

						reste = (reste) - compteCreditTs.getMontant();

						// annulation du CNP
						euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
								compteCreditTs.getCompteSource());
						if (euCnp != null) {
							// euCnp.setMontDebit(euCnp.getMontDebit() - compteCreditTs.getMontant());
							euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
							euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
							cnpRepo.save(euCnp);
						}

						// ajout dans Eu_cnp_entree
						EuCnpEntree euCnpEntree = new EuCnpEntree();
						/*
						 * Long idCnpEntree = cnpEntreeRepo.getLastInsertedId(); if (idCnpEntree ==
						 * null) { idCnpEntree = 1L; } else { idCnpEntree += 1; }
						 * euCnpEntree.setIdCnpEntree(idCnpEntree);
						 */euCnpEntree.setDateEntree(new Date());
						euCnpEntree.setEuCnp(euCnp);
						euCnpEntree.setMontCnpEntree(compteCreditTs.getMontant());
						euCnpEntree.setTypeCnpEntree("GCP");
						cnpEntreeRepo.save(euCnpEntree);

						// mise a jour comptecreditTs
						compteCreditTs.setMontant(0.0);

					}
				}

				// creation de gcp dans EuOp;
				/*
				 * Long idOperation2 = operationRepo.getLastOperationInsertedId();
				 * idOperation2++;
				 */ EuOperation euoperation2 = new EuOperation();
				// euoperation2.setIdOperation(idOperation2);
				euoperation2.setDateOp(new Date());
				euoperation2.setHeureOp(new Date());
				euoperation2.setMontantOp(montantBon);
				if (membreMoraleVendeur != null) {
					euoperation2.setEuMembreMorale(membreMoraleVendeur);
				} else {
					euoperation2.setEuMembre(membreVendeur);
				}
				euoperation2.setEuUtilisateur(euUtilisateur);
				euoperation2.setCodeProduit(codeProduit);
				euoperation2.setLibOp("CREATION DE GCP VENDEUR");
				euoperation2.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
				euoperation2.setTypeOp("BL");
				operationRepo.save(euoperation2);

				// création de la source GCP AU CNP
				/*
				 * Long idCnp = cnpRepo.getLastCnpInsertedId(); idCnp += 1;
				 */ EuCnp cnp = new EuCnp();
				cnp.setDateCnp(new Date());
				cnp.setEuCapa(null);
				// cnp.setEuDomiciliation(null);
				cnp.setTypeCnp("Inr");
				if (membreMoraleVendeur != null) {
					cnp.setSourceCredit(membreMoraleVendeur.getCodeMembreMorale() + formatter.format(new Date()));
				} else {
					cnp.setSourceCredit(membreVendeur.getCodeMembre() + formatter.format(new Date()));

				}
				cnp.setOrigineCnp(codeProduit);
				cnp.setMontDebit(montantBon);
				cnp.setMontCredit(0);
				cnp.setSoldeCnp(montantBon);
				cnp.setTransfertGcp(0);
				cnp.setEuGcp(euGcp);
				cnp.setEuCompteCredit(null);
				// cnp.setIdCnp(idCnp);

				cnpRepo.save(cnp);

				// mettre a jour les articles vendus
				System.out.println("mise a jour des articlesvendus = " + ListArticlesVendu.size());

				for (ArticleVendu articleVendu : ListArticlesVendu) {
					EuArticlesVendu article = new EuArticlesVendu();
					/*
					 * Long idArticleVendu = articleVenduRepo.getLastEuArticleVenduInsertedId(); if
					 * (idArticleVendu == null) { idArticleVendu = 1L; } else { idArticleVendu++; }
					 */
					// article.setIdArticleVendu(idArticleVendu);
					if (StringUtils.isWhitespace(articleVendu.getCodeBarre())) {
						article.setCodeBarre(articleVendu.getCodeBarre());
					}
					if (membreMoraleAcheteur != null) {
						article.setCodeMembreAcheteur(membreMoraleAcheteur.getCodeMembreMorale());
					} else {
						article.setCodeMembreAcheteur(membreAcheteur.getCodeMembre());
					}
					article.setDateVente(new Date());
					if (!StringUtils.isWhitespace(articleVendu.getDesignation())) {
						article.setDesignation(articleVendu.getDesignation());
					}
					article.setQuantite(articleVendu.getQuantite());
					article.setPrixUnitaire(articleVendu.getPrix());
					if (membreMoraleVendeur != null) {
						article.setCodeMembreVendeur(membreMoraleVendeur.getCodeMembreMorale());
					} else {
						article.setCodeMembreVendeur(membreVendeur.getCodeMembre());

					}
					article.setDateVente(new Date());
					if (!StringUtils.isWhitespace(articleVendu.getReference())) {
						article.setReference(articleVendu.getReference());
					}
					article.setEuBon(euBonLivraison);
					articleVenduRepo.save(article);
				}

				// mise a jour du Bon de consommation
				if (membreMoraleVendeur != null) {
					bon.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				} else {
					bon.setBonCodeMembreDistributeur(membreVendeur.getCodeMembre());

				}
				bon.setBonDateExpression(new Date());
				bon.setBonExprimer(1);
				bonRepo.save(bon);

				// envoi de 2 sms
				messageacheteur = montantBon + " ONT ETE RETIRES DU COMPTE :"
						+ compteTransactionDemandeur.getCodeCompte();
				messagevendeur = montantBon + " ONT ETE AJOUTES AU COMPTE :" + comptePrincipalVendeur.getCodeCompte();

				// 2 enregistrements dans la table eu_sms
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());

				// acheteur
				/*
				 * Long idsms = smsRepo.getLastSmsInserted(); if (idsms == null) { idsms = 1L; }
				 * else {
				 * 
				 * idsms++; }
				 */
				EuSms eusms = new EuSms();
				// eusms.setNeng(idsms);
				if (membreMoraleAcheteur != null) {
					eusms.setRecipient(membreMoraleAcheteur.getPortableMembre());
				} else {
					eusms.setRecipient(membreAcheteur.getPortableMembre());
				}
				eusms.setSmsbody(messageacheteur);
				eusms.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms);

				// vendeur
				/*
				 * Long idsms2 = smsRepo.getLastSmsInserted(); if (idsms2 == null) { idsms2 =
				 * 1L; } else {
				 * 
				 * idsms2++; }
				 */
				EuSms eusms2 = new EuSms();
				// eusms2.setNeng(idsms2);
				if (membreMoraleVendeur != null) {
					eusms2.setRecipient(comptePrincipalVendeur.getEuMembreMorale().getPortableMembre());
				} else {
					eusms2.setRecipient(comptePrincipalVendeur.getEuMembre().getPortableMembre());
				}
				eusms2.setSmsbody(messagevendeur);
				eusms2.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms2);

				// mise à jour des comptes vendeur
				soldeVendeur = comptePrincipalVendeur.getSolde() + montantBon;
				comptePrincipalVendeur.setSolde(soldeVendeur);
				compteRepo.save(comptePrincipalVendeur);

				// mise à jour du comptes ACHETEUR
				soldeAcheteur = compteTransactionDemandeur.getSolde() - montantBon;
				compteTransactionDemandeur.setSolde(soldeAcheteur);
				compteRepo.save(compteTransactionDemandeur);

				System.out.println("OPERATION DE REGLEMENT SMCIPN EFFECTUEE AVEC SUCCES");
				reponse = "OK";

				return reponse;
			}
		}
		return reponse;
	}

	@Override
	public String saveReglementParBonAuGcs(String codeTegc, String compteVendeur, String CodeBon, String typeR,
			String typeProduit, String codeTypeCredit, String nomProduit, Long idUtilisateur,
			List<ArticleVendu> ListArticlesVendu) {
		EuMembre membreAcheteur = null;
		EuMembreMorale membreMoraleAcheteur = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		EuBon euBonLivraison = null;
		String reponse = "KO";
		String codeProduit = "";
		String codeCompteTransactionAcheteur;
		// double tauxprelever;
		// EuCompte comptePrincipalSurveillance;
		// EuMembreMorale membreMoraleSurveillance;
		EuCompte compteNnMargeSurveillance;
		EuCompte compteTransactionAcheteur;
		EuCompte comptePrincipalVendeur;
		EuMembreMorale membreMoraleVendeur;
		EuTegc euTegc = null;
		EuUtilisateur euUtilisateur;
		List<EuCompteCreditTs> listCompteCreditTs;
		List<EuGcsc> ListGcsc;
		EuBon bon;
		EuCnp euCnp;
		double reste;
		// Long idOperation;
		String typeOperation = "";
		// String codeTegc;
		String typesmc = "";
		EuSmc euSmc;
		EuGcp euGcp = null;
		EuDetailGcsc euDetailGcsc;
		String messageacheteur;
		String messagevendeur;
		double soldeVendeur;
		// double soldeSurv;
		double soldeAcheteur;
		// double montantPrelever;
		double montantFraisExploitation;
		double montantBon;
		double montantGcsc;
		double montantGcp;
		double mntSubRestAPayer = 0d;
		double tauxprelever = 0d;
		double reste2 = 0;
		double reste3 = 0;
		double reste4 = 0;

		// retrouver euUtilisateur
		euUtilisateur = utilisateurRepo.findOne(idUtilisateur);

		// retrouver compte surveillance
		compteNnMargeSurveillance = retrouverCompteNnSurveillanceSource();
		System.out.println("compteNnMargeSurveillance = " + compteNnMargeSurveillance.getCodeCompte());
		// membreMoraleSurveillance = compteNnMargeSurveillance.getEuMembreMorale();
		// comptePrincipalSurveillance =
		// compteRepo.findCompteSurveillanceByCodeMembreMorale("TPAGCP",
		// membreMoraleSurveillance.getCodeMembreMorale());

		// retrouver le compte et la personne morale du vendeur
		comptePrincipalVendeur = compteRepo.findOne(compteVendeur);
		membreMoraleVendeur = comptePrincipalVendeur.getEuMembreMorale();

		// retrouver le bon de consommation
		bon = bonRepo.findBon(CodeBon);
		montantBon = bon.getBonMontant();

		// recherche du taux de prelevement des frais d'exploitation
		tauxprelever = parametreRepo.getMontantTauxOperation();
		montantFraisExploitation = Math.floor(montantBon * tauxprelever / 100);
		System.out.println("montantPrelever = " + montantFraisExploitation);

		// retrouver le code membre acheteur et le compte acheteur
		String codemembreAcheteur = bon.getBonCodeMembreEmetteur();
		if (codemembreAcheteur.endsWith("P")) {
			membreAcheteur = membreRepo.findOne(codemembreAcheteur);
			codeCompteTransactionAcheteur = "NB-TSRPG-" + membreAcheteur.getCodeMembre();
			compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
		} else {
			membreMoraleAcheteur = membreMoraleRepo.findOne(codemembreAcheteur);
			codeCompteTransactionAcheteur = "NB-TSGCI-" + membreMoraleAcheteur.getCodeMembreMorale();
			compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
		}
		if (codeCompteTransactionAcheteur.endsWith("P")) {
			switch (typeR) {
			case "r":
				codeProduit = "RPGr";
				typeOperation = "BCr";
				break;
			case "nr":
				codeProduit = "RPGnr";
				typeOperation = "BCnr";
				break;
			}
		} else {
			if (codeCompteTransactionAcheteur.startsWith("NB-TSI")) {
				typeR = "pre";
				codeProduit = "InrPRE";
				typeOperation = "InrPRE";
			} else {
				switch (typeR) {
				case "r":
					codeProduit = "Ir";
					typeOperation = "BCr";
					break;
				case "nr":
					codeProduit = "Inr";
					typeOperation = "BCnr";
					break;
				case "rea":
					codeProduit = "Inr";
					typeOperation = "BC";
					break;

				}
			}
		}

		listCompteCreditTs = new ArrayList<>();
		ListGcsc = new ArrayList<>();

		listCompteCreditTs = compteCreditTsRepo.findByEuBon_BonNumero(bon.getBonNumero());
		System.out.println("listCompteCreditTs size= " + listCompteCreditTs.size());

		if (listCompteCreditTs.isEmpty()) {
			reponse = "KO1";
		} else {

			// retrouver le tegc correspondant
			// codeTegc = euUtilisateur.getCodeTegc();
			if (StringUtils.isNotBlank(codeTegc)) {
				euTegc = tegcRepo.findOne(codeTegc);
			}
			// mise
			// retrouver la liste des gcsc correspondants
			if (euTegc != null) {
				ListGcsc = gcscRepo.findByTegc(euTegc.getCodeTegc());

			}
			if (ListGcsc.isEmpty()) {

			} else {

				// enregistrement dans eu operation
				/*
				 * idOperation = operationRepo.getLastOperationInsertedId(); if (idOperation ==
				 * null) { idOperation = 1L; } else {
				 * 
				 * idOperation++; }
				 */
				EuOperation euoperation = new EuOperation();
				// euoperation.setIdOperation(idOperation);
				euoperation.setDateOp(new Date());
				euoperation.setHeureOp(new Date());
				euoperation.setMontantOp(montantBon);
				if (codeCompteTransactionAcheteur.endsWith("P")) {
					euoperation.setEuMembre(membreAcheteur);

				} else {
					euoperation.setEuMembreMorale(membreMoraleAcheteur);
				}
				euoperation.setEuUtilisateur(euUtilisateur);
				euoperation.setCodeProduit(codeProduit);
				if (typeR.equals("rea")) {
					euoperation.setLibOp("REAPPRO " + nomProduit);
				} else {
					euoperation.setLibOp(nomProduit);
				}
				euoperation.setEuCategorieCompte(compteTransactionAcheteur.getEuCategorieCompte());
				euoperation.setTypeOp(typeOperation);
				operationRepo.save(euoperation);

				// creer le bon de livraison (autoincrement)
				Long IdBon = bonRepo.findByMaxIdInserted();

				euBonLivraison = new EuBon();
				euBonLivraison.setBonCodeBarre("");
				euBonLivraison.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				euBonLivraison.setBonCodeMembreEmetteur(codemembreAcheteur);
				euBonLivraison.setBonDate(new Date());
				euBonLivraison.setBonMontant(montantBon);
				euBonLivraison.setBonNumero("BL" + StringUtils.leftPad(String.valueOf(IdBon + 1), 8, '0'));
				euBonLivraison.setBonType("BL");
				euBonLivraison.setBonExprimer(1);
				bonRepo.saveAndFlush(euBonLivraison);

				// enregistrement dans la table eu_tegc
				/*
				 * codeTegc = euUtilisateur.getCodeTegc(); if(StringUtils.isNotBlank(codeTegc)){
				 * euTegc = tegcRepo.findOne(codeTegc);
				 * 
				 * 
				 * euTegc.setMontant(euTegc.getMontant() + montantBon);
				 * euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantBon);
				 * tegcRepo.save(euTegc);
				 */
				// calcul du montant total restant de la subvention
				if (euTegc != null) {
					mntSubRestAPayer = gcscRepo.getSumGcscByTegc(euTegc.getCodeTegc());
					System.out.println("mntSubRestAPayer= " + mntSubRestAPayer);
				}
				reste = montantBon;

				if (montantBon > mntSubRestAPayer) {
					montantGcsc = mntSubRestAPayer;
					montantGcp = montantBon - mntSubRestAPayer;
					System.out.println("montantGcsc= " + montantGcsc);
					System.out.println("montantGcp= " + montantGcp);

					reste = montantBon;
					reste2 = montantGcsc;
					reste3 = montantGcp;

					if (euTegc != null) {
						euTegc.setMontant(euTegc.getMontant() + montantGcp);
						euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantGcp);
						euTegc.setSubvention(0);
						tegcRepo.save(euTegc);
					}
					// mise à jour des comptes vendeur

					soldeVendeur = comptePrincipalVendeur.getSolde() + montantGcp;
					comptePrincipalVendeur.setSolde(soldeVendeur);
					compteRepo.save(comptePrincipalVendeur);

					for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {
						// prk = compteCreditTs.getEuCompteCredit().getPrk();

						if (compteCreditTs.getMontant() > (reste)) {
							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							/// euSmc.setIdSmc(idsmc);
							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(montantBon);
							euSmc.setMontantSolde(montantBon);
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getCompteSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */
							EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(montantBon);
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								euCnp.setMontDebit(euCnp.getMontDebit() - montantBon);
								euCnp.setMontCredit(euCnp.getMontCredit() + montantBon);
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() + montantBon);
							}

							// enregistrememnt euGCP vendeur
							/*
							 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
							 * 1L; } else { idgcp++; }
							 */ euGcp = new EuGcp();
							// euGcp.setIdGcp(idgcp);
							euGcp.setEuTegc(euTegc);
							euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							euGcp.setDateConso(new Date());
							euGcp.setReste(montantGcp);
							euGcp.setMontGcp(montantGcp);
							euGcp.setMontPreleve(0);
							euGcp.setTypeGcp("DIST");
							euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							euGcp.setSource(compteCreditTs.getCompteSource());
							euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
							euGcp.setEuBon(euBonLivraison);
							gcpRepo.saveAndFlush(euGcp);

							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste2) {
									reste2 = reste2 - euGcsc.getSolde();

									// creation du detail gcsc
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */ euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									// enregistrement du euGcsc
									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);

								}
							}

							// Mise à jour des eucomptecredit
							compteCreditTs.setMontant(compteCreditTs.getMontant() - montantBon);

							break;
						} else if (compteCreditTs.getMontant() <= reste) {

							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);

							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(compteCreditTs.getMontant());
							euSmc.setMontantSolde(compteCreditTs.getMontant());
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							if (reste3 > compteCreditTs.getMontant()) {
								// enregistrememnt euGCP vendeur
								/*
								 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
								 * 1L; } else { idgcp++; }
								 */ euGcp = new EuGcp();
								// euGcp.setIdGcp(idgcp);
								euGcp.setEuTegc(euTegc);
								euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
								euGcp.setDateConso(new Date());
								euGcp.setReste(compteCreditTs.getMontant());
								euGcp.setMontGcp(compteCreditTs.getMontant());
								euGcp.setMontPreleve(0);
								euGcp.setTypeGcp("DIST");
								euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
								euGcp.setSource(compteCreditTs.getCompteSource());
								euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
								euGcp.setEuBon(euBonLivraison);
								gcpRepo.saveAndFlush(euGcp);
								reste3 = reste3 - compteCreditTs.getMontant();

							} else if (reste3 <= compteCreditTs.getMontant()) {
								// enregistrememnt euGCP vendeur
								/*
								 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
								 * 1L; } else { idgcp++; }
								 */
								euGcp = new EuGcp();
								// euGcp.setIdGcp(idgcp);
								euGcp.setEuTegc(euTegc);
								euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
								euGcp.setDateConso(new Date());
								euGcp.setReste(reste3);
								euGcp.setMontGcp(reste3);
								euGcp.setMontPreleve(0);
								euGcp.setTypeGcp("DIST");
								euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
								euGcp.setSource(compteCreditTs.getCompteSource());
								euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
								euGcp.setEuBon(euBonLivraison);
								gcpRepo.saveAndFlush(euGcp);
								reste3 = 0;
							}

							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste2) {
									reste2 = reste2 - euGcsc.getSolde();

									// creation du detail gcsc
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */ euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									// enregistrement euGcsc
									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);
								}
							}
							/*
							 * // mise à jour des comptes vendeur
							 * 
							 * soldeVendeur = comptePrincipalVendeur.getSolde() + montantBon;
							 * comptePrincipalVendeur.setSolde(soldeVendeur);
							 * compteRepo.save(comptePrincipalVendeur);
							 */
							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */ EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							reste = (reste) - compteCreditTs.getMontant();

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								// euCnp.setMontDebit(euCnp.getMontDebit() - compteCreditTs.getMontant());
								euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
								cnpRepo.save(euCnp);
							}

							// ajout dans Eu_cnp_entree
							EuCnpEntree euCnpEntree = new EuCnpEntree();
							/*
							 * Long idCnpEntree = cnpEntreeRepo.getLastInsertedId(); if (idCnpEntree ==
							 * null) { idCnpEntree = 1L; } else { idCnpEntree += 1; }
							 * euCnpEntree.setIdCnpEntree(idCnpEntree);
							 */euCnpEntree.setDateEntree(new Date());
							euCnpEntree.setEuCnp(euCnp);
							euCnpEntree.setMontCnpEntree(compteCreditTs.getMontant());
							euCnpEntree.setTypeCnpEntree("GCP");
							cnpEntreeRepo.save(euCnpEntree);

							// mise a jour comptecreditTs
							compteCreditTs.setMontant(0.0);

						}
					}

				} else {
					// montantBon <= mntSubRestAPayer

					reste = montantBon;

					for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {
						// prk = compteCreditTs.getEuCompteCredit().getPrk();

						if (compteCreditTs.getMontant() > reste) {
							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);
							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(montantBon);
							euSmc.setMontantSolde(montantBon);
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getCompteSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */
							EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(montantBon);
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								euCnp.setMontDebit(euCnp.getMontDebit() - montantBon);
								euCnp.setMontCredit(euCnp.getMontCredit() + montantBon);
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() + montantBon);
							}

							// enregistrement des euGcsc et details
							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste) {
									reste = reste - euGcsc.getSolde();

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);

								} else {

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */ euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(reste);
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + reste);
									euGcsc.setSolde(euGcsc.getSolde() - reste);
									gcscRepo.save(euGcsc);

									break;
								}
							}

							// Mise à jour des eucomptecredit
							compteCreditTs.setMontant(compteCreditTs.getMontant() - montantBon);

							break;
						} else if (compteCreditTs.getMontant() <= reste) {

							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);

							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(compteCreditTs.getMontant());
							euSmc.setMontantSolde(compteCreditTs.getMontant());
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */ EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							reste = (reste) - compteCreditTs.getMontant();

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
								cnpRepo.save(euCnp);
							}

							reste4 = compteCreditTs.getMontant();
							// enregistrement des euGcsc et details
							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste4) {
									reste4 = reste4 - euGcsc.getSolde();

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);

								} else {

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(reste4);
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + reste4);
									euGcsc.setSolde(euGcsc.getSolde() - reste4);
									gcscRepo.save(euGcsc);

									break;
								}
							}

							// mise a jour comptecreditTs
							compteCreditTs.setMontant(0.0);

						}
					}
				}

				// creation de gcp dans EuOp;
				/*
				 * Long idOperation2 = operationRepo.getLastOperationInsertedId();
				 * idOperation2++;
				 */
				EuOperation euoperation2 = new EuOperation();
				// euoperation2.setIdOperation(idOperation2);
				euoperation2.setDateOp(new Date());
				euoperation2.setHeureOp(new Date());
				euoperation2.setMontantOp(montantBon);
				euoperation2.setEuMembreMorale(membreMoraleVendeur);
				euoperation2.setEuUtilisateur(euUtilisateur);
				euoperation2.setCodeProduit(codeProduit);
				euoperation2.setLibOp("CREATION DE GCP VENDEUR");
				euoperation2.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
				euoperation2.setTypeOp("BL");
				operationRepo.save(euoperation2);

				// création de la source GCP AU CNP
//                Long idCnp = cnpRepo.getLastCnpInsertedId();
//                idCnp += 1;
				EuCnp cnp = new EuCnp();
				cnp.setDateCnp(new Date());
				cnp.setEuCapa(null);
				// cnp.setEuDomiciliation(null);
				cnp.setTypeCnp("Inr");
				cnp.setSourceCredit(membreMoraleVendeur.getCodeMembreMorale() + formatter.format(new Date()));
				cnp.setOrigineCnp(codeProduit);
				cnp.setMontDebit(montantBon);
				cnp.setMontCredit(0);
				cnp.setSoldeCnp(montantBon);
				cnp.setTransfertGcp(0);
				cnp.setEuGcp(euGcp);
				cnp.setEuCompteCredit(null);
				// cnp.setIdCnp(idCnp);

				cnpRepo.save(cnp);

				// mettre a jour les articles vendus
				System.out.println("mise a jour des articlesvendus = " + ListArticlesVendu.size());

				// ListArticlesVendu.stream().forEach((ArticleVendu articleVendu) -> {
				for (ArticleVendu articleVendu : ListArticlesVendu) {
					EuArticlesVendu article = new EuArticlesVendu();
					/*
					 * Long idArticleVendu = articleVenduRepo.getLastEuArticleVenduInsertedId(); if
					 * (idArticleVendu == null) { idArticleVendu = 1L; } else { idArticleVendu++; }
					 */
					// article.setIdArticleVendu(idArticleVendu);
					if (StringUtils.isWhitespace(articleVendu.getCodeBarre())) {
						article.setCodeBarre(articleVendu.getCodeBarre());
					}
					if (membreMoraleAcheteur != null) {
						article.setCodeMembreAcheteur(membreMoraleAcheteur.getCodeMembreMorale());
					} else {
						article.setCodeMembreAcheteur(membreAcheteur.getCodeMembre());
					}
					article.setDateVente(new Date());
					if (!StringUtils.isWhitespace(articleVendu.getDesignation())) {
						article.setDesignation(articleVendu.getDesignation());
					}
					article.setQuantite(articleVendu.getQuantite());
					article.setPrixUnitaire(articleVendu.getPrix());
					article.setCodeMembreVendeur(membreMoraleVendeur.getCodeMembreMorale());
					article.setDateVente(new Date());
					if (!StringUtils.isWhitespace(articleVendu.getReference())) {
						article.setReference(articleVendu.getReference());
					}
					article.setEuBon(euBonLivraison);
					articleVenduRepo.save(article);
				}

				// mise a jour du Bon de consommation
				bon.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				bon.setBonDateExpression(new Date());
				bon.setBonExprimer(1);
				bonRepo.save(bon);

				// envoi de 2 sms
				messageacheteur = montantBon + " ONT ETE RETIRES DU COMPTE :"
						+ compteTransactionAcheteur.getCodeCompte();
				messagevendeur = montantBon + " ONT ETE AJOUTES AU REMBOURSEMENT DE VOTRE SUBVENTION";

				// 2 enregistrements dans la table eu_sms
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());

				// acheteur
				/*
				 * Long idsms = smsRepo.getLastSmsInserted(); if (idsms == null) { idsms = 1L; }
				 * else {
				 * 
				 * idsms++; }
				 */
				EuSms eusms = new EuSms();
				// eusms.setNeng(idsms);
				if (membreMoraleAcheteur != null) {
					eusms.setRecipient(membreMoraleAcheteur.getPortableMembre());
				} else {
					eusms.setRecipient(membreAcheteur.getPortableMembre());
				}
				eusms.setSmsbody(messageacheteur);
				eusms.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms);

				// vendeur
				/*
				 * Long idsms2 = smsRepo.getLastSmsInserted(); if (idsms2 == null) { idsms2 =
				 * 1L; } else { idsms2++; }
				 */
				EuSms eusms2 = new EuSms();
				// eusms2.setNeng(idsms2);
				eusms2.setRecipient(comptePrincipalVendeur.getEuMembreMorale().getPortableMembre());
				eusms2.setSmsbody(messagevendeur);
				eusms2.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms2);

				// mise à jour du comptes ACHETEUR
				soldeAcheteur = compteTransactionAcheteur.getSolde() - montantBon;
				compteTransactionAcheteur.setSolde(soldeAcheteur);
				compteRepo.save(compteTransactionAcheteur);

				// renvoi de la reponse
				reponse = "OK";

				return reponse;
			}
		}
		return reponse;
	}

	@Override
	public String saveReglementFactureParBonAuGcsc(String codeTegc, String compteVendeur, String CodeBon, String typeR,
			String typeProduit, String codeTypeCredit, String nomProduit, String referenceFacture, String periode,
			Long idUtilisateur) {
		EuMembre membreAcheteur = null;
		EuMembreMorale membreMoraleAcheteur = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		EuBon euBonLivraison = null;
		String reponse = "KO";
		String codeProduit = "";
		String codeCompteTransactionAcheteur;
		// double tauxprelever;
		// EuCompte comptePrincipalSurveillance;
		// EuMembreMorale membreMoraleSurveillance;
		EuCompte compteNnMargeSurveillance;
		EuCompte compteTransactionAcheteur;
		EuCompte comptePrincipalVendeur;
		EuMembreMorale membreMoraleVendeur;
		EuTegc euTegc = null;
		EuUtilisateur euUtilisateur;
		List<EuCompteCreditTs> listCompteCreditTs;
		List<EuGcsc> ListGcsc;
		EuBon bon;
		EuCnp euCnp;
		double reste;
		// Long idOperation;
		String typeOperation = "";
		// String codeTegc;
		String typesmc = "";
		EuSmc euSmc;
		EuGcp euGcp = null;
		EuDetailGcsc euDetailGcsc;
		String messageacheteur;
		String messagevendeur;
		double soldeVendeur;
		// double soldeSurv;
		double soldeAcheteur;
		// double montantPrelever;
		double montantFraisExploitation;
		double montantBon;
		double montantGcsc;
		double montantGcp;
		double mntSubRestAPayer = 0d;
		double tauxprelever = 0d;
		double reste2 = 0;
		double reste3 = 0;
		double reste4 = 0;

		// retrouver euUtilisateur
		euUtilisateur = utilisateurRepo.findOne(idUtilisateur);

		// retrouver compte surveillance
		compteNnMargeSurveillance = retrouverCompteNnSurveillanceSource();
		System.out.println("compteNnMargeSurveillance = " + compteNnMargeSurveillance.getCodeCompte());
		// membreMoraleSurveillance = compteNnMargeSurveillance.getEuMembreMorale();
		// comptePrincipalSurveillance =
		// compteRepo.findCompteSurveillanceByCodeMembreMorale("TPAGCP",
		// membreMoraleSurveillance.getCodeMembreMorale());

		// retrouver le compte et la personne morale du vendeur
		comptePrincipalVendeur = compteRepo.findOne(compteVendeur);
		membreMoraleVendeur = comptePrincipalVendeur.getEuMembreMorale();

		// retrouver le bon de consommation
		bon = bonRepo.findBon(CodeBon);
		montantBon = bon.getBonMontant();

		// recherche du taux de prelevement des frais d'exploitation
		tauxprelever = parametreRepo.getMontantTauxOperation();
		montantFraisExploitation = Math.floor(montantBon * tauxprelever / 100);
		System.out.println("montantPrelever = " + montantFraisExploitation);

		// retrouver le code membre acheteur et le compte acheteur
		String codemembreAcheteur = bon.getBonCodeMembreEmetteur();
		if (codemembreAcheteur.endsWith("P")) {
			membreAcheteur = membreRepo.findOne(codemembreAcheteur);
			codeCompteTransactionAcheteur = "NB-TSRPG-" + membreAcheteur.getCodeMembre();
			compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
		} else {
			membreMoraleAcheteur = membreMoraleRepo.findOne(codemembreAcheteur);
			codeCompteTransactionAcheteur = "NB-TSGCI-" + membreMoraleAcheteur.getCodeMembreMorale();
			compteTransactionAcheteur = compteRepo.findOne(codeCompteTransactionAcheteur);
		}
		if (codeCompteTransactionAcheteur.endsWith("P")) {
			switch (typeR) {
			case "r":
				codeProduit = "RPGr";
				typeOperation = "BCr";
				break;
			case "nr":
				codeProduit = "RPGnr";
				typeOperation = "BCnr";
				break;
			}
		} else {
			if (codeCompteTransactionAcheteur.startsWith("NB-TSI")) {
				typeR = "pre";
				codeProduit = "InrPRE";
				typeOperation = "InrPRE";
			} else {
				switch (typeR) {
				case "r":
					codeProduit = "Ir";
					typeOperation = "BCr";
					break;
				case "nr":
					codeProduit = "Inr";
					typeOperation = "BCnr";
					break;
				case "rea":
					codeProduit = "Inr";
					typeOperation = "BC";
					break;

				}
			}
		}

		listCompteCreditTs = new ArrayList<>();
		ListGcsc = new ArrayList<>();

		listCompteCreditTs = compteCreditTsRepo.findByEuBon_BonNumero(bon.getBonNumero());
		System.out.println("listCompteCreditTs size= " + listCompteCreditTs.size());

		if (listCompteCreditTs.isEmpty()) {
			reponse = "KO1";
		} else {

			// retrouver le tegc correspondant
			// codeTegc = euUtilisateur.getCodeTegc();
			if (StringUtils.isNotBlank(codeTegc)) {
				euTegc = tegcRepo.findOne(codeTegc);
			}
			// mise
			// retrouver la liste des gcsc correspondants
			if (euTegc != null) {
				ListGcsc = gcscRepo.findByTegc(euTegc.getCodeTegc());
				System.out.println("ListGcsc size= " + ListGcsc.size());
			}
			if (ListGcsc.isEmpty()) {

			} else {

				// enregistrement dans eu operation
				/*
				 * idOperation = operationRepo.getLastOperationInsertedId(); if (idOperation ==
				 * null) { idOperation = 1L; } else {
				 * 
				 * idOperation++; }
				 */
				EuOperation euoperation = new EuOperation();
				// euoperation.setIdOperation(idOperation);
				euoperation.setDateOp(new Date());
				euoperation.setHeureOp(new Date());
				euoperation.setMontantOp(montantBon);
				if (codeCompteTransactionAcheteur.endsWith("P")) {
					euoperation.setEuMembre(membreAcheteur);

				} else {
					euoperation.setEuMembreMorale(membreMoraleAcheteur);
				}
				euoperation.setEuUtilisateur(euUtilisateur);
				euoperation.setCodeProduit(codeProduit);
				if (typeR.equals("rea")) {
					euoperation.setLibOp("REAPPRO " + nomProduit);
				} else {
					euoperation.setLibOp(nomProduit);
				}
				euoperation.setEuCategorieCompte(compteTransactionAcheteur.getEuCategorieCompte());
				euoperation.setTypeOp(typeOperation);
				operationRepo.save(euoperation);

				// creer le bon de livraison (autoincrement)
				Long IdBon = bonRepo.findByMaxIdInserted();

				euBonLivraison = new EuBon();
				euBonLivraison.setBonCodeBarre("");
				euBonLivraison.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				euBonLivraison.setBonCodeMembreEmetteur(codemembreAcheteur);
				euBonLivraison.setBonDate(new Date());
				euBonLivraison.setBonMontant(montantBon);
				euBonLivraison.setBonNumero("BL" + StringUtils.leftPad(String.valueOf(IdBon + 1), 8, '0'));
				euBonLivraison.setBonType("BL");
				euBonLivraison.setBonExprimer(1);
				bonRepo.saveAndFlush(euBonLivraison);

				// enregistrement dans la table eu_tegc
				/*
				 * codeTegc = euUtilisateur.getCodeTegc(); if(StringUtils.isNotBlank(codeTegc)){
				 * euTegc = tegcRepo.findOne(codeTegc);
				 * 
				 * 
				 * euTegc.setMontant(euTegc.getMontant() + montantBon);
				 * euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantBon);
				 * tegcRepo.save(euTegc);
				 */
				// calcul du montant total restant de la subvention
				if (euTegc != null) {
					mntSubRestAPayer = gcscRepo.getSumGcscByTegc(euTegc.getCodeTegc());
					System.out.println("mntSubRestAPayer= " + mntSubRestAPayer);
				}
				reste = montantBon;

				if (montantBon > mntSubRestAPayer) {
					montantGcsc = mntSubRestAPayer;
					montantGcp = montantBon - mntSubRestAPayer;
					System.out.println("montantGcsc= " + montantGcsc);
					System.out.println("montantGcp= " + montantGcp);

					reste = montantBon;
					reste2 = montantGcsc;
					reste3 = montantGcp;

					if (euTegc != null) {
						euTegc.setMontant(euTegc.getMontant() + montantGcp);
						euTegc.setSoldeTegc(euTegc.getSoldeTegc() + montantGcp);
						euTegc.setSubvention(0);
						tegcRepo.save(euTegc);
					}
					// mise à jour des comptes vendeur

					soldeVendeur = comptePrincipalVendeur.getSolde() + montantGcp;
					comptePrincipalVendeur.setSolde(soldeVendeur);
					compteRepo.save(comptePrincipalVendeur);

					for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {
						// prk = compteCreditTs.getEuCompteCredit().getPrk();

						if (compteCreditTs.getMontant() > (reste)) {
							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);
							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(montantBon);
							euSmc.setMontantSolde(montantBon);
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getCompteSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */
							EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(montantBon);
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								euCnp.setMontDebit(euCnp.getMontDebit() - montantBon);
								euCnp.setMontCredit(euCnp.getMontCredit() + montantBon);
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() + montantBon);
							}

							// enregistrememnt euGCP vendeur
							/*
							 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
							 * 1L; } else { idgcp++; }
							 */
							euGcp = new EuGcp();
							// euGcp.setIdGcp(idgcp);
							euGcp.setEuTegc(euTegc);
							euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							euGcp.setDateConso(new Date());
							euGcp.setReste(montantGcp);
							euGcp.setMontGcp(montantGcp);
							euGcp.setMontPreleve(0);
							euGcp.setTypeGcp("DIST");
							euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							euGcp.setSource(compteCreditTs.getCompteSource());
							euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
							euGcp.setEuBon(euBonLivraison);
							gcpRepo.saveAndFlush(euGcp);

							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste2) {
									reste2 = reste2 - euGcsc.getSolde();

									// creation du detail gcsc
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									// enregistrement du euGcsc
									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);

								}
							}

							// Mise à jour des eucomptecredit
							compteCreditTs.setMontant(compteCreditTs.getMontant() - montantBon);

							break;
						} else if (compteCreditTs.getMontant() <= reste) {

							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);

							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(compteCreditTs.getMontant());
							euSmc.setMontantSolde(compteCreditTs.getMontant());
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							if (reste3 > compteCreditTs.getMontant()) {
								// enregistrememnt euGCP vendeur
								/*
								 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
								 * 1L; } else { idgcp++; }
								 */ euGcp = new EuGcp();
								// euGcp.setIdGcp(idgcp);
								euGcp.setEuTegc(euTegc);
								euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
								euGcp.setDateConso(new Date());
								euGcp.setReste(compteCreditTs.getMontant());
								euGcp.setMontGcp(compteCreditTs.getMontant());
								euGcp.setMontPreleve(0);
								euGcp.setTypeGcp("DIST");
								euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
								euGcp.setSource(compteCreditTs.getCompteSource());
								euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
								euGcp.setEuBon(euBonLivraison);
								gcpRepo.saveAndFlush(euGcp);
								reste3 = reste3 - compteCreditTs.getMontant();

							} else if (reste3 <= compteCreditTs.getMontant()) {
								// enregistrememnt euGCP vendeur
								/*
								 * Long idgcp = gcpRepo.getLastEuGcpInsertedId(); if (idgcp == null) { idgcp =
								 * 1L; } else { idgcp++; }
								 */
								euGcp = new EuGcp();
								// euGcp.setIdGcp(idgcp);
								euGcp.setEuTegc(euTegc);
								euGcp.setCodeMembre(comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
								euGcp.setDateConso(new Date());
								euGcp.setReste(reste3);
								euGcp.setMontGcp(reste3);
								euGcp.setMontPreleve(0);
								euGcp.setTypeGcp("DIST");
								euGcp.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
								euGcp.setSource(compteCreditTs.getCompteSource());
								euGcp.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
								euGcp.setEuBon(euBonLivraison);
								gcpRepo.saveAndFlush(euGcp);
								reste3 = 0;
							}

							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste2) {
									reste2 = reste2 - euGcsc.getSolde();

									// creation du detail gcsc
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									// enregistrement euGcsc
									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);
								}
							}
							/*
							 * // mise à jour des comptes vendeur
							 * 
							 * soldeVendeur = comptePrincipalVendeur.getSolde() + montantBon;
							 * comptePrincipalVendeur.setSolde(soldeVendeur);
							 * compteRepo.save(comptePrincipalVendeur);
							 */
							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */
							EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							reste = (reste) - compteCreditTs.getMontant();

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								// euCnp.setMontDebit(euCnp.getMontDebit() - compteCreditTs.getMontant());
								euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
								cnpRepo.save(euCnp);
							}

							// ajout dans Eu_cnp_entree
							EuCnpEntree euCnpEntree = new EuCnpEntree();
							/*
							 * Long idCnpEntree = cnpEntreeRepo.getLastInsertedId(); if (idCnpEntree ==
							 * null) { idCnpEntree = 1L; } else { idCnpEntree += 1; }
							 * euCnpEntree.setIdCnpEntree(idCnpEntree);
							 */euCnpEntree.setDateEntree(new Date());
							euCnpEntree.setEuCnp(euCnp);
							euCnpEntree.setMontCnpEntree(compteCreditTs.getMontant());
							euCnpEntree.setTypeCnpEntree("GCP");
							cnpEntreeRepo.save(euCnpEntree);

							// mise a jour comptecreditTs
							compteCreditTs.setMontant(0.0);

						}
					}

				} else {
					// montantBon <= mntSubRestAPayer

					reste = montantBon;

					for (EuCompteCreditTs compteCreditTs : listCompteCreditTs) {
						// prk = compteCreditTs.getEuCompteCredit().getPrk();

						if (compteCreditTs.getMontant() > reste) {
							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);
							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(montantBon);
							euSmc.setMontantSolde(montantBon);
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getCompteSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 * 
							 */ EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(montantBon);
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								euCnp.setMontDebit(euCnp.getMontDebit() - montantBon);
								euCnp.setMontCredit(euCnp.getMontCredit() + montantBon);
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() + montantBon);
							}

							// enregistrement des euGcsc et details
							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste) {
									reste = reste - euGcsc.getSolde();

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */ euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);

								} else {

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */ euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(reste);
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + reste);
									euGcsc.setSolde(euGcsc.getSolde() - reste);
									gcscRepo.save(euGcsc);

									break;
								}
							}

							// Mise à jour des eucomptecredit
							compteCreditTs.setMontant(compteCreditTs.getMontant() - montantBon);

							break;
						} else if (compteCreditTs.getMontant() <= reste) {

							// enregistrememnt euSMC
							typesmc = "";

							if (compteCreditTs.getEuCompteCredit().getEuProduit().getCodeProduit().equals("RPGr")) {
								typesmc = "CNCSr";
							} else {
								typesmc = "CNCSnr";
							}

							/*
							 * Long idsmc = smcRepo.getLastEuSmcInsertedId(); if (idsmc == null) { idsmc =
							 * 1L; } else {
							 * 
							 * idsmc++; }
							 */ euSmc = new EuSmc();
							// euSmc.setIdSmc(idsmc);

							// *euSmc.setCodeCapa(euCreditCapa.getEuCompteCreditCapaPK().getCodeCapa());
							euSmc.setDateSmc(new Date());
							euSmc.setMontant(compteCreditTs.getMontant());
							euSmc.setMontantSolde(compteCreditTs.getMontant());
							euSmc.setEntree(Double.valueOf("0"));
							euSmc.setSolde(Double.valueOf("0"));
							euSmc.setSortie(Double.valueOf("0"));
							euSmc.setOrigineSmc(0);
							euSmc.setSourceCredit(compteCreditTs.getEuCompteCredit().getSource());
							euSmc.setTypeSmc(typesmc);
							euSmc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							smcRepo.save(euSmc);

							// enregistrement des creditsconsommer avec les
							// credits
							/*
							 * Long idcreditconsommer = creditConsoRepo.getLastEuConsommationInsertedId();
							 * if (idcreditconsommer == null) { idcreditconsommer = 1L; } else {
							 * 
							 * idcreditconsommer++; }
							 */ EuCreditConsommer eucreditConsommer = new EuCreditConsommer();
							// eucreditConsommer.setIdConsommation(idcreditconsommer);
							eucreditConsommer.setCodeCompte(compteTransactionAcheteur.getCodeCompte());
							if (membreMoraleAcheteur != null) {
								eucreditConsommer.setCodeMembre(null);
								eucreditConsommer.setCodeMembreMorale(membreMoraleAcheteur.getCodeMembreMorale());
							} else {
								eucreditConsommer.setCodeMembre(membreAcheteur.getCodeMembre());
								eucreditConsommer.setCodeMembreMorale(null);
							}
							eucreditConsommer.setDateConsommation(new Date());
							eucreditConsommer.setHeureConsommation(new Date());
							eucreditConsommer.setMontConsommation(compteCreditTs.getMontant());
							eucreditConsommer.setEuOperation(euoperation);
							eucreditConsommer.setCodeMembreDist(
									comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
							eucreditConsommer.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
							eucreditConsommer.setEuProduit(compteCreditTs.getEuCompteCredit().getEuProduit());
							eucreditConsommer.setEuBon(bon);
							eucreditConsommer.setCodeTypeCredit(codeTypeCredit);
							eucreditConsommer.setTypeProduit(typeProduit);
							creditConsoRepo.save(eucreditConsommer);

							reste = (reste) - compteCreditTs.getMontant();

							// annulation du CNP
							euCnp = cnpRepo.findBySourceCredit(compteCreditTs.getEuCompteCredit().getIdCredit(),
									compteCreditTs.getCompteSource());
							if (euCnp != null) {
								euCnp.setMontCredit(euCnp.getMontCredit() + compteCreditTs.getMontant());
								euCnp.setSoldeCnp(euCnp.getSoldeCnp() - compteCreditTs.getMontant());
								cnpRepo.save(euCnp);
							}

							reste4 = compteCreditTs.getMontant();
							// enregistrement des euGcsc et details
							for (EuGcsc euGcsc : ListGcsc) {
								if (euGcsc.getSolde() <= reste4) {
									reste4 = reste4 - euGcsc.getSolde();

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(euGcsc.getSolde());
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + euGcsc.getSolde());
									euGcsc.setSolde(0);
									gcscRepo.save(euGcsc);

								} else {

									// details
									/*
									 * Long idDetailgcsc = detailGcscRepo.getLastInsertedId(); if (idDetailgcsc ==
									 * null) { idDetailgcsc = 1L; } else { idDetailgcsc++; }
									 */
									euDetailGcsc = new EuDetailGcsc();
									// euDetailGcsc.setIdDetailGcsc(idDetailgcsc);
									euDetailGcsc.setCodeMembre(
											comptePrincipalVendeur.getEuMembreMorale().getCodeMembreMorale());
									euDetailGcsc.setMontGcsc(reste4);
									euDetailGcsc.setDateConso(new Date());
									euDetailGcsc.setEuGcsc(euGcsc);
									euDetailGcsc.setEuCompteCredit(compteCreditTs.getEuCompteCredit());
									euDetailGcsc.setSource(compteCreditTs.getCompteSource());
									euDetailGcsc.setEuBon(euBonLivraison);
									detailGcscRepo.saveAndFlush(euDetailGcsc);

									euGcsc.setDebit(euGcsc.getDebit() + reste4);
									euGcsc.setSolde(euGcsc.getSolde() - reste4);
									gcscRepo.save(euGcsc);

									break;
								}
							}

							// mise a jour comptecreditTs
							compteCreditTs.setMontant(0.0);

						}
					}
				}

				// creation de gcp dans EuOp;
				/*
				 * Long idOperation2 = operationRepo.getLastOperationInsertedId();
				 * idOperation2++;
				 */
				EuOperation euoperation2 = new EuOperation();
				// euoperation2.setIdOperation(idOperation2);
				euoperation2.setDateOp(new Date());
				euoperation2.setHeureOp(new Date());
				euoperation2.setMontantOp(montantBon);
				euoperation2.setEuMembreMorale(membreMoraleVendeur);
				euoperation2.setEuUtilisateur(euUtilisateur);
				euoperation2.setCodeProduit(codeProduit);
				euoperation2.setLibOp("CREATION DE GCP VENDEUR");
				euoperation2.setEuCategorieCompte(comptePrincipalVendeur.getEuCategorieCompte());
				euoperation2.setTypeOp("BL");
				operationRepo.save(euoperation2);

				// création de la source GCP AU CNP
				/*
				 * Long idCnp = cnpRepo.getLastCnpInsertedId(); idCnp += 1;
				 */
				EuCnp cnp = new EuCnp();
				cnp.setDateCnp(new Date());
				cnp.setEuCapa(null);
				// cnp.setEuDomiciliation(null);
				cnp.setTypeCnp("Inr");
				cnp.setSourceCredit(membreMoraleVendeur.getCodeMembreMorale() + formatter.format(new Date()));
				cnp.setOrigineCnp(codeProduit);
				cnp.setMontDebit(montantBon);
				cnp.setMontCredit(0);
				cnp.setSoldeCnp(montantBon);
				cnp.setTransfertGcp(0);
				cnp.setEuGcp(euGcp);
				cnp.setEuCompteCredit(null);
				// cnp.setIdCnp(idCnp);

				cnpRepo.save(cnp);

				// mettre a jour les articles vendus
				EuArticlesVendu article = new EuArticlesVendu();
				/*
				 * Long idArticleVendu = articleVenduRepo.getLastEuArticleVenduInsertedId(); if
				 * (idArticleVendu == null) { idArticleVendu = 1L; } else {
				 * 
				 * idArticleVendu++; } article.setIdArticleVendu(idArticleVendu);
				 */
				article.setCodeBarre("");
				if (membreMoraleAcheteur != null) {
					article.setCodeMembreAcheteur(membreMoraleAcheteur.getCodeMembreMorale());
				} else {
					article.setCodeMembreAcheteur(membreAcheteur.getCodeMembre());
				}
				article.setDateVente(new Date());
				article.setDesignation(nomProduit);
				article.setQuantite(1);
				article.setPrixUnitaire(montantBon);
				article.setCodeMembreVendeur(membreMoraleVendeur.getCodeMembreMorale());
				if (StringUtils.isWhitespace(referenceFacture)) {
					referenceFacture = " ";
				} else if (StringUtils.isWhitespace(periode)) {
					periode = " ";
				} else {
					article.setReference(referenceFacture + " periode: " + periode);
				}
				article.setEuBon(euBonLivraison);
				articleVenduRepo.save(article);

				// mise a jour du Bon de consommation
				bon.setBonCodeMembreDistributeur(membreMoraleVendeur.getCodeMembreMorale());
				bon.setBonDateExpression(new Date());
				bon.setBonExprimer(1);
				bonRepo.save(bon);

				// envoi de 2 sms
				messageacheteur = montantBon + " ONT ETE RETIRES DU COMPTE :"
						+ compteTransactionAcheteur.getCodeCompte();
				messagevendeur = montantBon + " ONT ETE AJOUTES AU REMBOURSEMENT DE VOTRE SUBVENTION";

				// 2 enregistrements dans la table eu_sms
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());

				// acheteur
				/*
				 * Long idsms = smsRepo.getLastSmsInserted(); if (idsms == null) { idsms = 1L; }
				 * else {
				 * 
				 * idsms++; }
				 */
				EuSms eusms = new EuSms();
				// eusms.setNeng(idsms);
				if (membreMoraleAcheteur != null) {
					eusms.setRecipient(membreMoraleAcheteur.getPortableMembre());
				} else {
					eusms.setRecipient(membreAcheteur.getPortableMembre());
				}
				eusms.setSmsbody(messageacheteur);
				eusms.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms);

				// vendeur
				/*
				 * Long idsms2 = smsRepo.getLastSmsInserted(); if (idsms2 == null) { idsms2 =
				 * 1L; } else { idsms2++; }
				 */
				EuSms eusms2 = new EuSms();
				// eusms2.setNeng(idsms2);
				eusms2.setRecipient(comptePrincipalVendeur.getEuMembreMorale().getPortableMembre());
				eusms2.setSmsbody(messagevendeur);
				eusms2.setDatetime(formater.format(new Date()));
				smsRepo.save(eusms2);

				// mise à jour du comptes ACHETEUR
				soldeAcheteur = compteTransactionAcheteur.getSolde() - montantBon;
				compteTransactionAcheteur.setSolde(soldeAcheteur);
				compteRepo.save(compteTransactionAcheteur);

				// renvoi de la reponse
				reponse = euBonLivraison.getBonNumero();

				return reponse;
			}
		}
		return reponse;
	}

}
