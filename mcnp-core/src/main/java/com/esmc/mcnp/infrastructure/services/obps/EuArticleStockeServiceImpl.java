package com.esmc.mcnp.infrastructure.services.obps;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuArticleStockesRepository;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockes;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euArticleStockeService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuArticleStockeServiceImpl extends BaseServiceImpl<EuArticleStockes, Long> implements EuArticleStockeService {

	private static final long serialVersionUID = 1L;
	
	private @Autowired EuArticleStockesRepository articleStockeRepository;

    @Override
    protected BaseRepository<EuArticleStockes, Long> getRepository() {
        return articleStockeRepository;
    }

	@Override
	public EuArticleStockes findArticleStockeByCodebarre(String codeBarre) {
		return articleStockeRepository.findByCodeBarre(codeBarre);
	}

	@Override
	public List<String> findReferenceByCategorie(Integer articleStockesCategorie) {
		return articleStockeRepository.findReferenceByCategorie(articleStockesCategorie);
	}

	@Override
	public EuArticleStockes findArticleStockesByReference(String reference) {
		return articleStockeRepository.findByReference(reference);
	}

	@Override
	public Long findMaxId() {
		return articleStockeRepository.findMaxId();
	}

	@Override
	public List<String> findReferenceByCodeTegc(String codeTegc) {
		return articleStockeRepository.findReferenceByCodeTegc(codeTegc);
	}
	
	@Override
	public List<EuArticleStockes> findDesignationByCodeTegc(String codeTegc) {
		return articleStockeRepository.findDesignationByCodeTegc(codeTegc);
	}
    
}
