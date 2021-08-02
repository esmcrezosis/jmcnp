package com.esmc.mcnp.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * Annotation de données Excel d'exportation personnalisée
 * 
 * @author ruoyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
	/**
	 * Trier dans Excel lors de l'exportation
	 */
	public int sort() default Integer.MAX_VALUE;

	/**
	 * Le nom exporté vers Excel.
	 */
	public String name() default "";

	/**
	 * Format de date, tel que: aaaa-MM-jj
	 */
	public String dateFormat() default "";

	/**
	 * S'il s'agit d'un type de dictionnaire, veuillez définir la valeur de type du
	 * dictionnaire (par exemple: sys_user_sex)
	 */
	public String dictType() default "";

	/**
	 * Lire le contenu de l'expression (par exemple: 0 = homme, 1 = femme, 2 =
	 * inconnu)
	 */
	public String readConverterExp() default "";

	/**
	 * Séparateur, lisez le contenu du groupe de chaînes
	 */
	public String separator() default ",";

	/**
	 * Précision BigDecimal Par défaut: -1 (la mise en forme BigDecimal n'est pas
	 * activée par défaut)
	 */
	public int scale() default -1;

	/**
	 * Règles d'arrondi BigDecimal Par défaut: BigDecimal.ROUND_HALF_EVEN
	 */
	public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

	/**
	 * Type d'exportation (chaîne 0 nombre 1)
	 */
	public ColumnType cellType() default ColumnType.STRING;

	/**
	 * La hauteur de chaque colonne dans Excel lors de l'exportation en caractères
	 */
	public double height() default 14;

	/**
	 * Lors de l'exportation, la largeur de chaque colonne dans Excel est en
	 * caractères
	 */
	public double width() default 16;

	/**
	 * Le suffixe de texte, tel que% 90 devient 90%
	 */
	public String suffix() default "";

	/**
	 * Lorsque la valeur est vide, la valeur par défaut du champ
	 */
	public String defaultValue() default "";

	/**
	 * Information rapide
	 */
	public String prompt() default "";

	/**
	 * Le paramètre ne peut sélectionner que le contenu de la colonne qui ne peut
	 * pas être saisi.
	 */
	public String[] combo() default {};

	/**
	 * Exportation des données et réponse aux exigences: Parfois, nous devons
	 * exporter un modèle, qui est requis pour le titre, mais le contenu doit être
	 * rempli par l'utilisateur.
	 */
	public boolean isExport() default true;

	/**
	 * Le nom d'attribut dans une autre classe prend en charge l'acquisition à
	 * plusieurs niveaux, séparés par des points décimaux
	 */
	public String targetAttr() default "";

	/**
	 * S'il faut compter automatiquement les données, ajoutez une ligne de données
	 * statistiques à la fin
	 */
	public boolean isStatistics() default false;

	/**
	 * Alignement des champs d'exportation (0: par défaut; 1: gauche; 2: centre; 3:
	 * droite)
	 */
	Align align() default Align.AUTO;

	public enum Align {
		AUTO(0), LEFT(1), CENTER(2), RIGHT(3);

		private final int value;

		Align(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * Type de champ (0: exportation et importation; 1: exportation uniquement; 2:
	 * importation uniquement)
	 */
	Type type() default Type.ALL;

	public enum Type {
		ALL(0), EXPORT(1), IMPORT(2);

		private final int value;

		Type(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	public enum ColumnType {
		NUMERIC(0), STRING(1), IMAGE(2);

		private final int value;

		ColumnType(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
}