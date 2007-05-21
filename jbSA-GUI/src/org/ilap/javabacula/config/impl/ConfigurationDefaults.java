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

import java.util.Properties;

import org.ilap.javabacula.util.BaculaConstants;


public class ConfigurationDefaults {
    
    private static Properties _defaults;
    private static ConfigurationDefaults configDefaults = null;
    
    public  static synchronized ConfigurationDefaults getInstance() {
        //*** FIXME ***
        // We need a good locking mechanism here  
        if(configDefaults == null) { 
            configDefaults = new ConfigurationDefaults();
        }
        return configDefaults;
    }
  
    protected  ConfigurationDefaults() 
    {
        _defaults = new Properties();

        // General settings
        _defaults.setProperty(BaculaConstants.ID_JB_NAME,    BaculaConstants.JB_NAME);
        _defaults.setProperty(BaculaConstants.ID_JB_VERSION, BaculaConstants.JB_VERSION);
        
        // GUI Settings
        _defaults.setProperty(BaculaConstants.ID_SPLASH,        "true");	
        _defaults.setProperty(BaculaConstants.ID_STARTUP_DIR,   "" );
        _defaults.setProperty(BaculaConstants.ID_WINDOW_LEFT,   "10");
        _defaults.setProperty(BaculaConstants.ID_WINDOW_TOP,    "10");
        _defaults.setProperty(BaculaConstants.ID_WINDOW_WIDTH,  "640");
        _defaults.setProperty(BaculaConstants.ID_WINDOW_HEIGHT, "480");

        // Director settings
        // *** FIXME ***
        // Security Settings
        // *** FIXME ***
  }

  public Properties getDefaultsParameters() {
  	return _defaults;
  }
 
}
