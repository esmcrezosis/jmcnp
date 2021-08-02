package com.esmc.mcnp.domain.dto.bc;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BcCredit {

	private Long idCredit;
	private String codeMembre;
	private Double montantPlace;
	private Double montantCredit;
	private String typeRecurrent;
	private Date dateOctroi;
	private Boolean activer;

	public static BcCredit fromCompteCredit(EuCompteCredit cc) {
		return new BcCredit()
				.setActiver(cc.getActiver())
				.setCodeMembre(cc.getCodeMembre())
				.setDateOctroi(cc.getDateOctroi())
				.setTypeRecurrent(cc.getTypeRecurrent())
				.setIdCredit(cc.getIdCredit())
				.setMontantCredit(cc.getMontantCredit())
				.setMontantPlace(cc.getMontantPlace());
	}
	
	public static List<BcCredit> fromCompteCredits(List<EuCompteCredit> ccs) {
		List<BcCredit> credits = Lists.newArrayList();
		if (!ccs.isEmpty()) {
			ccs.forEach(cc -> credits.add(fromCompteCredit(cc)));
		}
		return credits;
	}

}
