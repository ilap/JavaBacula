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

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import javax.swing.ListModel;

import org.ilap.javabacula.model.Volume;

/**
 *
 * @author ilap
 */
public class VolumeTableModel extends AbstractTableAdapter {
    /*
     * MediaId | VolumeName | VolStatus | Enabled | VolBytes | VolFiles 
     * | VolRetention | Recycle | Slot | InChanger | MediaType | LastWritten
     */
    private static final String [] COLS = 
                                    {"Volume Name", "Media ID", "Status", "Pool", 
                                     "Enabled", "VolBytes", "VolFiles", 
                                     "VolRetention", "Recycle", 
                                     "Slot", "In Changer", "Media Type",
                                     "Last Written" };
    
    public VolumeTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Volume volume = (Volume) getRow(rowIndex);            

        switch (columnIndex) {
            case 0 : return volume;                
            case 1 : return volume.getMediaId();
            case 2 : return volume.getVolStatus();
            case 3 : return volume.getPool();
            case 4 : return volume.isEnabled();
            case 5 : return volume.getVolBytes();
            case 6 : return volume.getVolFiles();
            case 7 : return volume.getVolRetention();
            case 8 : return volume.isRecycle();
            case 9 : return volume.getSlot();
            case 10 : return volume.isInChanger();
            case 11 : return volume.getMediaType();
            case 12 : return volume.getLastWritten();
            default :
                throw new IllegalStateException("Unknown column");
        }
    }
}
