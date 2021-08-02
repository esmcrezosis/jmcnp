package com.kreatech.common.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kreatech.common.util.OrioleStringUtils;

/**
 * Objet de message général, une structure de données emboîtable basée sur
 * l'implémentation du dictionnaire. Prise en charge de la structure de données
 * JSON.
 * 
 * 
 */
public class JSONObject extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	private static final Pattern arrayNamePattern = Pattern.compile("(\\w+)((\\[\\d+\\])+)");
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Structure du tableau.
	 */
	public static class JSONArray extends ArrayList<Object> {
		private static final long serialVersionUID = 1L;

		public JSONArray() {
			super();
		}

		public JSONArray(int size) {
			super(size);
		}

		@Override
		public String toString() {
			try {
				return JSON.marshal(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public Object set(int index, Object element) {
			return super.set(index, transfer(element));
		}

		@Override
		public boolean add(Object element) {
			return super.add(transfer(element));
		}

		@Override
		public void add(int index, Object element) {
			super.add(index, transfer(element));
		}
	}

	public JSONObject() {
		super();
	}

	public JSONObject(final JSONObject other) {
		super(other);
	}

	@Override
	public String toString() {
		try {
			return JSON.marshal(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Converti en chaîne au format compact.
	 *
	 * @return renvoie la chaîne de format compact de cet objet.
	 */
	public String toCompactString() {
		try {
			return objectMapper.writeValueAsString(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Obtenez la valeur entière du champ spécifié. Si le champ n'existe pas ou ne
	 * peut pas être converti en entier, null est renvoyé.
	 *
	 * @param name Nom du champ , prend en charge plusieurs niveaux.
	 * @return renvoie la valeur entière spécifiée, ou null.
	 */
	public Integer intValue(final String name) {
		return valueAsInt(value(name));
	}

	/**
	 * Obtenez la valeur entière du champ spécifié. Si le champ n'existe pas ou ne
	 * peut pas être converti en entier, la valeur defaultValue est renvoyée.
	 *
	 * @param name         Nom du champ , prend en charge plusieurs niveaux.
	 * @param defaultValue La valeur renvoyée lorsque la requête échoue.
	 * @return renvoie la valeur entière spécifiée, ou defaultValue.
	 */
	public Integer intValue(final String name, final Integer defaultValue) {
		return OrioleStringUtils.nvl(intValue(name), defaultValue);
	}

	/**
	 * Obtenez la valeur entière longue du champ spécifié. Si le champ n'existe pas
	 * ou ne peut pas être converti en un entier long, null est renvoyé.
	 *
	 * @param name Nom du champ , prend en charge plusieurs niveaux.
	 * @return renvoie la valeur entière longue spécifiée, ou null.
	 */
	public Long longValue(final String name) {
		return valueAsLong(value(name));
	}

	/**
	 * Obtenez la valeur entière longue du champ spécifié. Si le champ n'existe pas
	 * ou ne peut pas être converti en un entier long, la valeur defaultValue est
	 * renvoyée.
	 *
	 * @param name         Nom du champ, prend en charge plusieurs niveaux.
	 * @param defaultValue La valeur renvoyée lorsque la requête échoue.
	 * @return renvoie la valeur entière longue spécifiée, ou defaultValue.
	 */
	public Long longValue(final String name, final Long defaultValue) {
		return OrioleStringUtils.nvl(longValue(name), defaultValue);
	}

	/**
	 * Obtenez la valeur booléenne du champ spécifié. Si le champ n'existe pas ou ne
	 * peut pas être converti en booléen, null est renvoyé.
	 *
	 * @param name Nom du champ , prend en charge plusieurs niveaux.
	 * @return renvoie la valeur booléenne spécifiée, ou null.
	 */
	public Boolean boolValue(final String name) {
		return valueAsBool(value(name));
	}

	/**
	 * Obtenez la valeur booléenne du champ spécifié. Si le champ n'existe pas ou ne
	 * peut pas être converti en booléen, la valeur defaultValue est renvoyée.
	 *
	 * @param name         Nom du champ , prend en charge plusieurs niveaux.
	 * @param defaultValue La valeur renvoyée lorsque la requête échoue.
	 * @return renvoie la valeur booléenne spécifiée, ou defaultValue.
	 */
	public Boolean boolValue(final String name, final Boolean defaultValue) {
		return OrioleStringUtils.nvl(boolValue(name), defaultValue);
	}

	/**
	 * Obtenez la valeur de chaîne du champ spécifié. Si le champ n'existe pas,
	 * renvoie null.
	 *
	 * @param name Nom du champ , prend en charge plusieurs niveaux.
	 * @return renvoie la valeur de chaîne spécifiée, ou null.
	 */
	public String strValue(final String name) {
		return valueAsStr(value(name));
	}

	/**
	 * Obtenez la valeur de chaîne du champ spécifié. Si le champ n'existe pas, la
	 * valeur defaultValue est renvoyée.
	 *
	 * @param name         Nom du champ , prend en charge plusieurs niveaux.
	 * @param defaultValue La valeur renvoyée lorsque la requête échoue.
	 * @return renvoie la valeur de chaîne spécifiée, ou defaultValue.
	 */
	public String strValue(final String name, final String defaultValue) {
		return OrioleStringUtils.nvl(strValue(name), defaultValue);
	}

	/**
	 * Obtenez la valeur du champ spécifié.
	 *
	 * @param name nom du champ, prend en charge plusieurs niveaux, prend en charge
	 *             l'indice de tableau.
	 * @return renvoie la valeur du champ spécifié.
	 */
	public Object value(final String name) {
		final int indexDot = name.indexOf('.');
		if (indexDot >= 0) {
			return obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1));
		} else {
			final Matcher matcher = arrayNamePattern.matcher(name);
			if (matcher.find()) {
				return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Object>() {
					@Override
					public Object callback(JSONArray arr, int index) {
						return elementAt(arr, index);
					}
				});
			} else {
				return get(name);
			}
		}
	}

	/**
	 * Définissez la valeur du champ spécifié.
	 *
	 * @param name   nom du champ, prend en charge plusieurs niveaux, prend en
	 *               charge l'indice de tableau.
	 * @param Valeur du champ de valeur .
	 * @return renvoie cet objet.
	 */
	public JSONObject value(final String name, final Object value) {
		final int indexDot = name.indexOf('.');
		if (indexDot >= 0) {
			obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1), value);
		} else {
			final Matcher matcher = arrayNamePattern.matcher(name);
			if (matcher.find()) {
				endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Void>() {
					@Override
					public Void callback(JSONArray arr, int index) {
						elementAt(arr, index, value);
						return null;
					}
				});
			} else {
				set(name, value);
			}
		}
		return this;
	}

	/**
	 * Récupère le champ objet (type non scalaire). Les données renvoyées sont une
	 * structure. Lorsque l'objet spécifié n'existe pas, un objet MessageObject vide
	 * est créé pour le nom spécifié.
	 *
	 * @param name Nom du champ. Les noms à plusieurs niveaux ne sont pas pris en
	 *             charge et les indices de tableau sont pris en charge.
	 * @return renvoie l'objet spécifié. Si l'objet n'existe pas, un objet
	 *         MessageObject vide est créé pour le nom spécifié.
	 */
	public JSONObject obj(final String name) {
		final Matcher matcher = arrayNamePattern.matcher(name);
		if (matcher.find()) {
			return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<JSONObject>() {
				@Override
				public JSONObject callback(JSONArray arr, int index) {
					return objAt(arr, index);
				}
			});
		} else {
			JSONObject obj = getObj(name);
			if (obj == null) {
				obj = new JSONObject();
				put(name, obj);
			}
			return obj;
		}
	}

	/**
	 * Obtenez le champ de tableau. L'objet correspondant au nom est renvoyé en tant
	 * qu'objet tableau. Lorsque le champ spécifié n'existe pas, un tableau vide est
	 * créé.
	 *
	 * @param name Nom du champ. Les noms et indices à plusieurs niveaux ne sont pas
	 *             pris en charge.
	 * @return renvoie un tableau (List).
	 */
	public JSONArray arr(final String name) {
		JSONArray arr = getArr(name);
		if (arr == null) {
			arr = new JSONArray();
			put(name, arr);
		}
		return arr;
	}

	/**
	 * Récupère le champ objet (type non scalaire). Les données renvoyées sont une
	 * structure.
	 *
	 * @param name Nom du champ
	 * @return renvoie le champ d'objet spécifié.
	 */
	public JSONObject getObj(final String name) {
		return (JSONObject) get(name);
	}

	/**
	 * Obtenez le champ de type de tableau.
	 *
	 * @param name Nom du champ .
	 * @return renvoie un champ de type tableau.
	 */
	public JSONArray getArr(final String name) {
		return (JSONArray) get(name);
	}

	/**
	 * Renvoie la valeur entière du champ. S'il n'existe pas, renvoie null.
	 *
	 * @param name Nom du champ.
	 * @return renvoie la valeur entière du champ spécifié.
	 */
	public Integer getInt(final String name) {
		return valueAsInt(get(name));
	}

	/**
	 * Renvoie la valeur entière du champ. S'il n'existe pas, la valeur defaultValue
	 * est renvoyée.
	 *
	 * @param name         Nom du champ.
	 * @param defaultValue La valeur renvoyée lorsque le champ n'existe pas.
	 * @return renvoie la valeur entière du champ spécifié.
	 */
	public Integer getInt(final String name, Integer defaultValue) {
		return OrioleStringUtils.nvl(getInt(name), defaultValue);
	}

	/**
	 * Renvoie la valeur entière longue du champ. S'il n'existe pas, renvoie null.
	 *
	 * @param name Nom du champ.
	 * @return renvoie la valeur entière longue du champ spécifié.
	 */
	public Long getLong(final String name) {
		return valueAsLong(get(name));
	}

	/**
	 * Renvoie la valeur entière longue du champ. S'il n'existe pas, la valeur
	 * defaultValue est renvoyée.
	 *
	 * @param name         Nom du champ.
	 * @param defaultValue La valeur renvoyée lorsque le champ n'existe pas.
	 * @return renvoie la valeur entière longue du champ spécifié.
	 */
	public Long getLong(final String name, Long defaultValue) {
		return OrioleStringUtils.nvl(getLong(name), defaultValue);
	}

	/**
	 * Renvoie la valeur de la chaîne de champ. S'il n'existe pas, renvoie null.
	 *
	 * @param name Nom du champ.
	 * @return renvoie la valeur de chaîne du champ spécifié.
	 */
	public String getStr(final String name) {
		return valueAsStr(get(name));
	}

	/**
	 * Renvoie la valeur de la chaîne de champ. S'il n'existe pas, la valeur
	 * defaultValue est renvoyée.
	 *
	 * @param name         Nom du champ.
	 * @param defaultValue La valeur renvoyée lorsque le champ n'existe pas.
	 * @return renvoie la valeur de chaîne du champ spécifié.
	 */
	public String getStr(final String name, final String defaultValue) {
		return OrioleStringUtils.nvl(getStr(name), defaultValue);
	}

	/**
	 * La valeur du champ est renvoyée en type booléen. S'il n'existe pas, renvoie
	 * null.
	 *
	 * @param name Nom du champ.
	 * @return Valeur du champ
	 */
	public Boolean getBool(final String name) {
		return valueAsBool(get(name));
	}

	/**
	 * La valeur du champ est renvoyée en type booléen. S'il n'existe pas, la valeur
	 * defaultValue est renvoyée.
	 *
	 * @param name         Nom du champ.
	 * @param defaultValue La valeur renvoyée lorsque le champ n'existe pas.
	 * @return Valeur du champ
	 */
	public Boolean getBool(final String name, final Boolean defaultValue) {
		return OrioleStringUtils.nvl(getBool(name), defaultValue);
	}

	/**
	 * Définir la valeur du champ
	 *
	 * @param name  nom du champ
	 * @param value Valeur du champ (scalaire: nombre, chaîne, booléen; structure:
	 *              MessageObject). S'il s'agit de type Map et non de type
	 *              MessageObject, il sera automatiquement converti en type
	 *              MessageObject puis stocké (À ce stade, si vous modifiez les
	 *              données dans la carte, elles ne seront pas reflétées dans cet
	 *              objet).
	 * @return renvoie cet objet
	 */
	public JSONObject set(final String name, final Object value) {
		put(name, value);
		return this;
	}

	/**
	 * Convertissez cet objet en Java Bean.
	 *
	 * @param beanClass Objet de classe Java Bean.
	 * @return renvoie le Java Bean converti.
	 */
	public <T> T asBean(Class<T> beanClass) {
		try {
			return JSON.unmarshal(JSON.marshal(this), beanClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Surchargez les méthodes de la classe de base. Si la valeur est de type Map
	 * mais pas de type MessageObject, créez-en une nouvelle avec un contenu
	 * équivalent à la map d'origine MessageObject comme valeur (note: changer le
	 * contenu de la carte après cela ne sera pas reflété dans le MessageObject). Le
	 * but de la surcharge de cette méthode est de permettre à JSON d'être
	 * correctement analysé en objets MessageObject. Il n'est pas recommandé
	 * d'appeler cette méthode directement, veuillez utiliser set (nom, value) pour
	 * définir la valeur du champ.
	 */
	@Override
	public Object put(String key, Object value) {
		return super.put(key, transfer(value));
	}

	public static Integer valueAsInt(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			return Integer.valueOf((String) value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? 1 : 0;
		} else {
			return null;
		}
	}

	public static Long valueAsLong(Object value) {
		if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Number) {
			return ((Number) value).longValue();
		} else if (value instanceof String) {
			return Long.valueOf((String) value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? 1L : 0L;
		} else {
			return null;
		}
	}

	public static String valueAsStr(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	public static Boolean valueAsBool(Object value) {
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue() != 0.0;
		} else if (value instanceof String) {
			return Boolean.valueOf((String) value);
		} else {
			return null;
		}
	}

	/**
	 * Convertissez tous les types de Map mais pas MessageObject à tous les niveaux
	 * en MessageObject.
	 *
	 * @param Valeur de la valeur.
	 * @return renvoie la valeur convertie.
	 */
	@SuppressWarnings("unchecked")
	private static Object transfer(final Object value) {
		if (!(value instanceof JSONObject) && value instanceof Map) {
			return toObj((Map<String, Object>) value);
		} else if (!(value instanceof JSONArray) && value instanceof Collection) {
			return toArr((Collection<Object>) value);
		} else {
			return value;
		}
	}

	private static JSONArray toArr(final Collection<Object> list) {
		final JSONArray arr = new JSONArray(list.size());
		for (final Object element : list) {
			arr.add(element);
		}
		return arr;
	}

	private static JSONObject toObj(final Map<String, Object> map) {
		final JSONObject obj = new JSONObject();
		for (final Map.Entry<String, Object> ent : map.entrySet()) {
			obj.put(ent.getKey(), transfer(ent.getValue()));
		}
		return obj;
	}

	/**
	 * Renvoie l'élément d'indice spécifié sous forme de tableau, s'il n'existe pas,
	 * crée un tableau vide à cette position.
	 *
	 * @param arr    Le tableau actuel.
	 * @param Indice d'index.
	 * @return renvoie l'élément de l'indice spécifié du tableau actuel, qui doit
	 *         être un tableau.
	 */
	private static JSONArray arrayAt(JSONArray arr, int index) {
		expand(arr, index);
		if (arr.get(index) == null) {
			arr.set(index, new JSONArray());
		}
		return (JSONArray) arr.get(index);
	}

	/**
	 * Renvoie l'élément d'indice spécifié en tant que structure. S'il n'existe pas,
	 * créez une structure vide à cette position.
	 *
	 * @param arr    Le tableau actuel.
	 * @param Indice d'index.
	 * @return renvoie l'élément d'indice spécifié du tableau actuel, qui est une
	 *         structure.
	 */
	private static JSONObject objAt(final JSONArray arr, int index) {
		expand(arr, index);
		if (arr.get(index) == null) {
			arr.set(index, new JSONObject());
		}
		return (JSONObject) arr.get(index);
	}

	/**
	 * Définissez la valeur de la position d'indice spécifiée du tableau.
	 *
	 * @param arr    Tableau.
	 * @param Indice d'index.
	 * @param Valeur de la valeur.
	 */
	private static void elementAt(final JSONArray arr, final int index, final Object value) {
		expand(arr, index).set(index, value);
	}

	/**
	 * Obtenez la valeur de l'élément d'indice spécifié du tableau.
	 *
	 * @param arr    Tableau.
	 * @param Indice d'index.
	 * @return Valeur.
	 */
	private static Object elementAt(final JSONArray arr, final int index) {
		return expand(arr, index).get(index);
	}

	/**
	 * Étendez le tableau à l'indice spécifié pour empêcher l'indice de traverser la
	 * limite pendant l'accès.
	 *
	 * @param arr    tableau
	 * @param Indice d'index
	 * @return renvoie le tableau passé
	 */
	private static JSONArray expand(final JSONArray arr, final int index) {
		while (arr.size() <= index) {
			arr.add(null);
		}
		return arr;
	}

	/**
	 * Dernier rappel de tableau.
	 *
	 * @auteur Mike
	 *
	 * @param <T> Le rappel renvoie le type de données.
	 */
	private interface EndArrayCallback<T> {
		/**
		 * Lors de la localisation au dernier niveau du tableau, cette méthode sera
		 * appelée.
		 *
		 * @param arr   Le dernier objet du tableau de niveau.
		 * @param index Le dernier niveau d'index.
		 * @return renvoie la valeur de retour du rappel.
		 */
		T callback(JSONArray arr, int index);
	}

	/**
	 * Fonctions utilitaires pour gérer les tableaux multidimensionnels (y compris
	 * les tableaux unidimensionnels). Le nom du tableau multidimensionnel est
	 * comme: arrary [1] [2] [3], Alors name = array, indexStr = [1] [2] [3], dans
	 * callback, endArr sera l'objet spécifié par array [1] [2], indexe = 3.
	 *
	 * @param name       Le nom sans indice et les noms à plusieurs niveaux ne sont
	 *                   pas pris en charge.
	 * @param indexesStr La chaîne de la partie d'index, telle que: [1] [2] [3]
	 * @param Fonction   de rappel de rappel.
	 * @return renvoie la valeur de retour de la fonction de rappel.
	 */
	private <T> T endArray(final String name, final String indexesStr, final EndArrayCallback<T> callback) {
		JSONArray endArr = arr(name);
		final int[] indexes = parseIndexes(indexesStr);
		int i = 0;
		while (i < indexes.length - 1) {
			endArr = arrayAt(endArr, indexes[i++]);
		}
		return callback.callback(endArr, indexes[i]);
	}

	private static int[] parseIndexes(final String s) {
		int[] indexes = null;
		List<Integer> list = new ArrayList<Integer>();

		final StringTokenizer st = new StringTokenizer(s, "[]");
		while (st.hasMoreTokens()) {
			final int index = Integer.valueOf(st.nextToken());
			if (index < 0) {
				throw new RuntimeException(String.format("Illegal index %1$d in \"%2$s\"", index, s));
			}

			list.add(index);
		}

		indexes = new int[list.size()];
		int i = 0;
		for (Integer tmp : list.toArray(new Integer[list.size()])) {
			indexes[i++] = tmp;
		}

		return indexes;
	}
}
