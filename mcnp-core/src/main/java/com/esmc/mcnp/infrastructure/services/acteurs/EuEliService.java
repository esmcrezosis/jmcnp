package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuDetailEli;
import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.services.base.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EuEliService extends BaseService<EuEli, Integer> {
	Optional<EuEli> findByNumero(String numero);

	List<EuEli> findByCodeTegc(String codeTegc);

	EuEli createEli(Date date, String codeMembre, String codeTegc, double montBan, double montBai, double montOpi);

	EuEli createEli(Date date, String codeMembre, String codeTegc, double montBan, double montBai, double montOpi,
			List<EuDetailEli> details);
}
