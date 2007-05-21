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

package org.ilap.javabacula.ui.util;

import java.text.DateFormat;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;

import com.jgoodies.binding.formatter.EmptyDateFormatter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.binding.adapter.Bindings;

import org.jdesktop.swingx.*;

/**
 *
 * @author ilap
 */
public class BindComponents {
    
    /** Singleton */
    protected BindComponents() {
    }
    
    public static void bindCheckBoxModel(JCheckBox cb, ValueModel valueModel) {
        Bindings.bind(cb, valueModel);
    }
    
    public static void bindComboBox(JComboBox cb, SelectionInList selectionInList) {
        Bindings.bind(cb, selectionInList);
    }
    
    public static void bindAndSetDateField(JFormattedTextField ftf,
        ValueModel valueModel) {
        
        DateFormat shortFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        shortFormat.setLenient(false);
        
        JFormattedTextField.AbstractFormatter defaultFormatter = 
            new EmptyDateFormatter(shortFormat);
        JFormattedTextField.AbstractFormatter displayFormatter = 
            new EmptyDateFormatter();
        DefaultFormatterFactory formatterFactory = 
            new DefaultFormatterFactory(defaultFormatter, displayFormatter);
        
        
        ftf.setFormatterFactory(formatterFactory);
        Bindings.bind(ftf, valueModel);

    }    
}
