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

package org.ilap.javabacula.ui.views;

import org.ilap.javabacula.model.BaculaObjectManagerModel;

/**
 *
 * @author ilap
 */
public abstract class BaculaView extends javax.swing.JPanel {
    BaculaObjectManagerModel model;
    boolean wantButtons = false;

    /** Creates a new instance of BaculaView */
    public BaculaView() {
    }

    public BaculaView(boolean wantButtons) {
        this.wantButtons = wantButtons;
    }

    /** Creates a new instance of BaculaView 
     * @param model 
     */
    public BaculaView(BaculaObjectManagerModel model) {
        this.model = model;
    }
    
    public synchronized boolean getWantButtons() {
        return this.wantButtons;
    }

    public synchronized void setWantButtons(boolean wantButtons) {
        this.wantButtons = wantButtons;
    }

    
    public synchronized BaculaObjectManagerModel getModel() {
        return model;
    }

    public synchronized void setModel(BaculaObjectManagerModel model) {
        this.model = model;
    }
    
    public abstract void initializeView();
    public abstract void uninitializeView();
    public abstract void doOnEnter();
    public abstract void doOnLeave();
}
