package com.esmc.mcnp.web.controller;

import java.security.Principal;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esmc.mcnp.commons.dynamicquery.DynamicQuery;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.web.controller.base.BaseController;

@Controller
public class HomeController extends BaseController {

	private EuTegcService tegcService;
	private DynamicQuery dynQuery;	

	@Autowired
	public HomeController(EuTegcService tegcService, DynamicQuery dynQuery) {
		this.tegcService = tegcService;
		this.dynQuery = dynQuery;
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Principal principal) {
		return "home/home";
	}

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@Transactional
	@ModelAttribute("statutMembre")
	public String getMembreStatut() {
		String codeMembre = getCodeMembreUser();
		if (StringUtils.isNotBlank(codeMembre) && codeMembre.endsWith("M")) {
			String sql = "select code_statut from eu_membre_morale where code_membre_morale like ?";
			Map<String, String> result = dynQuery.nativeQueryMap(sql, codeMembre);
			if (result.values().size() > 0) {
				String code = result.get("code_statut");
				System.out.println("Statut : " + code);
				return code;
			}
		}
		return null;
	}

	@ModelAttribute("nomtegc")
	public String retrouverRaisonTegc() {
		String nomTegc = "";
		String codeTegc;
		EuUtilisateur utilisateur = getCurrentUser();
		if (utilisateur != null) {
			codeTegc = utilisateur.getCodeTegc();
			if (StringUtils.isNotBlank(codeTegc)) {
				EuTegc euTegc = tegcService.getById(codeTegc);
				if (euTegc != null && StringUtils.isNotBlank(euTegc.getNomTegc())
						&& !euTegc.getTypeTegc().equals("PRESTATAIRE")) {
					nomTegc = euTegc.getNomTegc().toUpperCase();
				}
			}
		}
		return nomTegc;
	}
}
