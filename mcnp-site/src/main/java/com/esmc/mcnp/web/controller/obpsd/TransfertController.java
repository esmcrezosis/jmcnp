package com.esmc.mcnp.web.controller.obpsd;

import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.components.TransfertService;
import com.esmc.mcnp.dto.bn.Nn;
import com.esmc.mcnp.dto.bn.TransfertSMS;
import com.esmc.mcnp.dto.smcipn.Transfert;
import com.esmc.mcnp.services.obpsd.EuNnService;

@RestController
@RequestMapping(value = "/transfert")
public class TransfertController {

    @Inject
    private TransfertService transfertService;
    private @Inject
    EuNnService nnService;

    @RequestMapping(value = "sms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String doTransfert(@RequestBody TransfertSMS transfert) {
        if (transfert != null && transfert.getTypeTransfert() != null && !transfert.getTypeTransfert().isEmpty()) {
            if (transfert.getTypeTransfert().equalsIgnoreCase("GROS")) {
                return transfertService.doTransfertGros(transfert);
            } else if (transfert.getTypeTransfert().equals("DET")) {
            	System.out.println("Detaillant");
                return transfertService.doTransfertDetail(transfert);
            } else {
                return transfertService.doTransfertSource(transfert);
            }
        }
        return "Echec de l'opération : Veuillez entrer des infos valides!";
    }
    
    @RequestMapping(value = "cmfh", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String doTransfertCmfh(@RequestBody TransfertSMS transfert) {
        if (Objects.nonNull(transfert) && StringUtils.isNotBlank(transfert.getTypeTransfert())) {
            if (StringUtils.isNotBlank(transfert.getCodeCompteVendeur()) && StringUtils.isNotBlank(transfert.getCodeCompteAcheteur()) && transfert.getTypeTransfert().equalsIgnoreCase("cmfh")) {
                return transfertService.doTransfertCmfh(transfert);
            }
        }
        return "Echec de l'opération : Veuillez entrer des infos valides!";
    }

    @RequestMapping(value = "nn", method = RequestMethod.POST)
    public String emettreNn(@RequestBody Nn nn) {
        if (Objects.nonNull(nn)) {
            return nnService.EmettreNn(nn);
        }
        return "KO";
    }

    @RequestMapping(value = "pre", method = RequestMethod.POST)
    public String doTransfertPre(Transfert transfert) {
        if (transfert != null) {

        }
        return "Echec de l'opération : Veuillez entrer des infos valides!";
    }
}
