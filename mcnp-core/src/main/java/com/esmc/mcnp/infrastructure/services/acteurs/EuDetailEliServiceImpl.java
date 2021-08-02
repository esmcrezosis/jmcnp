package com.esmc.mcnp.infrastructure.services.acteurs;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.acteurs.EuDetailEliRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuDetailEli;
import com.esmc.mcnp.domain.entity.acteur.EuEli;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euDetailEliService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailEliServiceImpl extends BaseServiceImpl<EuDetailEli, Integer> implements EuDetailEliService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailEliRepository detailEliRepository;

	@Override
	protected BaseRepository<EuDetailEli, Integer> getRepository() {
		return detailEliRepository;
	}

	@Override
	public List<EuDetailEli> findByEliAndStatut(Integer idEli, Integer statut) {
		return detailEliRepository.findAllByIdEliAndStatut(idEli, statut);
	}

	@Override
	public double getSommeDetailByeliAndStatut(Integer idEli, Integer statut) {
		return detailEliRepository.getSumByEliAndStatut(idEli, statut);
	}

	@Override
	public List<EuDetailEli> findAllByIdEli(Integer idEli) {
		return detailEliRepository.findAllByIdEli(idEli);
	}

	@Override
	public double getSommeDetailByeli(Integer idEli) {
		return detailEliRepository.getSumByEli(idEli);
	}

	@Override
	public EuDetailEli addDetailEli(EuEli eli, String typeBps, String libelleProduit, int qte, double prixUnit,
			int qteVte, double prixVte, Integer statut) {
		if (Objects.nonNull(eli) && StringUtils.isNotBlank(typeBps) && qte > 0 && prixUnit > 0) {
			EuDetailEli detEli = new EuDetailEli();
			detEli.setIdEli(eli.getIdEli());
			detEli.setLibelleProduit(libelleProduit);
			detEli.setQuantite(qte);
			detEli.setPrixUnitaire(prixUnit);
			detEli.setMontantProduit(detEli.getPrixUnitaire() * detEli.getQuantite());
			detEli.setQteVente((double) qteVte);
			detEli.setPrixVente(prixVte);
			detEli.setTypeBps(typeBps);
			detEli.setStatut(statut);
			return detEli;
		}
		return null;
	}
}
