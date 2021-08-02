package com.esmc.mcnp.web.controller.bc;

import com.esmc.mcnp.infrastructure.services.setting.EuParametresService;
import com.esmc.mcnp.web.controller.base.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/param")
public class ParametreController extends BaseController {
    private final EuParametresService parametresService;

    public ParametreController(EuParametresService parametresService) {
        this.parametresService = parametresService;
    }

    @GetMapping
    public ResponseEntity<?> getParam(@RequestParam("code") String codeParam, @RequestParam("lib") String libParam){
        return ResponseEntity.ok(parametresService.getParametre(codeParam, libParam));
    }
}
