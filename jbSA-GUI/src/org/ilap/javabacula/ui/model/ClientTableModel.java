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

import org.ilap.javabacula.model.ClientDaemon;

/**
 *
 * @author ilap
 */
public class ClientTableModel  extends AbstractTableAdapter {
    
        private static final String [] COLS = 
                                    {"Client Name", "Client Address", "Port", 
                                     "Max Jobs", "File Retention",   
                                     "Job Retention", "AutoPrune", "Catalog"};
    
    /** Creates a new instance of ClientTableModel */
    public ClientTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        ClientDaemon client = (ClientDaemon) getRow(rowIndex);            

        switch (columnIndex) {
                case 0 : return client;                 //  Name
                case 1 : return client.getAddress();    // Address
                case 2 : return client.getPort();       //
                case 3 : return client.getMaxJobs();    //
                case 4 : return client.getFileRetention();  //
                case 5 : return client.getJobRetention();  //
                case 6 : return client.isAutoPrune();  //
                case 7 : return client.getCatalog();  //
                
                default :
                    throw new IllegalStateException("Unknown column");
        }
    }
    
}
