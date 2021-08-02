package com.esmc.mcnp.dao.repository.pc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuCreance;

public interface EuCreanceRepository extends BaseRepository<EuCreance, Long> {

    public List<EuCreance> findByCodeMembreDeb(String codeMembreDeb);

    public Page<EuCreance> findByCodeMembreDeb(String codeMembreDeb, Pageable page);

    public List<EuCreance> findByCodeMembreCred(String codeMembreCred);

    public Page<EuCreance> findByCodeMembreCred(String codeMembreCred, Pageable page);

    public List<EuCreance> findByCodeMembreCredAndCodeMembreDeb(String codeMembreCred, String codeMembreDeb);

    public Page<EuCreance> findByCodeMembreCredAndCodeMembreDeb(String codeMembreCred, String codeMembreDeb,
            Pageable page);
}
