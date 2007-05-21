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

import org.ilap.javabacula.ui.model.BaculaTreeNode;
import org.ilap.javabacula.util.BaculaConstants;
import java.awt.Component;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.ilap.javabacula.model.*;
import org.ilap.javabacula.util.*;

public class SystemsTreeRenderer extends DefaultTreeCellRenderer {

    Hashtable icons = null;
    public SystemsTreeRenderer() {
        super();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
        boolean sel, boolean expanded, boolean leaf, int row,
        boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
            row, hasFocus);

        // Load icons to the Hash table
        if (icons == null) {
            icons = (Hashtable) tree.getClientProperty("JTree.icons");
        }       
        
        if (value instanceof BaculaTreeNode) {
            Icon icon = getIcon((BaculaTreeNode)value);

            if(icon != null) {
                setIcon(icon);
                ((BaculaTreeNode)value).setIcon(icon);
            }            
        }
	
        return this;
    }
     
    private Icon getIconByObject(Object object) {
        
        Icon icon = null;
                
        if (object instanceof BaculaSystem) {
            icon = (Icon) icons.get(BaculaConstants.ID_SYSTEM);            
        } else if (object instanceof DirectorDaemon) {
            icon = (Icon) icons.get(BaculaConstants.ID_DIRECTOR);            
        } else if (object instanceof ClientDaemon) {
            icon = (Icon) icons.get(BaculaConstants.ID_CLIENT);            
        } else if (object instanceof StorageDaemon) {
            icon = (Icon) icons.get(BaculaConstants.ID_STORAGE);            
        }
        
        return icon;
    }
    


    private Icon getIcon(BaculaTreeNode treeNode) {
        // if exist get icon from BaculaTreeNode
        Icon icon = treeNode.getIcon();
        
        if (icon != null) {
            return icon;
        }
        
        
        Object userObject = ((DefaultMutableTreeNode) treeNode).getUserObject();        
        
        if (userObject == null) {
            return null;
        }
        
        if (userObject instanceof BaculaObject) {
            icon = getIconByObject(userObject);        
        } else {
            icon = (Icon) icons.get(userObject.toString());
        }

        if (icon == null) {
            icon = (Icon) icons.get(BaculaConstants.ID_UNKNOWN);
        }
        
        treeNode.setIcon(icon);
        
        return icon;
    }
}
