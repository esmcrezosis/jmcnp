package com.esmc.mcnp.domain.dto.desendettement;

import com.esmc.mcnp.domain.entity.pc.StatutReclamation;
import com.esmc.mcnp.domain.entity.pc.TypePassif;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class ReclamationDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateReclamation;
    private TypePassif typePassif;
    private Integer idCasPassif;
    private String codeMembre;
    private String ancienCodeMembre;
    private String numeroBonMf;
    private String libelleReclamation;
    private String descriptionRec;
    private Double montantReclamation;
    private Double montantValide;
    private boolean valider;
    private StatutReclamation statut;
    private List<MultipartFile> files;
}
