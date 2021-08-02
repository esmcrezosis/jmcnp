/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.others;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuCreditCodebarre;

/**
 *
 * @author yokmcnp
 */

public interface EuCreditCodebarreRepository extends BaseRepository<EuCreditCodebarre, String> {

    @Query("select ecc from EuCreditCodebarre ecc  where ecc.codebarre= :codebarre")
    public EuCreditCodebarre findCreditByCodebar(@Param("codebarre") String codebar);

}
