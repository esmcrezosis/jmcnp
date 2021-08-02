package com.esmc.mcnp.web.controller.obpsd;

import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreUtilise;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreUtiliseService;
import com.esmc.mcnp.web.controller.base.BaseController;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping(value = "/bonNeutre")
public class EuBonNeutreController extends BaseController {

    private EuBonNeutreService bnNeutreService;
    private EuBonNeutreDetailService bnNeutreDetailService;
    private EuBonNeutreUtiliseService bnUtiliseService;

    @Autowired
    public EuBonNeutreController(EuBonNeutreService bnNeutreService, EuBonNeutreDetailService bnNeutreDetailService,
                                 EuBonNeutreUtiliseService bnUtiliseService) {
        this.bnNeutreService = bnNeutreService;
        this.bnNeutreDetailService = bnNeutreDetailService;
        this.bnUtiliseService = bnUtiliseService;
    }

    @GetMapping
    public String bonNeutre() {
        return "bn/index";
    }

    @GetMapping(value = "{agr}/ban")
    public String listeBonNeutre(Model model, @PathVariable(value = "agr", required = false) String agr) {
        model.addAttribute("agr", agr);
        return "bn/bonNeutre";
    }

    @GetMapping(value = "bans")
    @ResponseBody
    public JqGrid<EuBonNeutre> loadBan(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                       @RequestParam(value = "nomMembre", required = false) String nomMembre,
                                       @RequestParam(value = "prenomMembre", required = false) String prenomMembre,
                                       @RequestParam(value = "typeBan", required = false) String typeBan,
                                       @RequestParam(value = "deb", required = false) String dateDebut,
                                       @RequestParam(value = "fin", required = false) String dateFin,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "rows", required = false) Integer rows,
                                       @RequestParam(value = "sidx", required = false) String sidx,
                                       @RequestParam(value = "sord", required = false) String sord) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date deb = null;
        Date fin = null;
        if (StringUtils.isNotBlank(dateDebut)) {
            try {
                deb = format.parse(dateDebut);
            } catch (ParseException e) {
            }
        }
        if (StringUtils.isNotBlank(dateFin)) {
            try {
                fin = format.parse(dateFin);
            } catch (ParseException e) {
            }
        }
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

        JqGrid<EuBonNeutre> response = new JqGrid<>();
        Page<EuBonNeutre> bonNeutrePage = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            bonNeutrePage = bnNeutreService.findByCodeMembre(codeMembre, pageRequest);
        } else if (StringUtils.isNotBlank(nomMembre) && StringUtils.isNotBlank(prenomMembre)) {
            bonNeutrePage = bnNeutreService.findByNomAndBonNeutrePrenom(nomMembre, prenomMembre, pageRequest);
        } else if (StringUtils.isNotBlank(nomMembre)) {
            bonNeutrePage = bnNeutreService.findByNom(nomMembre, pageRequest);
            if (bonNeutrePage.getSize() == 0) {
                bonNeutrePage = bnNeutreService.findByRaisonSociale(nomMembre, pageRequest);
            }
        } else if (deb != null && fin != null) {
            bonNeutrePage = bnNeutreService.findByDateBetween(deb, fin, pageRequest);
        } else if (deb != null) {
            bonNeutrePage = bnNeutreService.findByDateSup(deb, pageRequest);
        } else if (fin != null) {
            bonNeutrePage = bnNeutreService.findByDateInf(fin, pageRequest);
        } else if (StringUtils.isNotBlank(typeBan)) {
            bonNeutrePage = bnNeutreService.findByType(typeBan, pageRequest);
        } else {
            bonNeutrePage = bnNeutreService.listAll(pageRequest);
        }
        if (Objects.nonNull(bonNeutrePage) && bonNeutrePage.getTotalElements() > 0) {
            response.setRows(bonNeutrePage.getContent());
            response.setTotal(Integer.toString(bonNeutrePage.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "{agr}/detail/{id}")
    public String afficherDetBAn(@PathVariable(value = "agr", required = false) String agr, @PathVariable("id") Integer id, Model model) {
        EuBonNeutre bonNeutre = bnNeutreService.getById(id);
        model.addAttribute("arg", agr);
        if (Objects.nonNull(bonNeutre)) {
            model.addAttribute("bn", bonNeutre);
            return "bn/detailBonNeutre";
        } else {
            return "bn/bonNeutre";
        }
    }

    @GetMapping(value = "{agr}/banDetail")
    public String listeBonNeutreDetail(Model model, @PathVariable(value = "agr", required = false) String agr) {
        model.addAttribute("agr", agr);
        return "bn/bnDetail";
    }

    @GetMapping(value = "detailBans")
    @ResponseBody
    public JqGrid<EuBonNeutreDetail> loadDetailBan(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                                   @RequestParam(value = "nomMembre", required = false) String nomMembre,
                                                   @RequestParam(value = "prenomMembre", required = false) String prenomMembre,
                                                   @RequestParam(value = "typeBan", required = false) String typeBan,
                                                   @RequestParam(value = "deb", required = false) String dateDebut,
                                                   @RequestParam(value = "fin", required = false) String dateFin,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "rows", required = false) Integer rows,
                                                   @RequestParam(value = "sidx", required = false) String sidx,
                                                   @RequestParam(value = "sord", required = false) String sord) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date deb = null;
        Date fin = null;
        if (StringUtils.isNotBlank(dateDebut)) {
            try {
                deb = format.parse(dateDebut);
            } catch (ParseException e) {
            }
        }
        if (StringUtils.isNotBlank(dateFin)) {
            try {
                fin = format.parse(dateFin);
            } catch (ParseException e) {
            }
        }
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

        JqGrid<EuBonNeutreDetail> response = new JqGrid<>();
        Page<EuBonNeutreDetail> bonNeutrePage = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            bonNeutrePage = bnNeutreDetailService.findByCodeMembre(codeMembre, pageRequest);
        } else if (StringUtils.isNotBlank(nomMembre) && StringUtils.isNotBlank(prenomMembre)) {
            bonNeutrePage = bnNeutreDetailService.findByNomAndPrenom(nomMembre, prenomMembre, pageRequest);
        } else if (StringUtils.isNotBlank(nomMembre)) {
            bonNeutrePage = bnNeutreDetailService.findByNom(nomMembre, pageRequest);
            if (bonNeutrePage.getSize() == 0) {
                bonNeutrePage = bnNeutreDetailService.findByRaisonSociale(nomMembre, pageRequest);
            }
        } else if (deb != null && fin != null) {
            bonNeutrePage = bnNeutreDetailService.findByDate(deb, fin, pageRequest);
        } else if (deb != null) {
            bonNeutrePage = bnNeutreDetailService.findByDateSup(deb, pageRequest);
        } else if (fin != null) {
            bonNeutrePage = bnNeutreDetailService.findByDateInf(fin, pageRequest);
        } else if (StringUtils.isNotBlank(typeBan)) {
            bonNeutrePage = bnNeutreDetailService.findByType(typeBan, pageRequest);
        } else {
            bonNeutrePage = bnNeutreDetailService.getAll(pageRequest);
        }
        if (Objects.nonNull(bonNeutrePage) && bonNeutrePage.getTotalElements() > 0) {
            response.setRows(bonNeutrePage.getContent());
            response.setTotal(Integer.toString(bonNeutrePage.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "{agr}/banUtilise")
    public String listeBonNeutreUtilise(Model model, @PathVariable(value = "agr", required = false) String agr) {
        model.addAttribute("agr", agr);
        return "bn/bnUtilise";
    }

    @GetMapping(value = "banUtilises")
    @ResponseBody
    public JqGrid<EuBonNeutreUtilise> loadBanUtilise(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                                     @RequestParam(value = "code", required = false) String code,
                                                     @RequestParam(value = "deb", required = false) String dateDebut,
                                                     @RequestParam(value = "fin", required = false) String dateFin,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "rows", required = false) Integer rows,
                                                     @RequestParam(value = "sidx", required = false) String sidx,
                                                     @RequestParam(value = "sord", required = false) String sord) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date deb = null;
        Date fin = null;
        if (StringUtils.isNotBlank(dateDebut)) {
            try {
                deb = format.parse(dateDebut);
            } catch (ParseException e) {
            }
        }
        if (StringUtils.isNotBlank(dateFin)) {
            try {
                fin = format.parse(dateFin);
            } catch (ParseException e) {
            }
        }
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

        JqGrid<EuBonNeutreUtilise> response = new JqGrid<>();
        Page<EuBonNeutreUtilise> bonNeutrePage = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            bonNeutrePage = bnUtiliseService.findByBonNeutreCodeMembre(codeMembre, pageRequest);
        } else if (StringUtils.isNotBlank(code)) {
            bonNeutrePage = bnUtiliseService.findByBonNeutreCode(code, pageRequest);
        } else if (deb != null && fin != null) {
            bonNeutrePage = bnUtiliseService.findByBonNeutreUtiliseDateBetween(deb, fin, pageRequest);
        } else if (deb != null) {
            bonNeutrePage = bnUtiliseService.findByBonNeutreUtiliseDateAfter(deb, pageRequest);
        } else if (fin != null) {
            bonNeutrePage = bnUtiliseService.findByBonNeutreUtiliseDateBefore(fin, pageRequest);
        } else {
            bonNeutrePage = bnUtiliseService.listAll(pageRequest);
        }
        if (Objects.nonNull(bonNeutrePage) && bonNeutrePage.getTotalElements() > 0) {
            response.setRows(bonNeutrePage.getContent());
            response.setTotal(Integer.toString(bonNeutrePage.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "bnUtilise")
    @ResponseBody
    public JqGrid<EuBonNeutreUtilise> loadBanUtilise(@RequestParam(value = "idDetail", required = false) Integer idDetail,
                                                     @RequestParam(value = "idBn", required = false) Integer idbn,
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

        JqGrid<EuBonNeutreUtilise> response = new JqGrid<>();
        Page<EuBonNeutreUtilise> bonNeutrePage = null;
        if (idDetail != null) {
            bonNeutrePage = bnUtiliseService.findByBonNeutreDetailId(idDetail, pageRequest);
        } else {
            bonNeutrePage = bnUtiliseService.findByBonNeutreId(idbn, pageRequest);
        }
        if (Objects.nonNull(bonNeutrePage) && bonNeutrePage.getTotalElements() > 0) {
            response.setRows(bonNeutrePage.getContent());
            response.setTotal(Integer.toString(bonNeutrePage.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "bnDetail")
    @ResponseBody
    public JqGrid<EuBonNeutreDetail> loadBanDetail(@RequestParam(value = "idbn", required = false) Integer idBn,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "rows", required = false) Integer rows,
                                                   @RequestParam(value = "sidx", required = false) String sidx,
                                                   @RequestParam(value = "sord", required = false) String sord) {
        Sort sort = null;
        String orderBy = sidx;
        if (sord.equals("desc")) {
            sort = Sort.by(Sort.Direction.DESC, orderBy);
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
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

        JqGrid<EuBonNeutreDetail> response = new JqGrid<>();
        Page<EuBonNeutreDetail> bonNeutrePage = bnNeutreDetailService.findByBonNeutreId(idBn, pageRequest);
        if (Objects.nonNull(bonNeutrePage) && bonNeutrePage.getTotalElements() > 0) {
            response.setRows(bonNeutrePage.getContent());
            response.setTotal(Integer.toString(bonNeutrePage.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }
}
