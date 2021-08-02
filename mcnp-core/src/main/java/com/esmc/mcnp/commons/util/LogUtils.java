package com.esmc.mcnp.commons.util;

import com.esmc.mcnp.commons.json.JSON;
import com.esmc.mcnp.core.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Traiter et enregistrer les fichiers journaux
 * 
 * 
 */
public class LogUtils {
	public static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");
	public static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");

	/**
	 * Enregistrer le journal d'accès [nom d'utilisateur] [jsessionid] [ip]
	 * [accepter] [UserAgent] [url] [params] [Referer]
	 *
	 * @param request
	 * @throws Exception
	 */
	public static void logAccess(HttpServletRequest request) throws Exception {
		String username = getUsername();
		String jsessionId = request.getRequestedSessionId();
		String ip = IpUtil.getIpAddr(request);
		String accept = request.getHeader("accept");
		String userAgent = request.getHeader("User-Agent");
		String url = request.getRequestURI();
		String params = getParams(request);

		StringBuilder s = new StringBuilder();
		s.append(getBlock(username));
		s.append(getBlock(jsessionId));
		s.append(getBlock(ip));
		s.append(getBlock(accept));
		s.append(getBlock(userAgent));
		s.append(getBlock(url));
		s.append(getBlock(params));
		s.append(getBlock(request.getHeader("Referer")));
		getAccessLog().info(s.toString());
	}

	/**
	 * Enregistrer les erreurs anormales Format [exception]
	 *
	 * @param message
	 * @param e
	 */
	public static void logError(String message, Throwable e) {
		String username = getUsername();
		StringBuilder s = new StringBuilder();
		s.append(getBlock("exception"));
		s.append(getBlock(username));
		s.append(getBlock(message));
		ERROR_LOG.error(s.toString(), e);
	}

	/**
	 * Enregistrer les erreurs de page Enregistrement du journal des erreurs [page /
	 * eception] [nom d'utilisateur] [statusCode] [errorMessage] [servletName] [uri]
	 * [exceptionName] [ip] [exception]
	 *
	 * @param request
	 */
	public static void logPageError(HttpServletRequest request) {
		String username = getUsername();

		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String message = (String) request.getAttribute("javax.servlet.error.message");
		String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
		Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");

		if (statusCode == null) {
			statusCode = 0;
		}

		StringBuilder s = new StringBuilder();
		s.append(getBlock(t == null ? "page" : "exception"));
		s.append(getBlock(username));
		s.append(getBlock(statusCode));
		s.append(getBlock(message));
		s.append(getBlock(IpUtil.getIpAddr(request)));

		s.append(getBlock(uri));
		s.append(getBlock(request.getHeader("Referer")));
		StringWriter sw = new StringWriter();

		while (t != null) {
			t.printStackTrace(new PrintWriter(sw));
			t = t.getCause();
		}
		s.append(getBlock(sw.toString()));
		getErrorLog().error(s.toString());

	}

	public static String getBlock(Object msg) {
		if (msg == null) {
			msg = "";
		}
		return "[" + msg.toString() + "]";
	}

	protected static String getParams(HttpServletRequest request) throws Exception {
		Map<String, String[]> params = request.getParameterMap();
		return JSON.marshal(params);
	}

	protected static String getUsername() {
		return SecurityUtils.getPrincipal().getUsername();
	}

	public static Logger getAccessLog() {
		return ACCESS_LOG;
	}

	public static Logger getErrorLog() {
		return ERROR_LOG;
	}
}
