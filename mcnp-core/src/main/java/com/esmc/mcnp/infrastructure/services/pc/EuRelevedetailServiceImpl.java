package com.esmc.mcnp.services.pc;

import java.util.List;

import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuRelevedetail;
import com.esmc.mcnp.repositories.pc.EuRelevedetailRepository;

@Service("euRelevedetailService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRelevedetailServiceImpl extends CrudServiceImpl<EuRelevedetail, Integer>
        implements EuRelevedetailService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final EuRelevedetailRepository relevedetRepo;

    public EuRelevedetailServiceImpl(EuRelevedetailRepository relevedetRepo) {
        super(relevedetRepo);
        this.relevedetRepo = relevedetRepo;
    }

    @Override
    public Double getDetailSumCapaByReleve(Integer releveId) {
        return relevedetRepo.getDetailSumCapaByReleve(releveId);
    }

    @Override
    public Double getDetailSumCapaByReleve(Integer releveId, String ressource) {
        return relevedetRepo.getDetailSumCapaByReleve(releveId, ressource);
    }

    @Override
    public List<EuRelevedetail> findByReleve(Integer releveId) {
        return relevedetRepo.findByRelevedetailReleve(releveId);
    }

    @Override
    public List<EuRelevedetail> findByProduit(String produit) {
        return relevedetRepo.findByRelevedetailProduit(produit);
    }

    @Override
    public List<EuRelevedetail> findByReleveAndProduit(Integer releveId, String produit) {
        return relevedetRepo.findAllByReleveAndProduit(releveId, produit);
    }

    @Override
    public Page<EuRelevedetail> findByReleve(Integer releveId, Pageable pageable) {
        return relevedetRepo.findByRelevedetailReleve(releveId, pageable);
    }

    @Override
    public Page<EuRelevedetail> findByProduit(String produit, Pageable pageable) {
        return relevedetRepo.findByRelevedetailProduit(produit, pageable);
    }

    @Override
    public Page<EuRelevedetail> findByReleveAndProduit(Integer releveId, String produit, Pageable pageable) {
        return relevedetRepo.findAllByReleveAndProduit(releveId, produit, pageable);
    }

}
