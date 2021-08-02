package com.esmc.mcnp.infrastructure.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.acteur.EuActeursCreneaux;
import com.esmc.mcnp.domain.entity.ba.EuCapaTs;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapa;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.mprg.EuDetailKrr;
import com.esmc.mcnp.domain.entity.mprg.EuKrr;
import com.esmc.mcnp.domain.entity.oi.EuBnpSqmax;
import com.esmc.mcnp.domain.entity.oi.EuCaps;
import com.esmc.mcnp.domain.entity.others.EuOperation;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.domain.entity.smcipn.EuUtiliser;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.acteurs.EuActeurCreneauxService;
import com.esmc.mcnp.infrastructure.services.ba.EuCapaTsService;
import com.esmc.mcnp.infrastructure.services.bc.EuBonService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteCreditCapaService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteCreditService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.common.EuOperationService;
import com.esmc.mcnp.infrastructure.services.mprg.EuDetailKrrService;
import com.esmc.mcnp.infrastructure.services.mprg.EuKrrService;
import com.esmc.mcnp.infrastructure.services.oi.EuBnpSqmaxService;
import com.esmc.mcnp.infrastructure.services.oi.EuCapsService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuUtiliserService;

@Component
@Transactional
public class BonConsoComponent {

	
	private EuCompteCreditService ccService;
	private EuCompteService compteService;
	private EuParametresService paramService;
	private CreditUtility creditUtility;
	private EuOperationService opService;
	private EuCapsService capsService;
	private CreditComponent creditService;
	private EuKrrService krrService;
	private EuDetailKrrService detailKrrService;
	private EuBnpSqmaxService sqmaxService;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;
	private EuBonService bonService;
	private EuCapaTsService capatsService;
	private EuActeurCreneauxService acteurCreneauxService;
	private EuSmcService smcService;
	private EuUtiliserService utliliserService;
	private EuCompteCreditCapaService cccapaService;
	
	@Autowired
	public BonConsoComponent(EuCompteCreditService ccService, EuCompteService compteService,
			EuParametresService paramService, CreditUtility creditUtility, EuOperationService opService,
			EuCapsService capsService, CreditComponent creditService, EuKrrService krrService,
			EuDetailKrrService detailKrrService, EuBnpSqmaxService sqmaxService, EuMembreService membreService,
			EuMembreMoraleService moraleService, EuBonService bonService, EuCapaTsService capatsService,
			EuActeurCreneauxService acteurCreneauxService, EuSmcService smcService, EuUtiliserService utliliserService,
			EuCompteCreditCapaService cccapaService) {
		this.ccService = ccService;
		this.compteService = compteService;
		this.paramService = paramService;
		this.creditUtility = creditUtility;
		this.opService = opService;
		this.capsService = capsService;
		this.creditService = creditService;
		this.krrService = krrService;
		this.detailKrrService = detailKrrService;
		this.sqmaxService = sqmaxService;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.bonService = bonService;
		this.capatsService = capatsService;
		this.acteurCreneauxService = acteurCreneauxService;
		this.smcService = smcService;
		this.utliliserService = utliliserService;
		this.cccapaService = cccapaService;
	}

	public boolean souscrireRpg(String codeMembre, EuCompte compte, EuCompte compteNn, String NumeroBon, String typeBon,
			CalculBonInfo bonInfo, double montant) throws DataAccessException, NullPointerException {

		double capa = montant;
		double credit = 0;
		double creditSqmax = 0;
		double sqmax = 0;
		double quota = 0;
		double mont_capa_eff = 0;
		double credit_fs = 0;
		boolean enroller = false;
		boolean caps_lie = false;
		int periode = paramService.getParam("periode", "valeur");
		EuCaps caps = new EuCaps();

		EuBon bon = bonService.findByBonCode(NumeroBon, 0);
		if (bon == null) {
			return false;
		}
		List<EuCapaTs> capatss = capatsService.findByEuBon_BonNumero(bon.getBonNumero());
		if (capatss.size() == 0) {
			return false;
		}
		EuMembre membre = compte.getEuMembre();
		if (!Objects.nonNull(membre)) {
			membre = membreService.findById(codeMembre);
		}

		double mont_op = capa;
		String codeProduit = typeBon + bonInfo.getCatBon();

		/**
		 * Vérification du type de crédit (récurrent ou non récurrent) et calcul du
		 * montant du crédit
		 */
		if (bonInfo.getCatBon().equals("r")) {
			double reste_capa_eff = 0;

			quota = paramService.getParametre("quota", "RPGr");
			List<EuCompteCredit> ccef = ListUtils
					.emptyIfNull(ccService.findbyCompteAndProduit(compte.getCodeCompte(), codeProduit));
			if (!ccef.isEmpty()) {
				mont_capa_eff = ccef.stream().filter(c -> c.getNbreRenouvel() != 0)
						.mapToDouble(EuCompteCredit::getMontantPlace).sum();
			}

			if (mont_capa_eff > 0) {
				if (quota > mont_capa_eff) {
					reste_capa_eff = quota - mont_capa_eff;
					if (capa > reste_capa_eff) {
						sqmax = capa - reste_capa_eff;
						capa = reste_capa_eff;
						credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));
						creditSqmax = Math.floor(creditUtility.calculBonConso(bonInfo, sqmax) / 4);
					} else {
						credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));
					}
				} else {
					sqmax = capa;
					capa = 0;
					creditSqmax = Math.floor(creditUtility.calculBonConso(bonInfo, sqmax) / 4);
				}
			} else {
				credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));
			}
		} else {
			credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));
		}

		if (credit > 0) {
			double mont_bc = creditUtility.calculBcStandard(capa);
			double mont_bcp = 0;
			double tbcp = 0;
			if (bonInfo.getCatBon().equals("r")) {
				tbcp = paramService.getParametre("TBCP", "valeur");
				mont_bcp = mont_bc * tbcp / 100;
				mont_bc = credit;
				if (StringUtils.isNotBlank(membre.getAutoEnroler()) && membre.getAutoEnroler().equalsIgnoreCase("N")) {
					enroller = true;
					caps = capsService.findByEuMembre2_CodeMembre(membre.getCodeMembre());
					if (Objects.nonNull(caps)) {
						if (Objects.nonNull(caps.getEuCompteCredit())) {
							caps_lie = true;
						} else {
							credit_fs = paramService.getParametre("PCAPS", "valeur");
							mont_bc = mont_bc - credit_fs;
						}
					} else {
						return false;
					}
				}
			} else {
				mont_bc = credit;
			}

			EuCategorieCompte catCompte = new EuCategorieCompte();
			catCompte.setCodeCat("TPAGCRPG");

			Date date = new Date();
			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(catCompte);
			op.setCodeProduit(codeProduit);
			op.setDateOp(date);
			op.setHeureOp(date);
			op.setLibOp("Achat de Bon de Consommation");
			op.setTypeOp("ABC");
			op.setMontantOp(mont_op);
			op.setEuMembre(membre);
			opService.add(op);

			compte.setSolde(compte.getSolde() + mont_bc);
			compteService.update(compte);

			EuProduit produit = new EuProduit();
			produit.setCodeProduit(codeProduit);
			String c_source = null;
			c_source = compteNn.getCodeCompte();
			EuCompteCredit cc = creditService.createCredit(compte, produit, capa, mont_bc, false, null, op, c_source,
					membre.getCodeMembre(), bonInfo);
			creditService.updateListCapaTs(capatss, cc, capa, date, bonInfo.getCatBon());

			compteNn.setSolde(compteNn.getSolde() - capa);
			compteService.update(compteNn);

			if (enroller && !caps_lie) {
				caps.setIndexer(1);
				caps.setEuCompteCredit(cc);
				caps.setPeriode(1);
				caps.setPanu(0);
				caps.setMontFs(caps.getMontFs() + credit_fs);
				caps.setMontPanuFs(0);
				capsService.add(caps);

				/*if (Objects.nonNull(caps.getEuMembre1())) {
					EuCompte compte_fs = creditService.saveCompte(caps.getEuMembre1(), "TFS", "NB", credit_fs);
					EuProduit produitfs = new EuProduit();
					produitfs.setCodeProduit("FS");
					creditService.createCredit(compte_fs, produitfs, caps.getMontCaps(), credit_fs, true,
							caps.getCodeCaps(), op, String.valueOf(cc.getIdCredit()),
							caps.getEuMembre1().getCodeMembre(),
							new CalculBonInfo("r", "limite", bonInfo.getTypeProduit(), bonInfo.getDuree(), 0.0));

				} else {
					EuCompte compte_fs = creditService.saveCompte(caps.getEuMembreMorale(), "TFS", "NB", credit_fs);
					EuProduit produitfs = new EuProduit();
					produitfs.setCodeProduit("FS");
					creditService.createCredit(compte_fs, produitfs, caps.getMontCaps(), credit_fs, true,
							caps.getCodeCaps(), op, String.valueOf(cc.getIdCredit()),
							caps.getEuMembreMorale().getCodeMembreMorale(),
							new CalculBonInfo("r", "limite", bonInfo.getTypeProduit(), bonInfo.getDuree(), 0.0));
				}*/
			}

			if (StringUtils.isNotBlank(bonInfo.getTypeRecurrent())
					&& bonInfo.getTypeRecurrent().equalsIgnoreCase("illimite")) {
				EuKrr krr = new EuKrr();
				krr.setTypeKrr("krrBCRI");
				krr.setDateDemande(date);
				krr.setDateEchue(null);
				krr.setDateRenouveller(DateUtils.addDays(date, periode));
				krr.setEuMembre(membre);
				krr.setEuMembreMorale(null);
				krr.setEuProduit(produit);
				krr.setIdCredit(cc.getIdCredit());
				krr.setMontCapa(capa);
				krr.setMontKrr(mont_bcp * paramService.getParam("periode", "RBNP"));
				krr.setMontReconst(mont_bcp);
				krrService.add(krr);

				EuDetailKrr detKrr = new EuDetailKrr();
				detKrr.setAnnuler(0);
				detKrr.setIdCredit(cc.getIdCredit());
				detKrr.setIdKrr(krr.getIdKrr());
				detKrr.setMontCredit(mont_bcp);
				detKrr.setSourceCredit(cc.getSource());
				detailKrrService.add(detKrr);
			}

			if (codeProduit.equalsIgnoreCase("RPGr") && creditSqmax > 0) {

				compte.setSolde(compte.getSolde() + creditSqmax);
				compteService.update(compte);

				Long idSqmax = 1L;
				if (sqmaxService.count() > 0) {
					idSqmax += sqmaxService.findLastSQMaxIdInserted();
				}

				EuProduit produit_sqmax = new EuProduit();
				produit_sqmax.setCodeProduit(codeProduit);
				EuCompteCredit cc_sqmax = creditService.createCredit(compte, produit_sqmax, sqmax, creditSqmax, true,
						idSqmax.toString(), op, "SQMAXUI", membre.getCodeMembre(), bonInfo);
				if (cc_sqmax != null && credit <= 0) {
					creditService.updateListCapaTs(capatss, cc, capa, date, bonInfo.getCatBon());

					compteNn.setSolde(compteNn.getSolde() - capa);
					compteService.update(compteNn);
				} else {
					throw new RuntimeException("Echec de l'exécution : Création du bon de consommation RPG");
				}

				EuBnpSqmax bnp_sqmax = new EuBnpSqmax();
				bnp_sqmax.setIdSqmax(idSqmax);
				bnp_sqmax.setEuMembre(membre);
				bnp_sqmax.setMontant(sqmax);
				bnp_sqmax.setEuCategorieCompte(compte.getEuCategorieCompte());
				bnp_sqmax.setIdCredit(cc.getIdCredit());
				sqmaxService.add(bnp_sqmax);

				compteNn.setSolde(compteNn.getSolde() - sqmax);
				compteService.update(compteNn);
			}

			bon.setBonDateExpression(date);
			bon.setBonExprimer(1);
			bonService.update(bon);

			return true;
		}
		return false;
	}

	public boolean souscrireI(String codeMembre, EuCompte compte, EuCompte compteNn, String NumeroBon,
			List<EuCapaTs> capatss, String typeBon, CalculBonInfo bonInfo, double montant)
			throws DataAccessException, NullPointerException {

		double credit = 0;
		double capa = montant;

		EuBon bon = bonService.findByBonCode(NumeroBon, 0);
		if (bon == null) {
			return false;
		}

		String codeProduit = typeBon + bonInfo.getCatBon();
		// verification de mode d'achat du CAPA
		EuMembreMorale moral = compte.getEuMembreMorale();
		if (!Objects.nonNull(moral)) {
			moral = moraleService.findById(codeMembre);
		}

		// Verification du type de credit (recurrent ou non
		// recurrent) et calcul du montant de crdit
		if (bonInfo.getCatBon().equals("r")) {
			credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));
		} else {
			credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));
		}
		if (credit > 0) {

			EuCategorieCompte catCompte = new EuCategorieCompte("TPAGCI");
			Date date = new Date();
			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(catCompte);
			op.setCodeProduit(codeProduit);
			op.setDateOp(date);
			op.setLibOp("Achat de Bon de Consommation");
			op.setTypeOp("ABC");
			op.setMontantOp(capa);
			op.setEuMembreMorale(moral);
			op.setEuUtilisateur(null);
			op.setHeureOp(date);
			opService.add(op);

			compte.setSolde(compte.getSolde() + credit);
			compteService.update(compte);

			EuProduit produit = new EuProduit();
			String c_source = compteNn.getCodeCompte();
			produit.setCodeProduit(codeProduit);
			EuCompteCredit cc = creditService.createCredit(compte, produit, capa, credit, false, null, op, c_source,
					codeMembre, bonInfo);

			creditService.updateListCapaTs(capatss, cc, capa, date, bonInfo.getCatBon());

			compteNn.setSolde(compteNn.getSolde() - montant);
			compteService.update(compteNn);

			bon.setBonDateExpression(date);
			bon.setBonExprimer(1);
			bonService.update(bon);
			return true;
		} else {
			return false;
		}

	}

	public boolean souscrireCncs(String codeMembre, EuCompte compte, EuCompte compteNn, String NumeroBon,
			List<EuCapaTs> capats, String typeBon, CalculBonInfo bonInfo, double montant, EuUtilisateur user) {

		double credit = 0;
		double capa = montant;

		EuBon bon = bonService.findByBonCode(NumeroBon, 0);
		if (bon == null) {
			throw new CompteNonTrouveException("Le bon n° " + NumeroBon + " n'existe pas!");
		}

		String codeProduit = typeBon + bonInfo.getCatBon();
		// verification de mode d'achat du CAPA
		EuMembreMorale moral = compte.getEuMembreMorale();
		if (!Objects.nonNull(moral)) {
			moral = moraleService.findById(codeMembre);
		}
		EuActeursCreneaux act = acteurCreneauxService.getActeurCreneauByCodemembre(codeMembre);
		if (act == null) {
			throw new CompteNonTrouveException("Cet Acteur n'est pas mise ssur chaîne");
		}
		credit = Math.floor(creditUtility.calculBonConso(bonInfo, capa));

		double solde_smc = smcService.findSumByOrigineSmc();
		if (solde_smc >= montant) {
			Date date = new Date();
			String codeCompteTi = "NR-TPN-" + codeMembre;

			Long idOp = opService.getLastOperation() + 1;
			EuCategorieCompte codeCateg = new EuCategorieCompte();
			codeCateg.setCodeCat("TPN");

			EuOperation op = new EuOperation();
			op.setEuCategorieCompte(codeCateg);
			op.setCodeProduit("CNCSnr");
			op.setDateOp(date);
			op.setLibOp("CAPA CNCSnr");
			op.setTypeOp("APA");
			op.setIdOperation(idOp);
			op.setEuUtilisateur(user);
			op.setHeureOp(date);
			opService.add(op);

			EuCompte compteti = compteService.getById(codeCompteTi);
			if (compteti != null) {
				compteti.setSolde(compteti.getSolde() + montant);
				compteService.update(compteti);
			} else {
				compteti = new EuCompte();
				compteti.setSolde(montant);
				compteti.setCodeCompte(codeCompteTi);
				compteti.setDateAlloc(date);
				compteti.setEuMembreMorale(compteNn.getEuMembreMorale());
				compteti.setDesactiver("0");
				compteti.setLibCompte("TPN");
				EuTypeCompte typecompte = new EuTypeCompte();
				typecompte.setCodeTypeCompte("NR");
				compteti.setEuTypeCompte(typecompte);
				EuCategorieCompte catcompte = new EuCategorieCompte();
				catcompte.setCodeCat("TPN");
				catcompte.setCodeTypeCompte("NR");
				compteti.setEuCategorieCompte(catcompte);
				compteService.create(compteti);
			}

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Long idCredit = ccService.getLastCreditInsertedId();
			if (idCredit == null) {
				idCredit = 0L;
			}
			EuProduit produit = new EuProduit();
			produit.setCodeProduit(codeProduit);
			EuCompteCredit cc = new EuCompteCredit();
			cc.setIdCredit(idCredit + 1);
			cc.setCodeMembre(codeMembre);
			cc.setMontantPlace(montant);
			cc.setMontantCredit(credit);
			cc.setEuOperation(op);
			cc.setEuProduit(produit);
			cc.setCodeTypeCredit("CNCS");
			cc.setSource(codeMembre + dateFormatter.format(date));
			cc.setCompteSource(compteNn.getCodeCompte());
			cc.setDateOctroi(date);
			cc.setDatedeb(date);
			Date datefin = ServerUtil.ajouterJours(date, 30);
			cc.setDatefin(datefin);
			if (bonInfo.getCatBon().equals("nr")) {
				cc.setRenouveller("N");
				cc.setPrk(bonInfo.getPrk());
			} else {
				cc.setRenouveller("O");
				cc.setNbreRenouvel((int) Math.ceil(bonInfo.getDuree()));
				cc.setPrk(0.0);
			}
			cc.setBnp(0);
			cc.setKrr("N");
			cc.setDomicilier(0);
			cc.setEuCompte(compteti);
			cc.setNbreRenouvel(0);
			ccService.create(cc);

			double mont_capa = montant;
			int i = 0;
			while (mont_capa > 0 && i < capats.size()) {
				EuCapaTs c = capats.get(i);
				if (mont_capa > c.getMontantSolde()) {
					EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
					EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
					ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
					ccCapaPK.setIdCredit(cc.getIdCredit());
					ccCapa.setId(ccCapaPK);
					ccCapa.setCodeProduit(codeProduit);
					ccCapa.setMontant(c.getMontantSolde());
					cccapaService.add(ccCapa);

					mont_capa -= c.getMontantSolde();

					c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
					c.setMontantSolde(0d);
					capatsService.update(c);
					i++;

				} else {
					EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
					EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
					ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
					ccCapaPK.setIdCredit(cc.getIdCredit());
					ccCapa.setId(ccCapaPK);
					ccCapa.setCodeProduit(codeProduit);
					ccCapa.setMontant(mont_capa);
					cccapaService.add(ccCapa);

					c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
					c.setMontantSolde(c.getMontantSolde() - mont_capa);
					capatsService.update(c);
					mont_capa = 0;
				}
			}

			List<EuSmc> smcs = smcService.findByOrigineSmc();
			if (Objects.nonNull(smcs) && smcs.size() > 0) {
				double mont_smc = credit;
				int c = 0;
				while (mont_smc > 0 && i < smcs.size()) {
					EuSmc smc = smcs.get(c);
					if (smc.getMontantSolde() > mont_smc) {
						smc.setSortie(smc.getSortie() + mont_smc);
						smc.setSolde(smc.getSolde() + mont_smc);
						smc.setMontantSolde(smc.getMontantSolde() - mont_smc);
						smcService.update(smc);

						Long idUtiliser = utliliserService.findLastUtiliserInsertedId();
						if (idUtiliser != null) {
							idUtiliser++;
						} else {
							idUtiliser = 1L;
						}
						EuUtiliser utiliser = new EuUtiliser();
						utiliser.setIdUtiliser(idUtiliser);
						utiliser.setEuSmc(smc);
						utiliser.setMontantAllouer(mont_smc);
						utiliser.setEuSmcipnpwi(null);
						utiliser.setIdCredit(idCredit);
						utiliser.setDateCreation(date);
						utliliserService.add(utiliser);

						mont_smc = 0;
					} else {
						mont_smc -= smc.getMontantSolde();

						Long idUtiliser = utliliserService.findLastUtiliserInsertedId();
						if (idUtiliser != null) {
							idUtiliser++;
						} else {
							idUtiliser = 1L;
						}
						EuUtiliser utiliser = new EuUtiliser();
						utiliser.setIdUtiliser(idUtiliser);
						utiliser.setEuSmc(smc);
						utiliser.setMontantAllouer(smc.getMontantSolde());
						utiliser.setEuSmcipnpwi(null);
						utiliser.setIdCredit(idCredit);
						utiliser.setDateCreation(date);
						utliliserService.add(utiliser);

						smc.setSortie(smc.getSortie() + smc.getMontantSolde());
						smc.setSolde(smc.getSolde() + smc.getMontantSolde());
						smc.setMontantSolde(0);
						smcService.update(smc);

						i++;
					}
				}
			}
			compteNn.setSolde(compteNn.getSolde() - montant);
			compteService.update(compteNn);

			bon.setBonDateExpression(date);
			bon.setBonExprimer(1);
			bonService.update(bon);

			return true;
		} else {
			throw new SoldeInsuffisantException(
					"Le solde de la source SMC est insuffisant pour effectuer cette transaction");
		}
	}
}
