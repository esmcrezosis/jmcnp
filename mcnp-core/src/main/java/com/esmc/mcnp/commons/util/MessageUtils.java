package com.esmc.mcnp.commons.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.esmc.mcnp.infrastructure.components.SpringUtils;

/**
 * Obtenir les fichiers de ressources i18n
 * 
 * 
 */
public class MessageUtils
{
	/**
    * Obtenez le message en fonction de la clé et des paramètres du message et déléguez au message de printemps
    *
    * Touche de message de code @param
    * Paramètres @param args
    * @return Obtenez la valeur de la traduction internationalisée
    */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
