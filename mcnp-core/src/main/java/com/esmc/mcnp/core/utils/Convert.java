package com.esmc.mcnp.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Set;

/**
 * Convertisseur de type
 */
public class Convert {
	
	/**
	* Convertir en chaîne <br>
	* Si la valeur donnée est nulle ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	* @return résultat 
	*/
	public static String toStr(Object value, String defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		if (value instanceof String) {
			return (String) value;
		}
		return value.toString();
	}

	/**
	* Convertir en chaîne <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, renvoie la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @return résultat 
	*/
	public static String toStr(Object value) {
		return toStr(value, null);
	}

	/**
	* Convertir en caractère <br>
	* Si la valeur donnée est nulle ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	* @return résultat 
	*/
	public static Character toChar(Object value, Character defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		if (value instanceof Character) {
			return (Character) value;
		}

		final String valueStr = toStr(value, null);
		return StringUtils.isEmpty(valueStr) ? defaultValue : valueStr.charAt(0);
	}

	/**
	* Convertir en caractère <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, renvoie la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @return résultat 
	*/
	public static Character toChar(Object value) {
		return toChar(value, null);
	}

	/**
	* Convertir en octet <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, revenez à la valeur par défaut <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	* @return  résultat 
	*/
	public static Byte toByte(Object value, Byte defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Byte) {
			return (Byte) value;
		}
		if (value instanceof Number) {
			return ((Number) value).byteValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Byte.parseByte(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en octet <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, renvoie la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @return résultat 
	*/
	public static Byte toByte(Object value) {
		return toByte(value, null);
	}

	/**
	* Convertir en court <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, revenez à la valeur par défaut <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	* @return résultat 
	*/
	public static Short toShort(Object value, Short defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Short) {
			return (Short) value;
		}
		if (value instanceof Number) {
			return ((Number) value).shortValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Short.parseShort(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en court <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, renvoie la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Short toShort(Object value) {
		return toShort(value, null);
	}

	/**
	* Convertir en nombre <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static Number toNumber(Object value, Number defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Number) {
			return (Number) value;
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return NumberFormat.getInstance().parse(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en nombre <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Number toNumber(Object value) {
		return toNumber(value, null);
	}

	/**
	* Convertir en int <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static Integer toInt(Object value, Integer defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en int <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, renvoie la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Integer toInt(Object value) {
		return toInt(value, null);
	}

	/**
	* Converti en tableau entier <br>
	*
	* @param str
	* La valeur convertie
	*@return résultat 
	*/
	public static Integer[] toIntArray(String str) {
		return toIntArray(",", str);
	}

	/**
	* Convertir en Long array <br>
	*
	* @param str
	* La valeur convertie
	*@return résultat 
	*/
	public static Long[] toLongArray(String str) {
		return toLongArray(",", str);
	}

	/**
	* Converti en tableau entier <br>
	*
	* @param split
	* Séparateur
	* @param split
	* La valeur convertie
	*@return résultat 
	*/
	public static Integer[] toIntArray(String split, String str) {
		if (StringUtils.isEmpty(str)) {
			return new Integer[] {};
		}
		String[] arr = str.split(split);
		final Integer[] ints = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			final Integer v = toInt(arr[i], 0);
			ints[i] = v;
		}
		return ints;
	}

	/**
	* Convertir en Long array <br>
	*
	* @param split
	* Séparateur
	* @param str
	* La valeur convertie
	*@return résultat 
	*/
	public static Long[] toLongArray(String split, String str) {
		if (StringUtils.isEmpty(str)) {
			return new Long[] {};
		}
		String[] arr = str.split(split);
		final Long[] longs = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			final Long v = toLong(arr[i], null);
			longs[i] = v;
		}
		return longs;
	}

	/**
	* Convertir en tableau de chaînes <br>
	*
	* @param str
	* La valeur convertie
	*@return résultat 
	*/
	public static String[] toStrArray(String str) {
		return toStrArray(",", str);
	}

	/**
	* Convertir en tableau de chaînes <br>
	*
	* @param split
	* Séparateur
	* @param split
	* La valeur convertie
	*@return résultat 
	*/
	public static String[] toStrArray(String split, String str) {
		return str.split(split);
	}

	/**
	* Convertir en long <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static Long toLong(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Long) {
			return (Long) value;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			// Soutenir la notation scientifique
			return new BigDecimal(valueStr.trim()).longValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en long <br>
	* Si la valeur donnée est <code> null </code>, ou si la conversion échoue, renvoie la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Long toLong(Object value) {
		return toLong(value, null);
	}

	/**
	* Convertir en double <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static Double toDouble(Object value, Double defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Double) {
			return (Double) value;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			// Soutenir la notation scientifique
			return new BigDecimal(valueStr.trim()).doubleValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en double <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Double toDouble(Object value) {
		return toDouble(value, null);
	}

	/**
	* Convertir en Float <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static Float toFloat(Object value, Float defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Float) {
			return (Float) value;
		}
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en Float <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Float toFloat(Object value) {
		return toFloat(value, null);
	}

	/**
	* Convertir en booléen <br>
	* Valeurs de chaîne prises en charge: true, false, yes, ok, no, 1,0. Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée. <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static Boolean toBool(Object value, Boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		valueStr = valueStr.trim().toLowerCase();
		switch (valueStr) {
		case "true":
			return true;
		case "false":
			return false;
		case "yes":
			return true;
		case "ok":
			return true;
		case "no":
			return false;
		case "1":
			return true;
		case "0":
			return false;
		default:
			return defaultValue;
		}
	}

	/**
	* Convertir en booléen <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static Boolean toBool(Object value) {
		return toBool(value, null);
	}

	/**
	* Convertir en objet Enum <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	*
	* @param clazz
	* Classe d'Enum
	* @param valeur
	* Valeur
	* @param defaultValue
	* Par défaut
	* @return Enum
	*/
	public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (clazz.isAssignableFrom(value.getClass())) {
			@SuppressWarnings("unchecked")
			E myE = (E) value;
			return myE;
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Enum.valueOf(clazz, valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en objet Enum <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut <code> null </code> <br>
	*
	* @param clazz
	* Classe d'Enum
	* @param valeur
	* Valeur
	* @return Enum
	*/
	public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value) {
		return toEnum(clazz, value, null);
	}

	/**
	* Convertir en BigInteger <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static BigInteger toBigInteger(Object value, BigInteger defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		if (value instanceof Long) {
			return BigInteger.valueOf((Long) value);
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return new BigInteger(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en BigInteger <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut <code> null </code> <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static BigInteger toBigInteger(Object value) {
		return toBigInteger(value, null);
	}

	/**
	* Convertir en BigDecimal <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	* @param defaultValue
	* Valeur par défaut en cas d'erreur de conversion
	*@return résultat 
	*/
	public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof Long) {
			return new BigDecimal((Long) value);
		}
		if (value instanceof Double) {
			return new BigDecimal((Double) value);
		}
		if (value instanceof Integer) {
			return new BigDecimal((Integer) value);
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return new BigDecimal(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	* Convertir en BigDecimal <br>
	* Si la valeur donnée est vide ou si la conversion échoue, la valeur par défaut sera renvoyée <br>
	* Aucune erreur ne sera signalée si la conversion échoue
	*
	* @param valeur
	* La valeur convertie
	*@return résultat 
	*/
	public static BigDecimal toBigDecimal(Object value) {
		return toBigDecimal(value, null);
	}

	/**
	* Convertit l'objet en chaîne <br>
	* 1. Les tableaux d'octets et ByteBuffer seront convertis en tableaux correspondant aux chaînes 2. Les tableaux d'objets appelleront la méthode Arrays.toString
	*
	* @param obj
	* Objet
	*@return Chaîne 
	*/
	public static String utf8Str(Object obj) {
		return str(obj, CharsetKit.CHARSET_UTF_8);
	}

	/**
	* Convertit l'objet en chaîne <br>
	* 1. Les tableaux d'octets et ByteBuffer seront convertis en tableaux correspondant aux chaînes 2. Les tableaux d'objets appelleront la méthode Arrays.toString
	*
	* @param obj
	* Objet
	* @param charsetName
		 *            jeu de caractères
	*@return Chaîne 
	*/
	public static String str(Object obj, String charsetName) {
		return str(obj, Charset.forName(charsetName));
	}

	/**
	* Convertit l'objet en chaîne <br>
	* 1. Les tableaux d'octets et ByteBuffer seront convertis en tableaux correspondant aux chaînes 2. Les tableaux d'objets appelleront la méthode Arrays.toString
	*
	* @param obj
	* Objet
	* @param charset
		 *            jeu de caractères
	*@return Chaîne 
	*/
	public static String str(Object obj, Charset charset) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof byte[] || obj instanceof Byte[]) {
			return str((Byte[]) obj, charset);
		} else if (obj instanceof ByteBuffer) {
			return str((ByteBuffer) obj, charset);
		}
		return obj.toString();
	}

	/**
	* Convertir le tableau d'octets en chaîne
	*
	* @param octets
	* tableau d'octets
	* @param charset
		 *            jeu de caractères
	*@return Chaîne 
	*/
	public static String str(byte[] bytes, String charset) {
		return str(bytes, StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}

	/**
	* Décoder le bytecode
	*
	* Données @param
	* Chaîne
	* @param charset
	* Jeu de caractères, si ce champ est vide, le résultat du décodage dépend de la plateforme
	*@return Chaîne décodée 
	*/
	public static String str(byte[] data, Charset charset) {
		if (data == null) {
			return null;
		}

		if (null == charset) {
			return new String(data);
		}
		return new String(data, charset);
	}

	/**
	* Convertissez les données encodées byteBuffer en une chaîne
	* Données 
	* @param
	* Les données
	* @param charset
	* Jeu de caractères, s'il est vide, utilisez le jeu de caractères système actuel
	*@return Chaîne 
	*/
	public static String str(ByteBuffer data, String charset) {
		if (data == null) {
			return null;
		}

		return str(data, Charset.forName(charset));
	}

	/**
	* Convertissez les données encodées byteBuffer en une chaîne
	*
	* Données @param
	* Les données
	* @param charset
	* Jeu de caractères, s'il est vide, utilisez le jeu de caractères système actuel
	*@return Chaîne 
	*/
	public static String str(ByteBuffer data, Charset charset) {
		if (null == charset) {
			charset = Charset.defaultCharset();
		}
		return charset.decode(data).toString();
	}

	// -----------------------------------------------------------------------
	// conversion demi-largeur pleine largeur
	/**
	* Demi-largeur à pleine largeur
	*
	* Entrée @param
	* Chaîne.
	* @return Chaîne pleine largeur.
	*/
	public static String toSBC(String input) {
		return toSBC(input, null);
	}

	/**
	* Demi-largeur à pleine largeur
	*
	* Entrée @param
	* Chaîne
	* @param notConvertSet
	* Jeu de caractères non remplacé
	* @return Chaîne pleine largeur.
	*/
	public static String toSBC(String input, Set<Character> notConvertSet) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (null != notConvertSet && notConvertSet.contains(c[i])) {
				// Ignorer les caractères non remplacés
				continue;
			}

			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	* Plein angle à demi-angle
	*
	* Entrée @param
	* Chaîne.
	* @return Chaîne demi-largeur
	*/
	public static String toDBC(String input) {
		return toDBC(input, null);
	}

	/**
	* Remplacez la pleine largeur par la demi-largeur
	*
	* @param text
	* Texte
	* @param notConvertSet
	* Jeu de caractères non remplacé
	* @return a remplacé les caractères
	*/
	public static String toDBC(String text, Set<Character> notConvertSet) {
		char c[] = text.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (null != notConvertSet && notConvertSet.contains(c[i])) {
				// Ignorer les caractères non remplacés
				continue;
			}

			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);

		return returnString;
	}

	/**
	* Conversion du nombre et du montant en majuscules, écrivez d'abord un complet, puis remplacez-le par zéro par exemple
	*
	* @param n
	* Nombre
	* @return chiffres majuscules chinois
	*/
	public static String digitUppercase(double n) {
		String[] fraction = { "角", "分" };
		String[] digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[][] unit = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$",
				"零元整");
	}
}
