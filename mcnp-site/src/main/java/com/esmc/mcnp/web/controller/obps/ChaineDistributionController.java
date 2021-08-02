package com.esmc.mcnp.web.controller.obps;

import com.esmc.mcnp.domain.enums.ChaineDistributionEnum;
import com.esmc.mcnp.domain.entity.obps.EuChaineDistribution;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.odd.EuAgenceOddService;
import com.esmc.mcnp.infrastructure.services.org.EuCantonService;
import com.esmc.mcnp.infrastructure.services.org.EuChaineDistributionService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.model.obps.ChaineDistributionForm;
import com.esmc.mcnp.util.JqGrid;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/chaine")
public class ChaineDistributionController extends BaseController {

    private final EuChaineDistributionService chaineService;
    private final EuCantonService cantonService;
    private final EuAgenceOddService agenceOddService;
    private final EuMembreMoraleService moraleService;

    @Autowired
    public ChaineDistributionController(EuChaineDistributionService chaineService,
                                        EuCantonService cantonService,
                                        EuAgenceOddService agenceOddService,
                                        EuMembreMoraleService moraleService) {
        this.chaineService = chaineService;
        this.cantonService = cantonService;
        this.agenceOddService = agenceOddService;
        this.moraleService = moraleService;
    }

    @GetMapping(value = "liste")
    public String afficheChaines(Model model) {
        List<String> typeChaines = Lists.newArrayList();
        Arrays.asList(ChaineDistributionEnum.values()).forEach(c -> typeChaines.add(c.name()));
        model.addAttribute("types", typeChaines);
        model.addAttribute("cantons", cantonService.getAll());
        return "obps/chaines";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public JqGrid<EuChaineDistribution> loadTpagcp(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                                   @RequestParam(value = "typeChaine", required = false) String typeChaine,
                                                   @RequestParam(value = "idCanton", required = false) Integer idCanton,
                                                   @RequestParam(value = "valider", required = false) Boolean valider,
                                                   @RequestParam(value = "autonome", required = false) Boolean autonome,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "rows", required = false) Integer rows,
                                                   @RequestParam(value = "sidx", required = false) String sidx,
                                                   @RequestParam(value = "sord", required = false) String sord) {
        Sort sort = null;
        String orderBy[] = sidx.split(",");
        if (orderBy.length > 0 && sord != null) {
            for (String o : orderBy) {
                if (o.trim().contains(" ")) {
                    String[] ord = o.trim().split(" ");
                    if (sort == null) {
                        if (ord[1].equals("desc")) {
                            sort = Sort.by(Sort.Direction.DESC, ord[0]);
                        } else {
                            sort = Sort.by(Sort.Direction.ASC, ord[0]);
                        }
                    } else {
                        if (ord[1].equals("desc")) {
                            sort.and(Sort.by(Sort.Direction.DESC, ord[0]));
                        } else {
                            sort.and(Sort.by(Sort.Direction.ASC, ord[0]));
                        }
                    }
                } else {
                    if (sord.equals("desc")) {
                        sort = Sort.by(Sort.Direction.DESC, o.trim());
                    } else {
                        sort = Sort.by(Sort.Direction.ASC, o.trim());
                    }
                }
            }

        }
        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid
        // starts with 1
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 100;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }

        JqGrid<EuChaineDistribution> response = new JqGrid<>();
        Page<EuChaineDistribution> chaineDistributions = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            chaineDistributions = chaineService.findAllByMembre(codeMembre, pageRequest);
        } else if (StringUtils.isNotBlank(typeChaine)) {
            chaineDistributions = chaineService.findAllByTypeChaine(ChaineDistributionEnum.valueOf(typeChaine.toUpperCase()), pageRequest);
        } else if (idCanton != null) {
            chaineDistributions = chaineService.findAllByCanton(idCanton, pageRequest);
        } else if (valider) {
            chaineDistributions = chaineService.findAllByValider(valider, pageRequest);
        } else if (autonome) {
            chaineDistributions = chaineService.findAllByAutonome(autonome, pageRequest);
        } else {
            chaineDistributions = chaineService.findAllBy(pageRequest);
        }
        if (Objects.nonNull(chaineDistributions) && chaineDistributions.getTotalElements() > 0) {
            response.setRows(chaineDistributions.getContent());
            response.setTotal(Integer.toString(chaineDistributions.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "add")
    public String addChaine(@ModelAttribute("chaine") ChaineDistributionForm chaineForm, Model model) {
        List<String> typeChaines = Lists.newArrayList();
        Arrays.asList(ChaineDistributionEnum.values()).forEach(c -> typeChaines.add(c.getChaine()));
        model.addAttribute("types", typeChaines);
        model.addAttribute("chaines", chaineService.getAll());
        model.addAttribute("cantons", cantonService.getAll());
        return "obps/chaine";
    }

    @PostMapping(value = "add")
    public String addChaineDist(@ModelAttribute("chaine") ChaineDistributionForm chaineForm, Model model) {
        try {
            EuUtilisateur user = getCurrentUser();
            EuChaineDistribution chaine = new EuChaineDistribution()
                    .setNom(chaineForm.getNom())
                    .setTypeChaine(ChaineDistributionEnum.valueOf(chaineForm.getTypeChaine().toUpperCase()))
                    .setAdresse(chaineForm.getAdresse())
                    .setCodePostal(chaineForm.getCodePostal())
                    .setTelephone(chaineForm.getTelephone())
                    .setVille(chaineForm.getVille())
                    .setAutonome(chaineForm.getAutonome())
                    .setValider(false)
                    .setDateCreate(LocalDate.now());
            if ((chaineForm.getAutonome() == null || !chaineForm.getAutonome()) && chaineForm.getIdChaineParent() != null) {
                chaine.setParent(chaineService.getById(chaineForm.getIdChaineParent()));
            }

            if (StringUtils.isNotBlank(chaineForm.getCodeMembreMorale())) {
                chaine.setProprio(moraleService.findById(chaineForm.getCodeMembreMorale()));
            }
            if (chaineForm.getIdCanton() != null) {
                chaine.setCanton(cantonService.getById(chaineForm.getIdCanton()));
            }
            chaine.setUserCreate(user);
            chaine.setAgencesOdd(user.getAgencesOdd());
            chaineService.create(chaine);
            return "redirect:/chaine/liste";
        } catch (Exception e) {
            List<String> typeChaines = Lists.newArrayList();
            Arrays.asList(ChaineDistributionEnum.values()).forEach(c -> typeChaines.add(c.getChaine()));
            model.addAttribute("types", typeChaines);
            model.addAttribute("chaine", chaineForm);
            model.addAttribute("chaines", chaineService.getAll());
            model.addAttribute("cantons", cantonService.getAll());
            model.addAttribute("message", e.getMessage());
            return "obps/chaine";
        }
    }
}
