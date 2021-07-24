package com.esmc.mcnp.web.controller.bc;

import com.esmc.mcnp.dto.bc.CompteCredit;
import com.esmc.mcnp.mapper.bc.EuCompteCreditMapper;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.services.cm.EuCategorieCompteService;
import com.esmc.mcnp.services.cm.EuCompteCreditService;
import com.esmc.mcnp.services.cm.EuCompteService;
import com.esmc.mcnp.services.cm.EuProduitService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
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
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/cm")
public class CompteController extends BaseController {

    private EuCompteService compteService;
    private EuCompteCreditService compteCreditService;
    private EuCategorieCompteService categorieCompteService;
    private EuProduitService produitService;
    private EuCompteCreditMapper creditMapper;

    @Autowired
    public CompteController(EuCompteService compteService, EuCompteCreditService compteCreditService,
                            EuCategorieCompteService categorieCompteService,
                            EuProduitService produitService, EuCompteCreditMapper creditMapper) {
        this.compteService = compteService;
        this.compteCreditService = compteCreditService;
        this.categorieCompteService = categorieCompteService;
        this.produitService = produitService;
        this.creditMapper = creditMapper;
    }

    @GetMapping(value = "comptes")
    public String listeCompte(Model model) {
        List<EuCategorieCompte> cats = categorieCompteService.listAll();
        model.addAttribute("cats", cats);
        return "cm/comptes";
    }

    @GetMapping(value = "lcredits")
    public String listeCompteCredit(Model model) {
        List<EuProduit> produits = produitService.listAll();
        model.addAttribute("produits", produits);
        return "cm/compteCredits";
    }

    @GetMapping(value = "{agr}/compte/{id}")
    public String vueCompte(@PathVariable("agr") String agr, @PathVariable("id") String codeCompte, Model model) {
        EuCompte compte = compteService.getById(codeCompte);
        model.addAttribute("compte", compte);
        model.addAttribute("agr", agr);
        return "cm/compte";
    }

    @GetMapping(value = "cc/{id}")
    public String vueCompteCredit(@PathVariable("id") Long id, Model model) {
        EuCompteCredit compteCredit = compteCreditService.getById(id);
        model.addAttribute("cc", compteCredit);
        return "cm/compteCredit";
    }

    @GetMapping(value = "listeComptes")
    @ResponseBody
    public JqGrid<EuCompte> loadComptes(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                        @RequestParam(value = "codecat", required = false) String codeCat,
                                        @RequestParam(value = "type", required = false) String typeCompte,
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
            rows = 20;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }

        JqGrid<EuCompte> response = new JqGrid<>();
        Page<EuCompte> euComptes = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            if (StringUtils.isNotBlank(codeCat)) {
                euComptes = compteService.findCompteByMembreAndCategorie(codeMembre, codeCat, pageRequest);
            } else if (StringUtils.isNotBlank(typeCompte)) {
                euComptes = compteService.findCompteByMembreAndType(codeMembre, typeCompte, pageRequest);
            } else {
                euComptes = compteService.findCompteByMembre(codeMembre, pageRequest);
            }
        } else if (StringUtils.isNotBlank(codeCat)) {
            euComptes = compteService.findCompteByCategorie(codeCat, pageRequest);
        } else if (StringUtils.isNotBlank(typeCompte)) {
            euComptes = compteService.findCompteByType(typeCompte, pageRequest);
        } else {
            euComptes = compteService.findAll(pageRequest);
        }
        if (Objects.nonNull(euComptes) && euComptes.getTotalElements() > 0) {
            response.setRows(euComptes.getContent());
            response.setTotal(Integer.toString(euComptes.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "bc")
    @ResponseBody
    public JqGrid<EuCompteCredit> loadCompteCredits(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                                    @RequestParam(value = "codeCompte", required = false) String codeCompte,
                                                    @RequestParam(value = "prk", required = false) Double prk,
                                                    @RequestParam(value = "typeBc", required = false) String produit,
                                                    @RequestParam(value = "typeRecurrent", required = false) String typeRecurrent,
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

        JqGrid<EuCompteCredit> response = new JqGrid<>();
        Page<EuCompteCredit> euCompteCredits = null;
        if (StringUtils.isNotBlank(codeMembre) && codeMembre.endsWith("P")) {
            if ("r".equalsIgnoreCase(produit)) {
                produit = "RPGr";
            } else if ("nr".equalsIgnoreCase(produit)) {
                produit = "RPGnr";
            }
        } else if (StringUtils.isNotBlank(codeMembre) && codeMembre.endsWith("P")) {
            if ("r".equalsIgnoreCase(produit)) {
                produit = "Ir";
            } else if ("nr".equalsIgnoreCase(produit)) {
                produit = "Inr";
            }
        } else {
            if (StringUtils.isNotBlank(produit)) {
                produit = "%" + produit;
            }
        }
        if (StringUtils.isNotBlank(codeCompte)) {
            if (StringUtils.isNotBlank(typeRecurrent)) {
                euCompteCredits = compteCreditService.findByCompteAndType(codeCompte, typeRecurrent, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByCodeCompte(codeCompte, pageRequest);
            }
        } else if (StringUtils.isNotBlank(codeMembre)) {
            if (StringUtils.isNotBlank(typeRecurrent)) {
                euCompteCredits = compteCreditService.findByCodeMembreAndTypeRecurrent(codeMembre, typeRecurrent, pageRequest);
            } else if (StringUtils.isNotBlank(produit)) {
                euCompteCredits = compteCreditService.findCreditByMembreAndProduit(codeMembre, produit, pageRequest);
            } else if (deb != null && fin != null) {
                euCompteCredits = compteCreditService.findByMembreAndDateBetween(codeMembre, deb, fin, pageRequest);
            } else if (deb != null) {
                euCompteCredits = compteCreditService.findByMembreAndDateSup(codeMembre, deb, pageRequest);
            } else if (fin != null) {
                euCompteCredits = compteCreditService.findByMembreAndDateInf(codeMembre, fin, pageRequest);
            } else if (prk != null) {
                euCompteCredits = compteCreditService.fetchByMembreAndPrk(codeMembre, prk, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByCodeMembre(codeMembre, pageRequest);
            }
        } else if (StringUtils.isNotBlank(typeRecurrent)) {
            if (deb != null && fin != null) {
                euCompteCredits = compteCreditService.findByTypeRecurrentAndDateBetween(typeRecurrent, deb, fin, pageRequest);
            } else if (deb != null) {
                euCompteCredits = compteCreditService.findByTypeRecurrentAndDateSup(typeRecurrent, deb, pageRequest);
            } else if (fin != null) {
                euCompteCredits = compteCreditService.findByTypeRecurrentAndDateInf(typeRecurrent, fin, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByTypeRecurrent(typeRecurrent, pageRequest);
            }
        } else if (StringUtils.isNotBlank(produit)) {
            if (deb != null && fin != null) {
                euCompteCredits = compteCreditService.findByProduitAndDateBetween(produit, deb, fin, pageRequest);
            } else if (deb != null) {
                euCompteCredits = compteCreditService.findByProduitAndDateSup(produit, deb, pageRequest);
            } else if (fin != null) {
                euCompteCredits = compteCreditService.findByProduitAndDateInf(produit, fin, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByProduit(produit, pageRequest);
            }
        } else if (deb != null && fin != null) {
            euCompteCredits = compteCreditService.findByDateOctroiBetween(deb, fin, pageRequest);
        } else if (deb != null) {
            euCompteCredits = compteCreditService.findByDatesup(deb, pageRequest);
        } else if (fin != null) {
            euCompteCredits = compteCreditService.findByDateInf(deb, pageRequest);
        } else {
            euCompteCredits = compteCreditService.findAll(pageRequest);
        }
        if (Objects.nonNull(euCompteCredits) && euCompteCredits.getTotalElements() > 0) {
            response.setRows(euCompteCredits.getContent());
            response.setTotal(Integer.toString(euCompteCredits.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }

    @GetMapping(value = "credits")
    @ResponseBody
    public JqGrid<CompteCredit> listCredits(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                                            @RequestParam(value = "prk", required = false) Double prk,
                                            @RequestParam(value = "produit", required = false) String produit,
                                            @RequestParam(value = "typeRecurrent", required = false) String typeRecurrent,
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

        JqGrid<CompteCredit> response = new JqGrid<>();
        Page<EuCompteCredit> euCompteCredits = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            if (StringUtils.isNotBlank(typeRecurrent)) {
                euCompteCredits = compteCreditService.findByCodeMembreAndTypeRecurrent(codeMembre, typeRecurrent, pageRequest);
            } else if (StringUtils.isNotBlank(produit)) {
                euCompteCredits = compteCreditService.findCreditByMembreAndProduit(codeMembre, produit, pageRequest);
            } else if (deb != null && fin != null) {
                euCompteCredits = compteCreditService.findByMembreAndDateBetween(codeMembre, deb, fin, pageRequest);
            } else if (deb != null) {
                euCompteCredits = compteCreditService.findByMembreAndDateSup(codeMembre, deb, pageRequest);
            } else if (fin != null) {
                euCompteCredits = compteCreditService.findByMembreAndDateInf(codeMembre, fin, pageRequest);
            } else if (prk != null) {
                euCompteCredits = compteCreditService.fetchByMembreAndPrk(codeMembre, prk, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByCodeMembre(codeMembre, pageRequest);
            }
        } else if (StringUtils.isNotBlank(typeRecurrent)) {
            if (deb != null && fin != null) {
                euCompteCredits = compteCreditService.findByTypeRecurrentAndDateBetween(typeRecurrent, deb, fin, pageRequest);
            } else if (deb != null) {
                euCompteCredits = compteCreditService.findByTypeRecurrentAndDateSup(typeRecurrent, deb, pageRequest);
            } else if (fin != null) {
                euCompteCredits = compteCreditService.findByTypeRecurrentAndDateInf(typeRecurrent, fin, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByTypeRecurrent(typeRecurrent, pageRequest);
            }
        } else if (StringUtils.isNotBlank(produit)) {
            if (deb != null && fin != null) {
                euCompteCredits = compteCreditService.findByProduitAndDateBetween(produit, deb, fin, pageRequest);
            } else if (deb != null) {
                euCompteCredits = compteCreditService.findByProduitAndDateSup(produit, deb, pageRequest);
            } else if (fin != null) {
                euCompteCredits = compteCreditService.findByProduitAndDateInf(produit, fin, pageRequest);
            } else {
                euCompteCredits = compteCreditService.findByProduit(produit, pageRequest);
            }
        } else if (deb != null && fin != null) {
            euCompteCredits = compteCreditService.findByDateOctroiBetween(deb, fin, pageRequest);
        } else if (deb != null) {
            euCompteCredits = compteCreditService.findByDatesup(deb, pageRequest);
        } else if (fin != null) {
            euCompteCredits = compteCreditService.findByDateInf(deb, pageRequest);
        } else {
            euCompteCredits = compteCreditService.findAll(pageRequest);
        }
        if (Objects.nonNull(euCompteCredits) && euCompteCredits.getTotalElements() > 0) {
            response.setRows(creditMapper.toEntities(euCompteCredits.getContent()));
            response.setTotal(Integer.toString(euCompteCredits.getTotalPages()));
        } else {
            response.setRows(Lists.newArrayList());
            response.setTotal("0");
        }
        return response;
    }
}
