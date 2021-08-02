package com.esmc.mcnp.dao.repository.bc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuBon;

public interface EuBonRepository extends BaseRepository<EuBon, Integer> {

	@Query("select b from EuBon b where b.bonExprimer = 0 and b.bonCodeMembreEmetteur like :membre and b.bonType like :type")
	public List<EuBon> findBonNonExpByMembre(@Param("membre") String codeMembre, @Param("type") String typeBon);

	public EuBon findByBonNumeroAndBonExprimer(String numero, int exprimer);

	@Query("select bn from EuBon bn where bn.bonNumero like :bonNumero")
	public EuBon findBon(@Param("bonNumero") String bonNumero);

	@Query("select max(b.bonId) From EuBon b")
	public Long findByMaxIdInserted();

	@Query("select sum(bn.bonMontant) from EuBon bn where bn.bonType in ('BC', 'BCnr', 'BCr')")
	public Double findSumAllBonConso();
}
