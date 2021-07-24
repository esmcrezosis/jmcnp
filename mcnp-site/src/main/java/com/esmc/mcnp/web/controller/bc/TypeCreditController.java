/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.controller.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.config.annotation.WebController;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.services.common.EuTypeCreditService;

/**
 *
 * @author Mawuli
 */
@WebController("/typeCredit")
public class TypeCreditController {

    private @Autowired
    EuTypeCreditService typeCreditService;

    @RequestMapping(value = "list")
    public @ResponseBody
    List<EuTypeCredit> listTypeCredit() {
        return typeCreditService.list();
    }
}
