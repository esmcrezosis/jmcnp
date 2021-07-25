package com.esmc.mcnp.repositories.bc;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.obps.EuCreditConsommer;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuCreditConsommerRepository extends BaseRepository<EuCreditConsommer, Long> {

    @Query("select max(e.idConsommation) from EuCreditConsommer e")
    public Long getLastEuConsommationInsertedId();

   /* @Query("select sum(e.montConsommation) from EuCreditConsommer e join e.euCompteCreditTs cc where cc.euCompteCredit.idCredit =? and SYSDATE()-e.dateConsommation<=30")
    public Double getSommeCreditConsommer(Long idcredit);

    @Query("select sum(e.montConsommation) from EuCreditConsommer e join e.euCompteCreditTs cc where cc.euCompteCredit.idCredit =? and SYSDATE()-e.dateConsommation<=30")
    public Double getSommeDejaConsommer(Long idcredit);
  
    @Query("select sum(e.montConsommation) from EuCreditConsommer e join e.euProduit p where p.codeProduit =? and SYSDATE()-e.dateConsommation<=30")
    public Double getSommeDejaConsommerParProduit(String codeProduit);*/
 
    
    @Query("select sum(e.montConsommation) from EuCreditConsommer e join e.euCompteCredit c where c.codeTypeCredit = :typeCredit and SYSDATE()-e.dateConsommation<=30")
    public Double getSommeDejaConsommerParTypeCredit(@Param("typeCredit") String codeTypeCredit);
    
    @Query("select sum(e.montConsommation) from EuCreditConsommer e join e.euCompteCredit c where c.codeTypeCredit like :typeCredit and e.codeMembre like :membre and SYSDATE()-e.dateConsommation<=30")
    public Double getSommeDejaConsommerParTypeCreditAndCodeMembre(@Param("typeCredit") String codeTypeCredit, @Param("membre") String codeMembre);
    
    @Query("select sum(e.montConsommation) from EuCreditConsommer e join e.euCompteCredit c where c.codeTypeCredit like :typeCredit and e.codeMembreMorale like :membre and SYSDATE()-e.dateConsommation<=30")
    public Double getSommeDejaConsommerParTypeCreditAndCodeMembreMorale(@Param("typeCredit") String codeTypeCredit, @Param("membre") String codeMembreMorale);


}
