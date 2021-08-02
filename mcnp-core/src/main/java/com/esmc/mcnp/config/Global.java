package com.esmc.mcnp.config;

import com.esmc.mcnp.commons.util.PropertiesUtils;
import com.esmc.mcnp.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe de configuration globale
 */
public class Global {
	private static final Logger log = LoggerFactory.getLogger(Global.class);

	private static String NAME = "application.properties";

	/**
	 * Instance d'objet actuelle
	 */
	private static Global global = null;

	/**
	 * Enregistrer les valeurs d'attribut globales
	 */
	private static Map<String, String> map = new HashMap<String, String>();

	private Global() {
	}

	/**
	 * Méthode de fabrique statique Récupère l'instance d'objet actuelle Mode
	 * singleton sécurisé multi-thread (en utilisant le double verrouillage de
	 * synchronisation)
	 */

	public static synchronized Global getInstance() {
		if (global == null) {
			synchronized (Global.class) {
				if (global == null)
					global = new Global();
			}
		}
		return global;
	}

	/**
	 * Obtenir la configuration
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = String.valueOf(PropertiesUtils.readPathValue(NAME, key));
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * Obtenir le nom du projet
	 */
	public static String getName() {
		return StringUtils.nvl(getConfig("boot.name"), "boot");
	}

	/**
	 * Obtenir la version du projet
	 */
	public static String getVersion() {
		return StringUtils.nvl(getConfig("app.version"), "1.0");
	}

	/**
	 * Année d'acquisition des droits d'auteur
	 */
	public static String getCopyrightYear() {
		return StringUtils.nvl(getConfig("app.copyrightYear"), "2020");
	}

	/**
	 * Obtenir le commutateur d'adresse IP
	 */
	public static Boolean isAddressEnabled() {
		return Boolean.valueOf(getConfig("boot.addressEnabled"));
	}

	/**
	 * Obtenir le chemin de téléchargement du fichier
	 */
	public static String getProfile() {
		return getConfig("app.profile");
	}

	/**
	 * Obtenir le chemin de téléchargement de l'avatar
	 */
	public static String getAvatarPath() {
		return getProfile() + "/avatar";
	}

	/**
	 * Obtenir le chemin de téléchargement
	 */
	public static String getDownloadPath() {
		return getProfile() + "/download";
	}

	/**
	 * Obtenir le chemin de téléchargement
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}

	/**
	 * Obtenir l'adresse de l'image
	 */
	public static String getImageUrl() {
		return getConfig("boot.imageUrl");
	}

}
