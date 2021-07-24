package com.esmc.mcnp.components;

//import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.services.bc.EuCreditConsommerService;
import com.esmc.mcnp.services.common.EuTypeCreditService;

@Component
public class QuotaUtility {

    private @Autowired
    EuCreditConsommerService creditConsommerService;
    private @Autowired
    EuTypeCreditService typeCreditService;
   /* private @Autowired
    EuTegcService tegcService;
*/
    @Transactional
    public Boolean verifyQuota(String typeCredit, String codeMembreAcheteur, Double montantAchat, String typeR) {
        Double montantDejaConsommer = 0d;
        Double quota;
       // List<EuTegc> ListEuTegc = Lists.newArrayList();

        if (StringUtils.isNotBlank(typeCredit)/* && StringUtils.isNotBlank(typeR)*/ && StringUtils.isNotBlank(codeMembreAcheteur)
                && !Double.isNaN(montantAchat)) {
     
                //recuperer le quota
                if (typeR.equals("nr")) {
                    quota = typeCreditService.findQuotaNRByTypeCredit(typeCredit);
                } else {
                    quota = typeCreditService.findQuotaRByTypeCredit(typeCredit);
                }
                //verifier le montant déjà consommé
                montantDejaConsommer = creditConsommerService.getSommeDejaConsommerParTypeCreditAndMembre(typeCredit, codeMembreAcheteur);
                if (montantDejaConsommer == null) {
                    montantDejaConsommer = 0d;
                }

               if (quota > 0) {
                    if ((montantDejaConsommer + montantAchat) > quota) {
                        return false;
                    } else {
                        return true;
                    }
                } else if (quota == 0) {
                    return true;
                }
            
        }
        return true;
    }
}
