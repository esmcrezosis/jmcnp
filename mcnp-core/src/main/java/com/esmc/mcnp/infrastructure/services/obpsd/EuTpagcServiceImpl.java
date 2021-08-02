package com.esmc.mcnp.infrastructure.services.obpsd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuTpagcpRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("tpagcpService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTpagcServiceImpl extends BaseServiceImpl<EuTpagcp, Long> implements EuTpagcpService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EuTpagcpRepository tpagcpRepo;

    @Override
    protected BaseRepository<EuTpagcp, Long> getRepository() {
        return tpagcpRepo;
    }

    @Override
    public List<EuTpagcp> findByCodeCompte(String codeCompte) {
        return tpagcpRepo.findByCodeCompte(codeCompte);
    }

    @Override
    public double getSumSoldeTpagcpbyCompte(String codeCompte) {
        return tpagcpRepo.getSumSoldeTpagcpbyCompte(codeCompte);
    }

    @Override
    public List<EuTpagcp> getAll() {
        return tpagcpRepo.getAll();
    }

    @Override
    public EuTpagcp getById(Long id) {
        return tpagcpRepo.getById(id);
    }

    @Override
    public double getSumEchuTpagcpbyCompte(String codeCompte) {
        return tpagcpRepo.getSumEchuTpagcpbyCompte(codeCompte);
    }

    @Override
    public List<EuTpagcp> findByMembreAndModeReg(String codeMembre, String modeReglement) {
        return tpagcpRepo.findByMembreAndModeReg(codeMembre, modeReglement);
    }

    @Override
    public List<EuTpagcp> findEchuByMembreAndModeReg(String codeMembre, String modeReglement) {
        return tpagcpRepo.findEchuByMembreAndModeReg(codeMembre, modeReglement);
    }

    @Override
    public List<EuTpagcp> findByIds(List<Long> ids) {
        return tpagcpRepo.findbyIds(ids);
    }

    @Override
    public Double getSumSoldeByIds(List<Long> ids) {
        return tpagcpRepo.getSumByIds(ids);
    }

    @Override
    public List<EuTpagcp> findByEuCompte_CodeCompte(String codeCompte) {
        return tpagcpRepo.findByEuCompte_CodeCompte(codeCompte);
    }

    @Override
    public Long getLastInsertedId() {
        return tpagcpRepo.getLastInsertedId();
    }

    @Override
    public Page<EuTpagcp> getAll(Pageable pageable) {
        return tpagcpRepo.findAll(pageable);
    }

    @Override
    public Page<EuTpagcp> findByMembreAndModeReg(String codeMembre, String modeReglement, Pageable pageable) {
        return tpagcpRepo.findByMembreAndModeReg(codeMembre, modeReglement, pageable);
    }

    @Override
    public Page<EuTpagcp> findAll(Pageable pageable) {
        return tpagcpRepo.findAll(pageable);
    }

    @Override
    public Page<EuTpagcp> findAll(String codeMembre, Pageable pageable) {
        return tpagcpRepo.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuTpagcp> findByMembreAndDate(String codeMembre, LocalDate dateDeb, LocalDate dateFin, Pageable pageable) {
        return tpagcpRepo.findByCodeMembreAndDateDebBetween(codeMembre, dateDeb, dateFin, pageable);
    }

    @Override
    public Page<EuTpagcp> findByMembreDateInf(String codeMembre, LocalDate dateDeb, Pageable pageable) {
        return tpagcpRepo.findByCodeMembreAndDateDebLessThanEqual(codeMembre, dateDeb, pageable);
    }

    @Override
    public Page<EuTpagcp> findByMembreDateSup(String codeMembre, LocalDate dateDeb, Pageable pageable) {
        return tpagcpRepo.findByCodeMembreAndDateDebGreaterThan(codeMembre, dateDeb, pageable);
    }

    @Override
    public List<EuTpagcp> findByMembre(String codeMembre) {
        return tpagcpRepo.findByCodeMembre(codeMembre);
    }

    @Override
    public List<EuTpagcp> findByDateDebut(LocalDate datedeb) {
        return tpagcpRepo.findByDateDeb(datedeb);
    }

    @Override
    public List<EuTpagcp> findTpagcpByDateEntre(LocalDate datedeb, LocalDate dateFin) {
        return tpagcpRepo.findByDate(datedeb, dateFin);
    }

    @Override
    public List<EuTpagcp> findByDateDebBetween(LocalDate datedeb, LocalDate dateFin) {
        return tpagcpRepo.findByDateDebBetween(datedeb, dateFin);
    }

    @Override
    public Page<EuTpagcp> findByDateDebBetween(LocalDate datedeb, LocalDate dateFin, Pageable pageable) {
        return tpagcpRepo.findByDateDebBetween(datedeb, dateFin, pageable);
    }

    @Override
    public List<Long> findAllByCodeMembre(String codeMembre) {
        return tpagcpRepo.findAllByCodeMembre(codeMembre);
    }

    @Override
    public List<EuTpagcp> findByDateDebutSup(LocalDate datedeb) {
        return tpagcpRepo.findByDateDebSup(datedeb);
    }

    @Override
    public Page<EuTpagcp> findByDateDebInf(LocalDate datedeb, Pageable pageable) {
        return tpagcpRepo.findByDateDebInf(datedeb, pageable);
    }

    @Override
    public Page<EuTpagcp> findByDateDebutSup(LocalDate datedeb, Pageable pageable) {
        return tpagcpRepo.findByDateDebSup(datedeb, pageable);
    }

    @Override
    public List<EuTpagcp> findByDateDebInf(LocalDate datedeb) {
        return tpagcpRepo.findByDateDebInf(datedeb);
    }

    @Override
    public List<EuTpagcp> findByDateFin(LocalDate datedeb, LocalDate dateFin) {
        return tpagcpRepo.findByDateFin(datedeb, dateFin);
    }

    @Override
    public List<EuTpagcp> findByDateFinBetween(LocalDate datedeb, LocalDate dateFin) {
        return tpagcpRepo.findByDateFinBetween(datedeb, dateFin);
    }

    @Override
    public List<EuTpagcp> findByDateFin(LocalDate dateFin) {
        return tpagcpRepo.findByDateFin(dateFin);
    }

    @Override
    public List<EuTpagcp> findByDateFinSup(LocalDate dateFin) {
        return tpagcpRepo.findByDateFinSup(dateFin);
    }

    @Override
    public List<EuTpagcp> findByDateFinInf(LocalDate dateFin) {
        return tpagcpRepo.findByDateFinInf(dateFin);
    }

    @Override
    public List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin) {
        return tpagcpRepo.findByDateDebAndFin(deb, fin);
    }

    @Override
    public List<EuTpagcp> findByMembre(String codeMembre, Integer ntf) {
        return tpagcpRepo.findByCodeMembreAndNtf(codeMembre, ntf);
    }

    @Override
    public List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin, Integer ntf) {
        return tpagcpRepo.findByDateDebAndFinAndNtf(deb, fin, ntf);
    }

    @Override
    public List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin, int page) {
        return tpagcpRepo.findByDateDebAndFin(deb, fin, PageRequest.of(0, page));
    }

    @Override
    public List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin, Integer ntf, int page) {
        return tpagcpRepo.findByDateDebAndFinAndNtf(deb, fin, ntf, PageRequest.of(0, page));
    }

}
