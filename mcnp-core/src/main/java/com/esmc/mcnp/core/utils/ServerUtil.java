package com.esmc.mcnp.core.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

public class ServerUtil {

	private static final String CONFIG_FILE = "resources/config";

	public ServerUtil() {
	}

	/**
	 * read property file from classpath
	 *
	 * @param propertyFileName
	 * @return
	 * @throws IOException
	 */
	public static Properties readPropertyFileFromClasspath(String propertyFileName) throws IOException {
		Properties props = new Properties();
		props.load(ServerUtil.class.getClassLoader().getResourceAsStream(propertyFileName));
		return props;
	}

	public static String getProperty(String propertyKey) {
		ResourceBundle rsb = ResourceBundle.getBundle(CONFIG_FILE);
		return rsb.getString(propertyKey);
	}

	public static String getProperty(Properties props, String key) {
		return props.getProperty(key);
	}

	public static String getCodeMembre(String codeCompte) {
		String codeMembre = "";
		if (!codeCompte.isEmpty()) {
			String[] split = codeCompte.split("-");
			codeMembre = split[split.length - 1];
		}
		return codeMembre;
	}

	public static String getTypeNumerique(String codeCompte) {
		String codeType = "";
		if (!codeCompte.isEmpty()) {
			String[] split = codeCompte.split("-");
			codeType = split[0];
		}
		return codeType;
	}

	public static String getTypeCompte(String codeCompte) {
		String codeType = "";
		if (!codeCompte.isEmpty()) {
			String[] split = codeCompte.split("-");
			codeType = split[1];
		}
		return codeType;
	}

	public static Date ajouterMois(Date date, int nbMois) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, nbMois);
		return cal.getTime();
	}

	public static Date ajouterJours(Date date, int nbJours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, nbJours);
		return cal.getTime();
	}

	public static String GenererUniqueCode() {
		UUID idOne = UUID.randomUUID();
		String Objet = idOne.toString();
		String string1 = Objet.substring(0, 2) + Objet.substring(2, 4) + Objet.substring(4, 6) + Objet.substring(6, 8);
		return string1.toUpperCase();
	}
	
	public static String GenerateUnicCode() {
		UUID idOne = UUID.randomUUID();
		String Objet = idOne.toString();
		String string1 = Objet.substring(0, 2) + Objet.substring(2, 4);
		return string1.toUpperCase();
	}

	public static String GenererUniqueCodeForBan() {
		UUID idOne = UUID.randomUUID();
		String Objet = idOne.toString();
		String string1 = Objet.substring(0, 2) + Objet.substring(2, 4) + Objet.substring(4, 6) + Objet.substring(6, 8)
				+ Objet.substring(9, 10);
		return string1.toUpperCase();
	}

	public static boolean isPhysique(String codeMembre) {
		return codeMembre.endsWith("P");
	}

	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return formatter.format(date);
	}

	public static String formatDate2(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		return formatter.format(date);
	}

	public static long dateToInt(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		Calendar cal1 = Calendar.getInstance();
		cal1.set(year, 1, 1);
		long time = cal.getTimeInMillis() - cal1.getTimeInMillis() + (3600 - 24);
		return  (time / (3600 * 24) + 366 * (year - 1));
	}
}
