package com.esmc.mcnp.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Date utilities.
 *
 * @author johnniang
 * @date 3/18/19
 */
public class DateUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String yyyyMMdd = "yyyy-MM-dd";

	public static final String yyyyMMddNo = "yyyyMMdd";

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM", "dd-MM-yyyy", "dd-MM-yyyy HH:mm:ss", "dd-MM-yyyy HH:mm", "MM-yyyy",
			"dd/MM/yyyy", "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm", "MM/yyyy", "dd.MM.yyyy", "dd.MM.yyyy HH:mm:ss",
			"dd.MM.yyyy HH:mm", "MM.yyyy" };

	/**
	 * Format de date(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * Format de date et heure(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private DateUtils() {
	}

	/**
	 * Date path i.e. year/month/day such as 2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		return df.format(now);
	}

	/**
	 * Gets current date.
	 *
	 * @return current date
	 */
	@NonNull
	public static Date now() {
		return new Date();
	}

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	public static String format(Timestamp date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	public static Date format(String str, String pattern) {
		if (str != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			try {
				return df.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotEmpty(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * Obtenez la date actuelle
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getTimeStamp() {
		return format(new Timestamp(System.currentTimeMillis()), DATE_TIME_PATTERN);
	}

	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * Converts from date into a calendar instance.
	 *
	 * @param date date instance must not be null
	 * @return calendar instance
	 */
	@NonNull
	public static Calendar convertTo(@NonNull Date date) {
		Assert.notNull(date, "Date must not be null");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Adds date.
	 *
	 * @param date     current date must not be null
	 * @param time     time must not be less than 1
	 * @param timeUnit time unit must not be null
	 * @return added date
	 */
	public static Date add(@NonNull Date date, long time, @NonNull TimeUnit timeUnit) {
		Assert.notNull(date, "Date must not be null");
		Assert.isTrue(time >= 0, "Addition time must not be less than 1");
		Assert.notNull(timeUnit, "Time unit must not be null");

		Date result;

		int timeIntValue;

		if (time > Integer.MAX_VALUE) {
			timeIntValue = Integer.MAX_VALUE;
		} else {
			timeIntValue = Long.valueOf(time).intValue();
		}

		// Calc the expiry time
		switch (timeUnit) {
		case DAYS:
			result = org.apache.commons.lang3.time.DateUtils.addDays(date, timeIntValue);
			break;
		case HOURS:
			result = org.apache.commons.lang3.time.DateUtils.addHours(date, timeIntValue);
			break;
		case MINUTES:
			result = org.apache.commons.lang3.time.DateUtils.addMinutes(date, timeIntValue);
			break;
		case SECONDS:
			result = org.apache.commons.lang3.time.DateUtils.addSeconds(date, timeIntValue);
			break;
		case MILLISECONDS:
			result = org.apache.commons.lang3.time.DateUtils.addMilliseconds(date, timeIntValue);
			break;
		default:
			result = date;
		}
		return result;
	}

	/**
	 * Date de chaîne convertie en date date
	 *
	 * @param strDate Chaîne de format de date à convertir
	 * @param format  Le format de date à convertir
	 * @return Chaîne de format de date à ce jour
	 */
	public static Date strToDate(String strDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Date de chaîne convertie en date date
	 *
	 * @param stringDate Chaîne de format de date à convertir
	 * @return Chaîne de format de date à ce jour
	 */
	public static Date defaultStrToDate(String stringDate) {
		return strToDate(stringDate, DEFAULT_FORMAT);
	}

	/**
	 * Date date convertie en date chaîne
	 *
	 * @param date   La date à convertir
	 * @param format string format de date à convertir
	 * @return Date en chaîne
	 */
	public static String dateToStr(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Date date convertie en date chaîne
	 *
	 * @param date La date à convertir
	 * @return Date en chaîne
	 */
	public static String defaultDateToStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		return dateFormat.format(date);
	}

	public static Date getStartDay(String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(yyyyMMdd);
		try {
			Date date = dateFormat.parse(strDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static Date getEndDay(String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(yyyyMMdd);
		try {
			Date date = dateFormat.parse(strDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return calendar.getTime();
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * Comparez la taille entre deux dates
	 *
	 * @param beforeDate
	 * @param afterDate
	 * @return Le premier est plus grand que le second, renvoie vrai, sinon faux
	 */
	public static boolean compareDate(Date beforeDate, Date afterDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(beforeDate);
		c2.setTime(afterDate);
		return c1.compareTo(c2) >= 0;
	}

	/**
	 * Calculer deux différences de temps
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// Obtenez la différence de temps en millisecondes entre deux heures
		long diff = endDate.getTime() - nowDate.getTime();
		// Calculez la différence en jours
		long day = diff / nd;
		// Calculez la différence en heures
		long hour = diff % nd / nh;
		// Calculez la différence en minutes
		long min = diff % nd % nh / nm;
		// Calculer la différence en secondes // afficher le résultat
		// long sec = diff % nd % nh % nm / ns;
		return day + "Jours" + hour + "Heures" + min + "Minutes";
	}

	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	public static String getTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

}
