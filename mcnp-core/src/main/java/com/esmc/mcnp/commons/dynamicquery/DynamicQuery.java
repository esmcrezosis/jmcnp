package com.esmc.mcnp.commons.dynamicquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Étendez SpringDataJpa, prenez en charge les requêtes jpql / nativesql dynamiques et les requêtes de pagination
 * @author mawuli
 */
public interface DynamicQuery {

	void save(Object entity);

	void update(Object entity);

	/**
	 * Effectuer des opérations de mise à jour et de suppression de nativeSql
	 * @param nativeSql
	 * @param params
	 * @return
	 */
	int nativeExecuteUpdate(String nativeSql, Object... params);

	/**
	 * Effectuer une requête statistique nativeSql
	 * @param nativeSql
	 * @param params Paramètre d'espace réservé (par exemple? 1) valeur du paramètre lié
	 * @return Nombre de statistiques
	 */
	Long nativeQueryCount(String nativeSql, Object... params);

	/**
	 * Exécuter la requête nativeSql sur une ligne
	 * @param resultClass Type de résultat de la requête
	 * @param nativeSql
	 * @param params Paramètre d'espace réservé (par exemple? 1) valeur du paramètre lié
	 * @return
	 */
	<T> T nativeQuerySingleResult(Class<T> resultClass, String nativeSql, Object... params);

	/**
	 * Exécuter la liste de requêtes nativeSql <Object[]>
	 * @param nativeSql
	 * @param params Paramètre d'espace réservé (par exemple? 1) valeur du paramètre lié
	 * @return
	 */
	<T> List<T> query(String nativeSql, Object... params);

	/**
	 * Exécuter une requête nativeSql
	 * @param resultClass
	 * @param nativeSql
	 * @param params Paramètre d'espace réservé (par exemple? 1) valeur du paramètre lié
	 * @return
	 */
	<T> List<T> query(Class<T> resultClass, String nativeSql, Object... params);

	/**
	 * Exécuter la requête de pagination nativeSql
	 * @param resultClass Type de résultat de la requête
	 * @param pageable Données de pagination
	 * @param nativeSql
	 * @param params Paramètre d'espace réservé (par exemple? 1) valeur du paramètre lié
	 * @return Objet de pagination
	 */
	<T> Page<T> nativeQuery(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);

	/**
	 * Exécuter la requête de pagination nativeSql
	 * @param resultClass
	 * @param pageable
	 * @param nativeSql
	 * @param params
	 * @return List<T>
	 */
	<T> List<T> nativeQueryPagingList(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);

    /**
     * Interroge la liste des objets et renvoie List<Map<key,value>>
     * @param nativeSql
     * @param params
     * @return  T
     */
    <T> T nativeQueryMap(String nativeSql, Object... params);

    /**
     * Interroge la liste d'objets et renvoie List <objet combiné>
     * @param resultClass
     * @param nativeSql
     * @param params
     * @return
     */
    <T> T nativeQueryModel(Class<T> resultClass, String nativeSql, Object... params);
    /**
     * Interroge la liste des objets et renvoie List<Map<key,value>>
     * @param nativeSql
     * @param params
     * @return  List<T>
     */
    <T> List<T> nativeQueryListMap(String nativeSql, Object... params);

    /**
     * Interroge la liste d'objets et renvoie List<Objet>
     * @param resultClass
     * @param nativeSql
     * @param params
     * @return  List<T>
     */
    <T> List<T> nativeQueryListModel(Class<T> resultClass, String nativeSql, Object... params);

    /**
     * Interroge la liste d'objets et renvoie List<Objet>
     * @param resultClass
     * @param nativeSql
     * @param params
     * @return  List<T>
     */
    <T> List<T> nativeQueryPagingListModel(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);
}
