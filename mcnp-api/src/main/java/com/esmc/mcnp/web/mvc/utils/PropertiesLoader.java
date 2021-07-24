package com.esmc.mcnp.web.mvc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	public PropertiesLoader() {
		// TODO Auto-generated constructor stub
	}
	
	public static Properties loadProperties(String resourceFileName){
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
          .getClassLoader()
          .getResourceAsStream(resourceFileName);
        try {
			configuration.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return configuration;
    }

}
