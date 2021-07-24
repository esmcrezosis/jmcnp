package com.esmc.mcnp.core.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Outils de jeu de caractères
 */
public class CharsetKit {
	/** ISO-8859-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";
	/** UTF-8 */
	public static final String UTF_8 = "UTF-8";
	/** GBK */
	public static final String GBK = "GBK";

	/** ISO-8859-1 */
	public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
	/** UTF-8 */
	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
	/** GBK */
	public static final Charset CHARSET_GBK = Charset.forName(GBK);

	/**
	* Convertir en objet Charset
	*
	* @param charset
	* Jeu de caractères, retourne au jeu de caractères par défaut s'il est vide
	* @return Charset
	*/
	public static Charset charset(String charset) {
		return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
	}

	/**
	* Codage du jeu de caractères de la chaîne convertie
	*
	* @param source
	* Chaîne
	* @param srcCharset
	* Jeu de caractères source, ISO-8859-1 par défaut
	* @param destCharset
	* Jeu de caractères cible, UTF-8 par défaut
	* @return Jeu de caractères converti 
	*/
	public static String convert(String source, String srcCharset, String destCharset) {
		return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
	}

	/**
	* Codage du jeu de caractères de la chaîne convertie
	*
	* @param source
	* Chaîne
	* @param srcCharset
	* Jeu de caractères source, ISO-8859-1 par défaut
	* @param destCharset
	* Jeu de caractères cible, UTF-8 par défaut
	* @return Jeu de caractères converti 
	*/
	public static String convert(String source, Charset srcCharset, Charset destCharset) {
		if (null == srcCharset) {
			srcCharset = StandardCharsets.ISO_8859_1;
		}

		if (null == destCharset) {
			srcCharset = StandardCharsets.UTF_8;
		}

		if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset)) {
			return source;
		}
		return new String(source.getBytes(srcCharset), destCharset);
	}

	/**
	 * @return Codage du jeu de caractères système
	 */
	public static String systemCharset() {
		return Charset.defaultCharset().name();
	}
}
