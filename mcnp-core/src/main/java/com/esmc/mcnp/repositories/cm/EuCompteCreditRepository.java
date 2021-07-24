package com.esmc.mcnp.repositories.cm;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuCompteCreditRepository extends BaseRepository<EuCompteCredit, Long> {

    @Query("select cc from EuCompteCredit cc join fetch cc.euProduit where cc.idCredit = :id")
    EuCompteCredit findById(@Param("id") long idCredit);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c where c.codeCompte = :compte and cc.montantCredit > 0")
    List<EuCompteCredit> findByEuCompte_CodeCompte(@Param("compte") String codeCompte);

    @Query(value = "select cc from EuCompteCredit cc join cc.euCompte c where c.codeCompte = :compte and cc.montantCredit > 0")
    Page<EuCompteCredit> findByEuCompte_CodeCompte(@Param("compte") String codeCompte, Pageable pageable);

    @Query("select sum(cc.montantCredit) from EuCompteCredit cc join cc.euCompte c where c.codeCompte = :compte")
    Double getSumByCodeCompte(@Param("compte") String codeCompte);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c where cc.montantCredit > 0 and c.codeCompte= :compte and cc.compteSource= :source")
    List<EuCompteCredit> findByCompteandSource(@Param("compte") String codeCompte, @Param("source") String source);

    @Query("select sum(cc.montantCredit) from EuCompteCredit cc join cc.euCompte c where c.codeCompte= :compte and cc.montantCredit > 0")
    Double getSumCreditByEuCompte(@Param("compte") String codeCompte);

    @Query("select sum(cc.montantPlace) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte= :compte and p.codeProduit= :produit")
    Double getSommeCreditRecurrentByCompte(@Param("compte") String codeCompte, @Param("produit") String codeProduit);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where cc.montantCredit > 0 and c.codeCompte = :compte and p.codeProduit = :produit")
    List<EuCompteCredit> findbyCompteAndProduit(@Param("compte") String codeCompte,
                                                @Param("produit") String codeProduit);

    @Query("select sum(cc.montantPlace) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte= :compte and p.codeProduit= :produit and cc.codeTypeCredit= :tcredit")
    Double getSommeCreditRecurrentByTypeCredit(@Param("compte") String codeCompte, @Param("produit") String codeProduit, @Param("tcredit") String typeCredit);

    @Query("select count(cc) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte= :compte and p.codeProduit= :produit")
    Long getNbreCreditRecurrentByProduitCompte(@Param("compte") String codeCompte, @Param("produit") String codeProduit);

    @Query("select max(cc.idCredit) from EuCompteCredit cc")
    Long getLastCreditInsertedId();

    @Query("select cc from EuCompteCredit cc join cc.euCompte c where c.codeCompte like :compte and cc.codeTypeCredit like :credit")
    List<EuCompteCredit> findByCompteAndTypeCredit(@Param("compte") String codeCompte,
                                                   @Param("credit") String typeCredit);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where c.codeCompte = :compte and p.codeProduit = :produit and cc.codeTypeCredit = :credit and cc.prk = :prk and cc.montantCredit > 0")
    List<EuCompteCredit> findByProduitAndCreditAndPrk(@Param("compte") String codeCompte,
                                                      @Param("produit") String codeProduit, @Param("credit") String codeTypeCredit, @Param("prk") Double prk);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where c.codeCompte = :compte and p.codeProduit = :produit and cc.codeTypeCredit = :credit and cc.montantCredit > 0")
    List<EuCompteCredit> findByCompteAndProduitAndCredit(@Param("compte") String codeCompte,
                                                         @Param("produit") String codeProduit, @Param("credit") String codeTypeCredit);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where c.codeCompte = :compte and cc.montantCredit > 0 and p.codeProduit = :produit and cc.codeTypeCredit = :credit and cc.compteSource<>'CMITNRPREKITTEC' and cc.compteSource<>'CAPUNRPREKITTEC' and cc.compteSource<>'CAIPCNRPREKITTEC' and cc.compteSource<>'CAPARPGNRPREKITTEC'")
    List<EuCompteCredit> findByProduitAndCredit(@Param("compte") String codeCompte,
                                                @Param("produit") String codeProduit, @Param("credit") String codeTypeCredit);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where c.codeCompte = :compte and cc.montantCredit > 0 and p.codeProduit = :produit")
    List<EuCompteCredit> findByProduit(@Param("compte") String codeCompte, @Param("produit") String codeProduit);

    @Query("select sum(cc.montantCredit) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte = :compte")
    Double getSommeCreditByCompte(@Param("compte") String codeCompte);

    @Query("select sum(cc.montantCredit) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte = :compte and p.codeProduit = :produit and cc.codeTypeCredit = :credit and cc.compteSource<>'CMITNRPREKITTEC' and cc.compteSource<>'CAPUNRPREKITTEC' and cc.compteSource<>'CAIPCNRPREKITTEC' and cc.compteSource<>'CAPARPGNRPREKITTEC'")
    Double getSommeCreditByCompte(@Param("compte") String codeCompte, @Param("produit") String codeProduit,
                                  @Param("credit") String codeTypeCredit);

    @Query("select sum(cc.montantCredit) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte= :compte and p.codeProduit= :produit")
    Double getSommeCreditByCompte2(@Param("compte") String codeCompte, @Param("produit") String codeProduit);

    @Query("select distinct p.codeProduit from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte = :compte and cc.montantCredit > 0")
    List<String> findCodeProduitByCodeCompte(@Param("compte") String codeCompte);

    @Query("select cc from EuCompteCredit cc join fetch cc.euCompte c join fetch cc.euProduit p where cc.renouveller= :renouv and cc.bnp in (:bnps) and p.codeProduit in :produits and cc.datefin <= :datefin and cc.duree <> :duree and cc.nbreRenouvel > 0")
    List<EuCompteCredit> fetchAllCredits(@Param("renouv") String renouveller, @Param("bnps") List<Integer> bnp,
                                         @Param("produits") List<String> produits, @Param("datefin") Date date, @Param("duree") double duree);

    @Query("select cc from EuCompteCredit cc join fetch cc.euCompte c join fetch cc.euProduit p where cc.renouveller= :renouv and cc.bnp = :bnp and p.codeProduit in (:produit) and cc.duree > :duree and cc.datefin<=:datefin and cc.nbreRenouvel > 0")
    List<EuCompteCredit> fetchAllCredits(@Param("renouv") String renouveller, @Param("bnp") Integer bnp,
                                         @Param("produit") List<String> produits, @Param("duree") double duree, @Param("datefin") Date date);

    @Query("select cc from EuCompteCredit cc join fetch cc.euCompte c join fetch cc.euProduit p where cc.renouveller= :renouv and cc.bnp = :bnp and p.codeProduit in (:produit) and cc.duree = :duree and cc.nbreRenouvel > 0")
    List<EuCompteCredit> fetchAllCredits(@Param("renouv") String renouveller, @Param("bnp") Integer bnp,
                                         @Param("produit") List<String> produits, @Param("duree") double duree);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where cc.montantCredit > 0 and cc.datefin <= current_date and p.codeProduit like :produit and c.codeCompte like :compte")
    List<EuCompteCredit> findByProduitAndCompte(@Param("produit") String codeProduit, @Param("compte") String codeCompte);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where cc.montantCredit > 0 and c.codeCompte like :compte and cc.typeRecurrent like :rec")
    List<EuCompteCredit> findByCompteAndType( @Param("compte") String codeCompte, @Param("rec") String typeRecurrent);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where c.codeCompte like :compte and cc.typeRecurrent like :rec")
    Page<EuCompteCredit> findByCompteAndType( @Param("compte") String codeCompte, @Param("rec") String typeRecurrent, Pageable pageable);

    @Query("select cc from EuCompteCredit cc join fetch cc.euCompte c left join fetch c.euMembre where cc.euProduit.codeProduit like :produit and cc.montantPlace = :place")
    List<EuCompteCredit> findByProduitAndMontantPlace(@Param("produit") String codeProduit,
                                                      @Param("place") double montantPlace);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where c.codeCompte = :compte and p.codeProduit = :produit and cc.prk = :prk and cc.montantCredit > 0")
    List<EuCompteCredit> fetchByCompteAndProduitAndPrk(@Param("compte") String codeCompte,
                                                       @Param("produit") String codeProduit, @Param("prk") Double prk);

    @Query("select cc from EuCompteCredit cc join cc.euCompte c join cc.euProduit p where cc.montantCredit > 0 and c.codeCompte = :compte and p.codeProduit = :produit and cc.prk = :prk and cc.typeProduit like :tproduit and cc.montantCredit > 0")
    List<EuCompteCredit> fetchByCompteAndProduitAndPrk(@Param("compte") String codeCompte,
                                                       @Param("produit") String codeProduit, @Param("prk") Double prk, @Param("tproduit") String typeProduit);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte like :compte and p.codeProduit like :produit and cc.typeRecurrent like :tr and cc.typeProduit like :tp and cc.montantCredit > 0")
    List<EuCompteCredit> findByTypeRecurrentAndTypeProduit(@Param("compte") String codeCompte,
                                                           @Param("produit") String codeProduit, @Param("tr") String typeRecurrent, @Param("tp") String typeProduit);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte like :compte and p.codeProduit like :produit and cc.typeRecurrent like :tr and cc.typeProduit like :tp and cc.duree = :duree and cc.montantCredit > 0")
    List<EuCompteCredit> findByTypeRecurrentAndTypeProduitAndDuree(@Param("compte") String codeCompte,
                                                                   @Param("produit") String codeProduit, @Param("tr") String typeRecurrent, @Param("tp") String typeProduit,
                                                                   @Param("duree") double duree);

    @Query("select sum(cc.montantPlace) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte= :compte and p.codeProduit= :produit and cc.typeRecurrent = :tr and cc.typeProduit = :tp and cc.montantCredit > 0")
    Double getSommeCreditRecurrentByType(@Param("compte") String codeCompte, @Param("produit") String codeProduit,
                                         @Param("tr") String typeRecurrent, @Param("tp") String typeProduit);

    @Query("select sum(cc.montantPlace) from EuCompteCredit cc join cc.euProduit p join cc.euCompte c where c.codeCompte= :compte and p.codeProduit= :produit and cc.typeRecurrent = :tr and cc.typeProduit = :tp and cc.duree = :duree and cc.montantCredit > 0")
    Double getSommeCreditRecurrentByTypeAndDuree(@Param("compte") String codeCompte,
                                                 @Param("produit") String codeProduit, @Param("tr") String typeRecurrent, @Param("tp") String typeProduit,
                                                 @Param("duree") double duree);

    @Query("select distinct p.codeProduit from EuCompteCredit cc join cc.euProduit p where cc.codeMembre = :membre and cc.montantCredit > 0")
    List<String> findCodeProduitByCodeMembre(@Param("membre") String codeMembre);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p where cc.codeMembre like :codeMembre and p.codeProduit like :produit and cc.typeRecurrent like :tr and cc.dateOctroi between :deb and :fin")
    Page<EuCompteCredit> findCreditByMembreAndProduitAndDate(@Param("codeMembre") String codeMembre, @Param("produit") String codeProduit, @Param("tr") String typeRecurrent,
                                                             @Param("deb") Date dateDeb, @Param("fin") Date dateFin, Pageable pageable);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p where cc.codeMembre like :codeMembre and p.codeProduit like :produit and cc.dateOctroi between :deb and :fin")
    Page<EuCompteCredit> findCreditByMembreAndProduitAndDate(@Param("codeMembre") String codeMembre, @Param("produit") String codeProduit,
                                                             @Param("deb") Date dateDeb, @Param("fin") Date dateFin, Pageable pageable);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p where cc.codeMembre like :codeMembre and p.codeProduit like :produit")
    Page<EuCompteCredit> findCreditByMembreAndProduit(@Param("codeMembre") String codeMembre, @Param("produit") String codeProduit, Pageable pageable);

    @Query("select cc from EuCompteCredit cc where cc.codeMembre like :codeMembre and cc.typeRecurrent like :tr and cc.dateOctroi between :deb and :fin")
    Page<EuCompteCredit> findCredits(@Param("codeMembre") String codeMembre, @Param("tr") String typeRecurrent,
                                     @Param("deb") Date dateDeb, @Param("fin") Date dateFin, Pageable pageable);

    Page<EuCompteCredit> findByCodeMembre(String codeMembre, Pageable pageable);

    Page<EuCompteCredit> findByCodeMembreAndTypeRecurrent(String codeMembre, String typeRecurrent, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrent(String typeRecurrent, Pageable pageable);

    Page<EuCompteCredit> findByDateOctroiBetween(Date dateDeb, Date dateFin, Pageable pageable);

    Page<EuCompteCredit> findByDateOctroiIsAfter(Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByDateOctroiIsBefore(Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByCodeMembreAndDateOctroiIsAfter(String codeMembre, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByCodeMembreAndDateOctroiIsBefore(String codeMembre, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByCodeMembreAndDateOctroiBetween(String codeMembre, Date dateDeb, Date fin, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrentAndDateOctroiIsAfter(String typeRecurrent, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrentAndDateOctroiIsBefore(String typeRecurrent, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByTypeRecurrentAndDateOctroiBetween(String typeRecurrent, Date dateDeb, Date fin, Pageable pageable);

    Page<EuCompteCredit> findByEuProduit_CodeProduit(String codeProduit, Pageable pageable);

    Page<EuCompteCredit> findByEuProduit_CodeProduitAndDateOctroiIsAfter(String codeProduit, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByEuProduit_CodeProduitAndDateOctroiIsBefore(String codeProduit, Date dateDeb, Pageable pageable);

    Page<EuCompteCredit> findByEuProduit_CodeProduitAndDateOctroiBetween(String codeProduit, Date dateDeb, Date fin, Pageable pageable);

    @Query("select cc from EuCompteCredit cc join cc.euProduit p where cc.codeMembre = :membre and p.codeProduit = :produit and cc.prk = :prk and cc.montantCredit > 0")
    Page<EuCompteCredit> fetchByMembreAndProduitAndPrk(@Param("membre") String codeMembre,
                                                       @Param("produit") String codeProduit, @Param("prk") Double prk, Pageable pageable);

    @Query("select cc from EuCompteCredit cc where cc.codeMembre = :membre and cc.prk = :prk")
    Page<EuCompteCredit> fetchByMembreAndPrk(@Param("membre") String codeMembre, @Param("prk") Double prk, Pageable pageable);

    Page<EuCompteCredit> findByPrk(Double prk, Pageable pageable);
}
