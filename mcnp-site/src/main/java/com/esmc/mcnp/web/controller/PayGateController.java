package com.esmc.mcnp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esmc.mcnp.web.controller.base.BaseController;

@Controller
@RequestMapping(value = "/paygate")
public class PayGateController extends BaseController {

	@GetMapping(value = "achatban")
	public String acheterBan() {
		return "public/achatBan";
	}
}
