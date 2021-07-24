package com.esmc.mcnp.core.constant;

import java.io.File;
import java.time.Duration;

/**
 * <pre>
 *     Constante publique
 * </pre>
 *
 * @author ryanwang
 * @date 2017/12/29
 */
public class AppConstant {

    /**
     * User home directory.
     */
    public final static String USER_HOME = System.getProperties().getProperty("user.home");

    /**
     * Temporary directory.
     */
    public final static String TEMP_DIR = System.getProperties().getProperty("java.io.tmpdir");
    /**
     * Version constant. (Available in production environment)
     */
    public static final String APP_VERSION;

    /**
     * Path separator.
     */
    public static final String FILE_SEPARATOR = File.separator;

    public final static Duration TEMP_TOKEN_EXPIRATION = Duration.ofDays(7);
    /**
     * user_session
     */
    public static String USER_SESSION_KEY = "user_session";

    static {
        // Set version
        APP_VERSION = AppConstant.class.getPackage().getImplementationVersion();
    }
}
