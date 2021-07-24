/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.ba;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.ba.EuCapaTs;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuCapaTsRepository extends BaseRepository<EuCapaTs, String> {

    @Query("select cpts from EuCapaTs cpts join fetch cpts.euCapa cp join cpts.euCompte c where c.codeCompte = :compte")
    public List<EuCapaTs> findCapaTsByCodeCompte(@Param("compte") String codeCompte);

    @Query("select cts from EuCapaTs cts join fetch cts.euCapa cp join cts.euCompte c where cts.montantSolde > 0 and c.codeCompte = :compte")
    public List<EuCapaTs> findCapaTsByCompte(@Param("compte") String compte);

    @Query("select cts from EuCapaTs cts join fetch cts.euCapa cp join cts.euCompte c where cts.montantSolde > 0 and c.codeCompte = :compte and cp.codeProduit like :produit")
    public List<EuCapaTs> findCapaTsByCompteType(@Param("compte") String compte, @Param("produit") String codeProduit);

    @Query("select sum(cts.montantSolde) from EuCapaTs cts join cts.euCapa cp join cts.euCompte c where cts.montantSolde > 0 and c.codeCompte = :compte")
    public Double getSumCapaTsByCompte(@Param("compte") String compte);

    @Query("select sum(cts.montantSolde) from EuCapaTs cts join cts.euCapa cp join cts.euCompte c where cts.montantSolde > 0 and c.codeCompte = :compte and cp.codeProduit like :produit")
    public Double getSumCapaTsByCompteType(@Param("compte") String compte, @Param("produit") String codeProduit);

    @Query("select sum(cts.montant) from EuCapaTs cts join cts.euCapa cp join cts.euCompte c where c.codeCompte = :compte and cp.codeProduit = :produit")
    public Double getSommeByCompteAndProduit(@Param("compte") String codeCompte, @Param("produit") String codeProduit);

    @Query("select cts from EuCapaTs cts join fetch cts.euCapa cp join cts.euCompte c where cts.montantSolde > 0 and c.codeCompte = :compte and cp.origineCapa like :origine")
    public List<EuCapaTs> findByCompteAndOrigine(@Param("compte") String codeCompte, @Param("origine") String origine);

    @Query("select cts from EuCapaTs cts join fetch cts.euCapa cp join cts.euCompte c where cts.montantSolde > 0 and c.codeCompte = :compte and cp.codeProduit = :produit and cp.origineCapa like :origine")
    public List<EuCapaTs> findByCompteAndProduitAndOrigine(@Param("compte") String codeCompte,
            @Param("produit") String codeProduit, @Param("origine") String origine);
    
    public List<EuCapaTs> findByEuBon_BonNumero(String numero);
    
    
    @Query("select cts from EuCapaTs cts join fetch cts.euCapa cp  where cts.montantSolde > 0 and cp.codeMembre = :codeMembre and cp.codeProduit = :produit")
    public List<EuCapaTs> findByProduitAndMembre(@Param("produit") String produit, @Param("codeMembre") String codeMembre);
}
