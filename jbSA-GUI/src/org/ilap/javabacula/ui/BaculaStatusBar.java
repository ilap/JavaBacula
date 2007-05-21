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

package org.ilap.javabacula.ui;

import org.jdesktop.swingx.*;
import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
/**
 *
 * @author ilap
 */
    public class BaculaStatusBar implements PropertyChangeListener {
        private JXStatusBar statusBar;
        private JLabel baculaLabel      = new JLabel();
        private JLabel dummyLabel       = new JLabel();
        private JLabel availLabel       = new JLabel();
        private JLabel unavailLabel     = new JLabel();
        private JProgressBar progressBar;
        private JLabel connectedLabel   = new JLabel();
        
        public BaculaStatusBar(JXStatusBar statusBar) {
            if (statusBar == null) return;
            this.statusBar = statusBar;
            
            initializeStatusBar();
        }
        
        private void initializeStatusBar() {           
            baculaLabel.setFont(new java.awt.Font("Dialog", 0, 10));
            baculaLabel.setText("JavaBacula v0.2 prealpha");
            statusBar.add(baculaLabel,  new JXStatusBar.Constraint(.20));
            statusBar.addSeparator();

            dummyLabel.setFont(new java.awt.Font("Dialog", 0, 12));
            //dummyLabel.setText("");
            statusBar.add(dummyLabel,  new JXStatusBar.Constraint(.45));
            statusBar.addSeparator();
            
            progressBar = new JProgressBar();
            progressBar.setIndeterminate(false);
            statusBar.add(progressBar,  new JXStatusBar.Constraint(.20));
            statusBar.addSeparator();

            availLabel.setFont(new java.awt.Font("Dialog", 0, 10));
            setAvailableChannel(0);
            statusBar.add(availLabel,  new JXStatusBar.Constraint(.20));
            statusBar.addSeparator();

            unavailLabel.setFont(new java.awt.Font("Dialog", 0, 10));
            setUnavailableChannel(0);
            statusBar.add(unavailLabel,  new JXStatusBar.Constraint(.20));
            statusBar.addSeparator();

            connectedLabel.setFont(new java.awt.Font("Dialog", 0, 10));
            setConnected(false);
            connectedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            //connectedLabel.setIcon();
            statusBar.add(connectedLabel,  new JXStatusBar.Constraint(.5));            
            statusBar.addSeparator();
        }
        
        
        public void setIndeterminate(boolean indeterminate) {
            if (progressBar != null) {
                progressBar.setIndeterminate(indeterminate);                
            }
        }
        
        public void setAvailableChannel(int channel) {
            availLabel.setText("Available Channels:" + channel);            
        }
        public void setUnavailableChannel(int channel) {
            unavailLabel.setText("Used Channels:" + channel);                        
        }

        public void setConnected(boolean connected) {
            if (connected) {
                connectedLabel.setText("CONNECTED");
            } else {
                connectedLabel.setText("DISCONNECTED");
            }
        }
        
        public void propertyChange(PropertyChangeEvent pce) {
            String property = pce.getPropertyName();
            
            if ("connected".equals(property)) {
                setConnected((Boolean) pce.getNewValue());
            } else if ("availConnections".equals(property)) {
                setAvailableChannel((Integer) pce.getNewValue());
            } else if ("unavailConnections".equals(property)) {
                setUnavailableChannel((Integer) pce.getNewValue());            
            }          
        }
        
    }
