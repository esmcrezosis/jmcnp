
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.controller.security;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.domain.dto.security.Login;
import com.esmc.mcnp.exception.ResourceNotFoundException;
import com.esmc.mcnp.infrastructure.services.security.EuUtilisateurService;
import com.esmc.mcnp.util.ErrorDTO;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 *
 * @author Mawuli
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

	private @Inject EuUtilisateurService userService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Login loggedIn(@RequestBody Login login) {
		Preconditions.checkNotNull(login);
		Login r_login = userService.authenticateUser(login);
		if (Objects.nonNull(r_login)) {
			return r_login;
		} else {
			throw new ResourceNotFoundException(login.getLogin(), "Cet utilisateur n'existe pas");
		}
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDTO handleResourceNotFoundError(ResourceNotFoundException ex) {
		List<String> messages = Lists.newArrayList();
		messages.add(ex.getMessage());
		return new ErrorDTO(ex.getCode(), messages);
	}
}
