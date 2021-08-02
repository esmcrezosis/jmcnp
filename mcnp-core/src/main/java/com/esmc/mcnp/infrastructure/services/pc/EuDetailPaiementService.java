package com.esmc.mcnp.infrastructure.services.pc;

import com.esmc.mcnp.domain.entity.pc.EuDetailPaiement;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

import java.util.List;

public interface EuDetailPaiementService extends BaseService<EuDetailPaiement, Integer> {

    public EuDetailPaiement findByTableAndId(String table, Integer id);
    
    public List<EuDetailPaiement> findByNumeroDemande(String numero);
    
    public List<EuDetailPaiement> findByIdDemandePaiement(Integer id);
}
