/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.mvc.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.commons.util.DateUtility;
import com.esmc.mcnp.domain.entity.acteur.EuAssociation;
import com.esmc.mcnp.domain.entity.acteur.EuMembreasso;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuRepresentation;
import com.esmc.mcnp.domain.entity.cm.EuRepresentationPK;
import com.esmc.mcnp.domain.entity.ksu.EuCarte;
import com.esmc.mcnp.domain.entity.ksu.EuKacm;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.components.CarteComponent;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.cm.EuRepresentationService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuAssociationService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuMembreassoService;
import com.esmc.mcnp.infrastructure.services.common.EuKacmService;
import com.esmc.mcnp.infrastructure.services.ksu.EuCarteService;
import com.esmc.mcnp.infrastructure.services.obps.EuTegcService;
import com.esmc.mcnp.infrastructure.services.security.EuUtilisateurService;
import com.esmc.mcnp.web.mvc.dto.Carte;
import com.esmc.mcnp.web.mvc.dto.CarteInfo;
import com.esmc.mcnp.web.mvc.dto.CarteRequest;
import com.esmc.mcnp.web.mvc.dto.InfoMembre;
import com.esmc.mcnp.web.mvc.dto.InfoMembreMorale;
import com.esmc.mcnp.web.mvc.dto.Result;
import com.esmc.mcnp.web.mvc.dto.User;
import com.esmc.mcnp.web.mvc.utils.ImageUtils;
import com.google.common.collect.Lists;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping(value = "/carte")
public class CarteController {

	private final EuMembreService membreService;
	private final EuMembreMoraleService moraleService;
	private final EuCarteService carteService;
	private final EuUtilisateurService userService;
	private final EuBonNeutreDetailService bnDetService;
	private final EuMembreassoService membreAssoService;
	private final EuRepresentationService repService;
	private final EuAssociationService assoService;
	private final EuBonNeutreService bonNeutreService;
	private final CarteComponent carteComponent;
	private final EuKacmService kacmService;
	private final EuTegcService tegcService;
	@Value("${file.url}")
	private String baseUrl;

	private final Logger log = LogManager.getLogger(CarteController.class.getName());

	@Autowired
	public CarteController(EuMembreService membreService, EuCarteService carteService, EuUtilisateurService userService,
			EuBonNeutreDetailService bnDetService, EuMembreassoService membreAssoService, CarteComponent carteComponent,
			EuMembreMoraleService moraleService, EuRepresentationService repService, EuAssociationService assoService,
			EuBonNeutreService bonNeutreService, EuKacmService kacmService, EuTegcService tegcService) {
		this.membreService = membreService;
		this.carteService = carteService;
		this.userService = userService;
		this.bnDetService = bnDetService;
		this.membreAssoService = membreAssoService;
		this.moraleService = moraleService;
		this.repService = repService;
		this.assoService = assoService;
		this.bonNeutreService = bonNeutreService;
		this.carteComponent = carteComponent;
		this.kacmService = kacmService;
		this.tegcService = tegcService;
	}

	@GetMapping(value = "/membre/{codeMembre}")
	public ResponseEntity<InfoMembre> findMembreInfo(@PathVariable("codeMembre") String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			EuMembre euMembre = membreService.findByCodeMembre(codeMembre);
			List<EuCarte> cartes = carteService.findByCodeMembre(codeMembre);
			if (Objects.nonNull(euMembre)) {
				InfoMembre info = InfoMembre.mapFromEuMembre(euMembre);
				Calendar calendar = new GregorianCalendar(2018, 8, 2);
				double somBan = bnDetService.getSumByCodeMembreAndDate(codeMembre, calendar.getTime());
				if (somBan > 0) {
					info.setAutoriser(true);
				} else {
					info.setAutoriser(false);
				}

				if (cartes != null && cartes.size() > 0) {
					info.setDoublon(true);
				} else {
					info.setDoublon(false);
				}

				return new ResponseEntity<>(info, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/morale/{codeMembre}")
	public ResponseEntity<InfoMembreMorale> findMoraleInfo(@PathVariable("codeMembre") String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			EuMembreMorale euMembre = moraleService.findByCodeMembreMorale(codeMembre);
			List<EuCarte> cartes = carteService.findByCodeMembreMorale(codeMembre);
			if (Objects.nonNull(euMembre)) {
				InfoMembreMorale info = InfoMembreMorale.mapToMorale(euMembre);
				Calendar calendar = new GregorianCalendar(2018, 8, 2);
				double somBan = bnDetService.getSumByCodeMembreAndDate(codeMembre, calendar.getTime());
				if (somBan > 0) {
					info.setAutoriser(true);
				} else {
					info.setAutoriser(false);
				}

				if (cartes != null && cartes.size() > 0) {
					info.setDoublon(true);
				} else {
					info.setDoublon(false);
				}

				return new ResponseEntity<>(info, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> loadUsers() {
		List<User> users = new ArrayList<>();
		List<EuUtilisateur> utilisateurs = userService.listAll();
		if (!utilisateurs.isEmpty()) {
			utilisateurs.forEach(u -> users.add(User.maptoUser(u)));
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping(value = "/user")
	public ResponseEntity<User> loadUser(@RequestBody String login) {
		User user = new User();
		EuUtilisateur utilisateur = userService.findByLogin(login);
		if (Objects.nonNull(utilisateur)) {
			user = User.maptoUser(utilisateur);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping(value = "/asso")
	public ResponseEntity<User> loadAsso(@RequestBody String login) {
		User user = new User();
		EuMembreasso utilisateur = membreAssoService.findByLogin(login);
		if (Objects.nonNull(utilisateur)) {
			user = User.mapToUser(utilisateur);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/creer")
	public ResponseEntity<Result> creerCarte(@RequestBody Carte carte) {
		if (StringUtils.isNotBlank(carte.getImage()) && StringUtils.isNotBlank(carte.getCodeMembre())) {
			EuMembre membre = membreService.findById(carte.getCodeMembre());
			if (Objects.nonNull(membre)) {
				List<EuCarte> cartes = carteService.findByCodeMembre(carte.getCodeMembre());
				if (cartes != null && cartes.size() > 0) {
					return new ResponseEntity<>(new Result(0,
							"Désolé, Vous ne pouvez réaliser cette opération: Ce membre a déjà fait une demande de carte"),
							HttpStatus.OK);
				}
				Calendar calendar = new GregorianCalendar(2018, 8, 2);
				double somBan = bnDetService.getSumByCodeMembreAndDate(carte.getCodeMembre(), calendar.getTime());
				if (somBan > 0) {
					double fraisCarte = carteComponent.calculKacm(membre);
					if (somBan >= fraisCarte) {
						List<EuMembre> membres = membreService.findByPortable(carte.getTelephone());
						if (membres != null) {
							if ((membres.size() == 1 && !membres.get(0).getCodeMembre().equals(carte.getCodeMembre()))
									|| membres.size() > 1) {
								return new ResponseEntity<>(new Result(0,
										"Echec de l'insertion de la carte: Le numéro Téléphone est déjà utilisé"),
										HttpStatus.OK);
							}
						}
						membres = membreService.findByEmail(carte.getEmail());
						if (membres != null) {
							if ((membres.size() == 1 && !membres.get(0).getCodeMembre().equals(carte.getCodeMembre()))
									|| membres.size() > 1) {
								return new ResponseEntity<>(
										new Result(0, "Echec de l'insertion de la carte: L'Email est déjà utilisé"),
										HttpStatus.OK);
							}
						}

						try {
							InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(carte.getImage()));
							BufferedImage bImageFromConvert = ImageIO.read(is);
							String nomImage = "";
							if (carte.getUserType().equals("user")) {
								nomImage = String.valueOf(carte.getIdUtilisateur()) + "-" + carte.getCodeMembre();
							} else {
								EuMembreasso userAsso = membreAssoService.findById(carte.getIdUtilisateur());
								nomImage = String.valueOf(userAsso.getMembreassoAssociation()) + "-"
										+ String.valueOf(carte.getIdUtilisateur()) + "-" + carte.getCodeMembre();
							}
							String url = baseUrl + nomImage + ".jpg";
							String pdf = baseUrl + nomImage + ".pdf";
							ImageIO.write(bImageFromConvert, "png", new File(url));
							ImageUtils.generatePDFFromImage(url, pdf);

							EuCarte euCarte = new EuCarte();
							euCarte.setEuMembre(membre);
							euCarte.setDateDemande(carte.getDateDemande());
							euCarte.setDateImpression(null);
							euCarte.setDateLivraison(null);
							euCarte.setIdUtilisateur(carte.getIdUtilisateur());
							euCarte.setImprimer(false);
							euCarte.setLivrer(false);
							euCarte.setUrlCarte(url);
							euCarte.setUserType(carte.getUserType());
							carteService.create(euCarte);

							List<EuTegc> tegcs = tegcService.findByCodeMembre(membre.getCodeMembre());
							if (tegcs.isEmpty()) {
								tegcService.creerTe(membre.getCodeMembre());
							}

							if (carte.isModifier()) {
								membre.setNomMembre(carte.getNomMmebre());
								membre.setPrenomMembre(carte.getPrenomMembre());
								membre.setLieuNaisMembre(carte.getLieuNais());
								membre.setPortableMembre(carte.getTelephone());
								membre.setEmailMembre(carte.getEmail());
								membre.setDateNaisMembre(DateUtility.asUtilDate(carte.getDateNais()));
							}
							membre.setDesactiver(3);
							membreService.update(membre);

							if (fraisCarte > 0) {
								EuBonNeutre bonNeutre = bonNeutreService.findByMembre(membre.getCodeMembre());
								bonNeutreService.updateBonNeutre(bonNeutre, "Activation du Compte Marchand BioCarte",
										"BA", fraisCarte);
								EuKacm kacmp = new EuKacm();
								kacmp.setCodeMembre(membre.getCodeMembre());
								kacmp.setTypeActivite("ACM");
								kacmp.setMontKacm(fraisCarte);
								kacmp.setMontOp(fraisCarte);
								kacmService.add(kacmp);
							}
							return new ResponseEntity<>(new Result(1, "Insertion de la carte bien effectuée"),
									HttpStatus.OK);
						} catch (IOException ex) {
							log.error("Echec de l'insertion de la carte: ", ex);
							return new ResponseEntity<>(
									new Result(0, "Echec de l'insertion de la carte: " + ex.getMessage()),
									HttpStatus.OK);
						}
					}
				} else {
					return new ResponseEntity<>(new Result(0,
							"Echec de l'insertion de la carte: " + carte.getCodeMembre() + " n'a pas de BAn suffisant"),
							HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(new Result(0,
						"Echec de l'insertion de la carte:  Ce Membre " + carte.getCodeMembre() + " n'existe pas"),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Result(0, "Pas de carte à inserer ou manque du code Membre"), HttpStatus.OK);

	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/creerMorale")
	public ResponseEntity<Result> creerCarteMorale(@RequestBody Carte carte) {
		if (carte.getImage() != null && StringUtils.isNotBlank(carte.getCodeMembre())) {
			EuMembreMorale morale = moraleService.findById(carte.getCodeMembre());
			List<EuCarte> cartes = carteService.findByCodeMembreMorale(carte.getCodeMembre());
			if (cartes != null && cartes.size() > 0) {
				return new ResponseEntity<>(new Result(0,
						"Désolé, Vous ne pouvez réaliser cette opération: Ce membre a déjà fait une demande de carte"),
						HttpStatus.OK);
			}
			Calendar calendar = new GregorianCalendar(2018, 8, 2);
			double somBan = bnDetService.getSumByCodeMembreAndDate(carte.getCodeMembre(), calendar.getTime());
			if (somBan > 0) {
				double fraisCarte = carteComponent.calculKacm(morale);
				if (somBan >= fraisCarte) {
					try {

						if (StringUtils.isNotBlank(carte.getCel())) {
							List<EuMembreMorale> membres = moraleService.findByPortable(carte.getCel());
							if (membres != null) {
								if ((membres.size() == 1
										&& !membres.get(0).getCodeMembreMorale().equals(carte.getCodeMembre()))
										|| membres.size() > 1) {
									return new ResponseEntity<>(new Result(0,
											"Echec de l'insertion de la carte: Le numéro Téléphone est déjà utilisé"),
											HttpStatus.OK);
								}
							}
						}
						if (StringUtils.isNotBlank(carte.getEmail())) {
							List<EuMembreMorale> membres = moraleService.findByEmail(carte.getEmail());
							if (membres != null) {
								if ((membres.size() == 1
										&& !membres.get(0).getCodeMembreMorale().equals(carte.getCodeMembre()))
										|| membres.size() > 1) {
									return new ResponseEntity<>(
											new Result(0, "Echec de l'insertion de la carte: L'Email est déjà utilisé"),
											HttpStatus.OK);
								}
							}
						}

						if (StringUtils.isNotBlank(carte.getCodeRep())) {
							EuMembre membre = membreService.findById(carte.getCodeRep());
							if (Objects.nonNull(membre) && membre.getDesactiver() != 0) {
								return new ResponseEntity<>(new Result(0,
										"Echec de l'insertion de la carte: Le Représentant principal désigné n'est pas encore activé"),
										HttpStatus.OK);
							}
						} else {
							EuRepresentation princ = repService.findByMoraleAndEtat(carte.getCodeMembre(), "inside");
							EuMembre membre = princ.getEuMembre();
							if (Objects.nonNull(membre) && membre.getDesactiver() != 0) {
								return new ResponseEntity<>(new Result(0,
										"Echec de l'insertion de la carte: Le Représentant principal n'est pas encore activé"),
										HttpStatus.OK);
							}
						}

						String nomImage = "";
						if (carte.getUserType().equals("user")) {
							nomImage = carte.getIdUtilisateur() + "-" + carte.getCodeMembre();
						} else {
							EuMembreasso userAsso = membreAssoService.findById(carte.getIdUtilisateur());
							nomImage = userAsso.getMembreassoAssociation() + "-"
									+ carte.getIdUtilisateur() + "-" + carte.getCodeMembre();
						}
						InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(carte.getImage()));
						BufferedImage bImageFromConvert = ImageIO.read(is);
						String url = baseUrl + nomImage + ".jpg";
						String pdf = baseUrl + nomImage + ".pdf";
						ImageIO.write(bImageFromConvert, "png", new File(url));
						ImageUtils.generatePDFFromImage(url, pdf);

						EuMembreMorale membre = moraleService.findById(carte.getCodeMembre());
						if (Objects.nonNull(membre)) {
							if (carte.isModifier()) {
								membre.setRaisonSociale(carte.getRaisonSociale());
								membre.setPortableMembre(carte.getCel());
								membre.setTelMembre(carte.getTelephone());
								membre.setEmailMembre(carte.getEmail());
							}
							membre.setDesactiver(0);
							moraleService.update(membre);

							List<EuTegc> tegcs = tegcService.findByCodeMembre(membre.getCodeMembreMorale());
							if (tegcs.isEmpty()) {
								tegcService.creerTe(membre.getCodeMembreMorale());
							}

							if (StringUtils.isNotBlank(carte.getCodeRep())) {
								EuRepresentation princ = repService.findByMoraleAndEtat(carte.getCodeMembre(),
										"inside");
								if (Objects.nonNull(princ)
										&& !princ.getEuMembre().getCodeMembre().equals(carte.getCodeRep())) {
									EuRepresentation rep = repService.findByMembreAndMorale(carte.getCodeMembre(),
											carte.getCodeRep());
									if (Objects.nonNull(rep)) {
										rep.setEtat("inside");
										repService.update(rep);
									} else {
										EuMembre memb = membreService.findById(carte.getCodeRep());
										EuRepresentation prep = new EuRepresentation();
										prep.setDateCreation(new Date());
										prep.setEtat("inside");
										prep.setEuMembre(memb);
										prep.setEuMembreMorale(morale);
										prep.setIdUtilisateur(carte.getIdUtilisateur());
										prep.setTitre("Representant");
										prep.setId(new EuRepresentationPK(carte.getCodeRep(), carte.getCodeMembre()));
										repService.add(prep);
									}
									princ.setEtat("outside");
									repService.update(princ);
								}
							}

							if (fraisCarte > 0) {
								EuBonNeutre bonNeutre = bonNeutreService.findByMembre(morale.getCodeMembreMorale());
								bonNeutreService.updateBonNeutre(bonNeutre, "Activation du Compte Marchand BioCarte",
										"BA", fraisCarte);
								EuKacm kacmp = new EuKacm();
								kacmp.setCodeMembre(morale.getCodeMembreMorale());
								kacmp.setTypeActivite("ACM");
								kacmp.setMontKacm(fraisCarte);
								kacmp.setMontOp(fraisCarte);
								kacmService.add(kacmp);
							}

							EuCarte euCarte = new EuCarte();
							euCarte.setEuMembreMorale(membre);
							euCarte.setEuMembre(null);
							euCarte.setDateDemande(carte.getDateDemande());
							euCarte.setDateImpression(null);
							euCarte.setDateLivraison(null);
							euCarte.setIdUtilisateur(carte.getIdUtilisateur());
							euCarte.setImprimer(false);
							euCarte.setLivrer(false);
							euCarte.setUrlCarte(url);
							euCarte.setUserType(carte.getUserType());
							carteService.create(euCarte);
							return new ResponseEntity<>(new Result(1, "Insertion de la carte bien effectuée"),
									HttpStatus.OK);
						}
					} catch (IOException ex) {
						log.error("Echec de l'insertion de la carte: ", ex);
						return new ResponseEntity<>(
								new Result(0, "Echec de l'insertion de la carte: " + ex.getMessage()), HttpStatus.OK);
					}
				}
			} else {
				return new ResponseEntity<>(new Result(0,
						"Echec de l'insertion de la carte: " + carte.getCodeMembre() + " n'a pas de BAn suffisant"),
						HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(new Result(0, "Pas de carte à inserer ou manque du code Membre"), HttpStatus.OK);

	}

	@GetMapping(value = "/list")
	public ResponseEntity<List<Carte>> loadCartes(@RequestBody CarteRequest request) {
		List<EuCarte> euCartes = Lists.newArrayList();
		List<Carte> cartes = Lists.newArrayList();
		if (Objects.isNull(request) || request.getDateDemande() == null) {
			euCartes = carteService.findByImprimer(request.isImpLivrer());
		} else {
			if (request.getDateDemande() != null && request.getDatefin() != null) {
				euCartes = carteService.findByImprimerAndDateDemandeBetween(request.isImpLivrer(),
						request.getDateDemande(), request.getDatefin());
			} else {
				euCartes = carteService.findByDateAndImprimer(request.getDateDemande(), request.isImpLivrer());
			}
		}
		if (!euCartes.isEmpty()) {
			euCartes.forEach(c -> {
				try {
					cartes.add(com.esmc.mcnp.web.mvc.utils.DTOUtils.mapEuCarte(c));
				} catch (Exception ex) {
					log.error("Mapping de Cartes", ex);
				}
			});
		}
		return new ResponseEntity<>(cartes, HttpStatus.OK);
	}

	@PostMapping(value = "/listInfo")
	public ResponseEntity<List<CarteInfo>> loadCarteInfo(@RequestBody CarteRequest request) {
		List<EuCarte> euCartes = Collections.emptyList();
		List<CarteInfo> cartes = Lists.newArrayList();
		if ("imp".equals(request.getRequestType())) {
			if (Objects.isNull(request) || request.getDateDemande() == null) {
				euCartes = carteService.findByImprimer(request.isImpLivrer());
			} else {
				if (StringUtils.isNotBlank(request.getCodeMembre())) {
					euCartes = carteService.findByCodeMembre(request.getCodeMembre());
				} else if (request.getDateDemande() != null && request.getDatefin() != null) {
					euCartes = carteService.findByImprimerAndDateDemandeBetween(request.isImpLivrer(),
							request.getDateDemande(), request.getDatefin());
				} else {
					euCartes = carteService.findByDateAndImprimer(request.getDateDemande(), request.isImpLivrer());
				}
			}
		} else {
			User user = null;
			EuMembreasso membreAss = null;
			if ("asso".equals(request.getUserType())) {
				membreAss = membreAssoService.findById(request.getIdUser());
				user = User.mapToUser(membreAss);
			} else {
				user = User.maptoUser(userService.getById(request.getIdUser()));
			}
			if (!user.getUserType().equals("asso")
					|| (user.getUserType().equals("asso") && membreAss.getMembreassoAssociation() == 1)) {

				if (Objects.isNull(request) || request.getDateDemande() == null) {
					if (Objects.isNull(request) || request.getDateDemande() == null) {
						euCartes = carteService.findByImprimer(request.isImpLivrer());
					} else {
						if (StringUtils.isNotBlank(request.getCodeMembre())) {
							euCartes = carteService.findByCodeMembre(request.getCodeMembre());
						} else if (request.getDateDemande() != null && request.getDatefin() != null) {
							euCartes = carteService.findByLivrerAndDateDemandeBetween(request.isImpLivrer(),
									request.getDateDemande(), request.getDatefin());
						} else {
							euCartes = carteService.findByDateAndLivrer(request.getDateDemande(),
									request.isImpLivrer());
						}
					}
				} else {
					if (Objects.isNull(request) || request.getDateDemande() == null) {
						euCartes = carteService.findByLivrer(request.isImpLivrer(), request.getIdUser());
					} else {
						if (StringUtils.isNotBlank(request.getCodeMembre())) {
							euCartes = carteService.findByCodeMembre(request.getCodeMembre());
						} else if (request.getDateDemande() != null && request.getDatefin() != null) {
							euCartes = carteService.findByLivrerAndDateDemandeBetween(request.isImpLivrer(),
									request.getIdUser(), request.getDateDemande(), request.getDatefin());
						} else {
							euCartes = carteService.findByDateAndLivrer(request.getDateDemande(), request.isImpLivrer(),
									request.getIdUser());
						}
					}
				}
			}
		}
		if (!euCartes.isEmpty()) {
			euCartes.forEach(c -> {
				CarteInfo cinfo = com.esmc.mcnp.web.mvc.utils.DTOUtils.mapEuCarteToInfo(c);
				if (StringUtils.isNotBlank(c.getUserType()) && c.getUserType().equalsIgnoreCase("asso")) {
					EuAssociation assoc = assoService
							.findById(membreAssoService.findById(c.getIdUtilisateur()).getMembreassoAssociation());
					cinfo.setNomAsso(assoc.getAssociationNom());
				} else {
					cinfo.setNomAsso("ESMC");
				}
				cartes.add(cinfo);
			});
		}
		return new ResponseEntity<>(cartes, HttpStatus.OK);
	}

	@Transactional
	@PostMapping(value = "/updateCarte")
	public ResponseEntity<Result> updateCarteInfo(@RequestBody CarteInfo carteInfo) {
		if (Objects.nonNull(carteInfo)) {
			EuCarte carte = carteService.getById(carteInfo.getIdCarte());
			if (Objects.nonNull(carte)) {
				if (!carte.isImprimer() || !carte.isLivrer()) {
					if (carte.isImprimer() && !carte.isLivrer()) {
						carte.setLivrer(true);
						carte.setDateLivraison(LocalDate.now());
						carteService.update(carte);
					} else {
						carte.setImprimer(true);
						carte.setDateImpression(LocalDate.now());
						carteService.update(carte);
					}
					return new ResponseEntity<>(new Result(1, "Operation bien effectuée"), HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(new Result(0, "Pas de carte à mettre à jour"), HttpStatus.OK);
	}
}
