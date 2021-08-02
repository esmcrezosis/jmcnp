package com.esmc.mcnp.commons.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;


/**
 * Outil de lecture de fichier de propriété
 * 
 */
public class PropertiesUtils {

	/**
	 * Lire la valeur en fonction de la clé
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String readValue(String fileName, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
			props.load(in);
			String value = props.getProperty(key);
			in.close();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lire toutes les informations des propriétés
	 * @param fileName
	 * @return
	 */
	public static Properties readProperties(String fileName) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
			props.load(in);
			in.close();
			return props;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Méthode de lecture du chemin, que le caractère de fin soit "\\", "/", ""
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String readPathValue(String fileName, String key) {
		String result = readValue(fileName, key).replace("\\", "/");
		if (!result.endsWith("\\")) {
			result += "/";
		}
		return result;
	}
	
	/**
	 * Obtenez le chemin absolue du fichier en fonction du chemin relative du fichier
	 * @param relativePath chemin relative
	 * @return
	 */
	public static String getProjectPath(String relativePath) {
		try {
			String absolutePath = "";
			if (relativePath == null || "".equals(relativePath.trim())) {
				absolutePath = getRootPath();
			} else {
				if (relativePath.trim().indexOf("/") != 0) {
					relativePath = "/" + relativePath.trim();
				}
				absolutePath =  PropertiesUtils.class.getResource(relativePath).getPath();
//				absolutePath =  ProjectPathUtil.class.getClassLoader().getResource(relativePath).getPath();
//				absolutePath = ProjectPathUtil.class.getClass().getResource(relativePath).getPath();
			}
			return absolutePath;
		} catch (Exception e) {
			e.printStackTrace();
			//LOGGER.info("Erreur de chemin d'accès, le message d'erreur est:"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * Obtenez le chemin d'accès racine du projet
	 * @return
	 */
	public static String getRootPath() {
		try {
			String rootPath = getProjectPath("/");
			return rootPath;
		} catch (Exception e) {
			e.printStackTrace();
			//LOGGER.info("Obtenez l'erreur de chemin suivante, le message d'erreur est:"+e.getMessage());
		}
		return null;
	}
}
