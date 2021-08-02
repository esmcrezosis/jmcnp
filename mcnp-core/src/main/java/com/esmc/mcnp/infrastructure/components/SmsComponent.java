package com.esmc.mcnp.infrastructure.components;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
/*import java.text.SimpleDateFormat;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.model.sms.EuSms;
import com.esmc.mcnp.services.EuSmsService;*/
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.domain.entity.sms.EuSmsConnexion;
import com.esmc.mcnp.infrastructure.services.common.EuSmsConnexionService;


@Component
public class SmsComponent {

	//private @Autowired EuSmsService smsService;
	private @Autowired EuSmsConnexionService smsConnexionService;

	//private @Autowired EuSmsSentService smsSentService;

	public SmsComponent() {
	}

	public boolean createAndSendCode(String codeEnvoi, String codeMembreAcheteur, String smsBody){
		if(StringUtils.isNotBlank(smsBody) && StringUtils.isNotBlank(codeEnvoi) && StringUtils.isNotBlank(codeMembreAcheteur)){

			Long idsmsCon = smsConnexionService.getLastInsertId();
			if (idsmsCon == null) {
				idsmsCon = 1L;
			} else {
				idsmsCon++;
			}
			EuSmsConnexion smsConnexion = new EuSmsConnexion();
			smsConnexion.setSmsConnexionId(idsmsCon);
			smsConnexion.setSmsConnexionCodeMembre(codeMembreAcheteur);
			smsConnexion.setSmsConnexionCodeEnvoi(codeEnvoi.substring(0, 5));
			smsConnexion.setSmsConnexionCodeRecu(smsBody);
			smsConnexion.setSmsConnexionDate(new Date());
			smsConnexion.setSmsConnexionUtilise(0);
			smsConnexionService.add(smsConnexion);

			/*//eusms
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String recipient = indicatif + numero;
			Long idSms = smsService.getLastSmsInserted();
			if (idSms == null) {
				idSms = 1L;
			} else {
				idSms++;
			}
			EuSms sms = new EuSms();
			sms.setNeng(idSms);
			sms.setDateenvoi(formatter.format(date));
			sms.setDatetime(formatter.format(date));
			sms.setEtat(null);
			sms.setRecipient(recipient);
			sms.setSmsbody(smsBody);
			sms.setIddatetime(ServerUtil.dateToInt(date));
			sms = smsService.add(sms);*/

			return true;
		}
		return false;
	}

	public boolean verifyCodeSms(String codeEnvoi, String codeBonRecu, String codeMembre){
		if(StringUtils.isNotBlank(codeEnvoi) && StringUtils.isNotBlank(codeBonRecu)){
			String codeRecu =  smsConnexionService.getCodeRecuSmsConnexionByCodeEnvoi(codeMembre, codeEnvoi);
			if(StringUtils.isNotBlank(codeRecu)){
				if(codeRecu.contains(codeBonRecu)){
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public int miseAjourSmsConnexion(String codeRecu){
		if(StringUtils.isNotBlank(codeRecu)){
			EuSmsConnexion smsConnexion =  smsConnexionService.getSmsConnexionByCodeEnvoi(codeRecu);
			if(smsConnexion!=null){
				smsConnexion.setSmsConnexionUtilise(1);
				smsConnexionService.add(smsConnexion);
				return 1;
			}
			return 0;
		}
		return 0;
	}


	public boolean sendCodeSms(String codeMembreAcheteur, String smsBody, String codeEnvoi, String numeroTelephone){
		if(StringUtils.isNotBlank(smsBody) && StringUtils.isNotBlank(codeMembreAcheteur)
				 && StringUtils.isNotBlank(numeroTelephone)){

			Long idsmsCon = smsConnexionService.getLastInsertId();
			if (idsmsCon == null) {
				idsmsCon = 1L;
			} else {
				idsmsCon++;
			}
			EuSmsConnexion smsConnexion = new EuSmsConnexion();
			smsConnexion.setSmsConnexionId(idsmsCon);
			smsConnexion.setSmsConnexionCodeMembre(codeMembreAcheteur);
			smsConnexion.setSmsConnexionCodeEnvoi(codeEnvoi.substring(0, 5));
			smsConnexion.setSmsConnexionCodeRecu(smsBody);
			smsConnexion.setSmsConnexionDate(new Date());
			smsConnexion.setSmsConnexionUtilise(0);
			smsConnexionService.add(smsConnexion);

			try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
				URI uri = new URIBuilder()
						.setScheme("http")
						.setHost("prodsms.gacsource.net")
						.setPath("/espacepersonnel/envoismseasys/portable/"+numeroTelephone+"/message/" + smsBody)
						.build();
				//https://prod.gacsource.net/espacepersonnel/envoismseasys/portable/22890291387/message/testlooky/
				System.out.println(uri.toString());
				HttpGet httpget = new HttpGet(uri);
				try (CloseableHttpResponse response = client.execute(httpget)) {
					/*if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						String html = EntityUtils.toString(entity);
						Element body = Jsoup.parse(html).body();
						String[] texts = body.text().split(" ");
						Integer status = Integer.parseInt(texts[0].split("=")[1]);
						if(status==0){
						Integer msgid = Integer.parseInt(texts[1].split("=")[1]);
						smst.setMsgid(msgid);
						}
						sms.setEtat(status);
						smsService.update(sms);
						smst.setEtat(status);
						smsSentService.update(smst);
						
					}*/
					return true;
				}
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}

	public boolean sendNotifications(String codeMembreAcheteur, String smsBody){
		if(StringUtils.isNotBlank(smsBody) && StringUtils.isNotBlank(codeMembreAcheteur)){

			/*Long idsmsCon = smsConnexionService.getLastInsertId();
			if (idsmsCon == null) {
				idsmsCon = 1L;
			} else {
				idsmsCon++;
			}
			EuSmsConnexion smsConnexion = new EuSmsConnexion();
			smsConnexion.setSmsConnexionId(idsmsCon);
			smsConnexion.setSmsConnexionCodeMembre(codeMembreAcheteur);
			smsConnexion.setSmsConnexionCodeEnvoi(codeEnvoi.substring(0, 5));
			smsConnexion.setSmsConnexionCodeRecu(smsBody);
			smsConnexion.setSmsConnexionDate(new Date());
			smsConnexion.setSmsConnexionUtilise(0);
			smsConnexionService.add(smsConnexion);*/

			try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
				URI uri = new URIBuilder()
						.setScheme("http")
						.setHost("172.16.20.50")
						.setPath("/notif/envoinotification.php")
						.setParameter("titre", "ESMC")
						.setParameter("code_membre", codeMembreAcheteur)
						.setParameter("message", smsBody)
						.build();
				/*http://172.16.20.51/notif/envoinotification.php?titre=ESMC&code_membre=0010010010010091699P&message=MESSAGE
*/				System.out.println(uri.toString());
				HttpGet httpget = new HttpGet(uri);
				try (CloseableHttpResponse response = client.execute(httpget)) {
					/*if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						String html = EntityUtils.toString(entity);
						Element body = Jsoup.parse(html).body();
						String[] texts = body.text().split(" ");
						Integer status = Integer.parseInt(texts[0].split("=")[1]);
						if(status==0){
						Integer msgid = Integer.parseInt(texts[1].split("=")[1]);
						smst.setMsgid(msgid);
						}
						sms.setEtat(status);
						smsService.update(sms);
						smst.setEtat(status);
						smsSentService.update(smst);
						
					}*/
					return true;
				}
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}


	/*public boolean sendSms(String smsBody, String indicatif, String numero) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String recipient = indicatif + numero;
		Long idSms = smsService.getLastSmsInserted();
		if (idSms == null) {
			idSms = 1L;
		} else {
			idSms++;
		}
		EuSms sms = new EuSms();
		sms.setNeng(idSms);
		sms.setDateenvoi(formatter.format(date));
		sms.setDatetime(formatter.format(date));
		sms.setEtat(null);
		sms.setRecipient(recipient);
		sms.setSmsbody(smsBody);
		sms.setIddatetime(ServerUtil.dateToInt(date));
		sms = smsService.add(sms);

		EuSmsSent smst = new EuSmsSent();
		smst.setDatetime(formatter.format(date));
		smst.setEtat(null);
		smst.setMsgid(0);
		smst.setRecipient(recipient);
		smst.setSmsbody(smsBody);
		smst.setTypeexpediteur("TOGOCEL");
		smst = smsSentService.add(smst);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("prodsms.gacsource.net/smstogocel")
					.setPath("/envoisms.php")
					.setParameter("login", "esmc")
					.setParameter("password", "mcnp")
					.setParameter("type", "1")
					.setParameter("destination", recipient)
					.setParameter("message", smsBody)
					.build();
			System.out.println(uri.toString());
			HttpGet httpget = new HttpGet(uri);
			System.out.println(uri.toString());
			try (CloseableHttpResponse response = client.execute(httpget)) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String html = EntityUtils.toString(entity);
					Element body = Jsoup.parse(html).body();
					String[] texts = body.text().split(" ");
					Integer status = Integer.parseInt(texts[0].split("=")[1]);
					if(status==0){
					Integer msgid = Integer.parseInt(texts[1].split("=")[1]);
					smst.setMsgid(msgid);
					}
					sms.setEtat(status);
					smsService.update(sms);
					smst.setEtat(status);
					smsSentService.update(smst);
					return true;
				}
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}*/
	
}
