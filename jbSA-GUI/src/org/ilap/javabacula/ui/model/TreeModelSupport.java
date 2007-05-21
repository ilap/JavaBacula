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

package org.ilap.javabacula.ui.model;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import java.util.Vector;

/**
 *
 * @author ilap
 */
public class TreeModelSupport {
    private Vector listeners = new Vector();

    public void addTreeModelListener(TreeModelListener listener) {
        if (listener != null && !this.listeners.contains(listener) ) {
            this.listeners.addElement(listener);
        }
    }

    public void removeTreeModelListener(TreeModelListener listener) {
        if (listener != null) {
            this.listeners.removeElement(listener);
        }
    }

    public void fireTreeNodesChanged(TreeModelEvent evt) {
        TreeModelListener listener;
        for (Object o: this.listeners) {
            listener = (TreeModelListener) o;
            listener.treeNodesChanged(evt);
        }
    }

    public void fireTreeNodesInserted(TreeModelEvent evt) {
        TreeModelListener listener;
        for (Object o: this.listeners) {
            listener = (TreeModelListener) o;
            listener.treeNodesInserted(evt);
        }
    }

    public void fireTreeNodesRemoved(TreeModelEvent evt) {
        TreeModelListener listener;
        for (Object o: this.listeners) {
            listener = (TreeModelListener) o;
            listener.treeNodesRemoved(evt);
        }
    }

    public void fireTreeStructureChanged(TreeModelEvent evt) {
        TreeModelListener listener;
        for (Object o: this.listeners) {
            listener = (TreeModelListener) o;
            listener.treeStructureChanged(evt);
        }
    }
}
