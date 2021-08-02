package com.esmc.mcnp.dao.repository.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuSmsmoney;

@Repository
public interface EuSmsmoneyRepository extends BaseRepository<EuSmsmoney, Long> {

    @Query("select max(s.neng) from EuSmsmoney s")
    public Long getLastInsertId();

    @Query("select s.creditamount from EuSmsmoney s where s.creditcode = :codeCredit and s.iddatetimeconsumed = 0 and s.destaccountConsumed is null")
    public double findCreditAmountByCreditcode(@Param("codeCredit") String creditCode);
    
    public EuSmsmoney findByCreditcode(String creditCode);
}
