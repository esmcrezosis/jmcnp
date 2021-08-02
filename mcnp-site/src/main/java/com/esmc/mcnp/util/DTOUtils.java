/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.domain.dto.acteur.Acteur;
import com.esmc.mcnp.domain.dto.acteur.Consultation;
import com.esmc.mcnp.domain.dto.acteur.Filiere;
import com.esmc.mcnp.domain.dto.acteur.Membre;
import com.esmc.mcnp.domain.dto.acteur.MembreMorale;
import com.esmc.mcnp.domain.dto.echange.DetailDomicilie;
import com.esmc.mcnp.domain.dto.ksu.Compte;
import com.esmc.mcnp.domain.dto.obps.ArticleStocke;
import com.esmc.mcnp.domain.dto.other.Cour;
import com.esmc.mcnp.domain.entity.acteur.EuActeur;
import com.esmc.mcnp.domain.entity.acteur.EuFiliere;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomicilie;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuRepresentation;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockes;
import com.esmc.mcnp.domain.entity.others.EuCours;
import com.esmc.mcnp.infrastructure.components.RechercheService;

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

    public Membre membreMapper(EuMembre euMembre) {
        Membre membre = null;
        if (euMembre != null) {
            membre = new Membre();
            membre.setCodeMembre(euMembre.getCodeMembre());
            membre.setNomMembre(euMembre.getNomMembre());
            membre.setPrenomMembre(euMembre.getPrenomMembre());
            membre.setProfession(euMembre.getProfessionMembre());
            membre.setPortable(euMembre.getPortableMembre());
            membre.setEmail(euMembre.getEmailMembre());
            membre.setAutoEnroler(euMembre.getAutoEnroler());
            membre.setEmpreinte1(euMembre.getEmpreinte1());
            membre.setEmpreinte2(euMembre.getEmpreinte2());
            membre.setEmpreinte3(euMembre.getEmpreinte3());
            membre.setEmpreinte4(euMembre.getEmpreinte4());
            membre.setEmpreinte5(euMembre.getEmpreinte5());
            membre.setEmpreinte6(euMembre.getEmpreinte6());
            membre.setEmpreinte7(euMembre.getEmpreinte7());
            membre.setEmpreinte8(euMembre.getEmpreinte8());
            membre.setEmpreinte9(euMembre.getEmpreinte9());
            membre.setEmpreinte10(euMembre.getEmpreinte10());
            membre.setEmpreinte11(euMembre.getEmpreinte11());
            membre.setEmpreinte12(euMembre.getEmpreinte12());
            //membre.setPhotompp(euMembre.getPhotompp());
            //membre.setIdUtilisateur(memb.getIdUtilisateur().longValue());
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

    public Membre getMembre() {
        Membre membre = null;
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
        return membre;
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
