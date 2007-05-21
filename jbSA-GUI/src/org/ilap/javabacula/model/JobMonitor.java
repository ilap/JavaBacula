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

import org.ilap.javabacula.network.IStatusListener;
import org.ilap.javabacula.network.BaculaConnection;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
public class JobMonitor extends Monitor {
    
    public JobMonitor() {
        super(5);
    }

    public JobMonitor(int sleepTime) {
        super(sleepTime);
    }

    public JobMonitor(IStatusListener l) {
        super(l);
    }
    
    public void handleSession() {
        String status;
        final String command = new String("list job");
        final BaculaConnection conn = getConnection();
        
        status = conn.getCommandResult(command);
        // TODO: delete info LOGGER
        BaculaConstants.LOGGER.info("JobMonitor Thread is working: " + command);
        BaculaConstants.LOGGER.debug("JobMonitor Thread is working: " + command);
        sendStatus(status);         
        BaculaConstants.LOGGER.debug("JobMonitor Thread is working: AFTER SEND STATUS " + command);
    }
}
