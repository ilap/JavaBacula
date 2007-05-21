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

package org.ilap.javabacula.auth;

import java.io.IOException;
import java.util.EventObject;
import java.io.*;
import java.util.*;

/**
 * This is an event object that is passed to connection listener methods
 * 
 * 
 * @author DOROGI Pal
 */
public class LogonProcessEvent extends EventObject {
    private IOException reason;
    
    public LogonProcessEvent(Object eventSource) {
        this(eventSource, null);
    }
    
    /** Creates a new instance of LogonProcessEvent */
    public LogonProcessEvent(Object eventSource, IOException reason) {
        super(eventSource);
        this.reason = reason;
    }
    
    public IOException getReason() {
        return this.reason;
    }

   public void setReason(IOException reason) {
      this.reason = reason;     
   }
}
