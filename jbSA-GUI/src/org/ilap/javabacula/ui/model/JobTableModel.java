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

import org.ilap.javabacula.model.Job;

/**
 *
 * @author ilap
 */
public class JobTableModel extends AbstractTableAdapter {
    
    private static final String [] COLS = 
                                    {"Name", "Job Type", "Priority", 
                                     "Enabled", "Max Jobs", 
                                     "Reschedule", "Times", "Interval", "Spool", 
                                     "Write Part After Job"};
    
    public JobTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Job job = (Job) getRow(rowIndex);            

        switch (columnIndex) {
            case 0 : return job;                
            case 1 : return job.getJobType();
            case 2 : return job.getPriority();
            case 3 : return job.isEnabled();
            case 4 : return job.getMaxJobs();
            case 5 : return job.isReschedule();
            case 6 : return job.getTimes();
            case 7 : return job.getInterval();
            case 8 : return job.isSpool();
            case 9 : return job.isWritePartAfterJob();
            default :
                throw new IllegalStateException("Unknown column");
        }
    }
}