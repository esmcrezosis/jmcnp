package com.esmc.mcnp.infrastructure.services.pc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dao.repository.ba.EuCapaRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuAncienCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuAncienCompteRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuRepartitionMf107Repository;
import com.esmc.mcnp.dao.repository.cmfh.EuRepartitionMf11000Repository;
import com.esmc.mcnp.dao.repository.obpsd.PlaceRepository;
import com.esmc.mcnp.dao.repository.others.EuTegcRepository;
import com.esmc.mcnp.dao.repository.pc.EuRecouvrementMcnpRepository;
import com.esmc.mcnp.dao.repository.pc.EuRecouvrementRepository;
import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompte;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf107;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf11000;
import com.esmc.mcnp.domain.entity.obps.EuAncienGcp;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.obpsd.Place;
import com.esmc.mcnp.domain.entity.pc.EuRecouvrement;
import com.esmc.mcnp.domain.entity.pc.EuRecouvrementMcnp;
import com.esmc.mcnp.domain.entity.pc.EuReleve;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.infrastructure.components.EchangeUtility;
import com.esmc.mcnp.infrastructure.components.SmcipnComponent;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;
import com.esmc.mcnp.infrastructure.services.obps.EuAncienGcpService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcipnpwiService;
import com.google.common.collect.Lists;

@Service("euRecouvrementMcnpService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRecouvrementMcnpServiceImpl extends BaseServiceImpl<EuRecouvrementMcnp, Integer>
		implements EuRecouvrementMcnpService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final EuRecouvrementMcnpRepository recouvMcnpRepo;
	private final EuRecouvrementRepository recRepo;
	private EuReleveService releveService;
	private EuAncienCompteRepository ancCompteRepo;
	private EuAncienCompteCreditRepository ancCCRepo;
	private EuAncienGcpService ancGcpService;
	private SmcipnComponent smcipnComponent;
	private EchangeUtility echangeUtility;
	private EuSmcipnpwiService smcipnService;
	private EuCompteRepository compteRepo;
	private EuCapaRepository capaRepo;
	private EuRepartitionMf107Repository repartMf107Repo;
	private EuRepartitionMf11000Repository repartMf11000Repo;
	private EuTegcRepository tegcRepo;
	private final PlaceRepository placeRepository;

	public EuRecouvrementMcnpServiceImpl(EuRecouvrementMcnpRepository recouvMcnpRepo, EuRecouvrementRepository recRepo,
			EuReleveService releveService, EuAncienCompteRepository ancCompteRepo,
			EuAncienCompteCreditRepository ancCCRepo, EuAncienGcpService ancGcpService, SmcipnComponent smcipnComponent,
			EchangeUtility echangeUtility, EuSmcipnpwiService smcipnService, EuCompteRepository compteRepo,
			EuCapaRepository capaRepo, EuRepartitionMf107Repository repartMf107Repo,
			EuRepartitionMf11000Repository repartMf11000Repo, EuTegcRepository tegcRepo,
			PlaceRepository placeRepository) {
		this.recouvMcnpRepo = recouvMcnpRepo;
		this.recRepo = recRepo;
		this.releveService = releveService;
		this.ancCompteRepo = ancCompteRepo;
		this.ancCCRepo = ancCCRepo;
		this.ancGcpService = ancGcpService;
		this.smcipnComponent = smcipnComponent;
		this.echangeUtility = echangeUtility;
		this.smcipnService = smcipnService;
		this.compteRepo = compteRepo;
		this.capaRepo = capaRepo;
		this.repartMf107Repo = repartMf107Repo;
		this.repartMf11000Repo = repartMf11000Repo;
		this.tegcRepo = tegcRepo;
		this.placeRepository = placeRepository;
	}

	@Override
	protected BaseRepository<EuRecouvrementMcnp, Integer> getRepository() {
		return recouvMcnpRepo;
	}

	@Override
	public String recouvrerMcnp(Integer idReleve) {
		if (idReleve != null) {
			EuReleve releve = releveService.getById(idReleve);
			if (Objects.nonNull(releve)) {
				return recouvrerMcnp(releve);
			}
		}
		return "Le relevé " + idReleve + " n'existe pas";
	}

	@Override
	@Transactional(readOnly = false)
	public String recouvrerMcnp(String codeMembre, String newCodeMembre, String type) {
		StringBuilder result = new StringBuilder();
		if (StringUtils.isNotBlank(newCodeMembre) && StringUtils.isNotBlank(type)) {
			List<EuReleve> releves = releveService.findByMembreAndType(newCodeMembre, type, 1);
			if (!releves.isEmpty()) {
				for (EuReleve releve : releves) {
					List<EuRecouvrementMcnp> oldRecMcnps = recouvMcnpRepo.findByIdReleve(releve.getReleveId());
					if (oldRecMcnps.isEmpty()) {
						double mont_a_recouvrer = releveService.verifierRelever("MCNP", codeMembre, type, 1);
						if (mont_a_recouvrer > 0) {
							if ("CNP".equals(type)) {
								if (codeMembre.endsWith("P")) {
									result.append(recouvrerCnp(releve, newCodeMembre, "RPG"));
								} else {
									result.append(recouvrerCnp(releve, newCodeMembre, "I"));
								}
							} else if ("GCP".equals(type)) {
								result.append(recouvrerGcp(releve, newCodeMembre, type));
							} else if ("MF11000".equals(type) || "MF107".equals(type)) {
								result.append(recouvrerMf(releve, newCodeMembre, type));
							} else {
								result.append(recouvrerCncs(releve, newCodeMembre, type));
							}
						} else {
							result.append("Le releve ").append(releve.getReleveId()).append(" n'est pas correct");
						}
					} else {
						result.append("Ce releve ").append(releve.getReleveId()).append(" est déjà remboursé");
					}
				}
			} else {
				result.append("Pas de relevés de recouvrement existant pour ce membre ").append(newCodeMembre);
			}
		} else {
			result.append("Veuillez fournir les infos nécéssaires pour cette opération");
		}
		return result.toString();
	}

	@Override
	@Transactional(readOnly = false)
	public String recouvrerMcnp(EuReleve releve) {
		if (Objects.nonNull(releve)) {
			List<EuRecouvrementMcnp> oldRecMcnps = recouvMcnpRepo.findByIdReleve(releve.getReleveId());
			if (oldRecMcnps.isEmpty()) {
				double mont_a_recouvrer = releveService.verifierRelever(releve);
				if (mont_a_recouvrer > 0) {
					if ("CNP".equals(releve.getReleveType())) {
						if (releve.getReleveMembre().endsWith("P")) {
							return recouvrerCnp(releve, releve.getNewCodeMembre(), "RPG");
						} else {
							return recouvrerCnp(releve, releve.getNewCodeMembre(), "I");
						}
					} else if ("GCP".equals(releve.getReleveType())) {
						return recouvrerGcp(releve, releve.getNewCodeMembre(), releve.getReleveType());
					} else if ("MF11000".equals(releve.getReleveType()) || "MF107".equals(releve.getReleveType())) {
						return recouvrerMf(releve, releve.getNewCodeMembre(), releve.getReleveType());
					} else {
						return recouvrerCncs(releve, releve.getNewCodeMembre(), releve.getReleveType());
					}
				} else {
					return "Le releve " + releve.getReleveId() + " n'est pas correct";
				}
			} else {
				return "Ce releve " + releve.getReleveId() + " est déjà remboursé";
			}
		}
		return "Le relevé n'existe pas";
	}

	@Transactional(readOnly = false)
	public String recouvrerCnp(EuReleve releve, String newCodeMembre, String ressource) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String codeMembreDebiteur = "0010010010010000212M";
		try {
			List<EuAncienCompteCredit> ancCompteCredits = null;
			List<Place> placements = null;
			double mont_recouvrement = 0;
			if (releve.getReleveMembre().length() == 20) {
				String ancCodeCompte = "";
				if (newCodeMembre.endsWith("P")) {
					ancCodeCompte = "NB-TPAGCRPG-" + releve.getReleveMembre();
				} else {
					ancCodeCompte = "NB-TPAGCI-" + releve.getReleveMembre();
				}
				ancCompteCredits = ancCCRepo.findByCodeCompteAndRembourser(ancCodeCompte, false);
				if (!ancCompteCredits.isEmpty()) {
					Double somCreditnr = ancCompteCredits.stream()
							.filter(c -> c.getCodeProduit().equals(ressource + "nr"))
							.mapToDouble(EuAncienCompteCredit::getMontantCredit).sum();
					Double somCapar = ancCompteCredits.stream().filter(c -> c.getCodeProduit().equals(ressource + "r"))
							.mapToDouble(EuAncienCompteCredit::getMontantPlace).sum();
					Double somCapanr = (somCreditnr * 5.6) / 8;
					mont_recouvrement = somCapar + somCapanr;
				}
			} else {
				if ("CNP".equals(releve.getReleveType())) {
					placements = placeRepository.findByMembreAndLibLikeAndRembourser(releve.getReleveMembre(), releve.getReleveType() + "%", false);
				} else {
					placements = placeRepository.findByMembreAndLibAndRembourser(releve.getReleveMembre(), releve.getReleveType(), false);
				}
				if (placements.size() > 0) {
					mont_recouvrement = placements.stream().mapToDouble(p -> Double.parseDouble(p.getMontant())).sum();
				}
			}
			if (mont_recouvrement > 0) {
				EuTegc tegc = tegcRepo.findByCodeMembre(codeMembreDebiteur).get(0);
				String numAppelOffre = smcipnComponent.doSmcipnCharge(codeMembreDebiteur, tegc.getCodeTegc(),
						"Reglement Passif " + ressource + " du " + newCodeMembre, mont_recouvrement);
				EuEchange ech = echangeUtility.echangeNRNN(codeMembreDebiteur, newCodeMembre, numAppelOffre, ressource,
						mont_recouvrement, null);

				// Création ou Mise à jour du compte du crédit à obtenir
				EuCompte compteDest = compteRepo.findOne(ech.getCodeCompteObt());
				EuTypeCompte typeCompte = new EuTypeCompte();
				EuCategorieCompte cat = new EuCategorieCompte();
				if (compteDest != null) {
					compteDest.setSolde(compteDest.getSolde() + ech.getMontant());
					compteRepo.save(compteDest);
				} else {
					cat.setCodeCat("CAPA");
					typeCompte.setCodeTypeCompte("NN");

					compteDest = new EuCompte();
					compteDest.setCodeCompte(ech.getCodeCompteObt());
					compteDest.setDateAlloc(date);
					compteDest.setDesactiver("N");
					compteDest.setEuCategorieCompte(cat);
					if (newCodeMembre.endsWith("M")) {
						EuMembreMorale moral = new EuMembreMorale();
						moral.setCodeMembreMorale(newCodeMembre);
						compteDest.setEuMembreMorale(moral);
						compteDest.setEuMembre(null);
					} else {
						EuMembre membre = new EuMembre();
						membre.setCodeMembre(newCodeMembre);
						compteDest.setEuMembre(membre);
						compteDest.setEuMembreMorale(null);
					}
					compteDest.setSolde(ech.getMontant());
					compteDest.setEuTypeCompte(typeCompte);
					compteDest.setLibCompte("Bon d'Achat Interne");
					compteDest.setCardprinteddate(null);
					compteDest.setCardprintediddate(0);
					compteDest.setMifarecard(null);
					compteDest.setNumeroCarte(null);
					compteRepo.save(compteDest);
				}

				EuCapa eucapa = new EuCapa();
				eucapa.setCodeCapa("BAi" + formatter.format(date));
				eucapa.setDateCapa(date);
				eucapa.setCodeMembre(newCodeMembre);
				eucapa.setCodeProduit(ressource);
				eucapa.setTypeCapa("BAI");
				eucapa.setOrigineCapa("BC");
				eucapa.setMontantCapa(ech.getMontant());
				eucapa.setMontantUtiliser(0);
				eucapa.setMontantSolde(ech.getMontant());
				eucapa.setEuCompte(compteDest);
				eucapa.setEtatCapa("Actif");
				eucapa.setIdOperation(null);
				eucapa.setHeureCapa(date);
				capaRepo.save(eucapa);

				EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
				EuRecouvrementMcnp recMcnp = new EuRecouvrementMcnp();
				recMcnp.setCodeSmcipn(smcipn.getCodeSmcipn());
				recMcnp.setDateRecouvrement(date);
				recMcnp.setIdEchange(ech.getIdEchange());
				recMcnp.setIdReleve(releve.getReleveId());
				recMcnp.setIdUtilisateur(null);
				recMcnp.setMontRecouvrement(mont_recouvrement);
				recMcnp.setTypeRessource(ressource);
				recMcnp.setOldCodeMembre(releve.getReleveMembre());
				recMcnp.setNewCodeMembre(newCodeMembre);
				recouvMcnpRepo.save(recMcnp);

				if (!placements.isEmpty()) {
					placements.forEach(p -> p.setRembourser(true));
					placeRepository.saveAll(placements);
				} else {
					ancCompteCredits.forEach(c -> c.setRembourser(true));
					ancCCRepo.saveAll(ancCompteCredits);
				}

				releve.setPublier(2);
				if (!releve.getTraiter()) {
					releve.setTraiter(true);
				}
				releveService.update(releve);

				return "Recouvrement du releve " + releve.getReleveId() + " : Operation effectuée avec succès";
			} else {
				return "Le Recouvrement du releve " + releve.getReleveId()
						+ " a échoué : Pas de CNP correspondant ou les CNP sont déjà recouvrés";
			}
		} catch (Exception e) {
			return "Echec du recouvrement du relevé " + releve.getReleveId() + " : " + e.getMessage();
		}
	}

	@Transactional
	public String recouvrerCncs(EuReleve releve, String newCodeMembre, String ressource) {
		EuAncienCompte ancCompte;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String codeMembreDebiteur = "0010010010010000212M";
		String codeCompte = "";
		if (newCodeMembre.endsWith("P")) {
			codeCompte = "NN-TCNCS-" + releve.getReleveMembre();
		} else {
			codeCompte = "NR-TCNCSEI-" + releve.getReleveMembre();
		}
		ancCompte = ancCompteRepo.findOne(codeCompte);
		if (Objects.nonNull(ancCompte) && ancCompte.getSolde() > 0) {
			try {
				if (releve.getReleveMembre().endsWith("M")) {
					double somCredit = ancCCRepo.getCompteSumCreditByCodeMembre(releve.getReleveMembre(), "CNCS%")
							.orElse(0.0);
					if (somCredit != ancCompte.getSolde()) {
						return "Echec du recouvrement du relevé " + releve.getReleveId() + " : Solde non conforme";
					}
				}
				double mont_recouvrement = ancCompte.getSolde();
				EuTegc tegc = tegcRepo.findByCodeMembre(codeMembreDebiteur).get(0);
				String numAppelOffre = smcipnComponent.doSmcipnCharge(codeMembreDebiteur, tegc.getCodeTegc(),
						"Reglement Passif " + ressource + " du " + newCodeMembre, mont_recouvrement);
				EuEchange ech = echangeUtility.echangeNRNN(codeMembreDebiteur, newCodeMembre, numAppelOffre, ressource,
						mont_recouvrement, null);

				// Création ou Mise à jour du compte du crédit à obtenir
				EuCompte compteDest = compteRepo.findOne(ech.getCodeCompteObt());
				EuTypeCompte typeCompte = new EuTypeCompte();
				EuCategorieCompte cat = new EuCategorieCompte();
				if (compteDest != null) {
					compteDest.setSolde(compteDest.getSolde() + ech.getMontant());
					compteRepo.save(compteDest);
				} else {
					cat.setCodeCat("CAPA");
					typeCompte.setCodeTypeCompte("NN");

					compteDest = new EuCompte();
					compteDest.setCodeCompte(ech.getCodeCompteObt());
					compteDest.setDateAlloc(date);
					compteDest.setDesactiver("N");
					compteDest.setEuCategorieCompte(cat);
					if (newCodeMembre.endsWith("M")) {
						EuMembreMorale moral = new EuMembreMorale();
						moral.setCodeMembreMorale(newCodeMembre);
						compteDest.setEuMembreMorale(moral);
						compteDest.setEuMembre(null);
					} else {
						EuMembre membre = new EuMembre();
						membre.setCodeMembre(newCodeMembre);
						compteDest.setEuMembre(membre);
						compteDest.setEuMembreMorale(null);
					}
					compteDest.setSolde(ech.getMontant());
					compteDest.setEuTypeCompte(typeCompte);
					compteDest.setLibCompte("Bon d'Achat Interne");
					compteDest.setCardprinteddate(null);
					compteDest.setCardprintediddate(0);
					compteDest.setMifarecard(null);
					compteDest.setNumeroCarte(null);
					compteRepo.save(compteDest);
				}

				EuCapa eucapa = new EuCapa();
				eucapa.setCodeCapa("BAi" + formatter.format(date));
				eucapa.setDateCapa(date);
				eucapa.setCodeMembre(newCodeMembre);
				eucapa.setCodeProduit(ressource);
				eucapa.setTypeCapa("BAI");
				eucapa.setOrigineCapa("BC");
				eucapa.setMontantCapa(ech.getMontant());
				eucapa.setMontantUtiliser(0);
				eucapa.setMontantSolde(ech.getMontant());
				eucapa.setEuCompte(compteDest);
				eucapa.setEtatCapa("Actif");
				eucapa.setIdOperation(null);
				eucapa.setHeureCapa(date);
				capaRepo.save(eucapa);

				EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
				EuRecouvrementMcnp recMcnp = new EuRecouvrementMcnp();
				recMcnp.setCodeSmcipn(smcipn.getCodeSmcipn());
				recMcnp.setDateRecouvrement(date);
				recMcnp.setIdEchange(ech.getIdEchange());
				recMcnp.setIdReleve(releve.getReleveId());
				recMcnp.setIdUtilisateur(null);
				recMcnp.setMontRecouvrement(mont_recouvrement);
				recMcnp.setTypeRessource(ressource);
				recMcnp.setOldCodeMembre(releve.getReleveMembre());
				recMcnp.setNewCodeMembre(newCodeMembre);
				recouvMcnpRepo.save(recMcnp);

				if (!releve.getTraiter()) {
					releve.setTraiter(true);
				}
				releve.setPublier(2);
				releveService.update(releve);

				return "Recouvrement du releve " + releve.getReleveId() + " : Opération effectuée avec succès";
			} catch (Exception e) {
				return "Echec du recouvrement du relevé " + releve.getReleveId() + " : " + e.getMessage();
			}
		} else {
			return "Echec du recouvrement du relevé " + releve.getReleveId() + " : cet ancien compte " + codeCompte
					+ " n'existe pas";
		}
	}

	@Transactional
	public String recouvrerGcp(EuReleve releve, String newCodeMembre, String ressource) {
		EuAncienCompte ancCompte = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String codeMembreDebiteur = "0010010010010000212M";
		String codeCompte = "NB-TPAGCP-" + releve.getReleveMembre();

		ancCompte = ancCompteRepo.findOne(codeCompte);
		if (Objects.nonNull(ancCompte)) {
			try {
				List<EuAncienGcp> ancgcpss = ancGcpService.findByMembre(releve.getReleveMembre());
				if (!ancgcpss.isEmpty()) {
					Double somResteGcp = ancgcpss.stream().mapToDouble(EuAncienGcp::getReste).sum();
					double mont_recouvrement = somResteGcp;
					EuTegc tegc = tegcRepo.findByCodeMembre(codeMembreDebiteur).get(0);
					String numAppelOffre = smcipnComponent.doSmcipnCharge(codeMembreDebiteur, tegc.getCodeTegc(),
							"Reglement Passif " + ressource + " du " + newCodeMembre, mont_recouvrement);
					EuEchange ech = echangeUtility.echangeNRNN(codeMembreDebiteur, newCodeMembre, numAppelOffre,
							ressource, mont_recouvrement, null);
					// Création ou Mise à jour du compte du crédit à obtenir
					EuCompte compteDest = compteRepo.findOne(ech.getCodeCompteObt());
					EuTypeCompte typeCompte = new EuTypeCompte();
					EuCategorieCompte cat = new EuCategorieCompte();
					if (compteDest != null) {
						compteDest.setSolde(compteDest.getSolde() + (ech.getMontant() / 12));
						compteRepo.save(compteDest);
					} else {
						cat.setCodeCat("CAPA");
						typeCompte.setCodeTypeCompte("NN");

						compteDest = new EuCompte();
						compteDest.setCodeCompte(ech.getCodeCompteObt());
						compteDest.setDateAlloc(date);
						compteDest.setDesactiver("N");
						compteDest.setEuCategorieCompte(cat);
						if (newCodeMembre.endsWith("M")) {
							EuMembreMorale moral = new EuMembreMorale();
							moral.setCodeMembreMorale(newCodeMembre);
							compteDest.setEuMembreMorale(moral);
							compteDest.setEuMembre(null);
						} else {
							EuMembre membre = new EuMembre();
							membre.setCodeMembre(newCodeMembre);
							compteDest.setEuMembre(membre);
							compteDest.setEuMembreMorale(null);
						}
						compteDest.setSolde(ech.getMontant() / 12);
						compteDest.setEuTypeCompte(typeCompte);
						compteDest.setLibCompte("Bon d'Achat Interne");
						compteDest.setCardprinteddate(null);
						compteDest.setCardprintediddate(0);
						compteDest.setMifarecard(null);
						compteDest.setNumeroCarte(null);
						compteRepo.save(compteDest);
					}

					EuCapa eucapa = new EuCapa();
					eucapa.setCodeCapa("BAi" + formatter.format(date));
					eucapa.setDateCapa(date);
					eucapa.setCodeMembre(newCodeMembre);
					eucapa.setCodeProduit(ressource);
					eucapa.setTypeCapa("BAI");
					eucapa.setOrigineCapa(ressource);
					eucapa.setMontantCapa(ech.getMontant());
					eucapa.setMontantUtiliser(0);
					eucapa.setMontantSolde(ech.getMontant());
					eucapa.setEuCompte(compteDest);
					eucapa.setEtatCapa("Actif");
					eucapa.setIdOperation(null);
					eucapa.setHeureCapa(date);
					capaRepo.save(eucapa);

					EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
					EuRecouvrementMcnp recMcnp = new EuRecouvrementMcnp();
					recMcnp.setCodeSmcipn(smcipn.getCodeSmcipn());
					recMcnp.setDateRecouvrement(date);
					recMcnp.setIdEchange(ech.getIdEchange());
					recMcnp.setIdReleve(releve.getReleveId());
					recMcnp.setIdUtilisateur(null);
					recMcnp.setMontRecouvrement(mont_recouvrement);
					recMcnp.setTypeRessource(ressource);
					recMcnp.setOldCodeMembre(releve.getReleveMembre());
					recMcnp.setNewCodeMembre(newCodeMembre);
					recouvMcnpRepo.save(recMcnp);

					EuRecouvrement rec = new EuRecouvrement();
					rec.setCodeMembre(newCodeMembre);
					rec.setDateDebut(date);
					rec.setDateFin(ServerUtil.ajouterJours(date, 30));
					rec.setIdRecouvrementMcnp(recMcnp.getIdRecouvrementMcnp());
					rec.setMontRecouvrement(somResteGcp);
					rec.setMontTranche(somResteGcp / 12);
					rec.setMontPayer(somResteGcp / 12);
					rec.setResteAPayer(somResteGcp - rec.getMontPayer());
					rec.setNbreRenouv(12);
					rec.setResteNbreRenouv(rec.getNbreRenouv() - 1);
					rec.setTypeRessource(ressource);
					rec.setPeriodeRenouv(30);
					recRepo.save(rec);

					if (!releve.getTraiter()) {
						releve.setTraiter(true);
					}
					releve.setPublier(2);
					releveService.update(releve);

					return "Recouvrement du releve " + releve.getReleveId() + " : Operation effectuée avec succès";
				}
			} catch (Exception e) {
				return "Echec du recouvrement du relevé " + releve.getReleveId() + " : " + e.getMessage();
			}
		} else {
			return "Echec du recouvrement du relevé " + releve.getReleveId() + " : cet ancien compte " + codeCompte
					+ " n'existe pas";
		}
		return "Echec du recouvrement du relevé " + releve.getReleveId();
	}

	@Transactional(readOnly = false)
	public String recouvrerMf(EuReleve releve, String newCodeMembre, String ressource) {
		EuAncienCompte ancCompte = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String codeMembreDebiteur = "0010010010010000212M";
		String codeCompte = "";
		codeCompte = "NN-TR-" + releve.getReleveMembre();
		ancCompte = ancCompteRepo.findOne(codeCompte);
		if (Objects.nonNull(ancCompte)) {
			List<EuRepartitionMf107> mf107s = Lists.newArrayList();
			List<EuRepartitionMf11000> mf11000s = Lists.newArrayList();
			Double montMf = 0.0;
			Double montMfpaye = 0.0;
			if ("MF11000".equals(ressource)) {
				mf11000s = repartMf11000Repo.findByCodeMf11000(releve.getReleveMembre());
				montMf = repartMf11000Repo.getSumMf11000MontRepByCode(releve.getReleveMembre());
				montMfpaye = repartMf11000Repo.getSumMf11000MontRegltByCode(releve.getReleveMembre());
			} else {
				mf107s = repartMf107Repo.findByCodeMembre(releve.getReleveMembre());
				montMf = repartMf107Repo.getSumMf107MontRepByMembre(releve.getReleveMembre());
				montMfpaye = repartMf107Repo.getSumMf107MontRegltByMembre(releve.getReleveMembre());
			}
			int nbre = 23;
			int reste = 0;
			if (!mf107s.isEmpty() || !mf11000s.isEmpty()) {
				double mise = 0;
				double mont_recouvrement = montMf - montMfpaye;
				mise = Math.floor(mont_recouvrement / nbre);
				reste = nbre;
				try {
					EuTegc tegc = tegcRepo.findByCodeMembre(codeMembreDebiteur).get(0);
					String numAppelOffre = smcipnComponent.doSmcipnCharge(codeMembreDebiteur, tegc.getCodeTegc(),
							"Reglement Passif " + ressource + " du " + newCodeMembre, mont_recouvrement);
					EuEchange ech = echangeUtility.echangeNRNN(codeMembreDebiteur, newCodeMembre, numAppelOffre,
							ressource, mont_recouvrement, null);
					// Création ou Mise à jour du compte du crédit à obtenir
					EuCompte compteDest = compteRepo.findOne(ech.getCodeCompteObt());
					EuTypeCompte typeCompte = new EuTypeCompte();
					EuCategorieCompte cat = new EuCategorieCompte();
					if (compteDest != null) {
						compteDest.setSolde(compteDest.getSolde() + mise);
						compteRepo.save(compteDest);
					} else {
						cat.setCodeCat("CAPA");
						typeCompte.setCodeTypeCompte("NN");

						compteDest = new EuCompte();
						compteDest.setCodeCompte(ech.getCodeCompteObt());
						compteDest.setDateAlloc(date);
						compteDest.setDesactiver("N");
						compteDest.setEuCategorieCompte(cat);
						if (newCodeMembre.endsWith("M")) {
							EuMembreMorale moral = new EuMembreMorale();
							moral.setCodeMembreMorale(newCodeMembre);
							compteDest.setEuMembreMorale(moral);
							compteDest.setEuMembre(null);
						} else {
							EuMembre membre = new EuMembre();
							membre.setCodeMembre(newCodeMembre);
							compteDest.setEuMembre(membre);
							compteDest.setEuMembreMorale(null);
						}
						compteDest.setSolde(mise);
						compteDest.setEuTypeCompte(typeCompte);
						compteDest.setLibCompte("Bon d'Achat Interne");
						compteDest.setCardprinteddate(null);
						compteDest.setCardprintediddate(0);
						compteDest.setMifarecard(null);
						compteDest.setNumeroCarte(null);
						compteRepo.save(compteDest);
					}

					EuCapa eucapa = new EuCapa();
					eucapa.setCodeCapa("BAi" + formatter.format(date));
					eucapa.setDateCapa(date);
					eucapa.setCodeMembre(newCodeMembre);
					eucapa.setCodeProduit(ressource);
					eucapa.setTypeCapa("BAI");
					eucapa.setOrigineCapa(ressource);
					eucapa.setMontantCapa(mise);
					eucapa.setMontantUtiliser(0);
					eucapa.setMontantSolde(mise);
					eucapa.setEuCompte(compteDest);
					eucapa.setEtatCapa("Actif");
					eucapa.setIdOperation(null);
					eucapa.setHeureCapa(date);
					capaRepo.save(eucapa);

					EuSmcipnpwi smcipn = smcipnService.findByNumeroAppel(numAppelOffre);
					EuRecouvrementMcnp recMcnp = new EuRecouvrementMcnp();
					recMcnp.setCodeSmcipn(smcipn.getCodeSmcipn());
					recMcnp.setDateRecouvrement(date);
					recMcnp.setIdEchange(ech.getIdEchange());
					recMcnp.setIdReleve(releve.getReleveId());
					recMcnp.setIdUtilisateur(null);
					recMcnp.setMontRecouvrement(mont_recouvrement);
					recMcnp.setTypeRessource(ressource);
					recMcnp.setOldCodeMembre(releve.getReleveMembre());
					recMcnp.setNewCodeMembre(newCodeMembre);
					recouvMcnpRepo.save(recMcnp);

					EuRecouvrement rec = new EuRecouvrement();
					rec.setCodeMembre(newCodeMembre);
					rec.setDateDebut(date);
					rec.setDateFin(ServerUtil.ajouterJours(date, 30));
					rec.setIdRecouvrementMcnp(recMcnp.getIdRecouvrementMcnp());
					rec.setMontRecouvrement(mont_recouvrement);
					rec.setMontTranche(mise);
					rec.setMontPayer(mise);
					rec.setResteAPayer(mont_recouvrement - rec.getMontPayer());
					rec.setNbreRenouv(reste);
					rec.setResteNbreRenouv(rec.getNbreRenouv() - 1);
					rec.setTypeRessource(ressource);
					rec.setPeriodeRenouv(30);
					recRepo.save(rec);

					if (!releve.getTraiter()) {
						releve.setTraiter(true);
					}
					releve.setPublier(2);
					releveService.update(releve);

					return "Recouvrement du releve " + releve.getReleveId() + " : Opération bien effectuée";
				} catch (Exception e) {
					return "Echec du recouvrement du relevé " + releve.getReleveId() + " : " + e.getMessage();
				}
			}
		} else {
			return "Echec du recouvrement du relevé " + releve.getReleveId() + " : Cet ancien compte " + codeCompte
					+ " n'existe pas";
		}
		return "Echec du recouvrement du relevé " + releve.getReleveId();
	}

}
