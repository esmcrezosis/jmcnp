package com.esmc.mcnp.repositories.pc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.pc.EuCreance;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCreanceRepository extends BaseRepository<EuCreance, Long> {

    public List<EuCreance> findByCodeMembreDeb(String codeMembreDeb);

    public Page<EuCreance> findByCodeMembreDeb(String codeMembreDeb, Pageable page);

    public List<EuCreance> findByCodeMembreCred(String codeMembreCred);

    public Page<EuCreance> findByCodeMembreCred(String codeMembreCred, Pageable page);

    public List<EuCreance> findByCodeMembreCredAndCodeMembreDeb(String codeMembreCred, String codeMembreDeb);

    public Page<EuCreance> findByCodeMembreCredAndCodeMembreDeb(String codeMembreCred, String codeMembreDeb,
            Pageable page);
}
