package com.esmc.mcnp.services.obps;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dto.obps.TegcView;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.repositories.cm.EuMembreRepository;
import com.esmc.mcnp.repositories.others.EuTegcRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service("euTegcService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTegcServiceImpl extends CrudServiceImpl<EuTegc, String> implements EuTegcService {

	private final EuTegcRepository tegcRepo;
	private final EuMembreRepository membreRepo;
	private final EuMembreMoraleRepository moraleRepo;

	protected EuTegcServiceImpl(EuTegcRepository repository, EuTegcRepository tegcRepo, EuMembreMoraleRepository moraleRepo, EuMembreRepository membreRepo) {
		super(repository);
		this.tegcRepo = tegcRepo;
		this.membreRepo = membreRepo;
		this.moraleRepo = moraleRepo;
	}

	@Override
	public List<EuTegc> findByCodeMembre(String codeMembre) {
		if (codeMembre.endsWith("M")) {
			return tegcRepo.findByCodeMembre(codeMembre);
		} else {
			return tegcRepo.findByCodeMembrePhysique(codeMembre);
		}
	}

	@Override
	public Double getSoldeByCodeMembre(String codeMembre) {
		if (codeMembre.endsWith("M")) {
			return tegcRepo.getSoldeByCodeMembre(codeMembre);
		} else {
			return tegcRepo.getSoldeByCodeMembrePhysique(codeMembre);
		}
	}

	@Override
	public List<TegcView> findbyMembre(String codeMembre) {
		if (codeMembre.endsWith("M")) {
			List<EuTegc> tegcs = tegcRepo.findByEuMembreMorale_CodeMembreMorale(codeMembre);
			return tegcs.stream().map(t -> TegcView.fromEuTegc(t)).collect(Collectors.toList());
		} else {
			return tegcRepo.findByEuMembre_CodeMembre(codeMembre).stream().map(t -> TegcView.fromEuTegc(t))
					.collect(Collectors.toList());
		}
	}

	@Override
	public Double getSoldeByMembreAndTe(String codeMembre, String codeTegc) {
		if (codeMembre.endsWith("M")) {
			return tegcRepo.getSoldeByMembreandTe(codeMembre, codeTegc);
		} else {
			return tegcRepo.getSoldeByMembrePhysiqueandTe(codeMembre, codeTegc);
		}
	}

	@Override
	public List<EuTegc> findByMembreAndTe(String codeMembre, String codeTegc) {
		if (codeMembre.endsWith("M")) {
			return tegcRepo.fetchByMembreAndTe(codeMembre, codeTegc);
		} else {
			return tegcRepo.fetchByMembrePysiqueAndTe(codeMembre, codeTegc);
		}
	}

	@Override
	public EuTegc findTegcByCodeMembreAndNomTegc(String codeMembre, String nomTegc) {
		if (codeMembre.endsWith("M")) {
			return tegcRepo.findTegcByCodeMembreAndNomTegc(codeMembre, nomTegc);
		} else {
			return tegcRepo.findTegcByCodeMembrePhysiqueAndNomTegc(codeMembre, nomTegc);
		}
	}

	@Override
	public List<EuTegc> findByCodeMembrePhysique(String codeMembre) {
		return tegcRepo.findByCodeMembrePhysique(codeMembre);
	}

	@Override
	public EuTegc findByCodeTegc(String codeTegc) {
		return tegcRepo.findByCodeTegc(codeTegc);
	}

	@Override
	public List<EuTegc> findTebyMembre(String codeMembre) {
		if (codeMembre.endsWith("M")) {
			return tegcRepo.findByEuMembreMorale_CodeMembreMorale(codeMembre);
		} else {
			return tegcRepo.findByEuMembre_CodeMembre(codeMembre);
		}
	}

	@Override
	public EuTegc creerTe(String codeMembre) {
		EuMembre membre = null;
		EuMembreMorale morale = null;
		if (codeMembre.endsWith("P")) {
			membre = membreRepo.findOne(codeMembre);
		} else {
			morale = moraleRepo.findOne(codeMembre);
		}
		Date date = new Date();
		String codeTegc = "TEGCP" + codeMembre + "00001";
		EuTegc tegc = new EuTegc();
		tegc.setCodeTegc(codeTegc);
		tegc.setDateTegc(date);
		if (codeMembre.endsWith("P")) {
			tegc.setEuMembre(membre);
			tegc.setEuMembreMorale(null);
			tegc.setNomTegc(membre.getNomMembre() + " " + membre.getPrenomMembre());
			tegc.setNomProduit("Prestation");
			tegc.setTypeTegc("PRESTATAIRE");
		} else {
			tegc.setEuMembreMorale(morale);
			tegc.setEuMembre(null);
			tegc.setNomTegc(morale.getRaisonSociale());
			tegc.setNomProduit(morale.getDomaineActivite());
			tegc.setTypeTegc("DISTRIBUTEUR");
		}
		tegc.setFormel(0);
		tegc.setIdFiliere(null);
		tegc.setIdUtilisateur(null);
		tegc.setMdv(12);
		tegc.setMontant(0.0);
		tegc.setMontantUtilise(0.0);
		tegc.setSoldeTegc(0.0);
		tegc.setSpecial(0);
		tegc.setSubvention(0);
		tegc.setNonrecurrent(1);
		tegc.setPeriode1(0);
		tegc.setPeriode2(0);
		tegc.setPeriode3(0);
		tegc.setOrdinaire(1);
		tegc.setRecurrentIllimite(0);
		tegc.setRecurrentLimite(0);
		tegc.setRegimeTva(0);
		tegc.setTranchePayement(0);
		tegc = create(tegc);
		return tegc;
	}

	@Override
	public Page<EuTegc> findByMembre(String codeMembre, Pageable pageable) {
		if (codeMembre.endsWith("P")) {
			return tegcRepo.findByEuMembre_CodeMembre(codeMembre, pageable);
		} else {
			return tegcRepo.findByEuMembreMorale_CodeMembreMorale(codeMembre, pageable);
		}
	}

}
