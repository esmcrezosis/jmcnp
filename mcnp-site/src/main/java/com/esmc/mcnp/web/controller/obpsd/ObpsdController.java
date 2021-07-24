package com.esmc.mcnp.web.controller.obpsd;

import com.esmc.mcnp.web.controller.base.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/obpsd")
public class ObpsdController extends BaseController {
    
    @GetMapping
    public String index(Model model){
        return "obpsd/index";
    }
}