package com.epam.dsb.dk.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesResourceManager {
    private static final Logger LOG = Logger.getLogger(PropertiesResourceManager.class);
    private Properties properties= new Properties();

    public PropertiesResourceManager() {
        properties= new Properties();
    }

    public PropertiesResourceManager(final String resourceName) {
        properties = appendFromResource(properties, resourceName);
    }

    private Properties appendFromResource(final Properties objProperties, final String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                LOG.info(e.getMessage());
            }
        } else {
            LOG.error(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
        return objProperties;
    }

    public String getProperty(final String key){
        return properties.getProperty(key);
    }
}
