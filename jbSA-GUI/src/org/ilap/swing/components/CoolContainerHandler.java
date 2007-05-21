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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author ilap
 */
public class CoolContainerHandler extends ContainerAdapter {
    
    private KeyAdapter keyAdapter;
    
    /**
     * Creates a new instance of CoolContainerHandler
     */
    public CoolContainerHandler(KeyAdapter keyAdapter) {
        this.keyAdapter = keyAdapter;
    }
    
    public void componentAdded(ContainerEvent evt) {
	componentAdded(evt.getChild());
    }

    public void componentRemoved(ContainerEvent evt) {
	componentRemoved(evt.getChild());
    }
    
    private void componentAdded(Component comp)	{
        comp.addKeyListener(keyAdapter);
	
        if(comp instanceof Container) {
            Container cont = (Container)comp;
            cont.addContainerListener(this);
            Component[] comps = cont.getComponents();
            
            for(int i = 0; i < comps.length; i++) {
                componentAdded(comps[i]);
            }
	}
    }

    private void componentRemoved(Component comp) {
	comp.removeKeyListener(keyAdapter);
        
	if(comp instanceof Container) {
            Container cont = (Container)comp;
            cont.removeContainerListener(this);
            Component[] comps = cont.getComponents();
        
            for(int i = 0; i < comps.length; i++) {
                componentRemoved(comps[i]);
            }
	}
    }
}
