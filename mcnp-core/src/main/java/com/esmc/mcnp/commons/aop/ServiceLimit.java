package com.esmc.mcnp.commons.aop;

import java.lang.annotation.*;

/**
 * Annotations personnalisées
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLimit {
    /**
     * la description
     */
    String description() default "";

    /**
     * Clé
     */
    String key() default "";

    /**
     * Types de
     */
    LimitType limitType() default LimitType.CUSTOMER;

    enum LimitType {
        /**
         * Clé personnalisée
         */
        CUSTOMER,
        /**
         * Selon l'IP du demandeur
         */
        IP
    }
}