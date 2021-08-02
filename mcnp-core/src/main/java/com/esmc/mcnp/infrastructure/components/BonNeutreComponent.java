package com.esmc.mcnp.infrastructure.components;

import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.core.utils.ServerUtil;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;

@Component
@Transactional
public class BonNeutreComponent {

	private EuBonNeutreService bnService;
	private EuBonNeutreDetailService bnDetailService;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;

	@Inject
	public BonNeutreComponent(EuBonNeutreService bnService, EuBonNeutreDetailService bnDetailService,
			EuMembreService membreService, EuMembreMoraleService moraleService) {
		this.bnService = bnService;
		this.bnDetailService = bnDetailService;
		this.membreService = membreService;
		this.moraleService = moraleService;
	}

	public void approBAn(String codeMembreAppro, String codeMembre, double montant) {
		if (StringUtils.isNotBlank(codeMembreAppro) && StringUtils.isNotBlank(codeMembre)) {
			EuMembre membre = null;
			EuMembreMorale morale = null;
			EuBonNeutre bn = bnService.findByMembre(codeMembreAppro);
			EuBonNeutre bnBenef = bnService.findByMembre(codeMembre);
			if (Objects.nonNull(bnBenef) && Objects.nonNull(bn) && bn.getBonNeutreMontantSolde() >= montant) {
				String type = "PP";
				if (codeMembre.endsWith("M")) {
					type = "PM";
				}
				if (codeMembre.endsWith("P")) {
					membre = membreService.findByCodeMembre(codeMembre);
				} else {
					morale = moraleService.findByCodeMembreMorale(codeMembre);
				}
				try {

					EuBonNeutreAppro appro = bnService.updateBonNeutreAppro(bn, "Approvisionnement de BAn", type,
							codeMembre, montant);
					bnBenef.setBonNeutreMontant(bnBenef.getBonNeutreMontant() + montant);
					bnBenef.setBonNeutreMontantSolde(bnBenef.getBonNeutreMontantSolde() + montant);
					bnService.update(bnBenef);

					EuBonNeutreDetail bnDetail = new EuBonNeutreDetail();
					bnDetail.setBonNeutreApproId(appro.getBonNeutreApproId());
					bnDetail.setBonNeutreDetailBanque(null);
					bnDetail.setBonNeutreDetailCode(ServerUtil.GenererUniqueCodeForBan());
					bnDetail.setBonNeutreDetailDate(new Date());
					bnDetail.setBonNeutreDetailDateNumero(null);
					bnDetail.setBonNeutreDetailMontant(montant);
					bnDetail.setBonNeutreDetailMontantSolde(montant);
					bnDetail.setBonNeutreDetailMontantUtilise(0);
					bnDetail.setBonNeutreDetailNumero(null);
					bnDetail.setBonNeutreDetailType("BUDGET");
					bnDetail.setBonNeutreDetailVignette(null);
					bnDetail.setBonNeutreTiersId(null);
					bnDetail.setEuBonNeutre(bnBenef);
					if (codeMembre.endsWith("P")) {
						bnDetail.setIdCanton(membre.getEuCanton().getIdCanton());
					} else {
						bnDetail.setIdCanton(morale.getEuCanton().getIdCanton());
					}
					bnDetailService.create(bnDetail);
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}
}
