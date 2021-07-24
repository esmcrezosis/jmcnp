package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.commons.dynamicquery.DynamicQuery;
import com.esmc.mcnp.dto.cm.MembreMorale;
import com.esmc.mcnp.mapper.cm.MembreMoraleMapper;
import com.esmc.mcnp.model.cm.EuStatutJuridique;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;
import com.esmc.mcnp.services.odd.EuAgenceOddService;
import com.esmc.mcnp.services.org.EuAgenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("euMembreMoraleService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMembreMoraleServiceImpl extends BaseServiceImpl<EuMembreMorale, String>
        implements EuMembreMoraleService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final EuMembreMoraleRepository moraleRepo;
    private final MembreMoraleMapper membreMoraleMapper;
    private final EuAgenceOddService agenceOddService;
    private final EuAgenceService agenceService;
    private final DynamicQuery query;

    @Autowired
    public EuMembreMoraleServiceImpl(EuMembreMoraleRepository moraleRepo, MembreMoraleMapper membreMoraleMapper,
                                     EuAgenceService agenceService, EuAgenceOddService agenceOddService,
                                     DynamicQuery query) {
        this.moraleRepo = moraleRepo;
        this.membreMoraleMapper = membreMoraleMapper;
        this.agenceOddService = agenceOddService;
        this.agenceService = agenceService;
        this.query = query;
    }

    @Override
    protected BaseRepository<EuMembreMorale, String> getRepository() {
        return moraleRepo;
    }

    @Override
    public String generateCodeMembre(String codeAgence) {
        String maxCode = moraleRepo.getMaxCodeByAgence(codeAgence);
        if (StringUtils.isNotBlank(maxCode)) {
            String numero = maxCode.substring(12, 18);
            Long id = Long.valueOf(numero);
            id++;
            return codeAgence + StringUtils.leftPad(id.toString(), 7, "0") + "M";
        } else {
            return codeAgence + StringUtils.leftPad("1", 7, "0") + "M";
        }
    }

    @Override
    public String generateCodeMembreOdd(Integer id) {
        EuAgencesOdd agencesOdd = agenceOddService.getById(id);
        String maxCode = moraleRepo.getMaxCodeByAgenceOdd(id);
        if (StringUtils.isNotBlank(maxCode)) {
            String numero = maxCode.substring(12, 18);
            Long num = Long.valueOf(numero);
            id++;
            return agencesOdd.getCodeAgencesOdd() + StringUtils.leftPad(id.toString(), 7, "0") + "M";
        } else {
            return agencesOdd.getCodeAgencesOdd() + StringUtils.leftPad("1", 7, "0") + "M";
        }
    }

    @Override
    public EuMembreMorale createMembre(String raisonSociale, String codeStatut, String numeroRegistre,
                                       Date dateCreation, String domaineActivite, String adresse,
                                       String bp, String quartier, String ville, String telephone,
                                       String portable, String email, String siteWeb,
                                       String typeFournisseur, String codeAgence, Integer idAgenceOdd) {
        String codeMembre = null;
        EuCanton canton = null;
        EuAgencesOdd agencesOdd = null;
        EuAgence agence = null;
        if (StringUtils.isNotBlank(codeAgence)) {
            codeMembre = generateCodeMembre(codeAgence);
            agence = agenceService.getById(codeAgence);
            canton = new EuCanton(agence.getIdCanton());
        } else if (idAgenceOdd != null) {
            codeMembre = generateCodeMembreOdd(idAgenceOdd);
            agencesOdd = agenceOddService.getById(idAgenceOdd);
            canton = agencesOdd.getEuCanton();
        } else {
            agencesOdd = agenceOddService.findBySource(true);
            canton = agencesOdd.getEuCanton();
        }
        EuMembreMorale membreMorale = new EuMembreMorale();
        membreMorale.setCodeMembreMorale(codeMembre)
                .setRaisonSociale(raisonSociale)
                .setEuStatutJuridique(new EuStatutJuridique(codeStatut))
                .setNumRegistreMembre(numeroRegistre)
                .setDomaineActivite(domaineActivite)
                .setBpMembre(bp)
                .setVilleMembre(ville)
                .setQuartierMembre(quartier)
                .setTelMembre(telephone)
                .setPortableMembre(portable)
                .setEmailMembre(email)
                .setSiteWeb(siteWeb)
                .setTypeFournisseur(typeFournisseur)
                .setDateIdentification(new Date())
                .setHeureIdentification(LocalTime.now())
                .setAutoEnroler("O")
                .setEuAgence(agence)
                .setAgencesOdd(agencesOdd)
                .setDesactiver(0)
                .setEtatMembre("N")
                .setEuCanton(canton);
        return moraleRepo.save(membreMorale);
    }

    @Override
    public EuMembreMorale findByRaisonSociale(String raison_sociale) {
        return moraleRepo.findByRaisonSociale(raison_sociale);
    }

    @Override
    public EuMembreMorale findByCodeMembreMorale(String codeMembre) {
        return moraleRepo.findByKey(codeMembre);
    }

    @Override
    public List<EuMembreMorale> findByPortableAndDesactiver(String telephone, Integer desactiver) {
        return moraleRepo.findByPortableMembreLikeAndDesactiver(telephone, desactiver);
    }

    @Override
    public List<EuMembreMorale> findByEmailAndDesactiver(String email, Integer desactiver) {
        return moraleRepo.findByEmailMembreLikeAndDesactiver(email, desactiver);
    }

    @Override
    public List<EuMembreMorale> findByPortable(String telephone) {
        return moraleRepo.findByPortableMembre(telephone);
    }

    @Override
    public List<EuMembreMorale> findByEmail(String email) {
        return moraleRepo.findByEmailMembre(email);
    }

    @Override
    public EuMembreMorale createAdmin(MembreMorale membreMorale) {
        String code = moraleRepo.findByPrimaryKey("000000000000%");
        if (StringUtils.isBlank(code)) {
            code = "0000000000000000001M";
        } else {
            String ordre = code.substring(12, 19);
            String numeroOrdre = String.valueOf(Integer.parseInt(ordre) + 1);
            code = StringUtils.leftPad(numeroOrdre, 19, "0") + "M";
        }
        EuMembreMorale euMembreMorale = membreMoraleMapper.toEuMembreMorale(membreMorale);
        euMembreMorale.setCodeMembreMorale(code);
        return moraleRepo.save(euMembreMorale);
    }

    @Override
    public String findMembreInfo(String codeMembre) {
        String sql = "select raison_sociale from eu_membre_morale where code_membre_morale like ?";
        Map<String, String> result = query.nativeQueryMap(sql, codeMembre);
        if(result.values().size() > 0) {
            return result.get("raison_sociale");
        }
        return null;
    }

}
