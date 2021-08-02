package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.dao.repository.bc.EuCnpEntreeRepository;
import com.esmc.mcnp.dao.repository.bc.EuCnpRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteRepository;
import com.esmc.mcnp.dao.repository.obps.EuDetailTpagcpRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpPreleverRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpRepository;
import com.esmc.mcnp.dao.repository.others.EuTegcRepository;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.bc.EuCnp;
import com.esmc.mcnp.domain.entity.bc.EuCnpEntree;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.obps.*;
import com.esmc.mcnp.commons.exception.business.CompteNonIntegreException;
import com.esmc.mcnp.commons.exception.business.SoldeInsuffisantException;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("euGcpService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuGcpServiceImplImpl extends CrudServiceImpl<EuGcp, Long> implements EuGcpService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuGcpRepository gcpRepo;
	private @Autowired EuCompteRepository compteRepo;
	private @Autowired EuGcpPreleverRepository gcpPrelRepo;
	private @Autowired EuTegcRepository tegcRepo;
	private @Autowired EuCnpRepository cnpRepo;
	private @Autowired EuDetailTpagcpRepository detTpaRepo;
	private @Autowired EuCnpEntreeRepository cnpEntRepo;

	protected EuGcpServiceImplImpl(EuGcpRepository gcpRepo) {
		super(gcpRepo);
		this.gcpRepo = gcpRepo;
	}

	@Override
	public List<EuGcp> findByEuBon_BonNumero(String numero) {
		return gcpRepo.findByEuBon_BonNumero(numero);
	}

	@Override
	public Long getLastEuGcpInsertedId() {
		return gcpRepo.getLastEuGcpInsertedId();
	}

	@Override
	public List<EuGcp> findGcpByCodeMembre(String codeMembre) {
		return gcpRepo.findGcpByCodeMembre(codeMembre);
	}

	@Override
	public Page<EuGcp> findByMembre(String codeMembre, Pageable pageable) {
		return gcpRepo.findByMembre(codeMembre, pageable);
	}

	@Override
	public Page<EuGcp> findByMembreAndTypeGcp(String codeMembre, String typeGcp, Pageable pageable) {
		return gcpRepo.findByMembreAndTypeGcp(codeMembre, typeGcp, pageable);
	}

	@Override
	public Page<EuGcp> findByMembreAndDateConso(String codeMembre, Date deb, Date fin, Pageable pageable) {
		return gcpRepo.findByMembreAndDateConso(codeMembre, deb, fin, pageable);
	}

	@Override
	public Page<EuGcp> findByDate(Date deb, Date fin, Pageable pageable) {
		return gcpRepo.findByDateConso(deb, fin, pageable);
	}

	@Override
	public Page<EuGcp> findByDateSup(Date dateConso, Pageable pageable) {
		return gcpRepo.findByDateConsoGreaterThan(dateConso, pageable);
	}

	@Override
	public Page<EuGcp> findByDateInf(Date dateConso, Pageable pageable) {
		return gcpRepo.findByDateConsoLessThanEqual(dateConso, pageable);
	}

	@Override
	public Page<EuGcp> findByMembreAndDateSup(String codeMembre, Date dateConso, Pageable pageable) {
		return gcpRepo.findByCodeMembreAndDateConsoGreaterThan(codeMembre, dateConso, pageable);
	}

	@Override
	public Page<EuGcp> findByMembreAndDateInf(String codeMembre, Date dateConso, Pageable pageable) {
		return gcpRepo.findByCodeMembreAndDateConsoLessThanEqual(codeMembre, dateConso, pageable);
	}

	@Override
	public List<EuGcp> findByNumeroBon(String numeroBon) {
		return gcpRepo.findByNumeroBon(numeroBon);
	}

	@Override
	public void preleverGcp(String typeOp, EuCompte compte, EuTegc tegc, Long idTpagcp, EuBon bon, String codeMembre,
			Double montant) throws CompteNonIntegreException, NullPointerException, DataAccessException {

		Date date = new Date();
		double mont_deduit = montant;
		Double solde = gcpRepo.getSoldeByTegc(tegc.getCodeTegc()).orElse(0.0);
		if (solde > 0 && tegc.getSoldeTegc() > 0 && solde.equals(tegc.getSoldeTegc())) {
			List<EuGcp> gcps = findByTegc(tegc.getCodeTegc());
			int compteur = 0;
			while (mont_deduit > 0 && compteur < gcps.size()) {
				EuGcp gcp = gcps.get(compteur);
				if (gcp.getReste() < mont_deduit) {
					Long id = gcpPrelRepo.getLastInsertId();
					if (id == null) {
						id = 0L;
					}
					EuGcpPrelever gcpPrel = new EuGcpPrelever();
					gcpPrel.setIdPrelevement(id + 1);
					gcpPrel.setCodeMembre(gcp.getCodeMembre());
					gcpPrel.setDatePrelevement(date);
					gcpPrel.setEuBon(bon);
					gcpPrel.setEuGcp(gcp);
					gcpPrel.setEuTegc(tegc);
					gcpPrel.setHeurePrelevement(date);
					gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
					gcpPrel.setSourceCredit(gcp.getSource());
					gcpPrel.setMontPrelever(gcp.getReste());
					gcpPrel.setMontRapprocher(0);
					gcpPrel.setIdTpagcp(idTpagcp);
					gcpPrel.setSoldePrelevement(gcp.getReste());
					gcpPrelRepo.save(gcpPrel);

					if (!Objects.nonNull(
							cnpRepo.findCnpBySourceCredit(gcp.getEuCompteCredit().getIdCredit(), gcp.getSource()))) {
						throw new CompteNonIntegreException(
								"Le CNP de certains crédit n'existe pas; Contactez ESMC pour vérifiercation de votre compte!");
					}

					EuCnp cnp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
					if (Objects.nonNull(cnp)) {
						cnp.setMontDebit(cnp.getMontDebit() + gcp.getReste());
						cnp.setSoldeCnp(cnp.getSoldeCnp() - gcp.getReste());
						cnpRepo.save(cnp);

						Long idCnpEntree = cnpEntRepo.getLastInsertedId();
						if (idCnpEntree == null) {
							idCnpEntree = 0L;
						}
						EuCnpEntree cnpEnt = new EuCnpEntree();
						cnpEnt.setIdCnpEntree(idCnpEntree++);
						cnpEnt.setDateEntree(date);
						cnpEnt.setEuCnp(cnp);
						cnpEnt.setMontCnpEntree(gcp.getReste());
						cnpEnt.setTypeCnpEntree(typeOp);
					} else {
						throw new CompteNonIntegreException(
								"Le CNP de certains GCP n'existe pas; Contactez ESMC pour vérifiercation de votre compte!");
					}

					mont_deduit -= gcp.getReste();

					gcp.setMontPreleve(gcp.getMontPreleve() + gcp.getReste());
					gcp.setReste(gcp.getMontGcp() - gcp.getMontPreleve());
					gcpRepo.save(gcp);

					compteur += 1;
				} else {
					Long id = gcpPrelRepo.getLastInsertId();
					if (id == null) {
						id = 0L;
					}
					EuGcpPrelever gcpPrel = new EuGcpPrelever();
					gcpPrel.setIdPrelevement(id + 1);
					gcpPrel.setCodeMembre(gcp.getCodeMembre());
					gcpPrel.setDatePrelevement(date);
					gcpPrel.setEuBon(bon);
					gcpPrel.setEuGcp(gcp);
					gcpPrel.setEuTegc(tegc);
					gcpPrel.setHeurePrelevement(date);
					gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
					gcpPrel.setSourceCredit(gcp.getSource());
					gcpPrel.setMontPrelever(mont_deduit);
					gcpPrel.setMontRapprocher(0);
					gcpPrel.setSoldePrelevement(mont_deduit);
					gcpPrel.setIdTpagcp(idTpagcp);
					gcpPrelRepo.save(gcpPrel);

					if (!Objects.nonNull(
							cnpRepo.findCnpBySourceCredit(gcp.getEuCompteCredit().getIdCredit(), gcp.getSource()))) {
						throw new CompteNonIntegreException();
					}

					EuCnp cnp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
					if (Objects.nonNull(cnp)) {
						cnp.setMontDebit(cnp.getMontDebit() + mont_deduit);
						cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduit);
						cnpRepo.save(cnp);

						Long idCnpEntree = cnpEntRepo.getLastInsertedId();
						if (idCnpEntree == null) {
							idCnpEntree = 0L;
						}
						EuCnpEntree cnpEnt = new EuCnpEntree();
						cnpEnt.setIdCnpEntree(idCnpEntree++);
						cnpEnt.setDateEntree(date);
						cnpEnt.setEuCnp(cnp);
						cnpEnt.setMontCnpEntree(mont_deduit);
						cnpEnt.setTypeCnpEntree(typeOp);
					} else {
						throw new CompteNonIntegreException(
								"Le CNP de certains GCP n'existe pas; Contactez ESMC pour vérifiercation de votre compte!");
					}

					gcp.setMontPreleve(gcp.getMontPreleve() + mont_deduit);
					gcp.setReste(gcp.getMontGcp() - gcp.getMontPreleve());
					gcpRepo.save(gcp);

					mont_deduit = 0;
				}
			}
			tegc.setMontantUtilise(tegc.getMontantUtilise() + montant);
			tegc.setSoldeTegc(tegc.getMontant() - tegc.getMontantUtilise());
			tegc = tegcRepo.save(tegc);

			EuDetailTpagcpPK iddettpa = new EuDetailTpagcpPK(idTpagcp, tegc.getCodeTegc());
			EuDetailTpagcp detTpa = new EuDetailTpagcp();
			detTpa.setId(iddettpa);
			detTpa.setMontant(montant);
			detTpaRepo.save(detTpa);
		} else {
			throw new CompteNonIntegreException("C2: Compte Incohérent : Le solde du TE = " + tegc.getSoldeTegc()
					+ " n'est pas egal à la somme des BL " + solde + " correspondants");
		}
		compte.setSolde(compte.getSolde() - montant);
		compteRepo.save(compte);

	}

	@Override
	public Double getSoldeByMembre(String codeMembre) {
		Optional<Double> opsolde = gcpRepo.getSoldeByMembre(codeMembre);
		if (opsolde.isPresent()) {
			return opsolde.get();
		} else {
			return 0d;
		}
	}

	@Override
	public List<EuGcp> findByTegc(String codeTegc) {
		return gcpRepo.findByTegc(codeTegc);
	}

	@Override
	public Double getSoldeByCodeTegc(String codetegc) {
		return gcpRepo.getSoldeByCodeTegc(codetegc).orElse(0.0);
	}

	@Override
	public List<EuGcp> findByCodeTegc(String codeTegc) {
		return gcpRepo.findByCodeTegc(codeTegc);
	}

	@Override
	public Double getSoldeByTegc(String codetegc) {
		return gcpRepo.getSoldeByTegc(codetegc).orElse(0.0);
	}

	@Override
	public Double getSoldeByTegcAndType(String codetegc, String typeGcp) {
		return gcpRepo.getSoldeByTeAndTypeGcp(codetegc, typeGcp).orElse(0.0);
	}

	@Override
	public List<EuGcp> findByTegcAndType(String codetegc, String typeGcp) {
		return gcpRepo.findByTeAndTypeGcp(codetegc, typeGcp);
	}

	@Override
	public void preleverGcp(String typeGcp, String typeOp, EuCompte compte, EuTegc tegc, Long idTpagcp, EuBon bon,
			String membre, Double montant) throws CompteNonIntegreException, NullPointerException, DataAccessException {
		Date date = new Date();
		double mont_deduit = montant;
		Double solde = gcpRepo.getSoldeByTegc(tegc.getCodeTegc()).orElse(0.0);
		if (solde > 0 && tegc.getSoldeTegc() > 0 && solde.equals(tegc.getSoldeTegc())) {
			List<EuGcp> gcps = Lists.newArrayList();
			if (StringUtils.isNotBlank(typeGcp)) {
				if (typeGcp.equals("CR")) {
					gcps = gcpRepo.findByTeWithCr(tegc.getCodeTegc());
				} else {
					gcps = findByTegcAndType(tegc.getCodeTegc(), typeGcp);
				}
			} else {
				gcps = findByTegcWithoutCr(tegc.getCodeTegc());
			}
			if (gcps.isEmpty()) {
				throw new SoldeInsuffisantException("Il n'y a pas de GCp du type " + typeGcp + " sur ce TE");
			}
			int compteur = 0;
			while (mont_deduit > 0 && compteur < gcps.size()) {
				EuGcp gcp = gcps.get(compteur);
				if (gcp.getReste() < mont_deduit) {
					Long id = gcpPrelRepo.getLastInsertId();
					if (id == null) {
						id = 0L;
					}
					EuGcpPrelever gcpPrel = new EuGcpPrelever();
					gcpPrel.setIdPrelevement(id + 1);
					gcpPrel.setCodeMembre(gcp.getCodeMembre());
					gcpPrel.setDatePrelevement(date);
					gcpPrel.setEuBon(bon);
					gcpPrel.setEuGcp(gcp);
					gcpPrel.setEuTegc(tegc);
					gcpPrel.setHeurePrelevement(date);
					gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
					gcpPrel.setSourceCredit(gcp.getSource());
					gcpPrel.setMontPrelever(gcp.getReste());
					gcpPrel.setMontRapprocher(0);
					gcpPrel.setIdTpagcp(idTpagcp);
					gcpPrel.setSoldePrelevement(gcp.getReste());
					gcpPrelRepo.save(gcpPrel);

					if (!Objects.nonNull(
							cnpRepo.findCnpBySourceCredit(gcp.getEuCompteCredit().getIdCredit(), gcp.getSource()))) {
						throw new CompteNonIntegreException(
								"Le CNP de certains crédit n'existe pas; Contactez ESMC pour vérifiercation de votre compte!");
					}

					EuCnp cnp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
					if (Objects.nonNull(cnp)) {
						cnp.setMontDebit(cnp.getMontDebit() + gcp.getReste());
						cnp.setSoldeCnp(cnp.getSoldeCnp() - gcp.getReste());
						cnpRepo.save(cnp);

						Long idCnpEntree = cnpEntRepo.getLastInsertedId();
						if (idCnpEntree == null) {
							idCnpEntree = 0L;
						}
						EuCnpEntree cnpEnt = new EuCnpEntree();
						cnpEnt.setIdCnpEntree(idCnpEntree++);
						cnpEnt.setDateEntree(date);
						cnpEnt.setEuCnp(cnp);
						cnpEnt.setMontCnpEntree(gcp.getReste());
						cnpEnt.setTypeCnpEntree(typeOp);
					} else {
						throw new CompteNonIntegreException(
								"Le CNP de certains GCP n'existe pas; Contactez ESMC pour vérifiercation de votre compte!");
					}

					mont_deduit -= gcp.getReste();

					gcp.setMontPreleve(gcp.getMontPreleve() + gcp.getReste());
					gcp.setReste(gcp.getMontGcp() - gcp.getMontPreleve());
					gcpRepo.save(gcp);

					compteur += 1;
				} else {
					Long id = gcpPrelRepo.getLastInsertId();
					if (id == null) {
						id = 0L;
					}
					EuGcpPrelever gcpPrel = new EuGcpPrelever();
					gcpPrel.setIdPrelevement(id + 1);
					gcpPrel.setCodeMembre(gcp.getCodeMembre());
					gcpPrel.setDatePrelevement(date);
					gcpPrel.setEuBon(bon);
					gcpPrel.setEuGcp(gcp);
					gcpPrel.setEuTegc(tegc);
					gcpPrel.setHeurePrelevement(date);
					gcpPrel.setIdCredit(gcp.getEuCompteCredit().getIdCredit());
					gcpPrel.setSourceCredit(gcp.getSource());
					gcpPrel.setMontPrelever(mont_deduit);
					gcpPrel.setMontRapprocher(0);
					gcpPrel.setSoldePrelevement(mont_deduit);
					gcpPrel.setIdTpagcp(idTpagcp);
					gcpPrelRepo.save(gcpPrel);

					System.out.println("ID Crédit :" + gcp.getEuCompteCredit().getIdCredit() + "  ===  Source Crédit :"
							+ gcp.getSource());
					if (!Objects.nonNull(
							cnpRepo.findCnpBySourceCredit(gcp.getEuCompteCredit().getIdCredit(), gcp.getSource()))) {
						throw new CompteNonIntegreException();
					}

					EuCnp cnp = cnpRepo.findByEuGcp_IdGcp(gcp.getIdGcp());
					if (Objects.nonNull(cnp)) {
						cnp.setMontDebit(cnp.getMontDebit() + mont_deduit);
						cnp.setSoldeCnp(cnp.getSoldeCnp() - mont_deduit);
						cnpRepo.save(cnp);

						Long idCnpEntree = cnpEntRepo.getLastInsertedId();
						if (idCnpEntree == null) {
							idCnpEntree = 0L;
						}
						EuCnpEntree cnpEnt = new EuCnpEntree();
						cnpEnt.setIdCnpEntree(idCnpEntree++);
						cnpEnt.setDateEntree(date);
						cnpEnt.setEuCnp(cnp);
						cnpEnt.setMontCnpEntree(mont_deduit);
						cnpEnt.setTypeCnpEntree(typeOp);
					} else {
						throw new CompteNonIntegreException(
								"Le CNP de certains GCP n'existe pas; Contactez ESMC pour vérifiercation de votre compte!");
					}

					gcp.setMontPreleve(gcp.getMontPreleve() + mont_deduit);
					gcp.setReste(gcp.getMontGcp() - gcp.getMontPreleve());
					gcpRepo.save(gcp);

					mont_deduit = 0;
				}
			}
			tegc.setMontantUtilise(tegc.getMontantUtilise() + montant);
			tegc.setSoldeTegc(tegc.getMontant() - tegc.getMontantUtilise());
			tegc = tegcRepo.save(tegc);

			EuDetailTpagcpPK iddettpa = new EuDetailTpagcpPK(idTpagcp, tegc.getCodeTegc());
			EuDetailTpagcp detTpa = new EuDetailTpagcp();
			detTpa.setId(iddettpa);
			detTpa.setMontant(montant);
			detTpaRepo.save(detTpa);
		} else {
			throw new CompteNonIntegreException("C2: Compte Incohérent : Le solde du TE = " + tegc.getSoldeTegc()
					+ " n'est pas egal à la somme des BL " + solde + " correspondants");
		}
		compte.setSolde(compte.getSolde() - montant);
		compteRepo.save(compte);
	}

	@Override
	public List<EuGcp> findByGcpMembreAndType(String codeMembre, String typeGcp) {
		return gcpRepo.findGcpByMembreAndType(codeMembre, typeGcp);
	}

	@Override
	public Double getSoldeByMembreAndType(String codeMembre, String typeGcp) {
		return gcpRepo.getSoldeByMembreAndType(codeMembre, typeGcp).orElse(0.0);
	}

	@Override
	public List<EuGcp> findByTegcWithoutCr(String codeTegc) {
		return gcpRepo.findByTeWithoutCr(codeTegc);
	}

	@Override
	public Double getSoldeByTegcWithoutCr(String codeTegc) {
		return gcpRepo.getSoldeByTeWithoutCr(codeTegc).orElse(0.0);
	}

	@Override
	public Double getSoldeByMembreWithoutCr(String codeMembre) {
		return gcpRepo.getSoldeByMembreWithoutCr(codeMembre).orElse(0.0);
	}

	@Override
	public List<EuGcp> findGcpByMembreWithoutCr(String codeMembre) {
		return gcpRepo.findByMembreWithoutCr(codeMembre);
	}

	@Override
	public Page<EuGcp> findByTegcAndType(String codetegc, String typeGcp, Pageable pageable) {
		return gcpRepo.findByTeAndTypeGcp(codetegc, typeGcp, pageable);
	}

	@Override
	public List<EuGcp> findByMembreAndTeAndTypeGcp(String codeMembre, String codeTegc, String typeGcp) {
		return gcpRepo.findByMembreAndTeAndTypeGcp(codeMembre, codeTegc, typeGcp);
	}

	@Override
	public Page<EuGcp> findByMembreAndTeAndTypeGcp(String codeMembre, String codeTegc, String typeGcp,
			Pageable pageable) {
		return gcpRepo.findByMembreAndTeAndTypeGcp(codeMembre, codeTegc, typeGcp, pageable);
	}

}
