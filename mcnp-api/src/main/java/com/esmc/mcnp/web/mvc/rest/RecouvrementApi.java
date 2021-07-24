package com.esmc.mcnp.web.mvc.rest;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.services.pc.EuRecouvrementMcnpService;
import com.esmc.mcnp.web.mvc.dto.RecouvrementDto;
import com.esmc.mcnp.web.mvc.dto.Result;

@RestController
@RequestMapping("/recouvrer")
public class RecouvrementApi {
	private EuRecouvrementMcnpService recouvMcnpService;

	@Inject
	public RecouvrementApi(EuRecouvrementMcnpService recouvMcnpService) {
		this.recouvMcnpService = recouvMcnpService;
	}

	@RequestMapping(value = "mcnp", method = RequestMethod.POST)
	public ResponseEntity<Result> recouvrerMcnp(@RequestBody RecouvrementDto recouvrement) {
		if (StringUtils.isNotBlank(recouvrement.getCodeMembre())
				&& StringUtils.isNotBlank(recouvrement.getNewCodeMembre())
				&& StringUtils.isNotBlank(recouvrement.getTypeRessource())) {
			try {
				recouvMcnpService.recouvrerMcnp(recouvrement.getCodeMembre(), recouvrement.getNewCodeMembre(),
						recouvrement.getTypeRessource());
				return new ResponseEntity<>(new Result(0, "L'opération a ete bien effectuée"), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new Result(1, "Echec de l'opération : " + e.getMessage()), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Result(1, "Les infos à fournir ne sont pas complètes!!!"), HttpStatus.OK);
	}
}
