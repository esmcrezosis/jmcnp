package com.esmc.mcnp.services.sys;

import com.esmc.mcnp.commons.dynamicquery.DynamicQuery;
import com.esmc.mcnp.commons.model.PageBean;
import com.esmc.mcnp.commons.model.Result;
import com.esmc.mcnp.model.sys.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    public Result list(SysLog log) {
        String nativeSql = "SELECT COUNT(*) FROM sys_log ";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<SysLog> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_log ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(log.getPageNo(),log.getPageSize());
            List<SysLog> list =  dynamicQuery.nativeQueryPagingList(SysLog.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
