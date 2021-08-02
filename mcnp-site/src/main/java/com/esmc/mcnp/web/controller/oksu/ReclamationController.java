package com.esmc.mcnp.web.controller.oksu;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esmc.mcnp.commons.util.FileUploadUtils;
import com.esmc.mcnp.commons.util.StringUtils;
import com.esmc.mcnp.dao.repository.pc.EuReclamationAttachmentRepository;
import com.esmc.mcnp.domain.dto.desendettement.ReclamationDto;
import com.esmc.mcnp.domain.entity.pc.EuReclamation;
import com.esmc.mcnp.domain.entity.pc.EuReclamationAttachment;
import com.esmc.mcnp.domain.entity.pc.StatutReclamation;
import com.esmc.mcnp.domain.entity.pc.TypePassif;
import com.esmc.mcnp.domain.mapper.pc.ReclamationMapper;
import com.esmc.mcnp.infrastructure.services.pc.EuCasPassifService;
import com.esmc.mcnp.infrastructure.services.pc.EuReclamationService;
import com.esmc.mcnp.util.JqGrid;
import com.esmc.mcnp.web.controller.base.BaseController;
import com.esmc.mcnp.web.dto.util.Result;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "ticket")
public class ReclamationController extends BaseController {
	private final EuReclamationService reclamationService;
	private final EuReclamationAttachmentRepository attachmentRepository;
	private final EuCasPassifService casPassifService;
	private final ReclamationMapper reclamationMapper;

	public ReclamationController(EuReclamationService reclamationService,
			EuReclamationAttachmentRepository attachmentRepository, EuCasPassifService casPassifService,
			ReclamationMapper reclamationMapper) {
		this.reclamationService = reclamationService;
		this.attachmentRepository = attachmentRepository;
		this.casPassifService = casPassifService;
		this.reclamationMapper = reclamationMapper;
	}

	@GetMapping
	public String lister(Model model) {
		model.addAttribute("cas", casPassifService.listAll());
		return "oksu/reclamations";
	}

	@GetMapping(value = "reclamer")
	public String reclamer(@ModelAttribute("reclamation") ReclamationDto reclamationDto, Model model) {
		model.addAttribute("cas", casPassifService.listAll());
		return "oksu/reclamation";
	}

	@GetMapping(value = "edit/{id}")
	public String editReclamation(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cas", casPassifService.listAll());
		model.addAttribute("reclamation", reclamationMapper.toDto(reclamationService.getById(id)));
		return "oksu/editrecl";
	}

	@PostMapping(value = "edit")
	public ResponseEntity<?> editReclamation(ReclamationDto reclamationDto) {
		reclamationService.update(reclamationMapper.toEntity(reclamationDto));
		return ResponseEntity.ok(new Result(0, "Opération effectuée avec sucès"));
	}

	@PostMapping(value = "reclamer")
	public ResponseEntity<?> doReclamation(ReclamationDto reclamationDto, HttpServletRequest request) {
		if (reclamationDto.getTypePassif() != null && reclamationDto.getIdCasPassif() != null) {
			if (reclamationDto.getTypePassif().equals(TypePassif.REDEMARE)) {
				if (StringUtils.isNotBlank(reclamationDto.getAncienCodeMembre())) {
					if (reclamationDto.getAncienCodeMembre().length() < 13) {
						StringUtils.leftPad(reclamationDto.getAncienCodeMembre(), 13, "0");
					}
				} else if (TypePassif.MCNP.equals(reclamationDto.getTypePassif())) {
					if (reclamationDto.getAncienCodeMembre().length() != 20) {
						return ResponseEntity.ok(new Result(0, "L'ancien code Membre n'est pas correct"));
					}
				}
			}
			try {
				EuReclamation rec = reclamationMapper.toEntity(reclamationDto);
				rec.setDateReclamation(LocalDateTime.now());
				rec.setValider(false);
				rec.setStatut(StatutReclamation.ENCOURS);
				reclamationService.create(rec);
				if (reclamationDto.getFiles() != null && !reclamationDto.getFiles().isEmpty()) {
					for (MultipartFile file : reclamationDto.getFiles()) {
						FileUploadUtils.upload(file);
						EuReclamationAttachment att = new EuReclamationAttachment();
						att.setFileName(FileUploadUtils.extractFilename(file));
						att.setReclamation(rec);
						attachmentRepository.save(att);
					}
				}
				return ResponseEntity.ok(new Result(0, "Opération effectuée avec sucès"));
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Result(1, "Echec de l'opération:" + e.getMessage()));
			}
		}
		return ResponseEntity.badRequest().body(new Result(0, "Veuillez renseigner les infos obligatoires"));
	}

	@GetMapping(value = "liste")
	@ResponseBody
	public JqGrid<ReclamationDto> loadReclamation(
			@RequestParam(value = "codeMembre", required = false) String codeMembre,
			@RequestParam(value = "typePassif", required = false) TypePassif typePassif,
			@RequestParam(value = "cas", required = false) Integer cas,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {

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

		Page<EuReclamation> capas = null;
		if (StringUtils.isNotBlank(codeMembre)) {
			if (typePassif != null) {
				capas = reclamationService.findByMembreAndTypePassif(codeMembre, typePassif, pageRequest);
			} else if (cas != null) {
				capas = reclamationService.findByMembreAndCasPassif(codeMembre, cas, pageRequest);
			} else {
				capas = reclamationService.findByCodeMembre(codeMembre, pageRequest);
			}
		}
		JqGrid<ReclamationDto> response = new JqGrid<>();

		if (Objects.nonNull(capas) && capas.getTotalElements() > 0) {
			response.setRows(reclamationMapper.toDtos(capas.getContent()));
			response.setTotal(Integer.toString(capas.getTotalPages()));
		} else {
			response.setRows(Lists.newArrayList());
			response.setTotal("0");
		}
		return response;
	}
}
