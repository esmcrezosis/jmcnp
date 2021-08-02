package com.esmc.mcnp.web.controller.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.domain.entity.security.EuConfirmation;
import com.esmc.mcnp.infrastructure.services.common.EuConfirmationService;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.security.Verif;

@RestController
@RequestMapping(value = "/confirmation")
public class ConfirmationController extends BaseController {

	private EuConfirmationService confirmationService;

	@Autowired
	public ConfirmationController(EuConfirmationService confirmationService) {
		this.confirmationService = confirmationService;
	}

	@Transactional
	@PostMapping(value = "request")
	public Verif insertconfirmation(@RequestParam String codeMembre, @RequestParam String codeOperateur,
			@RequestParam String activite, @RequestParam String message) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(codeOperateur)
				&& StringUtils.isNotBlank(message)) {
			try {
				EuConfirmation confirm = new EuConfirmation();
				confirm.setCodeMembre(codeMembre);
				confirm.setCodeOperateur(codeOperateur);
				confirm.setNomOperateur(StringUtils.EMPTY);
				confirm.setDataText(message);
				confirm.setActivite(activite);
				confirm.setStatus(1);
				confirm.setTexteConfirmation(message);
				confirm.setTypeConfirmation(3);
				confirm.setDateCreation(String.valueOf(Instant.now().toEpochMilli()));
				confirm.setPage((new URL(activite).getPath()));
				confirm = confirmationService.add(confirm);
				return new Verif(confirm.getIdConfirmation(), confirm.getStatus());
			} catch (Exception e) {
				getLog().error("Erreur de creation de la confirmation", e);
				return null;
			}
		}
		return null;
	}

	@GetMapping(value = "check")
	public Verif checkConfirmation(@RequestParam Long id) {
		if (id != null) {
			EuConfirmation conf = confirmationService.findById(id);
			if (Objects.nonNull(conf)) {
				return new Verif(conf.getIdConfirmation(), conf.getStatus());
			}
		}
		return null;
	}

	@PostMapping(value = "secureconfirmationmessage")
	public String secureconfirmationmessage(@RequestParam String codeMembre, @RequestParam String nomOperateur,
			@RequestParam String codeOperateur, @RequestParam String message, @RequestParam Long id) {
		if (StringUtils.isNotBlank(codeMembre) && id != null && StringUtils.isNotBlank(codeOperateur)
				&& StringUtils.isNotBlank(message)) {
			String url;
			try {
				url = "http://172.16.20.51/notif/envoipush.php?message_confirmation="
						+ URLEncoder.encode(message, "UTF-8") + "&id_confirmation="
						+ URLEncoder.encode(String.valueOf(id), "UTF-8") + "&code_membre="
						+ URLEncoder.encode(codeMembre, "UTF-8") + "&nom_operateur="
						+ URLEncoder.encode(codeOperateur, "UTF-8") + "&message="
						+ URLEncoder.encode(codeOperateur, "UTF-8");
				System.out.println("URL = " + url);
				return getUrlContent(url);
			} catch (Exception e) {
				getLog().error("Erreur d'envoie de la confirmation", e);
				return null;
			}
		} else {
			getLog().error("Erreur d'envoie de la confirmation : Manque d'informations fournies");
			return null;
		}
	}

	public String getUrlContent(String sUrl) throws Exception {
		URL url = new URL(sUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.connect();
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String content = "", line;
		while ((line = rd.readLine()) != null) {
			content += line + "\n";
		}
		return content;
	}
}
