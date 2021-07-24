/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.controller.base;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.esmc.mcnp.util.ErrorDTO;
import com.google.common.collect.Lists;

/**
 *
 * @author Administrateur
 */
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleResourceNotFoundError(HttpMessageNotReadableException ex) {
        List<String> messages = Lists.newArrayList();
        messages.add(ex.getMessage());
        return new ErrorDTO("400", messages);
    }
}
