package com.esmc.mcnp.commons.constant;

/**
 * Variables statiques au niveau du système
 */
public class SystemConstant {

	public static final String SF_FILE_SEPARATOR = System.getProperty("file.separator");

	public static final String DATA_ROWS = "rows";

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final Integer PAGE_SIZE = 10;

	public static final String FILE = "file";

	public static final String TRUE = "true";

	public static final String FALSE = "false";

	public static final Short DELETE_STATUS_YES = 0;

	public static final Short DELETE_STATUS_NO = 1;

	/**
	 * Avatar: 0 par défaut 1 téléchargement
	 */
	public static final Short AVATAR_STATUS_YES = 1;

	public static final Short AVATAR_STATUS_NO = 0;

    /**
     * Abonnez-vous aux actualités: 0 Non 1 Oui
     */
    public static final Short SUBSCRIBE_STATUS_YES = 1;

    public static final Short SUBSCRIBE_STATUS_NO = 0;

    /**
     * Recommandation du jour: 0 Non 1 Oui
     */
    public static final Short HOT_STATUS_YES = 1;

    public static final Short HOT_STATUS_NO = 0;

    /**
     * Connexion: 0 Non 1 Oui
     */
    public static final Short SIGN_STATUS_YES = 1;

    public static final Short SIGN_STATUS_NO = 0;


    /**
     * 500 Logo
     */
    public static final String CODE_500 = "500";

	/**
	 * 200 Logo
	 */
	public static final String CODE_200 = "200";

	/**
	 * CODE Logo
	 */
	public static final String CODE_0 = "0";

	/**
	 * Type de menu
	 */
	public enum MenuType {
		/**
		 * table des matières
		 */
		CATALOG(0),
		/**
		 * menu
		 */
		MENU(1),
		/**
		 * Bouton
		 */
		BUTTON(2);

		private final int value;

		private MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * Dictionnaire commun
	 */
	public enum MacroType {

		/**
		 * Types de
		 */
		TYPE(0),

		/**
		 * paramètre
		 */
		PARAM(1);

		private final int value;

		private MacroType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	/**
	 * Variable générale, qui signifie disponible, désactivée, affichée, masquée, oui, non
	 */
	public enum StatusType {

		/**
		 * Désactiver, masquer
		 */
		DISABLE(0),

		/**
		 * Disponible, montrer
		 */
		ENABLE(1),

		/**
		 * afficher
		 */
		SHOW(1),

		/**
		 * cacher
		 */
		HIDDEN(0),

		/**
		 * Oui
		 */
		YES(1),

		/**
		 * Non
		 */
		NO(0);

		private final int value;

		private StatusType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
