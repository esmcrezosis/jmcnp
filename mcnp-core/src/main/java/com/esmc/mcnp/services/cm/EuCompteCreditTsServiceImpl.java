package com.esmc.mcnp.services.cm;

import java.util.List;
import java.util.Objects;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.repositories.cm.EuCompteCreditTsRepository;
import com.esmc.mcnp.repositories.cm.EuCompteRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("compteCreditTsService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCompteCreditTsServiceImpl extends BaseServiceImpl<EuCompteCreditTs, Long>
		implements EuCompteCreditTsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCompteCreditTsRepository cctsRepo;
	private @Autowired EuCompteRepository compteRepo;

	@Override
	protected BaseRepository<EuCompteCreditTs, Long> getRepository() {
		return cctsRepo;
	}

	@Override
	public boolean verifierCompte(String codeCompte) {
		if (StringUtils.isNotBlank(codeCompte)) {
			EuCompte compte = compteRepo.findOne(codeCompte);
			if (Objects.nonNull(compte)) {
				List<EuCompteCreditTs> ccs = findByCompte(codeCompte);
				if (ccs.size() > 0) {
					Double solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
					return solde == compte.getSolde();
				}
			}
		}
		return false;
	}

	@Override
	public boolean verifierSolde(String codeCompte, Double montant) {
		if (StringUtils.isNotBlank(codeCompte)) {
			EuCompte compte = compteRepo.findOne(codeCompte);
			if (Objects.nonNull(compte)) {
				List<EuCompteCreditTs> ccs = findByCompte(codeCompte);
				if (ccs.size() > 0) {
					Double solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
					if (solde == compte.getSolde()) {
						return montant <= compte.getSolde();
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean verifierSolde(String codeCompte, String produit, Double montant) {
		if (StringUtils.isNotBlank(codeCompte)) {
			EuCompte compte = compteRepo.findOne(codeCompte);
			if (Objects.nonNull(compte)) {
				List<EuCompteCreditTs> ccs = findByCompte(codeCompte);
				if (ccs.size() > 0) {
					Double solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
					if (solde == compte.getSolde() && compte.getSolde() >= montant) {
						ccs = findByCompteAndProduit(codeCompte, produit);
						solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
						return solde >= montant;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean verifierSoldeCredit(String codeCompte, String typeCredit, Double montant) {
		if (StringUtils.isNotBlank(codeCompte)) {
			EuCompte compte = compteRepo.findOne(codeCompte);
			if (Objects.nonNull(compte)) {
				List<EuCompteCreditTs> ccs = findByCompte(codeCompte);
				if (ccs.size() > 0) {
					Double solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
					if (solde == compte.getSolde() && compte.getSolde() >= montant) {
						ccs = findByCompteAndTypeCredit(codeCompte, typeCredit);
						solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
						return solde >= montant;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean verfierSolde(String codeCompte, String produit, String typeCredit, Double montant) {
		if (StringUtils.isNotBlank(codeCompte)) {
			EuCompte compte = compteRepo.findOne(codeCompte);
			if (Objects.nonNull(compte)) {
				List<EuCompteCreditTs> ccs = findByCompte(codeCompte);
				if (ccs.size() > 0) {
					Double solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
					if (solde == compte.getSolde() && compte.getSolde() >= montant) {
						ccs = findByCompteAndProduitAndTypeCredit(codeCompte, produit, typeCredit);
						solde = ccs.stream().mapToDouble(EuCompteCreditTs::getMontant).sum();
						return solde >= montant;
					}
				}
			}
		}
		return false;
	}

	@Override
	public List<EuCompteCreditTs> findByCompte(String codeCompte) {
		return cctsRepo.findByEuCompte_CodeCompte(codeCompte);
	}

	@Override
	public List<EuCompteCreditTs> findByCompteAndProduit(String codeCompte, String produit) {
		return cctsRepo.findByProduit(codeCompte, produit);
	}

	@Override
	public List<EuCompteCreditTs> findByCompteAndProduitAndTypeCredit(String codeCompte, String produit,
			String typeCredit) {
		return cctsRepo.findByProduitAndCredit(codeCompte, produit, typeCredit);
	}

	@Override
	public List<EuCompteCreditTs> findByCompteAndTypeCredit(String codeCompte, String typeCredit) {
		return cctsRepo.findByCompteAndCredit(codeCompte, typeCredit);
	}

	@Override
	public List<EuCompteCreditTs> findByEuBon_BonNumero(String numero) {
		return cctsRepo.findByEuBon_BonNumero(numero);
	}

}
