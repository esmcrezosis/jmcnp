package com.esmc.mcnp.web.mvc.rest;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.services.acteurs.EuBanqueService;
import com.esmc.mcnp.services.obpsd.EuRelevebancairedetailService;
import com.esmc.mcnp.web.mvc.dto.Banque;
import com.esmc.mcnp.web.mvc.dto.Releve;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

@RestController
@RequestMapping("/releve")
public class RelevebancaireController extends BaseRestController {
	private EuRelevebancairedetailService relbancdetService;
	private EuBanqueService banqueService;

	@Autowired
	public RelevebancaireController(EuRelevebancairedetailService relbancdetService, EuBanqueService banqueService) {
		this.relbancdetService = relbancdetService;
		this.banqueService = banqueService;
	}

	@RequestMapping(value = "/banque", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Banque>> loadBanque() {
		return new ResponseEntity<>(Banque.toListBanque(banqueService.list()), HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Result> insertReleveBancaire(@RequestBody Releve releve) {
		if (Objects.nonNull(releve)) {
			if (StringUtils.isNotBlank(releve.getNumero()) && StringUtils.isNotBlank(releve.getLibelle())
					&& releve.getMontant() > 0) {
				if (StringUtils.isBlank(releve.getCodeBanque())) {
					releve.setCodeBanque("CCP");
				}
				String reponse = relbancdetService.insertReleveBancairedetail(releve.getCodeBanque(), releve.getDate(),
						releve.getNumero(), releve.getLibelle(), releve.getMontant(),
						Objects.requireNonNull(getCurrentUser()).getIdUtilisateur());
				if (reponse.equals("OK")) {
					return new ResponseEntity<>(new Result(1, "L'opération a été effectuée avec succès"),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new Result(0, reponse), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(new Result(0, "Echec de l'opération : Manque d'informations"),
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(new Result(0, "Echec de l'opération : Les valeurs fournies sont toutes nulles"),
					HttpStatus.BAD_REQUEST);
		}
	}

}
