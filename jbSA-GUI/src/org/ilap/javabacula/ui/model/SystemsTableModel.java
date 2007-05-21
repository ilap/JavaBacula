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

import org.ilap.javabacula.model.BaculaSystem;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
/** Creates a new instance of SystemsTableModel */
public class SystemsTableModel extends   AbstractTableAdapter {
        
        public SystemsTableModel(ListModel listModel) {
            super(listModel, BaculaConstants.SYS_COLS);
        }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
            BaculaSystem system = (BaculaSystem) getRow(rowIndex);            
            String lastLogin;
            
            try {
                lastLogin = system.getLastLogin().toString();
            } catch (Exception e) {
                lastLogin = null;
            }
            
            switch (columnIndex) {
                case 0 : return system; //.getName();       // Name
                case 1 : return system.getDirAddress(); // Address
                case 2 : return system.getDirPort();    // Port
                case 3 : return system.getLastLogin();  // Last login
                case 4 : return system.getLoginName();  // Logged as
                default :
                    throw new IllegalStateException("Unknown column");
            }
        }
        
        
}
