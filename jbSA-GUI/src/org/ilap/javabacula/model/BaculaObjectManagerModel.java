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

import com.jgoodies.binding.list.SelectionInList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import javax.swing.AbstractAction;

import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.model.BaculaObject;
import org.ilap.javabacula.network.*;

import javax.swing.ListModel;
import com.jgoodies.binding.list.ArrayListModel;
import java.util.ListIterator;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringTokenizer;

/**
 *
 * @author ilap
 */
abstract public class BaculaObjectManagerModel implements java.util.Comparator {
    
    /**
     * Holds the List of Bacula's objects and provides operations
     * to create, add, remove and change a Object's properties.
     */
    private final BaculaObjectManager baculaManager;
    
    
    /**
     * Holds the list of managed Bacula objects plus a single selection.
     */
    private SelectionInList objectsSelection;
    
    private Action newObjectAction;
    private Action editObjectAction;
    private Action deleteObjectAction;
    private Action refreshObjectAction;
    
    /** Creates a new instance of BaculaObjectManagerModel
     * @param baculaManager
     */
    public BaculaObjectManagerModel(BaculaObjectManager baculaManager) {
        this.baculaManager = baculaManager;
        this.setObjectsSelection(new SelectionInList(this.baculaManager.getManagedObjects()));
        
        this.newObjectAction = new NewObjectAction();
        this.editObjectAction = new EditObjectAction();
        this.deleteObjectAction = new DeleteObjectAction();
        this.refreshObjectAction = new RefreshObjectAction();
        
        this.getObjectsSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
        
        
        updateActionEnablement();
    }
    
    public SelectionInList getObjectsSelection() {
        return objectsSelection;
    }
    
    public void setObjectsSelection(SelectionInList objectsSelection) {
        this.objectsSelection = objectsSelection;
    }
    
    public BaculaObjectManager getBaculaManager() {
        return baculaManager;
    }
    
    protected void addItem(BaculaObject item) {
        getBaculaManager().addItem(item);
    }
    
    
    
    protected void updateActionEnablement() {
        boolean hasSelection = getObjectsSelection().hasSelection();
        
        
        getNewObjectAction().setEnabled(
                baculaManager.isState(BaculaConstants.CREATEABLE));
        getEditObjectAction().setEnabled(hasSelection  &&
                baculaManager.isState(BaculaConstants.EDITABLE));
        getDeleteObjectAction().setEnabled(hasSelection &&
                baculaManager.isState(BaculaConstants.DELETEABLE));
        getRefreshObjectAction().setEnabled(
                baculaManager.isState(BaculaConstants.REFRESHABLE));
    }
    
    /**
     * Enables or disables this model's Actions when it is notified
     * about a change in the <em>selectionEmpty</em> property
     * of the SelectionInList.
     */
    private final class SelectionEmptyHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
    
    public MouseListener getDoubleClickHandler() {
        return new DoubleClickHandler();
    }
    
    /**
     * Returns a MouseListener that selects and edits a Bacula object on double-click.
     *
     * @return a MouseListener that selects and edits a Bacula object on double-click.
     */
    private final class DoubleClickHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                doEdit();
            }
        }
    }
    
    /**
     * Returns the Action that creates a new BAcula object and adds it
     * to this model's List of managed Objects.
     * Opens is implemented in the inherited class as a doEdit method.
     *
     * @return the Action that creates and adds a new Bacula object
     */
    public Action getNewObjectAction() {
        return this.newObjectAction;
    }
    
    /**
     * Returns the Action that opens an Editor dialog of the selected Album.
     *
     * @return the Action that opens an Editor dialog of the selected Album
     */
    public Action getEditObjectAction() {
        return this.editObjectAction;
    }
    
    /**
     * Returns the Action that deletes the selected Bacula object from
     * this model's List of managed objects.
     *
     * @return The Action that deletes the selected Bacula object
     */
    public Action getDeleteObjectAction() {
        return this.deleteObjectAction;
    }
    
    public Action getRefreshObjectAction() {
        return this.refreshObjectAction;
    }
    
    private final class NewObjectAction extends AbstractAction {
        
        private NewObjectAction() {
            super("New\u2026");
        }
        
        public void actionPerformed(ActionEvent e) {
            doNew();
        }
    }
    
    private final class EditObjectAction extends AbstractAction {
        
        private EditObjectAction() {
            super("Edit\u2026");
        }
        
        public void actionPerformed(ActionEvent e) {
            doEdit();
        }
    }
    
    private final class DeleteObjectAction extends AbstractAction {
        
        private DeleteObjectAction() {
            super("Delete");
        }
        
        public void actionPerformed(ActionEvent e) {
            doDelete();
        }
    }
    
    private final class RefreshObjectAction extends AbstractAction {
        
        private RefreshObjectAction() {
            super("Refresh");
        }
        
        public void actionPerformed(ActionEvent e) {
            doRefresh();
        }
    }
    
    protected BaculaObject getSelectedItem() {
        return (BaculaObject) getObjectsSelection().getSelection();
    }
    
    abstract public int compare(Object one, Object two);
    
    protected synchronized void consistencyCheck(ArrayListModel newDaemonsList) {
                
        BaculaObject oldObj, newObj;
        ListIterator newLit, oldLit;
        boolean deleteIt = false;
        
        ListModel oldDaemonsList = this.getBaculaManager().getManagedObjects();
        oldLit = ((ArrayListModel) oldDaemonsList).listIterator();
        
        /**
         * Scan the old daemons for remove
         */
        while (oldLit.hasNext()) {
            deleteIt = true;
            oldObj = (BaculaObject) oldLit.next();
            
            newLit = newDaemonsList.listIterator();
            while (newLit.hasNext()) {
                newObj = (BaculaObject) newLit.next();
                
                // delete equal object from new list
                if (compare(oldObj, newObj) == 0) {
                    deleteIt = false;
                    newLit.remove();
                }
            }
            
            /**
             * *** FIXME *** Doesn't work properly
             * Delete a daemon from the old list and remove from the Listener
             *
             */
            if (deleteIt) {
                oldObj.releaseObject();
                oldLit.remove();
            }
        }
        
        /**
         * Add the new daemons to the ListModel and initialize it
         */
        newLit = ((ArrayListModel) newDaemonsList).listIterator();
        while (newLit.hasNext()) {
            newObj = (BaculaObject) newLit.next();
            newObj.initializeObject();
            oldLit.add(newObj);
        }
    }
    
    
    protected String[] parseLine(String line, String delimiter) {
        String field;
        
        StringTokenizer st = new StringTokenizer(line, delimiter);
        int fieldsCount = (st.countTokens()-1)*2;
        
        // Parse fields
        Pattern pattern =  Pattern.compile("([\\S]+)"+delimiter);
        Matcher fields  = pattern.matcher(line);
        
        // Parse values
        Scanner values = new Scanner(line).useDelimiter("\\s*[^ ]+"+delimiter+"\\s*");
        
        //values.
        //fields.
        String[] tokens = new String[fieldsCount];
        int count = 0;
        while (fields.find()) {
            // Even
            field = fields.group().replace(delimiter, "");
            tokens[count] = field;
            // Odd
            if (values.hasNext()) {
                tokens[count+1] = values.next();
            }
            count += 2;
        }
        
        return tokens;
    }
    
    protected String[] parseTableLine(String line, String delimiter) {
        String field;
        
        StringTokenizer st = new StringTokenizer(line, delimiter);
        int fieldsCount = st.countTokens();
        int idx = 0;
        
        String[] tokens = new String[fieldsCount];
        while (st.hasMoreElements()) {
            tokens[idx] = st.nextToken();
            idx++;
        }
        return tokens;
    }
    
    /**
     * Refresh the ListModel with the current objects.
     * If necessery delete or add an object from the current listmodel.
     * @param command
     */
    protected synchronized void reloadByCommand(String command) {
        String result;
        BaculaConnection conn =
                ConnectionManager.getInstance().reserveConnection();
        
        /** *** FIXME *** */
        if (conn != null) {
            
            // 1. get command result from director
            result = conn.getCommandResult(command);
            
            // 2. build the storage objects from list
            ConnectionManager.getInstance().releaseConnection(conn);
            reloadByData(result);
        }
    }
    
    protected synchronized void reloadByData(String dataStream) {
        consistencyCheck(parseShowCommand(dataStream));
    }
    
    abstract ArrayListModel parseShowCommand(String cmdResult);
    
    abstract void doEdit();
    abstract void doDelete();
    abstract void doNew();
    abstract void doRefresh();
    //
    public abstract void startModel();
    public abstract void stopModel();
}
