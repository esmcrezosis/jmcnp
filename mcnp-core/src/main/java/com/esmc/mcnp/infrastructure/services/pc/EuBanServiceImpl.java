package com.esmc.mcnp.services.pc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuBan;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuBanRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBanServiceImpl extends BaseServiceImpl<EuBan, Long> implements EuBanService {

	/**
	 * 
	 */
	private Logger log = LogManager.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired
	EuBanRepository banRepository;

	@Override
	protected BaseRepository<EuBan, Long> getRepository() {
		return banRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public int emettreBan(String codeMembre, double montant) {
		if (StringUtils.isNotBlank(codeMembre) && montant > 0) {
			try {
				EuBan ban = new EuBan();
				ban.setCodeMembre(codeMembre);
				ban.setDateEmission(new Date());
				ban.setMontEmis(BigDecimal.valueOf(montant));
				ban.setMontVendu(BigDecimal.ZERO);
				ban.setSolde(BigDecimal.valueOf(montant));
				banRepository.save(ban);
				return 1;
			} catch (Exception e) {
				log.error("Erreur d'execution", e);
				return -1;
			}
		} else {
			return 0;
		}
	}

	@Override
	public EuBan emettreBanBis(String codeMembre, double montant) {
		if (StringUtils.isNotBlank(codeMembre) && montant > 0) {
			try {
				if (codeMembre.equals("0000000000000000007M")) {
					List<EuBan> bans = findByCodeMembre(codeMembre);
					if (!bans.isEmpty()) {
						EuBan ban = bans.get(0);
						ban.setMontEmis(ban.getMontEmis().add(BigDecimal.valueOf(montant)));
						ban.setSolde(ban.getSolde().add(BigDecimal.valueOf(montant)));
						banRepository.save(ban);
						return ban;
					} else {
						EuBan ban = new EuBan();
						ban.setCodeMembre(codeMembre);
						ban.setDateEmission(new Date());
						ban.setMontEmis(BigDecimal.valueOf(montant));
						ban.setMontVendu(BigDecimal.ZERO);
						ban.setSolde(BigDecimal.valueOf(montant));
						banRepository.save(ban);
						return ban;
					}
				} else {
					EuBan ban = new EuBan();
					ban.setCodeMembre(codeMembre);
					ban.setDateEmission(new Date());
					ban.setMontEmis(BigDecimal.valueOf(montant));
					ban.setMontVendu(BigDecimal.ZERO);
					ban.setSolde(BigDecimal.valueOf(montant));
					ban = banRepository.save(ban);
					return ban;
				}
			} catch (Exception e) {
				log.error("Erreur d'execution", e);
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<EuBan> findByCodeMembre(String codeMembre) {
		return banRepository.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuBan> findByMembre(String codeMembre) {
		return banRepository.findByMembre(codeMembre);
	}
}
