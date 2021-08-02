package com.esmc.mcnp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Lire la configuration relative au projet
 * 
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig
{
    /** Nom du projet */
    private String name;

    /** Version du Projet */
    private String version;

    /** Année du droit d'auteur */
    private String copyrightYear;

    /** Exemple de commutateur de démonstration */
    private boolean demoEnabled;

    /** Télécharger le chemin */
    private static String profile;

    /** Obtenir le changement d'adresse */
    private static boolean addressEnabled;
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        AppConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        AppConfig.addressEnabled = addressEnabled;
    }

    /**
     * Obtenir le chemin de téléchargement de l'avatar
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * Obtenir le chemin de téléchargement
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * Obtenir le chemin de téléchargement
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}