package com.esmc.mcnp.web.controller.security;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.esmc.mcnp.UserSecurity;

@Controller
public class AuthenticationController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		System.out.println("In Auth Controller");
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurity) {
			return new ModelAndView(new RedirectView("/home", true, false));
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return new ModelAndView(new RedirectView("/login", true, false));
	}
}
