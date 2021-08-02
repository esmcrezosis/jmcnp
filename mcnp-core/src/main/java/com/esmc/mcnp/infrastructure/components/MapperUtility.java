package com.esmc.mcnp.infrastructure.components;

import org.springframework.stereotype.Component;

import com.esmc.mcnp.domain.dto.obps.Article;

@Component
public class MapperUtility {
    public Article buildOrderDTO(String codeMembreMorale, String reference, String designation, Double prix) {
        Article article = new Article();
        article.setCodeMembreMorale(codeMembreMorale);
        article.setDesignation(designation);
        article.setReference(reference);
        article.setPrix(prix);
        return article;
    }
}
