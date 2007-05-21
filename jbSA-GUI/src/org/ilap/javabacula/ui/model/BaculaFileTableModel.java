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

import org.ilap.javabacula.ui.model.BaculaFile;
import java.io.*;
import java.util.Vector;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.*;

import org.ilap.javabacula.util.*;
import org.ilap.javabacula.model.*;
import org.ilap.javabacula.network.*;

/**
 *
 * @author ilap
 */
public class BaculaFileTableModel extends AbstractTableModel {
   private SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    protected BaculaFile directory;
    protected Vector<BaculaFile>  children;
    protected int       rowCount;

    /** Creates a new instance of BaculaDirectoryTableModel */
    public BaculaFileTableModel() {
    }

    public BaculaFileTableModel(BaculaFile directory) {
        this();
        setValues(directory);
    }

    protected void setValues(BaculaFile directory) {
        this.directory = directory;
        
        if (this.directory == null) {
            children = null;
        } else {
            children = directory.getChildren();
        }
                        
        if (this.children != null) {
            this.rowCount = children.size();            
        } else {
            this.rowCount = 0;
        }

    }

    public void setDirectory(BaculaFile directory) {
        setValues(directory);
        fireTableDataChanged();
    }

    public int getRowCount() {
        return this.children != null ? this.rowCount : 0;
    }

    public int getColumnCount() {
        return this.children != null ? 3 :0;
    }

    /**
     * ----------,0,root,root,0,1970-01-01 01:00:00,+,/opt/Shares/    
     */
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Size";
            case 2:
                return "Mod. Time";
            default:
                return "unknown";
        }
    }

    public Object getValueAt(int row, int column){
        if (this.directory == null || this.children == null) {
            return null;
        }

        BaculaFile baculaFile = children.get(row);

        switch (column) {
        case 0:
            return baculaFile;
        case 1:
            if ( baculaFile.isDirectory() ) {
                return "<DIR>";
            } else {
                return new Long(baculaFile.getSize());
            }
        case 2:
            return simpleDateFormat.format(baculaFile.getMtime());
        default:
            return "";
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
