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

import org.ilap.javabacula.model.CommonDaemon;

/**
 * Ez NEM a show storages|clients kimenete alapjan kesziti el az oszlop mezoket,
 * hanem a status parancs kimenete alapjan (lasd. CommonDaemons.java)
 * @author ilap
 */
public class BaculaDaemonTableModel extends AbstractTableAdapter {
    private static final String [] DAEMON_COLS = 
                                      { "Name", "Address", "Port", 
                                        "Version", "Build Date", "Host OS",
                                        "Dist. Name", "Dist. Version", 
                                        "Started", "Jobs Since"};
    
    /** Creates a new instance of BaculaObjectTableModel */
    public BaculaDaemonTableModel(ListModel listModel) {
            super(listModel, DAEMON_COLS);            
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        CommonDaemon daemon = (CommonDaemon) getRow(rowIndex);            

        switch (columnIndex) {
                case 0 : return daemon;       // Name
                case 1 : return daemon.getAddress();    // Address
                case 2 : return daemon.getPort();       // Port
                case 3 : return daemon.getVersion();    //
                case 4 : return daemon.getBuildDate();  //
                case 5 : return daemon.getHostOS();     //
                case 6 : return daemon.getDistName();   //
                case 7 : return daemon.getDistVersion();//
                case 8 : return daemon.getStarted();    //
                case 9 : return daemon.getJobsSince();  //
                default :
                    throw new IllegalStateException("Unknown column");
            }
        }
    
}
