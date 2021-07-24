package com.esmc.mcnp.web.controller.obps;

import com.esmc.mcnp.web.controller.base.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/obps")
public class ObpsController extends BaseController{
    
    @GetMapping
    public String index(){
        return "obps/index";
    }
}