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

import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;

/**
 * Converter class for the Sortable CoolTable (by Ilap)
 */
public class CoolTableSelectionConverter extends AbstractConverter {
 	final CoolTable	table;
 
 	public CoolTableSelectionConverter(final ValueModel selectionIndexHolder, final CoolTable table) {
 		super(selectionIndexHolder);
 		this.table = table;
 	}
 
 	public Object convertFromSubject(Object subjectValue) {
 		int viewIndex = -1;
 		int modelIndex = -1;
 
 		if (subjectValue != null) {
 			modelIndex = ((Integer)subjectValue).intValue();
 			if (modelIndex >= 0) {
 				viewIndex = table.convertRowIndexToView(modelIndex);
 			}
 		}
 		return new Integer(viewIndex);
 	}
 
 	public void setValue(Object newValue) {
 		int viewIndex = -1;
 		int modelIndex = -1;
 
 		if (newValue != null) {
 			viewIndex = ((Integer) newValue).intValue();
 			if (viewIndex >= 0) {
                            modelIndex = table.convertRowIndexToModel(viewIndex);
                        }
 		}
 		subject.setValue(new Integer(modelIndex));
        }
    }