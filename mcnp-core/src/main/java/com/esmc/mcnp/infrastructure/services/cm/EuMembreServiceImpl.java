package com.esmc.mcnp.services.cm;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.commons.dynamicquery.DynamicQuery;
import com.esmc.mcnp.dto.cm.Membre;
import com.esmc.mcnp.mapper.cm.MembreMapper;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.org.EuAgenceService;

@Service("euMembreService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMembreServiceImpl extends BaseServiceImpl<EuMembre, String> implements EuMembreService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final EuMembreRepository membreRepo;
	private final EuAgenceOddService agenceOddService;
	private final EuAgenceService agenceService;
	private final MembreMapper membreMapper;
	private final DynamicQuery query;

	@Autowired
	public EuMembreServiceImpl(EuMembreRepository membreRepo, EuAgenceOddService agenceOddService,
			EuAgenceService agenceService, MembreMapper membreMapper, DynamicQuery query) {
		this.membreRepo = membreRepo;
		this.agenceOddService = agenceOddService;
		this.agenceService = agenceService;
		this.membreMapper = membreMapper;
		this.query = query;
	}

	@Override
	protected BaseRepository<EuMembre, String> getRepository() {
		return membreRepo;
	}

	@Override
	public String genererCodeMembre(String codeAgence) {
		String maxCode = membreRepo.getMaxCodeByAgence(codeAgence);
		if (StringUtils.isNotBlank(maxCode)) {
			String numero = maxCode.substring(12, 18);
			Long id = Long.valueOf(numero);
			id++;
			return codeAgence + StringUtils.leftPad(id.toString(), 7, "0") + "P";
		} else {
			return codeAgence + StringUtils.leftPad("1", 7, "0") + "P";
		}
	}

	@Override
	public String genererCodeMembreOdd(Integer id) {
		EuAgencesOdd agencesOdd = agenceOddService.getById(id);
		String maxCode = membreRepo.getMaxCodeByAgenceOdd(id);
		if (StringUtils.isNotBlank(maxCode)) {
			String numero = maxCode.substring(12, 18);
			Long num = Long.valueOf(numero);
			id++;
			return agencesOdd.getCodeAgencesOdd() + StringUtils.leftPad(id.toString(), 7, "0") + "P";
		} else {
			return agencesOdd.getCodeAgencesOdd() + StringUtils.leftPad("1", 7, "0") + "P";
		}
	}

	@Override
	public EuMembre findByCodeMembre(String codeMembre) {
		return membreRepo.findByCodeMembre(codeMembre);
	}

	@Override
	public EuMembre createMembre(String nom, String prenom, Date dateNais, String sexe, String lieuNais, String sitFam,
			Integer nbreEnf, String adresse, String quartier, String ville, String telephone, String bp, String email,
			String profession, String codeAgence, Integer idAgenceOdd) {
		String codeMembre = null;
		EuCanton canton = null;
		EuAgencesOdd agencesOdd = null;
		EuAgence agence = null;
		if (StringUtils.isNotBlank(codeAgence)) {
			codeMembre = genererCodeMembre(codeAgence);
			agence = agenceService.getById(codeAgence);
			canton = new EuCanton(agence.getIdCanton());
		} else if (idAgenceOdd != null) {
			codeMembre = genererCodeMembreOdd(idAgenceOdd);
			agencesOdd = agenceOddService.getById(idAgenceOdd);
			canton = agencesOdd.getEuCanton();
		} else {
			agencesOdd = agenceOddService.findBySource(true);
			canton = agencesOdd.getEuCanton();
		}
		EuMembre membre = new EuMembre();
		membre.setCodeMembre(codeMembre).setNomMembre(nom).setPrenomMembre(prenom).setDateNaisMembre(dateNais)
				.setLieuNaisMembre(lieuNais).setSexeMembre(sexe).setSitfamMembre(sitFam).setNbrEnfMembre(nbreEnf)
				.setQuartierMembre(quartier).setVilleMembre(ville).setTelMembre(telephone).setBpMembre(bp)
				.setEmailMembre(email).setProfessionMembre(profession).setAutoEnroler("O").setEtatMembre("N")
				.setDesactiver(0).setDateIdentification(new Date()).setHeureIdentification(LocalTime.now())
				.setAgencesOdd(agencesOdd).setEuAgence(agence).setEuCanton(canton);
		return membreRepo.save(membre);
	}

	@Override
	public EuMembre createMembre(String nom, String prenom, Date dateNais, String lieuNais, String sexe, String sitFam,
			Integer nbreEnf, String adresse, String quartier, String ville, String telephone, String bp, String email,
			String profession, String nomMere, String nomPere, String codeAgence, Integer idAgenceOdd) {
		String codeMembre = null;
		EuCanton canton = null;
		EuAgencesOdd agencesOdd = null;
		EuAgence agence = null;
		if (StringUtils.isNotBlank(codeAgence)) {
			codeMembre = genererCodeMembre(codeAgence);
			agence = agenceService.getById(codeAgence);
			canton = new EuCanton(agence.getIdCanton());
		} else if (idAgenceOdd != null) {
			codeMembre = genererCodeMembreOdd(idAgenceOdd);
			agencesOdd = agenceOddService.getById(idAgenceOdd);
			canton = agencesOdd.getEuCanton();
		} else {
			agencesOdd = agenceOddService.findBySource(true);
			canton = agencesOdd.getEuCanton();
		}
		EuMembre membre = new EuMembre();
		membre.setNomMembre(nom).setPrenomMembre(prenom).setDateNaisMembre(dateNais).setLieuNaisMembre(lieuNais)
				.setSexeMembre(sexe).setSitfamMembre(sitFam).setNbrEnfMembre(nbreEnf).setMereMembre(nomMere)
				.setPereMembre(nomPere).setQuartierMembre(quartier).setVilleMembre(ville).setTelMembre(telephone)
				.setBpMembre(bp).setEmailMembre(email).setProfessionMembre(profession).setAutoEnroler("O")
				.setEtatMembre("N").setDesactiver(0).setDateIdentification(new Date())
				.setHeureIdentification(LocalTime.now()).setAgencesOdd(agencesOdd).setEuAgence(agence)
				.setEuCanton(canton);
		return membreRepo.save(membre);
	}

	@Override
	public List<EuMembre> findByPortableAndDesactiver(String telephone, Integer desactiver) {
		return membreRepo.findByPortableMembreLikeAndDesactiver(telephone, desactiver);
	}

	@Override
	public List<EuMembre> findByEmailAndDesactiver(String email, Integer desactiver) {
		return membreRepo.findByEmailMembreLikeAndDesactiver(email, desactiver);
	}

	@Override
	public List<EuMembre> findByPortable(String telephone) {
		return membreRepo.findByPortableMembre(telephone);
	}

	@Override
	public List<EuMembre> findByEmail(String email) {
		return membreRepo.findByEmailMembre(email);
	}

	@Override
	public List<EuMembre> getListActivatedMembre() {
		return membreRepo.getListActivatedMembre();
	}

	@Override
	public List<byte[]> getListActivatedEmpreintes() {
		return membreRepo.getListActivatedEmpreintes();
	}

	@Override
	public EuMembre createAdmin(Membre membre) {
		String code = membreRepo.findMaxCodeByKey("000000000000%");
		if (StringUtils.isBlank(code)) {
			code = "0000000000000000001P";
		} else {
			String ordre = code.substring(12, 19);
			String numeroOrdre = String.valueOf(Integer.parseInt(ordre) + 1);
			code = StringUtils.leftPad(numeroOrdre, 19, "0") + "P";
		}
		EuMembre euMembre = membreMapper.toEuMembre(membre);
		euMembre.setAgencesOdd(agenceOddService.findBySource(true));
		euMembre.setCodeMembre(code);
		return membreRepo.save(euMembre);
	}

	@Override
	public String findMembreInfo(String codeMembre) {
		String sql = "select nom_membre, prenom_membre from eu_membre where code_membre like ?";
		Map<String, String> result = query.nativeQueryMap(sql, codeMembre);
		if (result.values().size() > 0) {
			return result.get("nom_membre") + " " + result.get("prenom_membre");
		}
		return null;
	}

	@Override
	public String[] getMembreInfo(String codeMembre) {
		String sql = "select nom_membre, prenom_membre from eu_membre where code_membre like ?";
		Map<String, String> result = query.nativeQueryMap(sql, codeMembre);
		if (result.values().size() > 0) {
			String[] reponse = new String[2];
			reponse[0] = result.get("nom_membre");
			reponse[1] = result.get("prenom_membre");
			return reponse;
		}
		return null;
	}
}
