package com.esmc.mcnp.web.fingerprint;

import com.esmc.mcnp.web.mvc.utils.PropertiesLoader;
import com.neurotec.licensing.NLicense;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class LicenseManager {
private final Map<String, Boolean> licenses;
    // ===========================================================
    // Private static final fields
    // ===========================================================
    protected static final Log log = LogFactory.getLog(LicenseManager.class);
    private static final String ADDRESS = "/local";
    private static final String PORT = "5000";

    // ===========================================================
    // Public static final fields
    // ===========================================================
    public static final String PROGRESS_CHANGED_PROPERTY = "progress";
    public static final String LAST_STATUS_MESSAGE_PROPERTY = "last-status-message";
    public static final String LICENSE_DIR_NAME = PropertiesLoader.loadProperties("application.properties").getProperty("license.dir");

    // ===========================================================
    // Private static fields
    // ===========================================================
    private static LicenseManager instance;

    // ===========================================================
    // Private fields
    // ===========================================================
    private final PropertyChangeSupport propertyChangeSupport;
    private final Set<String> obtainedLicenses;
    private final Map<String, Boolean> licenseCache;
    private int progress;
    private String lastStatusMessage;
    private boolean debug = true;

    // ===========================================================
    // Private constructors
    // ===========================================================
    private LicenseManager() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        obtainedLicenses = new HashSet<String>();
        licenseCache = new HashMap<String, Boolean>();
        lastStatusMessage = "";
        licenses = new HashMap<String, Boolean>();
    }

    // ===========================================================
    // Public static methods
    // ===========================================================
    public static LicenseManager getInstance() {
        synchronized (LicenseManager.class) {
            if (instance == null) {
                instance = new LicenseManager();
            }
            return instance;
        }
    }

    // ===========================================================
    // Private methods
    // ===========================================================
    private void setProgress(int progress) {
        int oldProgress = getProgress();
        this.progress = progress;
        propertyChangeSupport.firePropertyChange(PROGRESS_CHANGED_PROPERTY, oldProgress, progress);
    }

    // ===========================================================
    // Public methods
    // ===========================================================
    public boolean isActivated(String component) {
        return isActivated(component, false);
    }

    public boolean isActivated(String component, boolean cache) {
        if (component == null) {
            throw new NullPointerException("component");
        }
        if (cache) {
            if (licenseCache.containsKey(component)) {
                return licenseCache.get(component);
            }
        }
        boolean result;
        try {
            result = NLicense.isComponentActivated(component);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (cache) {
            licenseCache.put(component, result);
        }
        return result;
    }

    public synchronized boolean obtain(Collection<String> licenses) throws IOException {
        return obtain(licenses, ADDRESS, PORT);
    }

    public synchronized void addLicenses() {
    	System.out.println("LICENSE_DIR_NAME= "+LICENSE_DIR_NAME);
        File LICENSE_DIR = new File(LICENSE_DIR_NAME);
        List<File> licenseFiles = new ArrayList<>();
     	
        
        if (LICENSE_DIR.exists()) {
            licenseFiles.addAll(Arrays.asList(LICENSE_DIR.listFiles()));
            System.out.println("licenseFiles size= "+licenseFiles.size());
            if (licenseFiles.size() > 0) {
                licenseFiles.forEach(f -> System.out.println("Fichier License : " + f.getName()));
            }
        }
        if (licenseFiles == null || licenseFiles.isEmpty()) {
            log.debug("No license files configured to load.");
        } else {
            log.debug("Number of License Files configured: " + licenseFiles.size());
            licenseFiles.forEach((File licenseFile) -> {
                try {
                    String licenseContent = FileUtils.readFileToString(licenseFile, "UTF-8");
                    NLicense.add(licenseContent);
                    log.debug("Added license: " + licenseFile.getName());
                } catch (IOException e) {
                    throw new com.esmc.mcnp.web.mvc.biometric.BiometricServiceException("An error occurred while activating license from file: " + licenseFile, e);
                }
            });

        }
    }

    public synchronized boolean obtain(Collection<String> licenses, String address, String port) throws IOException {
        String oldStatus = lastStatusMessage;
        lastStatusMessage = String.format("Obtaining licenses from server %s:%s\n", address, port);
        propertyChangeSupport.firePropertyChange(LAST_STATUS_MESSAGE_PROPERTY, oldStatus, lastStatusMessage);
        if (debug) {
            System.out.print(lastStatusMessage);
        }
        int i = 0;
        setProgress(i);
        boolean result = false;
        try {
            for (String license : licenses) {
                boolean obtained = false;
                try {
                    obtained = NLicense.obtainComponents(address, port, license);
                    if (obtained) {
                        obtainedLicenses.add(license);
                    }
                    result |= obtained;
                } finally {
                    oldStatus = lastStatusMessage;
                    lastStatusMessage = license + ": " + (obtained ? "obtained" : "not obtained") + "\n";
                    propertyChangeSupport.firePropertyChange(LAST_STATUS_MESSAGE_PROPERTY, oldStatus, lastStatusMessage);
                    if (debug) {
                        System.out.print(lastStatusMessage);
                    }
                }
                setProgress(++i);
            }
        } finally {
            setProgress(100);
        }
        return result;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isProgress() {
        return (progress != 0) && (progress != 100);
    }

    public int getProgress() {
        return progress;
    }

    public int getLicenseCount() {
        return obtainedLicenses.size();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public boolean obtainLicenses(List<String> names) throws IOException {
		if (names == null) {
			return true;
		}
		boolean result = true;
		for (String license : names) {
			if (isActivated(license)) {
				System.out.println(license + ": " + " already obtained");
			} else {
				boolean state = NLicense.obtainComponents(ADDRESS, PORT, license);
				licenses.put(license, state);
				if (state) {
					System.out.println(license + ": obtained");
				} else {
					result = false;
					System.out.println(license + ": not obtained");
				}
			}
		}
		return result;
	}


}
