package com.esmc.mcnp.components;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.dto.other.TableauBnp;
import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.config.EuParametres;
import com.esmc.mcnp.model.config.EuParametresPK;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.obps.EuBps;
import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.model.obpsd.EuUtiliserNn;
import com.esmc.mcnp.model.oi.EuBnp;
import com.esmc.mcnp.model.oi.EuBnpCredit;
import com.esmc.mcnp.model.oi.EuTypeBnp;
import com.esmc.mcnp.model.others.EuCours;
import com.esmc.mcnp.repositories.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.repositories.common.EuParametreRepository;
import com.esmc.mcnp.repositories.obps.EuBpsRepository;
import com.esmc.mcnp.repositories.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.repositories.oi.EuBnpCreditRepository;
import com.esmc.mcnp.repositories.oi.EuBnpRepository;
import com.esmc.mcnp.repositories.oi.EuTypeBnpRepository;
import com.esmc.mcnp.repositories.others.EuCourRepository;
import com.esmc.mcnp.repositories.others.EuSmsmoneyRepository;
import com.esmc.mcnp.services.obpsd.EuNnService;
import com.esmc.mcnp.services.setting.EuParametresService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Component("creditUtility")
@Transactional
public class CreditUtility {

	private EuSmsmoneyRepository smsMoneyRepo;
	private EuCourRepository courRepo;
	private EuParametresService parametresService;
	private EuBnpCreditRepository bnpCreditRepo;
	private EuBnpRepository bnpRepo;
	private EuTypeBnpRepository typeBnpRepo;
	private EuNnService nnService;
	private EuUtiliserNnRepository utiliserNnRepo;
	private EuCompteCreditRepository ccRepo;
	private EuParametreRepository pRepo;
	private EuCompteRepository compteRepo;
	private EuBpsRepository bpsRepo;
	private final EuMembreRepository membreRepo;

	/**
	 * private @Autowired EuCompteRepository compteRepo; private @Autowired
	 * EuDetailKrrRepository detKrrRepo;
	 *
	 * @param codeSms
	 * @return
	 */

	public CreditUtility(EuSmsmoneyRepository smsMoneyRepo, EuCourRepository courRepo,
			EuParametresService parametresService, EuBnpCreditRepository bnpCreditRepo, EuBnpRepository bnpRepo,
			EuTypeBnpRepository typeBnpRepo, EuNnService nnService,
			EuUtiliserNnRepository utiliserNnRepo, EuCompteCreditRepository ccRepo,
			EuCompteCreditCapaRepository ccCapaRepo,
			EuParametreRepository pRepo, EuCompteRepository compteRepo, EuBpsRepository bpsRepo, EuMembreRepository membreRepo) {
		this.membreRepo = membreRepo;
		this.smsMoneyRepo = smsMoneyRepo;
		this.courRepo = courRepo;
		this.parametresService = parametresService;
		this.bnpCreditRepo = bnpCreditRepo;
		this.bnpRepo = bnpRepo;
		this.typeBnpRepo = typeBnpRepo;
		this.nnService = nnService;
		this.utiliserNnRepo = utiliserNnRepo;
		this.ccRepo = ccRepo;
		this.pRepo = pRepo;
		this.compteRepo = compteRepo;
		this.bpsRepo = bpsRepo;
	}
	
	public boolean isAutoEnroller(String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			EuMembre membre = membreRepo.findOne(codeMembre);
			if (Objects.nonNull(membre)) {
				return membre.getAutoEnroler().equals("O");
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public double getSmsValue(String codeSms) {
		double capa = 0;
		EuSmsmoney money = smsMoneyRepo.findByCreditcode(codeSms);
		if (Objects.nonNull(money)) {
			double smsValue = money.getCreditamount();
			if (!money.getCurrencycode().equalsIgnoreCase("XOF")) {
				String codeCours = money.getCurrencycode() + "-" + "XOF";
				EuCours cours = courRepo.findOne(codeCours);
				if (Objects.nonNull(cours)) {
					capa = Math.floor((smsValue * cours.getValDevFin()) / cours.getValDevInit());
				}
			} else {
				capa = smsValue;
			}
		}
		return capa;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public double calculBcStandard(double montant) {
		int prk = parametresService.getParam("prk", "r");
		double pck = parametresService.getParametre("pck", "r");
		if (montant > 0) {
			return (montant * prk) / pck;
		} else {
			return 0;
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public double calculateCapaStandard(double mont_bc) {
		int prk = parametresService.getParam("prk", "r");
		double pck = parametresService.getParametre("pck", "r");
		if (mont_bc > 0) {
			return (mont_bc * pck) / prk;
		} else {
			return 0;
		}
	}

	public double calculBonConsoOrdRi(double mont_capa) {
		if (mont_capa > 0) {
			return (5 * mont_capa) / 44.8;
		} else {
			return 0;
		}
	}

	public double calculCapaBCOrdRi(double mont_bc) {
		if (mont_bc > 0) {
			return (44.8 * mont_bc) / 5;
		} else {
			return 0;
		}
	}

	public double calculKacm(String typeSouscription, double montant) {
		double tx_bnp = 0;
		double fs = parametresService.getParametre("FS", "valeur");
		double fl = parametresService.getParametre("FL", "valeur");
		double fcps = parametresService.getParametre("FCPS", "valeur");
		double mbcm = parametresService.getParametre("BCM", "valeur");
		if (typeSouscription.equalsIgnoreCase("CAIPC")) {
			tx_bnp = parametresService.getParametre("BNPNMPSFS", "valeur");
		} else {
			tx_bnp = parametresService.getParametre("BNPMPSFS", "valeur");
		}
		double kacm = fs + fl + fcps;
		double msbcm = mbcm * tx_bnp;
		return (montant * (kacm / msbcm));
	}

	public int calculNombreOpi(double banNormal, double banReduit) {
		if (banNormal > 0 && banReduit > 0) {
			int nbreOpi = parametresService.getParam("OPI", "serie");
			return Double.valueOf(Math.ceil((banNormal / banReduit) * nbreOpi)).intValue();
		}
		return 0;
	}

	public double calculMsbc(CalculBonInfo bonInfo, double montant) {
		if (bonInfo.getCatBon().equals("nr")) {
			Double pck = parametresService.getParametre("pck", "nr");
			if (StringUtils.isNotBlank(bonInfo.getTypeCredit()) && bonInfo.getTypeCredit().equals("AG")) {
				return ((montant * pck) / bonInfo.getPrk());
			} else {
				Double txEsc = parametresService.getParametre("taux", "escompte");
				return ((montant * (1 + txEsc / 100)) * pck / bonInfo.getPrk());
			}
		} else {
			if (bonInfo.getTypeRecurrent().equals("illimite")) {
				if (bonInfo.getTypeProduit().equals("PO")) {
					double taux = parametresService.getParametre("MSBCri", "PO");
					return montant * taux;
				} else {
					double taux = parametresService.getParametre("MSBCri", "PS");
					return montant * taux;
				}
			} else {
				if (bonInfo.getDuree() == 22.4) {
					double taux = parametresService.getParametre("MSBCrl", "PM");
					return montant * taux;
				} else if (bonInfo.getDuree() == 11.2) {
					if (bonInfo.getTypeProduit().equals("PO")) {
						double taux = parametresService.getParametre("MSBCrl", "PO");
						return montant * taux;
					} else {
						double taux = parametresService.getParametre("MSBCrl", "PS");
						return montant * taux;
					}
				} else if (bonInfo.getDuree() == 1) {
					EuBps bps = bpsRepo.findOne(bonInfo.getBps().intValue());
					int periode = parametresService.getParam("BCJ", "periode");
					if (Objects.nonNull(bps) && periode > 0) {
						return (montant * periode) / bps.getValeurParametre();
					}
				}
			}
		}
		return 0;
	}

	public double calculBonConso(CalculBonInfo bonInfo, double montant) {
		if (bonInfo.getCatBon().equals("nr")) {
			Double pck = parametresService.getParametre("pck", "nr");
			if (StringUtils.isNotBlank(bonInfo.getTypeCredit()) && bonInfo.getTypeCredit().equals("AG")) {
				return ((montant * bonInfo.getPrk()) / pck);
			} else {
				Double txEsc = parametresService.getParametre("taux", "escompte");
				return ((montant * bonInfo.getPrk()) / (pck * (1 + txEsc / 100)));
			}
		} else {
			if (bonInfo.getTypeRecurrent().equals("illimite")) {
				if (bonInfo.getTypeProduit().equals("PO")) {
					double taux = parametresService.getParametre("MSBCri", "PO");
					return montant / taux;
				} else {
					double taux = parametresService.getParametre("MSBCri", "PS");
					return montant / taux;
				}
			} else {
				if (bonInfo.getDuree() == 22.4) {
					double taux = parametresService.getParametre("MSBCrl", "PM");
					return montant / taux;
				} else if (bonInfo.getDuree() == 11.2) {
					if (bonInfo.getTypeProduit().equals("PO")) {
						double taux = parametresService.getParametre("MSBCrl", "PO");
						return montant / taux;
					} else {
						double taux = parametresService.getParametre("MSBCrl", "PS");
						return montant / taux;
					}
				} else if (bonInfo.getDuree() == 1) {
					EuBps euBps = bpsRepo.findOne(bonInfo.getBps());
					int periode = parametresService.getParam("BCJ", "periode");
					if (Objects.nonNull(euBps) && periode > 0) {
						return (montant * euBps.getValeurParametre()) / periode;
					}
				}
			}
		}
		return 0;
	}

	public double calculMsbcTiers(String typeProduit, String typeBnp, double montant) {
		if (StringUtils.isNotBlank(typeProduit) && StringUtils.isNotBlank(typeBnp) && montant > 0) {
			if (typeProduit.equals("PO")) {
				return (typeBnp.equalsIgnoreCase("CAPU") || typeBnp.equalsIgnoreCase("CMIT")) ? montant * 18.6
						: montant * 16.1;
			} else {
				return (typeBnp.equalsIgnoreCase("CAPU") || typeBnp.equalsIgnoreCase("CMIT")) ? montant * 46.5
						: montant * 40.25;
			}
		}
		return 0;
	}

	public double calculBcTiers(String typeProduit, String typeBnp, double montant) {
		if (StringUtils.isNotBlank(typeProduit) && StringUtils.isNotBlank(typeBnp) && montant > 0) {
			if (typeProduit.equalsIgnoreCase("PO")) {
				return (typeBnp.equalsIgnoreCase("CAPU") || typeBnp.equalsIgnoreCase("CMIT")) ? montant / 18.6
						: montant / 12.32;
			} else {
				return (typeBnp.equalsIgnoreCase("CAPU") || typeBnp.equalsIgnoreCase("CMIT")) ? montant / 46.5
						: montant / 15.67999998;
			}
		}
		return 0;
	}

	public EuMembre selectBnpMembre() {
		double montMinSel = parametresService.getParametre("MMSB", "valeur");
		double pck = parametresService.getParametre("pck", "r");
		int prk = parametresService.getParam("prk", "r");
		double capa = montMinSel * pck / prk;
		List<EuCompteCredit> credits = ListUtils.emptyIfNull(ccRepo.findByProduitAndMontantPlace("RPGr", capa));
		if (!credits.isEmpty()) {
			System.out.println("Selection d'un membre pour le BNP!");
			if (credits.size() == 1) {
				return credits.get(0).getEuCompte().getEuMembre();
			} else {
				return credits.stream().map(cc -> cc.getEuCompte().getEuMembre())
						.reduce((m1, m2) -> (m1.getDateIdentification().before(m2.getDateIdentification()) ? m1 : m2))
						.get();
			}
		} else {
			double quota = parametresService.getParametre("quota", "RPGr");
			if (capa < quota) {
				double mont_aug = parametresService.getParametre("MAPB", "valeur");
				montMinSel += mont_aug;
				EuParametres param = pRepo.findOne(new EuParametresPK("MMSB", "valeur"));
				param.setMontant(montMinSel);
				pRepo.save(param);
				return selectBnpMembre();
			}
		}
		return null;
	}

	

	public double reconstituerCapaKrr(EuKrr krr, Object membre) {
		double mont_capa = 0;
		if (Objects.nonNull(krr) && Objects.nonNull(membre)) {
			if (krr.getMontKrr() == krr.getMontReconst()) {
				if (krr.getTypeKrr().equals("krrBNP") || krr.getTypeKrr().equals("krrBNPP")) {
					double pck = parametresService.getParametre("pck", "nr");
					double prk_rec = parametresService.getParametre("PRK", "RCAPA");
					mont_capa = krr.getMontReconst() * pck / prk_rec;
				} else {
					mont_capa = krr.getMontReconst();
				}
				EuMembre memb = null;
				EuMembreMorale moral = null;
				if (membre instanceof EuMembre) {
					memb = (EuMembre) membre;
				} else {
					moral = (EuMembreMorale) membre;
				}
				if (Double.valueOf(mont_capa).equals(krr.getMontCapa())) {
					EuNn nn = nnService.EmettreNn(krr.getEuProduit().getCodeProduit().toUpperCase(), mont_capa, "Auto");
					EuUtiliserNn utilNn = new EuUtiliserNn();
					if (Objects.nonNull(memb)) {
						utilNn.setCodeMembreNb(memb.getCodeMembre());
					} else {
						utilNn.setCodeMembreNb(moral.getCodeMembreMorale());
					}
					utilNn.setCodeMembreNn("NN-" + nn.getIdNn());
					utilNn.setCodeProduit(krr.getEuProduit().getCodeProduit());
					utilNn.setCodeSms(null);
					utilNn.setDateTransfert(new Date());
					utilNn.setIdOperation(null);
					utilNn.setIdUtilisateur(null);
					utilNn.setMontTransfert(mont_capa);
					utilNn.setNumBon(null);
					utilNn.setMotif(krr.getTypeKrr());
					utiliserNnRepo.save(utilNn);

					if (krr.getTypeKrr().equals("krrBNP") || krr.getTypeKrr().equals("krr")) {
						EuCategorieCompte cat = null;
						String codeCompteNn = "NN-";

						if (krr.getEuProduit().getCodeProduit().equals("RGPr")) {
							cat = new EuCategorieCompte("TPAGCRPG");
							codeCompteNn = "NN-" + cat.getCodeCat() + memb.getCodeMembre();
						} else if (krr.getEuProduit().getCodeProduit().equals("PaR")) {
							cat = new EuCategorieCompte("TPaR");
						} else {
							cat = new EuCategorieCompte("TPAGCI");
							codeCompteNn = "NN-" + cat.getCodeCat() + moral.getCodeMembreMorale();
						}
						EuCompte compteNn = new EuCompte();
						compteNn.setCodeCompte(codeCompteNn);
						compteNn.setDateAlloc(new Date());
						compteNn.setDesactiver("N");
						compteNn.setEuCategorieCompte(cat);
						if (Objects.nonNull(moral)) {
							compteNn.setEuMembreMorale(moral);
						} else {
							compteNn.setEuMembre(memb);
						}
						compteNn.setSolde(mont_capa);
						compteNn.setEuTypeCompte(new EuTypeCompte("NN"));
						compteNn.setLibCompte("Compte " + cat.getCodeCat());
						compteNn.setCardprinteddate(null);
						compteNn.setCardprintediddate(0);
						compteNn.setMifarecard(null);
						compteNn.setNumeroCarte(null);
						compteRepo.save(compteNn);
					}
				}
			}
		}
		return mont_capa;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<TableauBnp> doTableauBnp(String codeBnp) {
		List<TableauBnp> tbl = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(codeBnp)) {
			EuBnp bnp = bnpRepo.getEuBnpWithTypeBnp(codeBnp);
			EuBnpCredit creditBnp = bnpCreditRepo.getBnpCreditByCodeBnp(codeBnp);
			if (Objects.nonNull(bnp) && Objects.nonNull(creditBnp)) {
				EuCompteCredit credit = creditBnp.getEuCompteCredit();
				EuTypeBnp typeBnp = bnp.getEuTypeBnp();
				double montbnp = bnp.getMontantBnp();
				double som_par = 0;
				double montConus = 0;
				double montPar = 0;
				double montPanu = 0;
				double montFs = 0;
				double m_credit = Math.round(calculBcStandard(credit.getMontantPlace()));
				montConus = Math.round(m_credit * (typeBnp.getTxConus() / 100));
				montPar = Math.round(m_credit * (typeBnp.getTxPar() / 100));
				int periode = 1;
				while (montbnp > som_par) {
					double mont_credit = m_credit;
					if (typeBnp.getCodeTypeBnp().equals("CMIT") || typeBnp.getCodeTypeBnp().equals("CAPU")) {
						montPanu = Math.round(mont_credit * (typeBnp.getTxPanu() / 100));
						if (isAutoEnroller(bnp.getCodeMembreBenef())) {
							montFs = Math.round(mont_credit * (typeBnp.getTxFs() / 100));
						}
					} else if (typeBnp.getCodeTypeBnp().equals("CAIPC")) {
						if (isAutoEnroller(bnp.getCodeMembreBenef())) {
							montFs = Math.round(mont_credit * (typeBnp.getTxFs() / 100));
						}
					}
					tbl.add(new TableauBnp(credit.getIdCredit(), codeBnp, bnp.getMontantBnp(), m_credit, montConus,
							montPar, montPanu, montFs, periode));
					m_credit = Math.round(calculBcStandard(credit.getMontantPlace() - montPar));
					som_par += montPar;
					periode += 1;
				}
			}
		}
		return tbl;
	}

	@SuppressWarnings("unused")
	public List<TableauBnp> doTableauBnp(String codeBnp, boolean fs, double montant) {
		List<TableauBnp> tbl = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(codeBnp)) {
			EuTypeBnp typeBnp = typeBnpRepo.findOne(codeBnp);
			double montbnp = montant;
			double som_par = 0;
			double som_conus = 0;
			double som_fs = 0;
			double som_panu = 0;
			double montPanu = 0;
			double montFs = 0;
			int periode = 1;
			double m_credit = Math.round(calculBcStandard(montant));
			double montConus = Math.round(m_credit * (typeBnp.getTxConus() / 100));
			double montPar = Math.round(m_credit * (typeBnp.getTxPar() / 100));
			while (montbnp > som_par) {
				double mont_credit = m_credit;
				if (typeBnp.getCodeTypeBnp().equals("CMIT") || typeBnp.getCodeTypeBnp().equals("CAPU")) {
					if (fs) {
						montPanu = Math.round(mont_credit * (typeBnp.getTxPanu() / 100));
						som_panu += montPanu;
						montFs = Math.round(mont_credit * (typeBnp.getTxFs() / 100));
						som_fs += montFs;
					} else {
						montPanu = Math.round(mont_credit * ((typeBnp.getTxPanu() + typeBnp.getTxFs()) / 100));
						som_panu += montPanu;
					}
				} else if (typeBnp.getCodeTypeBnp().equals("CAIPC")) {
					if (fs) {
						montFs = Math.round(mont_credit * (typeBnp.getTxFs() / 100));
						som_fs += montFs;
					}
				} else {

				}
				tbl.add(new TableauBnp(1, codeBnp, montbnp, m_credit, montConus, montPar, montPanu, montFs, periode));
				montant = montant - montPar;
				m_credit = Math.round(calculBcStandard(montant));
				som_par += montPar;
				periode += 1;
			}
		}
		return tbl;
	}
}
