package com.esmc.mcnp.web.controller.obpsd;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.domain.dto.desendettement.Compensation;
import com.esmc.mcnp.infrastructure.components.GcpService;

@RestController
@RequestMapping(value = "/compensation")
public class CompensationController {

    private @Autowired
    GcpService gcpService;

    @RequestMapping(method = RequestMethod.POST)
    public String doCompensation(@RequestBody Compensation compens) {
        if (compens.getTypeOp() == 2) {
            if (compens.getTypeCompensation().equals("C")) {
                return gcpService.doCompensation(compens);
            } else {
                return gcpService.doMprg(compens);
            }
        }
        return "KO";
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public @ResponseBody
    Compensation loadCompensation(@PathVariable("code") String codeCompte) {
        if (StringUtils.isNotBlank(codeCompte)) {
            Compensation rcp = gcpService.getCompensationByCompte(codeCompte);
            if (Objects.nonNull(rcp)) {
                return rcp;
            }
        }
        return null;
    }

    @RequestMapping(value = "mprg/{code}", method = RequestMethod.GET)
    public @ResponseBody
    Compensation loadMprg(@PathVariable("code") String codeCompte) {
        if (StringUtils.isNotBlank(codeCompte)) {
            Compensation rcp = gcpService.getMprgByCompte(codeCompte);
            if (Objects.nonNull(rcp)) {
                return rcp;
            }
        }
        return null;
    }

}
