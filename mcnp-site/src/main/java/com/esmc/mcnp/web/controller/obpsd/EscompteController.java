package com.esmc.mcnp.web.controller.obpsd;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.domain.dto.obps.LGcp;
import com.esmc.mcnp.infrastructure.components.GcpService;
import com.esmc.mcnp.web.controller.base.BaseRestController;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/escompte")
public class EscompteController extends BaseRestController {

    private @Autowired
    GcpService gcpService;

    @RequestMapping(method = RequestMethod.POST)
    public String doEscompte(@RequestBody LGcp lgcp) {
        if (Objects.nonNull(lgcp) && lgcp.getOp() == 2) {
            return gcpService.doEscompteGcp(lgcp);
        }
        return "KO";
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public @ResponseBody
    LGcp listGcps(
            @PathVariable(value = "code") String codeCompte) {
        LGcp rlgcp = gcpService.getLGcpByCompte(codeCompte);
        if (!rlgcp.getGcps().isEmpty()) {
            return rlgcp;
        }
        return null;
    }
}
