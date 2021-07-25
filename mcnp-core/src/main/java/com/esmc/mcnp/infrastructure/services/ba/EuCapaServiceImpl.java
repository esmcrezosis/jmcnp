package com.esmc.mcnp.services.ba;

import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.ba.EuCapaRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("capaService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCapaServiceImpl extends BaseServiceImpl<EuCapa, String> implements EuCapaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	EuCapaRepository capaRepo;
	@Autowired
	private EuCompteRepository compteRepo;

	@Override
	protected BaseRepository<EuCapa, String> getRepository() {
		return capaRepo;
	}

	@Override
	public List<EuCapa> findByCodeMembre(String codeMembre) {
		return capaRepo.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuCapa> fetchByCodeMembre(String codeMembre) {
		return capaRepo.fetchByCodeMembre(codeMembre);
	}

	@Override
	public List<EuCapa> findByMembreAndOrigine(String codeMembre, String origine) {
		return capaRepo.findByCodeMembreAndOrigineCapa(codeMembre, origine);
	}

	@Override
	public List<EuCapa> findbyMembreAndProduitAndOrigine(String codeMembre, String produit, String origine) {
		return capaRepo.findByMembreAndProduitAndOrigine(codeMembre, produit, origine);
	}

	@Override
	public Boolean verifierCompte(String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			String codeCompte = "NN-CAPA-" + codeMembre;
			EuCompte compte = compteRepo.findOne(codeCompte);
			if (Objects.nonNull(compte)) {
				List<EuCapa> capas = ListUtils.emptyIfNull(fetchByCodeMembre(codeMembre));
				if (!capas.isEmpty()) {
					double somme = capas.stream().mapToDouble(EuCapa::getMontantSolde).sum();
					return somme == compte.getSolde();
				}
			}
		}
		return false;
	}

	@Override
	public Boolean verifierCompte(String codeMembre, Double soldeCompte) {
		if (StringUtils.isNotBlank(codeMembre)) {
			List<EuCapa> capas = ListUtils.emptyIfNull(fetchByCodeMembre(codeMembre));
			if (!capas.isEmpty()) {
				double somme = capas.stream().mapToDouble(EuCapa::getMontantSolde).sum();
				return somme == soldeCompte;
			}
		}
		return false;
	}

	@Override
	public Boolean verifierSolde(String codeMembre, Double montant) {
		if (StringUtils.isNotBlank(codeMembre)) {
			List<EuCapa> capas = ListUtils.emptyIfNull(fetchByCodeMembre(codeMembre));
			if (!capas.isEmpty()) {
				double somme = capas.stream().mapToDouble(EuCapa::getMontantSolde).sum();
				return somme >= montant;
			}
		}
		return false;
	}

	@Override
	public boolean updateCapa(List<EuCapa> capas, Double montant) {
		if (!capas.isEmpty()) {
			try {
				int compteur = 0;
				double mont = montant;
				while (mont > 0 && compteur < capas.size()) {
					EuCapa capa = capas.get(compteur);
					if (capa.getMontantSolde() < mont) {
						mont -= capa.getMontantSolde();
						compteur++;
						capa.setMontantUtiliser(capa.getMontantUtiliser() + capa.getMontantSolde());
						capa.setMontantSolde(0.0);
						update(capa);
					} else {
						capa.setMontantUtiliser(capa.getMontantUtiliser() + mont);
						capa.setMontantSolde(capa.getMontantSolde() - mont);
						update(capa);
						mont = 0;
					}
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public List<String> findProduitByMembreAndOrigine(String codeMembre, String origine) {
		return capaRepo.findProduitByMembreAndOrigine(codeMembre, origine);
	}

	@Override
	public List<EuCapa> findByMembreAndOrigine(String codeMembre, List<String> origines) {
		return capaRepo.findByMembreAndOrigine(codeMembre, origines);
	}

	@Override
	public double findSumCapaByMembreAndOrigine(String codeMembre, String origine) {
		return capaRepo.findSumCapaByMembreAndOrigine(codeMembre, origine);
	}

	@Override
	public double findSumCapaByMembreAndOrigine(String codeMembre, List<String> origines) {
		return capaRepo.findSumCapaByMembreAndOrigine(codeMembre, origines);
	}

	@Override
	public Page<EuCapa> findByMembreAndOrigine(String codeMembre, String origineCapa, Pageable pageable) {
		return capaRepo.findByCodeMembreAndOrigineCapa(codeMembre, origineCapa, pageable);
	}

	@Override
	public Page<EuCapa> findByCompte(String codeCompte, Pageable pageable) {
		return capaRepo.findByEuCompte_CodeCompte(codeCompte, pageable);
	}

	@Override
	public Page<EuCapa> findByCompteAndOrigine(String codeCompte, String origine, Pageable pageable) {
		return capaRepo.findByEuCompte_CodeCompteAndOrigineCapa(codeCompte, origine, pageable);
	}

	@Override
	public Page<EuCapa> findByCodeMembre(String codeMembre, Pageable pageable) {
		return capaRepo.findByCodeMembre(codeMembre, pageable);
	}

	@Override
	public List<EuCapa> findByType(String typeCapa) {
		return capaRepo.findByType(typeCapa);
	}

	@Override
	public List<EuCapa> findByType(String codeMembre, String typeCapa) {
		return capaRepo.findByType(codeMembre, typeCapa);
	}

	@Override
	public List<EuCapa> findByCompteAndType(String codeCompte, String typeCapa) {
		return capaRepo.findByCompteAndType(codeCompte, typeCapa);
	}

	@Override
	public List<EuCapa> findByMembreAndType(String codeMembre, String typeCapa) {
		return capaRepo.findByMembreAndType(codeMembre, typeCapa);
	}

	@Override
	public List<EuCapa> findByMembreAndTypeAndOrigine(String codeMembre, String typeCapa, String origine) {
		return capaRepo.findByMembreAndTypeAndOrigine(codeMembre, typeCapa, origine);
	}

}
