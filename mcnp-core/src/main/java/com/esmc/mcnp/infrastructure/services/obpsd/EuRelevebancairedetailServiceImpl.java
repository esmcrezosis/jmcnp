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

import com.esmc.mcnp.dao.repository.acteurs.EuBanqueRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuRelevebancairedetailRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.domain.entity.obpsd.EuRelevebancaire;
import com.esmc.mcnp.domain.entity.obpsd.EuRelevebancairedetail;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRelevebancairedetailServiceImpl extends BaseServiceImpl<EuRelevebancairedetail, Integer>
		implements EuRelevebancairedetailService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRelevebancairedetailRepository detRelBancRepo;
	private @Autowired EuBanqueRepository banqueRepo;
	private @Autowired EuRelevebancaireService relbancService;
	private final Logger log = LogManager.getLogger(EuRelevebancairedetailServiceImpl.class.getName());

	@Override
	protected BaseRepository<EuRelevebancairedetail, Integer> getRepository() {
		return detRelBancRepo;
	}

	@Override
	public boolean isReleveBancaireDetailExist(String numero) {
		if (StringUtils.isNotBlank(numero)) {
			EuRelevebancairedetail detRelBanc = detRelBancRepo.findByRelevebancairedetailNumero(numero);
			if (Objects.isNull(detRelBanc)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public String insertReleveBancairedetail(String codeBanque, Date date, String numero, String libelle,
			double montant, Long idUtilisateur) {
		EuBanque banque;
		if (StringUtils.isNotBlank(codeBanque)) {
			banque = banqueRepo.findOne(codeBanque);
			if (Objects.nonNull(banque)) {
				Optional<EuRelevebancairedetail> optdetRel = findByNumero(numero);
				if (!optdetRel.isPresent()) {
					Optional<EuRelevebancaire> optRelBancaire = relbancService.findByDateAndBanque(date, codeBanque);
					if (optRelBancaire.isPresent()) {
						try {
							Integer lastid = detRelBancRepo.findLastInsertedRelevebancairedetail();
							if (lastid == null) {
								lastid = 1;
							} else {
								lastid++;
							}
							EuRelevebancairedetail reldet = new EuRelevebancairedetail();
							reldet.setRelevebancairedetailRelevebancaire(optRelBancaire.get().getRelevebancaireId());
							reldet.setPublier(0);
							reldet.setRelevebancairedetailDate(date);
							reldet.setRelevebancairedetailDateValeur(null);
							reldet.setRelevebancairedetailId(lastid);
							reldet.setRelevebancairedetailNumero(numero);
							reldet.setRelevebancairedetailLibelle(libelle);
							reldet.setRelevebancairedetailMontant(String.valueOf(montant));
							add(reldet);
							return "OK";
						} catch (Exception e) {
							log.error("Erreur d'insertion du détail relevé bancaire", e);
							return "Erreur d'insertion du détail relevé bancaire : " + e.getMessage();
						}
					} else {
						Optional<EuRelevebancaire> optRelBanc = relbancService.insertRelevebancaire(date, codeBanque,
								idUtilisateur);
						if (optRelBanc.isPresent()) {
							try {
								Integer lastid = detRelBancRepo.findLastInsertedRelevebancairedetail();
								if (lastid == null) {
									lastid = 1;
								} else {
									lastid++;
								}
								EuRelevebancairedetail reldet = new EuRelevebancairedetail();
								reldet.setRelevebancairedetailRelevebancaire(optRelBanc.get().getRelevebancaireId());
								reldet.setPublier(0);
								reldet.setRelevebancairedetailDate(date);
								reldet.setRelevebancairedetailDateValeur(null);
								reldet.setRelevebancairedetailId(lastid);
								reldet.setRelevebancairedetailLibelle(libelle);
								reldet.setRelevebancairedetailNumero(numero);
								reldet.setRelevebancairedetailMontant(String.valueOf(montant));
								add(reldet);
								return "OK";
							} catch (Exception e) {
								log.error("Erreur d'insertion du détail relevé bancaire", e);
								return "Erreur d'insertion du détail relevé bancaire : " + e.getMessage();
							}
						} else {
							return "Echec d'insertion du relevé bancaire";
						}
					}
				} else {
					return "Cette transaction n° " + numero + " existe déjà dans notre base de données";
				}
			} else {
				return "La banque fournie n'existe pas dans notre base de données";
			}
		} else {
			return "Vous devez fournir la banque de la transaction!!!";
		}
	}

	@Override
	public Optional<EuRelevebancairedetail> findByNumero(String numero) {
		if (StringUtils.isNotBlank(numero)) {
			return Optional.ofNullable(detRelBancRepo.findByRelevebancairedetailNumero(numero));
		}
		return Optional.empty();
	}

	@Override
	public Optional<EuRelevebancairedetail> findByNumeroAndPublier(String numero, int publier) {
		if (StringUtils.isNotBlank(numero)) {
			return Optional.ofNullable(detRelBancRepo.findByRelevebancairedetailNumeroAndPublier(numero, publier));
		}
		return Optional.empty();
	}

}
