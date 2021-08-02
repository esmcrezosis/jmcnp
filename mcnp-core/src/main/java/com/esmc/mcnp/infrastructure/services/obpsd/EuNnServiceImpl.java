/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteGeneralRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuNnRepository;
import com.esmc.mcnp.domain.dto.bn.Nn;
import com.esmc.mcnp.domain.entity.ba.EuNn;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneral;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneralPK;
import com.esmc.mcnp.domain.entity.obpsd.EuTypeNn;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 *
 * @author USER
 */
@Service("nnService")
@Repository
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class EuNnServiceImpl extends BaseServiceImpl<EuNn, Integer> implements EuNnService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private EuNnRepository nnDao;

	@Autowired
	private EuCompteGeneralRepository cgDao;

	@Override
	public String EmettreNn(Nn nn) {
		String rep = StringUtils.EMPTY;
		Date date = new Date();
		try {
			/*Long id = nnDao.getLastInsertId();
			if (id == null) {
				id = 0L;
			}*/
			EuTypeNn typeNn = new EuTypeNn();
			typeNn.setCodeTypeNn(nn.getTypeNn());
			System.out.println(nn.getTypeNn());

			EuNn eunn = new EuNn();
			eunn.setDateEmission(date);
			//eunn.setIdNn(id + 1);
			eunn.setEuMembreMorale(null);
			eunn.setEuTypeNn(typeNn);
			eunn.setMontantEmis(nn.getMontant());
			eunn.setMontantRemb(0.0);
			eunn.setSoldeNn(nn.getMontant());
			eunn.setTypeEmission("Manuelle");
			nnDao.save(eunn);

			EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
			cgPK.setCodeCompte("FGS" + nn.getTypeNn());
			cgPK.setCodeTypeCompte("NN");
			cgPK.setService("E");
			EuCompteGeneral cg = cgDao.findOne(cgPK);
			if (cg != null) {
				cg.setSolde(cg.getSolde() + nn.getMontant());
				cgDao.save(cg);
			} else {
				cg = new EuCompteGeneral();
				cg.setId(cgPK);
				cg.setIntitule("FG Source " + nn.getTypeNn());
				cg.setSolde(nn.getMontant());
				cgDao.save(cg);
			}
			rep = "Operation effectuée avec succès!!!";
		} catch (Exception e) {
			rep = "Echec de l'opération : " + e.getLocalizedMessage();
		}
		return rep;
	}

	@Override
	public EuNn EmettreNn(String type_nn, Double montant, Date date) {
		try {
			/*Long id = nnDao.getLastInsertId();
			if (id == null) {
				id = 0L;
			}*/
			EuTypeNn typeNn = new EuTypeNn();
			typeNn.setCodeTypeNn(type_nn);
			EuNn eunn = new EuNn();
			eunn.setDateEmission(date);
			//eunn.setIdNn(id + 1);
			eunn.setEuMembreMorale(null);
			eunn.setEuTypeNn(typeNn);
			eunn.setMontantEmis(montant);
			eunn.setMontantRemb(montant);
			eunn.setSoldeNn(0.0);
			eunn.setTypeEmission("Auto");
			nnDao.save(eunn);

			EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
			cgPK.setCodeCompte("FGS" + type_nn);
			cgPK.setCodeTypeCompte("NN");
			cgPK.setService("E");
			EuCompteGeneral cg = cgDao.findOne(cgPK);
			if (cg != null) {
				cg.setSolde(cg.getSolde() + montant);
				cgDao.save(cg);
			} else {
				cg = new EuCompteGeneral();
				cg.setId(cgPK);
				cg.setIntitule("FG Source " + type_nn);
				cg.setSolde(montant);
				cgDao.save(cg);
			}
			return eunn;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public EuNn EmettreNn(String type_nn, Double montant, String mode) {
		Date date = new Date();
		try {
			/*Long id = nnDao.getLastInsertId();
			if (id == null) {
				id = 0L;
			}*/
			EuTypeNn typeNn = new EuTypeNn();
			typeNn.setCodeTypeNn(type_nn);
			EuNn eunn = new EuNn();
			eunn.setDateEmission(date);
			//eunn.setIdNn(id + 1);
			eunn.setEuMembreMorale(null);
			eunn.setEuTypeNn(typeNn);
			eunn.setMontantEmis(montant);
			eunn.setMontantRemb(montant);
			eunn.setSoldeNn(0.0);
			eunn.setTypeEmission(mode);
			nnDao.save(eunn);

			EuCompteGeneralPK cgPK = new EuCompteGeneralPK();
			cgPK.setCodeCompte("FGS" + type_nn);
			cgPK.setCodeTypeCompte("NN");
			cgPK.setService("E");
			EuCompteGeneral cg = cgDao.findOne(cgPK);
			if (cg != null) {
				cg.setSolde(cg.getSolde() + montant);
				cgDao.save(cg);
			} else {
				cg = new EuCompteGeneral();
				cg.setId(cgPK);
				cg.setIntitule("FG Source " + type_nn);
				cg.setSolde(montant);
				cgDao.save(cg);
			}
			return eunn;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getLastInsertId() {
		return nnDao.getLastInsertId();
	}

	@Override
	public Double getSumNnByTypeNn(String type_nn) {
		return nnDao.getSumNnByTypeNn(type_nn);
	}

	@Override
	public List<EuNn> findSourceNns(String type_nn) {
		return nnDao.findSourceNns(type_nn);
	}

	@Override
	public List<EuNn> findNnByEmetteur(String codeMembre) {
		return nnDao.findNnByEmetteur(codeMembre);
	}

	@Override
	public List<EuNn> findByMembre(String codeMembre) {
		return nnDao.findByEuMembreMorale_codeMembreMorale(codeMembre);
	}

	@Override
	protected BaseRepository<EuNn, Integer> getRepository() {
		return nnDao;
	}

	@Override
	public List<EuNn> getListNnByTypeNnAndCodeMembreAndTypeEmission(String type_nn, String codeMembre,
			String typeEmission) {
		return nnDao.getListNnByTypeNnAndCodeMembreAndTypeEmission(type_nn, codeMembre, typeEmission);
	}

}
