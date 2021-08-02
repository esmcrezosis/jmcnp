package com.esmc.mcnp.domain.dto.ksu;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import com.esmc.mcnp.domain.enums.LivraisonEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class LivraisonKsu {
    private Long id;
    private String codeSuivi;
    private Integer nombre;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateDemande;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateLivraison;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateReception;
    private LivraisonEnum status;
}
