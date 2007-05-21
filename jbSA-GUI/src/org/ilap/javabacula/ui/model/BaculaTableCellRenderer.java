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

import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.ui.util.BaculaIcons;
import org.ilap.javabacula.ui.util.TreeFileIcons;
import org.ilap.javabacula.model.*;

/**
 * CellRenderer for the Director list and render a cell with director icon
 *
 */
public class BaculaTableCellRenderer extends DefaultTableCellRenderer {
    
        
    public BaculaTableCellRenderer() {
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
                
        if (object instanceof DirectorDaemon) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_DIRECTOR);            
        } else if (object instanceof BaculaSystem) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_SYSTEM);            
        } else if (object instanceof ClientDaemon) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_CLIENT);            
        } else if (object instanceof StorageDaemon) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_STORAGE);            
        } else if (object instanceof Catalog) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_CATALOG);            
        } else if (object instanceof Pool) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_POOL);            
        } else if (object instanceof FileSet) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_FILESET);            
        } else if (object instanceof Schedule) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_SCHEDULE);            
        } else if (object instanceof Job) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_JOB);            
        } else if (object instanceof Volume) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_VOLUME);            
        } else if (object instanceof StorageDevice) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_DEVICE);            
        } else if (object instanceof BaculaFile) {
            icon = TreeFileIcons.getTreeFileIcon((BaculaFile)object, false);            
        } else if (object instanceof ActivatedJob) {
            icon = getJobIcon((ActivatedJob)object);            
        }
        
        return icon;
    }

    private Icon getJobIcon(ActivatedJob activatedJob) {
        Icon icon;
        String tstr = activatedJob.getJobStatus();
        if (tstr.equals("R")) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_JOBR);                        
        } else if (tstr.equals("T")) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_JOBO);                        
        } else if (tstr.equals("E") || tstr.equals("e") || tstr.equals("f")) {
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_JOBE);     
        } else if (tstr.equals("A") || tstr.equals("B") || tstr.equals("c") ||
            tstr.equals("d") || tstr.equals("F") || tstr.equals("j") ||
            tstr.equals("M") || tstr.equals("m") || tstr.equals("p") ||
            tstr.equals("s") || tstr.equals("t")) {
            icon = BaculaIcons.getInstance().getIcon(BaculaConstants.ID_JOBW);
            
        } else { // use default
            icon = (Icon) BaculaIcons.getInstance().getIcon(BaculaConstants.ID_JOB);            
        }
        return icon;
    }
}
