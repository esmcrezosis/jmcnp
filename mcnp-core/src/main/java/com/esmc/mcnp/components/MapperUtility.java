package com.esmc.mcnp.components;

import com.esmc.mcnp.dto.obps.Article;
import org.springframework.stereotype.Component;

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
