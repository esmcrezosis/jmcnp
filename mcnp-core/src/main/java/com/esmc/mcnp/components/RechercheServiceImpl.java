package com.esmc.mcnp.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuAccessKids;
import com.esmc.mcnp.model.acteur.EuActeur;
import com.esmc.mcnp.model.op.EuAppelOffre;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.model.ba.EuCapaDeclaration;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.model.others.EuCours;
import com.esmc.mcnp.model.bc.EuCreditCodebarre;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.acteur.EuFiliere;
import com.esmc.mcnp.model.others.EuFrais;
import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.model.acteur.EuMaison;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.others.EuProposition;
import com.esmc.mcnp.model.cm.EuRepresentation;
import com.esmc.mcnp.model.obpsd.EuSmsmoney;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.esmc.mcnp.repositories.others.EuAccessKidsRepository;
import com.esmc.mcnp.repositories.obps.EuArticleStockesRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.others.EuCourRepository;
import com.esmc.mcnp.repositories.others.EuCreditCodebarreRepository;
import com.esmc.mcnp.repositories.others.EuFiliereRepository;
import com.esmc.mcnp.repositories.obps.EuGcpRepository;
import com.esmc.mcnp.repositories.org.EuMaisonRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.repositories.common.EuFraisRepository;
import com.esmc.mcnp.repositories.common.EuOperationRepository;
import com.esmc.mcnp.repositories.common.EuSmsRepository;
import com.esmc.mcnp.repositories.others.EuRepresentationRepository;
import com.esmc.mcnp.repositories.others.EuSmsmoneyRepository;
import com.esmc.mcnp.repositories.others.EuTegcRepository;
import com.esmc.mcnp.repositories.acteurs.EuActeurRepository;
import com.esmc.mcnp.repositories.ba.EuCapaDeclarationRepository;
import com.esmc.mcnp.repositories.ba.EuCapaRepository;
import com.esmc.mcnp.repositories.bc.EuCreditConsommerRepository;
import com.esmc.mcnp.repositories.bc.EuTypeCreditRepository;
import com.esmc.mcnp.repositories.security.EuUtilisateurRepository;
import com.esmc.mcnp.repositories.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.repositories.smcipn.EuSmcRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RechercheServiceImpl implements RechercheService {

    @Autowired
    private EuRepresentationRepository repDao;
    @Autowired
    private EuMembreMoraleRepository membreMoralDao;
    @Autowired
    private EuMembreRepository membreDao;
    @Autowired
    private EuCompteRepository compteDao;
    @Autowired
    private EuTypeCreditRepository TypeCreditDao;
    @Autowired
    private EuCourRepository courDao;
    @Autowired
    private EuOperationRepository operationDao;
    @Autowired
    private EuSmsRepository smsDao;
    @Autowired
    private EuSmcRepository smcDao;
    @Autowired
    private EuGcpRepository gcpDao;
    @Autowired
    private EuTegcRepository tegcpDao;
    @Autowired
    private EuCreditConsommerRepository creditConsoDao;
    @Autowired
    private EuCreditCodebarreRepository codebarreDao;
    @Autowired
    private EuMaisonRepository maisonDao;
    @Autowired
    private EuActeurRepository acteurDao;
    @Autowired
    private EuCapaDeclarationRepository declarationDao;
    @Autowired
    private EuCompteCreditRepository creditDao;
    @Autowired
    private EuCompteCreditTsRepository creditTsDao;
    @Autowired
    private EuCompteCreditCapaRepository creditcapaDao;
    @Autowired
    private EuArticleStockesRepository articleDao;
    @Autowired
    private EuUtilisateurRepository utilisateurDao;
    @Autowired
    private EuFiliereRepository filiereDao;
    @Autowired
    private EuAppelOffreRepository appelOffreDao;
    @Autowired
    private EuFraisRepository fraisDao;
    @Autowired
    private EuAccessKidsRepository accessDao;
    @Autowired
    private EuCapaRepository capaDao;
    @Autowired
    private EuSmsmoneyRepository smsMoneyRepo;

   
    
    
    
    
    
    @Override
    public List<String> FindListDesProduitsComptesTpn(String codeCompte) {
        System.out.println("codeCompte= " + codeCompte);
        if (!codeCompte.isEmpty()) {
            return creditDao.findCodeProduitByCodeCompte(codeCompte);
        } else {
            return null;
        }
    }

    @Override
    public List<String> FindListDesProduitsCapa(String codeCompte) {
        System.out.println("codeCompte= " + codeCompte);
        if (!codeCompte.isEmpty()) {
            return capaDao.findCodeProduitByCodeCompte(codeCompte);
        } else {
            return null;
        }
    }

    @Override
    public Boolean VerifierLecteur(String numSerie) {
        boolean verif = false;
        EuAccessKids euAccessKid = accessDao.getLecteurBySerialNumber(numSerie);
        if (euAccessKid != null) {
            verif = true;
        } else {
            verif = false;
        }
        return verif;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuArticleStockes> FindAllArticlesEnStock(String codemembre) {
        if (!codemembre.isEmpty()) {
            return articleDao.findByEuMembreMorale_codeMembreMorale(codemembre);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuFrais FindFraisbyProposition(Long IdProposition) {
        if (IdProposition != 0) {
            return fraisDao.findByIdProposition(IdProposition);
        } else {
            return null;
        }
    }

    @Override
    public EuProposition FindPropositionByNumero(String numeroOffre) {
        if (!numeroOffre.isEmpty()) {
            return appelOffreDao.findPropositionByNumero(numeroOffre);
        } else {
            return null;
        }
    }

    @Override
    public EuActeur FindSurveillance() {
        String typeActeur = "gac_surveillance";
        return acteurDao.findSurveillance(typeActeur);
    }

    @Override
    public EuAppelOffre FindAppelOffresByNum(String numeroOffre) {
        if (!numeroOffre.isEmpty()) {
            return appelOffreDao.findAppelOffresByNumero(numeroOffre);
        } else {
            return null;
        }
    }

    @Override
    public EuFiliere FindEuFiliereById(Long Id) {
        if (Id != null) {
            return filiereDao.findOne(Id);
        } else {
            return null;
        }

    }

    @Override
    public EuUtilisateur FindUtilisateurByCodeMembre(String codemembre) {
        if (!codemembre.isEmpty()) {
            return utilisateurDao.findUserByCodeMembre(codemembre);
        } else {
            return null;
        }
    }

    @Override
    public EuCompteCreditCapa finfEuCompteCreditCapa(Long idcredit) {
        if (idcredit != null) {
			List<EuCompteCreditCapa> listccc = creditcapaDao.findCreditCapaByIdCredit(idcredit);
			return listccc.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Long findMaxIdfromDeclaration() {
        Long id = null;
        if (declarationDao.findAll().size() > 0) {
            id = declarationDao.getLastIdDeclarationInsertedId();
        } else {
            id = 0L;
        }
        return id;
    }

    @Override
    public EuCapaDeclaration getEuCapaDeclarationByCodebarre(String codebarre) {
        if (!codebarre.isEmpty()) {
            return declarationDao.getEuCapaDeclarationByCodebarre(codebarre);
        } else {
            return null;
        }
    }

    @Override
    public EuCapaDeclaration getEuCapaDeclaration(String reference) {
        if (!reference.isEmpty()) {
            return declarationDao.getEuCapaDeclarationByReference(reference);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuMembre findMembreByCompte(String codeCompte) {
        if (!codeCompte.isEmpty()) {
            String[] compte_parts = codeCompte.split("-");
            String codeMembre = compte_parts[2];
            if (String.valueOf(codeMembre.charAt(codeMembre.length() - 1)).equals("P")) {
                return membreDao.findOne(codeMembre);
            }
        }
        return null;
    }

    @Override
    public EuMembre findMembreById(String id) {
        try {
            if (!id.isEmpty() && id.endsWith("P")) {
                return membreDao.findOne(id);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }

    }

    @Override
    public EuMembre findMembreByNomPrenom(String nom, String prenom) {
        if (!nom.isEmpty() && !prenom.isEmpty()) {
            return membreDao.findByNomMembreAndPrenomMembre(nom, prenom);
        }
        return null;
    }

    @Override
    public EuMembreMorale findMembreMoraleByCompte(String codeCompte) {
        if (!codeCompte.isEmpty()) {
            String[] compte_parts = codeCompte.split("-");
            String codeMembre = compte_parts[2];
            if (String.valueOf(codeMembre.charAt(codeMembre.length() - 1)).equals("M")) {
                return membreMoralDao.findOne(codeMembre);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuMembreMorale findMembreMoraleById(String id) {
        if (!id.isEmpty() && id.endsWith("M")) {
            return membreMoralDao.findByKey(id);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuMembreMorale> findMembreRepresentant(String codeMembreRep) {
        if (!codeMembreRep.isEmpty()) {
            return membreMoralDao.findByEuRepresentation(codeMembreRep);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuMembreMorale> findMembreMoraleByNom(String nom, String prenom) {
        if (!nom.isEmpty() && !prenom.isEmpty()) {
            return membreMoralDao.findByEuRepresentation_NomPrenom(nom, prenom);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuCompte findCompteById(String id) {
        try {
            if (!id.isEmpty()) {
                if (id.endsWith("M")) {
                    EuCompte compte = compteDao.findCompteCatbyId(id);
                    System.out.println(compte.getCodeCompte());
                    return compte;
                } else {
                    EuCompte compte = compteDao.findComptePPCatbyId(id);
                    System.out.println(compte.getCodeCompte());
                    return compte;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuCompte> findCompteByMembre(String codeMembre) {
        if (!codeMembre.isEmpty()) {
            System.out.println("Exec requete");
            if (codeMembre.endsWith("P")) {
                System.out.println("Exec requete MP");
                return compteDao.findByEuMembre_CodeMembre(codeMembre);
            } else {
                System.out.println("Exec requete MM");
                return compteDao.findByEuMembreMorale_CodeMembreMorale(codeMembre);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuCompte findCompteByNumCarte(String numeroCarte) {
        EuCompte compte = null;
        System.out.println("Recherche du compte :" + numeroCarte);
        if (!numeroCarte.isEmpty()) {
            compte = compteDao.findByNumeroCarte(numeroCarte);
        }
        return compte;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuCompte findbyMembreAndCodeCat(String code_membre, String code_cat) {
        if (!code_membre.isEmpty()) {
            if (String.valueOf(code_membre.charAt(code_membre.length() - 1)).equals("M")) {
                return compteDao.findByEuMembre_CodeMembreAndEuCategorieCompte_CodeCat(code_membre, code_cat);
            } else {
                return compteDao.findByEuMembreMorale_CodeMembreMoraleAndEuCategorieCompte_CodeCat(code_membre,
                        code_cat);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuRepresentation> fetchAllByMembreMoral(String codeMembre) {
        if (!codeMembre.isEmpty()) {
            return repDao.findByEuMembreMorale_CodeMembreMorale(codeMembre);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuMembreMorale findByRaisonSociale(String raison_sociale) {
        if (!raison_sociale.isEmpty()) {
            return membreMoralDao.findByRaisonSociale(raison_sociale);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuCours> findAllCours() {

        return courDao.findAll();

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EuTypeCredit> FindAllTypeCredit() {
        return TypeCreditDao.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuTypeCredit FindEuTypeCredit(String codetypecredit) {
        return TypeCreditDao.findOne(codetypecredit);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EuCreditCodebarre findCodebarreByCode(String codebarre) {
        if (!codebarre.isEmpty()) {
            return codebarreDao.findCreditByCodebar(codebarre);
        } else {
            return null;
        }
    }

    @Override
    public Long findMaxIdfromOperation() {
        Long id = null;
        if (operationDao.count() > 0) {
            id = operationDao.getLastOperationInsertedId();
        }
        return id;

    }

    @Override
    public Long findMaxIdfromEuSmc() {
        Long id = null;
        if (smcDao.count() > 0) {
            id = smcDao.getLastEuSmcInsertedId();
        }
        return id;
    }

    @Override
    public Long findMaxIdfromEuSms() {
        Long id = null;
        if (smsDao.count() > 0) {
            id = smsDao.getLastSmsInserted();
        }
        return id;
    }

    @Override
    public Long findMaxIdfromEuGcp() {
        Long id = null;
        if (gcpDao.count() > 0) {
            id = gcpDao.getLastEuGcpInsertedId();
        }
        return id;

    }

    @Override
    public Long findMaxIdfromCreditConsommer() {
        Long id = null;
        if (creditConsoDao.count() > 0) {
            return creditConsoDao.getLastEuConsommationInsertedId();
        }
        return id;

    }

    @Override
    public EuMaison getEuMAisonByCodeMembre(String codemembre) {
        if (!codemembre.isEmpty()) {
            return maisonDao.getMaisonByCodemembre(codemembre);
        } else {
            return null;
        }
    }

    @Override
    public EuActeur getEuActeurByCodeMembre(String codemembre) {

        if (!codemembre.isEmpty()) {
            return acteurDao.findByCodeMembre(codemembre);
        } else {
            return null;
        }

    }

    

    @Override
    public EuTegc getEuTegc(String codeTegc) {
        if (!codeTegc.isEmpty()) {
            return tegcpDao.findOne(codeTegc);
        } else {
            return null;
        }
    }

    @Override
    public EuGcsc findEuGcsc(String codemembre) {
        return null;
    }

    @Override
    public List<EuCompteCredit> FindAllCredit(String codecompte) {
        if (!codecompte.isEmpty()) {
            return creditDao.findByEuCompte_CodeCompte(codecompte);
        } else {
            return null;
        }
    }

    @Override
    public List<EuCompteCreditTs> FindAllCreditTs(String codecompte) {
        if (!codecompte.isEmpty()) {
            return creditTsDao.findByEuCompte_CodeCompte(codecompte);
        } else {
            return null;
        }
    }

    @Override
    public EuCompteCredit FindCompteCredit(Long idCredit) {
        if (idCredit != null) {
            return creditDao.findOne(idCredit);
        } else {
            return null;
        }
    }

    @Override
    public Double findMontSmsByCodeSms(String codeSMS) {
        Double mont = 0d;
        EuSmsmoney money = smsMoneyRepo.findByCreditcode(codeSMS);
        if (money != null && (money.getDestaccountConsumed() == null || money.getDestaccountConsumed().isEmpty())) {
            mont = money.getCreditamount();
        }
        return mont;
    }

    @Override
    public List<EuDetailDomicilie> FindDetailDomicilie(String codeMembre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
