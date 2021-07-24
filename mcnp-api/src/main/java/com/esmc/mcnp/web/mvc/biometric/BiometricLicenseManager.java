/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package com.esmc.mcnp.web.mvc.biometric;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.web.fingerprint.BiometricConfig;
import com.esmc.mcnp.web.mvc.utils.Utils;
import com.neurotec.lang.NCore;
import com.neurotec.licensing.NLicense;
import com.sun.jna.Platform;

/**
 * Component that obtains and releases licenses for particular biometric
 * components
 */
@Component
public class BiometricLicenseManager {

    protected final Log log = LogFactory.getLog(this.getClass());

    public static final String FINGER_MATCHING_COMPONENT = "Biometrics.FingerMatching";
    public static final String FINGER_EXTRACTION_COMPONENT = "Biometrics.FingerExtraction";
    public static final String FINGER_SCANNING_COMPONENT = "Devices.FingerScanners";
    public static final String MEDIA_COMPONENT = "Media";
    private static final String WIN32_X86 = "Win32_x86";
    private static final String WIN64_X64 = "Win64_x64";
    private static final String LINUX_X86 = "Linux_x86";
    private static final String LINUX_X86_64 = "Linux_x86_64";

    @Value(value = "${library.dir}")
    private String libraryDirPath;

    @Autowired
    BiometricConfig config;

    /**
     * On startup, we ensure licenses are appropriately added and the
     * fingerprint server is available
     */
    @PostConstruct
    public void startup() {
        log.info("Initializing Biometric Licenses");
        initLibraryPath();
        addLicenses();
    }

    @PreDestroy
    public void cleanup() {
        log.info("Neurotechnology Core shutting down.");
        NCore.shutdown();
    }

    /**
     * Neurotechnology requires a license, which may be provided via license
     * files Any configured license files will be attempted to be loaded
     */
    public void addLicenses() {
        if (config.getLicenseFiles() == null || config.getLicenseFiles().isEmpty()) {
            log.debug("No license files configured to load.");
        } else {
            log.debug("Number of License Files configured: " + config.getLicenseFiles().size());
            config.getLicenseFiles().forEach((File licenseFile) -> {
                try {
                    String licenseContent = FileUtils.readFileToString(licenseFile, "UTF-8");
                    NLicense.add(licenseContent);
                    log.debug("Added license: " + licenseFile.getName());
                } catch (IOException e) {
                    throw new BiometricServiceException("An error occurred while activating license from file: " + licenseFile, e);
                }
            });

        }
    }

    /**
     * Obtain matching license
     */
    public void obtainMatchingLicense() {
        obtainLicense(FINGER_MATCHING_COMPONENT);
    }

    /**
     * Obtain media license
     */
    public void obtainMediaLicense() {
        obtainLicense(MEDIA_COMPONENT);
    }

    /**
     * Obtain extraction license
     */
    public void obtainExtractionLicense() {
        obtainLicense(FINGER_EXTRACTION_COMPONENT);
    }

    /**
     * Obtain extraction license
     */
    public void obtainScanningLicense() {
        obtainLicense(FINGER_SCANNING_COMPONENT);
    }

    /**
     * Release matching license
     */
    public void releaseMatchingLicense() {
        releaseLicense(FINGER_MATCHING_COMPONENT);
    }

    /**
     * Release extraction license
     */
    public void releaseExtractionLicense() {
        releaseLicense(FINGER_EXTRACTION_COMPONENT);
    }

    /**
     * Release scanning license
     */
    public void releaseScanningLicense() {
        releaseLicense(FINGER_SCANNING_COMPONENT);
    }

    /**
     * Before operations requiring use of the Neurotechnology components, one
     * must obtain a license for the particular component
     *
     * @param component
     */
    public void obtainLicense(String component) {
        log.debug("Obtaining license for component: " + component);
        try {
            if (!NLicense.obtainComponents("/local", 5000, component)) {
                throw new BiometricServiceException("Unable to obtain a license for " + component);
            }
        } catch (IOException e) {
            throw new BiometricServiceException("Unable to obtain a license for " + component, e);
        }
        log.debug("Obtained license for component: " + component);
    }

    /**
     * After operations requiring use of the Neurotechnology components, one
     * must release the license for the particular component
     *
     * @param component
     */
    protected void releaseLicense(String component) {
        log.debug("Releasing license for component: " + component);
        try {
            NLicense.releaseComponents(component);
            log.debug("License released...");
        } catch (IOException e) {
            throw new BiometricServiceException("An error occurred while releasing " + component + " license", e);
        }
    }

    // ===========================================================
    // Public static methods
    // ===========================================================
    public void initLibraryPath() {
        String libraryPath = getLibraryPath();
        System.out.println("Chargement du JNA Jar Chemin : " + libraryPath);
        String jnaLibraryPath = System.getProperty("jna.library.path");
        if (Utils.isNullOrEmpty(jnaLibraryPath)) {
            System.setProperty("jna.library.path", libraryPath);
        } else {
            System.setProperty("jna.library.path", String.format("%s%s%s", jnaLibraryPath, Utils.PATH_SEPARATOR, libraryPath));
        }
        System.setProperty("java.library.path", String.format("%s%s%s", System.getProperty("java.library.path"), Utils.PATH_SEPARATOR, libraryPath));
        if (Platform.isMac()) {
            String jnaPlatformLibraryPath = System.getProperty("jna.platform.library.path");
            if (Utils.isNullOrEmpty(jnaPlatformLibraryPath)) {
                System.setProperty("jna.platform.library.path", libraryPath);
            } else {
                System.setProperty("jna.platform.library.path", String.format("%s%s%s", jnaPlatformLibraryPath, Utils.PATH_SEPARATOR, libraryPath));
            }
            System.setProperty("java.platform.library.path", String.format("%s%s%s", System.getProperty("java.platform.library.path"), Utils.PATH_SEPARATOR, libraryPath));
        }
        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public String getLibraryPath() {
    	System.out.println("Chargement des Dlls Chemin : " + libraryDirPath);
        StringBuilder path = new StringBuilder();
        int index = libraryDirPath.lastIndexOf(Utils.FILE_SEPARATOR);
        if (index == -1) {
            return null;
        }
        String part = libraryDirPath.substring(0, index);
        System.out.println("Part : " + part);
        if (Platform.isWindows()) {
            if (part.endsWith("Bin")) {
            	System.out.println("On est ds le BIN");
                path.append(part);
                path.append(Utils.FILE_SEPARATOR);
                path.append(Platform.is64Bit() ? WIN64_X64 : WIN32_X86);
            }
        } else if (Platform.isLinux()) {
            index = part.lastIndexOf(Utils.FILE_SEPARATOR);
            if (index == -1) {
                return null;
            }
            part = part.substring(0, index);
            path.append(part);
            path.append(Utils.FILE_SEPARATOR);
            path.append("Lib");
            path.append(Utils.FILE_SEPARATOR);
            path.append(Platform.is64Bit() ? LINUX_X86_64 : LINUX_X86);
        } else if (Platform.isMac()) {
            index = part.lastIndexOf(Utils.FILE_SEPARATOR);
            if (index == -1) {
                return null;
            }
            part = part.substring(0, index);
            path.append(part);
            path.append(Utils.FILE_SEPARATOR);
            path.append("Frameworks");
            path.append(Utils.FILE_SEPARATOR);
            path.append("MacOSX");
        }
        return path.toString();
    }
}
