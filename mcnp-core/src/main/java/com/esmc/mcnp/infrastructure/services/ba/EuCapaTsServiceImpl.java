package com.esmc.mcnp.infrastructure.services.ba;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.ba.EuCapaTsRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuFnRepository;
import com.esmc.mcnp.domain.entity.ba.EuCapaTs;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapa;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.domain.entity.smcipn.EuFn;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("capatsServcie")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCapaTsServiceImpl extends BaseServiceImpl<EuCapaTs, String> implements EuCapaTsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuCapaTsRepository capatsRepo;
	@Autowired
	private EuCompteRepository compteRepo;
	@Autowired
	private EuCompteCreditCapaRepository ccCapaRepo;
	@Autowired
	private EuFnRepository fnRepo;

	@Override
	protected BaseRepository<EuCapaTs, String> getRepository() {
		return capatsRepo;
	}

	@Override
	public Boolean updateList(List<EuCapaTs> capatss, double montant) {
		if (capatss.size() > 0) {
			try {
				int compteur = 0;
				double mont = montant;
				while (mont > 0 && compteur < capatss.size()) {
					EuCapaTs capats = capatss.get(compteur);
					if (capats.getMontantSolde() < mont) {
						mont -= capats.getMontantSolde();
						compteur++;
						capats.setMontantUtiliser(capats.getMontantUtiliser() + capats.getMontantSolde());
						capats.setMontantSolde(0.0);
						update(capats);
					} else {
						capats.setMontantUtiliser(capats.getMontantUtiliser() + mont);
						capats.setMontantSolde(capats.getMontantSolde() - mont);
						update(capats);
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
	public boolean updateListCapaTs(List<EuCapaTs> capas, EuBon bon, EuCompteCredit cc, String typeR, Date date,
			double capa) {
		if (!capas.isEmpty()) {
			System.out.println("Nombre CAPA :" + capas.size());
			try {
				double mont_capa = capa;
				int i = 0;
				while (mont_capa > 0 && i < capas.size()) {
					EuCapaTs c = capas.get(i);
					if (mont_capa > c.getMontantSolde()) {
						System.out.println("cas 1");
						EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
						EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
						ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
						ccCapaPK.setIdCredit(cc.getIdCredit());
						ccCapa.setId(ccCapaPK);
						ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
						ccCapa.setMontant(c.getMontantSolde());
						ccCapa.setEuBon(bon);
						ccCapaRepo.save(ccCapa);

						System.out.println("Jusqu'ici tout va bien 11");

						Long idFn = fnRepo.getLastFnInsertedId();
						if (idFn == null) {
							idFn = 1L;
						} else {
							idFn += 1;
						}
						EuFn fn = new EuFn();
						fn.setIdFn(idFn);
						fn.setTypeFn("I" + typeR);
						fn.setDateFn(date);
						fn.setEuCapa(c.getEuCapa());
						fn.setMontant(c.getMontantSolde());
						fn.setSortie(0);
						fn.setEntree(0);
						fn.setSolde(0);
						fn.setOrigineFn(1);
						fn.setMtSolde(c.getMontantSolde());
						fnRepo.save(fn);

						mont_capa -= c.getMontantSolde();

						c.setMontantUtiliser(c.getMontantUtiliser() + c.getMontantSolde());
						c.setMontantSolde(0.0);
						update(c);
						i++;

					} else {

						System.out.println("cas 2");
						EuCompteCreditCapa ccCapa = new EuCompteCreditCapa();
						EuCompteCreditCapaPK ccCapaPK = new EuCompteCreditCapaPK();
						ccCapaPK.setCodeCapa(c.getEuCapa().getCodeCapa());
						ccCapaPK.setIdCredit(cc.getIdCredit());
						ccCapa.setId(ccCapaPK);
						ccCapa.setCodeProduit(cc.getEuProduit().getCodeProduit());
						ccCapa.setMontant(mont_capa);
						ccCapa.setEuBon(bon);
						ccCapaRepo.save(ccCapa);

						Long idFn = fnRepo.getLastFnInsertedId();
						if (idFn == null) {
							idFn = 1L;
						} else {
							idFn += 1;
						}
						EuFn fn = new EuFn();
						fn.setIdFn(idFn);
						fn.setTypeFn("I" + typeR);
						fn.setDateFn(date);
						fn.setEuCapa(c.getEuCapa());
						fn.setMontant(mont_capa);
						fn.setSortie(0);
						fn.setEntree(0);
						fn.setSolde(0);
						fn.setMtSolde(mont_capa);
						fn.setOrigineFn(1);
						fnRepo.save(fn);

						c.setMontantUtiliser(c.getMontantUtiliser() + mont_capa);
						c.setMontantSolde(c.getMontantSolde() - mont_capa);
						update(c);

						mont_capa = 0;
					}
				}
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Boolean verifierSolde(String codeCompte, Double montant) {
		if (StringUtils.isNotBlank(codeCompte) && montant > 0) {
			List<EuCapaTs> capaTss = ListUtils.emptyIfNull(findByCompte(codeCompte));
			if (capaTss.size() > 0) {
				double somme = capaTss.stream().mapToDouble(EuCapaTs::getMontantSolde).sum();
				System.out.println("Somme des CAPA TS : " + somme + " <-> Montant à vérifier : " + montant);
				return somme >= montant;
			}
		}
		return false;
	}

	@Override
	public Boolean verifierCompte(String codeCompte, Double soldeCompte) {
		if (StringUtils.isNotBlank(codeCompte) && soldeCompte > 0) {
			if (verifierCompte(codeCompte)) {
				List<EuCapaTs> capaTss = ListUtils.emptyIfNull(findByCompte(codeCompte));
				if (capaTss.size() > 0) {
					double somme = capaTss.stream().mapToDouble(EuCapaTs::getMontantSolde).sum();
					System.out.println("Somme des CAPA TS : " + somme + " <-> Montant à vérifier : " + soldeCompte);
					return somme >= soldeCompte;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean verifierCompte(String codeCompte) {
		EuCompte compte = compteRepo.findOne(codeCompte);
		if (Objects.nonNull(compte)) {
			System.out.println("Vérification du Compte " + compte.getCodeCompte());
			List<EuCapaTs> capaTss = ListUtils.emptyIfNull(findByCompte(codeCompte));
			System.out.println("Nombre de CAPA " + capaTss.size());
			if (capaTss.size() > 0) {
				double somme = capaTss.stream().mapToDouble(EuCapaTs::getMontantSolde).sum();
				System.out.println("Solde du Compte : " + compte.getSolde() + " <-> Somme des CAPA TS : " + somme);
				return somme == compte.getSolde();
			}
		}
		return false;
	}

	@Override
	public List<EuCapaTs> findByCompte(String codeCompte) {
		return capatsRepo.findCapaTsByCompte(codeCompte);
	}

	@Override
	public List<EuCapaTs> findByCompteAndOrigine(String codeCompte, String origine) {
		return capatsRepo.findByCompteAndOrigine(codeCompte, origine);
	}

	@Override
	public List<EuCapaTs> findByCompteAndOrigineAndProduit(String codeCompte, String origine, String produit) {
		return capatsRepo.findByCompteAndProduitAndOrigine(codeCompte, produit, origine);
	}

	@Override
	public List<EuCapaTs> findByEuBon_BonNumero(String numero) {
		return capatsRepo.findByEuBon_BonNumero(numero);
	}

	@Override
	public List<EuCapaTs> findByProduitAndMembre(String produit, String codeMembre) {
		return capatsRepo.findByProduitAndMembre(produit, codeMembre);
	}
	
	

}
