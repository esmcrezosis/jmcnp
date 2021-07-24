package com.esmc.mcnp.web.controller.ksu;

import com.esmc.mcnp.dto.ksu.LivraisonKsu;
import com.esmc.mcnp.mapper.ksu.LivraisonKsuMapper;
import com.esmc.mcnp.model.enums.LivraisonEnum;
import com.esmc.mcnp.model.ksu.EuLivraisonKsu;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.services.ksu.EuCarteService;
import com.esmc.mcnp.services.ksu.EuLivraisonKsuService;
import com.esmc.mcnp.web.controller.base.BaseController;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/carte")
public class LivraisonKsuController extends BaseController {

    private EuLivraisonKsuService livraisonKsuService;
    private EuCarteService carteService;
    private LivraisonKsuMapper livraisonKsuMapper;

    @Autowired
    public LivraisonKsuController(EuLivraisonKsuService livraisonKsuService, EuCarteService carteService,
                                  LivraisonKsuMapper livraisonKsuMapper) {
        this.livraisonKsuService = livraisonKsuService;
        this.carteService = carteService;
        this.livraisonKsuMapper = livraisonKsuMapper;
    }

    @GetMapping(value = "demande")
    public String demandeLivraison(ModelMap model, @RequestParam(name = "deb", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deb,
                                   @RequestParam(name = "fin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin, Pageable page) {
        if (deb == null && fin == null) {
            model.addAttribute("cartes", carteService.findByImprimer(true, getCurrentUser().getIdUtilisateur(), page));
        } else if (deb != null && fin == null) {
            model.addAttribute("cartes",
                    carteService.findByDateDemandeAndImprimer(deb, true, getCurrentUser().getIdUtilisateur(), page));
            model.addAttribute("deb", deb);
        } else if (deb == null && fin != null) {
            if (fin.isAfter(LocalDate.now())) {
                model.addAttribute("cartes", carteService.findByImprimerAndDateDemandeBetween(true,
                        Objects.requireNonNull(getCurrentUser()).getIdUtilisateur(), LocalDate.now(), fin, page));
            } else {
                model.addAttribute("cartes", carteService.findByImprimerAndDateDemandeBetween(true,
                        Objects.requireNonNull(getCurrentUser()).getIdUtilisateur(), fin, LocalDate.now(), page));
            }
            model.addAttribute("fin", fin);
        } else {
            model.addAttribute("cartes", carteService.findByImprimerAndDateDemandeBetween(true,
                    Objects.requireNonNull(getCurrentUser()).getIdUtilisateur(), deb, fin, page));
            model.addAttribute("deb", deb);
            model.addAttribute("fin", fin);
        }
        return "ksu/demande";
    }

    @Transactional
    @GetMapping(value = "addDemande")
    public ResponseEntity<String> addDemande(@RequestParam("cartes") List<Long> cartes) {
        EuUtilisateur currentUser = getCurrentUser();
        if (!cartes.isEmpty()) {
            EuLivraisonKsu livraisonKsu = new EuLivraisonKsu();
            livraisonKsu.setDateDemande(LocalDate.now()).setNombre(cartes.size()).setStatus(LivraisonEnum.ENCOURS)
                    .setUser(currentUser).setCodeSuivi(RandomStringUtils.randomAlphanumeric(7).toUpperCase())
                    .setCartes(carteService.listAllByIds(cartes));
            livraisonKsuService.create(livraisonKsu);
            return new ResponseEntity<String>("Demande bien éffectuée", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Echec de la demande", HttpStatus.OK);
    }

    @GetMapping(value = "livraison")
    public String livraison(@RequestParam(name = "deb", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deb,
                            @RequestParam(name = "fin", required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin, ModelMap model, Pageable page) {
        if (deb == null && fin == null) {
            model.addAttribute("livraisons", livraisonKsuService.findByDateDemande(LocalDate.now(), page));
        } else if (deb != null && fin == null) {
            model.addAttribute("livraisons", livraisonKsuService.findByDateDemande(deb, page));
        } else if (deb == null && fin != null) {
            if (fin.isAfter(LocalDate.now())) {
                model.addAttribute("livraisons",
                        livraisonKsuService.findByDateDemandeBetween(LocalDate.now(), fin, page));
            } else {
                model.addAttribute("livraisons",
                        livraisonKsuService.findByDateDemandeBetween(fin, LocalDate.now(), page));
            }
        } else {
            model.addAttribute("livraisons", livraisonKsuService.findByDateDemandeBetween(deb, fin, page));
        }
        return "ksu/livraisons";
    }

    @GetMapping(value = "demandes")
    public String listDemande(@RequestParam(name = "deb", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deb,
                              @RequestParam(name = "fin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin, ModelMap model, Pageable page) {
        EuUtilisateur user = getCurrentUser();
        if (deb == null && fin == null) {
            model.addAttribute("livraisons", livraisonKsuService.findByUser(user.getIdUtilisateur(), page));
        } else if (deb != null && fin == null) {
            model.addAttribute("livraisons", livraisonKsuService.findByUser(user.getIdUtilisateur(), deb, page));
        } else if (deb == null && fin != null) {
            if (fin.isAfter(LocalDate.now())) {
                model.addAttribute("livraisons", livraisonKsuService.findByUserAndDateDemande(user.getIdUtilisateur(),
                        LocalDate.now(), fin, page));
            } else {
                model.addAttribute("livraisons", livraisonKsuService.findByUserAndDateDemande(user.getIdUtilisateur(),
                        fin, LocalDate.now(), page));
            }
        } else {
            assert user != null;
            model.addAttribute("livraisons",
                    livraisonKsuService.findByUserAndDateDemande(user.getIdUtilisateur(), deb, fin, page));
        }
        return "ksu/demandes";
    }

    @GetMapping(value = "envoi")
    public String editLivraison(@RequestParam(name = "id", required = false) Long id, ModelMap model) {
        if (id != null) {
            EuLivraisonKsu liv = livraisonKsuService.getById(id);
            if (Objects.nonNull(liv)) {
                model.addAttribute("livraison", liv);
                return "ksu/livraison";
            } else {
                model.addAttribute("message", "Livraison " + id + " non trouvée");
                return "redirect:/livraison";
            }
        } else {
            model.addAttribute("livraison", new EuLivraisonKsu());
            return "ksu/livraison";
        }
    }

    @Transactional
    @PostMapping(value = "envoi")
    public String majLivraison(@ModelAttribute(name = "livraison") EuLivraisonKsu livraison, RedirectAttributes model) {
        if (Objects.nonNull(livraison) && livraison.getId() != null) {
            livraison = livraisonKsuService.getById(livraison.getId());
            livraison.setDateLivraison(LocalDate.now()).setStatus(LivraisonEnum.ENVOYE);
            livraisonKsuService.update(livraison);
            return "redirect:/carte/livraison";
        }
        return "ksu/livraison";
    }

    @GetMapping(value = "reception")
    public String reception(@RequestParam(name = "id", required = false) Long id, ModelMap model) {
        if (id != null) {
            EuLivraisonKsu liv = livraisonKsuService.getById(id);
            if (Objects.nonNull(liv)) {
                model.addAttribute("livraison", liv);
                return "ksu/reception";
            } else {
                model.addAttribute("message", "Livraison " + id + " non trouvée");
                return "redirect:/carte/demandes";
            }
        } else {
            model.addAttribute("livraison", new EuLivraisonKsu());
            return "ksu/reception";
        }
    }

    @GetMapping(value = "findKsu")
    public ResponseEntity<?> searchLivraison(@RequestParam(name = "code") String codeSuivi) {
        if (StringUtils.isNotBlank(codeSuivi)) {
            return ResponseEntity.ok().body(livraisonKsuService.findByCodeSuivi(codeSuivi));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livraison non trouvée");
        }
    }

    @Transactional
    @PostMapping(value = "reception")
    public String recLivraison(@ModelAttribute(name = "livraison") LivraisonKsu livraison, RedirectAttributes model) {
        StringBuilder builder = new StringBuilder();
        if (Objects.nonNull(livraison) && livraison.getId() != null) {
            Optional<EuLivraisonKsu> ksu = livraisonKsuService.fetchById(livraison.getId());
            if (ksu.isPresent() && ksu.get().getStatus().equals(LivraisonEnum.ENVOYE)) {
                EuLivraisonKsu livraisonKsu = livraisonKsuMapper.toEuLivraisonKsu(livraison);
                livraisonKsu.setUser(getCurrentUser());
                livraisonKsu.setDateReception(LocalDateTime.now());
                livraisonKsuService.update(livraisonKsu);
                model.addFlashAttribute("message", "Reception bien effectuée");
                return "redirect:/carte/demandes";
            } else {
                builder.append("Cette Demande de livraison n'est pas encore envoyée");
            }
        }
        model.addAttribute("message", builder.toString());
        return "ksu/reception";
    }

}
