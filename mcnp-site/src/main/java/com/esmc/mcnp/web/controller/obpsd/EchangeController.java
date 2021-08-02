package com.esmc.mcnp.web.controller.obpsd;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esmc.mcnp.commons.util.StringUtils;
import com.esmc.mcnp.core.utils.SecurityUtils;
import com.esmc.mcnp.domain.dto.bc.CalculBonInfo;
import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.others.EuOperation;
import com.esmc.mcnp.infrastructure.components.CreditComponent;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.common.EuOperationService;
import com.esmc.mcnp.infrastructure.services.obpsd.EuEchangeService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.util.Reponse;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.model.echange.BcEchange;
import com.esmc.mcnp.web.model.echange.EchBcForm;

@Controller
@RequestMapping(value = "/ech")
public class EchangeController extends BaseController {

    private final EuEchangeService echangeService;
    private final EuCompteService compteService;
    private final EuMembreService membreService;
    private final EuMembreMoraleService moraleService;
    private final EuOperationService opService;
    private final EuParametresService paramService;
    private final CreditComponent creditComponent;

    public EchangeController(EuEchangeService echangeService, EuCompteService compteService,
                             EuMembreService membreService, EuMembreMoraleService moraleService, EuOperationService opService,
                             EuParametresService paramService, CreditComponent creditComponent) {
        this.echangeService = echangeService;
        this.compteService = compteService;
        this.membreService = membreService;
        this.moraleService = moraleService;
        this.opService = opService;
        this.paramService = paramService;
        this.creditComponent = creditComponent;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "echbc")
    public ResponseEntity<?> echBonConso(@RequestBody EchBcForm echBcForm) {
        if (StringUtils.isNotBlank(echBcForm.getCodeMembre()) && StringUtils.isNotBlank(echBcForm.getType())
                && !echBcForm.getDetails().isEmpty()) {
            if (echBcForm.getType().equals("BC")) {
                EuMembre membre = null;
                EuMembreMorale morale = null;
                if (echBcForm.getCodeMembre().endsWith("P")) {
                    membre = membreService.findById(echBcForm.getCodeMembre());
                } else {
                    morale = moraleService.findById(echBcForm.getCodeMembre());
                }
                if ((Objects.nonNull(membre) && membre.getDesactiver() == 0)
                        || (Objects.nonNull(morale) && morale.getDesactiver() == 0)) {
                    AtomicReference<Double> montantBai = new AtomicReference<>(0.0);
                    AtomicReference<Double> montantBc = new AtomicReference<>(0.0);
                    echBcForm.getDetails().forEach(d -> {
                        if (d.getTypeBc().equalsIgnoreCase("RPGr") || d.getTypeBc().equalsIgnoreCase("Ir")) {
                            if ("illimite".equalsIgnoreCase(d.getTypeRecurrent())) {
                                montantBai.updateAndGet(v -> (double) (v + d.getMsbc()));
                            } else if ("limite".equalsIgnoreCase(d.getTypeRecurrent())) {
                                var param = paramService.getParametre("MSBCrl", "PS");
                                var duree = d.getDuree();
                                var reste = duree - d.getNbreRenouvel();
                                var montplace = d.getMsbc();
                                var bc = montplace / param;
                                var credit = bc * reste;
                                montantBai.updateAndGet(v -> v + Math.floor(credit * param));
                            }
                        } else if ("RPGnr".equalsIgnoreCase(d.getTypeBc()) || "Inr".equalsIgnoreCase(d.getTypeBc())) {
                            if (d.getBc() > 0) {
                                montantBai.updateAndGet(v -> (double) (v + (d.getBc() * 5.6) / d.getPrk()));
                            }
                        }
                        montantBc.updateAndGet(v -> (double) (v + d.getBc()));
                    });
                    if (montantBai.get() > 0) {
                        var date = new Date();
                        var montBci = Math.floor((montantBai.get() / 2187.5) * 70000);
                        var codeCategorie = "TPAGCRPG";
                        var codeCompte = "NB-" + codeCategorie + "-" + echBcForm.getCodeMembre();
                        if (echBcForm.getCodeMembre().endsWith("M")) {
                            codeCategorie = "TPAGCI";
                            codeCompte = "NB-" + codeCategorie + "-" + echBcForm.getCodeMembre();
                        }
                        var compteOrg = compteService.getById(codeCompte);
                        List<Long> credits = echBcForm.getDetails().stream().map(BcEchange::getId)
                                .collect(Collectors.toList());
                        compteService.updateCompte(codeCompte, echBcForm.getCodeMembre(), credits,
                                "Echange de BC en BCi", montantBc.get());
                        echBcForm.getDetails().forEach(c -> {
                            creditComponent.updateCnpByCredit(c.getId(), c.getBc());
                        });

                        EuCategorieCompte categorie = new EuCategorieCompte(codeCategorie);
                        EuOperation op = new EuOperation();
                        op.setEuCategorieCompte(categorie);
                        op.setCodeProduit("BC");
                        op.setDateOp(date);
                        op.setLibOp("Echange de Bon de Consommation");
                        op.setTypeOp("E");
                        op.setMontantOp(montantBai.get());
                        if (echBcForm.getCodeMembre().endsWith("P")) {
                            op.setEuMembre(compteOrg.getEuMembre());
                        } else {
                            op.setEuMembreMorale(compteOrg.getEuMembreMorale());
                        }
                        op.setEuUtilisateur(SecurityUtils.getPrincipal());
                        op.setHeureOp(date);
                        opService.add(op);

                        EuCompte compte = compteService.createOrUpdate("NB", "TBC", echBcForm.getCodeMembre(), montBci);
                        CalculBonInfo bonInfo = new CalculBonInfo();
                        bonInfo.setCatBon("nr");
                        bonInfo.setPrk(5.6);
                        bonInfo.setDuree(5.6);
                        bonInfo.setTypeProduit("PS");
                        creditComponent.createCredit(compte, new EuProduit("BCi"), Math.floor(montantBai.get()), montBci, false,
                                null, op, compte.getCodeCompte(), echBcForm.getCodeMembre(), bonInfo);

                        EuEchange ech = new EuEchange();
                        ech.setCatEchange("BC");
                        ech.setTypeEchange("NB/NB");
                        ech.setMontantEchange(montantBai.get());
                        ech.setMontant(montBci);
                        ech.setCompenser(0);
                        ech.setRegler(0);
                        ech.setEuCompte(compteOrg);
                        ech.setCodeCompteObt(compte.getCodeCompte());
                        ech.setDateEchange(date);
                        ech.setDateReglement(date);
                        if (echBcForm.getCodeMembre().endsWith("M")) {
                            ech.setEuMembreMorale(compteOrg.getEuMembreMorale());
                            ech.setEuMembre(null);
                        } else {
                            ech.setEuMembreMorale(null);
                            ech.setEuMembre(compteOrg.getEuMembre());
                        }
                        ech.setCodeProduit("BC");
                        ech.setAgio(0);
                        ech = echangeService.add(ech);
                    }
                    return ResponseEntity.ok(new Reponse("Operation Bien effectuée, montant = " + montantBai.get()));
                }
            }
        }
        return ResponseEntity.badRequest().body(new Reponse("Echec de l'opération: Donées insuffisantes"));
    }

    public ResponseEntity<?> echangeCmfh() {
        return ResponseEntity.badRequest().body(new Reponse("Echec de l'opération: Donées insuffisantes"));
    }
}
