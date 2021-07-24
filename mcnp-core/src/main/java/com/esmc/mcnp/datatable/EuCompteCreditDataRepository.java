package com.esmc.mcnp.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.esmc.mcnp.model.cm.EuCompteCredit;

public interface EuCompteCreditDataRepository extends DataTablesRepository<EuCompteCredit, Long> {
   
}
