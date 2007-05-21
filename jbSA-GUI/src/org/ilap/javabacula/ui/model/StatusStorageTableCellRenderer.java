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

import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;

import org.ilap.javabacula.model.*;
import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.ui.util.BaculaIcons;


/**
 * CellRenderer for StorageStatus
 * @author ilap
 */
public class StatusStorageTableCellRenderer extends DefaultTableCellRenderer {
    
        
    public StatusStorageTableCellRenderer() {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                       boolean hasFocus, int row, int column) {
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            
        Icon icon = getIcon(value);
        if (icon != null) {
            setIcon(icon);
        }
    
        return this;
    }
    
    private Icon getIcon(Object object) {
        Icon icon = null;
                
        if (object instanceof StorageDaemon) {
            StorageDaemon sd = (StorageDaemon)object;
            if (sd.isAutoChanger()) {
                icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_TAPELIB);                            
            } else {
                icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_SINGLEDEV);                                            
            }
        } else if (object instanceof StorageDevice) {
            StorageDevice sd = (StorageDevice) object;
            if (sd.getParentJukeBox() != null) {
                icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_TAPELIB);                            
            } else {
                icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_SINGLEDEV);                                                            
            }        
        }        
        return icon;
    }
}

