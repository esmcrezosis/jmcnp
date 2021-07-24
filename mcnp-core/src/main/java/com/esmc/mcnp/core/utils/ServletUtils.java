package com.esmc.mcnp.core.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Outils client
 */
public class ServletUtils {
	/**
	 * Obtenir le paramètre String
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * Obtenir le paramètre String
	 */
	public static String getParameter(String name, String defaultValue) {
		return Convert.toStr(getRequest().getParameter(name), defaultValue);
	}

	/**
	 * Obtenir des paramètres entiers
	 */
	public static Integer getParameterToInt(String name) {
		return Convert.toInt(getRequest().getParameter(name));
	}

	/**
	 * Obtenir des paramètres entiers
	 */
	public static Integer getParameterToInt(String name, Integer defaultValue) {
		return Convert.toInt(getRequest().getParameter(name), defaultValue);
	}

	/**
	 * Obtenir l'objet Request
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * Obtenir l'objet Response
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * Obtenir l'objet Session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * Rendre la chaîne au client
	 *
	 * Réponse @param Rendu des objets
	 * 
	 * @param chaîne Chaîne à rendre
	 * @return null
	 */
	public static String renderString(HttpServletResponse response, String string) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Est-ce une requête asynchrone Ajax
	 * 
	 * @param request
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		String uri = request.getRequestURI();
		if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
			return true;
		}

		String ajax = request.getParameter("__ajax");
		if (StringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
			return true;
		}
		return false;
	}

	/**
	 * Obtenez le chemin d'accès complet de la requête, y compris: nom de domaine,
	 * port, chemin d'accès au contexte
	 * 
	 * @return
	 */
	public static String getServletUrl() {
		HttpServletRequest request = ServletUtils.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextPath = request.getServletContext().getContextPath();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
	}

	/**
	 * Obtenir le chemin d'accès relatif au contexte
	 * 
	 * @return
	 */
	public static String getContextPath() {
		HttpServletRequest request = ServletUtils.getRequest();
		return request.getServletContext().getContextPath();
	}

	/**
	 * Obtenir le chemin absolu du contexte relatif
	 * 
	 * @return
	 */
	public static String getRealPath() {
		HttpServletRequest request = ServletUtils.getRequest();
		return request.getServletContext().getRealPath("");
	}
}
