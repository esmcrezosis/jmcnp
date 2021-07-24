package com.esmc.mcnp.core.utils;

/**
 * Formatage de chaîne
 */
public class StrFormatter {
	public static final String EMPTY_JSON = "{}";
	public static final char C_BACKSLASH = '\\';
	public static final char C_DELIM_START = '{';
	public static final char C_DELIM_END = '}';

	/**
	* Chaîne de format <br>
	* Cette méthode remplace simplement l'espace réservé {} par des paramètres dans l'ordre <br>
	* Si vous voulez afficher {}, utilisez \\ pour échapper {, si vous voulez afficher le \ before {}, utilisez double échappement \\\\ <br>
	* Exemple: <br>
	* Habituellement, utilisez: format ("ceci est {} pour {}", "a", "b") -> c'est a pour b <br>
	* Echap {}: format ("ceci est \\ {} pour {}", "a", "b") -> c'est \ {} pour un <br>
	* Echap \: format ("ceci est \\\\ {} pour {}", "a", "b") -> c'est \ a pour b <br>
	*
	* @param strPattern
	* Modèle de chaîne
	* @param argArray
	* liste de paramètres
	*@return résultat 
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
