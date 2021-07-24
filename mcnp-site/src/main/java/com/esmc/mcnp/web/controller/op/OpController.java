package com.esmc.mcnp.web.controller.op;

import com.esmc.mcnp.web.controller.base.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/op")
public class OpController extends BaseController {
    
    @GetMapping
    public String index(Model model){
        return "op/index";
    }
}