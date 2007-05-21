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



// import org.ilap.javabacula.core.config.impl.*;

import java.io.File;
import java.util.Properties;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.ilap.javabacula.util.BaculaConstants;


import org.apache.log4j.Logger;
import java.beans.*;

public class ConfigurationManager extends PropertyManager {
    
    private static PropertyChangeSupport _pcs = null;
 
    private File       _propertyFile = null;
    private boolean    _isLoaded     = false;
    private boolean    _isChanged    = false;


    public String getDefaultPath() {
        return BaculaConstants.CONFIG_DIR;
    }

    private void loadIfNeeded() {
	if (!_isLoaded) {
	    loadDefaults();
	}
    }

    public final boolean loadDefaults() {
	if (_isLoaded) {
	    return false;
	}

	boolean status = load(new File(getDefaultPath()));

        if (status == true) {
            
        }
        // *** FIXME *** If can not load the property file
        // why _isLoaded result is true
        _isLoaded = true;
	return status;
    }

    public final boolean saveDefaults() {
        
        File toFile = new File(getDefaultPath());

        boolean saved = saveToFile(toFile);
        if (saved) {
            _propertyFile = toFile;
	    return saved;
	}
        
	if (!_isLoaded) {
	    return false;
	}

	if (_propertyFile != null) {
	    return saveToFile(_propertyFile);
	}
	return false;
    }

    public final boolean isChanged()    { return _isChanged; }
    public final boolean isLoaded()     { return _isLoaded; }

    
    public final boolean load(File file) {
	
        boolean status = loadFromFile(file);
	if (status) {
	}
	return status;
    }

    public final boolean save(File file) {
	
        if (!_isLoaded) {
	    return false;
	}
	
        return saveToFile(file);
    }

    public final String getString(String key, String defaultValue) {
	loadIfNeeded();
	return getValue(key, defaultValue);
    }

    public final int getInteger(String key, int defaultValue) {
	loadIfNeeded();
	try {
	    String s = getValue(key, Integer.toString(defaultValue));
	    return Integer.parseInt(s);
	} catch (NumberFormatException nfe) {
	    return defaultValue;
	}
    }

    
    public final double getDouble(String key, double defaultValue) {
	loadIfNeeded();
	try {
	    String s = getValue(key, Double.toString(defaultValue));
	    return Double.parseDouble(s);
	} catch (NumberFormatException nfe) {
	    return defaultValue;
	}
    }

    public final boolean getBoolean(String key, boolean defaultValue) {
	loadIfNeeded();
	Boolean dflt = new Boolean(defaultValue);
	Boolean b =
	    key != null
	    ? new Boolean(getValue(key, dflt.toString()))
	    : dflt;
	return b.booleanValue();
    }


    /**
     * Sets the string value of a configuration property.
     *
     * @param key the configuration key to modify.
     * @param newValue the value to set the key to.
     */
    public final void setString(String key, String newValue) {
	setValue(key, newValue);
    }

    /**
     * Sets the numeric value of a configuration property.
     *
     * @param key the configuration key to modify.
     * @param value the value to set the key to.
     */
    public final void setInteger(String key, int value) {
	setValue(key, Integer.toString(value));
    }

    /**
     * Sets the numeric value of a configuration property.
     *
     * @param key the configuration key to modify.
     * @param value the value to set the key to.
     */
    public final void setDouble(String key, double value) {
	setValue(key, Double.toString(value));
    }

    public final void setBoolean(String key, boolean value) {
	Boolean bool = new Boolean(value);
	setValue(key, bool.toString());
    }
    
    public boolean hasKey(String key) {
	return getValue(key, "true").equals(getValue(key, "false"));
    }

    
    /**
     * Adds a property change listener.
     *
     * @param pcl The class which will listen for property changes.
     */
    public final void addPropertyChangeListener(PropertyChangeListener pcl) {
	
        if (_pcs == null) {
	    _pcs = new PropertyChangeSupport(this);
	}
	_pcs.addPropertyChangeListener(pcl);
    }

    /**
     * Removes a property change listener.
     *
     * @param pcl The class to remove as a property change listener.
     */
    public final void removePropertyChangeListener(PropertyChangeListener pcl) {
	
        if (_pcs != null) {
	    _pcs.removePropertyChangeListener(pcl);
	}
    }
    

    public void setValue(String key, String newValue) {
	loadIfNeeded();
        
	String oldValue = getValue(key, newValue);
	super.setValue(key, newValue);
	
        if (_pcs != null) {
	    _pcs.firePropertyChange(key, oldValue, newValue);
	}
    }
}


