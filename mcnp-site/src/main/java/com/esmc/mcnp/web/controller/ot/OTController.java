package com.esmc.mcnp.web.controller.ot;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.components.Payement;
import com.esmc.mcnp.components.TransfertUtility;
import com.esmc.mcnp.core.utils.StringUtils;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.ot.EuCandidature;
import com.esmc.mcnp.model.ot.EuCandidaturePoste;
import com.esmc.mcnp.model.ot.EuFormation;
import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.services.bc.EuBonService;
import com.esmc.mcnp.services.bc.EuCreditConsommerService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.obps.EuGcpService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.ot.EuCandidaturePosteService;
import com.esmc.mcnp.services.ot.EuCandidatureService;
import com.esmc.mcnp.services.ot.EuFormationService;
import com.esmc.mcnp.services.security.EuRolesService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.model.ot.Postuler;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/ot")
public class OTController extends BaseController {

    private final EuCandidatureService canService;
    private final EuCandidaturePosteService canPosteservice;
    private final EuCompteService compteService;
    private final EuCompteCreditService compteCreditService;
    private final EuRolesService roleService;
    private final EuGcpService gcpService;
    private final EuCreditConsommerService creditConsService;
    private final EuTegcService tegcService;
    private final EuBonService bonService;
    private final TransfertUtility transfertUtility;
    private final EuFormationService formationService;
    private final Payement payement;

    public OTController(EuCandidatureService canService, EuCandidaturePosteService canPosteservice,
                        EuTegcService tegcService, EuGcpService gcpService, EuCreditConsommerService creditConsService,
                        EuCompteService compteService, EuCompteCreditService compteCreditService, Payement payement,
                        TransfertUtility transfertUtility, EuBonService bonService, EuRolesService roleService,
                        EuFormationService formationService) {
        this.canService = canService;
        this.canPosteservice = canPosteservice;
        this.compteService = compteService;
        this.compteCreditService = compteCreditService;
        this.roleService = roleService;
        this.gcpService = gcpService;
        this.creditConsService = creditConsService;
        this.tegcService = tegcService;
        this.bonService = bonService;
        this.transfertUtility = transfertUtility;
        this.formationService = formationService;
        this.payement = payement;
    }

    @GetMapping(value = "pp")
    public String indexOt() {
        return "ot/index";
    }

    @GetMapping(value = "offre")
    public String listePoste() {
        return "ot/candidatures";
    }

    @GetMapping(value = "offre/{id}")
    public String editCandidature(@PathVariable("id") Integer idCandidature, Model model) {
        model.addAttribute("candidature", canService.getById(idCandidature));
        return "ot/candidature";
    }

    @PostMapping(value = "postuler")
    public ResponseEntity<?> postuler(Postuler postuler) {
        String codeMembre = getCurrentUser().getCodeMembre();
        if (StringUtils.isNotBlank(codeMembre)) {
            EuCandidaturePoste cp = canPosteservice.findById(postuler.getIdCandidaturePoste());
            if (Objects.nonNull(cp)) {
                //String codeMembreVendeur = "0000000000000000031M";
                String codeMembreVendeur = "0010010010010000212M";
                EuTegc tegc = tegcService.findByCodeMembre(codeMembreVendeur).get(0);
                String codeCompteBci = "NB-TBC-" + codeMembre;
                EuCompte bci = compteService.getById(codeCompteBci);
                if (bci.getSolde() >= 700000) {
                    List<EuCompteCredit> credits = compteCreditService.findByCodeCompte(codeCompteBci);
                    if (!credits.isEmpty()) {
                        Double somCredit = credits.stream().mapToDouble(EuCompteCredit::getMontantCredit).sum();
                        if (bci.getSolde() == somCredit) {
                            TransactionStatus transactionStatus = getTransactionStatus();
                            try {
                                EuBon bl = payement.createBonLivraison(codeMembreVendeur, codeMembre, "VPC", 700000);
                                bl.setBonExprimer(1);
                                bl.setBonDateExpression(new Date());
                                bonService.update(bl);

                                CalculBonInfo bonInfo = new CalculBonInfo();
                                bonInfo.setCatBon("r");
                                bonInfo.setTypeCredit("AG");
                                bonInfo.setPrk(5.6);
                                EuBon bon = transfertUtility.tansfertBC(codeMembre, "BCi", bonInfo, 5.6, 700000d);
                                payement.makeTransaction(codeMembreVendeur, codeMembre, "TBC", tegc, bon, bl, "DIST",
                                        700000);
                                payement.creerMarge(new Date(), 700000);
                                EuRoles role = roleService.getById(postuler.getIdPoste());
                                EuFormation formation = new EuFormation();
                                formation.setActiver(false);
                                formation.setDateBci(LocalDate.now());
                                formation.setCodeMembre(codeMembre);
                                formation.setLibelleFormation(role.getLibelleRoles());
                                formation.setIdCandidature(postuler.getIdCandidature());
                                formation.setIdCandidaturePoste(postuler.getIdCandidaturePoste());
                                formation.setCodeBci(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
                                formationService.create(formation);
                                getTransactionManager().commit(transactionStatus);
                                return ResponseEntity.ok(new Result(0, "Operation Bien effectuée"));
                            } catch (Exception e) {
                                getTransactionManager().rollback(transactionStatus);
                                getLog().error("Echec de la vente", e);
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(new Result(1, "Echec de la vente : Erreur Serveur " + e.getMessage()));
                            }
                        } else {
                            return ResponseEntity.badRequest()
                                    .body(new Result(1, "Echec de l'opération: Votre compte n'est pas correct, le solde ne correspond pas aux détails. Veuillez contactez ESMC"));
                        }
                    } else {
                        return ResponseEntity.badRequest()
                                .body(new Result(1, "Echec de l'opération: Votre compte n'est pas correct, ils manquent les détails de transactions y afférant. Veuillez contactez ESMC"));
                    }
                } else {
                    return ResponseEntity.badRequest()
                            .body(new Result(1, "Echec de l'opération: Le solde de votre compte est insuffisant"));
                }
            }
        }
        return ResponseEntity.badRequest()
                .body(new Result(1, "Echec de l'opération: Veuillez fournir les renseignemnts nécssaires"));
    }

    @ResponseBody
    @GetMapping(value = "pliste")
    public JqGrid<EuCandidature> listCatUnite(@RequestParam(value = "idPoste", required = false) Integer idPoste,
                                              @RequestParam(value = "codePoste", required = false) String codePoste,
                                              @RequestParam(value = "poste", required = false) String poste,
                                              @RequestParam(value = "date", required = false) LocalDate date,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "rows", required = false) Integer rows,
                                              @RequestParam(value = "sidx", required = false) String sortBy,
                                              @RequestParam(value = "sord", required = false) String order) {
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = Sort.by(Sort.Direction.DESC, orderBy);
            } else {
                sort = Sort.by(Sort.Direction.ASC, orderBy);
            }
        }

        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }
        Page<EuCandidature> candidatures = null;
        if (idPoste != null) {
            candidatures = canService.findByPosteId(idPoste, pageRequest);
        } else if (StringUtils.isNotBlank(codePoste)) {
            candidatures = canService.findByCodePoste(codePoste, pageRequest);
        } else if (StringUtils.isNotBlank(poste)) {
            candidatures = canService.findByLibellePoste(poste, pageRequest);
        } else if (date != null) {
            candidatures = canService.findByDate(date, pageRequest);
        } else {
            candidatures = canService.listAll(pageRequest);
        }
        JqGrid<EuCandidature> response = new JqGrid<>();
        if (candidatures.getTotalElements() > 0) {
            response.setRows(candidatures.getContent());
            response.setTotal(Integer.toString(candidatures.getTotalPages()));
            return response;
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
            return response;
        }
    }

    @ResponseBody
    @GetMapping(value = "cdposte")
    public JqGrid<EuCandidaturePoste> listPosteCand(
            @RequestParam(value = "idCandidature", required = false) Integer idCandidature,
            @RequestParam(value = "codePoste", required = false) String codePoste,
            @RequestParam(value = "poste", required = false) String poste,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sidx", required = false) String sortBy,
            @RequestParam(value = "sord", required = false) String order) {
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = Sort.by(Sort.Direction.DESC, orderBy);
            } else {
                sort = Sort.by(Sort.Direction.ASC, orderBy);
            }
        }

        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }
        Page<EuCandidaturePoste> postes = null;
        if (idCandidature != null) {
            postes = canPosteservice.findByCandidature(idCandidature, pageRequest);
        } else if (StringUtils.isNotBlank(codePoste)) {
            postes = canPosteservice.findByCodePoste(codePoste, pageRequest);
        } else if (StringUtils.isNotBlank(poste)) {
            postes = canPosteservice.findByLibellePoste(poste, pageRequest);
        } else {
            postes = canPosteservice.listAll(pageRequest);
        }
        JqGrid<EuCandidaturePoste> response = new JqGrid<>();
        if (postes.getTotalElements() > 0) {
            response.setRows(postes.getContent());
            response.setTotal(Integer.toString(postes.getTotalPages()));
            return response;
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
            return response;
        }
    }
}
