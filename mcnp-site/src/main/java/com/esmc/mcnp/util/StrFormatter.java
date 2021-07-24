package com.esmc.mcnp.util;

import com.esmc.mcnp.core.utils.StringUtils;

/**
 * Formatage de chaîne
 */
public class StrFormatter {
	public static final String EMPTY_JSON = "{}";
	public static final char C_BACKSLASH = '\\';
	public static final char C_DELIM_START = '{';
	public static final char C_DELIM_END = '}';

	/**
	 * Format string<br>
	 * This method simply replaces the placeholder {} with parameters in order<br>
	 * If you want to output {}, use \\ to escape {, if you want to output the \ before {}, use double escape \\\\<br>
	 * Example: <br>
	 * Usually use: format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * Escape {}: format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * Escape\: format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 *
	 * @param strPattern
	 * String template
	 * @param argArray
	 *            parameter list
	 * @return result
	 */
	public static String format(final String strPattern, final Object... argArray) {
		if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray)) {
			return strPattern;
		}
		final int strPatternLength = strPattern.length();

		// Initialisez la longueur définie pour de meilleures performances
		StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

		int handledPosition = 0;
		int delimIndex;// Emplacement de l'espace réservé
		for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
			delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
			if (delimIndex == -1) {
				if (handledPosition == 0) {
					return strPattern;
				} else { // Le reste du modèle de chaîne ne contient plus d'espaces réservés et le résultat sera renvoyé après l'ajout du reste
					sbuf.append(strPattern, handledPosition, strPatternLength);
					return sbuf.toString();
				}
			} else {
				if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {
					if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH) {
						// Il y a un caractère d'échappement avant le caractère d'échappement, l'espace réservé est toujours valide
						sbuf.append(strPattern, handledPosition, delimIndex - 1);
						sbuf.append(Convert.utf8Str(argArray[argIndex]));
						handledPosition = delimIndex + 2;
					} else {
						// L'espace réservé est échappé
						argIndex--;
						sbuf.append(strPattern, handledPosition, delimIndex - 1);
						sbuf.append(C_DELIM_START);
						handledPosition = delimIndex + 1;
					}
				} else {
					// Espace réservé normal
					sbuf.append(strPattern, handledPosition, delimIndex);
					sbuf.append(Convert.utf8Str(argArray[argIndex]));
					handledPosition = delimIndex + 2;
				}
			}
		}
		// Ajouter tous les caractères après le dernier espace réservé
		sbuf.append(strPattern, handledPosition, strPattern.length());

		return sbuf.toString();
	}
}
