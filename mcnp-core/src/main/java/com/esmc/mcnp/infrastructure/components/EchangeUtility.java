package com.esmc.mcnp.infrastructure.components;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.infrastructure.services.ba.EuCapaService;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.obpsd.EuNnService;
import com.esmc.mcnp.infrastructure.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.infrastructure.services.obpsd.EuTraiteService;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteGeneralRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.common.EuOperationRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcscRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuEchangeRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuNnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuSmcipnwiRepository;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.ba.EuNn;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneral;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneralPK;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.domain.entity.obpsd.EuTypeNn;
import com.esmc.mcnp.domain.entity.obpsd.EuUtiliserNn;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.others.EuOperation;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuGcsc;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.google.common.collect.Maps;

/**
 * Created by USER on 07/11/2016.
 */
@Component
public class EchangeUtility {

	private final EuTraiteService traiteService;
	private final EuTpagcpService tpagcpService;
	private final EuCompteService compteService;
	private final EuCapaService capaService;
	private final EuNnService nnService;
	private final EuUtiliserNnRepository utiliserNnRepo;
	private final EuMembreService membreService;
	private final EuMembreMoraleService moraleService;
	private final EuCompteRepository compteRepo;
	private final EuSmcipnwiRepository smcipnRepo;
	private final EuGcscRepository gcscRepo;
	private final EuAppelOffreRepository offreRepo;
	private final EuCompteCreditRepository creditRepo;
	private final EuNnRepository nnRepo;
	private final EuCompteGeneralRepository cgDao;
	private final EuEchangeRepository echRepo;
	private final EuOperationRepository opRepo;
	private final CreditComponent creditService;

	public EchangeUtility(EuTraiteService traiteService, EuTpagcpService tpagcpService,
						  EuCompteService compteService, EuCapaService capaService,
						  EuNnService nnService, EuUtiliserNnRepository utiliserNnRepo,
						  EuMembreService membreService, EuMembreMoraleService moraleService,
						  EuCompteRepository compteRepo, EuSmcipnwiRepository smcipnRepo,
						  EuGcscRepository gcscRepo, EuAppelOffreRepository offreRepo,
						  EuCompteCreditRepository creditRepo, EuNnRepository nnRepo,
						  EuCompteGeneralRepository cgDao, EuEchangeRepository echRepo,
						  EuOperationRepository opRepo, CreditComponent creditService) {
		this.traiteService = traiteService;
		this.tpagcpService = tpagcpService;
		this.compteService = compteService;
		this.capaService = capaService;
		this.nnService = nnService;
		this.utiliserNnRepo = utiliserNnRepo;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.compteRepo = compteRepo;
		this.smcipnRepo = smcipnRepo;
		this.gcscRepo = gcscRepo;
		this.offreRepo = offreRepo;
		this.creditRepo = creditRepo;
		this.nnRepo = nnRepo;
		this.cgDao = cgDao;
		this.echRepo = echRepo;
		this.opRepo = opRepo;
		this.creditService = creditService;
	}

	@Transactional
	public boolean echangeOpi(String codeMembre, List<Long> tpagcps, double montant) {
		if (montant > 0 && tpagcps.size() > 0) {
			Double sumsolde = tpagcpService.getSumSoldeByIds(tpagcps);
			if (sumsolde >= montant) {
				List<EuTraite> traites = traiteService.findByTpagcps(tpagcps);
				int i = 0;
				double mont_deduit = 0;
				Map<Long, Integer> nbres = Maps.newHashMap();
				Map<Long, Double> msolde = Maps.newHashMap();
				while (i < traites.size() && mont_deduit < montant) {
					EuTraite traite = traites.get(i);
					traite.setTraiteEscompteNature(1);
					traite.setTraitePayer(1);
					msolde.merge(traite.getTraiteTegcp(), traite.getTraiteMontant(), Double::sum);
					nbres.merge(traite.getTraiteTegcp(), 1, Integer::sum);
					mont_deduit += traite.getTraiteMontant();
					traiteService.update(traite);
					i++;
				}
				msolde.forEach((k, v) -> {
					EuTpagcp tpagcp = tpagcpService.findById(k);
					tpagcp.setMontEchange(tpagcp.getMontEchange() + v);
					tpagcp.setSolde(tpagcp.getSolde() - v);
					tpagcp.setResteNtf(tpagcp.getResteNtf() - nbres.get(k));
					tpagcpService.update(tpagcp);
				});

				Date date = new Date();
				String typeNn = "INR";
				String typeCapa = "I";
				if (codeMembre.endsWith("P")) {
					typeNn = "RPGNR";
					typeCapa = "RPG";
				}
				nnService.EmettreNn(typeNn, mont_deduit, date);
				EuUtiliserNn utilNn = new EuUtiliserNn();
				utilNn.setCodeMembreNb(null);
				utilNn.setCodeMembreNn(codeMembre);
				utilNn.setCodeProduit(typeNn);
				utilNn.setCodeProduitNn("OPI");
				utilNn.setCodeSms(null);
				utilNn.setDateTransfert(date);
				utilNn.setIdOperation(null);
				utilNn.setIdUtilisateur(null);
				utilNn.setMontTransfert(mont_deduit);
				utilNn.setNumBon(null);
				utilNn.setMotif("Transfert automatique: Escompte en Nature d'OPI");
				utiliserNnRepo.save(utilNn);

				String codeCompteDest = "NN-CAPA-" + codeMembre;
				EuCompte compteDest = compteService.getById(codeCompteDest);
				if (Objects.nonNull(compteDest)) {
					compteDest.setSolde(compteDest.getSolde() + mont_deduit);
					compteService.update(compteDest);
				} else {
					compteDest = new EuCompte();
					EuCategorieCompte cat = new EuCategorieCompte();
					cat.setCodeCat("CAPA");
					EuTypeCompte typeCompte = new EuTypeCompte();
					typeCompte.setCodeTypeCompte("NN");
					compteDest.setCodeCompte(codeCompteDest);
					compteDest.setEuCategorieCompte(cat);
					compteDest.setEuTypeCompte(typeCompte);
					compteDest.setLibCompte(cat.getCodeCat());
					if (codeMembre.endsWith("P")) {
						compteDest.setEuMembre(membreService.findById(codeMembre));
					} else {
						compteDest.setEuMembreMorale(moraleService.findById(codeMembre));
					}
					compteDest.setDesactiver("N");
					compteDest.setSolde(mont_deduit);
					compteDest.setDateAlloc(date);
					compteService.create(compteDest);
				}

				EuCapa capa = new EuCapa();
				capa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
				capa.setDateCapa(date);
				capa.setCodeMembre(codeMembre);
				capa.setCodeProduit(typeCapa);
				capa.setTypeCapa(typeCapa);
				capa.setOrigineCapa("OPI");
				capa.setMontantCapa(mont_deduit);
				capa.setMontantUtiliser(0);
				capa.setMontantSolde(mont_deduit);
				capa.setEuCompte(compteDest);
				capa.setEtatCapa("Actif");
				capa.setIdOperation(null);
				capa.setHeureCapa(date);
				capaService.add(capa);
				return true;
			}
		}
		return false;
	}

	@Transactional
	public boolean echangeOpiCommandeNr(String codeMembre, List<EuTraite> traites, double montant) {
		System.out.println("Echange d'OPI en BAi du membre " + codeMembre + " d'une valeur de " + montant);
		if (montant > 0 && traites.size() > 0) {
			int i = 0;
			double mont_deduit = 0;
			Map<Long, Integer> nbres = Maps.newHashMap();
			Map<Long, Double> msolde = Maps.newHashMap();
			while (i < traites.size() && mont_deduit < montant) {
				EuTraite traite = traites.get(i);
				traite.setTraiteEscompteNature(1);
				traite.setTraitePayer(1);
				msolde.merge(traite.getTraiteTegcp(), traite.getTraiteMontant(), Double::sum);
				nbres.merge(traite.getTraiteTegcp(), 1, Integer::sum);
				mont_deduit += traite.getTraiteMontant();
				traiteService.update(traite);
				i++;
			}
			msolde.forEach((k, v) -> {
				EuTpagcp tpagcp = tpagcpService.findById(k);
				tpagcp.setMontEchange(tpagcp.getMontEchange() + v);
				tpagcp.setSolde(tpagcp.getSolde() - v);
				tpagcp.setResteNtf(tpagcp.getResteNtf() - nbres.get(k));
				tpagcpService.update(tpagcp);
			});

			Date date = new Date();
			String typeNn = "Inr";
			String typeCapa = "BAi";
			if (codeMembre.endsWith("P")) {
				typeNn = "RPGnr";
				typeCapa = "BAi";
			}
			nnService.EmettreNn(typeNn, mont_deduit, date);
			EuUtiliserNn utilNn = new EuUtiliserNn();
			utilNn.setCodeMembreNb(null);
			utilNn.setCodeMembreNn(codeMembre);
			utilNn.setCodeProduit(typeNn);
			utilNn.setCodeProduitNn("OPI");
			utilNn.setCodeSms(null);
			utilNn.setDateTransfert(date);
			utilNn.setIdOperation(null);
			utilNn.setIdUtilisateur(null);
			utilNn.setMontTransfert(mont_deduit);
			utilNn.setNumBon(null);
			utilNn.setMotif("Transfert automatique: Escompte en Nature d'OPI");
			utiliserNnRepo.save(utilNn);

			System.out.println("Création ou mise à jour du compte CAPA");
			String codeCompteDest = "NN-CAPA-" + codeMembre;
			EuCompte compteDest = compteService.getById(codeCompteDest);
			if (Objects.nonNull(compteDest)) {
				compteDest.setSolde(compteDest.getSolde() + mont_deduit);
				compteService.update(compteDest);
			} else {
				compteDest = new EuCompte();
				EuCategorieCompte cat = new EuCategorieCompte();
				cat.setCodeCat("CAPA");
				EuTypeCompte typeCompte = new EuTypeCompte();
				typeCompte.setCodeTypeCompte("NN");
				compteDest.setCodeCompte(codeCompteDest);
				compteDest.setEuCategorieCompte(cat);
				compteDest.setEuTypeCompte(typeCompte);
				compteDest.setLibCompte(cat.getCodeCat());
				if (codeMembre.endsWith("P")) {
					compteDest.setEuMembre(membreService.findById(codeMembre));
				} else {
					compteDest.setEuMembreMorale(moraleService.findById(codeMembre));
				}
				compteDest.setDesactiver("N");
				compteDest.setSolde(mont_deduit);
				compteDest.setDateAlloc(date);
				compteService.create(compteDest);
			}

			System.out.println("Création d'une ligne de CAPA");
			EuCapa capa = new EuCapa();
			capa.setCodeCapa("CAPA" + ServerUtil.formatDate2(date));
			capa.setDateCapa(date);
			capa.setCodeMembre(codeMembre);
			capa.setCodeProduit(typeCapa);
			capa.setTypeCapa(typeCapa);
			capa.setOrigineCapa("OPI");
			capa.setMontantCapa(mont_deduit);
			capa.setMontantUtiliser(0);
			capa.setMontantSolde(mont_deduit);
			capa.setEuCompte(compteDest);
			capa.setEtatCapa("Actif");
			capa.setIdOperation(null);
			capa.setHeureCapa(date);
			capaService.add(capa);
			return true;
		}
		return false;
	}

	public EuEchange echangeNRNN(String codeMembre, String codeMembreDest, String numAppelOffre, String typeProduit,
			double montant, Long idUtilisateur) {
		Date date = new Date();
		String codeCategorie = StringUtils.EMPTY;
		String codeCompte = StringUtils.EMPTY;
		String codeProduit = StringUtils.EMPTY;
		String codeTypeNn;
		if (codeMembre.endsWith("M")) {
			codeCompte = "NR-TI-" + codeMembre;
			codeCategorie = "TI";
			codeTypeNn = "BAI";
		} else {
			codeCompte = "NR-TCNCS-" + codeMembre;
			codeCategorie = "TCNCS";
			codeTypeNn = "BAI";
		}
		if (codeMembreDest.endsWith("P")) {
			codeProduit = "RPG";
		} else {
			codeProduit = "I";
		}
		// Récupération du SMCIPN correspondant au numéro d'appel d'offre
		EuSmcipnpwi smcipn = smcipnRepo.findByNumeroAppel(numAppelOffre);
		if (smcipn != null) {
			// Récupération du GCsc et del'appel d'offre
			EuGcsc gcsc = gcscRepo.findByEuSmcipn_CodeSmcipn(smcipn.getCodeSmcipn());
			EuAppelOffre offre = offreRepo.findAppelOffresByNumero(smcipn.getNumeroAppel());
			if (gcsc == null && offre.getTypeAppelOffre().equals("inrpre")) {
				throw new IllegalStateException("le TEGCSC de remboursement du SMCIPN n'est pas défini!");
			}
			String codeCompteOrig = codeCompte;
			EuCompte compteOrigine = compteRepo.findOne(codeCompteOrig);
			if (compteOrigine != null && compteOrigine.getSolde() >= montant) {
				String codeCompteDest = "NN-CAPA-" + codeMembreDest;
				List<EuCompteCredit> credit_s = creditRepo.findByEuCompte_CodeCompte(compteOrigine.getCodeCompte());
				if (credit_s.isEmpty()) {
					throw new CompteNonIntegreException("Il n'y a pas de crédits correspondant à ce compte");
				} else {
					double somme_credit = 0;
					somme_credit = credit_s.stream().mapToDouble((c) -> c.getMontantCredit()).sum();
					if (compteOrigine.getSolde() != somme_credit) {
						throw new CompteNonIntegreException("Il n'y a pas de crédits correspondant à ce compte");
					}
				}
				try {
					List<EuCompteCredit> credits = creditRepo.findByCompteandSource(compteOrigine.getCodeCompte(),
							smcipn.getCodeSmcipn());
					if (credits.isEmpty()) {
						throw new CompteNonIntegreException("Il n'y a pas de crédits correspondant à ce compte");
					} else {
						double somme_credit = 0;
						somme_credit = credits.stream().map((c) -> c.getMontantCredit()).reduce(somme_credit,
								(accumulator, _item) -> accumulator + _item);
						if (montant > somme_credit) {
							throw new CompteNonIntegreException("Le solde de votre compte est insuffisant!!!");
						}
					}

					// Création du NN à utiliser à la source NN
					EuTypeNn typeNn = new EuTypeNn();
					typeNn.setCodeTypeNn(codeTypeNn);
					EuNn eunn = new EuNn();
					eunn.setDateEmission(date);
					eunn.setEuMembreMorale(null);
					eunn.setEuTypeNn(typeNn);
					eunn.setMontantEmis(montant);
					eunn.setMontantRemb(montant);
					eunn.setSoldeNn(0.0);
					eunn.setTypeEmission("Auto");
					nnRepo.save(eunn);

					// Mise à jour du compte général NN
					EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
					cgPK.setCodeCompte("FGS" + codeProduit);
					cgPK.setCodeTypeCompte("NN");
					cgPK.setService("E");
					EuCompteGeneral cg = cgDao.findOne(cgPK);
					if (cg != null) {
						cg.setSolde(cg.getSolde() + montant);
						cgDao.save(cg);
					} else {
						cg = new EuCompteGeneral();
						cg.setId(cgPK);
						cg.setIntitule("FG Source " + codeProduit);
						cg.setSolde(montant);
						cgDao.save(cg);
					}

					// Création de l'opération d'échange
					Long id_echange = echRepo.findLastEchangeInsertedId();
					if (id_echange == null) {
						id_echange = 0L;
					}
					EuEchange ech = new EuEchange();
					ech.setIdEchange(id_echange + 1);
					ech.setCatEchange(codeProduit);
					ech.setTypeEchange("NR/NN");
					ech.setMontantEchange(montant);
					ech.setMontant(montant);
					ech.setCompenser(0);
					ech.setRegler(0);
					ech.setEuCompte(compteOrigine);
					ech.setCodeCompteObt(codeCompteDest);
					ech.setDateEchange(date);
					ech.setDateReglement(date);
					if (codeMembre.endsWith("M")) {
						ech.setEuMembreMorale(compteOrigine.getEuMembreMorale());
						ech.setEuMembre(null);
					} else {
						ech.setEuMembreMorale(null);
						ech.setEuMembre(compteOrigine.getEuMembre());
					}
					ech.setCodeProduit(codeProduit);
					ech.setAgio(0);
					ech = echRepo.save(ech);

					Long idOp = opRepo.getLastOperationInsertedId();
					if (idOp == null) {
						idOp = 0L;
					}

					EuCategorieCompte categorie = new EuCategorieCompte();
					categorie.setCodeCat(codeCategorie);

					EuOperation op = new EuOperation();
					op.setEuCategorieCompte(categorie);
					op.setCodeProduit(codeProduit);
					op.setDateOp(date);
					op.setLibOp("Echange de " + codeCategorie);
					op.setTypeOp("E");
					op.setMontantOp(montant);
					if (codeMembre.endsWith("P")) {
						op.setEuMembre(compteOrigine.getEuMembre());
					} else {
						op.setEuMembreMorale(compteOrigine.getEuMembreMorale());
					}
					op.setIdOperation(idOp + 1);
					EuUtilisateur user = new EuUtilisateur();
					if (idUtilisateur != null) {
						user.setIdUtilisateur(idUtilisateur);
						op.setEuUtilisateur(user);
					}
					op.setHeureOp(date);
					opRepo.save(op);

					// Mise à jour des FNs
					creditService.updateListFns(credits, op, montant);

					// Mise à jour du compte du crédit échangé
					compteOrigine.setSolde(compteOrigine.getSolde() - montant);
					compteRepo.save(compteOrigine);
					return ech;
				} catch (Exception e) {
					throw e;
				}
			} else {
				throw new CompteNonIntegreException("Le solde de votre compte est insuffisant!!!");
			}
		} else {
			throw new IllegalStateException(
					"Echec de l'opération : Il n'y a pas de SMCIPN associé à cet appel d'offre!!!");
		}
	}

}
