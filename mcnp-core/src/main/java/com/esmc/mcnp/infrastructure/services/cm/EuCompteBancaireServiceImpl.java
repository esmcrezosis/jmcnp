package com.esmc.mcnp.infrastructure.services.cm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteBancaireRepository;
import com.esmc.mcnp.domain.entity.cm.EuCompteBancaire;
import com.esmc.mcnp.commons.exception.business.CompteNonTrouveException;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCompteBancaireService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCompteBancaireServiceImpl extends BaseServiceImpl<EuCompteBancaire, Long>
        implements EuCompteBancaireService {
    private static final long serialVersionUID = 1L;
    private @Autowired
    EuCompteBancaireRepository compteBancaireRepo;

    @Override
    protected BaseRepository<EuCompteBancaire, Long> getRepository() {
        return compteBancaireRepo;
    }

    @Override
    public List<EuCompteBancaire> findListCompteBancaire(String codeMembre) {
        if (Strings.isNotBlank(codeMembre)) {
            if (codeMembre.endsWith("M")) {
                return compteBancaireRepo.findByCodeMembreMorale(codeMembre);
            } else {
                return compteBancaireRepo.findByCodeMembre(codeMembre);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<EuCompteBancaire> findByMembreAndBanque(String codeMembre, String codeBanque) {
        if (Strings.isNotBlank(codeMembre)) {
            if (codeMembre.endsWith("M")) {
                return Optional.ofNullable(compteBancaireRepo.findByCodeMembreMoraleAndCodeBanque(codeMembre, codeBanque));
            } else {
                return Optional.ofNullable(compteBancaireRepo.findByCodeMembreAndCodeBanque(codeMembre, codeBanque));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<EuCompteBancaire> findComptePrincipal(String codeMembre, int principal) {
        if (StringUtils.isNotBlank(codeMembre)) {
            if (codeMembre.endsWith("P")) {
                return Optional.ofNullable(compteBancaireRepo.findByCodeMembreAndPrincipal(codeMembre, principal));
            } else {
                return Optional.ofNullable(compteBancaireRepo.findByCodeMembreMoraleAndPrincipal(codeMembre, principal));
            }
        }
        return Optional.empty();
    }

    @Override
    public EuCompteBancaire getCompteBancaire(String codeMembre, String codeBanque) throws CompteNonTrouveException {
        Optional<EuCompteBancaire> opCpte = findComptePrincipal(codeMembre, 1);
        if (opCpte.isPresent()) {
            return opCpte.get();
        } else {
            List<EuCompteBancaire> cptes = findListCompteBancaire(codeMembre);
            if (!cptes.isEmpty()) {
                if (cptes.size() == 1) {
                    return cptes.get(0);
                } else {
                    List<EuCompteBancaire> filCptes = cptes.parallelStream().filter(c -> c.getCodeBanque().equals(codeBanque)).collect(Collectors.toList());
                    if (filCptes.isEmpty()) {
                        return cptes.get(0);
                    } else {
                        return filCptes.get(0);
                    }
                }
            } else {
                throw new CompteNonTrouveException("Compte Bancaire Non Trouvé");
            }
        }
    }

}
