package com.esmc.mcnp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static com.esmc.mcnp.core.constant.AppConstant.*;
import static com.esmc.mcnp.commons.util.AppUtils.ensureSuffix;


/**
 * Halo configuration properties.
 *
 * @author johnniang
 */
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    /**
     * Doc api disabled. (Default is true)
     */
    private boolean docDisabled = true;

    /**
     * Production env. (Default is true)
     */
    private boolean productionEnv = true;

    /**
     * Authentication enabled
     */
    private boolean authEnabled = true;

    /**
     * Admin path.
     */
    private String adminPath = "admin";
    
    private String carteUrl;
    
    private String esmc;

    /**
     * Work directory.
     */
    private String workDir = ensureSuffix(USER_HOME, FILE_SEPARATOR) + ".mcnp" + FILE_SEPARATOR;

    /**
     * Halo backup directory.(Not recommended to modify this config);
     */
    private String backupDir = ensureSuffix(TEMP_DIR, FILE_SEPARATOR) + "mcnp-backup" + FILE_SEPARATOR;

    /**
     * Upload prefix.
     */
    private String uploadUrlPrefix = "upload";

    /**
     * Download Timeout.
     */
    private Duration downloadTimeout = Duration.ofSeconds(30);

    public AppProperties() throws IOException {
        // Create work directory if not exist
        Files.createDirectories(Paths.get(workDir));
        Files.createDirectories(Paths.get(backupDir));
    }
}
