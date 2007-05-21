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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ListModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

import org.ilap.javabacula.model.StorageDaemon;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
public class StorageStatusTableModel extends AbstractTableAdapter {
    private SimpleDateFormat simpleDateFormat = 
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private final String ISJB = new String(BaculaConstants.ID_TAPELIBS);
    private final String ISSD = new String(BaculaConstants.ID_SINGLEDEVS);
    private static final String [] COLS = 
                                    {"Storage Name", 
                                     "Device Name",
                                     "Address", 
                                     "Version",
                                     "Started", 
                                     "Jobs Since", 
                                     "Host OS (Dist Name)"};
    
    /** Creates a new instance of StorageStatusTableModel 
     * @param listModel 
     */
    public StorageStatusTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        StorageDaemon storage = (StorageDaemon) getRow(rowIndex);            

        switch (columnIndex) {
                case  0 : return storage;                // Name
                case  1 : return storage.getDeviceName();
                case  2 : return storage.getAddress();
                case  3 : return storage.getVersion();
                case  4 :
                    Date date = storage.getStarted();
                    if (date != null) {
                        return simpleDateFormat.format(date);                    
                    } else {
                        return date;
                    }
                case  5 : return storage.getJobsSince();
                case  6 : return storage.getHostOS() + " ("+ storage.getDistName() + ")";
                default :
                    throw new IllegalStateException("Unknown column");
        }
    }
 }