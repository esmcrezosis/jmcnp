package com.esmc.mcnp.web.controller.bc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esmc.mcnp.components.CreditComponent;
import com.esmc.mcnp.core.utils.SecurityUtils;
import com.esmc.mcnp.core.utils.StringUtils;
import com.esmc.mcnp.dto.bc.CalculBonInfo;
import com.esmc.mcnp.model.acteur.EuDepotVente;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cmfh.EuCmfh;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.cmfh.EuCmfhService;
import com.esmc.mcnp.services.cmfh.EuDepotVenteService;
import com.esmc.mcnp.services.common.EuOperationService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.esmc.mcnp.web.model.echange.DepotVente;
import com.esmc.mcnp.web.model.echange.ObtenirBci;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "bc")
public class BciController extends BaseController {

    private final EuCapaService capaService;
    private final EuMembreService membreService;
    private final EuMembreMoraleService moraleService;
    private final EuOperationService opService;
    private final CreditComponent creditComponent;
    private final EuCompteService compteService;
    private final EuCmfhService cmfhService;
    private final EuDepotVenteService depotVenteService;

    public BciController(EuCapaService capaService, EuMembreService membreService, EuMembreMoraleService moraleService,
                         CreditComponent creditComponent, EuCompteService compteService, EuOperationService opService,
                         EuCmfhService cmfhService, EuDepotVenteService depotVenteService) {
        this.capaService = capaService;
        this.membreService = membreService;
        this.moraleService = moraleService;
        this.opService = opService;
        this.creditComponent = creditComponent;
        this.compteService = compteService;
        this.cmfhService = cmfhService;
        this.depotVenteService = depotVenteService;
    }

    @PostMapping(value = "interne")
    public ResponseEntity<?> obtenirBciByBai(@RequestBody ObtenirBci obtBci) {
        if (StringUtils.isNotBlank(obtBci.getCodeMembre()) && StringUtils.isNotBlank(obtBci.getType())
                && obtBci.getMontant() != null) {
            if (obtBci.getType().equalsIgnoreCase("BAi")) {
                EuMembre membre = null;
                EuMembreMorale morale = null;
                if (obtBci.getCodeMembre().endsWith("P")) {
                    membre = membreService.findById(obtBci.getCodeMembre());
                } else {
                    morale = moraleService.findById(obtBci.getCodeMembre());
                }
                if ((Objects.nonNull(membre) && membre.getDesactiver() == 0)
                        || (Objects.nonNull(morale) && morale.getDesactiver() == 0)) {
                    var codeCompteBai = "NN-CAPA-" + obtBci.getCodeMembre();
                    EuCompte compteBai = compteService.getById(codeCompteBai);
                    List<EuCapa> capas = Lists.newArrayList();
                    if (StringUtils.isNotBlank(obtBci.getOrigine())) {
                        capas = capaService.findByMembreAndTypeAndOrigine(obtBci.getCodeMembre(),
                                obtBci.getType().toUpperCase(), obtBci.getOrigine());
                    } else {
                        capas = capaService.findByMembreAndType(obtBci.getCodeMembre(), obtBci.getType().toUpperCase());
                    }
                    if (capas.size() > 0) {
                        TransactionStatus status = getTransactionStatus();
                        try {
                            var date = new Date();
                            var somBai = capas.stream().mapToDouble(EuCapa::getMontantSolde).sum();
                            if (somBai >= obtBci.getMontant() && compteBai.getSolde() >= obtBci.getMontant()) {
                                var montBci = obtBci.getMontant();
                                if (obtBci.getType().equalsIgnoreCase("OPI")
                                        || obtBci.getType().equalsIgnoreCase("BC")) {
                                    montBci = (obtBci.getMontant() / 2187.5) * 70000;
                                }
                                EuCategorieCompte categorie = new EuCategorieCompte("TBC");
                                EuOperation op = new EuOperation();
                                op.setEuCategorieCompte(categorie);
                                op.setCodeProduit("BC");
                                op.setDateOp(date);
                                op.setLibOp("Echange de Bon de Consommation");
                                op.setTypeOp("E");
                                op.setMontantOp(obtBci.getMontant());
                                if (obtBci.getCodeMembre().endsWith("P")) {
                                    op.setEuMembre(compteBai.getEuMembre());
                                } else {
                                    op.setEuMembreMorale(compteBai.getEuMembreMorale());
                                }
                                op.setEuUtilisateur(SecurityUtils.getPrincipal());
                                op.setHeureOp(date);
                                opService.add(op);

                                EuCompte compteBci = compteService.createOrUpdate("NB", "TBC", obtBci.getCodeMembre(),
                                        montBci);
                                CalculBonInfo bonInfo = new CalculBonInfo();
                                bonInfo.setCatBon("nr");
                                bonInfo.setPrk(5.6);
                                bonInfo.setDuree(5.6);
                                bonInfo.setTypeProduit("PS");
                                EuCompteCredit cc = creditComponent.createCredit(compteBci, new EuProduit("BCi"),
                                        obtBci.getMontant(), montBci, false, null, op, compteBci.getCodeCompte(),
                                        obtBci.getCodeMembre(), bonInfo);
                                creditComponent.updateListCapa(capas, cc, obtBci.getMontant(), date,
                                        obtBci.getCodeMembre());

                                compteBai.setSolde(compteBai.getSolde() - obtBci.getMontant());
                                compteService.update(compteBai);
                                getTransactionManager().commit(status);
                                return ResponseEntity.ok(new Result(0, "Opération effectuée avec succès"));
                            } else {
                                return ResponseEntity.badRequest()
                                        .body(new Result(1, "Echec de l'opération: Votre solde est insuffisant"));
                            }
                        } catch (Exception e) {
                            getTransactionManager().rollback(status);
                            getLog().error("Echec de l'opération", e);
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new Result(1, "Echec de l'opération: " + e.getMessage()));
                        }
                    } else {
                        return ResponseEntity.badRequest().body(
                                new Result(1, "Echec de l'opération: Vous ne disposez pas les ressources requises"));
                    }
                } else {
                    return ResponseEntity.badRequest()
                            .body(new Result(1, "Echec de l'opération: Votre compte est desactivé"));
                }
            }
        }
        return ResponseEntity.badRequest()
                .body(new Result(1, "Echec de l'opération: Veuillez fournir les renseignemnts nécssaires"));
    }

    @PostMapping(value = "cmfh")
    public ResponseEntity<?> obtenirBciByCmfh(@RequestBody DepotVente depot) {
        if (StringUtils.isNotBlank(depot.getCodeMembre()) && StringUtils.isNotBlank(depot.getType())
                && depot.getIds().length > 0) {
            if (depot.getType().equalsIgnoreCase("CMFH")) {
                EuMembre membre = null;
                EuMembreMorale morale = null;
                if (depot.getCodeMembre().endsWith("P")) {
                    membre = membreService.findById(depot.getCodeMembre());
                } else {
                    morale = moraleService.findById(depot.getCodeMembre());
                }
                if ((Objects.nonNull(membre) && membre.getDesactiver() == 0)
                        || (Objects.nonNull(morale) && morale.getDesactiver() == 0)) {
                    EuCmfh cmfh = cmfhService.findAllByMembre(depot.getCodeMembre()).get(0);
                    if (Objects.nonNull(cmfh)) {
                        if (cmfh.getRembourser() == 0) {
                            TransactionStatus status = getTransactionStatus();
                            List<EuDepotVente> depots = depotVenteService.listAllByIds(Arrays.asList(depot.getIds()));
                            if (!depots.isEmpty()) {
                                List<EuDepotVente> deps = depots.stream()
                                        .filter(d -> !d.getPayer())
                                        .collect(Collectors.toList());
                                Double montant = deps.stream().mapToDouble(EuDepotVente::getMontDepot).sum();
                                try {
                                    depotVenteService.recouvrerCmfh(depots, montant);
                                    var codeCompteBai = "NN-CAPA-" + depot.getCodeMembre();
                                    EuCompte compteBai = compteService.getById(codeCompteBai);
                                    List<EuCapa> capas = Lists.newArrayList();
                                    capas = capaService.findByMembreAndOrigine(depot.getCodeMembre(),
                                            depot.getType().toUpperCase());
                                    var date = new Date();
                                    var somBai = capas.stream().mapToDouble(EuCapa::getMontantSolde).sum();
                                    if (somBai >= depot.getMontant() && compteBai.getSolde() >= depot.getMontant()) {
                                        var montBci = montant;
                                        EuCategorieCompte categorie = new EuCategorieCompte("TBC");
                                        EuOperation op = new EuOperation();
                                        op.setEuCategorieCompte(categorie);
                                        op.setCodeProduit("BC");
                                        op.setDateOp(date);
                                        op.setLibOp("Echange de Bon de Consommation");
                                        op.setTypeOp("E");
                                        op.setMontantOp(depot.getMontant());
                                        if (depot.getCodeMembre().endsWith("P")) {
                                            op.setEuMembre(compteBai.getEuMembre());
                                        } else {
                                            op.setEuMembreMorale(compteBai.getEuMembreMorale());
                                        }
                                        op.setEuUtilisateur(SecurityUtils.getPrincipal());
                                        op.setHeureOp(date);
                                        opService.add(op);

                                        EuCompte compteBci = compteService.createOrUpdate("NB", "TBC",
                                                depot.getCodeMembre(), montBci);
                                        CalculBonInfo bonInfo = new CalculBonInfo();
                                        bonInfo.setCatBon("nr");
                                        bonInfo.setPrk(5.6);
                                        bonInfo.setDuree(5.6);
                                        bonInfo.setTypeProduit("PS");
                                        EuCompteCredit cc = creditComponent.createCredit(compteBci,
                                                new EuProduit("BCi"), depot.getMontant(), montBci, false, null, op,
                                                compteBci.getCodeCompte(), depot.getCodeMembre(), bonInfo);
                                        creditComponent.updateListCapa(capas, cc, depot.getMontant(), date,
                                                depot.getCodeMembre());

                                        compteBai.setSolde(compteBai.getSolde() - depot.getMontant());
                                        compteService.update(compteBai);
                                        getTransactionManager().commit(status);
                                        return ResponseEntity.ok(new Result(0, "Opération effectuée avec succès"));
                                    }
                                } catch (Exception e) {
                                    getTransactionManager().rollback(status);
                                    getLog().error("Echec de l'operation", e);
                                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                            .body(new Result(1, "Echec de l'opération: " + e.getMessage()));
                                }
                            }
                        } else {
                            return ResponseEntity.badRequest()
                                    .body(new Result(1, "Echec de l'opération: Souscription dejà remboursée"));
                        }
                    } else {
                        return ResponseEntity.badRequest()
                                .body(new Result(1, "Echec de l'opération: Ce membre ne figure pas parmi les CMFH"));
                    }
                }
            }
        }
        return ResponseEntity.badRequest()
                .body(new Result(1, "Echec de l'opération: Veuillez fournir les renseignemnts nécssaires"));
    }
}
