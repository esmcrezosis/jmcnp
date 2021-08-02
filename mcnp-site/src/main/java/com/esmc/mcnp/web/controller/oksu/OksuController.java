package com.esmc.mcnp.web.controller.oksu;

import com.esmc.mcnp.infrastructure.services.cm.EuCategorieCompteService;
import com.esmc.mcnp.web.controller.base.BaseController;

import com.esmc.mcnp.web.dto.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/oksu")
public class OksuController extends BaseController {

    private final EuCategorieCompteService categorieCompteService;

    @Autowired
    public OksuController(EuCategorieCompteService categorieCompteService) {
        this.categorieCompteService = categorieCompteService;
    }

    @GetMapping
    public String index(Model model) {
        return "oksu/index";
    }

    @GetMapping(value = "bcri")
    public String getBcri(Model model) {
        String cat = "TPAGCRPG";
        if (getCurrentUser().getCodeMembre().endsWith("M")) {
            cat = "TPAGCI";
        }
        model.addAttribute("cats", categorieCompteService.listAll());
        model.addAttribute("agr", "OKSU");
        model.addAttribute("cat", cat);
        model.addAttribute("type", "NB");
        return "cm/comptes";
    }

    @GetMapping(value = "gcp")
    public String getGcp(Model model) {
        model.addAttribute("cats", categorieCompteService.listAll());
        model.addAttribute("agr", "OKSU");
        model.addAttribute("cat", "TPAGCP");
        model.addAttribute("type", "NB");
        return "oksu/tegcp";
    }

    @GetMapping(value = "bai")
    public String getBai(Model model) {
        model.addAttribute("cats", categorieCompteService.listAll());
        model.addAttribute("agr", "OKSU");
        model.addAttribute("cat", "CAPA");
        model.addAttribute("type", "NN");
        return "cm/comptes";
    }
    
    @GetMapping(value = "opi")
    public String getOpi(Model model) {
        model.addAttribute("cats", categorieCompteService.listAll());
        model.addAttribute("agr", "OKSU");
        return "oksu/opiemis";
    }

    @GetMapping(value = "ban")
    public String listeBonNeutre(Model model) {
        model.addAttribute("agr", "OKSU");
        return "bn/bonNeutre";
    }

    @GetMapping(value = "cmfh")
    public String listeCmfh(Model model) {
        model.addAttribute("agr", "OKSU");
        return "oksu/obtBciCmfh";
    }

    @GetMapping(value = "getbcibai")
    public String obtenirBcByBai() {
        return "oksu/obtBciBai";
    }

    @GetMapping(value = "getbci")
    public String obtenirBci() {
        return "oksu/obtBci";
    }

    @GetMapping(value = "/payerForm")
    public String payerFormation() {
        return "oksu/formation";
    }

    @PostMapping(value = "/payerForm")
    public ResponseEntity<?> doPayerFormation() {
        return ResponseEntity.ok(new Result(0, "Operation bien effectuée"));
    }
}