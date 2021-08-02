package com.esmc.mcnp.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.esmc.mcnp.commons.util.text.StrFormatter;

/**
 * Outils de chaines de caractères
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
	/** Chaîne vide */
	private static final String NULLSTR = "";

	/** Caractère Souligner */
	private static final char SEPARATOR = '_';

	/**
	 * Get parameter is not null
	 *
	 * @param value
	 * defaultValue the value to be judged
	 * @return value return value
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * * Determine whether a Collection is empty, including List, Set, Queue
	 *
	 * @param coll
	 * Collection to be judged
	 * @return true: empty false: not empty
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return isNull(coll) || coll.isEmpty();
	}

	/**
	 * * Determine whether a Collection is not empty, including List, Set, Queue
	 *
	 * @param coll
	 * Collection to be judged
	 * @return true: not empty false: empty
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * * Determine whether an object array is empty
	 *
	 * @param objects
	 * Array of objects to be judged
	 ** @return true: empty false: not empty
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * Determine whether an object array is not empty
	 *
	 * @param objects
	 * Array of objects to be judged
	 * @return true: not empty false: empty
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * * Determine whether a Map is empty
	 *
	 * @param map
	 * Map to be judged
	 * @return true: empty false: not empty
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	/**
	 * * Determine whether a Map is empty
	 *
	 * @param map
	 * Map to be judged
	 * @return true: not empty false: empty
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * * Determine whether a string is empty
	 *
	 * @param str
	 * String
	 * @return true: empty false: not empty
	 */
	public static boolean isEmpty(String str) {
		return isNull(str) || NULLSTR.equals(str.trim());
	}

	/**
	 * * Determine whether a string is non-empty
	 *
	 * @param str
	 * String
	 * @return true: non-empty string false: empty string
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * * Determine whether an object is empty
	 *
	 * @param object
	 * Object
	 * @return true: empty false: not empty
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * * Determine whether an object is not empty
	 *
	 * @param object
	 * Object
	 * @return true: not empty false: empty
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * * Determine whether an object is an array type (array of Java basic types)
	 *
	 * @param object
	 * Object
	 * @return true: it is an array false: it is not an array
	 */
	public static boolean isArray(Object object) {
		return isNotNull(object) && object.getClass().isArray();
	}

	/**
	 * Eliminer les espaces
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * Intercept string
	 *
	 * @param str
	 * String
	 * @param start
	 *            Start
	 * @return result
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
	 * Intercept string
	 *
	 * @param str
	 * String
	 * @param start
	 *            Start
	 * @param end
	 *            End
	 * @return result
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
	 * Formatted text, {} means placeholder<br>
	 * This method simply replaces the placeholder {} with parameters in order<br>
	 * If you want to output {}, use \\ to escape {, if you want to output the \ before {}, use double escape \\\\<br>
	 * Example: <br>
	 * Usually use: format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * Escape {}: format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * Escape\: format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 *
	 * @param template
	 * Text template, the replaced part is represented by {}
	 * @param params
	 * Parameter value
	 * @return formatted text
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
		// Si le caractère précédent est en majuscule
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
	 * Does it contain a string
	 *
	 * @param str
	 * Verification string
	 * @param strs
	 * String group
	 * @return contains return true
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
	 * Convert the string named in underscore capital to camel case. If the string named in underscore capitalization before conversion is empty, an empty string is returned.
	 * For example: HELLO_WORLD->HelloWorld
	 *
	 * @param name
	 * Character string named in underscore capitalization before conversion
	 * @return converted camel case named string
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
			// Ignorer le soulignement ou le double soulignement au début et à la fin de la chaîne d'origine
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
	 * Nomenclature CamelCase Par exemple: username-> userName
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
            sb.append(Character.toLowerCase(c));  //L'unité est tout en minuscules
        }
        return sb.toString();
    }

	/**
	 * String bracket label
	 * Example: [Information]
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
	 * Check whether the array contains the specified value
	 * @param arr array
	 * @param targetValue value
	 * @return does it contain
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
}