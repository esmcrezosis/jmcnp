package com.esmc.mcnp.web.controller.sys;

import com.esmc.mcnp.commons.model.Result;
import com.esmc.mcnp.model.sys.SysLog;
import com.esmc.mcnp.services.sys.SysLogService;
import com.esmc.mcnp.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gestion des journaux
 */
@Api(tags ="Gestion des journaux")
@RestController
@RequestMapping("/sys/log")
public class LogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * Liste des journaux
     */
    @PostMapping("/list")
    public Result list(SysLog log){
        return sysLogService.list(log);
    }

}
