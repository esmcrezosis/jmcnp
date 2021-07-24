package com.esmc.mcnp.web.controller.bc;

import com.esmc.mcnp.datatable.EuCompteCreditDataRepository;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.repositories.cm.EuCompteCreditRepository;
import com.esmc.mcnp.web.controller.base.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class EuBonDataController extends BaseRestController {

	private EuCompteCreditDataRepository ccDataRepository;
	private EuCompteCreditRepository ccRepository;

	@Autowired
	public EuBonDataController(EuCompteCreditDataRepository ccDataRepository, EuCompteCreditRepository ccRepository) {
		this.ccDataRepository = ccDataRepository;
		this.ccRepository = ccRepository;
	}

	@GetMapping(value = "/credits")
	public DataTablesOutput<EuCompteCredit> list(@Valid DataTablesInput input) {
		return ccDataRepository.findAll(input);
	}

	@GetMapping(value = "/ccs")
	public List<EuCompteCredit> list() {
		return ccRepository.findAll();
	}

	@GetMapping(value = "/advCredits")
	public DataTablesOutput<EuCompteCredit> listAdvanced(@Valid DataTablesInput input) throws ParseException {
		return ccDataRepository.findAll(input, new CreditSpecification(input));
	}

	private class CreditSpecification implements Specification<EuCompteCredit> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Date dateDeb;
		private final Date dateFin;

		CreditSpecification(DataTablesInput input) throws ParseException {
			String dateFilter = input.getColumn("dateOctroi").getSearch().getValue();
			System.out.println("Filtre : " + dateFilter);
			if (!StringUtils.hasText(dateFilter)) {
				dateDeb = dateFin = null;
				return;
			}
			String[] bounds = dateFilter.split(";");
			dateDeb = getValue(bounds, 0);
			dateFin = getValue(bounds, 1);
		}

		private Date getValue(String[] bounds, int index) throws ParseException {
			if (bounds.length > index && StringUtils.hasText(bounds[index])) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				return dateFormat.parse(bounds[index]);
			}
			return null;
		}

		@Override
		public Predicate toPredicate(Root<EuCompteCredit> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) {
			Expression<Date> date = root.get("dateOctroi").as(Date.class);
			if (dateDeb != null && dateFin != null) {
				return criteriaBuilder.between(date, dateDeb, dateFin);
			} else if (dateDeb != null) {
				return criteriaBuilder.greaterThanOrEqualTo(date, dateDeb);
			} else if (dateFin != null) {
				return criteriaBuilder.lessThanOrEqualTo(date, dateFin);
			} else {
				return criteriaBuilder.conjunction();
			}
		}

	}

}
