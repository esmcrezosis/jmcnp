package com.esmc.mcnp.services.cmfh;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.components.EchangeUtility;
import com.esmc.mcnp.components.SmcipnComponent;
import com.esmc.mcnp.config.AppProperties;
import com.esmc.mcnp.model.acteur.EuDepotVente;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.model.pc.EuRecouvrementMcnp;
import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.repositories.ba.EuCapaRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cmfh.EuDepotVenteRepository;
import com.esmc.mcnp.repositories.others.EuTegcRepository;
import com.esmc.mcnp.repositories.pc.EuRecouvrementMcnpRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import com.esmc.mcnp.services.smcipn.EuSmcipnpwiService;

@Service("euDepotVenteService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDepotVenteServiceImpl extends CrudServiceImpl<EuDepotVente, Long> implements EuDepotVenteService {

	private final EuDepotVenteRepository depotVenteRepo;
	private final EuTegcRepository tegcRepo;
	private final SmcipnComponent smcipnComponent;
	private final EchangeUtility echangeUtility;
	private final EuSmcipnpwiService smcipnService;
	private final EuCompteRepository compteRepo;
	private final EuCapaRepository capaRepo;
	private final EuRecouvrementMcnpRepository recouvMcnpRepo;
	private final AppProperties properties;
	private Logger log = LogManager.getLogger(EuDepotVenteServiceImpl.class.getName());

	public EuDepotVenteServiceImpl(EuDepotVenteRepository depotVenteRepo, EuTegcRepository tegcRepo,
			SmcipnComponent smcipnComponent, EchangeUtility echangeUtility, EuSmcipnpwiService smcipnService,
			EuCompteRepository compteRepo, EuCapaRepository capaRepo, EuRecouvrementMcnpRepository recouvMcnpRepo,
			AppProperties properties) {
		super(depotVenteRepo);
		this.depotVenteRepo = depotVenteRepo;
		this.tegcRepo = tegcRepo;
		this.smcipnComponent = smcipnComponent;
		this.echangeUtility = echangeUtility;
		this.smcipnService = smcipnService;
		this.compteRepo = compteRepo;
		this.capaRepo = capaRepo;
		this.recouvMcnpRepo = recouvMcnpRepo;
		this.properties = properties;
	}

	@Override
	public boolean recouvrerCmfh(int size) {
		List<EuDepotVente> depotList = fetchAllByDate(0, size);
		if (depotList.size() > 0) {
			for (EuDepotVente depot : depotList)
				recouvrerCmfh(depot);
			return true;
		}
		return false;
	}

	@Override
	public List<EuDepotVente> findByCodeMembre(String codeMembre) {
		return depotVenteRepo.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuDepotVente> findByMembre(String codeMembre) {
		return depotVenteRepo.findByMembre(codeMembre);
	}

	@Override
	public Page<EuDepotVente> findByMembre(String codeMembre, Pageable pageable) {
		return depotVenteRepo.findByMembre(codeMembre, pageable);
	}

	@Override
	public List<EuDepotVente> findByMembre(String nom, String prenom) {
		return depotVenteRepo.findByMembre(nom, prenom);
	}

	@Override
	public Page<EuDepotVente> findByMembre(String nom, String prenom, Pageable pageable) {
		return depotVenteRepo.findByMembre(nom, prenom, pageable);
	}

	@Override
	public List<EuDepotVente> fetchAllByDate(int min, int limit) {
		PageRequest page = PageRequest.of(min, limit);
		List<EuDepotVente> depots = Collections.emptyList();
		CompletableFuture<List<EuDepotVente>> cfDepots = depotVenteRepo.findByOrderByDateDepot(page);
		try {
			depots = cfDepots.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Echec de l'execution de la requete de récuperation des depôt ventes", e);
		}
		return depots;
	}

	@Transactional(readOnly = false)
	@Override
	public boolean recouvrerCmfh(EuDepotVente depot) {
		final String codeMembreDebiteur = properties.getEsmc();
		final Date date = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		EuTegc tegc = null;
		if (depot.getCodeMembre().endsWith("M")) {
			tegc = tegcRepo.findByCodeMembre(depot.getCodeMembre()).get(0);
		} else {
			tegc = tegcRepo.findByCodeMembrePhysique(depot.getCodeMembre()).get(0);
		}
		if (Objects.nonNull(tegc)) {
			double mont_recouvrement = depot.getMontDepot();
			String numAppelOffre = smcipnComponent.doSmcipnCharge(codeMembreDebiteur, tegc.getCodeTegc(),
					"Reglement CMFH N° " + depot.getCodeMembre(), mont_recouvrement);
			if (StringUtils.isNotBlank(numAppelOffre)) {
				EuEchange ech = echangeUtility.echangeNRNN(codeMembreDebiteur, depot.getCodeMembre(), numAppelOffre, "",
						mont_recouvrement, null);
				if (Objects.nonNull(ech)) {
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
						if (depot.getCodeMembre().endsWith("M")) {
							EuMembreMorale moral = new EuMembreMorale();
							moral.setCodeMembreMorale(depot.getCodeMembre());
							compteDest.setEuMembreMorale(moral);
							compteDest.setEuMembre(null);
						} else {
							EuMembre membre = new EuMembre();
							membre.setCodeMembre(depot.getCodeMembre());
							compteDest.setEuMembre(membre);
							compteDest.setEuMembreMorale(null);
						}
						compteDest.setSolde(ech.getMontant());
						compteDest.setEuTypeCompte(typeCompte);
						compteDest.setLibCompte("NN CAPA");
						compteDest.setCardprinteddate(null);
						compteDest.setCardprintediddate(0);
						compteDest.setMifarecard(null);
						compteDest.setNumeroCarte(null);
						compteRepo.save(compteDest);
					}

					EuCapa eucapa = new EuCapa();
					eucapa.setCodeCapa("CAPA" + formatter.format(date));
					eucapa.setDateCapa(date);
					eucapa.setCodeMembre(depot.getCodeMembre());
					if (depot.getCodeMembre().endsWith("M")) {
						eucapa.setCodeProduit("Inr");
					} else {
						eucapa.setCodeProduit("RPGnr");
					}
					eucapa.setTypeCapa("BAI");
					eucapa.setOrigineCapa("CMFH");
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
					recMcnp.setIdReleve(null);
					recMcnp.setIdUtilisateur(null);
					recMcnp.setMontRecouvrement(mont_recouvrement);
					recMcnp.setTypeRessource("CMFH");
					recMcnp.setOldCodeMembre(null);
					recMcnp.setNewCodeMembre(depot.getCodeMembre());
					recouvMcnpRepo.save(recMcnp);

					depot.setPayer(true);
					update(depot);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean recouvrerCmfh(List<EuDepotVente> depots, Double montant) {
		if (!depots.isEmpty() && montant > 0) {
			final String codeMembreDebiteur = properties.getEsmc();
			final Date date = new Date();
			final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			EuTegc tegc = null;
			if (depots.get(0).getCodeMembre().endsWith("M")) {
				tegc = tegcRepo.findByCodeMembre(depots.get(0).getCodeMembre()).get(0);
			} else {
				tegc = tegcRepo.findByCodeMembrePhysique(depots.get(0).getCodeMembre()).get(0);
			}
			if (Objects.nonNull(tegc)) {
				double mont_recouvrement = montant;
				String numAppelOffre = smcipnComponent.doSmcipnCharge(codeMembreDebiteur, tegc.getCodeTegc(),
						"Reglement CMFH N° " + depots.get(0).getCodeMembre(), mont_recouvrement);
				if (StringUtils.isNotBlank(numAppelOffre)) {
					EuEchange ech = echangeUtility.echangeNRNN(codeMembreDebiteur, depots.get(0).getCodeMembre(),
							numAppelOffre, "", mont_recouvrement, null);

					if (Objects.nonNull(ech)) {
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
							if (depots.get(0).getCodeMembre().endsWith("M")) {
								EuMembreMorale moral = new EuMembreMorale();
								moral.setCodeMembreMorale(depots.get(0).getCodeMembre());
								compteDest.setEuMembreMorale(moral);
								compteDest.setEuMembre(null);
							} else {
								EuMembre membre = new EuMembre();
								membre.setCodeMembre(depots.get(0).getCodeMembre());
								compteDest.setEuMembre(membre);
								compteDest.setEuMembreMorale(null);
							}
							compteDest.setSolde(ech.getMontant());
							compteDest.setEuTypeCompte(typeCompte);
							compteDest.setLibCompte("NN CAPA");
							compteDest.setCardprinteddate(null);
							compteDest.setCardprintediddate(0);
							compteDest.setMifarecard(null);
							compteDest.setNumeroCarte(null);
							compteRepo.save(compteDest);
						}

						EuCapa eucapa = new EuCapa();
						eucapa.setCodeCapa("CAPA" + formatter.format(date));
						eucapa.setDateCapa(date);
						eucapa.setCodeMembre(depots.get(0).getCodeMembre());
						if (depots.get(0).getCodeMembre().endsWith("M")) {
							eucapa.setCodeProduit("Inr");
						} else {
							eucapa.setCodeProduit("RPGnr");
						}
						eucapa.setTypeCapa("BAI");
						eucapa.setOrigineCapa("CMFH");
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
						recMcnp.setIdReleve(null);
						recMcnp.setIdUtilisateur(null);
						recMcnp.setMontRecouvrement(mont_recouvrement);
						recMcnp.setTypeRessource("CMFH");
						recMcnp.setOldCodeMembre(null);
						recMcnp.setNewCodeMembre(depots.get(0).getCodeMembre());
						recouvMcnpRepo.save(recMcnp);

						depots.forEach(d -> {
							d.setPayer(true);
						});
						updateInBatch(depots);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean recouvrerCmfh(String codeMembre, Double montant) {
		// TODO Auto-generated method stub
		return false;
	}

}
