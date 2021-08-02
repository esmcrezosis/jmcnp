package com.esmc.mcnp.commons.model;

import java.util.HashMap;

import com.esmc.mcnp.commons.util.StringUtils;

/**
 * Rappel de message d'opération
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** code d'état */
    public static final String CODE_TAG = "code";

    /** Contenu de retour */
    public static final String MSG_TAG = "msg";

    /** Objet de données */
    public static final String DATA_TAG = "data";

    /**
     * Type d'état
     */
    public enum Type
    {
        /** Succès */
        SUCCESS(0),
        /** Avertissement */
        WARN(301),
        /** Erreur */
        ERROR(500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /**
     * Initialiser un objet AjaxResult nouvellement créé afin qu'il représente un message vide.
     */
    public AjaxResult()
    {
    }

    /**
     * Initialiser un objet AjaxResult nouvellement créé
     *
     * @param type d'état de type
     * @param msg renvoie le contenu
     */
    public AjaxResult(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * Initialiser un objet AjaxResult nouvellement créé
     *
     * @param type type d'état
     * @param msg renvoie le contenu
     * @param data objet de données
     */
    public AjaxResult(Type type, String msg, Object data)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * Retourner le message de réussite
     *
     * @return message de réussite
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("Opération réussie");
    }

    /**
     * Renvoyer les données réussies
     *
     * @return message de réussite
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("Opération réussie", data);
    }

    /**
     * Retourner le message de réussite
     *
     * @param msg renvoie le contenu
     * @return message de réussite
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * Retourner le message de réussite
     *
     * @param msg renvoie le contenu
     * @param data objet de données
     * @return message de réussite
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(Type.SUCCESS, msg, data);
    }

    /**
     * Retour message d'avertissement
     *
     * @param msg renvoie le contenu
     * @return message d'avertissement
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * Retour message d'avertissement
     *
     * @param msg renvoie le contenu
     * @param data objet de données
     * @return message d'avertissement
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(Type.WARN, msg, data);
    }

    /**
     * Renvoyer un message d'erreur
     * 
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("L'opération a échoué");
    }

    /**
     * Retourner un message d'erreur
     *
     * @param msg renvoie le contenu
     * @return message d'avertissement
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * Retourner un message d'erreur
     *
     * @param msg renvoie le contenu
     * @param data objet de données
     * @return message d'avertissement
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(Type.ERROR, msg, data);
    }
    
    /**
     * Retourner un message d'erreur
     * @param code code d'erreur
     * @param msg contenu
     * @return message d'erreur
     */
    public static AjaxResult error(int code, String msg)
    {
        AjaxResult json = new AjaxResult();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }
    
    
    /**
     * Retourner le message de réussite
     *
     * @param msg contenu 
     * @return message de réussite
     */
    public static AjaxResult success2(String msg)
    {
        AjaxResult json = new AjaxResult();
        json.put("msg", msg);
        json.put("code", 0);
        return json;
    }
    
    /**
     * La réponse renvoie les résultats
     * @param rows affectent le nombre de lignes
     * @return résultat de l'opération
     */
    public static AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * La réponse renvoie les résultats
     * @param résultat
     * @return résultat de l'opération
     */
    public static AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }
    
    public  boolean isSuccess(){
        Object o=this.get(AjaxResult.CODE_TAG);
        if(o!=null){
            int  code=(Integer) o;
            if(code== Type.SUCCESS.value){
                return true;
            }
        }
        return false;
    }
    
}
