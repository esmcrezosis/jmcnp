package com.esmc.mcnp.web.controller.cm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.components.RechercheService;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.others.EuCours;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.acteur.EuFiliere;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.dto.acteur.Acteur;
import com.esmc.mcnp.dto.acteur.Filiere;
import com.esmc.mcnp.dto.acteur.MembreMorale;
import com.esmc.mcnp.dto.echange.DetailDomicilie;
import com.esmc.mcnp.dto.ksu.Compte;
import com.esmc.mcnp.dto.obps.ArticleStocke;
import com.esmc.mcnp.dto.other.Cour;
import com.esmc.mcnp.repositories.others.EuFiliereRepository;
import com.esmc.mcnp.web.dto.cm.Membre;
import com.esmc.mcnp.exception.ResourceNotFoundException;
import com.esmc.mcnp.util.DTOUtils;
import com.esmc.mcnp.util.ErrorDTO;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(value = "/recherche")
public class RechercheController {

	@Autowired
	private RechercheService rechercheService;
	@Autowired
	private DTOUtils dtoUtils;
	@Autowired
	private EuFiliereRepository filiereRepo;

	@RequestMapping(value = "creditsdomicilies/{codeMembre}", method = RequestMethod.GET)
	public List<DetailDomicilie> rechercheCreditsDomicilies(@PathVariable("codeMembre") String codeMembre)
			throws ResourceNotFoundException {
		if (!StringUtils.isNotBlank(codeMembre) || !(codeMembre.length() == 20) || !codeMembre.endsWith("P")) {
			throw new ResourceNotFoundException(codeMembre, "Code Membre incorrect");
		}
		List<DetailDomicilie> listDetailDomicilie = Lists.newArrayList();
		List<EuDetailDomicilie> listEuDetailDomicilie = rechercheService.FindDetailDomicilie(codeMembre);
		if (Objects.nonNull(listEuDetailDomicilie)) {
			for (EuDetailDomicilie euDetailDomicilie : listEuDetailDomicilie) {
				listDetailDomicilie.add(dtoUtils.detailDomicilieMapper(euDetailDomicilie));
			}
			return listDetailDomicilie;
		} else {
			throw new ResourceNotFoundException("Aucun cours de devise n'est enregistré");
		}

	}

	@GetMapping(value = "membre")
	public Membre rechercheMembre(@RequestParam String codeMembre) throws ResourceNotFoundException {
		if (codeMembre.endsWith("P")) {
			if (!StringUtils.isNotBlank(codeMembre) || !(codeMembre.length() == 20) || !codeMembre.endsWith("P")) {
				throw new ResourceNotFoundException(codeMembre, "Code Membre incorrect");
			}
			EuMembre euMembre = rechercheService.findMembreById(codeMembre);
			if (!Objects.nonNull(euMembre)) {
				throw new ResourceNotFoundException(codeMembre, "Le Code Membre n'existe pas");
			}
			return new Membre(euMembre.getCodeMembre(), euMembre.getNomMembre(), euMembre.getPrenomMembre(), euMembre.getDesactiver());
		} else {
			if (!StringUtils.isNotBlank(codeMembre) || !(codeMembre.length() == 20) || !codeMembre.endsWith("M")) {
				throw new ResourceNotFoundException(codeMembre, "Le Code Membre est Incorrect!");
			}
			EuMembreMorale morale = rechercheService.findMembreMoraleById(codeMembre);
			if (Objects.nonNull(morale)) {
				return new Membre(morale.getCodeMembreMorale(), morale.getRaisonSociale(), morale.getDesactiver());
			} else {
				throw new ResourceNotFoundException(codeMembre, "Le code membre n'existe pas");
			}
		}

	}

	@GetMapping(value = "morale")
	public MembreMorale rechercheMembreMorale(@RequestParam String codeMembre) throws ResourceNotFoundException {
		if (!StringUtils.isNotBlank(codeMembre) || !(codeMembre.length() == 20) || !codeMembre.endsWith("M")) {
			throw new ResourceNotFoundException(codeMembre, "Le Code Membre est Incorrect!");
		}
		EuMembreMorale morale = rechercheService.findMembreMoraleById(codeMembre);
		if (Objects.nonNull(morale)) {
			return dtoUtils.membreMoralMapper(morale);
		} else {
			throw new ResourceNotFoundException(codeMembre, "Le code membre n'existe pas");
		}

	}

	@RequestMapping(value = "compte/{codeCompte}", method = RequestMethod.GET)
	public Compte rechercheCompte(@PathVariable("codeCompte") String codeCompte) throws ResourceNotFoundException {
		EuCompte compte = rechercheService.findCompteById(codeCompte);
		if (Objects.nonNull(compte)) {
			return dtoUtils.mapCompte(compte);
		} else {
			throw new ResourceNotFoundException(codeCompte, "Le code compte n'existe pas");
		}
	}

	@RequestMapping(value = "listetpn/{codeCompte}", method = RequestMethod.GET)
	public List<String> rechercheListeTpn(@PathVariable("codeCompte") String codeCompte)
			throws ResourceNotFoundException {
		List<String> produits = rechercheService.FindListDesProduitsComptesTpn(codeCompte);
		if (Objects.nonNull(produits)) {
			return produits;
		} else {
			throw new ResourceNotFoundException(codeCompte, "Aucun produit de compte TPN trouvé");
		}
	}

	@RequestMapping(value = "listecapa/{codeCompte}", method = RequestMethod.GET)
	public List<String> rechercheListeCapa(@PathVariable("codeCompte") String codeCompte)
			throws ResourceNotFoundException {
		List<String> produits = rechercheService.FindListDesProduitsCapa(codeCompte);
		if (Objects.nonNull(produits)) {
			return produits;
		} else {
			throw new ResourceNotFoundException(codeCompte, "Aucun produit de compte Capa trouvé");
		}
	}

	@RequestMapping(value = "lecteur/{numSerie}", method = RequestMethod.GET)
	public Boolean rechercheLecteur(@PathVariable("numSerie") String numSerie) throws ResourceNotFoundException {
		Boolean verif = rechercheService.VerifierLecteur(numSerie);
		if (Objects.nonNull(verif)) {
			return verif;
		} else {
			throw new ResourceNotFoundException("Ce lecteur n'existe pas en base de données");
		}
	}

	@RequestMapping(value = "acteur/{codeMembre}", method = RequestMethod.GET)
	public Acteur rechercheActeurByMembre(@PathVariable("codeMembre") String codeMembre)
			throws ResourceNotFoundException {
		EuActeur acteur = rechercheService.getEuActeurByCodeMembre(codeMembre);
		if (Objects.nonNull(acteur)) {
			return dtoUtils.mapActeur(acteur);
		} else {
			throw new ResourceNotFoundException("Ce acteur n'existe pas");
		}
	}

	@RequestMapping(value = "filiere/{idFiliere}", method = RequestMethod.GET)
	public Filiere rechercheFiliereByMembreMorale(@PathVariable("idFiliere") Long idFiliere)
			throws ResourceNotFoundException {
		EuFiliere eufiliere = filiereRepo.findOne(idFiliere);
		if (Objects.nonNull(eufiliere)) {
			return dtoUtils.mapFiliere(eufiliere);
		} else {
			throw new ResourceNotFoundException("Cet acteur n'a pas de filiere");
		}
	}

	@RequestMapping(value = "articlesStockes/{codeMembre}", method = RequestMethod.GET)
	public List<ArticleStocke> rechercheArticlesByMembre(@PathVariable("codeMembre") String codeMembre)
			throws ResourceNotFoundException {
		List<ArticleStocke> ListArticles = new ArrayList<>();
		List<EuArticleStockes> Articles = rechercheService.FindAllArticlesEnStock(codeMembre);
		System.out.println("Articles.getsize = " + Articles.size());
		if (Objects.nonNull(Articles)) {

			Articles.stream().forEach((article) -> {
				ListArticles.add(dtoUtils.mapArticle(article));

			});
			return ListArticles;
		} else {
			throw new ResourceNotFoundException("Cet acteur n'a pas d'articles en stock");
		}
	}

	@RequestMapping(value = "cour/{type}", method = RequestMethod.GET)
	public List<Cour> rechercheListCours(@PathVariable("type") String type) throws ResourceNotFoundException {
		List<Cour> ListCour = new ArrayList<>();
		List<EuCours> Cours = rechercheService.findAllCours();
		if (Objects.nonNull(Cours)) {

			Cours.stream().forEach((cour) -> {
				ListCour.add(dtoUtils.mapCours(cour));
			});

			return ListCour;
		} else {
			throw new ResourceNotFoundException("Aucun cours de devise n'est enregistré");
		}
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDTO handleResourceNotFoundError(ResourceNotFoundException ex) {
		System.out.println("Handling Not found Exception ...");
		List<String> messages = Lists.newArrayList();
		messages.add(ex.getMessage());
		return new ErrorDTO(ex.getCode(), messages);
	}
}
