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

package org.ilap.swing.components;

import java.util.Hashtable;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class CoolTableCellRenderer extends DefaultTableCellRenderer { 
      
    private DefaultTableCellRenderer _defaultRenderer;
    
    public CoolTableCellRenderer() {
    }

    public CoolTableCellRenderer(TableCellRenderer renderer) {
        _defaultRenderer = (DefaultTableCellRenderer)renderer;
    }


    public Icon getIcon(Object aObject) {
        System.out.println(aObject.getClass().toString() + " XX: " + (String)aObject);
        return null;
    }

    public String getText(Object aObject) {
        return aObject.toString();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                        boolean isSelected, 
                                                        boolean hasFocus, 
                                                        int row, int column) {

        if (value == null) return this;
        if (_defaultRenderer == null) return this;
        
        Component cell = _defaultRenderer.getTableCellRendererComponent(table, 
                                        value,isSelected, hasFocus, row, column);
        Hashtable icons = (Hashtable) table.getClientProperty("JTablle.icons");
        
        if (icons == null) return cell; 

        if (column == 0) {
            _defaultRenderer.setIcon((Icon) icons.get("director"));
        } else {
            _defaultRenderer.setIcon(null);
        }
        
        System.out.println("FromCellRenderer:" + value.getClass().toString());
           
        return cell;            
    }
}
