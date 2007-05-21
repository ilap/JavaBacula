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

import java.io.Serializable;
import java.awt.event.*;
import java.awt.Window;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.list.ArrayListModel;

import org.ilap.javabacula.model.BaculaSystem;
import org.ilap.javabacula.network.ConnectionManager;
import org.ilap.javabacula.config.Configuration;
import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.ui.dialogs.*;

/**
 *
 * @author ilap
 */
public final class SystemManagerModel extends BaculaObjectManagerModel {
    
    private Action connectDirectorAction;
    private Action disconnectDirectorAction;
    
    private boolean loaded = false;

    public SystemManagerModel(SystemManager systemManager) {
        super(systemManager);

        /**
         * Initializes the SelectionInList and Action.
         */        
        this.connectDirectorAction = new ConnectDirectorAction();
        this.disconnectDirectorAction = new DisconnectDirectorAction();
        updateConnectionActionEnablement();        

        /**
         * Initializes the event handling.
         */
        this.getObjectsSelection().addPropertyChangeListener(
              SelectionInList.PROPERTYNAME_SELECTION_INDEX,
                new ConnectionCheckHandler());        
    }
        
    public Action getConnectDirectorAction() {
        return this.connectDirectorAction;
    }

    public Action getDisconnectDirectorAction() {
        return this.disconnectDirectorAction;
    }

    /** Implements the abstract New Action method */
    protected void doNew() {
        Object system = createAndAddItem();
        this.getObjectsSelection().setSelection(system);
    }
      
    /** Implements the abstract Edit Action method */
    protected void doEdit() {
        editSelectedItem();
    }

    /** Implement the Delete Action method */
    void doDelete() {
        String key;
        
        key = BaculaConstants.ID_CONF_DIR + "." + getObjectsSelection().getSelectionIndex();
        getBaculaManager().removeItem(getSelectedItem());
        Configuration.removeKey(key);                
    }
    
    public void doRefresh() {
        loadData();
    }


    private void doConnect() {
        connectToSelectedItem();
    }

    private void doDisconnect() {
        disconnectFromSelectedItem();
    }
    
    
    private boolean openSystemEditor(BaculaSystem system, String title) {
        SystemEditorDialog dialog = new SystemEditorDialog(null, system, title);
        dialog.setVisible(true);
        return dialog.hasBeenCanceled();
    }
    
    private BaculaSystem createAndAddItem() {
        BaculaSystem system = (BaculaSystem) SystemManager.getInstance().createBaculaItem();
        boolean canceled = openSystemEditor(system, "Create new System");
        
        if (!canceled) {
            addItem(system);
            return system;
        }
        return null;
    }
    
    private void editSelectedItem() {
        boolean canceled = openSystemEditor((BaculaSystem) getSelectedItem(),
                "Modify System properties");
        if (!canceled) {
            getObjectsSelection().fireSelectedContentsChanged();
        }
    }

    private static final class DisposeOnClose extends ComponentAdapter implements Serializable {
            public void componentHidden(ComponentEvent e) {
                Window w = (Window) e.getComponent();
                w.dispose();
            }
    }
    private boolean openLoginDialog(BaculaSystem system) {
        SystemLoginDialog dialog = new SystemLoginDialog(null, system);
        dialog.addComponentListener(new DisposeOnClose());
        dialog.setVisible(true);
        return dialog.hasBeenCanceled();
    }
    
    private void connectToSelectedItem() {
        boolean canceled = openLoginDialog((BaculaSystem) getSelectedItem());
        if (!canceled) {
            getObjectsSelection().fireSelectedContentsChanged();
        }
    }

    private void disconnectFromSelectedItem() {        
        BaculaSystem dir = (BaculaSystem) getObjectsSelection().getSelection();
        String dirname;
        
        if (dir != null) {
            dirname = dir.getName();
        } else {
            dirname = "unknown";
        }
        String message = "Do you really want to disconnect from\n \"" 
                            + dirname + "\"?";  
        
        int result = JOptionPane.showConfirmDialog(null,
                                        message,
                                        "ConnectionService",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            ConnectionManager.getInstance().releaseConnections();
            getObjectsSelection().fireSelectedContentsChanged();
        }
    }
    

    // Actions
    private final class ConnectDirectorAction extends AbstractAction {
        
        private ConnectDirectorAction() {
            super("Connect\u2026");
        }
        
        public void actionPerformed(ActionEvent e) {
            doConnect();
        }
    }

    private final class DisconnectDirectorAction extends AbstractAction {
        
        private DisconnectDirectorAction() {
            super("Disconnect\u2026");
        }
        
        public void actionPerformed(ActionEvent e) {
            doDisconnect();
        }
    }
    
    private void updateConnectionActionEnablement() {
        boolean hasSelection = getObjectsSelection().hasSelection();
        boolean isConnected = false;
        BaculaSystem bs;
        
        if (hasSelection) {
            bs = (BaculaSystem) getSelectedItem();
            if (bs != null) {
                isConnected = bs.isConnected();                        
            }
        }
    
        getConnectDirectorAction().setEnabled(hasSelection && !isConnected);
        getDisconnectDirectorAction().setEnabled(hasSelection && isConnected);
    }    
    
    private final class ConnectionCheckHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateConnectionActionEnablement();
        }
    }

    /**
     * Load stored BaculaSystemManager's datas from the configuration file and
     * create an object for each entry in the config file.
     * FIXME need isLoaded variable and/or reload method 
     */
    public void loadData() {
        Integer i = 0;
        String value, key;
        
        key = BaculaConstants.ID_CONF_DIR + "." + i.toString();
        value = Configuration.getString(key);
        
        while (value.length() != 0) {
            this.addItem(new BaculaSystem(value));
            /*
             * Remove the "javabacula.director.x" keys from the properties.
             * Because we can not garanty that the values it be consistent with
             * the BaculaSystemManager's object datas.
             *
             * Save method is: On the end of the app we store each BaculaSystem
             * to the configfile with the SystemManager's 
             * saveToConfigFile() method.
             */
            Configuration.removeKey(key);
            i++;
            key = BaculaConstants.ID_CONF_DIR + "." + i.toString();
            value = Configuration.getString(key);
        }
    }
    
    /**  FIXME */
    public ArrayListModel parseShowCommand(String cmd) {
        return null;
    }

    public void startModel() {
        if (!loaded) {
            loadData();
        }
    }
    
    public void stopModel() {
        // Nothing
    }

            
    public int compare(Object one, Object two) {
        String str = ((BaculaObject)two).getName();
        return ((BaculaObject)one).getName().equals(str)?0:1;
    }


}
