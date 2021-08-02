package com.esmc.mcnp.infrastructure.services.sys;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.esmc.mcnp.commons.util.DateUtils;
import com.esmc.mcnp.dao.repository.sys.SysOperationLogRepository;
import com.esmc.mcnp.domain.entity.sys.QSysOperationLog;
import com.esmc.mcnp.domain.entity.sys.SysOperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

@Service
public class SysOperationLogServiceImpl implements ISysOperationLogService {

	@Resource
	private SysOperationLogRepository sysOperationLogRepository;

	@Override
	public SysOperationLog save(SysOperationLog sysOperationLog) {
		return sysOperationLogRepository.save(sysOperationLog);
	}

	@Override
	public Page<SysOperationLog> findPage(Map<String, Object> condition, Pageable pageable) {
		if (null != condition && !condition.isEmpty()) {
			BooleanBuilder booleanBuilder = new BooleanBuilder();
			QSysOperationLog sysOperationLog = QSysOperationLog.sysOperationLog;
			if (condition.containsKey("title")) {
				String title = condition.get("title").toString();
				booleanBuilder.and(sysOperationLog.title.likeIgnoreCase("%" + title + "%"));
			}
			if (condition.containsKey("createdDateStart")) {
				String createdDateStart = condition.get("createdDateStart").toString();
				Date startDay = DateUtils.getStartDay(createdDateStart);
				booleanBuilder.and(sysOperationLog.createdDate
						.goe(startDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
			}
			if (condition.containsKey("createdDateEnd")) {
				String createdDateEnd = condition.get("createdDateEnd").toString();
				Date endDay = DateUtils.getEndDay(createdDateEnd);
				booleanBuilder.and(sysOperationLog.createdDate
						.loe(endDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
			}
			return sysOperationLogRepository.findAll(booleanBuilder, pageable);
		}
		return sysOperationLogRepository.findAll(pageable);
	}

	@Override
	public void deleteById(Long id) {
		sysOperationLogRepository.deleteById(id);
	}
}
