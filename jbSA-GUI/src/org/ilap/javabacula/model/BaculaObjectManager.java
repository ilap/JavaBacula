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

import javax.swing.ListModel;

import com.jgoodies.binding.list.ArrayListModel;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
abstract public class BaculaObjectManager extends BaculaObjectFactory {
    
    // Createable, editable, deleteable, refreshable 
    protected int   state = BaculaConstants.NOSTATE;

    protected final ArrayListModel      listModel;
    
    protected BaculaObjectManager(int state) {
        setState(state);
        listModel = new ArrayListModel();
    }
 
    public ListModel getManagedObjects() {
        return listModel;
    }    
    
    public void setState(int state) {
        this.state = state;
    }
    
    public boolean isState(int state) {
        return (this.state & state) != 0;        
    }

    /**
     * @param object 
     */
    public void addItem(BaculaObject object) {
        this.listModel.add(object);
    }
    
    
    /**
     * @param object 
     */
    public void removeItem(BaculaObject object) {
        this.listModel.remove(object);
    }
     
}

