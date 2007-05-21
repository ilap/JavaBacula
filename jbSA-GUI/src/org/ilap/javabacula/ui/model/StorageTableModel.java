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

import javax.swing.ListModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

import org.ilap.javabacula.model.StorageDaemon;

/**
 *
 * @author ilap
 */
public class StorageTableModel extends AbstractTableAdapter {
    
        private static final String [] COLS = 
                                    {"Storage Name", "Storage ID", 
                                     "Storage Host", "Port",
                                     "Device Name", "Media Type", "Max Jobs"};
    
    /** Creates a new instance of StorageTableModel */
    public StorageTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        StorageDaemon storage = (StorageDaemon) getRow(rowIndex);            

        switch (columnIndex) {
                case 0 : return storage;                // Name
                case 1 : return storage.getStorageId(); // Storage ID
                case 2 : return storage.getAddress();   // Address
                case 3 : return storage.getPort();      // Port
                case 4 : return storage.getDeviceName();// Device Name
                case 5 : return storage.getMediaType(); // MediaType
                case 6 : return storage.getMaxJobs();   // Max Jobs
                default :
                    throw new IllegalStateException("Unknown column");
        }
    }
}
