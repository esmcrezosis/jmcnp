package com.esmc.mcnp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.esmc.mcnp.core.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author doublelifeke
 * Email: hautxxxyzjk@163.com
 * DateTime: 2020/11/7 23:32
 * Description:
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
	 * Obtenir la requête
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * Obtenir la reponse
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * Obtenir la session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * Render the string to the client
	 *
	 * @param response
	 * Render objects
	 * @param string
	 * String to be rendered
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
	 * Is it an Ajax asynchronous request
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
	 * Get the complete request path, including: domain name, port, context access path
	 * @return
	 */
	public static String getServletUrl() {
		HttpServletRequest request = ServletUtils.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextPath = request.getServletContext().getContextPath();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
	}
	
	/**
	 * Get relative context access path
	 * @return
	 */
	public static String getContextPath() {
		HttpServletRequest request = ServletUtils.getRequest();
		return request.getServletContext().getContextPath();
	}
	
	/**
	 * Get relative context absolute path
	 * @return
	 */
	public static String getRealPath() {
		HttpServletRequest request = ServletUtils.getRequest();
		return request.getServletContext().getRealPath("");
	}


    /**
    * Obtenez l'adresse IP du client (peut pénétrer le proxy)
    *
    * @return Récupère l'adresse IP du client
    */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static boolean isInternalIp() {
        return "127.0.0.1".equals(getIp());
    }

    /**
    * Obtenez l'adresse du client
    *
    * @return Obtenir l'adresse du client
    */
    public static String getLocation() {
        if (isInternalIp()) {
            return "IP intranet";
        }
        String ip = getIp();
        // Emplacement de la requête d'adresse IP
        String url = "http://freeapi.ipip.net/" + ip;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new URL(url).openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while ((cp = bufferedReader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            JSONArray json = JSONArray.parseArray(stringBuilder.toString());
            return (String) json.get(0) + json.get(1) + json.get(2) + json.get(3) + json.get(4);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getLocationByIp() {
        if (isInternalIp()) {
            return "IP intranet";
        }
        // Emplacement de la requête d'adresse IP
        String url = "http://whois.pconline.com.cn/ipJson.jsp";
        String location = "inconnue";
        String ip = getIp();
        String rspStr = HttpUtils.sendGet(url, "ip=" + ip + "&json=true", CharsetKit.GBK);
        if (StringUtils.isEmpty(rspStr)) {
            return location;
        }
        JSONObject obj = JSONObject.parseObject(rspStr);
        String region = obj.getString("pro");
        String city = obj.getString("city");
        return String.format("%s %s", region, city);
    }

}
