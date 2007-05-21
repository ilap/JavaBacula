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

package org.ilap.javabacula.config;

import java.io.File;

import java.beans.PropertyChangeListener;

import org.ilap.javabacula.config.impl.ConfigurationManager;


public class Configuration {
    
    /**
     * The only occurance of the configuration handler.
     */
    private static ConfigurationManager config =
            new ConfigurationManager();
    // ***FIXME*** .getInstance();
    
    // No more Instances
    private Configuration() {
    }
    
    /**
     * Returns the single instance of the Configuration Manager.
     *
     * @return the Configuration Manager
     */
    public static ConfigurationManager getConfigurationManager() {
        return config;
    }
    
    /**
     * Load the configuration from the default location.
     *
     * The configuration will be automatically loaded from the default
     * location the first time a value is queried or modified, if it
     * had not been previously loaded.  Only the first load request
     * will be honored, so if the configuration is to be loaded from
     * a non-default location, load(name) must be used prior to any
     * other call.  The configuration can be loaded only one time.
     *
     * Implementations must ignore load requests once a load is
     * already successful, and must return false for each of those
     * ignored requests.
     *
     * @return true if the load is successful, otherwise false
     */
    public static final boolean load() {
        return config.loadDefaults();
    }
    
    /**
     * Load the configuration from a specified file.
     *
     * @param file the File to load
     *
     * @return true if the load is successful, otherwise false
     */
    public static final boolean load(File file) {
        return config.load(file);
    }
    
    /**
     * Save the configuration to the default location.
     *
     * Implementations do not have to handle this method.
     * If the method is not allowed or it fails, the implementation
     * must return false.
     *
     * @return true if the save is successful, otherwise false
     */
    public static final boolean save() {
        return Configuration.save(false);
    }
    
    /**
     * Save the configuration to the default location.
     *
     * Implementations do not have to handle this method.
     * If the method is not allowed or it fails, the implementation
     * must return false.
     *
     * @param force the file to save even if it would not normally
     * be saved.
     *
     * @return true if the save is successful, otherwise false
     */
    public static final boolean save(boolean force) {
        return config.saveDefaults();
    }
    
    /**
     * Returns the string value of a configuration property.
     *
     * @param key the key to retrieve the value of
     *
     * @return the string value of the parameter if it exists, otherwise
     * a zero length string
     */
    public static String getString(String key) {
        return getString(key, "");
    }
    
    /**
     * Returns the string value of a configuration property.
     *
     * @param key the key to retrieve the value of
     * @param defaultValue the value to return if the key does not exist
     *
     * @return the string value of the parameter if it exists, otherwise the
     *   default value
     */
    public static final String getString(String key,
            String defaultValue) {
        return config.getString(key, defaultValue);
    }
    
    /**
     * Returns the numeric value of a configuration property.
     *
     * @param key the key to retrieve the value of
     *
     * @return the string value of the parameter if it exists, otherwise zero
     */
    public static final int getInteger(String key) {
        return getInteger(key, 0);
    }
    
    /**
     * Returns the numeric value of a configuration property.
     *
     * @param key the key to retrieve the value of
     * @param defaultValue if the key is not found
     *
     * @return the string value of the parameter if it exists,
     *         otherwise the default value
     */
    public static final double getDouble(String key,
            double defaultValue) {
        return config.getDouble(key, defaultValue);
    }
    
    /**
     * Returns the numeric value of a configuration property.
     *
     * @param key the key to retrieve the value of
     *
     * @return the string value of the parameter if it exists, otherwise zero
     */
    public static final double getDouble(String key) {
        return getDouble(key, 0);
    }
    
    /**
     * Returns the numeric value of a configuration property.
     *
     * @param key the key to retrieve the value of
     * @param defaultValue the value to return if the key does not exist
     *
     * @return the numeric value of the parameter if it exists, otherwise
     *  the default value
     */
    public static final int getInteger(String key, int defaultValue) {
        return config.getInteger(key, defaultValue);
    }
    
    /**
     * Returns the boolean value of a configuration property.
     *
     * @param key the key to retrieve the value of
     *
     * @return the boolean value of the parameter if it exists, otherwise false
     */
    public static final boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    
    /**
     * Returns the boolean value of a configuration property.
     *
     * @param key the key to retrieve the value of
     * @param defaultValue the value to return if the key does not exist
     *
     * @return the boolean value of the parameter if it exists, otherwise
     *  the default value
     */
    public static final boolean getBoolean(String key,
            boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }
    
    /**
     * Sets the string value of a configuration property.
     *
     * @param key the key to set
     * @param newValue the value to set the key to.
     */
    public static final void setString(String key, String newValue) {
        config.setString(key, newValue);
    }
    
    /**
     * Sets the numeric value of a configuration property.
     *
     * @param key the key to set
     * @param newValue the value to set the key to.
     */
    public static final void setInteger(String key, int newValue) {
        config.setInteger(key, newValue);
    }
    
    /**
     * Sets the numeric value of a configuration property.
     *
     * @param key the key to set
     * @param newValue the value to set the key to.
     */
    public static final void setDouble(String key, double newValue) {
        config.setDouble(key, newValue);
    }
    
    /**
     * Sets the boolean value of a configuration property.
     *
     * @param key the key to set
     * @param newValue the value to set the key to.
     */
    public static final void setBoolean(String key,
            boolean newValue) {
        config.setBoolean(key, newValue);
    }
    
    /**
     * Adds a property change listener.
     *
     * @param pcl The property change listener to add
     */
    public static final void addListener(PropertyChangeListener pcl) {
        config.addPropertyChangeListener(pcl);
    }
    
    /**
     * Removes a property change listener.
     *
     * @param pcl The property change listener to remove
     */
    public static final void removeListener(PropertyChangeListener pcl) {
        config.removePropertyChangeListener(pcl);
    }
    
    /**
     * Create a single component configuration key.
     *
     * @param k1 key component 1.
     * @return the new {@link String}.
     */
    public static String makeKey(String k1) {
        return "javabacula." + k1;
    }
    
    /**
     * Create a single component configuration key.
     *
     * @param k1 key component 1.
     * @return the new {@link String}.
     */
    public static void removeKey(String k1) {
        if (config.hasKey(k1)) {
            config.removeKey(k1);
        }
    }
}

