package com.esmc.mcnp.dao.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;

public interface EuCompteCreditDataRepository extends DataTablesRepository<EuCompteCredit, Long> {
   
}
