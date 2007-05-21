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

import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

import org.ilap.javabacula.util.*;
import org.ilap.javabacula.model.*;
import org.ilap.javabacula.network.*;

import org.ilap.javabacula.model.StorageDevice;

/**
 *
 * @author ilap
 */
public class DeviceTableModel extends AbstractTableModel {
    protected StorageDaemon daemon;
    protected Vector<StorageDevice>  children;
    protected int       rowCount;

    /** Creates a new instance of BaculadaemonTableModel */
    public DeviceTableModel() {
    }

    public DeviceTableModel(StorageDaemon daemon) {
        this();
        setValues(daemon);
    }

    protected void setValues(StorageDaemon daemon) {
        this.daemon = daemon;
        
        if (this.daemon == null) {
            children = null;
        } else {
            children = daemon.getDevices();
        }
                        
        if (this.children != null) {
            this.rowCount = children.size();            
        } else {
            this.rowCount = 0;
        }

    }

    public void setDevices(StorageDaemon daemon) {
        setValues(daemon);
        fireTableDataChanged();
    }

    public int getRowCount() {
        return this.children != null ? this.rowCount : 0;
    }

    public int getColumnCount() {
        return this.children != null ? 6 :0;
    }

    /**
     * ----------,0,root,root,0,1970-01-01 01:00:00,+,/opt/Shares/    
     */
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Device Name";
            case 1: return "Parent JukeBox";
            case 2: return "Device Path";
            case 3: return "Volume";
            case 4: return "Pool";
            case 5: return "Message";
            default:
                return "unknown";
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex){
        if (this.daemon == null || this.children == null) {
            return null;
        }

        StorageDevice device = children.get(rowIndex);        

        switch (columnIndex) {
            case 0 : return device;                     // Status
            case 1 : return device.getParentJukeBox();  // Parent JB
            case 2 : return device.getDevicePath();  // Path
            case 3 : return device.getVolume();      // Volume
            case 4 : return device.getPool();        // Pool
            case 5 : return device.getMessage();     // Message 
            default :
                throw new IllegalStateException("Unknown column");
        }
    }
    
    public Class getColumnClass(int column) {
        if (column == 0) {
            return getValueAt(0, 0).getClass();
        } else {
            return super.getColumnClass(column);
        }
    }
    
}