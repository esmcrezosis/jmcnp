package com.esmc.mcnp.services.sys;

import com.esmc.mcnp.commons.model.Result;
import com.esmc.mcnp.model.sys.SysLog;

public interface SysLogService {

    Result list(SysLog log);
}
