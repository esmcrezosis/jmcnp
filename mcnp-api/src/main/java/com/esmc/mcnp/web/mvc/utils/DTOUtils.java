package com.esmc.mcnp.web.mvc.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.components.RechercheService;
import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.model.ksu.EuCarte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.others.EuCours;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.acteur.EuFiliere;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.cm.EuRepresentation;
import com.esmc.mcnp.dto.acteur.Acteur;
import com.esmc.mcnp.dto.acteur.Consultation;
import com.esmc.mcnp.dto.acteur.Filiere;
import com.esmc.mcnp.dto.acteur.Membre;
import com.esmc.mcnp.dto.acteur.MembreMorale;
import com.esmc.mcnp.dto.echange.DetailDomicilie;
import com.esmc.mcnp.dto.ksu.Compte;
import com.esmc.mcnp.dto.obps.ArticleStocke;
import com.esmc.mcnp.dto.other.Cour;
import com.esmc.mcnp.web.mvc.dto.Carte;
import com.esmc.mcnp.web.mvc.dto.CarteInfo;

/**
 *
 * @author Mawuli
 */
@Component
public class DTOUtils {

	private Consultation consultation;
	@Inject
	private RechercheService recherche;

	public DTOUtils() {
	}

	public DTOUtils(Consultation consultation) {
		this.consultation = consultation;
	}

	public List<Compte> getComptesByMembre(String codeMembre) {
		List<Compte> comptes = new ArrayList<>();
		if (!codeMembre.isEmpty()) {
			List<EuCompte> s_comptes = recherche.findCompteByMembre(codeMembre);
			Compte compte;
			for (EuCompte c : s_comptes) {
				if (codeMembre.endsWith("P")) {
					compte = new Compte(c.getCodeCompte(), membreMapper(c.getEuMembre()), c.getSolde());
					comptes.add(compte);
				} else {
					compte = new Compte(c.getCodeCompte(), membreMoralMapper(c.getEuMembreMorale()), c.getSolde());
					comptes.add(compte);
				}
			}
		}
		return comptes;
	}

	public Membre membreMapper(EuMembre memb) {
		Membre membre = null;
		if (memb != null) {
			membre = new Membre();
			membre.setCodeMembre(memb.getCodeMembre());
			membre.setNomMembre(memb.getNomMembre());
			membre.setPrenomMembre(memb.getPrenomMembre());
			membre.setProfession(memb.getProfessionMembre());
			membre.setPortable(memb.getPortableMembre());
			membre.setEmail(memb.getEmailMembre());
			membre.setAutoEnroler(memb.getAutoEnroler());
			membre.setEmpreinte1(memb.getEmpreinte1());
			membre.setEmpreinte2(memb.getEmpreinte2());
			membre.setEmpreinte3(memb.getEmpreinte3());
			membre.setEmpreinte4(memb.getEmpreinte4());
			membre.setEmpreinte5(memb.getEmpreinte5());
			membre.setEmpreinte6(memb.getEmpreinte6());
			membre.setEmpreinte7(memb.getEmpreinte7());
			membre.setEmpreinte8(memb.getEmpreinte8());
			membre.setEmpreinte9(memb.getEmpreinte9());
			membre.setEmpreinte10(memb.getEmpreinte10());
			membre.setEmpreinte11(memb.getEmpreinte11());
			membre.setEmpreinte12(memb.getEmpreinte12());
			//membre.setPhotompp(memb.getPhotompp());
			// membre.setIdUtilisateur(memb.getIdUtilisateur().longValue());
		}
		return membre;
	}

	public MembreMorale membreMoralMapper(EuMembreMorale memb) {
		MembreMorale membre = null;
		if (memb != null) {
			membre = new MembreMorale();
			membre.setCodeMembreMorale(memb.getCodeMembreMorale());
			membre.setRaisonSociale(memb.getRaisonSociale());
			membre.setDomaineActivite(memb.getDomaineActivite());
			membre.setNumRegistreMembre(memb.getNumRegistreMembre());
			membre.setPortableMembre(memb.getPortableMembre());
			membre.setEmailMembre(memb.getEmailMembre());
			membre.setSiteWeb(memb.getSiteWeb());
			membre.setQuartierMembre(memb.getQuartierMembre());
			membre.setVilleMembre(memb.getVilleMembre());
			membre.setIdUtilisateur(memb.getIdUtilisateur());
			if (memb.getEuRepresentations() != null && memb.getEuRepresentations().size() > 0) {
				for (EuRepresentation rep : memb.getEuRepresentations()) {
					membre.getRepresentants().add(membreMapper(rep.getEuMembre()));
				}
			}
			if (memb.getIdFiliere() != null) {
				membre.setIdFiliere(memb.getIdFiliere());
			}
		}
		return membre;
	}

	public static byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			ImageIO.write(image, type, out);
			return out.toByteArray();
		}
	}

	public static final byte[] get(String url, Map<String, String> headers) {
		try {
			URLConnection conn = new URL(url).openConnection();
			if (headers != null) {
				headers.entrySet().forEach((entry) -> {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				});
			}
			byte[] result;
			try (InputStream is = conn.getInputStream()) {
				result = IOUtils.toByteArray(is);
			}
			List<String> header = conn.getHeaderFields().get("Content-Disposition");
			if (header != null && header.size() > 0) {
				headers.put("Content-Disposition", header.get(0));
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] fetchRemoteFile(String location) throws Exception {
		URL url = new URL(location);
		InputStream is = null;
		byte[] bytes = null;
		is = url.openStream(); // handle errors
		bytes = IOUtils.toByteArray(is);
		if (is != null) {
			is.close();
		}
		return bytes;
	}

	public static byte[] fetchLocalFile(String location) {
		File file = new File(location);
		byte[] result = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			BufferedImage img = ImageIO.read(file);
			ImageIO.write(img, "jpg", baos);
			baos.flush();
			result = baos.toByteArray();
		} catch (IOException ex) {
		}
		return result;
	}

	public static Carte mapEuCarte(EuCarte euCarte) {
		Carte carte = new Carte();
		if (Objects.nonNull(euCarte.getEuMembre())) {
			carte.setCodeMembre(euCarte.getEuMembre().getCodeMembre());
		} else {
			carte.setCodeMembre(euCarte.getEuMembreMorale().getCodeMembreMorale());
		}
		carte.setDateDemande(euCarte.getDateDemande());
		carte.setDateLivraison(euCarte.getDateLivraison());
		carte.setDateImpression(euCarte.getDateImpression());
		carte.setIdUtilisateur(euCarte.getIdUtilisateur());
		carte.setImprimer(euCarte.isImprimer());
		carte.setLivrer(euCarte.isLivrer());
		// carte.setImage(Base64.getEncoder().encodeToString(fetchLocalFile(euCarte.getUrlCarte())));
		return carte;
	}

	public static CarteInfo mapEuCarteToInfo(EuCarte euCarte) {
		CarteInfo carte = new CarteInfo();
		if (Objects.nonNull(euCarte.getEuMembre())) {
			carte.setCodeMembre(euCarte.getEuMembre().getCodeMembre());
			carte.setNomMembre(euCarte.getEuMembre().getNomMembre());
			carte.setPrenomMembre(euCarte.getEuMembre().getPrenomMembre());
			carte.setContact(euCarte.getEuMembre().getPortableMembre());
		} else {
			carte.setCodeMembre(euCarte.getEuMembreMorale().getCodeMembreMorale());
			carte.setNomMembre(euCarte.getEuMembreMorale().getRaisonSociale());
			carte.setContact(euCarte.getEuMembreMorale().getPortableMembre());
		}
		carte.setDateDemande(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(euCarte.getDateDemande()));
		carte.setIdCarte(euCarte.getIdCarte());
		carte.setImprimer(euCarte.isImprimer());
		carte.setLivrer(euCarte.isLivrer());
		if (StringUtils.isNotBlank(euCarte.getUrlCarte())) {
			File file = new File(euCarte.getUrlCarte());
			carte.setUrl(file.getName());
		}
		carte.setIdUtilisateur(euCarte.getIdUtilisateur());
		if (StringUtils.isNotBlank(euCarte.getUrlCarte())) {
			try {
				carte.setImage(Base64.getEncoder().encodeToString(fetchLocalFile(euCarte.getUrlCarte())));
			} catch (Exception e) {
			}
		}
		return carte;
	}

	public Membre getMembre() {
		Membre membre;
		EuMembre memb;
		if (consultation.getCodeMembre().endsWith("P")) {
			if (consultation.getMethodeConsultation().equals("ID")) {
				memb = recherche.findMembreById(consultation.getCodeMembre());
				membre = membreMapper(memb);
			} else {
				memb = recherche.findMembreByNomPrenom(consultation.getNomMembre(), consultation.getPrenomMembre());
				membre = membreMapper(memb);
			}
			return membre;
		}
		return null;
	}

	public MembreMorale getMembreMorale() {
		EuMembreMorale moral;
		MembreMorale m_moral = null;
		if (consultation.getCodeMembre().endsWith("M")) {
			System.out.println("Traitement de :" + consultation.getCodeMembre());
			if (consultation.getMethodeConsultation().equals("ID")) {
				moral = recherche.findMembreMoraleById(consultation.getCodeMembre());
				System.out.println("Traitement de :" + moral.getCodeMembreMorale() + " " + moral.getRaisonSociale());
			} else {
				moral = recherche.findByRaisonSociale(consultation.getRaisonSociale());
			}
			if (moral != null) {
				List<EuRepresentation> reps = recherche.fetchAllByMembreMoral(moral.getCodeMembreMorale());
				if (reps != null && reps.size() > 0) {
					moral.setEuRepresentations(reps);
					m_moral = membreMoralMapper(moral);
				}
			}
		}
		return m_moral;
	}

	public List<Compte> getComptes() {
		if (consultation.getTypeConsultation().equals("CS") && consultation.getMethodeConsultation().equals("CM")) {
			List<Compte> comptes = new ArrayList<>();
			List<EuCompte> s_comptes = recherche.findCompteByMembre(consultation.getCodeMembre());
			if (s_comptes != null && s_comptes.size() > 0) {
				Compte compte;
				for (EuCompte c : s_comptes) {
					if (c.getEuMembre() != null) {
						compte = new Compte(c.getCodeCompte(), membreMapper(c.getEuMembre()), c.getSolde());
					} else {
						compte = new Compte(c.getCodeCompte(), membreMoralMapper(c.getEuMembreMorale()), c.getSolde());
					}
					comptes.add(compte);
				}
				return comptes;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Compte getCompte() {
		Compte compte = null;
		EuCompte c_compte;
		switch (consultation.getMethodeConsultation()) {
		case "ID":
			System.out.println(consultation.getCodeCompte());
			c_compte = recherche.findCompteById(consultation.getCodeCompte());
			if (c_compte != null) {
				String codeMembre = ServerUtil.getCodeMembre(c_compte.getCodeCompte());
				if (codeMembre.endsWith("P")) {
					EuMembre membre = recherche.findMembreById(codeMembre);
					if (membre != null) {
						System.out.println(membre.getCodeMembre());
						compte = new Compte(c_compte.getCodeCompte(), membreMapper(membre), c_compte.getSolde());
					}
				} else {
					EuMembreMorale membre = recherche.findMembreMoraleById(codeMembre);
					if (membre != null) {
						System.out.println(membre.getCodeMembreMorale());
						List<EuRepresentation> reps = recherche.fetchAllByMembreMoral(membre.getCodeMembreMorale());
						System.out.println(reps.size());
						if (reps.size() > 0) {
							membre.setEuRepresentations(reps);
						}
						compte = new Compte(c_compte.getCodeCompte(), membreMoralMapper(membre), c_compte.getSolde());
					}
				}
				return compte;
			} else {
				return null;
			}
		case "NC":
			System.out.println(consultation.getNumeroCarte());
			c_compte = recherche.findCompteByNumCarte(consultation.getNumeroCarte());
			if (c_compte != null) {
				String codeMembre = ServerUtil.getCodeMembre(c_compte.getCodeCompte());
				if (String.valueOf(codeMembre.charAt(codeMembre.length() - 1)).equals("P")) {
					EuMembre membre = recherche.findMembreById(codeMembre);
					compte = new Compte(c_compte.getCodeCompte(), membreMapper(membre), c_compte.getSolde());
				} else {
					EuMembreMorale membre = recherche.findMembreMoraleById(codeMembre);
					if (membre != null) {
						System.out.println(membre.getCodeMembreMorale());
						List<EuRepresentation> reps = recherche.fetchAllByMembreMoral(membre.getCodeMembreMorale());
						if (reps.size() > 0) {
							membre.setEuRepresentations(reps);
						}
						compte = new Compte(c_compte.getCodeCompte(), membreMoralMapper(membre), c_compte.getSolde());
					}
				}
				return compte;
			} else {
				return null;
			}
		default:
			return null;
		}
	}

	public Object getTraitement() {
		switch (consultation.getTypeConsultation()) {
		case "CM":
			if (consultation.getCodeMembre().endsWith("P")) {
				Membre membre = getMembre();
				if (membre != null) {
					return membre;
				} else {
					return "Le membre demand� n'existe pas!!!";
				}
			} else {
				MembreMorale moral = getMembreMorale();
				if (moral != null) {
					return moral;
				} else {
					return "Le membre demand� n'existe pas!!!";
				}
			}
		case "CNC":
			System.out.println("Recup�ration du compte : ");
			Compte compte = getCompte();
			if (compte != null) {
				System.out.println("cpmte= " + compte.getCodeCompte());
				return compte;
			} else {
				return "Cette carte ne correspond pas a un compte marchand !!!";
			}
		default:
			return null;

		}

	}

	public Compte mapCompte(EuCompte compte) {
		Compte cpte = new Compte();
		cpte.setCodeCompte(compte.getCodeCompte());
		cpte.setSolde(compte.getSolde());
		if (compte.getEuMembre() != null) {
			cpte.setMembre(membreMapper(compte.getEuMembre()));
		} else {
			cpte.setMorale(membreMoralMapper(compte.getEuMembreMorale()));
		}
		cpte.setCodeCategorie(compte.getEuCategorieCompte().getCodeCat());

		return cpte;
	}

	public Acteur mapActeur(EuActeur euacteur) {
		Acteur acteur = new Acteur();
		acteur.setIdActeur(euacteur.getIdActeur());
		acteur.setTypeActeur(euacteur.getTypeActeur());
		acteur.setCodeActivite(euacteur.getCodeActivite());

		return acteur;
	}

	public ArticleStocke mapArticle(EuArticleStockes articlestocke) {
		ArticleStocke article = new ArticleStocke();
		article.setCodeBarre(articlestocke.getCodeBarre());
		article.setDesignation(articlestocke.getDesignation());
		article.setPrix(articlestocke.getPrix());
		article.setReference(articlestocke.getReference());
		article.setQuantite(articlestocke.getQteSolde());
		return article;
	}

	public Cour mapCours(EuCours eucour) {
		Cour cour = new Cour();
		cour.setCodeCours(eucour.getCodeCours());
		cour.setValDevFin(eucour.getValDevFin());
		cour.setValDevInit(eucour.getValDevInit());
		return cour;
	}

	public Filiere mapFiliere(EuFiliere eufiliere) {
		Filiere filiere = new Filiere();
		filiere.setNomFiliere(eufiliere.getNomFiliere());
		return filiere;
	}

	public DetailDomicilie detailDomicilieMapper(EuDetailDomicilie euDetailDomicilie) {
		DetailDomicilie detailDomicilie = new DetailDomicilie();
		detailDomicilie.setDureeRenouvellement(euDetailDomicilie.getDureeRenouvellement());
		detailDomicilie.setIdCredit(euDetailDomicilie.getEuCompteCredit().getIdCredit());
		detailDomicilie.setMontantCredit(euDetailDomicilie.getMontantCredit());
		return detailDomicilie;
	}

}
