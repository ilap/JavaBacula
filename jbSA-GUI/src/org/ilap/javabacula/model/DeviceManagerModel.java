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


import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.AbstractAction;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

import org.ilap.javabacula.util.*;
import org.ilap.javabacula.config.*;
import org.ilap.javabacula.ui.dialogs.*;
import org.ilap.javabacula.model.StorageDaemon;

/**
 *
 * @author ilap
 */
public class DeviceManagerModel extends BaculaObjectManagerModel {

    private Action mountDeviceAction;
    public DeviceManagerModel(StorageManager storageManager) {
        super(storageManager);
        initModel();
        initEventHandling();
    }
    
    
    private void initModel() {        
        this.mountDeviceAction = new MountDeviceAction();
        updateDeviceActionEnablement();
    }
    
    
    private void initEventHandling() {
        this.getObjectsSelection().addPropertyChangeListener(
              SelectionInList.PROPERTYNAME_SELECTION_INDEX,
                new ConnectionCheckHandler());
    }
    
    
    public Action getMountDeviceAction() {
        return this.mountDeviceAction;
    }
    
    

    private void doMount() {
        getObjectsSelection().setSelection(null);
    }
    
    
    private final class MountDeviceAction extends AbstractAction {
       
        private MountDeviceAction() {
            super("Mount\u2026");
        }
        
        public void actionPerformed(ActionEvent e) {
            doMount();
        }
    }

        
    
    private void updateDeviceActionEnablement() {
        boolean hasSelection = getObjectsSelection().hasSelection();
        boolean isAlive = false;
        
        if (hasSelection) {
            StorageDaemon sd = (StorageDaemon) getSelectedItem();
            if (sd != null) {
                isAlive = sd.isAlive();                            
            }
        }
    
        getMountDeviceAction().setEnabled(hasSelection && !isAlive);

    }    
    
    /**
     * Enables or disables this model's Actions when it is notified
     * about a change in the <em>selectionEmpty</em> property
     * of the SelectionInList.
     */
    private final class ConnectionCheckHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateDeviceActionEnablement();
        }
    }

    
    public void startModel() {
       // refresh("show storage");
    }

    public void stopModel() {
       // Do nothing
    }
    
    public void initializeDaemons() {
        startModel();
    }
       
    public int compare(Object one, Object two) {
        String str = ((BaculaObject)two).getName();
        return ((BaculaObject)one).getName().equals(str)?0:1;
    }
        
    protected ArrayListModel parseShowCommand(String result)  { return null;}

    protected void doNew() {}
    protected void doEdit() {}
    protected void doDelete() {}
    protected void doRefresh() {}

}
