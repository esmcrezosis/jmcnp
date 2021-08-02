package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuRelevebancaireRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuRelevebancaire;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euRelevebancaireService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRelevebancaireServiceImpl extends BaseServiceImpl<EuRelevebancaire, Integer>
		implements EuRelevebancaireService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRelevebancaireRepository relevebancRepo;
	private final Logger log = LogManager.getLogger(EuRelevebancaireServiceImpl.class.getName());

	@Override
	protected BaseRepository<EuRelevebancaire, Integer> getRepository() {
		return relevebancRepo;
	}

	@Override
	public boolean isReleveBancaireExist(Date date, String bank) {
		if (Objects.nonNull(date) && StringUtils.isNotBlank(bank)) {
			Optional<EuRelevebancaire> relBanc = findByDateAndBanque(date, bank);
			if (relBanc.isPresent()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Optional<EuRelevebancaire> findByDateAndBanque(Date date, String codeBanque) {
		return Optional.ofNullable(relevebancRepo.findByRelevebancaireDateAndRelevebancaireBanque(date, codeBanque));
	}

	@Override
	public Optional<EuRelevebancaire> insertRelevebancaire(Date date, String codeBanque, Long idUtilisateur) {
		log.info("Insertion du releve bancaire");
		if (Objects.nonNull(date) && StringUtils.isNotBlank(codeBanque)) {
			try {
				Integer lastid = relevebancRepo.findLastInsertedRelevebancaire();
				if (lastid == null) {
					lastid = 1;
				} else {
					lastid++;
				}
				EuRelevebancaire relBank = new EuRelevebancaire();
				relBank.setRelevebancaireId(lastid);
				relBank.setPublier(1);
				relBank.setRelevebancaireBanque(codeBanque);
				relBank.setRelevebancaireDate(date);
				relBank.setRelevebancaireFichier(null);
				relBank.setRelevebancaireUtilisateur(idUtilisateur.toString());
				return Optional.ofNullable(add(relBank));
			} catch (Exception e) {
				log.error("Erreur d'insertion du releve bancaire", e);
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

}
