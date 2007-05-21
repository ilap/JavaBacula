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
import org.ilap.javabacula.util.BaculaConstants;

/**
 * Ez egy fake queue menedzser, hiszen csak ket listat tart nyilvan:
 * 1. a feldolgozottak (amelyeket ismet fel lehet dolgozni: processedDaemonList) es a 
 * 2. a feldolgozas alatt levok listajat (processingDaemonList).
 * 
 * @author ilap
 */
public class StatusQueueManager {
    
    private int maxThread = 1;
    private static StatusQueueManager instance  = null;
    
    ArrayList statusThreads = new ArrayList(); 
    

    ArrayList processedDaemonsList = new ArrayList(); 
    ArrayList processingDaemonsList = new ArrayList(); 
    
    private boolean initialized;

    /** Creates a new instance of StatusQueueManager */
    private StatusQueueManager() {
        initialized = false;
    }
    
    public static StatusQueueManager getInstance() {
        if (instance == null) {
            instance = new StatusQueueManager(); 
            instance.initializeQueue();
        }
                
        return instance;
    }
    
    public synchronized void initializeQueue() {
        if (initialized) {
            return;
        }
        
        synchronized (statusThreads) {
            for (int i=0; i<maxThread; i++) {
                // TODO: get refreshTime from Configuration
                StatusMonitor sp = new StatusMonitor(StatusQueueManager.this, 1); 
                Thread t = new Thread(sp); 
                if (t != null) {
                    this.add(statusThreads, sp);
                    t.start();                     
                }
            }        
        }
        BaculaConstants.LOGGER.debug("StatusQueueManager: initialized");
        initialized = true;
    }

    public synchronized void uninitializeQueue() {
        if (!initialized) {
            return;
        }
        
        synchronized (statusThreads) {
            Collections.reverse(statusThreads);
            for (Object o: statusThreads ) {
                Monitor mon = (Monitor)o;
                mon.requestStop();
            }
            statusThreads.clear();
        }
        initialized = false;
        BaculaConstants.LOGGER.debug("StatusQueueManager: uninitialized");
    }
    
    private synchronized boolean add(ArrayList list, Object o) {
        return list.add(o);
    }

    private synchronized  Object remove(ArrayList list) {
        if (list.size()>0) {
            return list.remove(0);
        } else {
            return null;
        }
    }

    private synchronized boolean remove(ArrayList list, Object o) {
        if ((o!=null) && (list.size() > 0)) {
            return list.remove(o);            
        }
        return false;
    }
    
    public void registerDaemon(Object daemon) {        
        if (daemon == null) {
            return;
        }

        synchronized (this.processedDaemonsList) {
                add(this.processedDaemonsList, daemon);                    
        }
    }

    public void unRegisterDaemon(Object daemon) {
        synchronized (this.processedDaemonsList) {
            synchronized (this.processingDaemonsList) {
                remove(this.processedDaemonsList, daemon);
                remove(this.processingDaemonsList, daemon);
            }
         }
    }

    
    public  synchronized Object reserveDaemon() {
        synchronized (this.processedDaemonsList) {
            synchronized (this.processingDaemonsList) {
                Object o; 
                // Remove first entry
                o = remove(this.processedDaemonsList);
                String str = " null";
                if (o != null) {
                    add(this.processingDaemonsList, o);
                    str = o.toString();                    
                }
                return o;
            }
         }
    }
    
    public synchronized void releaseDaemon(Object daemon) {
        synchronized (this.processingDaemonsList) {
            synchronized (this.processedDaemonsList) {
                                
                if (remove(this.processingDaemonsList, daemon)) {
                    add(this.processedDaemonsList, daemon);                    
                }            
            }            
            
        }        
    }
        
}

