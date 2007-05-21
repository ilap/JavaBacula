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
public abstract class CoolDialog extends javax.swing.JDialog {
    
    protected KeyEventHandler keyEventHandler;
         
    
    /** Creates a new instance of CoolDialog */
    public CoolDialog(Frame parent, boolean modal) {
        super(parent, modal);
	initializeHandlers();
    }
    
    public CoolDialog(Frame parent, String title, boolean modal) {
	super(parent,title,modal);
        initializeHandlers();
    }
	
    public CoolDialog(Dialog parent, String title, boolean modal) {
        super(parent,title,modal);
	initializeHandlers();
    }
    
    public void initializeHandlers() {
        keyEventHandler = new KeyEventHandler();
	addKeyListener(keyEventHandler);
	addWindowListener(new WindowHandler());

        ((Container)getLayeredPane()).addContainerListener(
                new CoolContainerHandler(keyEventHandler));
	getContentPane().addContainerListener(
                new CoolContainerHandler(keyEventHandler));	
        
    }
        
    class KeyEventHandler extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            
            if(evt.isConsumed()) {
		return;                
            }

            switch (evt.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    onEnterKeyEvent(evt);
                    break;         
                case KeyEvent.VK_ESCAPE:
                    onCancelKeyEvent(evt);
                    break;
            }
            
	}
    }

    class WindowHandler extends WindowAdapter {
        public void windowClosing(WindowEvent evt) {
            onCancelKeyEvent(null);
	}
    }        
    
    protected abstract void onCancelKeyEvent(KeyEvent evt);
    protected abstract void onEnterKeyEvent(KeyEvent evt);
}
