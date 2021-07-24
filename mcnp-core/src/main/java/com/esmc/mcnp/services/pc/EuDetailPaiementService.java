package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.model.pc.EuDetailPaiement;
import com.esmc.mcnp.services.base.BaseService;

import java.util.List;

public interface EuDetailPaiementService extends BaseService<EuDetailPaiement, Integer> {

    public EuDetailPaiement findByTableAndId(String table, Integer id);
    
    public List<EuDetailPaiement> findByNumeroDemande(String numero);
    
    public List<EuDetailPaiement> findByIdDemandePaiement(Integer id);
}
