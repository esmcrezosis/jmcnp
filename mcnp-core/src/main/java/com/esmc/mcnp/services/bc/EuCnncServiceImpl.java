package com.esmc.mcnp.services.bc;

import java.util.List;
import java.util.Map;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import com.esmc.mcnp.services.cm.EuCompteGeneralService;
import com.esmc.mcnp.services.cm.EuCompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.bc.EuCnnc;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.repositories.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.bc.EuCnncRepository;
import com.google.common.collect.Maps;

/**
 * Created by USER on 06/12/2016.
 */
@Service("euCnncService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCnncServiceImpl extends BaseServiceImpl<EuCnnc, Long> implements EuCnncService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    private EuCnncRepository cnncRepository;
    @Autowired
    private EuCompteGeneralService cgService;
    @Autowired
    private EuCompteService compteService;
    @Autowired
    private EuCompteCreditTsRepository creditTsRepo;
    @Autowired
    private EuCompteCreditRepository creditRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveCnncTs(EuCompteCreditTs ts) {
        Long idCnnc = cnncRepository.getLastEuCnncInsertedId();
        if (idCnnc == null) {
            idCnnc = 1L;
        } else {
            idCnnc += 1;
        }
        EuCnnc cnnc = new EuCnnc();
        cnnc.setIdCnnc(idCnnc);
        cnnc.setCodeMembre(ts.getEuCompteCredit().getCodeMembre());
        cnnc.setDatefin(ts.getEuCompteCredit().getDatefin());
        cnnc.setEuCompteCredit(ts.getEuCompteCredit());
        cnnc.setLibelle("Credit Non consomme");
        cnnc.setMontCredit(ts.getMontant());
        cnnc.setMontUtilise(0);
        cnnc.setSolde(ts.getMontant());
        cnnc.setSourceCredit(ts.getEuCompteCredit().getSource());
        cnncRepository.save(cnnc);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveCnnc(EuCompteCredit cc) {
        Long idCnnc = cnncRepository.getLastEuCnncInsertedId();
        if (idCnnc == null) {
            idCnnc = 1L;
        } else {
            idCnnc += 1;
        }
        EuCnnc cnnc = new EuCnnc();
        cnnc.setIdCnnc(idCnnc);
        cnnc.setCodeMembre(cc.getCodeMembre());
        cnnc.setDatefin(cc.getDatefin());
        cnnc.setEuCompteCredit(cc);
        cnnc.setLibelle("Crédit Non consommé");
        cnnc.setMontCredit(cc.getMontantCredit());
        cnnc.setMontUtilise(0);
        cnnc.setSolde(cc.getMontantCredit());
        cnnc.setSourceCredit(cc.getSource());
        cnncRepository.save(cnnc);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String, Object> transfererCreditNonConsommer(EuCompteCredit cc) {
        Map<String, Object> stats = Maps.newHashMap();
        int nbre_credit_nc = 0;
        double mont_nconso = 0;
        EuCompte compte = cc.getEuCompte();
        List<EuCompteCreditTs> creditTs = creditTsRepo.findByIdCredit(cc.getIdCredit());
        if (creditTs.size() > 0) {

            // Annulation des crédits non consommés avant terme sur
            // les comptes de transaction
            for (EuCompteCreditTs ts : creditTs) {
                EuCompte cpteTs = ts.getEuCompteCredit().getEuCompte();
                cpteTs.setSolde(cpteTs.getSolde() - ts.getMontant());
                compteService.update(cpteTs);

                saveCnncTs(ts);

                cgService.saveCG("CNNC", "NB", "E", ts.getMontant());

                mont_nconso += ts.getMontant();
                ts.setMontant(Double.valueOf("0"));
                creditTsRepo.save(ts);
            }
        }

/**
 * Annulation des crédits non consommés avant terme sur les
 * comptes crédits
 */
        if (cc.getMontantCredit() > 0) {
            nbre_credit_nc++;
            mont_nconso += cc.getMontantCredit();

            compte.setSolde(compte.getSolde() - cc.getMontantCredit());
            compteService.update(compte);

            saveCnnc(cc);

            cgService.saveCG("CNNC", "NB", "E", cc.getMontantCredit());

            cc.setMontantCredit(0.0);
            creditRepo.save(cc);
        }
        stats.put("nbre", nbre_credit_nc);
        stats.put("som", mont_nconso);
        return stats;
    }

    @Override
    protected BaseRepository<EuCnnc, Long> getRepository() {
        return cnncRepository;
    }
}
