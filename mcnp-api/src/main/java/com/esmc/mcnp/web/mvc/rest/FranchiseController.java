package com.esmc.mcnp.web.mvc.rest;

import com.esmc.mcnp.commons.model.AjaxResult;
import com.esmc.mcnp.infrastructure.components.Payement;
import com.esmc.mcnp.infrastructure.services.cm.EuCompteService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "franchise")
public class FranchiseController extends BaseRestController {
    private Payement payement;
    private EuMembreMoraleService moraleService;
    private EuCompteService compteService;
    private EuParametresService parametresService;

    @Autowired
    public FranchiseController(Payement payement, EuMembreMoraleService moraleService,
                               EuCompteService compteService, EuParametresService parametresService) {
        this.payement = payement;
        this.moraleService = moraleService;
        this.compteService = compteService;
        this.parametresService = parametresService;
    }

    public AjaxResult payerFranchise(String codeMembre, Integer idTypeFranchise){
        String codeMembreESMC = parametresService.getStringParam("ESMC", "GIE");
        return AjaxResult.success();
    }
}
