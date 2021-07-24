package com.esmc.mcnp.services.oi;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.odd.EuMstier;
import com.esmc.mcnp.model.odd.EuMstiersUtilise;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.odd.EuMstierRepository;
import com.esmc.mcnp.repositories.odd.EuMstiersUtiliseRepository;
import com.google.common.collect.Lists;

/**
 * Created by mawuli on 07/06/17.
 */
@Service("euMstiersService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMstiersServiceImpl extends BaseServiceImpl<EuMstier, Integer> implements EuMstiersService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuMstierRepository mstierRepository;
	private @Autowired EuMstiersUtiliseRepository msuRepository;

	@Override
	protected BaseRepository<EuMstier, Integer> getRepository() {
		return mstierRepository;
	}

	@Override
	public List<EuMstier> findByMembre(String codeMembre) {
		return mstierRepository.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuMstier> findByMembreAndStatut(String codeMembre, String statut) {
		return mstierRepository.findByCodeMembreAndStatutMstiers(codeMembre, statut);
	}

	@Override
	public List<EuMstier> findByStatut(String statut) {
		return mstierRepository.findByStatutMstiers(statut);
	}

	@Override
	public Double getSumByMembre(String codeMembre) {
		return mstierRepository.findSumByCodeMembre(codeMembre);
	}

	@Override
	public Double getSumByMembreAndStatut(String codeMembre, String statut) {
		return mstierRepository.findSumByCodeMembreAndStatut(codeMembre, statut);
	}

	@Override
	public Double getSumByStatut(String statut) {
		return mstierRepository.findSumByStatut(statut);
	}

	@Override
	public void updateMstier(String typeMstier, String statut, String codeMembre, String codeMembreBenef, double monant,
			Date date) {
		if (StringUtils.isNotBlank(typeMstier) && StringUtils.isNotBlank(statut)) {
			List<EuMstier> mstiers = Lists.newArrayList();
			if (StringUtils.isNotBlank(codeMembre)) {
				mstiers = findByMembreAndTypeAndStatut(codeMembre, typeMstier, statut);
			} else {
				mstiers = findBytypeAndStatut(typeMstier, statut);
			}
			if (!mstiers.isEmpty()) {
				updateMstier(mstiers, codeMembreBenef, monant, date);
			}
		}
	}

	@Override
	public void updateMstier(List<EuMstier> mstiers, String codeMembreBenef, double montant, Date date) {
		double mont_deduire = montant;
		int i = 0;
		while (i < mstiers.size() && mont_deduire > 0) {
			EuMstier ms = mstiers.get(i);
			if (ms.getMontantRestant() >= mont_deduire) {
				ms.setMontantUtilise(ms.getMontantUtilise() + mont_deduire);
				ms.setMontantRestant(ms.getMontantRestant() - mont_deduire);
				update(ms);

				EuMstiersUtilise msu = new EuMstiersUtilise();
				msu.setCodeCaps(null);
				msu.setCodeMembre(codeMembreBenef);
				msu.setDateMstiersUtilise(date);
				msu.setIdMstiers(ms.getIdMstiers());
				msu.setMontantUtilise(mont_deduire);
				msuRepository.save(msu);

				mont_deduire = 0;
			} else {
				mont_deduire -= ms.getMontantRestant();

				EuMstiersUtilise msu = new EuMstiersUtilise();
				msu.setCodeCaps(null);
				msu.setCodeMembre(codeMembreBenef);
				msu.setDateMstiersUtilise(date);
				msu.setIdMstiers(ms.getIdMstiers());
				msu.setMontantUtilise(ms.getMontantRestant());
				msuRepository.save(msu);

				ms.setMontantUtilise(ms.getMontantUtilise() + ms.getMontantRestant());
				ms.setMontantRestant(ms.getMontantSouscris() - ms.getMontantUtilise());
				update(ms);

				i++;
			}
		}
	}

	@Override
	public List<EuMstier> findBytypeAndStatut(String type, String statut) {
		return mstierRepository.findByTypeMstiersAndStatutMstiers(type, statut);
	}

	@Override
	public List<EuMstier> findByMembreAndTypeAndStatut(String codeMembre, String type, String statut) {
		return mstierRepository.findByCodeMembreAndTypeMstiersAndStatutMstiers(codeMembre, type, statut);
	}

	@Override
	public Double sumBytypeAndStatut(String type, String statut) {
		return mstierRepository.sumByTypeAndStatut(type, statut);
	}

	@Override
	public Double sumByMembreAndTypeAndStatut(String codeMembre, String type, String statut) {
		return mstierRepository.sumByCodeMembreAndTypeAndStatut(codeMembre, type, statut);
	}
}
