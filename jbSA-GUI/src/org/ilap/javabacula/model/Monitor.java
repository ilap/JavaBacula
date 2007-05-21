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
import org.ilap.javabacula.network.ConnectionManager;

/**
 *
 * @author ilap
 */
abstract public class Monitor implements Runnable {

    private volatile boolean done;
    private BaculaConnection connection;
    private Thread monitor;
    private String threadName;


    private volatile int sleepTime = 1;

    List listenerList = new Vector();
    
    public Monitor() {
        done = false;
    }
    
    public Monitor(int sleepTime) {
        super();
        this.sleepTime = sleepTime;
    }
 
    public Monitor(IStatusListener l) {
        super();
        addIStatusListener(l);
    }
    
    public BaculaConnection getConnection() {
        return connection;
    }

    private synchronized boolean isDone() {
        return done;
    }

    private synchronized void setDone(boolean done) {
        this.done = done;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }           
    
    public synchronized void addIStatusListener(IStatusListener l) {
	listenerList.add(l);
    }

    public synchronized void removeIStatusListener(IStatusListener l) {
	listenerList.remove(l);
    } 
    
    protected synchronized void removeAllIStatusListener() {
	listenerList.clear();
    } 

    protected synchronized  void sendStatus(String status) {
		
        if (status == null) {
            return;
        }
        
        ListIterator iterator = listenerList.listIterator( );
	while (iterator.hasNext( )) {
            IStatusListener dl = (IStatusListener) iterator.next();
            dl.statusReceived(status);
	}
    } 

   public void startMonitor() {
        if (monitor == null) {
            monitor =  new Thread(this);
            threadName = monitor.toString();
        }
        monitor.start();
    }
   
    public void stopMonitor() {
        if (monitor == null) {
            return;
        }
        
        requestStop();
        monitor = null;
    }

    public void run() {
        connection = ConnectionManager.getInstance().reserveConnection();
        done = done && (connection != null);

        while (!isDone()) {
            handleSession();
            if (isDone()) {
               // break;
            }
            try { Thread.sleep(sleepTime*1000);} catch (InterruptedException ie) {;}             
        }
               
        if (connection != null) {
            ConnectionManager.getInstance().releaseConnection(connection);            
            connection = null;
        }
        this.removeAllIStatusListener();
    }

    
    public synchronized boolean isAlive() {
        return monitor != null;
    }        

    public synchronized void requestStop() {
        setDone(true);
    }        
 
    abstract void handleSession();
}
