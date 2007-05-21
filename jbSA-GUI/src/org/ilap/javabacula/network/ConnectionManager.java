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

package org.ilap.javabacula.network;



import java.io.IOException;
import java.net.*;
import java.util.Iterator;

import com.jgoodies.binding.list.ArrayListModel;
import org.ilap.javabacula.network.BaculaConnection;
import org.ilap.javabacula.model.BaculaSystem;
import org.ilap.javabacula.model.DirectorDaemon;
import org.ilap.javabacula.model.CommonDaemon;
import org.ilap.javabacula.model.StatusQueueManager;
import org.ilap.javabacula.auth.*;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
public class ConnectionManager extends LogonProcessService {
    
    private final ArrayListModel availableConnections;
    private final ArrayListModel unavailableConnections;
    
    public static final String PROPNAME_AVAILCONNECTIONS   = "availConnections";
    public static final String PROPNAME_UNAVAILCONNECTIONS = "unavailConnections";
    public static final String PROPNAME_MAXCONNECTIONS = "maxConnections";
    public static final String PROPNAME_CONNECTED      = "connected";
    private int maxConnections  = 7;
    // Who's connected???
    private boolean connected   = false;
    private int availConnections = 0;
    private int unavailConnections = 0;
    
    private static ConnectionManager instance;
    private static BaculaSystem system;
    private static DirectorDaemon director;
    
    private boolean initialized;
    
    
    private ConnectionManager() {
        setConnected(false);
        initialized = false;
        system  = null;
        director = null;
        //this.setMaxConnections(8);
        this.availableConnections   = new ArrayListModel();
        this.unavailableConnections = new ArrayListModel();
    }
    
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    
    public synchronized void releaseConnections() {
        // TODO: check this method
        
        StatusQueueManager.getInstance().uninitializeQueue();        
        releaseDirector();
        synchronized (availableConnections) {
            synchronized (unavailableConnections) {
                BaculaConnection conn;
                
                Iterator it = availableConnections.iterator();
                while (it.hasNext()) {
                    conn = (BaculaConnection) it.next();
                    conn.closeConnection();
                }
                availableConnections.clear();
                this.setAvailConnections(0);
                
                it = unavailableConnections.iterator();
                while (it.hasNext()) {
                    conn = (BaculaConnection) it.next();
                    conn.closeConnection();
                }
                unavailableConnections.clear();
                this.setUnavailConnections(0);
            }
        }
        setConnected(false);
    }
    
    
    public void initializeConnections(BaculaSystem system) {        
        this.system   = system;
        this.initialized = true;
    }
    
    public void uninitializeConnections() {        
        this.initialized = false;
        releaseConnections();
    }

    public BaculaConnection createItem() throws IOException {
        if (!initialized) {
            return null;
        }
        
        return new BaculaConnection(system);
    }
    
    public boolean createConnections() throws IOException {
        boolean result = true;
        
        synchronized (availableConnections) {
            synchronized (unavailableConnections) {
                int conns = this.getMaxConnections();
                BaculaConnection bc;
                
                for (int i=1; i< conns;i++) {
                    bc = new BaculaConnection(system);
                    
                    if (bc != null) {
                        this.availableConnections.add(bc);
                        BaculaConstants.LOGGER.debug("One connection created: " + i);
                    } else {
                        //*** FIXME *** create cleverer uninitialization method for clear list.
                        result = false;
                    }
                }
            }
        }
        if (!result) {
            releaseConnections();
        }
        return result;
    }
    
    public synchronized void addItem(BaculaConnection conn) {
        availableConnections.add(conn);
    }
    
    
   // public synchronized void removeItem(BaculaConnection conn) {
        /** ***FIXME ***/
        // Check if object is in the list
   //     unavailableConnections.remove(conn);
   //     availableConnections.remove(conn);
   // }
    
    public synchronized BaculaConnection reserveConnection() {
        BaculaConnection conn = null;
        
        if (!availableConnections.isEmpty()) {
            conn = (BaculaConnection) availableConnections.remove(0);
            unavailableConnections.add(conn);
            setAvailConnections(availableConnections.getSize());
            setUnavailConnections(unavailableConnections.getSize());
        }
        return conn;
    }
    
    public synchronized void releaseConnection(BaculaConnection conn) {
        unavailableConnections.remove(conn);
        availableConnections.add(conn);
        setAvailConnections(availableConnections.getSize());
        setUnavailConnections(unavailableConnections.getSize());
    }
    
    public synchronized void releaseDirector(){
        if (director != null) {
            director.releaseObject();
            director = null;
        }

    }
    
    public synchronized boolean authenticate() throws IOException {
        boolean result = true;
        if (!initialized) {
            return false;
        }

        BaculaConnection conn = createItem();
        
        // TODO: check if Director doesn't have another opened console 
        if (conn != null)  {
            addItem(conn);
        } else {
            result = false;
        }
        
        
        /* *** FIXME ***/
        if (result) {
            result &= createConnections();
        }
        if (result) {
            StatusQueueManager.getInstance().initializeQueue();
        }
        if (result) {
            director = new DirectorDaemon();
            director.initializeObject();
        }
        
        this.setConnected(result);
        return result;
    }
    
    public int getMaxConnections() {
        return this.maxConnections;
    }
    
    public void setMaxConnections(int maxConnections) {
        Object oldValue = getMaxConnections();
        this.maxConnections = maxConnections;
        
        firePropertyChange(PROPNAME_MAXCONNECTIONS, oldValue, this.maxConnections);
    }
    
    public boolean isConnected() {
        // *** FIXME *** Why we use redundand connected property
        return connected;
    }
    
    public void setConnected(boolean connected) {
        if (this.system != null) {
            boolean oConnected = isConnected();
            this.connected = connected;
            // *** FIXME ***
            system.setConnected(this.connected);
            firePropertyChange(PROPNAME_CONNECTED, oConnected, this.connected);
        }
    }

    public int getAvailConnections() {
        return availConnections;
    }

    public void setAvailConnections(int availConnections) {
        int oAvailConnections = getAvailConnections();
        this.availConnections = availConnections;
        firePropertyChange(PROPNAME_AVAILCONNECTIONS, oAvailConnections, this.availConnections);
    }

    public int getUnavailConnections() {
        return unavailConnections;
    }

    public void setUnavailConnections(int unavailConnections) {
        int oUnavailConnections = getUnavailConnections();
        this.unavailConnections = unavailConnections;
        firePropertyChange(PROPNAME_UNAVAILCONNECTIONS, oUnavailConnections, this.unavailConnections);
    }       
    
    public CommonDaemon getDirector() {        
        return director;
    }
}
