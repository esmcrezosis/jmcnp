package com.esmc.mcnp.web.controller.ot;

import java.util.Objects;

import com.esmc.mcnp.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.dto.ot.EuFormationDto;
import com.esmc.mcnp.mapper.ot.EuFormationMapper;
import com.esmc.mcnp.model.ot.EuFormation;
import com.esmc.mcnp.services.ot.EuFormationService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.util.Reponse;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/formation")
public class EuFormationController extends BaseController {
    private final EuFormationService formationService;
    private final EuFormationMapper formationMapper;

    @Autowired
    public EuFormationController(EuFormationService formationService, EuFormationMapper formationMapper) {
        this.formationService = formationService;
        this.formationMapper = formationMapper;
    }

    @GetMapping
    public String index(@ModelAttribute("formation") EuFormationDto formation) {
        return "ot/formations";
    }

    @ResponseBody
    @GetMapping(value = "liste")
    public JqGrid<EuFormationDto> listCatUnite(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "rows", required = false) Integer rows,
                                               @RequestParam(value = "sidx", required = false) String sortBy,
                                               @RequestParam(value = "sord", required = false) String order) {
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = Sort.by(Sort.Direction.DESC, orderBy);
            } else {
                sort = Sort.by(Sort.Direction.ASC, orderBy);
            }
        }

        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }
        Page<EuFormation> formations = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            formations = formationService.findByMembre(codeMembre, pageRequest);
        } else {
            formations = formationService.findByMembre(getCodeMembreUser(), pageRequest);
        }
        JqGrid<EuFormationDto> response = new JqGrid<>();
        if (Objects.nonNull(formations) && formations.getTotalElements() > 0) {
            response.setRows(formationMapper.toDtos(formations.getContent()));
            response.setTotal(Integer.toString(formations.getTotalPages()));
            return response;
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
            return response;
        }
    }


}
