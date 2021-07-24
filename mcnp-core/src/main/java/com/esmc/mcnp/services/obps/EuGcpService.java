package com.esmc.mcnp.services.obps;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.obps.EuGcp;
import com.esmc.mcnp.model.obps.EuTegc;
import com.esmc.mcnp.services.base.CrudService;

public interface EuGcpService extends CrudService<EuGcp, Long> {

	public List<EuGcp> findByEuBon_BonNumero(String numero);

	public Long getLastEuGcpInsertedId();

	public List<EuGcp> findGcpByCodeMembre(String codeMembre);

	Page<EuGcp> findByMembre(String codeMembre, Pageable pageable);

	Page<EuGcp> findByMembreAndTypeGcp(String codeMembre, String typeGcp, Pageable pageable);

	Page<EuGcp> findByMembreAndDateConso(String codeMembre, Date deb, Date fin, Pageable pageable);

	Page<EuGcp> findByDate(Date deb, Date fin, Pageable pageable);

	Page<EuGcp> findByDateSup(Date dateConso, Pageable pageable);

	Page<EuGcp> findByDateInf(Date dateConso, Pageable pageable);

	Page<EuGcp> findByMembreAndDateSup(String codeMembre, Date dateConso, Pageable pageable);

	Page<EuGcp> findByMembreAndDateInf(String codeMembre, Date dateConso, Pageable pageable);

	public List<EuGcp> findByGcpMembreAndType(String codeMembre, String typeGcp);

	public Double getSoldeByMembreAndType(String codeMembre, String typeGcp);

	public List<EuGcp> findByTegc(String codeTegc);

	public List<EuGcp> findByTegcWithoutCr(String codeTegc);

	public Double getSoldeByTegcWithoutCr(String codeTegc);

	public Double getSoldeByMembreWithoutCr(String codeMembre);

	public List<EuGcp> findGcpByMembreWithoutCr(String codeMembre);

	public List<EuGcp> findByCodeTegc(String codeTegc);

	public Double getSoldeByTegc(String codetegc);

	public Double getSoldeByTegcAndType(String codetegc, String typeGcp);

	List<EuGcp> findByTegcAndType(String codetegc, String typeGcp);

	Page<EuGcp> findByTegcAndType(String codetegc, String typeGcp, Pageable pageable);

	List<EuGcp> findByMembreAndTeAndTypeGcp(String codeMembre, String codeTegc, String typeGcp);

	Page<EuGcp> findByMembreAndTeAndTypeGcp(String codeMembre, String codeTegc, String typeGcp, Pageable pageable);

	public Double getSoldeByCodeTegc(String codetegc);

	public List<EuGcp> findByNumeroBon(String numeroBon);

	public Double getSoldeByMembre(String codeMembre);

	public void preleverGcp(String typeOp, EuCompte compte, EuTegc tegc, Long idTpagcp, EuBon bon, String membre,
			Double montant) throws CompteNonIntegreException, NullPointerException, DataAccessException;

	public void preleverGcp(String typeGcp, String typeOp, EuCompte compte, EuTegc tegc, Long idTpagcp, EuBon bon,
			String membre, Double montant) throws CompteNonIntegreException, NullPointerException, DataAccessException;
}
