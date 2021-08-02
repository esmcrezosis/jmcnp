package com.esmc.mcnp.infrastructure.services.pc;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.cm.EuAncienCompteCreditRepository;
import com.esmc.mcnp.dao.repository.cm.EuAncienCompteRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuRepartitionMf107Repository;
import com.esmc.mcnp.dao.repository.cmfh.EuRepartitionMf11000Repository;
import com.esmc.mcnp.dao.repository.obps.EuAncienGcpRepository;
import com.esmc.mcnp.dao.repository.obpsd.PlaceRepository;
import com.esmc.mcnp.dao.repository.pc.EuReleveRepository;
import com.esmc.mcnp.dao.repository.pc.EuRelevedetailRepository;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompte;
import com.esmc.mcnp.domain.entity.obpsd.Place;
import com.esmc.mcnp.domain.entity.pc.EuReleve;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service("euReleveService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuReleveServiceImpl extends CrudServiceImpl<EuReleve, Integer> implements EuReleveService {

	private final EuReleveRepository releveRepo;
	private final EuAncienGcpRepository ancGcpRepo;
	private final EuAncienCompteRepository ancCompteRepo;
	private final EuAncienCompteCreditRepository ancCCRepo;
	private final EuRelevedetailRepository detRelRepo;
	private final EuRepartitionMf11000Repository repMf11000Repo;
	private final EuRepartitionMf107Repository repMf107Repo;
	private final PlaceRepository placeRepo;

	protected EuReleveServiceImpl(EuReleveRepository repository, EuReleveRepository releveRepo,
			EuAncienGcpRepository ancGcpRepo, EuAncienCompteRepository ancCompteRepo,
			EuAncienCompteCreditRepository ancCCRepo, EuRelevedetailRepository detRelRepo,
			EuRepartitionMf11000Repository repMf11000Repo, EuRepartitionMf107Repository repMf107Repo,
			PlaceRepository placeRepo) {
		super(repository);
		this.releveRepo = releveRepo;
		this.ancGcpRepo = ancGcpRepo;
		this.ancCompteRepo = ancCompteRepo;
		this.ancCCRepo = ancCCRepo;
		this.detRelRepo = detRelRepo;
		this.repMf11000Repo = repMf11000Repo;
		this.repMf107Repo = repMf107Repo;
		this.placeRepo = placeRepo;
	}

	@Override
	public List<EuReleve> findByReleveMembre(String codeMembre) {
		return releveRepo.findByReleveMembre(codeMembre);
	}

	@Override
	public List<EuReleve> findByReleveMembre(String codeMembre, Integer publier) {
		return releveRepo.findByReleveMembreAndPublier(codeMembre, publier);
	}

	@Override
	public List<EuReleve> findByReleveMembreAndType(String codeMembre, String type, Integer publier) {
		return releveRepo.findByMembreAndType(codeMembre, type, publier);
	}

	@Override
	public Page<EuReleve> findByReleveMembre(String codeMembre, Pageable pageable) {
		return releveRepo.findByReleveMembre(codeMembre, pageable);
	}

	@Override
	public Page<EuReleve> findByReleveMembre(String codeMembre, Integer publier, Pageable pageable) {
		return releveRepo.findByReleveMembreAndPublier(codeMembre, publier, pageable);
	}

	@Override
	public List<EuReleve> findByReleveMembreAndType(String codeMembre, String type) {
		return releveRepo.findByMembreAndType(codeMembre, type);
	}

	@Override
	public List<EuReleve> findByMembre(String codeMembre) {
		return releveRepo.findByNewCodeMembre(codeMembre);
	}

	@Override
	public List<EuReleve> findByMembre(String codeMembre, Integer publier) {
		return releveRepo.findByNewCodeMembreAndPublier(codeMembre, publier);
	}

	@Override
	public List<EuReleve> findByMembreAndType(String codeMembre, String type, Integer publier) {
		return releveRepo.findByNewMembreAndType(codeMembre, type, publier);
	}

	@Override
	public Page<EuReleve> findByMembre(String codeMembre, Pageable pageable) {
		return releveRepo.findByNewCodeMembre(codeMembre, pageable);
	}

	@Override
	public Page<EuReleve> findByMembre(String codeMembre, Integer publier, Pageable pageable) {
		return releveRepo.findByNewCodeMembreAndPublier(codeMembre, publier, pageable);
	}

	@Override
	public List<EuReleve> findByReleveType(String type) {
		return releveRepo.findByReleveType(type);
	}

	@Override
	public Page<EuReleve> findByReleveType(String type, Pageable pageable) {
		return releveRepo.findByReleveType(type, pageable);
	}

	@Override
	public List<EuReleve> findByMembreAndType(String newCodeMembre, String type) {
		return releveRepo.findByNewMembreAndType(newCodeMembre, type);
	}

	@Override
	public Double verifierRelever(String typePassif, String codeMembre, String type, Integer publier) {
		if (typePassif.equals("MCNP")) {
			return verifierReleveMcnp(codeMembre, type, publier);
		}
		return 0.0;
	}

	@Override
	public Double verifierRelever(EuReleve releve) {
		return verifierReleveMcnp(releve);
	}

	@Override
	public Double verifierRelever(Integer idReleve) {
		EuReleve releve = getById(idReleve);
		if (releve != null) {
			return verifierRelever(releve);
		}
		return 0.0;
	}

	private Double verifierReleveMcnp(EuReleve releve) {
		if (Objects.nonNull(releve) && releve.getPublier() == 1) {
			Double soldeCapaRel = detRelRepo.getDetailSumCapaByReleve(releve.getReleveId());
			String codeCompte = "";
			EuAncienCompte ancCpte = null;
			Double soldeGcp = 0.0;
			Double soldeReste = 0.0;
			if ("CNP".equals(releve.getReleveType()) || "CNCS".equals(releve.getReleveType())) {
				if (releve.getReleveMembre().length() == 20) {
					if ("CNP".equals(releve.getReleveType())) {
						if ("CNP".equals(releve.getReleveType())) {
							if (releve.getReleveMembre().endsWith("P")) {
								codeCompte = "NB-TPAGCRPG-" + releve.getReleveMembre();
							} else {
								codeCompte = "NB-TPAGCI-" + releve.getReleveMembre();
							}
						}
						ancCpte = ancCompteRepo.findOne(codeCompte);
						Double soldeCapar = 0.0;
						Double soldeCapanr = 0.0;
						Double soldeCreditnr = 0.0;
						Double soldeCreditr = 0.0;
						if (releve.getReleveMembre().endsWith("P")) {
							soldeCreditr = ancCCRepo.getCompteSumCreditByCodeMembre(releve.getReleveMembre(), "RPGr")
									.orElse(0.0);
							soldeCreditnr = ancCCRepo.getCompteSumCreditByCodeMembre(releve.getReleveMembre(), "RPGnr")
									.orElse(0.0);
							soldeCapar = ancCCRepo.getCompteSumCapaByCodeMembre(releve.getReleveMembre(), "RPGr")
									.orElse(0.0);
							soldeCapanr = ancCCRepo.getCompteSumCapaByCodeMembre(releve.getReleveMembre(), "RPGnr")
									.orElse(0.0);
						} else {
							soldeCreditr = ancCCRepo.getCompteSumCreditByCodeMembre(releve.getReleveMembre(), "Ir")
									.orElse(0.0);
							soldeCreditnr = ancCCRepo.getCompteSumCreditByCodeMembre(releve.getReleveMembre(), "Inr")
									.orElse(0.0);
							soldeCapar = ancCCRepo.getCompteSumCapaByCodeMembre(releve.getReleveMembre(), "Ir")
									.orElse(0.0);
							soldeCapanr = ancCCRepo.getCompteSumCapaByCodeMembre(releve.getReleveMembre(), "Inr")
									.orElse(0.0);
						}
						Double soldeCredit = soldeCreditr + soldeCreditnr;
						Double soldeCapa = soldeCapar + soldeCapanr;
						if (soldeCapa.equals(soldeCapaRel) && soldeCredit.equals(ancCpte.getSolde())) {
							if (soldeCreditr == 0) {
								return soldeCreditnr * 5.6 / 8;
							} else {
								return soldeCapar + (soldeCreditnr * 5.6 / 8);
							}
						}
					} else if ("CNCS".equals(releve.getReleveType())) {
						if (releve.getReleveMembre().endsWith("P")) {
							codeCompte = "NN-TCNCS-" + releve.getReleveMembre();
							ancCpte = ancCompteRepo.findOne(codeCompte);
							return ancCpte.getSolde();
						} else {
							codeCompte = "NR-TCNCSEI-" + releve.getReleveMembre();
							ancCpte = ancCompteRepo.findOne(codeCompte);
							Double soldeCredit = ancCCRepo
									.getCompteSumCreditByCodeMembre(releve.getReleveMembre(), "CNCS%").orElse(0.0);
							Double soldeCapa = ancCCRepo.getCompteSumCapaByCodeMembre(releve.getReleveMembre(), "CNCS%")
									.orElse(0.0);
							if (soldeCapa.equals(soldeCapaRel) && soldeCredit.equals(ancCpte.getSolde())) {
								return soldeCredit;
							}
						}
					}
				} else {
					List<Place> places = Lists.newArrayList();
					if ("CNCS".equals(releve.getReleveType())) {
						places = placeRepo.findByMembreAndLib(releve.getReleveMembre(), releve.getReleveType());
					} else {
						places = placeRepo.findByMembreAndLibLike(releve.getReleveMembre(),
								releve.getReleveType() + "%");
					}
					System.out.println("Nombre de lignes = " + places.size());
					Double somme = places.stream().mapToDouble(p -> Double.parseDouble(p.getMontant())).sum();
					System.out.println("Somme des lignes = " + somme);
					return somme;
				}
			} else if ("GCP".equals(releve.getReleveType())) {
				codeCompte = "NB-TPAGCP-" + releve.getReleveMembre();
				ancCpte = ancCompteRepo.findOne(codeCompte);
				soldeReste = ancGcpRepo.getSumResteByCodeMembre(releve.getReleveMembre());
				soldeGcp = ancGcpRepo.getSumGcpByCodeMembre(releve.getReleveMembre());
				System.out.println("Solde Reste : " + soldeReste + " Solde Compte : " + ancCpte.getSolde()
						+ " solde GCP : " + soldeGcp + " Solde Releve : " + soldeCapaRel);
				if (soldeReste.equals(ancCpte.getSolde()) && soldeGcp.equals(soldeCapaRel)) {
					return soldeReste;
				}
			} else {
				if ("MF11000".equals(releve.getReleveType())) {
					codeCompte = "NN-TR-" + releve.getReleveMembre();
					ancCpte = ancCompteRepo.findOne(codeCompte);
					soldeReste = repMf11000Repo.getSumMf11000SoldeRepByCode(releve.getReleveMembre());
					if (soldeReste.equals(ancCpte.getSolde())) {
						return ancCpte.getSolde();
					}
				} else {
					soldeReste = repMf107Repo.getSumMf107SoldeRepByMembre(releve.getReleveMembre());
					Double soldeCapa = repMf107Repo.getSumMf107MontRepByMembre(releve.getReleveMembre());
					if (soldeReste.equals(soldeCapaRel) || soldeCapa.equals(soldeCapaRel)) {
						return soldeReste;
					}
				}
			}
		}
		return 0.0;
	}

	private Double verifierReleveMcnp(String codeMembre, String type, Integer publier) {
		double somReleve = 0;
		List<EuReleve> releves = findByMembreAndType(codeMembre, type, publier);
		if (releves.size() > 0) {
			for (EuReleve releve : releves) {
				if (Objects.nonNull(releve)) {
					Double soldeCapaRel = detRelRepo.getDetailSumCapaByReleve(releve.getReleveId());
					String codeCompte = "";
					EuAncienCompte ancCpte = null;
					if ("CNP".equals(type)) {
						if (codeMembre.endsWith("P")) {
							codeCompte = "NB-TPAGCRPG-" + codeMembre;
						} else {
							codeCompte = "NB-TPAGCI-" + codeMembre;
						}
					} else if ("GCP".equals(type)) {
						codeCompte = "NB-TPAGCP-" + codeMembre;
					} else if ("CNCS".equals(type)) {
						if (codeMembre.endsWith("P")) {
							codeCompte = "NN-TCNCS-" + codeMembre;
						} else {
							codeCompte = "NR-TCNCSEI-" + codeMembre;
						}
					} else {
						codeCompte = "NN-TR-" + codeMembre;
					}
					if (!"MF107".equals(type)) {
						ancCpte = ancCompteRepo.findOne(codeCompte);
					}
					Double soldeCapa = 0.0;
					Double soldeCredit = 0.0;
					Double soldeGcp = 0.0;
					Double soldeReste = 0.0;
					if (Objects.nonNull(ancCpte) || "MF107".equals(type)) {
						if ("CNP".equals(type) || "CNCS".equals(type)) {
							if ("CNP".equals(type)) {
								if (codeMembre.endsWith("P")) {
									soldeCredit = ancCCRepo.getCompteSumCreditByCodeMembre(codeMembre, "RPG%")
											.orElse(0.0);
									soldeCapa = ancCCRepo.getCompteSumCapaByCodeMembre(codeMembre, "RPG%").orElse(0.0);
								} else {
									soldeCredit = ancCCRepo.getCompteSumCreditByCodeMembre(codeMembre, "I%")
											.orElse(0.0);
									soldeCapa = ancCCRepo.getCompteSumCapaByCodeMembre(codeMembre, "I%").orElse(0.0);
								}
								if (soldeCapa.equals(soldeCapaRel) && soldeCredit.equals(ancCpte.getSolde())) {
									somReleve += soldeCredit;
								}
							} else if ("CNCS".equals(type)) {
								if (codeMembre.endsWith("M")) {
									soldeCredit = ancCCRepo.getCompteSumCreditByCodeMembre(codeMembre, "CNCS%")
											.orElse(0.0);
									soldeCapa = ancCCRepo.getCompteSumCapaByCodeMembre(codeMembre, "CNCS%").orElse(0.0);
									if (soldeCapa.equals(soldeCapaRel) && soldeCredit.equals(ancCpte.getSolde())) {
										somReleve += soldeCredit;
									}
								} else {
									somReleve += ancCpte.getSolde();
								}
							}
						} else if ("GCP".equals(type)) {
							soldeReste = ancGcpRepo.getSumResteByCodeMembre(codeMembre);
							soldeGcp = ancGcpRepo.getSumGcpByCodeMembre(codeMembre);
							if (soldeReste.equals(ancCpte.getSolde()) && soldeGcp.equals(soldeCapaRel)) {
								somReleve += soldeReste;
							}
						} else {
							if ("MF11000".equals(type)) {
								soldeReste = repMf11000Repo.getSumMf11000SoldeRepByCode(codeMembre);
								if (soldeReste.equals(ancCpte.getSolde())) {
									somReleve += ancCpte.getSolde();
								}
							} else {
								soldeReste = repMf107Repo.getSumMf107SoldeRepByMembre(codeMembre);
								soldeCapa = repMf107Repo.getSumMf107MontRepByMembre(codeMembre);
								if (soldeReste.equals(soldeCapaRel) || soldeCapa.equals(soldeCapaRel)) {
									somReleve += soldeReste;
								}
							}
						}
					}
				}
			}
		}
		return somReleve;
	}

}
