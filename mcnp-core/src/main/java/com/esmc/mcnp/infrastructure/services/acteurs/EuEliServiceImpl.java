package com.esmc.mcnp.infrastructure.services.acteurs;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.acteurs.EuEliRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreRepository;
import com.esmc.mcnp.dao.repository.others.EuTegcRepository;
import com.esmc.mcnp.domain.entity.acteur.EuDetailEli;
import com.esmc.mcnp.domain.entity.acteur.EuEli;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euEliService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuEliServiceImpl extends BaseServiceImpl<EuEli, Integer> implements EuEliService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuEliRepository elirepo;
	private @Autowired EuMembreMoraleRepository moraleRepo;
	private @Autowired EuMembreRepository membreRepo;
	private @Autowired EuTegcRepository tegcRepo;
	private @Autowired EuDetailEliService detEliService;

	@Override
	protected BaseRepository<EuEli, Integer> getRepository() {
		return elirepo;
	}

	@Override
	public Optional<EuEli> findByNumero(String numero) {
		return elirepo.findByNumeroEli(numero);
	}

	@Override
	public List<EuEli> findByCodeTegc(String codeTegc) {
		return elirepo.findByCodeTegc(codeTegc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public EuEli createEli(Date date, String codeMembre, String codeTegc, double montBan, double montBai,
			double montOpi) {
		if (StringUtils.isNotBlank(codeMembre) && StringUtils.isNotBlank(codeTegc)) {
			EuMembreMorale morale = null;
			EuMembre membre = null;
			if (codeMembre.endsWith("M")) {
				morale = moraleRepo.findByKey(codeMembre);
			} else {
				membre = membreRepo.findByCodeMembre(codeMembre);
			}
			if (Objects.nonNull(morale) || Objects.nonNull(membre)) {
				EuTegc tegc = tegcRepo.findByCodeTegc(codeTegc);
				if (Objects.nonNull(tegc)) {
					EuEli eli = new EuEli();
					eli.setCodeMembre(codeMembre);
					eli.setCodeTegc(codeTegc);
					eli.setMontantBan(montBan);
					if (montBan > 0) {
						eli.setBan(true);
					}
					eli.setMontantBai(montBai);
					if (montBai > 0) {
						eli.setBai(true);
					}
					eli.setMontantOpi(montOpi);
					if (montOpi > 0) {
						eli.setOpi(true);
					}
					eli.setMontantEli(montBan + montBai + montOpi);
					eli.setMontantVente(eli.getMontantEli());
					eli.setDateEli(date);
					eli.setPropose(0);
					eli.setRejeter(0);
					eli.setValider(4);
					eli.setPayer(true);
					if (morale != null) {
						eli.setIdCanton(morale.getEuCanton().getIdCanton());
					} else {
						eli.setIdCanton(membre.getEuCanton().getIdCanton());
					}
					add(eli);

					EuDetailEli detailEli = new EuDetailEli();
					detailEli.setIdEli(eli.getIdEli());
					detailEli.setLibelleProduit("");
					return eli;
				} else {
					throw new CompteNonTrouveException("Le membre N° " + codeMembre + " n'a pas de TE");
				}
			} else {
				throw new CompteNonTrouveException("Le membre N° " + codeMembre + " n'est pas trouvé");
			}
		} else {
			throw new IllegalArgumentException("Veuillez fournir les informations nécéssaires");
		}
	}

	@Override
	public EuEli createEli(Date date, String codeMembre, String codeTegc, double montBan, double montBai,
			double montOpi, List<EuDetailEli> details) {
		// TODO Auto-generated method stub
		return null;
	}
}
