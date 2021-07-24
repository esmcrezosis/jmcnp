package com.esmc.mcnp.web.controller.obps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.dto.obps.GcpDto;
import com.esmc.mcnp.mapper.obps.GcpMapper;
import com.esmc.mcnp.model.obps.EuGcp;
import com.esmc.mcnp.services.obps.EuGcpService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/gcp")
public class GcpController extends BaseController {

	private EuGcpService gcpService;
	private GcpMapper gcpMapper;

	@Autowired
	public GcpController(EuGcpService gcpService, GcpMapper gcpMapper) {
		this.gcpService = gcpService;
		this.gcpMapper = gcpMapper;
	}

	@GetMapping(value = "{agr}")
	public String loadGcp(Model model, @PathVariable("agr") String agr) {
		model.addAttribute("agr", agr);
		return "obps/gcp";
	}

	@GetMapping(value = "/gcps")
	@ResponseBody
	public JqGrid<GcpDto> loadGcps(@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "teGcp", required = false) String teGcp,
			@RequestParam(value = "typeGcp", required = false) String typeGcp,
			@RequestParam(value = "deb", required = false) String dateDeb,
			@RequestParam(value = "fin", required = false) String dateFin,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date deb = null;
		Date fin = null;
		if (StringUtils.isNotBlank(dateDeb)) {
			try {
				deb = format.parse(dateDeb);
			} catch (ParseException e) {
			}
		}
		if (StringUtils.isNotBlank(dateFin)) {
			try {
				fin = format.parse(dateFin);
			} catch (ParseException e) {
			}
		}
		Sort sort = null;
		String orderBy[] = sidx.split(",");
		if (orderBy.length > 0 && sord != null) {
			for (String o : orderBy) {
				if (o.trim().contains(" ")) {
					String[] ord = o.trim().split(" ");
					if (sort == null) {
						if (ord[1].equals("desc")) {
							sort = Sort.by(Sort.Direction.DESC, ord[0]);
						} else {
							sort = Sort.by(Sort.Direction.ASC, ord[0]);
						}
					} else {
						if (ord[1].equals("desc")) {
							sort.and(Sort.by(Sort.Direction.DESC, ord[0]));
						} else {
							sort.and(Sort.by(Sort.Direction.ASC, ord[0]));
						}
					}
				} else {
					if (sord.equals("desc")) {
						sort = Sort.by(Sort.Direction.DESC, o.trim());
					} else {
						sort = Sort.by(Sort.Direction.ASC, o.trim());
					}
				}
			}

		}
		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid
		// starts with 1
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 100;
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = PageRequest.of(page - 1, rows, sort);
		} else {
			pageRequest = PageRequest.of(page - 1, rows);
		}

		JqGrid<GcpDto> response = new JqGrid<>();
		Page<EuGcp> euGcps = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			if (deb != null && fin != null) {
				euGcps = gcpService.findByMembreAndDateConso(codeMembre, deb, fin, pageRequest);
			} else if (deb != null) {
				euGcps = gcpService.findByMembreAndDateSup(codeMembre, deb, pageRequest);
			} else if (fin != null) {
				euGcps = gcpService.findByMembreAndDateInf(codeMembre, fin, pageRequest);
			} else if (StringUtils.isNotBlank(typeGcp)) {
				euGcps = gcpService.findByMembreAndTypeGcp(codeMembre, typeGcp, pageRequest);
			} else {
				euGcps = gcpService.findByMembre(codeMembre, pageRequest);
			}
		} else if (deb != null && fin != null) {
			euGcps = gcpService.findByDate(deb, fin, pageRequest);
		} else if (deb != null) {
			euGcps = gcpService.findByDateSup(deb, pageRequest);
		} else if (fin != null) {
			euGcps = gcpService.findByDateInf(fin, pageRequest);
		} else {
			euGcps = gcpService.listAll(pageRequest);
		}
		if (Objects.nonNull(euGcps) && euGcps.getTotalElements() > 0) {
			response.setRows(gcpMapper.fromEuGcpList(euGcps.getContent()));
			response.setTotal(Integer.toString(euGcps.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}
}
