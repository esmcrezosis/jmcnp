package com.esmc.mcnp.services.bc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.bc.EuBonRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonServiceImpl extends BaseServiceImpl<EuBon, Integer> implements EuBonService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuBonRepository bonRepo;

	@Override
	protected BaseRepository<EuBon, Integer> getRepository() {
		return bonRepo;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean emettreBa(String type, String codeMembre, double montant) {
		if (StringUtils.isBlank(type) && StringUtils.isBlank(codeMembre)) {
			return false;
		}
		try {
			Date date = new Date();
			Long compteur = findByMaxIdInserted() + 1;
			EuBon bon = new EuBon();
			bon.setBonId(compteur.intValue());
			bon.setBonCodeBarre(null);
			bon.setBonCodeMembreDistributeur(null);
			bon.setBonCodeMembreEmetteur(codeMembre);
			bon.setBonMontant(montant);
			bon.setBonMontantSalaire(0);
			bon.setBonType(type);
			bon.setBonNumero(type + StringUtils.leftPad(String.valueOf(compteur), 8, '0'));
			bon.setBonDate(date);
			bon.setBonExprimer(0);
			bon.setBonDateExpression(null);
			bonRepo.saveAndFlush(bon);
			return true;
		} catch (RuntimeException e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public List<EuBon> findBonNonExprimer(String codeMembre, String typeBon) {
		return bonRepo.findBonNonExpByMembre(codeMembre, typeBon);
	}

	@Override
	public EuBon findByBonCode(String codeBon, int exprimer) {
		return bonRepo.findByBonNumeroAndBonExprimer(codeBon, exprimer);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Optional<EuBon> emettreBon(String type, String codeMembre, double montant) {
		if (StringUtils.isBlank(type) && StringUtils.isBlank(codeMembre)) {
			return Optional.empty();
		}
		try {
			Date date = new Date();
			Long compteur = findByMaxIdInserted();
			if (compteur == null) {
				compteur = 1L;
			} else {
				compteur++;
			}

			EuBon bon = new EuBon();
			bon.setBonId(compteur.intValue());
			bon.setBonCodeBarre(null);
			bon.setBonCodeMembreDistributeur(null);
			bon.setBonCodeMembreEmetteur(codeMembre);
			bon.setBonMontant(montant);
			bon.setBonMontantSalaire(0);
			bon.setBonType(type);
			bon.setBonNumero(type + StringUtils.leftPad(String.valueOf(compteur), 8, '0'));
			bon.setBonDate(date);
			bon.setBonExprimer(0);
			bon.setBonDateExpression(null);
			return Optional.of(bonRepo.saveAndFlush(bon));
		} catch (RuntimeException e) {
			System.out.println(e.getLocalizedMessage());
			return Optional.empty();
		}
	}

	@Override
	public Long findByMaxIdInserted() {
		return bonRepo.findByMaxIdInserted();
	}

	@Override
	public Double findSumAllBonConso() {
		return bonRepo.findSumAllBonConso();
	}

	@Override
	public Optional<EuBon> emettreBon(String type, String codeMembre, String codeMembreAch, double montant) {
		if (StringUtils.isBlank(type) && StringUtils.isBlank(codeMembre)) {
			return Optional.empty();
		}
		try {
			Date date = new Date();
			Long compteur = findByMaxIdInserted();
			if (compteur == null) {
				compteur = 1L;
			} else {
				compteur++;
			}

			EuBon bon = new EuBon();
			bon.setBonId(compteur.intValue());
			bon.setBonCodeBarre(null);
			bon.setBonCodeMembreDistributeur(codeMembreAch);
			bon.setBonCodeMembreEmetteur(codeMembreAch);
			bon.setBonMontant(montant);
			bon.setBonMontantSalaire(0);
			bon.setBonType(type);
			bon.setBonNumero(type + StringUtils.leftPad(String.valueOf(compteur), 8, '0'));
			bon.setBonDate(date);
			bon.setBonExprimer(0);
			bon.setBonDateExpression(null);
			return Optional.of(bonRepo.saveAndFlush(bon));
		} catch (RuntimeException e) {
			System.out.println(e.getLocalizedMessage());
			return Optional.empty();
		}
	}

}
