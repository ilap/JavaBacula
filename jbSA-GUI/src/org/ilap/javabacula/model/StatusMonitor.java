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

package org.ilap.javabacula.model;


import java.util.*;
import javax.swing.*;
import java.io.*;
import org.ilap.javabacula.model.ClientDaemon;
import org.ilap.javabacula.model.CommonDaemon;
import org.ilap.javabacula.model.StorageDaemon;
import org.ilap.javabacula.network.BaculaConnection;
import org.ilap.javabacula.util.BaculaConstants;


/**
 *
 * @author ilap
 */
public class StatusMonitor extends Monitor {
    
    private StatusQueueManager queueManager;
//    private IStatusListener statusListener;
    
    /** Creates a new instance of StatusMonitor 
     * @param queueManager holds all of the available Connections
     * @param refreshTime Refresh Time a.k.a sleepTime
     */
    public StatusMonitor(StatusQueueManager queueManager, int refreshTime) {
        super(refreshTime);
        this.queueManager = queueManager;
    }
    
    public void handleSession() {
        String command, status;
        final BaculaConnection conn = this.getConnection();
        CommonDaemon daemon;
        
        daemon = (CommonDaemon) queueManager.reserveDaemon();
            
        if (daemon == null) {
            BaculaConstants.LOGGER.debug("Thread is waiting for a daemon");
            try { Thread.sleep(1000);} catch (InterruptedException ie) {;}  
            return;
        }

        this.addIStatusListener(daemon);
  
        command = "status director";
        if (daemon instanceof ClientDaemon) {
            command = "status client=" + daemon.getName();
        } else if (daemon instanceof StorageDaemon) {
            command = "status storage=" + daemon.getName();
        }
            
        status = conn.getCommandResult(command);
        BaculaConstants.LOGGER.debug("StatusMonitor in Thread works: " + command);
        this.sendStatus(status);  
        this.removeIStatusListener(daemon);
        queueManager.releaseDaemon(daemon);    
    }
}
