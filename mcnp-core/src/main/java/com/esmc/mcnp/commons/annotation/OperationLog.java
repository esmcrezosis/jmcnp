package com.esmc.mcnp.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.esmc.mcnp.commons.model.BusinessType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * Titre du module
     */
    String title() default "";

    /**
     * Type d'entreprise
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * S'il faut enregistrer les paramètres demandés
     */
    boolean saveRequestParams() default true;

}
