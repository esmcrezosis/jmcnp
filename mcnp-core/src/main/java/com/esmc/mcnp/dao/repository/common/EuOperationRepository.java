package com.esmc.mcnp.dao.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.others.EuOperation;

@Repository
public interface EuOperationRepository extends BaseRepository<EuOperation, Long> {

	@Query("select max(s.idOperation) from EuOperation s")
	public Long getLastOperationInsertedId();

	@Query("select s from EuOperation s where libOp like :libOp")
	public List<EuOperation> getListOperations(@Param("libOp") String libOp);

}
