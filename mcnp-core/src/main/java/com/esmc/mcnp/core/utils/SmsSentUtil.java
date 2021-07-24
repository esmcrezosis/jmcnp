package com.esmc.mcnp.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Objects;

public class SmsSentUtil {

	public static String sendSms(String numero, String sms, String typeExpediteur) {
		String parametre = "";

		try {
			parametre = "http://prodsms.gacsource.net/smstogocel/envoisms.php?login=esmc&password=mcnp&type=1&destination=228"
					+ numero + "&message=" + URLEncoder.encode(sms, "UTF-8") + "";

			System.out.println("parametre= " + parametre);

		} catch (UnsupportedEncodingException e) {
			System.out.println("url non conforme");
		}

		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {

			URL url = new URL(parametre);
			urlConn = url.openConnection();
			if (urlConn != null) {
				urlConn.setReadTimeout(60 * 1000);
			}
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
						System.out.println("sb = " + sb);
					}
					bufferedReader.close();
				}
			}
			if (Objects.nonNull(in)) {
				in.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + parametre, e);
		}

		return sb.toString();
	}

	/*
	 * SimpleDateFormat formatter = null; formatter = new
	 * SimpleDateFormat("yyyyMMddHHmmss");
	 * 
	 * typeExpediteur = "TOGOCEL";
	 * 
	 * 
	 * //Long neng = smsSentService.findMaxInsertedIdSmsSent();
	 * 
	 * EuSmsSent eusmsSent = new EuSmsSent(); eusmsSent.setNeng(700L);
	 * eusmsSent.setDatetime(formatter.format(new Date())); eusmsSent.setEtat(null);
	 * eusmsSent.setMsgid(null); eusmsSent.setRecipient(numero);
	 * eusmsSent.setSmsbody(sms); eusmsSent.setTypeexpediteur(typeExpediteur);
	 * 
	 * smsSentService.add(eusmsSent);
	 

	public static void main(String[] args) {
		SmsSentUtil.sendSms("90245932", "Bonjour manaani! de la part de ESMC", "TOGOCEL");

	}*/

}
/*
 * $smssent = new Application_Model_EuSmsSent(); $smssentM = new
 * Application_Model_EuSmsSentMapper();
 * 
 * $compteursms = $smssentM->findConuter() + 1;
 * 
 * $smssent->setNEng($compteursms); $smssent->setRecipient($recipient);
 * $smssent->setSMSBody($smsbody); $smssent->setTypeExpediteur($type);
 * $smssent->setDateTime($date_id->toString('yyyy-MM-dd HH:mm:ss'));
 * $smssent->setEtat(NULL); $smssent->setMsgId(NULL); $smssentM->save($smssent);
 */