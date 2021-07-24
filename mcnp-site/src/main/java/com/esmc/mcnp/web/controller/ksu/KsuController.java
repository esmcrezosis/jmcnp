package com.esmc.mcnp.web.controller.ksu;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esmc.mcnp.dto.ksu.Carte;
import com.esmc.mcnp.mapper.ksu.CarteMapper;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.util.ErrorDTO;
import com.esmc.mcnp.util.JqGrid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmc.mcnp.config.AppProperties;
import com.esmc.mcnp.core.constant.AppConstant;
import com.esmc.mcnp.core.utils.FileUtils;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.ksu.EuCarte;
import com.esmc.mcnp.services.file.FileService;
import com.esmc.mcnp.services.ksu.EuCarteService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/ksu")
public class KsuController extends BaseController {

    private EuCarteService carteService;
    private CarteMapper carteMapper;
    private AppProperties appProperties;
    private FileService fileService;

    @Autowired
    public KsuController(EuCarteService carteService, CarteMapper carteMapper, AppProperties appProperties,
                         FileService fileService) {
        this.carteService = carteService;
        this.appProperties = appProperties;
        this.fileService = fileService;
        this.carteMapper = carteMapper;
    }

    @GetMapping(value = "/index")
    public String indexksu() {
        return "ksu/index";
    }

    @GetMapping(value = "/search")
    public String searchKsu(ModelMap model, Pageable page,
                            @ModelAttribute("codeMembre") @RequestParam(name = "codeMembre", required = false) String codeMembre,
                            @ModelAttribute("deb") @RequestParam(name = "deb", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDeb,
                            @ModelAttribute("fin") @RequestParam(name = "fin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        Page<EuCarte> cartes = null;
        if (StringUtils.isNotBlank(codeMembre)) {
            cartes = carteService.findKsuByMembre(codeMembre, page);
            model.addAttribute("codeMembre", codeMembre);
            model.addAttribute("deb", dateDeb);
            model.addAttribute("fin", dateFin);
            model.addAttribute("cartes", cartes);
        } else if (Objects.nonNull(dateDeb) && Objects.nonNull(dateFin)) {
            cartes = carteService.findByImprimerAndDateDemandeBetween(false, dateDeb, dateFin, page);
            model.addAttribute("codeMembre", codeMembre);
            model.addAttribute("deb", dateDeb);
            model.addAttribute("fin", dateFin);
            model.addAttribute("cartes", cartes);
        } else if (Objects.nonNull(dateDeb)) {
            cartes = carteService.findByDateDemande(dateDeb, page);
            model.addAttribute("codeMembre", codeMembre);
            model.addAttribute("deb", dateDeb);
            model.addAttribute("fin", dateFin);
            model.addAttribute("cartes", cartes);
        }
        return "ksu/search";
    }

    @GetMapping(value = "/impression")
    public String searchKsu() {
        return "ksu/cartes";
    }

    @GetMapping(value = "myksu")
    public String listeKsu() {
        return "ksu/myksu";
    }

    @GetMapping(value = "listksu")
    @ResponseBody
    public JqGrid<EuCarte> loadCartes(
            @RequestParam(value = "codeMembre", required = false) String codeMembre,
            @RequestParam(value = "deb", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deb,
            @RequestParam(value = "fin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(value = "_search", required = false) Boolean search,
            @RequestParam(value = "filters", required = false) String filters,
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
        JqGrid<EuCarte> carteJqGrid = new JqGrid<>();
        Page<EuCarte> cartePage = null;
        if (deb != null && fin != null) {
            cartePage = carteService.findByUtilisateurAndDateIn(user.getIdUtilisateur(), deb, fin, pageRequest);
        } else if (deb != null) {
            cartePage = carteService.findByUtilisateurAndDateInf(user.getIdUtilisateur(), deb, pageRequest);
        } else if (fin != null) {
            cartePage = carteService.findByUtilisateurAndDateSup(user.getIdUtilisateur(), fin, pageRequest);
        } else {
            cartePage = carteService.findByUser(user.getIdUtilisateur(), pageRequest);
        }

        if (Objects.nonNull(cartePage) && cartePage.getTotalElements() > 0) {
            List<EuCarte> creances = cartePage.getContent();
            carteJqGrid.setRows(creances);
            carteJqGrid.setRecords(Long.toString(cartePage.getTotalElements()));
            carteJqGrid.setTotal(Integer.toString(cartePage.getTotalPages()));
            carteJqGrid.setPage(Integer.toString(cartePage.getNumber() + 1));
            return carteJqGrid;
        } else {
            carteJqGrid.setRows(Lists.newArrayList());
            carteJqGrid.setRecords("0");
            carteJqGrid.setTotal("0");
            carteJqGrid.setPage("0");
            return carteJqGrid;
        }
    }

    @ResponseBody
    @GetMapping(value = "listeCartes")
    public JqGrid<?> loadKsu(@RequestParam(value = "codeMembre", required = false) String codeMembre,
                             @RequestParam(value = "deb", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deb,
                             @RequestParam(value = "fin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                             @RequestParam(value = "_search", required = false) Boolean search,
                             @RequestParam(value = "filters", required = false) String filters,
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
            rows = 20;
        }
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }

        EuUtilisateur utilisateur = getCurrentUser();
        JqGrid<Carte> response = new JqGrid<>();
        if (Objects.nonNull(utilisateur)) {
            Page<EuCarte> euCartes = null;
            if (StringUtils.isNotBlank(codeMembre)) {
                euCartes = carteService.findKsuByMembre(codeMembre, pageRequest);
            } else if (Objects.nonNull(deb) && Objects.nonNull(fin)) {
                euCartes = carteService.findByImprimerAndDateDemandeBetween(false, deb, fin, pageRequest);
            } else if (Objects.nonNull(deb)) {
                euCartes = carteService.findByDateDemandeAndImprimer(deb, false, pageRequest);
            }
            if (euCartes != null && euCartes.getTotalElements() > 0) {
                List<Carte> cartes = carteMapper.fromEuCarteList(euCartes.getContent());
                response.setRows(cartes);
                response.setRecords(Long.toString(euCartes.getTotalElements()));
                response.setTotal(Integer.toString(euCartes.getTotalPages()));
                response.setPage(Integer.toString(euCartes.getNumber() + 1));
                return response;
            } else {
                response.setRows(Lists.newArrayList());
                response.setRecords("0");
                response.setTotal("0");
                response.setPage("0");
                return response;
            }
        } else {
            response.setRows(Lists.newArrayList());
            response.setRecords("0");
            response.setTotal("0");
            response.setPage("0");
            return response;
        }
    }

    @GetMapping(value = "/carte")
    public String addksu(ModelMap model) {
        EuCarte carte = new EuCarte();
        carte.setEuMembre(new EuMembre());
        carte.setEuMembreMorale(new EuMembreMorale());
        model.addAttribute("carte", carte);
        return "ksu/carte";
    }

    @PostMapping(value = "/carte")
    public String editCarte(@ModelAttribute EuCarte carte, RedirectAttributes model) {
        if (Objects.nonNull(carte)) {
            carteService.update(carte);
            return "redirect:/search";
        }
        return null;
    }

    @GetMapping(value = "/edit/{id}")
    public String editCarte(@PathVariable(name = "id", required = false) Long id, ModelMap model) {
        if (id != null) {
            EuCarte carte = carteService.getById(id);
            model.addAttribute("readonly", true);
            model.addAttribute("carte", carte);
            return "ksu/carte";
        } else {
            EuCarte carte = new EuCarte();
            model.addAttribute("readonly", false);
            model.addAttribute("carte", carte);
            return "ksu/carte";
        }
    }

    @GetMapping(value = "livrer")
    public ResponseEntity<?> searchKsu(@RequestParam String codeMembre) {
        if (StringUtils.isNotBlank(codeMembre)) {
            if (codeMembre.endsWith("P")) {
                return ResponseEntity.ok(carteMapper.fromEuCarte(carteService.findByMembre(codeMembre)));
            } else {
                return ResponseEntity.ok(carteMapper.fromEuCarte(carteService.findByMembreMorale(codeMembre)));
            }
        } else {
            return ResponseEntity.badRequest().body("Vous devez fournir le code membre");
        }
    }

    @GetMapping(value = "/edit/{id}/{action}")
    public ResponseEntity<String> majKsu(@PathVariable("id") Long idCarte, @PathVariable("action") String action) {
        if (idCarte != null) {
            EuCarte carte = carteService.getById(idCarte);
            if (Objects.nonNull(carte)) {
                if ("imp".equals(action)) {
                    carte.setImprimer(true);
                    carte.setDateImpression(LocalDate.now());
                } else {
                    if (carte.isImprimer()) {
                        carte.setLivrer(true);
                        carte.setDateLivraison(LocalDate.now());
                    } else {
                        return new ResponseEntity<>("Echec de la Mise à jour du KSU N° " + idCarte
                                + "\n La carte n'est pas encore imprimée", HttpStatus.OK);
                    }
                }
                carteService.update(carte);
                return new ResponseEntity<>("Mise à jour du KSU N° " + idCarte + " bien effectuée", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Echec de la Mise à jour du KSU N° " + idCarte, HttpStatus.OK);
    }

    @GetMapping(value = "/imprimer")
    public ResponseEntity<String> imprimerKsu(@RequestParam("cartes") List<Long> cartes,
                                              @RequestParam("action") String action) {
        if (!cartes.isEmpty()) {
            List<EuCarte> ksus = carteService.listAllByIds(cartes);
            ksus.forEach(c -> {
                if ("imp".equals(action)) {
                    c.setImprimer(true);
                    c.setDateImpression(LocalDate.now());
                } else {
                    c.setLivrer(true);
                    c.setDateLivraison(LocalDate.now());
                }
            });
            carteService.updateInBatch(ksus);
            return new ResponseEntity<>("Mise à jour des KSU bien effectuée", HttpStatus.OK);

        }
        return new ResponseEntity<>("Echec de la Mise à jour des KSU", HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping(value = "/download")
    public ResponseEntity<?> downloadResource(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(name = "cartes", required = false) List<Long> cartes) {
        List<EuCarte> ksus = Lists.newArrayList();
        if (cartes != null && !cartes.isEmpty()) {
            ksus = carteService.listAllByIds(cartes);
        } else {
            String codeMembre = request.getParameter("codeMembre");
            String deb = request.getParameter("deb");
            String fin = request.getParameter("fin");
            if (StringUtils.isNotBlank(codeMembre)) {
                ksus = carteService.findKsuByMembre(codeMembre);
            } else if (StringUtils.isNotBlank(deb) && StringUtils.isNotBlank(fin)) {
                LocalDate dateDeb = LocalDate.parse(deb, DateTimeFormatter.ISO_DATE);
                LocalDate dateFin = LocalDate.parse(fin, DateTimeFormatter.ISO_DATE);
                ksus = carteService.findByDateDemandeBetween(dateDeb, dateFin);
            } else if (StringUtils.isNotBlank(deb)) {
                LocalDate dateDeb = LocalDate.parse(deb, DateTimeFormatter.ISO_DATE);
                ksus = carteService.findByDateDemande(dateDeb);
            }
        }
        if (!ksus.isEmpty()) {
            List<Path> files = Lists.newArrayList();
            List<EuCarte> ksuis = Lists.newArrayList();
            ksus.forEach(c -> {
                Path file = Paths.get(c.getUrlCarte());
                if (file.toFile().exists()) {
                    files.add(Paths.get(c.getUrlCarte()));
                    c.setImprimer(true);
                    c.setDateImpression(LocalDate.now());
                    ksuis.add(c);
                }
            });
            if (!ksuis.isEmpty()) {
                String baseUrl = appProperties.getCarteUrl();
                String fileName = "cartes" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
                Path zipPath = Paths.get(baseUrl + AppConstant.FILE_SEPARATOR + fileName + ".zip");
                try (OutputStream outputStream = Files.newOutputStream(zipPath)) {
                    try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
                        FileUtils.zip(files, zipOut);
                        Resource resource = fileService.loadFileAsResource(zipPath.getFileName().toString());

                        carteService.updateInBatch(ksuis);
                        String contentType = null;
                        try {
                            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                        } catch (IOException e) {
                            log.error("Could not determine file type.", e);
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
                        }
                        // Fallback to the default content type if type could not be determined
                        if (contentType == null) {
                            contentType = "application/octet-stream";
                        }
                        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                        "attachment; filename=\"" + resource.getFilename() + "\"")
                                .body(resource);
                    } catch (IOException e) {
                        log.error("Could not determine file type.", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
                    }
                } catch (IOException e) {
                    log.error("Could not determine file type.", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
                }

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorDTO("Les cartes à imprimer n'ont pas d'images de cartes associées"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDTO("Vous devez fournir des renseignements des cartes à imprimer"));
        }
    }

    @GetMapping(value = "find")
    public ResponseEntity<?> findKsu(@RequestParam(name = "codeMembre") String codeMembre) {
        if (!codeMembre.isEmpty()) {
            if (codeMembre.endsWith("P")) {
                return ResponseEntity.ok().body(carteMapper.fromEuCarte(carteService.findByMembre(codeMembre)));
            } else {
                return ResponseEntity.ok().body(carteMapper.fromEuCarte(carteService.findByMembreMorale(codeMembre)));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
