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

import org.ilap.javabacula.model.Pool;

/**
 *
 * @author ilap
 */
public class PoolTableModel extends AbstractTableAdapter {
    
    private static final String [] COLS = 
                                    {"Name", "Pool Type", "Use Catalog", 
                                     "Use Once", "Cat Files", "Max Vols", 
                                     "Auto Prune", "Vol Retention", 
                                     "Vol Use", "Recycle", "Label Format",
                                     "Cleaning Prefix", "Label Type", 
                                     "Recycle Oldest", "Purge Oldest", 
                                     "Max Vol Jobs", "Max Vol Files", 
                                     "Mig Time", "Mig HiBytes", "Mig LoBytes" };
    
    public PoolTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Pool pool = (Pool) getRow(rowIndex);            

        switch (columnIndex) {
            case 0 : return pool;                
            case 1 : return pool.getPoolType();
            case 2 : return pool.isUseCat();
            case 3 : return pool.isUseOnce();
            case 4 : return pool.getCatFiles();
            case 5 : return pool.getMaxVols();
            case 6 : return pool.isAutoPrune();
            case 7 : return pool.getVolRetention();
            case 8 : return pool.getVolUse();
            case 9 : return pool.isRecycle();
            case 10 : return pool.getLabelFormat();
            case 11 : return pool.getCleaningPrefix();
            case 12 : return pool.getLabelType();
            case 13 : return pool.isRecycleOldest();
            case 14 : return pool.isPurgeOldest();
            case 15 : return pool.getMaxVolJobs();
            case 16 : return pool.getMaxVolFiles();
            case 17 : return pool.getMigTime();
            case 18 : return pool.getMigHiBytes();
            case 19 : return pool.getMigLoBytes();
            default :
                throw new IllegalStateException("Unknown column");
        }
    }
}
