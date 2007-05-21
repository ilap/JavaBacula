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

import com.jgoodies.binding.beans.Model;

/**
 *
 * @author ilap
 */
abstract public class BaculaObject extends Model {
        
    public static final String PROPNAME_NAME        = "name";
    public static final String PROPNAME_TYPE        = "type";
    protected String  name = "Unknown";
    protected int     type = BaculaConstants.UNKNOWN_TYPE;
    

    /** Creates a new instance of BaculaObject */
    public BaculaObject(int type) {
        setType(type);
    }

    /** Creates a new instance of BaculaObject */
    public BaculaObject() {
        setType(BaculaConstants.UNKNOWN_TYPE);
    }

    /**
     * Returns this bacula object's type. 
     * 
     * @return this bacula object's type.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets this bacula object's name property and notifies observers about changes. 
     * 
     * @param name   The daemon name to set.
     */
    public void setType(int type) {
        int otype = getType();
        this.type = type;
        firePropertyChange(PROPNAME_TYPE, otype, this.type);
    }
    
    /**
     * Returns this bacula object's name. 
     * 
     * @return this bacula object's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this bacula object's name property and notifies observers about changes. 
     * 
     * @param name   The bacula object's name to set.
     */
    public synchronized void setName(String name) {
        String oname = getName();
        this.name = name;
        firePropertyChange(PROPNAME_NAME, oname, this.name);
    }

    public String toString() {
        return getName();
    }

    abstract public void initializeObject();
    abstract public void releaseObject();
}
