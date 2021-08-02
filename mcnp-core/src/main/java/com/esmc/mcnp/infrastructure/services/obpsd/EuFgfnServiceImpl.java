package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.acteurs.EuBanqueRepository;
import com.esmc.mcnp.dao.repository.acteurs.EuEliRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreApproDetailRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreTiersDetailRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuDetailFgfnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuFgfnRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTpagcpRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTraiteRepository;
import com.esmc.mcnp.domain.entity.acteur.EuEli;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreTiersDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuDetailFgfn;
import com.esmc.mcnp.domain.entity.obpsd.EuFgfn;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euFgfnService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuFgfnServiceImpl extends BaseServiceImpl<EuFgfn, String> implements EuFgfnService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuFgfnRepository fgfnRepo;
	private @Autowired EuBanqueRepository bankRepo;
	private @Autowired EuMembreMoraleRepository moraleRepo;
	private @Autowired EuDetailFgfnRepository detFgfnRepo;
	private @Autowired EuBonNeutreApproDetailRepository bnApproDetRepo;
	private @Autowired EuBonNeutreTiersDetailRepository bnTiersDetRepo;
	private @Autowired EuEliRepository eliRepo;
	private @Autowired EuTraiteRepository traiteRepo;
	private @Autowired EuTpagcpRepository tpagcpRepo;

	@Override
	protected BaseRepository<EuFgfn, String> getRepository() {
		return fgfnRepo;
	}

	@Override
	public List<EuFgfn> findAllFgFn() {
		return fgfnRepo.findAllFgFn();
	}

	@Override
	public Double getSommeFgfn() {
		return fgfnRepo.getSommeFgfn();
	}

	@Override
	public EuFgfn findByMembre(String codeMembre) {
		return fgfnRepo.findByEuMembreMorale_CodeMembreMorale(codeMembre);
	}

	@Override
	public void createFgfnByBonNeutreDetail(EuBonNeutreDetail bnDetail, String codeSms, double montant) {
		if (StringUtils.isNotBlank(bnDetail.getBonNeutreDetailBanque())) {
			createFgfn(bnDetail.getBonNeutreDetailBanque(), codeSms, null, null, montant);
		} else {
			if (bnDetail.getBonNeutreApproId() != null) {
				List<EuBonNeutreApproDetail> bnApproDetails = bnApproDetRepo
						.findByBonNeutreDetailId(bnDetail.getBonNeutreApproId());
				double som_deduit = montant;
				int c = 0;
				while (som_deduit > 0 && c < bnApproDetails.size()) {
					EuBonNeutreApproDetail bnApproDetail = bnApproDetails.get(c);
					if (bnApproDetail.getBonNeutreApproDetailMontant() >= som_deduit) {
						if (bnApproDetail.getBonNeutreApproDetailBanque().startsWith("ELI")) {
							EuEli eli = eliRepo.findByNumeroEli(bnApproDetail.getBonNeutreApproDetailBanque()).get();
							createFgfn(null, codeSms, eli, null, som_deduit);
						} else if (bnApproDetail.getBonNeutreApproDetailBanque().startsWith("OPI")) {
							String[] numOpi = bnApproDetail.getBonNeutreApproDetailBanque().split("-");
							EuTraite traite = traiteRepo.findOne(Long.parseLong(numOpi[1]));
							createFgfn(null, codeSms, null, traite, som_deduit);
						} else {
							createFgfn(bnApproDetail.getBonNeutreApproDetailBanque(), codeSms, null, null, som_deduit);
						}
						bnApproDetail.setBonNeutreApproDetailMontUtilise(
								bnApproDetail.getBonNeutreApproDetailMontUtilise() + som_deduit);
						bnApproDetail.setBonNeutreApproDetailSolde(bnApproDetail.getBonNeutreApproDetailMontant()
								- bnApproDetail.getBonNeutreApproDetailMontUtilise());
						bnApproDetRepo.save(bnApproDetail);

						som_deduit = 0;
					} else {
						if (bnApproDetail.getBonNeutreApproDetailBanque().startsWith("ELI")) {
							EuEli eli = eliRepo.findByNumeroEli(bnApproDetail.getBonNeutreApproDetailBanque()).get();
							createFgfn(null, codeSms, eli, null, bnApproDetail.getBonNeutreApproDetailSolde());
						} else if (bnApproDetail.getBonNeutreApproDetailBanque().startsWith("OPI")) {
							String[] numOpi = bnApproDetail.getBonNeutreApproDetailBanque().split("-");
							EuTraite traite = traiteRepo.findOne(Long.parseLong(numOpi[1]));
							createFgfn(null, codeSms, null, traite, bnApproDetail.getBonNeutreApproDetailSolde());
						} else {
							createFgfn(bnApproDetail.getBonNeutreApproDetailBanque(), codeSms, null, null,
									bnApproDetail.getBonNeutreApproDetailSolde());
						}

						som_deduit -= bnApproDetail.getBonNeutreApproDetailSolde();

						bnApproDetail
								.setBonNeutreApproDetailMontUtilise(bnApproDetail.getBonNeutreApproDetailMontUtilise()
										+ bnApproDetail.getBonNeutreApproDetailSolde());
						bnApproDetail.setBonNeutreApproDetailSolde(bnApproDetail.getBonNeutreApproDetailMontant()
								- bnApproDetail.getBonNeutreApproDetailMontUtilise());
						bnApproDetRepo.save(bnApproDetail);

						c++;
					}
				}
			} else if (bnDetail.getBonNeutreTiersId() != null) {
				List<EuBonNeutreTiersDetail> bnTiersDetails = bnTiersDetRepo
						.findByBonNeutreDetailId(bnDetail.getBonNeutreTiersId());
				double som_deduit = montant;
				int c = 0;
				while (som_deduit > 0 && c < bnTiersDetails.size()) {
					EuBonNeutreTiersDetail bnTiersDetail = bnTiersDetails.get(c);
					if (bnTiersDetail.getBonNeutreTiersDetailMontant() >= som_deduit) {
						createFgfn(bnTiersDetail.getBonNeutreTiersDetailBanque(), codeSms, null, null, som_deduit);
						bnTiersDetail.setBonNeutreTiersDetailMontUtilise(
								bnTiersDetail.getBonNeutreTiersDetailMontUtilise() + som_deduit);
						bnTiersDetail.setBonNeutreTiersDetailSolde(bnTiersDetail.getBonNeutreTiersDetailMontant()
								- bnTiersDetail.getBonNeutreTiersDetailMontUtilise());
						bnTiersDetRepo.save(bnTiersDetail);

						som_deduit = 0;
					} else {
						createFgfn(bnTiersDetail.getBonNeutreTiersDetailBanque(), codeSms, null, null,
								bnTiersDetail.getBonNeutreTiersDetailSolde());

						som_deduit -= bnTiersDetail.getBonNeutreTiersDetailSolde();

						bnTiersDetail
								.setBonNeutreTiersDetailMontUtilise(bnTiersDetail.getBonNeutreTiersDetailMontUtilise()
										+ bnTiersDetail.getBonNeutreTiersDetailSolde());
						bnTiersDetail.setBonNeutreTiersDetailSolde(bnTiersDetail.getBonNeutreTiersDetailMontant()
								- bnTiersDetail.getBonNeutreTiersDetailMontUtilise());
						bnTiersDetRepo.save(bnTiersDetail);

						c++;
					}
				}
			} else {
				if (bnDetail.getBonNeutreDetailType().equalsIgnoreCase("ELI")) {
					EuEli eli = eliRepo.findByNumeroEli(bnDetail.getBonNeutreDetailNumero()).get();
					createFgfn(null, codeSms, eli, null, montant);
				} else if (bnDetail.getBonNeutreDetailType().equalsIgnoreCase("ELI-BC")) {
					createFgfn(null, codeSms, null, null, montant);
				} else {
					EuTraite traite = traiteRepo.findOne(Long.parseLong(bnDetail.getBonNeutreDetailNumero()));
					createFgfn(null, codeSms, null, traite, montant);
				}
			}
		}

	}

	@Override
	public void createFgfn(String codeBanque, String codeSms, EuEli eli, EuTraite traite, double montant) {
		String type_fgfn = "FGFNMES";
		String codeFgfn = "";
		EuMembreMorale morale = null;
		EuBanque bank = null;
		EuTpagcp tpagcp = null;
		if (StringUtils.isNotBlank(codeBanque) && !codeBanque.equalsIgnoreCase("ELI")
				&& !codeBanque.equalsIgnoreCase("OPI")) {
			bank = bankRepo.findOne(codeBanque);
			if (Objects.nonNull(bank) && StringUtils.isNotBlank(bank.getCodeMembreMorale())) {
				codeFgfn = type_fgfn + "-" + bank.getCodeMembreMorale();
				morale = moraleRepo.findByKey(bank.getCodeMembreMorale());
			} else {
				codeFgfn = type_fgfn + "-" + codeBanque;
			}
		} else {
			if (Objects.nonNull(eli)) {
				codeFgfn = type_fgfn + "-ELI-" + eli.getCodeMembre();
				morale = moraleRepo.findByKey(eli.getCodeMembre());
			} else if (Objects.nonNull(traite)) {
				tpagcp = tpagcpRepo.findOne(traite.getTraiteTegcp());
				codeFgfn = type_fgfn + "-OPI-" + tpagcp.getCodeMembre();
				if (tpagcp.getCodeMembre().endsWith("M")) {
					morale = moraleRepo.findByKey(tpagcp.getCodeMembre());
				}
			} else {
				codeFgfn = type_fgfn + "-0000000000000000007M";
				morale = moraleRepo.findByKey("0000000000000000007M");
			}
		}
		EuFgfn fgfn = findById(codeFgfn);
		if (Objects.nonNull(fgfn)) {
			fgfn.setMontantFgfn(fgfn.getMontantFgfn() + montant);
			fgfn.setSoldeFgfn(fgfn.getSoldeFgfn() + montant);
			update(fgfn);
		} else {
			fgfn = new EuFgfn();
			fgfn.setTypeFgfn(type_fgfn);
			fgfn.setCodeFgfn(codeFgfn);
			if (Objects.nonNull(morale)) {
				fgfn.setEuMembreMorale(morale);
			} else {
				fgfn.setEuMembreMorale(null);
			}
			fgfn.setMontantFgfn(montant);
			fgfn.setMontantUtilise(0.0);
			fgfn.setSoldeFgfn(montant);
			fgfn.setCodeBanque(codeBanque);
			fgfn = add(fgfn);
		}

		/*
		 * Long id_detFgfn = detFgfnRepo.findLastDetFgfnInsertedId(); if (id_detFgfn ==
		 * null) { id_detFgfn = 0L; } else { id_detFgfn++; }
		 */
		EuDetailFgfn det_fgfn = new EuDetailFgfn();
		if (Objects.nonNull(bank) && StringUtils.isNotBlank(bank.getCodeMembreMorale())) {
			det_fgfn.setCodeMembrePbf(bank.getCodeMembreMorale());
		} else {
			if (Objects.nonNull(eli)) {
				det_fgfn.setCodeMembrePbf(eli.getCodeMembre());
			} else if (Objects.nonNull(tpagcp)) {
				det_fgfn.setCodeMembrePbf(tpagcp.getCodeMembre());
			} else if (Objects.nonNull(morale)) {
				det_fgfn.setCodeMembrePbf(morale.getCodeMembreMorale());
			} else {
				det_fgfn.setCodeMembrePbf(null);
			}
		}
		det_fgfn.setDateFgfn(new Date());
		det_fgfn.setEuFgfn(fgfn);
		det_fgfn.setMontFgfn(montant);
		// det_fgfn.setIdFgfn(id_detFgfn);
		det_fgfn.setMontPreleve(0);
		det_fgfn.setCreditcode(codeSms);
		det_fgfn.setSoldeFgfn(montant);
		det_fgfn.setOrigineFgfn("TR");
		det_fgfn.setTypeFgfn(type_fgfn);
		det_fgfn.setCodeBanque(codeBanque);
		detFgfnRepo.save(det_fgfn);
	}

	@Override
	public Page<EuFgfn> findAll(Pageable pageable) {
		return fgfnRepo.findAllFgfn(pageable);
	}

	@Override
	public Page<EuFgfn> findByMembre(String codeMembre, Pageable pageable) {
		return fgfnRepo.findByMembre(codeMembre, pageable);
	}

	@Override
	public Page<EuFgfn> findByBanque(String codeBanque, Pageable pageable) {
		return fgfnRepo.findByCodeBanque(codeBanque, pageable);
	}

}
