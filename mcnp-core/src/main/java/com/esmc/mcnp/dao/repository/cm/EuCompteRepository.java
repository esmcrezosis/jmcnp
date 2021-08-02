package com.esmc.mcnp.dao.repository.cm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuCompte;

public interface EuCompteRepository extends BaseRepository<EuCompte, String> {

    @Query("select c from EuCompte c left join fetch c.euMembre left join fetch c.euMembreMorale where c.codeCompte like :compte")
    EuCompte findCompteById(@Param("compte") String id);

    @Query("select c from EuCompte c join fetch c.euCategorieCompte left join fetch c.euMembreMorale pm left join fetch pm.euRepresentations rp join fetch rp.euMembre where c.codeCompte like :compte")
    EuCompte findCompteCatbyId(@Param("compte") String id);

    @Query("select c from EuCompte c join fetch c.euCategorieCompte left join fetch c.euMembre where c.codeCompte like :compte")
    EuCompte findComptePPCatbyId(@Param("compte") String id);

    EuCompte findByNumeroCarte(String numeroCarte);

    List<EuCompte> findByEuMembre_CodeMembre(String code_membre);

    Page<EuCompte> findByEuMembre_CodeMembre(String code_membre, Pageable pageable);

    List<EuCompte> findByEuMembreMorale_CodeMembreMorale(
            String code_membre_morale);

    Page<EuCompte> findByEuMembreMorale_CodeMembreMorale(
            String code_membre_morale, Pageable pageable);

    EuCompte findByEuMembre_CodeMembreAndEuCategorieCompte_CodeCat(
            String code_membre, String code_cat);

    Page<EuCompte> findByEuMembre_CodeMembreAndEuCategorieCompte_CodeCat(
            String code_membre, String code_cat, Pageable pageable);

    EuCompte findByEuMembreMorale_CodeMembreMoraleAndEuCategorieCompte_CodeCat(
            String code_membre_morale, String code_cat);

    //@Query("select c from EuCompte c left join fetch c.euMembreMorale mm where mm.codeMembreMorale like :membre and c.euCategorieCompte.codeCat like :code")
    Page<EuCompte> findByEuMembreMorale_CodeMembreMoraleAndEuCategorieCompte_CodeCat(
            String code_membre_morale, String code_cat, Pageable pageable);

    Page<EuCompte> findByEuCategorieCompte_CodeCat(String codeCat, Pageable pageable);

    EuCompte findByEuMembre_CodeMembreAndEuTypeCompte_codeTypeCompte(
            String code_membre, String codeTypeCompte);

    Page<EuCompte> findByEuMembre_CodeMembreAndEuTypeCompte_codeTypeCompte(
            String code_membre, String codeTypeCompte, Pageable pageable);

    EuCompte findByEuMembreMorale_CodeMembreMoraleAndEuTypeCompte_codeTypeCompte(
            String code_membre_morale, String codeTypeCompte);

    Page<EuCompte> findByEuMembreMorale_CodeMembreMoraleAndEuTypeCompte_codeTypeCompte(
            String code_membre_morale, String codeTypeCompte, Pageable pageable);

    Page<EuCompte> findByEuTypeCompte_CodeTypeCompte(String code, Pageable pageable);

    @Query("select cp from EuCompte cp join fetch cp.euCategorieCompte cat left join fetch cp.euMembreMorale pm where cat.codeCat = 'TPAGCP' and pm.codeMembreMorale= :morale")
    EuCompte findCompteSurveillanceByCodeMembreMorale(@Param("morale") String code_membre);

    @Query("select c from EuCompte c left join fetch c.euMembre where c.euCategorieCompte.codeCat like 'TPAGCRPG' and c.solde <= :solde")
    List<EuCompte> findBySolde(@Param("solde") double solde);
}
