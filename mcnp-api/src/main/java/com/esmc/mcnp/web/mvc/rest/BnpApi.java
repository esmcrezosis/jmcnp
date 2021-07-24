package com.esmc.mcnp.web.mvc.rest;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.components.SouscriptionBon;
import com.esmc.mcnp.dto.other.Bnp;
import com.esmc.mcnp.exception.CompteNonTrouveException;
import com.esmc.mcnp.model.bc.EuCycleFormation;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.oi.EuTypeBnp;
import com.esmc.mcnp.services.common.EuCycleFormationService;
import com.esmc.mcnp.services.obps.EuTegcService;
import com.esmc.mcnp.services.oi.EuTypeBnpService;
import com.esmc.mcnp.web.mvc.dto.BnpDto;
import com.esmc.mcnp.web.mvc.dto.Formation;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

@RestController
@RequestMapping(value = "/bnp")
public class BnpApi extends BaseRestController {

    private final EuTypeBnpService bnpTypeRepo;
    private final SouscriptionBon souscrireBon;
    private final EuTegcService tegcService;
    private final EuCycleFormationService cycleService;
    private final SouscriptionBon souscriptionBon;

    @Inject
    public BnpApi(EuTypeBnpService bnpTypeRepo,
                       SouscriptionBon souscrireBon, EuTegcService tegcService, EuCycleFormationService cycleService,
                       SouscriptionBon souscriptionBon) {
        this.bnpTypeRepo = bnpTypeRepo;
        this.souscrireBon = souscrireBon;
        this.tegcService = tegcService;
        this.cycleService = cycleService;
        this.souscriptionBon = souscriptionBon;
    }

    @RequestMapping(value = "/listeType", method = RequestMethod.GET)
    public ResponseEntity<Iterable<EuTypeBnp>> listTypeBnp() {
        return new ResponseEntity<>(bnpTypeRepo.list(), HttpStatus.OK);
    }

    @RequestMapping(value = "/cycles", method = RequestMethod.GET)
    public @ResponseBody
    List<EuCycleFormation> getCycles() {
        return cycleService.list();
    }

    @RequestMapping(value = "/cycle", method = RequestMethod.POST)
    public @ResponseBody
    EuCycleFormation getCycle(@RequestBody String cycle) {
        if (StringUtils.isNotBlank(cycle)) {
            return cycleService.findByCodeformation(cycle);
        } else {
            return null;
        }
    }

    @Transactional
    @RequestMapping(value = "/formation", method = RequestMethod.POST)
    public ResponseEntity<Result> doSouscriptionFormation(@RequestBody Formation formation, BindingResult errors) {
        if (errors.hasErrors()) {
            StringBuilder msgs = new StringBuilder("Echec de l'opération: ");
            errors.getAllErrors().stream().forEach(e -> msgs.append(e.getDefaultMessage()));
            return new ResponseEntity<>(new Result(0, msgs.toString()), HttpStatus.BAD_REQUEST);
        }
        try {
            souscriptionBon.souscrireFormation(formation.getCodeMembre(), formation.getCatBonConso(),
                    formation.getTypeProduit(), formation.getTypeRecurrent(), formation.getCycle(),
                    formation.getMontFormation(), formation.getNumBon());
            return new ResponseEntity<>(new Result(1, "Opération terminée avec succès"), HttpStatus.CREATED);
        } catch (CompteNonTrouveException | IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(new Result(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @RequestMapping(value = "/souscrire", method = RequestMethod.POST)
    public ResponseEntity<Result> souscrireTiers(@RequestBody Bnp bnp) {
        try {
            if (Objects.nonNull(bnp)) {
                EuTegc tegc = tegcService.getById(bnp.getCodeTeApp());
                if (Objects.nonNull(tegc)) {
                    if (souscrireBon.souscrirePourTiers(bnp) == 0) {
                        return new ResponseEntity<>(new Result(1, "Opération terminée avec succès"),
                                HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(
                    new Result(0, "Echec de l'opération : Veuillez vérifier les données envoyées!!!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @RequestMapping(value = "/sbnp", method = RequestMethod.POST)
    public ResponseEntity<Result> doSouscrirePrTiers(@ModelAttribute("bnp") BnpDto bnp) {
        if (souscrireBon.souscrirePourTiers(bnp.getCodeMembreBenef(), bnp.getCodeMembreApp(), bnp.getTypeBnp(),
                bnp.getMontant()) == 0) {
            new ResponseEntity<>(new Result(1, "Opération terminée avec succès"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new Result(0, "Echec de l'opération : Veuillez vérifier les données envoyées!!!"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
