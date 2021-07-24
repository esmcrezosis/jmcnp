package com.esmc.mcnp.web.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esmc.mcnp.web.dto.util.Result;

@Controller
public class McnpErrorController extends AbstractErrorController {
	
	private static final String PATH = "/error";

	@Value("${debug}")
	private boolean debug;


	public McnpErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@RequestMapping(value = PATH)
	Result error(HttpServletRequest request, HttpServletResponse response) {
        String message = (String) this.getErrorAttributes(request, debug).get("message");
        System.out.println(message);
		return new Result(response.getStatus(), message);
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	
}
