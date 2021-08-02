/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import java.util.List;

import com.esmc.mcnp.domain.dto.desendettement.Compensation;
import com.esmc.mcnp.domain.dto.obps.LGcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;

/**
 *
 * @author USER
 */
public interface GcpService {

	public List<EuTpagcp> getTpaGcpByCompte(String codeCompte);

	public String doEscompteGcp(LGcp gcp);

	public LGcp getLGcpByCompte(String codeCompte);

	public String doMprg(Compensation compens);

	public String doCompensation(Compensation compens);

	public Compensation getCompensationByCompte(String codeCompte);

	public Compensation getMprgByCompte(String codeCompte);
}
