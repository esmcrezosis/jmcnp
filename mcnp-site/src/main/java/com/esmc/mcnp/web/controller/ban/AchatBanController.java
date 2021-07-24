package com.esmc.mcnp.web.controller.ban;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esmc.mcnp.web.controller.base.BaseController;

@Controller
@RequestMapping(value = "/public")
public class AchatBanController extends BaseController {
	
	@GetMapping(value = "achatban")
	public String acheterBan() {
		return "public/achatBan";
	}
}
