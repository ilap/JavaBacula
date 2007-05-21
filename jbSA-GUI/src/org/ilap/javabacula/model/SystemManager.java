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

package org.ilap.javabacula.model;

import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.config.Configuration;
import org.ilap.javabacula.model.BaculaObject;
import org.ilap.javabacula.model.BaculaSystem;

/**
 *
 * @author ilap
 */
public class SystemManager extends BaculaObjectManager {
    
    private static SystemManager instance = null;    
    
    private SystemManager(int state) {
        super(state);
    }
    
    private static int initState() {
        return BaculaConstants.ALLSTATE;
    }

    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager(initState()); 
        } 
        return instance;
    }
    
    /**
     * Creates and return a new BaculaSystem.
     * 
     * 
     * 
     * @return the new BaculaSystem
     */
    public BaculaObject createBaculaItem() {
        return createBaculaItem(BaculaConstants.SYSTEM_TYPE);
    }


    /**
     * Save the available systems to the config file
     */
    public synchronized  void saveSystemsToConfig() {
        String s;
        BaculaSystem c;
        
        for (Integer i = 0; i < this.listModel.getSize(); i++) {
            c = (BaculaSystem) this.listModel.getElementAt(i);
            s =     c.getName() + ":" + 
                    c.getDirAddress() + ":" +
                    c.getDirPort().toString() + ":" +
                    c.getLoginName() + ":" +
                    c.getLastLogin();
            Configuration.setString(BaculaConstants.ID_CONF_DIR + "." + i.toString(),
                        s);
        }
    }    
}
