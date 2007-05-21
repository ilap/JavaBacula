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

import org.ilap.javabacula.model.FileSet;
/**
 *
 * @author ilap
 */
public class FileSetTableModel extends AbstractTableAdapter {
    
    private static final String [] COLS = {"Name" };
    
    public FileSetTableModel(ListModel listModel) {
            super(listModel, COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        FileSet fileSet = (FileSet) getRow(rowIndex);            

        switch (columnIndex) {
            case 0 : return fileSet;                
            default :
                throw new IllegalStateException("Unknown column");
        }
    }
}
