package com.esmc.mcnp.web.mvc.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.model.security.EuConfirmation;
import com.esmc.mcnp.model.others.EuProcuration;
import com.esmc.mcnp.dto.acteur.Membre;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.services.cm.EuMembreService;
import com.esmc.mcnp.services.cm.EuRepresentationService;
import com.esmc.mcnp.services.common.EuConfirmationService;
import com.esmc.mcnp.services.common.EuProcurationService;
import com.esmc.mcnp.web.fingerprint.BiometricConfig;
import com.esmc.mcnp.web.fingerprint.BiometricMatch;
import com.esmc.mcnp.web.fingerprint.BiometricSubject;
import com.esmc.mcnp.web.mvc.biometric.BiometricLicenseManager;
import com.esmc.mcnp.web.mvc.dto.Confirmation;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NFRecord;
import com.neurotec.biometrics.NFTemplate;
import com.neurotec.biometrics.NMatchingResult;
import com.neurotec.biometrics.NMatchingSpeed;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.NTemplate;
import com.neurotec.biometrics.NTemplateSize;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.io.NBuffer;
import com.neurotec.lang.NObject;

@RestController
@RequestMapping(value = "/fingermatching")

public class FingerMatchingApi {
	protected final Log logged = LogFactory.getLog(this.getClass());
	@Autowired
	BiometricConfig config;
	@Autowired
	BiometricLicenseManager licenseManager;
	@Autowired
	private EuMembreService membreService;
	@Autowired
	private EuMembreMoraleService membreMoraleService;
	@Autowired
	private EuRepresentationService representationService;
	@Autowired
	private EuConfirmationService confirmationService;
	@Autowired
	private EuProcurationService procurationService;

	Boolean verifierDoublons = false;

	// obtenir les licenses
	private void obtainLicense() {
		licenseManager.obtainMatchingLicense();
		licenseManager.obtainExtractionLicense();
		// licenseManager.obtainScanningLicense();
		// licenseManager.obtainMediaLicense();
	}

	private void releaseLicense() {
		licenseManager.releaseMatchingLicense();
		licenseManager.releaseExtractionLicense();

		// licenseManager.releaseMediaLicense();
	}

	private void dispose(NObject... objects) {
		for (NObject o : objects) {
			if (o != null) {
				o.dispose();
			}
		}
	}

	private NBiometricClient createBiometricClient() {
		if (!config.isMatchingServiceEnabled()) {
			logged.debug("Biometric Enrollment, Identification, and Matching");
		}
		NBiometricClient client = new NBiometricClient();
		client.setMatchingThreshold(config.getMatchingThreshold());
		client.setFingersMatchingSpeed(NMatchingSpeed.valueOf(config.getMatchingSpeed().name()));
		client.setFingersTemplateSize(NTemplateSize.valueOf(config.getTemplateSize().name()));
		return client;
	}

	// Converting a bytes array to string of hex character
	public String byteArrayToHexString(byte[] b) {
		int len = b.length;
		String data = new String();
		for (int i = 0; i < len; i++) {
			data += Integer.toHexString((b[i] >> 4) & 0xf);
			data += Integer.toHexString(b[i] & 0xf);
		}
		return data.toUpperCase();
	}

	public byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * @return converts a BiometricSubject to an NSubject // TODO: Unclear how the
	 *         type and format should be applied here
	 */
	private NSubject createSubject(BiometricSubject biometricSubject) {
		NSubject subject = new NSubject();
		NFTemplate compositeTemplate = null;
		if (!biometricSubject.getStringTemplates().isEmpty()) {
			try {
				compositeTemplate = new NFTemplate();
				for (String templateToRecord : biometricSubject.getStringTemplates()) {
					if (StringUtils.isNotBlank(templateToRecord)) {
						NTemplate template = null;
						try {
							byte[] templateBytes = hexStringToByteArray(templateToRecord);
							template = new NTemplate(new NBuffer(templateBytes));
							if (template.getFingers() != null) {

								for (NFRecord record : template.getFingers().getRecords()) {
									compositeTemplate.getRecords().add(record);
								}
							}
						} finally {
							dispose(template);
						}
					}
				}
				subject.setTemplateBuffer(compositeTemplate.save());
			} finally {
				dispose(compositeTemplate);
			}
		}
		// This needs to come last, or it gets reset
		/*
		 * if (biometricSubject.getSubjectId() != null) {
		 * subject.setId(biometricSubject.getSubjectId()); }
		 */

		return subject;
	}

	/**
	 * @return a List of BiometricsMatch that match the given biometricSubject,
	 *         along with information on the match quality
	 */
	public List<BiometricMatch> identify(BiometricSubject biometricSubject) {
		List<BiometricMatch> ret = new ArrayList<BiometricMatch>();

		logged.debug("Identifying Matches for source template...");

		NBiometricClient client = null;
		NSubject subject = null;

		obtainLicense();
		try {
			client = createBiometricClient();
			subject = createSubject(biometricSubject);
			NBiometricStatus status = client.identify(subject);

			if (status == NBiometricStatus.OK) {
				logged.debug("Found " + subject.getMatchingResults().size() + " possible matches");
				for (NMatchingResult result : subject.getMatchingResults()) {
					ret.add(new BiometricMatch(result.getId(), result.getScore()));
				}
			} else if (status == NBiometricStatus.MATCH_NOT_FOUND) {
				logged.debug("No match found");
			} else {
				logged.warn("Identification failed. Status: " + status);
			}
		} finally {
			releaseLicense();
			dispose(subject, client);
		}

		return ret;
	}

	// verifier avec les enregistrements de eu_membre
	/**
	 * @return a boolean value that match the given Subject, along with all records
	 *         of database
	 */
	public Boolean verify(NSubject currentSubject, NSubject recordedSubject) {
		// List<BiometricMatch> ret = new ArrayList<BiometricMatch>();

		logged.debug("verifying Matches for source template...");
		Boolean verify = false;
		NBiometricClient client = null;

		// obtainLicense();
		try {
			client = createBiometricClient();
			NBiometricStatus status = client.verify(currentSubject, recordedSubject);
			logged.debug("status= " + status);
			if (status == NBiometricStatus.OK) {
				logged.error("Found " + currentSubject.getMatchingResults().size() + " possible matches");
				verify = true;// il y a doublons

			} else if (status == NBiometricStatus.MATCH_NOT_FOUND) {
				logged.error("No match found");
				verify = false;// il n y a pas doublons

			} else {
				logged.error("Identification failed. Status: " + status);
				verify = false;
			}
		} finally {
			// releaseLicense();
			// dispose(client);
		}

		return verify;
	}

	/**
	 * @return an NSubject from records of eu_membre // TODO: Unclear how the type
	 *         and format should be applied here
	 */
	private NSubject createSubjectFromMembre(byte[] empreinte) {
		NSubject subject = new NSubject();
		NFTemplate compositeTemplate = null;

		String template1 = byteArrayToHexString(empreinte);

		BiometricSubject biometricSubject = new BiometricSubject();
		biometricSubject.addStringTemplate(template1);

		if (!biometricSubject.getStringTemplates().isEmpty()) {
			try {
				compositeTemplate = new NFTemplate();
				for (String templateToMatch : biometricSubject.getStringTemplates()) {
					if (StringUtils.isNotBlank(templateToMatch)) {
						NTemplate template = null;
						try {
							byte[] templateBytes = hexStringToByteArray(templateToMatch);
							template = new NTemplate(new NBuffer(templateBytes));
							if (template.getFingers() != null) {
								for (NFRecord record : template.getFingers().getRecords()) {
									compositeTemplate.getRecords().add(record);
								}
							}
						} finally {
							dispose(template);
						}
					}
				}
				subject.setTemplateBuffer(compositeTemplate.save());
			} finally {
				dispose(compositeTemplate);
			}
		}
		// This needs to come last, or it gets reset
		/*
		 * if (biometricSubject.getSubjectId() != null) {
		 * subject.setId(biometricSubject.getSubjectId()); }
		 */

		return subject;
	}

	// API SAUVEGARDER EN BASE
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/savetobase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Result> saveMembreRecords(@RequestBody BiometricSubject biometricSubject) throws IOException {
		EuMembre euMembre = null;
		obtainLicense();
		// recuperer le subject a partir de BiometricSubject
		String codeMembre = biometricSubject.getCodeMembre();
		if (StringUtils.isNotBlank(codeMembre)) {
			if (codeMembre.endsWith("P")) {
				euMembre = membreService.findById(codeMembre);
			} else {
				releaseLicense();
				return new ResponseEntity<>(new Result(1, "Interface reservée aux personnes physiques"),
						HttpStatus.CREATED);
			}
		}
		if (euMembre != null) {
			if (euMembre.getDesactiver() == 0) {
				releaseLicense();
				return new ResponseEntity<>(new Result(2, "DEJA REACTIVE"), HttpStatus.CREATED);
			} else if (euMembre.getDesactiver() == 2) {
				releaseLicense();
				return new ResponseEntity<>(new Result(3, "LE MEMBRE N A PAS FAIT LA DEMANDE DE CARTE"),
						HttpStatus.CREATED);
			} else if (euMembre.getDesactiver() == 3) {
				verifierDoublons = rechercheDoublons(biometricSubject);
				if (verifierDoublons == true) {
					releaseLicense();
					return new ResponseEntity<>(new Result(4, "DOUBLONS!!!"), HttpStatus.CREATED);
				} else {
					euMembre.setEmpreinte1(hexStringToByteArray(biometricSubject.getStringTemplates().get(0)));
					euMembre.setDesactiver(Integer.valueOf(0));
					membreService.add(euMembre);
					releaseLicense();
					return new ResponseEntity<>(new Result(5, "OPERATION BIEN EFFECTUEE!!!"), HttpStatus.CREATED);

				}
			}

		}
		releaseLicense();
		return new ResponseEntity<>(new Result(6, "MEMBRE NON TROUVE"), HttpStatus.CREATED);

	}

	// confirmer une operation
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/confirmerauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Result> operationConfirmationAuthentification(@RequestBody Confirmation confirmation)
			throws IOException {

		EuMembre euMembre = null;
		EuMembreMorale euMembreMorale = null;
		obtainLicense();
		NSubject subjectToMatch = null;
		NSubject subjectMembre = null;
		NSubject subjectFromCard = null;
		Boolean matcher = false;
		// recuperer le subject a partir de confirmation
		String codeMembre = confirmation.getCodeMembre();

		if (confirmation.getProcuration() == 1) {
			EuProcuration euProcuration = procurationService.findProcurationByCodeMembre(codeMembre);
			if (euProcuration != null) {
				String codeMembreMandataire = euProcuration.getCodeMembreMandataire();
				if (StringUtils.isNotBlank(codeMembreMandataire)) {
					euMembre = membreService.findById(codeMembreMandataire);
				}
			} else {
				return new ResponseEntity<>(new Result(1, "Il n y a pas de procuration pour ce membre!!!"),
						HttpStatus.CREATED);
			}

		} else {

			if (StringUtils.isNotBlank(codeMembre)) {
				if (codeMembre.endsWith("P")) {
					euMembre = membreService.findById(codeMembre);
				} else {
					euMembreMorale = membreMoraleService.findById(codeMembre);
					if (euMembreMorale != null) {
						String codeMembreRepresentant = representationService
								.findByMoraleAndEtat(euMembreMorale.getCodeMembreMorale(), "inside").getEuMembre()
								.getCodeMembre();
						euMembre = membreService.findById(codeMembreRepresentant);
					}
				}
			}
		}
		try {
			if (euMembre != null && euMembre.getDesactiver() == 0) {
				BiometricSubject biometricSubject = new BiometricSubject();
				List<String> stringTemplates = new ArrayList<>();
				stringTemplates.add(confirmation.getStringTemplate());
				biometricSubject.setStringTemplates(stringTemplates);
				subjectToMatch = createSubject(biometricSubject);

				BiometricSubject biometricCardSubject = new BiometricSubject();
				List<String> stringCardTemplates = new ArrayList<>();
				stringCardTemplates.add(confirmation.getStringCardTemplate());
				biometricCardSubject.setStringTemplates(stringCardTemplates);
				subjectFromCard = createSubject(biometricCardSubject);

				boolean matcher1 = verify(subjectToMatch, subjectFromCard);

				if (matcher1 == false) {

					dispose(subjectToMatch, subjectFromCard);
					releaseLicense();
					return new ResponseEntity<>(new Result(1, "La carte ne vous appartient pas!!!"),
							HttpStatus.CREATED);
				} else {

					if (euMembre.getEmpreinte1() != null) {
						subjectMembre = createSubjectFromMembre(euMembre.getEmpreinte1());

						matcher = verify(subjectToMatch, subjectMembre);

						if (matcher) {
							// c est bon enregistrement
							try {
								EuConfirmation euConfirmation = confirmationService.getConfirmationEnCours(codeMembre);

								if (euConfirmation != null) {
									if (euConfirmation.getStatus() == 2 || euConfirmation.getStatus() == 4) {
										dispose(subjectMembre, subjectToMatch);
										releaseLicense();
										return new ResponseEntity<>(new Result(2, "SESSION DE CONFIRMATION EXPIREE"),
												HttpStatus.CREATED);
									} else {

										euConfirmation.setStatus(2);
										confirmationService.update(euConfirmation);
										dispose(subjectMembre, subjectToMatch);
										releaseLicense();
										return new ResponseEntity<>(new Result(3, "CONFIRMATION BIEN EFFECTUEE"),
												HttpStatus.CREATED);
									}
								} else {
									dispose(subjectMembre, subjectToMatch);
									releaseLicense();
									// c est pas le meme pas d enregistrements
									logged.error("Aucune demande de confirmation");
									return new ResponseEntity<>(new Result(4, "Aucune demande de confirmation!"),
											HttpStatus.CREATED);

								}
							} catch (Exception e) {
								logged.error("Erreur de récupération de la demande de confirmation", e);
								dispose(subjectMembre, subjectToMatch);
								releaseLicense();
							}
						} else {
							logged.error("ECHEC DE CONFIRMATION");
							dispose(subjectMembre, subjectToMatch);
							releaseLicense();
							return new ResponseEntity<>(new Result(5, "ECHEC DE CONFIRMATION"), HttpStatus.CREATED);
						}
					}
				}

			}
			dispose(subjectMembre, subjectToMatch);
			logged.error("MEMBRE PAS ENCORE REACTIVE ");
			releaseLicense();
			return new ResponseEntity<>(new Result(6, "MEMBRE PAS ENCORE REACTIVE "), HttpStatus.CREATED);

		} catch (Exception e) {
			dispose(subjectMembre, subjectToMatch);
			releaseLicense();
			logged.error("IMPOSSIBLE D EFFECTUER L OPERATION", e);
			return new ResponseEntity<>(new Result(7, "IMPOSSIBLE D EFFECTUER L OPERATION"), HttpStatus.CREATED);
		}

	}

	// recuperer
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/recupnomprenom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Result> recuperationInfosMembre(@RequestBody Membre membre) throws IOException {
		String codeMembre = membre.getCodeMembre();
		if (StringUtils.isNotBlank(codeMembre)) {
			EuMembre euMembre = membreService.findById(codeMembre);
			if (euMembre != null) {
				String nomPrenom = euMembre.getNomMembre() + " " + euMembre.getPrenomMembre();
				return new ResponseEntity<>(new Result(0, nomPrenom), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Result(1, "ERREUR DE RECUPERATION DES INFOS DU MEMBRE"),
						HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>(new Result(2, "LE CODE MEMBRE EST INVALIDE "), HttpStatus.CREATED);

	}

	public synchronized Boolean rechercheDoublons(BiometricSubject biometricSubject) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					// le sujet principal
					NSubject subjectToSave = createSubject(biometricSubject);
					// recherche de doublons
					List<byte[]> listEmpreintes = membreService.getListActivatedEmpreintes();
					while (verifierDoublons) {

						if (!listEmpreintes.isEmpty()) {
							for (byte[] empreinteActive : listEmpreintes) {

								if (empreinteActive != null) {

									NSubject subjectMembre = createSubjectFromMembre(empreinteActive);
									if (subjectMembre != null) {
										while (verifierDoublons == false) {
											verifierDoublons = verify(subjectToSave, subjectMembre);
										}
									}
								}
							}
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			};
		});
		thread.setDaemon(true);
		thread.start();

		return verifierDoublons;
	}

}
