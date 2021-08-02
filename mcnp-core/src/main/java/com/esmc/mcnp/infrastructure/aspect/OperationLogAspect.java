package com.esmc.mcnp.infrastructure.aspect;

import com.alibaba.fastjson.JSONObject;
import com.esmc.mcnp.commons.annotation.OperationLog;
import com.esmc.mcnp.commons.util.ServletUtils;
import com.esmc.mcnp.commons.util.StringUtils;
import com.esmc.mcnp.config.thread.AsyncFactory;
import com.esmc.mcnp.config.thread.AsyncManager;
import com.esmc.mcnp.domain.entity.sys.SysOperationLog;
import com.esmc.mcnp.infrastructure.services.sys.ISysOperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class OperationLogAspect {

	@Resource
	private ISysOperationLogService sysOperationLogService;

	private static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class);

	@Pointcut("@annotation(com.esmc.mcnp.commons.annotation.OperationLog)")
	public void pointCut() {

	}

	/**
	 * Exécuter après le traitement de la demande
	 *
	 * @param joinPoint point de coupe
	 */
	@AfterReturning(pointcut = "pointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * Intercepter les opérations anormales
	 *
	 * @param joinPoint point de coupe
	 * @param e         exception
	 */
	@AfterThrowing(value = "pointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	private void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		OperationLog operationLog = getOperationLog(joinPoint);
		if (operationLog == null) {
			return;
		}
		SysOperationLog sysOperationLog = new SysOperationLog();
		sysOperationLog.setTitle(operationLog.title());
		sysOperationLog.setBusinessType(operationLog.businessType().name());
		// Nom de la méthode de réglage
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		sysOperationLog.setMethodName(className + "." + methodName + "()");
		// Définir la méthode de requete
		sysOperationLog.setRequestMethod(ServletUtils.getRequest().getMethod());
		// URL de la requete
		sysOperationLog.setUrl(ServletUtils.getRequest().getRequestURI());
		// Adresse IP
		sysOperationLog.setIp(ServletUtils.getIp());
		// emplacement
		sysOperationLog.setLocation(ServletUtils.getLocationByIp());
		// Paramètre de la requete
		if (operationLog.saveRequestParams()) {
			sysOperationLog.setRequestParams(getRequestParams());
		}
		// résultat
		if (jsonResult != null) {
			sysOperationLog.setResult(StringUtils.substring(jsonResult.toString(), 0, 4000));
		}
		// Message d'erreur
		if (e != null) {
			sysOperationLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 4000));
		}
		AsyncManager.asyncManager().execute(AsyncFactory.recordOper(sysOperationLog));
	}

	private OperationLog getOperationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if (method != null) {
			return method.getAnnotation(OperationLog.class);
		}
		return null;
	}

	/**
	 * Obtenez les paramètres demandés et mettez-les dans le journal
	 */
	private String getRequestParams() {
		Map<String, String[]> parameterMap = ServletUtils.getRequest().getParameterMap();
		if (StringUtils.isNotEmpty(parameterMap)) {
			String requestParams = JSONObject.toJSONString(parameterMap);
			return StringUtils.substring(requestParams, 0, 4000);
		}
		return null;
	}

}
