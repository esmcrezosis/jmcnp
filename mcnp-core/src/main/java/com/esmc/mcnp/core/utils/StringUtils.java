package com.esmc.mcnp.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Outils de chaines de caractères
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/** Chaîne vide */
	private static final String NULLSTR = "";

	/** Souligner */
	private static final char SEPARATOR = '_';

	/**
	 * Le paramètre Get n'est pas nul
	 *
	 * @param value defaultValue valeur à juger
	 * @return value valeur de retour
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * * Déterminer si une collection est vide, y compris la liste, l'ensemble, la
	 * file d'attente
	 *
	 * @param coll Collection à juger
	 * @return true: vide false: pas vide
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return isNull(coll) || coll.isEmpty();
	}

	/**
	 * * Déterminez si une collection n'est pas vide, y compris la liste,
	 * l'ensemble, la file d'attente
	 *
	 * @param coll Collection à juger
	 * @return true: pas vide false: vide
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * * Détermine si un tableau d'objets est vide
	 *
	 * @param objects Le tableau des objets à juger
	 ** @return true: vide false: pas vide
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * Détermine si un tableau d'objets n'est pas vide
	 *
	 * @param objects Le tableau des objets à juger
	 * @return true: pas vide false: vide
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * * Déterminer si une carte est vide
	 *
	 * @param map Carte à juger
	 * @return true: vide false: pas vide
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	/**
	 * * Déterminer si une carte est vide
	 *
	 * @param map Carte à juger
	 * @return true: pas vide false: vide
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * * Détermine si une chaîne est vide
	 *
	 * @param str Chaîne
	 * @return true: vide false: pas vide
	 */
	public static boolean isEmpty(String str) {
		return isNull(str) || NULLSTR.equals(str.trim());
	}

	/**
	 * * Détermine si une chaîne n'est pas vide
	 *
	 * @param str Chaîne
	 * @return true: chaîne non vide false: chaîne vide
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * * Détermine si un objet est vide
	 *
	 * Objet @param Object
	 * 
	 * @return true: vide false: pas vide
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * * Détermine si un objet n'est pas vide
	 *
	 * Objet @param Object
	 * 
	 * @return true: pas vide false: vide
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * * Détermine si un objet est un type de tableau (tableau de types de base
	 * Java)
	 *
	 * Objet objet @param
	 * 
	 * @return true: c'est un tableau false: ce n'est pas un tableau
	 */
	public static boolean isArray(Object object) {
		return isNotNull(object) && object.getClass().isArray();
	}

	/**
	 * Aller aux espaces
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * Chaîne d'interception
	 *
	 * @param str   chaîne
	 * @param start start
	 * @return résultat
	 */
	public static String substring(final String str, int start) {
		if (str == null) {
			return NULLSTR;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return NULLSTR;
		}

		return str.substring(start);
	}

	/**
	 * Chaîne d'interception
	 *
	 * @param str   chaîne
	 * @param start start
	 * @param end
	 * @return résultat
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return NULLSTR;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return NULLSTR;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * Texte formaté, {} signifie espace réservé <br>
	 * Cette méthode remplace simplement l'espace réservé {} par des paramètres dans
	 * l'ordre <br>
	 * Si vous voulez afficher {}, utilisez \\ pour échapper {, si vous voulez
	 * afficher le \ before {}, utilisez double échappement \\\\ <br>
	 * Exemple: <br>
	 * Habituellement, utilisez: format ("ceci est {} pour {}", "a", "b") -> c'est a
	 * pour b <br>
	 * Echap {}: format ("ceci est \\ {} pour {}", "a", "b") -> c'est \ {} pour un
	 * <br>
	 * Echap \: format ("ceci est \\\\ {} pour {}", "a", "b") -> c'est \ a pour b
	 * <br>
	 *
	 * @param template text template, la partie remplacée est représentée par {}
	 *                 Valeur du paramètre @param params
	 * @return Texte formaté
	 */
	public static String format(String template, Object... params) {
		if (isEmpty(params) || isEmpty(template)) {
			return template;
		}
		return StrFormatter.format(template, params);
	}

	/**
	 * Souligner la dénomination de la bosse
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// Si le caractère principal est en majuscule
		boolean preCharIsUpperCase = true;
		// Si le caractère actuel est en majuscule
		boolean curreCharIsUpperCase = true;
		// Si le caractère suivant est en majuscule
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}

			curreCharIsUpperCase = Character.isUpperCase(c);

			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}

			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * Contient-il une chaîne
	 *
	 * @param str  chaîne de vérification
	 * @param strs groupe de chaînes
	 * @return contient return true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Convertissez la chaîne nommée en majuscule de soulignement en cas de chameau.
	 * Si la chaîne nommée en majuscules de soulignement avant la conversion est
	 * vide, une chaîne vide est renvoyée. Par exemple: HELLO_WORLD-> HelloWorld
	 *
	 * @param name La chaîne nommée dans le trait de soulignement avant la
	 *             conversion
	 * @return converti le cas de chameau nommé string
	 */
	public static String convertToCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		// Vérification rapide
		if (name == null || name.isEmpty()) {
			// Pas besoin de changer
			return "";
		} else if (!name.contains("_")) {
			// Pas de trait de soulignement, ne mettez en majuscule que la première lettre
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// Diviser la chaîne d'origine avec des traits de soulignement
		String[] camels = name.split("_");
		for (String camel : camels) {
			// Ignorer le soulignement ou le double soulignement au début et à la fin de la
			// chaîne d'origine
			if (camel.isEmpty()) {
				continue;
			}
			// Mettre la première lettre en majuscule
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * Nomenclature de cas de chameau telle que：user_name->userName
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Bosse pour souligner
	 * 
	 * @param param
	 * @return
	 */
	public static String convertToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_");
			}
			sb.append(Character.toLowerCase(c)); // L'unité est tout en minuscules
		}
		return sb.toString();
	}

	/**
	 * Étiquette entre crochets dans la chaîne Exemple: [Information]
	 * 
	 * @param msg
	 * @return
	 */
	public static String getBlock(Object msg) {
		if (msg == null) {
			msg = "";
		}
		return "[" + msg.toString() + "]";
	}

	/**
	 * Vérifiez si le tableau contient la valeur spécifiée
	 *
	 * @param arr         tableau
	 * @param targetValue valeur
	 * @return contient-il
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
}