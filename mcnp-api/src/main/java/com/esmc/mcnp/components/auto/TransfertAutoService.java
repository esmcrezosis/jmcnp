package com.esmc.mcnp.components.auto;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.ba.EuDetailSmsmoney;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.org.EuPays;
import com.esmc.mcnp.model.cm.EuStatutJuridique;
import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.model.obpsd.EuUtiliserNn;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.obpsd.EuDetailSmsmoneyRepository;
import com.esmc.mcnp.repositories.obpsd.EuUtiliserNnRepository;
import com.esmc.mcnp.services.obpsd.EuNnService;

@Service
@Transactional
public class TransfertAutoService {

	@Autowired
	private EuCompteRepository compteDao;
	@Autowired
	private EuNnService nnService;
	@Autowired
	private EuMembreMoraleRepository mmRepo;
	private @Autowired EuUtiliserNnRepository utiliserNnRepo;
	@Autowired
	private EuDetailSmsmoneyRepository detSmsmoneyDao;

	private final Logger logger = LogManager.getLogger(TransfertAutoService.class);

	@Async
	@Scheduled(cron = "0 0/45 * * * ?")
	//@Scheduled(fixedRate = 3600000)
	public void doTransfertAuto() {
		logger.info("Approvisionnement de NN");
		String codeMembre = "0000000000000000001M";
		String codeCompte = "NN-TR-" + codeMembre;
		List<String> unites = Arrays.asList("CAPS", "CAPA", "FL", "FS", "FCPS");
		List<Double> montant_mins = Arrays.asList(35000000.0, 10000000.0, 1000000.0, 5000000.0, 1000000.0);
		Map<String, Double> mins = new HashMap<>();
		for (int i = 0; i < unites.size(); i++) {
			mins.put(unites.get(i), montant_mins.get(i));
		}
		Date date = new Date();
		try {
			double RATE = 22.4;
			for (String unite : unites) {
				if (unite.equals("CAPA")) {
					codeMembre = "0000000000000000002M";
					codeCompte = "NN-TR-" + codeMembre;
				} else {
					codeMembre = "0000000000000000001M";
					codeCompte = "NN-TR-" + codeMembre;
				}
				EuMembreMorale moral = mmRepo.findOne(codeMembre);
				if (Objects.isNull(moral)) {
					EuStatutJuridique statut = new EuStatutJuridique();
					statut.setCodeStatut("SARLU");

					EuPays pays = new EuPays();
					pays.setIdPays(1);

					moral = new EuMembreMorale();
					moral.setAutoEnroler("O");
					moral.setBpMembre("03 30038");
					moral.setCodeGacFiliere(null);
					moral.setCodeMembreMorale(codeMembre);
					moral.setCodesecret(null);
					moral.setCodeTypeActeur("SOURCE");
					moral.setDateIdentification(date);
					moral.setDomaineActivite("Transfert Unite NN");
					moral.setEmailMembre("esmc@esmc-gacsource.com");
					moral.setEtatMembre("N");
					moral.setEuStatutJuridique(statut);
					moral.setEuPay(pays);
					moral.setVilleMembre("Lomé");
					moral.setTelMembre("22193271");
					moral.setSiteWeb("www.esmc-gacsource.com");
					moral.setRaisonSociale("ESMC Gac Source");
					moral.setQuartierMembre("Tokoin Wuiti");
					moral.setPortableMembre("93666275");
					moral.setHeureIdentification(LocalTime.now());
					moral.setIdUtilisateur(null);
					moral.setNumRegistreMembre("TG-LOME 2014 B 514");
					moral = mmRepo.save(moral);
				}
				EuCompte compte = compteDao.findOne(codeCompte);
				if (Objects.isNull(compte)) {
					compte = new EuCompte();
					compte.setCardprinteddate(null);
					compte.setCardprintediddate(0);
					compte.setCodeCompte(codeCompte);
					compte.setDateAlloc(new Date());
					compte.setDesactiver("N");
					compte.setEuCategorieCompte(new EuCategorieCompte("TR"));
					compte.setEuTypeCompte(new EuTypeCompte("NN"));
					compte.setEuMembre(null);
					compte.setEuMembreMorale(moral);
					compte.setLibCompte("Compte de Transfert NN");
					compte.setMifarecard(null);
					compte.setNumeroCarte(null);
					compte.setSolde(0);
					compteDao.save(compte);
				}
				Double soldeUnite = detSmsmoneyDao.findSumByTypeNnAndMembre(codeMembre, unite);
				if (soldeUnite == null) {
					soldeUnite = 0.0;
				}
				if (soldeUnite <= mins.get(unite)) {
					double montant = RATE * mins.get(unite);
					nnService.EmettreNn(unite, montant, date);
					EuUtiliserNn utilNn = new EuUtiliserNn();
					utilNn.setCodeMembreNb(null);
					utilNn.setCodeMembreNn(codeMembre);
					utilNn.setCodeProduit(unite);
					utilNn.setCodeProduitNn(unite);
					utilNn.setCodeSms(null);
					utilNn.setDateTransfert(date);
					utilNn.setIdOperation(null);
					utilNn.setIdUtilisateur(null);
					utilNn.setMontTransfert(montant);
					utilNn.setNumBon(null);
					utilNn.setMotif("Transfert automatique");
					utiliserNnRepo.save(utilNn);

					/*Long idDetailSmsMoney = detSmsmoneyDao.getLastDetSmsmooneyInsertedId();
					if (idDetailSmsMoney == null) {
						idDetailSmsMoney = 0L;
					}*/
					EuDetailSmsmoney detSmsMoney = new EuDetailSmsmoney();
					//detSmsMoney.setIdDetailSmsmoney(idDetailSmsMoney + 1);
					detSmsMoney.setCodeMembre(codeMembre);
					detSmsMoney.setEuMembreMorale(moral);
					detSmsMoney.setOrigineSms("TR");
					detSmsMoney.setCreditcode("");
					detSmsMoney.setDateAllocation(date);
					detSmsMoney.setMontSms(montant);
					detSmsMoney.setMontRegle(0);
					detSmsMoney.setIdUtilisateur(0L);
					detSmsMoney.setTypeSms(unite);
					detSmsMoney.setMontVendu(0);
					detSmsMoney.setSoldeSms(montant);
					detSmsmoneyDao.save(detSmsMoney);

					compte.setSolde(compte.getSolde() + montant);
					compteDao.save(compte);
				}
			}
		} catch (Exception e) {
			logger.error("Erreur d'execution d'approvisionnement NN ", e);
		}
	}

}
