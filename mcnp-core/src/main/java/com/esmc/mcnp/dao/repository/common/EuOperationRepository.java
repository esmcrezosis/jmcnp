package com.esmc.mcnp.repositories.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuOperationRepository extends BaseRepository<EuOperation, Long> {

	@Query("select max(s.idOperation) from EuOperation s")
	public Long getLastOperationInsertedId();

	@Query("select s from EuOperation s where libOp like :libOp")
	public List<EuOperation> getListOperations(@Param("libOp") String libOp);

}
