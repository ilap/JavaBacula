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

import javax.swing.tree.DefaultMutableTreeNode;

//import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JPopupMenu;

import org.ilap.javabacula.ui.views.*;

/**
 *
 * @author ilap
 */
public class BaculaTreeNode extends DefaultMutableTreeNode {
    // TODO: make popupmenu to tree (Mark, Unmark)
    protected JPopupMenu popupMenu;
    protected BaculaView view;
    
    protected Icon icon;
    protected String iconName;

    public BaculaTreeNode() {
        this(null);
    }

    public BaculaTreeNode(Object userObject) {
        this(userObject, true, null);
    }

    public BaculaTreeNode(Object userObject, String iconName) {
        this(userObject, true, null);
        this.iconName = iconName;
    }

    public BaculaTreeNode(Object userObject, boolean allowsChildren, Icon icon) {
        super(userObject, allowsChildren);
        this.icon = icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getIconName() {
        if (iconName != null) {
            return iconName;
        } else {
            String str = userObject.toString();
            int index = str.lastIndexOf(".");
            if (index != -1) {
                return str.substring(++index);
            } else {
                return null;
            }
        }
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    
    public String toString() {
       return userObject.toString();
    }
}

