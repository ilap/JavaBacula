/*
 *  JavaBacula -- Java frontend of the Bacula® - The Network Backup Solution
 *  Copyright (C) 2007 by Pal DOROGI
 * 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by the
 *  Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Contact:   Pal DOROGI
 *  mailto:    pal.dorogi@gmail.com
 * 
 *  $Id$
 */

package org.ilap.javabacula.config.impl;

// JAVA libraries
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

// 3rd Party libraries
import org.apache.log4j.Logger;

// Private libraries
import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.config.impl.ConfigurationDefaults;


public class PropertyManager {
 
    private static final Logger LOGGER = 
            Logger.getRootLogger();

    private Properties _propertyHolder = null;

    public PropertyManager() {
	_propertyHolder = new Properties();
    }

    /**
     * Returns the default path for configuration file.
     *
     * @return a generic path string.
     */
    public String getDefaultPath() {
	return BaculaConstants.CONFIG_DIR;
    }


    /**
     * Load the configuration from a specified location.
     *
     * @param fromFile  the path to load the configuration from.
     *
     * @return true if the load was successful, false if not.
     */
    public boolean loadFromFile(File fromFile) {
        try {

            _propertyHolder.load(new FileInputStream(fromFile));
            LOGGER.info("Configuration is loaded from " + fromFile);
            return true;
            
        } catch (Exception e) {

            try {
                
                fromFile.createNewFile();
                if (fromFile.exists() && fromFile.isFile()) {
                    LOGGER.info("An empty configuration  file is created: " + fromFile);
                    return true;
                }
                
            } catch (IOException ioe) {
               _propertyHolder = 
                               ConfigurationDefaults.getInstance().getDefaultsParameters();
               LOGGER.warn("Unable to create configuration file " + fromFile + 
                           ", we will use the default settings", ioe);
            }
        }

        return false;
    }

    /**
     * Save the configuration to a specified location.
     *
     * @param file  the path to save the configuration at.
     *
     * @return true if the save was successful, false if not.
     */
    boolean saveToFile(File toFile) {
	try {
            
	    _propertyHolder.store(new FileOutputStream(toFile), 
                                  "Javabacula System Properties");
	    LOGGER.info("Configuration is saved to: " + toFile);
	    return true;
            
	} catch (Exception e) {
            LOGGER.warn("Unable to save configuration file: " + toFile);
	}
	return false;
    }

    public String getValue(String key, String defaultValue) {
	String result = "";
	
        try {
	    result = _propertyHolder.getProperty(key, defaultValue);
	} catch (Exception e) {
	    result = defaultValue;
	}
	
        LOGGER.debug("getValue(" + key + ") returns:" + result);
        return result;
    }

    public void setValue(String key, String value) {
	_propertyHolder.setProperty(key, value);
	LOGGER.debug("setValue(" + key + "," + value + ")");
    }
 
    public void removeKey(String key) {
	_propertyHolder.remove(key);
    }
    
}

