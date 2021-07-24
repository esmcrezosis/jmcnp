package com.esmc.mcnp.commons.constant;
/**
 * Le système demande des variables statiques
 */
public class MsgConstant {

	public static final String MSG_OPERATION_SUCCESS = "Opération réussie！";

	public static final String MSG_OPERATION_FAILED = "L'opération a échoué！";
	
	/**
	 * Lors de la suppression, il indique qu'il existe des nœuds enfants qui ne peuvent pas être supprimés
	 */
	public static final String MSG_HAS_CHILD = "L'opération a échoué, les données actuellement sélectionnées ont des données de nœud enfant！";
	
	/**
	 * Message d'erreur lors du chargement des données du formulaire
	 */
	public static final String MSG_INIT_FORM = "Échec de l'initialisation des données du formulaire, veuillez réessayer！";
	
}
