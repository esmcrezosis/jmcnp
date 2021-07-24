package com.esmc.mcnp.web.controller.oksu;

import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.core.utils.StringUtils;
import com.esmc.mcnp.dto.bn.EuCapaDto;
import com.esmc.mcnp.mapper.bn.EuCapaMapper;
import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.services.ba.EuCapaService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/bai")
public class BaiController extends BaseController {
    private final EuCapaService capaService;
    private final EuCapaMapper capaMapper;

    public BaiController(EuCapaService capaService, EuCompteCreditService compteCreditService,
            EuCapaMapper capaMapper) {
        this.capaService = capaService;
        this.capaMapper = capaMapper;
    }

    @GetMapping(value = "{agr}/{code}")
    public String index(Model model, @PathVariable("agr") String agr, @PathVariable("code") String codeCompte) {
        model.addAttribute("agr", agr);
        model.addAttribute("codeCompte", codeCompte);
        return "bn/bai";
    }

    @GetMapping(value = "liste")
    @ResponseBody
    public JqGrid<EuCapaDto> loadCompteCredits(@RequestParam(value = "codeMembre", required = false) String codeMembre,
            @RequestParam(value = "codeCompte", required = false) String codeCompte,
            @RequestParam(value = "typeCapa", required = false) String typeCapa,
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

        Page<EuCapa> capas = null;
        if (StringUtils.isNotBlank(codeCompte)) {
            if (StringUtils.isNotBlank(typeCapa)) {
                capas = capaService.findByCompteAndOrigine(codeCompte, typeCapa, pageRequest);
            } else {
                capas = capaService.findByCompte(codeCompte, pageRequest);
            }
        } else if (StringUtils.isNotBlank(codeMembre)) {
            if (StringUtils.isNotBlank(typeCapa)) {
                capas = capaService.findByMembreAndOrigine(codeMembre, typeCapa, pageRequest);
            } else {
                capas = capaService.findByCodeMembre(codeMembre, pageRequest);
            }
        }
        JqGrid<EuCapaDto> response = new JqGrid<>();

        if (Objects.nonNull(capas) && capas.getTotalElements() > 0) {
            response.setRows(capaMapper.toDtos(capas.getContent()));
            response.setTotal(Integer.toString(capas.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

}