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

import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.ListModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

import org.ilap.javabacula.model.ActivatedJob;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
public class ActivatedJobTableModel extends AbstractTableAdapter {
    //JobId | Name | StartTime| Type | Level | JobFiles | JobBytes | JobStatus |
    private static final String [] COLS = 
                           {"Job Name", "ID", "Start Time", "Type", "Level", 
                            "JobFiles", "Job Bytes", "Job Status"};

     private SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date startTime;

    public ActivatedJobTableModel(ListModel listModel) {
        super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        ActivatedJob activatedJob = (ActivatedJob) getRow(rowIndex);            

        switch (columnIndex) {
            case 0 : return activatedJob;                
            case 1 : return activatedJob.getJobID();                
            case 2 : startTime = activatedJob.getStartTime();
                    if (startTime != null) {
                        return simpleDateFormat.format(startTime);                                        
                    } else {
                        return null;
                    }
            case 3 : return BaculaConstants.GETJOBTYPE(activatedJob.getJobType());                
            case 4 : return BaculaConstants.GETBACKUPLEVEL(activatedJob.getBackupLevel());                
            case 5 : return activatedJob.getJobFiles();                
            case 6 : return activatedJob.getJobBytes();                
            case 7 : return BaculaConstants.GETJOBSTATUS(activatedJob.getJobStatus());                
            default :
                throw new IllegalStateException("Unknown column");
                                    
        }
    }
}
