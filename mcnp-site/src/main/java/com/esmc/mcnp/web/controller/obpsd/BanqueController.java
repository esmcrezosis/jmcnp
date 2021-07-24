package com.esmc.mcnp.web.controller.obpsd;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.components.CSVService;
import com.esmc.mcnp.components.OPIComponent;
import com.esmc.mcnp.components.SmsComponent;
import com.esmc.mcnp.core.utils.ExcelPOIHelper;
import com.esmc.mcnp.core.utils.MyCell;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.dto.obps.TegcView;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.SoldeInsuffisantException;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.bc.EuTypePrk;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.acteurs.EuBanqueService;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.bc.EuDetailBonOpiService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.obps.EuTypePrkService;
import com.esmc.mcnp.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.services.setting.EuParametresService;
import com.esmc.mcnp.util.DetailBonOpiMapper;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.opi.Opi;
import com.esmc.mcnp.web.dto.opi.OpiCriteria;
import com.esmc.mcnp.web.dto.smcipn.PaiementDto;
import com.esmc.mcnp.web.dto.opi.PayerOpi;
import com.google.common.collect.Lists;

@Controller
public class BanqueController extends BaseController {

	private OPIComponent paieCompo;
	private EuTpagcpService tpagcpService;
	private EuBanqueService bankService;
	private DetailBonOpiMapper detMapper;
	private EuDetailBonOpiService detBonOpiService;
	private EuMembreMoraleService moraleService;
	private EuMembreService membreService;
	private EuBonService bonService;
	private EuTegcService tegcService;
	private SmsComponent smsComp;
	private EuTypePrkService typePrkRepo;
	private EuParametresService paramService;
	private CSVService csvService;

	private @Resource ExcelPOIHelper excelHelper;

	@Autowired
	public BanqueController(OPIComponent paieCompo, EuTpagcpService tpagcpService, EuBanqueService bankService,
			DetailBonOpiMapper detMapper, EuDetailBonOpiService detBonOpiService, EuMembreMoraleService moraleService,
			EuMembreService membreService, EuBonService bonService, EuTegcService tegcService, SmsComponent smsComp,
			EuTypePrkService typePrkRepo, EuParametresService paramService, CSVService csvService) {
		super();
		this.paieCompo = paieCompo;
		this.tpagcpService = tpagcpService;
		this.bankService = bankService;
		this.detMapper = detMapper;
		this.detBonOpiService = detBonOpiService;
		this.moraleService = moraleService;
		this.membreService = membreService;
		this.bonService = bonService;
		this.tegcService = tegcService;
		this.smsComp = smsComp;
		this.typePrkRepo = typePrkRepo;
		this.paramService = paramService;
		this.csvService = csvService;
	}

	@RequestMapping(value = "/banque", method = RequestMethod.GET)
	public String espacebanque() {
		return "banque/index";
	}

	@ModelAttribute("banks")
	public List<EuBanque> getBanks() {
		return bankService.list();
	}

	@ModelAttribute("prks")
	public List<EuTypePrk> getPrks() {
		return typePrkRepo.list();
	}

	@RequestMapping(value = "/payeropi")
	public String payerOpi(@ModelAttribute("codeMembre") String codeMembre, @ModelAttribute("typeOpi") String typeReg,
			@ModelAttribute("payer") PayerOpi payer) {
		return "banque/opi";
	}

	@RequestMapping(value = "/escompte", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Object doEscompte(@RequestParam("codeMembre") String codeMembre,
			@RequestParam("type") String type, @RequestParam("codeBanque") String codeBanque,
			@RequestParam("typeEsc") String typeEsc, @RequestParam("modeReg") String modeReg,
			@RequestParam(value = "nbre", defaultValue = "0") Integer nbre, @RequestParam("montant") Double montant,
			@RequestParam("opis") Long[] opis, RedirectAttributes model) {
		try {
			if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(modeReg)) {
				EuMembreMorale moral = moraleService.findById(codeMembre);
				if (!Objects.nonNull(moral)) {
					return "Ce membre n'existe pas";
				}
				EuBon bon = paieCompo.emettreBonCommande(Arrays.asList(opis), codeMembre, type, codeBanque, modeReg,
						typeEsc, nbre, montant);
				EuBanque bank = bankService.findById(codeBanque);
				PayerOpi popi = new PayerOpi();
				popi.setCodeMembre(bon.getBonCodeMembreEmetteur());
				popi.setCodeMembrePbf(bank.getCodeMembreMorale());
				popi.setModeReg(modeReg);
				popi.setNbre(nbre);
				popi.setTypeEsc(typeEsc);
				popi.setMontant(bon.getBonMontant());

				detBonOpiService.add(detMapper.getDetailBon(popi, bon));
				return popi;
			} else {
				return "Veuillez renseigner les informations sur le Membre: son Numéro, le Type du Bon à payer et le mode de payement";
			}
		} catch (NullPointerException | DataAccessException | SoldeInsuffisantException | CompteNonIntegreException e) {
			if (e instanceof DataAccessException || e instanceof NullPointerException) {
				return "Erreur Interne au Serveur de Base de données; veuillez signaler cette erreur à l'ESMC";
			}
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/te", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<TegcView> fetchTe(@RequestParam("codeMembre") String codeMembre) {
		return tegcService.findbyMembre(codeMembre);
	}

	@RequestMapping(value = "/emettreopi", method = RequestMethod.GET)
	public String emettreOpi(Model model) {
		Opi opi = new Opi();
		EuUtilisateur user = getCurrentUser();
		if (user.getEuUserGroup().getCodeGroupe().equals("cnp_tegcp")) {
			Integer nbreOpi = paramService.getParam("OPI", "serie");
			List<EuTegc> tegcs;
			if (user.getCodeMembre().endsWith("P")) {
				tegcs = tegcService.findByCodeMembrePhysique(user.getCodeMembre());
			} else {
				tegcs = tegcService.findByCodeMembre(user.getCodeMembre());
			}
			if (StringUtils.isNotBlank(tegcs.get(0).getTypeTegc()) && (tegcs.get(0).getTypeTegc().equals("specifique")
					|| tegcs.get(0).getTypeTegc().equals("externe"))) {
				opi.setNbre(1);
			} else {
				opi.setNbre(nbreOpi);
			}
			opi.setCodeMembre(user.getCodeMembre());

			model.addAttribute("disabled", true);
			model.addAttribute("tegcs", tegcs);
		} else {
			model.addAttribute("disabled", false);
		}
		model.addAttribute("opi", opi);
		return "banque/emettreOpi";
	}

	@Transactional
	@RequestMapping(value = "/emettreopi", method = RequestMethod.POST)
	public String emissionOpi(@ModelAttribute("opi") Opi opi, Model model, RedirectAttributes rmodel,
			BindingResult errors) {
		try {
			if (opi.getDateDebut() != null) {
				Date date = new Date();
				if (opi.getDateDebut().compareTo(date) < 0) {
					model.addAttribute("message", "Veuillez mettre une date postérieure à la date d'aujourd'hui!");
					return "banque/emettreOpi";
				}
			}
			if (opi.getDiferre() < 0) {
				model.addAttribute("message", "Veuillez mettre une valeur supérieure à zero pour le différé");
				return "banque/emettreOpi";
			}
			EuBon bon = paieCompo.emetreOpiBonCommande(opi.getTypeGcp(), opi.getCodeMembre(), opi.getCodeTegc(),
					opi.getModePaiement(), opi.getMontant(), opi.getNbre(), opi.getSerie(), opi.getTypeOpi(),
					opi.getMontTranche1(), opi.getDiferre(), opi.getDateDebut(), opi.getReferencePaiement(), 0, 0,
					opi.getMarge(), opi.getTtc(), opi.getTauxTva());
			if (Objects.nonNull(bon) && StringUtils.isNotBlank(bon.getBonNumero())) {
				String code = ServerUtil.GenererUniqueCode();
				String smsBody = "ESMC: Veuillez entrer ce code " + bon.getBonNumero()
						+ " dans le champs Numéro du Bon pour valider l'émission d'OPI de "
						+ Math.floor(opi.getMontant());
				if (opi.getCodeMembre().endsWith("M")) {
					EuMembreMorale morale = moraleService.findById(opi.getCodeMembre());
					smsComp.createAndSendCode(code, morale.getCodeMembreMorale(), smsBody);
				} else {
					EuMembre membre = membreService.findById(opi.getCodeMembre());
					smsComp.createAndSendCode(code, membre.getCodeMembre(), smsBody);
				}
				rmodel.addFlashAttribute("vopi", opi);
				rmodel.addFlashAttribute("message",
						"Votre émission d'OPI est enregistrée, veuillez envoyer ce code " + code.substring(0, 5)
								+ " au 3162 TOGOCEL" + " pour recevoir le code de validation de votre émission");
				return "redirect:/validopi";
			} else {
				model.addAttribute("message", "veuillez renseigner tous les champs!");
				return "banque/emettreOpi";
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			model.addAttribute("message", "Erreur d'éxécution : " + e.getMessage());
			return "banque/emettreOpi";
		}

	}

	@RequestMapping(value = "/calculMontOpi", method = RequestMethod.GET)
	public @ResponseBody double calculMontMaj(@RequestParam("montant") Double montant) {
		if (montant != null) {
			Double pck = paramService.getParametre("pck", "nr");
			Double prk = paramService.getParametre("OPI", "prk");
			Double txEsc = paramService.getParametre("taux", "escompte");
			return Math.floor(((montant * prk) / (pck * (1 + txEsc / 100))));
		}
		return 0;
	}

	@RequestMapping(value = "/calculMontTranche", method = RequestMethod.GET)
	public @ResponseBody double calculMontTranche(@RequestParam("montant") Double montant,
			@RequestParam(defaultValue = "0.0", value = "tranche1") Double tranche, @RequestParam("nbre") Double nbre) {
		if (montant != null && nbre != null) {
			if (tranche > 0) {
				return Math.floor((montant - tranche) / nbre);
			} else {
				return Math.floor(montant / nbre);
			}
		}
		return 0;
	}

	@RequestMapping(value = "/validopi", method = RequestMethod.GET)
	public String validerEmission(@ModelAttribute("vopi") Opi opi) {
		return "banque/validOpi";
	}

	@Transactional
	@RequestMapping(value = "/validopi", method = RequestMethod.POST)
	public String validationEmission(@ModelAttribute("vopi") Opi opi, BindingResult errors, Model mod,
			RedirectAttributes model) {
		try {
			if (paieCompo.creerOpi(opi.getNumeroBon())) {
				model.addFlashAttribute("message", "L'opération est terminée avec succès");
				return "redirect:/emettreopi";
			} else {
				mod.addAttribute("message", "Veuillez verifier le numéro de votre bon!");
				return "banque/validOpi";
			}
		} catch (Exception e) {
			mod.addAttribute("message", "Erreur : " + e.getMessage());
			return "banque/validOpi";
		}
	}

	@RequestMapping(value = "/bonopi", method = RequestMethod.GET)
	public @ResponseBody Object getBonOpi(@RequestParam("numeroBon") String numeroBon) {
		if (StringUtils.isNotBlank(numeroBon)) {
			EuBon bon = bonService.findByBonCode(numeroBon, 0);
			if (Objects.nonNull(bon)) {
				EuDetailBonOpi detbonOpi = detBonOpiService.findByEuBon_BonNumero(bon.getBonNumero());
				Opi opi = new Opi(detbonOpi.getTypeGcp(), bon.getBonCodeMembreEmetteur(), null, null,
						detbonOpi.getCodeBanque(), bon.getBonMontant());
				return opi;
			} else {
				return "Ce Numéro de Bon n'existe pas";
			}
		}
		return null;
	}

	@RequestMapping(value = "/validationopi", method = RequestMethod.GET)
	public String validePaiementNon(@ModelAttribute("valopi") PayerOpi payerOpi) {
		return "banque/valideopi";
	}

	@RequestMapping(value = "/payerbonopi", method = RequestMethod.GET)
	public @ResponseBody PayerOpi getDetailOpiBon(@RequestParam("numeroBon") String numeroBon) {
		if (StringUtils.isNotBlank(numeroBon)) {
			return detMapper.getDetailOpiPayer(detBonOpiService.findByEuBon_BonNumero(numeroBon), numeroBon);
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "/valideropi", method = RequestMethod.POST)
	public @ResponseBody String validerOpi(@RequestParam("codeMembre") String codeMembre,
			@RequestParam("codeMembrePbf") String codeMembrePbf, @RequestParam("typeEsc") String typeEsc,
			@RequestParam("modeReg") String modeReg, @RequestParam(value = "nbre", defaultValue = "0") Integer nbre,
			@RequestParam("numeroBon") String numeroBon, @RequestParam("montant") Double montant) {
		try {
			if (paieCompo.payerGcp(codeMembrePbf, modeReg, typeEsc, nbre, numeroBon, montant)) {
				return "OK";
			} else {
				return "Echec de l'operation : Veuillez signaler cet echec à l'ESMC";
			}
		} catch (NullPointerException | DataAccessException | SoldeInsuffisantException | CompteNonIntegreException e) {
			if (e instanceof DataAccessException || e instanceof NullPointerException) {
				return "Erreur Interne au Serveur de Base de données; veuillez signaler cette erreur à l'ESMC";
			}
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/tpagcp/{typeOpi}/{code}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EuTpagcp> listGcps(@PathVariable(value = "code") String codeMembre,
			@PathVariable(value = "typeOpi") String typeOpi) {
		if (StringUtils.isNotBlank(codeMembre)) {
			return ListUtils.emptyIfNull(tpagcpService.findByMembreAndModeReg(codeMembre, typeOpi));
		} else {
			return Lists.newArrayList();
		}
	}

	@RequestMapping(value = "/payerba", method = RequestMethod.GET)
	public String payementBa(@ModelAttribute("paiement") PaiementDto paiememt, Model model) {
		return "banque/payement";
	}

	@Transactional
	@RequestMapping(value = "/payerba", method = RequestMethod.POST)
	public String doPayementBa(@ModelAttribute("paiement") PaiementDto paiement, RedirectAttributes model) {
		System.out.println("Doing OPI Salaire");
		try {
			EuBon bon = null;
			if (paiement.getTypeBa().equals("PaR")) {
				bon = paieCompo.emettreOpiBa(paiement.getCodeMembre(), paiement.getTypeBa(), paiement.getCodeBanque(),
						paiement.getMontant(), paiement.getNbre(), paiement.getSerie());
			} else {
				System.out.println("CNCS OPI");
				bon = paieCompo.emettreOpiCncs(paiement.getCodeMembre(), paiement.getTypeBa(), paiement.getCodeBanque(),
						paiement.getMontant(), paiement.getNbre(), paiement.getSerie());
			}
			if (Objects.nonNull(bon)) {
				String code = ServerUtil.GenererUniqueCode();
				String smsBody = "ESMC: Votre code de validation de l'émission d'OPI de "
						+ Math.floor(paiement.getMontant()) + " est : " + bon.getBonNumero();
				if (paiement.getCodeMembre().endsWith("")) {
					EuMembre membre = membreService.findById(paiement.getCodeMembre());
					smsComp.sendCodeSms(membre.getCodeMembre(), smsBody, code.substring(0, 5).toUpperCase(),
							membre.getTelMembre());
				} else {
					EuMembreMorale morale = moraleService.findById(paiement.getCodeMembre());
					smsComp.sendCodeSms(morale.getCodeMembreMorale(), smsBody, code.substring(0, 5).toUpperCase(),
							morale.getTelMembre());
				}
				model.addFlashAttribute("message",
						"L'émission des OPI a été bien effectuée, Un code de validation vous a été envoyé"
								+ " veuillez le saisir pour valider votre émission");
				return "redirect:/validerba";
			} else {
				model.addAttribute("message", "Echec de l'opération : veuillez en revoyant vos infos");
				return "banque/payement";
			}
		} catch (NullPointerException | DataAccessException | SoldeInsuffisantException | CompteNonIntegreException e) {
			if (e instanceof DataAccessException || e instanceof NullPointerException) {
				model.addAttribute("message",
						"Echec de l'opération : Erreur Interne au Serveur de Base de données; veuillez signaler cette erreur à l'ESMC");
			} else {
				model.addAttribute("message", e.getMessage());
			}
			return "banque/payement";
		}
	}

	@RequestMapping(value = "/validerba", method = RequestMethod.GET)
	public String validerPayementBa(@ModelAttribute("paiement") PaiementDto paiement, Model model) {
		return "banque/validePaiement";
	}

	@RequestMapping(value = "/validerba", method = RequestMethod.POST)
	public String dovaliderPayementBa(@ModelAttribute("paiement") PaiementDto paiememt, RedirectAttributes model) {
		if (paieCompo.creerOpi(paiememt.getNumeroBon())) {
			model.addFlashAttribute("message", "L'opération est terminée avec succès");
			return "redirect:/payerba";
		} else {
			model.addAttribute("message", "Veuillez verifier le numéro de votre bon!");
			return "banque/validePaiement";
		}
	}

	@RequestMapping(value = "/updateopi", method = RequestMethod.GET)
	public String updateDate(@ModelAttribute OpiCriteria dateOpi) {
		return "banque/update";
	}

	@RequestMapping(value = "/updateopi", method = RequestMethod.POST)
	public String doUpdateDate(@ModelAttribute OpiCriteria criteria, BindingResult errors, Model model,
			RedirectAttributes rmodel) {
		if (!errors.hasErrors()) {
			System.out.println("Date Début : " + criteria.getDateDebut().toString());
			try {
				paieCompo.updateTraiteDateFin(criteria.getCodeMembre(), criteria.getDateDebut(), criteria.getDateFin(),
						criteria.getDuree());
				rmodel.addFlashAttribute("message", "L'opération est terminée avec succès");
				return "redirect:/updateopi";
			} catch (Exception e) {
				model.addAttribute("message", "Erreur : " + e.getMessage());
				return "banque/update";
			}
		} else {
			StringBuilder sb = new StringBuilder();
			errors.getFieldErrors().forEach(e -> {
				sb.append(e.getDefaultMessage());
			});
			model.addAttribute("message", "Erreur : " + sb.toString());
			return "banque/update";
		}
	}

	@RequestMapping(value = "/updateTraite", method = RequestMethod.GET)
	public String updateTraite(@ModelAttribute OpiCriteria dateOpi) {
		return "banque/updateTraite";
	}

	@RequestMapping(value = "/updateTraite", method = RequestMethod.POST)
	public String doUpdateTraite(@ModelAttribute OpiCriteria dateOpi, BindingResult errors, Model model,
			RedirectAttributes rmodel) {
		if (!errors.hasErrors()) {
			System.out.println("Date Début : " + dateOpi.getDateDebut().toString());
			try {
				paieCompo.updateTraite(dateOpi.getDateDebut(), dateOpi.getDateFin());
				rmodel.addFlashAttribute("message", "L'opération est terminée avec succès");
				return "redirect:/updateTraite";
			} catch (Exception e) {
				model.addAttribute("message", "Erreur : " + e.getMessage());
				return "banque/updateTraite";
			}
		} else {
			StringBuilder sb = new StringBuilder();
			errors.getFieldErrors().forEach(e -> {
				sb.append(e.getDefaultMessage());
			});
			model.addAttribute("message", "Erreur : " + sb.toString());
			return "banque/updateTraite";
		}
	}

	@GetMapping(value = "/uploadWari")
	public String uploadWari() {
		return "banque/uploadReglement";
	}

	@PostMapping(value = "/uploadWari")
	public String uploadWari(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes)
			throws IOException {
		List<MultipartFile> lfiles = Arrays.asList(files);
		System.out.println("Nombre de fichiers : " + lfiles.size());
		if (lfiles.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/uploadWari";
		} else {
			lfiles.forEach(file -> {
				try {
					String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					System.out.println("Extension du Fichier : " + extension);
					if (extension.equals("xls") || extension.equals("xlsx")) {
						Map<Integer, List<MyCell>> data = excelHelper.readExcel(file.getInputStream(), extension);
					   if (!data.values().isEmpty()) {
							int size = data.values().size();
							System.out.println("Nombre de lignes = " + size);
							data.values().forEach(d -> {
								String content = d.get(0).getContent();
								String numeroTraite = content.split("/")[0];
								if (numeroTraite.startsWith("00")) {
									csvService.payerTraiter(numeroTraite);
								}
							});
						}
					} else if (extension.equals("csv")) {
						csvService.loadReglementFromCSV(file.getInputStream());
					}
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
			});
			redirectAttributes.addFlashAttribute("message", "Upload successfully  made!");
			return "redirect:/uploadWari";
		}
	}

}
