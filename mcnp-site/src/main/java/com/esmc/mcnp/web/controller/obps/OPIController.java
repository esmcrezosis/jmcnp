package com.esmc.mcnp.web.controller.obps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.domain.dto.obps.TpaGcpRequest;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.components.OPIComponent;
import com.esmc.mcnp.infrastructure.components.SmsComponent;
import com.esmc.mcnp.infrastructure.services.acteurs.EuBanqueService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.obps.EuGcpService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.obpsd.EuTpagcpService;
import com.esmc.mcnp.infrastructure.services.obpsd.EuTraiteService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.opi.ApproOpi;
import com.esmc.mcnp.web.dto.opi.Opi;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.util.ErrorDTO;

@Controller
public class OPIController extends BaseController {

    private OPIComponent opiCompo;
    private EuBanqueService bankService;
    private EuMembreMoraleService moraleService;
    private EuMembreService membreService;
    private EuTegcService tegcService;
    private SmsComponent smsComp;
    private EuParametresService paramService;
    private EuTpagcpService tpagcpService;
    private EuTraiteService traiteService;
    private EuGcpService gcpService;

    @Autowired
    public OPIController(OPIComponent opiCompo, EuTpagcpService tpagcpService, EuBanqueService bankService,
                         EuMembreMoraleService moraleService, EuMembreService membreService, EuTegcService tegcService,
                         SmsComponent smsComp, EuParametresService paramService, EuTraiteService traiteService,
                         EuGcpService gcpService) {
        this.opiCompo = opiCompo;
        this.bankService = bankService;
        this.moraleService = moraleService;
        this.membreService = membreService;
        this.tegcService = tegcService;
        this.smsComp = smsComp;
        this.paramService = paramService;
        this.tpagcpService = tpagcpService;
        this.traiteService = traiteService;
        this.gcpService = gcpService;
    }

    @ModelAttribute("banks")
    public List<EuBanque> getBanks() {
        return bankService.list();
    }

    @RequestMapping(value = "/opi", method = RequestMethod.GET)
    public String emettreOpi(Model model) {
        EuUtilisateur user = getCurrentUser();
        if (user.getEuUserGroup().getCodeGroupe().equals("cnp_tegcp")) {
            Opi opi = new Opi();
            Integer nbreOpi = paramService.getParam("prc", "nr");
            List<EuTegc> tegcs;
            if (user.getCodeMembre().endsWith("P")) {
                tegcs = tegcService.findByCodeMembrePhysique(user.getCodeMembre());
            } else {
                tegcs = tegcService.findByCodeMembre(user.getCodeMembre());
            }
            if (StringUtils.isBlank(tegcs.get(0).getTypeTegc())
                    || "specifique".equalsIgnoreCase(tegcs.get(0).getTypeTegc())
                    || "externe".equalsIgnoreCase(tegcs.get(0).getTypeTegc())) {
                opi.setNbre(1);
                opi.setMarge(0);
            } else {
                opi.setNbre(nbreOpi);
                opi.setMarge(1);
            }
            opi.setCodeMembre(user.getCodeMembre());

            model.addAttribute("disabled", true);
            model.addAttribute("tegcs", tegcs);
            model.addAttribute("opi", opi);
            return "distributeur/opi";
        } else {
            model.addAttribute("message", "Vous n'avez pas le droit d'accéder à cette page");
            return "error";
        }
    }

    @RequestMapping(value = "/approOpi", method = RequestMethod.GET)
    public String approvisionnerOpi(@ModelAttribute ApproOpi approOpi) {
        return "opi/approvisionner";
    }

    @RequestMapping(value = "/approOpi", method = RequestMethod.POST)
    public ResponseEntity<Result> doApproOpi(@RequestBody ApproOpi approOpi, BindingResult errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            if (approOpi.getMontant() > 0 && StringUtils.isNotBlank(approOpi.getCodeMembreVendeur())
                    && StringUtils.isNotBlank(approOpi.getCodeMembreAcheteur())
                    && StringUtils.isNotBlank(approOpi.getMoyenPayement())
                    && StringUtils.isNotBlank(approOpi.getNumeroCompte()) && approOpi.getTraites().size() > 0
                    && approOpi.getIdTpagcp() > 0) {
                TpaGcpRequest traiteDto = new TpaGcpRequest(approOpi.getIdTpagcp(), approOpi.getNbreOpi(),
                        approOpi.getTraites());
                List<TpaGcpRequest> dtraites = new ArrayList<>();
                dtraites.add(traiteDto);
                if (opiCompo.approvisionnerOpi(approOpi.getCodeMembreVendeur(), approOpi.getCodeMembreAcheteur(),
                        approOpi.getMoyenPayement(), approOpi.getNumeroCompte(), dtraites, approOpi.getMontant())) {
                    return new ResponseEntity<>(new Result(1, "Opération effectuée avec succès"), HttpStatus.OK);
                } else {
                    getLog().error("Echec de l'approvisionnement d'OPI");
                    return new ResponseEntity<>(new Result(0, "Echec de l'Opération : Echec de l'approvisionnement"),
                            HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            getLog().error("Echec de l'opération", e);
            return new ResponseEntity<>(new Result(0, "Echec de l'Opération : " + e.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<Result>(new Result(0, "Echec de l'opération"), HttpStatus.OK);
    }

    @RequestMapping(value = "/tpagcp", method = RequestMethod.GET)
    public ResponseEntity<?> findOpiByMembre(@RequestParam("codeMembre") String codeMembre) {
        if (StringUtils.isNotBlank(codeMembre)) {
            List<EuTpagcp> results = tpagcpService.findByMembre(codeMembre);
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Veuillez fournir le code membre"));
    }

    @RequestMapping(value = "/listTraite", method = RequestMethod.GET)
    public ResponseEntity<?> findTraiteByMembre(@RequestParam("id") Long id) {
        if (id != null) {
            List<EuTraite> results = traiteService.findTraiteDisponible(id);
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Veuillez fournir numero Traite"));
    }

    @Transactional
    @RequestMapping(value = "/opi", method = RequestMethod.POST)
    public String emissionOpi(@ModelAttribute("opi") Opi opi, Model model, RedirectAttributes rmodel,
                              BindingResult errors) {
        try {
            EuMembreMorale morale = null;
            EuMembre membre = null;
            if (opi.getCodeMembre().endsWith("P")) {
                membre = membreService.findById(opi.getCodeMembre());
                if (Objects.isNull(membre) || membre.getDesactiver() == 1) {
                    model.addAttribute("message",
                            "Vous n'êtes pas autorisé à faire cette opération. \n Veuillez contacter la direction de l'ESMC");
                    return "distributeur/opi";
                }
            } else {
                morale = moraleService.findById(opi.getCodeMembre());
                if (Objects.isNull(morale) || morale.getDesactiver() == 1) {
                    model.addAttribute("message",
                            "Vous n'êtes pas autorisé à faire cette opération. \n Veuillez contacter la direction de l'ESMC");
                    return "distributeur/opi";
                }
            }
            if (opi.getTypeGcp().equalsIgnoreCase("COM")) {
                model.addAttribute("message",
                        "Vous n'êtes pas autorisé à faire cette opération. \n L'émission d'OPI à partir de BL Commission est provisoirement suspendu!!!");
                return "distributeur/opi";
            }
            if (opi.getDateDebut() != null) {
                Date date = new Date();
                if (opi.getDateDebut().compareTo(date) <= 0) {
                    model.addAttribute("message", "Veuillez mettre une date postérieure à la date d'aujourd'hui!");
                    return "distributeur/opi";
                }
            }
            if (opi.getDiferre() < 0) {
                model.addAttribute("message", "Veuillez mettre une valeur supérieure à zero pour le différé");
                return "distributeur/opi";
            }
            EuTegc tegc = tegcService.findByCodeTegc(opi.getCodeTegc());
            if (tegc.getTypeTegc().equalsIgnoreCase("DISTRIBUTEUR")
                    || tegc.getTypeTegc().equalsIgnoreCase("PRESTATAIRE")) {
                opi.setMarge(1);
            }
            Double solde_gcps = gcpService.getSoldeByTegcAndType(tegc.getCodeTegc(), opi.getTypeGcp());
            if (solde_gcps < opi.getMontant()) {
                model.addAttribute("message",
                        "Le solde des GCp de ce type est insuffisant pour effectuer cette opération");
                return "distributeur/opi";
            }
            EuBon bon = opiCompo.emetreOpi(opi.getTypeGcp(), opi.getCodeMembre(), opi.getCodeTegc(),
                    opi.getModePaiement(), opi.getReferencePaiement(), opi.getMontant(), opi.getNbre(), opi.getSerie(),
                    opi.getTypeOpi(), opi.getMontTranche1(), opi.getDiferre(), opi.getDateDebut(), opi.getMarge(),
                    opi.getTtc(), opi.getTauxTva());
            if (Objects.nonNull(bon) && StringUtils.isNotBlank(bon.getBonNumero())) {
                String code = ServerUtil.GenererUniqueCode();
                String smsBody = "ESMC: Veuillez entrer ce code " + bon.getBonNumero()
                        + " dans le champs Numéro du Bon pour valider l'émission d'OPI de "
                        + Math.floor(opi.getMontant());
                if (opi.getCodeMembre().endsWith("M")) {
                    smsComp.createAndSendCode(code, morale.getCodeMembreMorale(), smsBody);
                } else {
                    smsComp.createAndSendCode(code, membre.getCodeMembre(), smsBody);
                }
                rmodel.addFlashAttribute("vopi", opi);
                rmodel.addFlashAttribute("message",
                        "Votre émission d'OPI est enregistrée, veuillez envoyer ce code " + code.substring(0, 5)
                                + " au 3162 TOGOCEL" + " pour recevoir le code de validation de votre émission");
                return "redirect:/confirmeropi";
            } else {
                model.addAttribute("message", "veuillez renseigner tous les champs!");
                return "distributeur/opi";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("message", "Erreur d'éxécution : " + e.getMessage());
            return "distributeur/opi";
        }

    }

    @RequestMapping(value = "/nbreOpi", method = RequestMethod.GET)
    public @ResponseBody
    Integer getNbreOpi(@RequestParam("codeTegc") String codeTegc) {
        if (StringUtils.isNotBlank(codeTegc)) {
            EuTegc tegc = tegcService.getById(codeTegc);
            if (Objects.nonNull(tegc) && StringUtils.isNotBlank(tegc.getTypeTegc())) {
                if (tegc.getTypeTegc().equalsIgnoreCase("specifique")
                        || tegc.getTypeTegc().equalsIgnoreCase("externe")) {
                    return 1;
                }
            }
        }
        return paramService.getParam("OPI", "serie");
    }

    @RequestMapping(value = "/montOpi", method = RequestMethod.GET)
    public @ResponseBody
    double calculMontMaj(@RequestParam("montant") Double montant) {
        if (montant != null) {
            Double pck = paramService.getParametre("pck", "nr");
            Double prk = paramService.getParametre("OPI", "prk");
            Double txEsc = paramService.getParametre("taux", "escompte");
            return Math.floor(((montant * prk) / (pck * (1 + txEsc / 100))));
        }
        return 0;
    }

    @RequestMapping(value = "/montTranche", method = RequestMethod.GET)
    public @ResponseBody
    double calculMontTranche(@RequestParam("montant") Double montant,
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

    @RequestMapping(value = "/confirmeropi", method = RequestMethod.GET)
    public String validerEmission(@ModelAttribute("vopi") Opi opi) {
        return "distributeur/confirmeropi";
    }

    @Transactional
    @RequestMapping(value = "/confirmeropi", method = RequestMethod.POST)
    public String validationEmission(@ModelAttribute("vopi") Opi opi, BindingResult errors, Model mod,
                                     RedirectAttributes model) {
        try {
            if (opiCompo.validerEmissionOpi(opi.getNumeroBon())) {
                model.addFlashAttribute("message", "L'opération est terminée avec succès");
                return "redirect:/opi";
            } else {
                mod.addAttribute("message", "Veuillez verifier le numéro de votre bon!");
                return "distributeur/confirmeropi";
            }
        } catch (Exception e) {
            mod.addAttribute("message", "Erreur : " + e.getMessage());
            return "distributeur/confirmeropi";
        }
    }

}
