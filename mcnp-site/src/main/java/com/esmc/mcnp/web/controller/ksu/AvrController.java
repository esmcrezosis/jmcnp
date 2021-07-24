package com.esmc.mcnp.web.controller.ksu;

import com.esmc.mcnp.core.utils.StringUtils;
import com.esmc.mcnp.model.acteur.EuAvrAchat;
import com.esmc.mcnp.model.acteur.EuAvrDetailAchat;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.acteurs.EuAvrAchatService;
import com.esmc.mcnp.services.acteurs.EuAvrDetailAchatService;
import com.esmc.mcnp.services.pc.EuObjetService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "avr")
public class AvrController extends BaseController {

    private final EuAvrAchatService avrAchatService;
    private final EuAvrDetailAchatService avrDetAchatService;
    private final EuObjetService objetService;

    public AvrController(EuAvrAchatService avrAchatService, EuAvrDetailAchatService avrDetAchatService,
                         EuObjetService objetService) {
        this.avrAchatService = avrAchatService;
        this.avrDetAchatService = avrDetAchatService;
        this.objetService = objetService;
    }

    @GetMapping(value = "achat")
    public String listerAvrAchat() {
        return "avr/achat";
    }

    @ResponseBody
    @GetMapping(value = "listavrachat")
    public JqGrid<EuAvrAchat> listeAvrAchat(
            @RequestParam(value = "codeMembre", required = false) String codeMembre,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord) {
        Sort sort = null;
        String orderBy = sidx;
        if (orderBy != null && sord != null) {
            if (sord.equals("desc")) {
                sort = Sort.by(Sort.Direction.DESC, orderBy);
            } else {
                sort = Sort.by(Sort.Direction.ASC, orderBy);
            }
        }
        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid
        // starts with 1
        if (page == null || page == 0) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        EuUtilisateur user = getCurrentUser();
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }
        JqGrid<EuAvrAchat> avrAchatJqGrid = new JqGrid<>();
        Page<EuAvrAchat> avrAchats = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            avrAchats = avrAchatService.findByMembre(codeMembre, pageRequest);
        } else {
            avrAchats = avrAchatService.findByMembre(getCodeMembreUser(), pageRequest);
        }
        if (Objects.nonNull(avrAchats) && avrAchats.getTotalElements() > 0) {
            List<EuAvrAchat> avrs = avrAchats.getContent();
            avrAchatJqGrid.setRows(avrs);
            avrAchatJqGrid.setRecords(Long.toString(avrAchats.getTotalElements()));
            avrAchatJqGrid.setTotal(Integer.toString(avrAchats.getTotalPages()));
            avrAchatJqGrid.setPage(Integer.toString(avrAchats.getNumber() + 1));
            return avrAchatJqGrid;
        } else {
            avrAchatJqGrid.setRows(Lists.newArrayList());
            avrAchatJqGrid.setRecords("0");
            avrAchatJqGrid.setTotal("0");
            avrAchatJqGrid.setPage("0");
            return avrAchatJqGrid;
        }
    }

    @ResponseBody
    @GetMapping(value = "listdetavrachat")
    public JqGrid<EuAvrDetailAchat> listeAvrDetAchat(
            @RequestParam(value = "referenceAvr", required = false) String reference,
            @RequestParam(value = "idAvrAchat", required = false) Integer idAvrAchat,
            @RequestParam(value = "codeMembre", required = false) String codeMembre,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord) {
        Sort sort = null;
        String orderBy = sidx;
        if (orderBy != null && sord != null) {
            if (sord.equals("desc")) {
                sort = Sort.by(Sort.Direction.DESC, orderBy);
            } else {
                sort = Sort.by(Sort.Direction.ASC, orderBy);
            }
        }
        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid
        // starts with 1
        if (page == null || page == 0) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        EuUtilisateur user = getCurrentUser();
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }
        JqGrid<EuAvrDetailAchat> avrAchatJqGrid = new JqGrid<>();
        Page<EuAvrDetailAchat> avrAchats = null;
        if (idAvrAchat != null) {
            avrAchats = avrDetAchatService.findByAvrAchat(idAvrAchat, pageRequest);
        } else if (StringUtils.isNotBlank(codeMembre)) {
            avrAchats = avrDetAchatService.findByMembre(codeMembre, pageRequest);
        } else if (StringUtils.isNotBlank(reference)) {
            avrAchats = avrDetAchatService.findByReferenceAvr(reference, pageRequest);
        } else  {
            avrAchats = avrDetAchatService.findByMembre(user.getCodeMembre(), pageRequest);
        }
        if (Objects.nonNull(avrAchats) && avrAchats.getTotalElements() > 0) {
            List<EuAvrDetailAchat> avrs = avrAchats.getContent();
            avrAchatJqGrid.setRows(avrs);
            avrAchatJqGrid.setRecords(Long.toString(avrAchats.getTotalElements()));
            avrAchatJqGrid.setTotal(Integer.toString(avrAchats.getTotalPages()));
            avrAchatJqGrid.setPage(Integer.toString(avrAchats.getNumber() + 1));
            return avrAchatJqGrid;
        } else {
            avrAchatJqGrid.setRows(Lists.newArrayList());
            avrAchatJqGrid.setRecords("0");
            avrAchatJqGrid.setTotal("0");
            avrAchatJqGrid.setPage("0");
            return avrAchatJqGrid;
        }
    }

    @GetMapping(value = "vente")
    public String listerAvrVente() {
        return "avr/vente";
    }

    @GetMapping(value = "article")
    public String listerArticle() {
        return "avr/articles";
    }
}
