package com.esmc.mcnp.web.controller.franchise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreDetailService;
import com.esmc.mcnp.infrastructure.services.ba.EuBonNeutreService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.cmfh.EuAssociationService;
import com.esmc.mcnp.infrastructure.services.franchise.EuAchatFranchisesService;
import com.esmc.mcnp.infrastructure.services.franchise.EuFranchisesService;
import com.esmc.mcnp.web.controller.base.BaseController;

@Controller
@RequestMapping(value = "franchise")
public class FranchiseController extends BaseController {

	private EuFranchisesService franchiseService;
	private EuAchatFranchisesService achatFranchiseService;
	private final EuAssociationService assoService;
	private EuMembreService membreService;
	private EuMembreMoraleService moraleService;
	private EuBonNeutreService bonNeutreService;
	private EuBonNeutreDetailService bnDetailService;

	@Autowired
	public FranchiseController(EuFranchisesService franchiseService, EuAchatFranchisesService achatFranchiseService,
			EuMembreService membreService, EuMembreMoraleService moraleService, EuBonNeutreService bonNeutreService,
			EuBonNeutreDetailService bnDetailService, EuAssociationService assoService) {
		this.assoService = assoService;
		this.franchiseService = franchiseService;
		this.achatFranchiseService = achatFranchiseService;
		this.membreService = membreService;
		this.moraleService = moraleService;
		this.bonNeutreService = bonNeutreService;
		this.bnDetailService = bnDetailService;
	}

	@GetMapping(value = "index")
	public String searchFranchise() {
		return "franchise/index";
	}

}
