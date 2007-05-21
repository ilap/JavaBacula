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
import java.net.*;
import java.util.*;
import java.awt.EventQueue;
import javax.swing.SwingUtilities;

import com.jgoodies.binding.beans.Model;


/**
 * The <b>LogonProcessService</bthis.reason> is the abstract base class for all classes
 * implementing
 * LogonProcessService az alaposztaly
 * itt hozom letre a konnekciokat, akkor van disconnected status, ha
 * a connection-ok elfogynak
 * 
 * KIVULROL JOHETO INPUT-ok, ha a LogonProcessService-t, mint objektumot nezem:
 * startLoginProcedure(): itt elindit egy login procedurat. mig itt
 * cacnelLoginProcedure() (stop connection, stop logonProcess) leallit
 * 
 * 
 * A listenerek (LogonProcessListener) akik figyelik a LogonProcessService-t
 * a kov. fv.-ket kell implementalniuk (LogonProcessListener interfesz):
 * connectionFailed(LogonProcessEvent source);
 * connectionStarted(LogonProcessEvent source);
 * connectionCanceled(LogonProcessEvent source);
 * connectionSucceeded(LogonProcessEvent source);
 * 
 * 
 * @author Pal DOROGI
 */
public abstract class LogonProcessService extends Model {
     
     private boolean    canceled;
     private Thread     loginThread;
     
     private Vector<LogonProcessListener> listenerList = new Vector<LogonProcessListener>();
    
     
     public LogonProcessService() {
     }
          
    /**
     * This method is intended to be implemented by clients
     * wishing to authenticate a user with a given parameters.
     * Clients should implement the logonProcess in a 
     * manner that the logonProcess can be cancelled at
     * any time.
     *
     * @param name username
     * @param password password
     * @param server server (optional)
     * 
     * @return <code>true</code> on logonProcess success
     * @throws IOException
     */
    //public abstract boolean connect() throws IOException;
    public abstract boolean authenticate() throws IOException;
    
    /**
     * Notifies the LogonProcessService that an already running
     * logonProcess request should be cancelled. This
     * method is intended to be used by clients who want
     * to provide user with control over cancelling a long
     * running logonProcess request.
     */
    public void cancelLogonProcess() {
    	this.canceled = true;
    	EventQueue.invokeLater(new Runnable() {
            public void run() {
                fireLogonProcessCanceled(new LogonProcessEvent(this)); 
            } 
         });
    }
  
    /**
     * This method is intended to be overridden by subclasses
     * to customize the threading to use pooling etc. The default
     * implementation just creates a new Thread with the given runnable.
     *
     * @param runnable runnable
     * @return a new login thread
     */
    public Thread getConnectionThread(Runnable runnable) {
      return new Thread(runnable);  
    }
    
    /**
     * This method starts the logonProcess process and is either
     * synchronous or asynchronous based on the synchronous property
     * 
     * @param user user
     * @param password password
     * @param server server
     * @throws IOException
     */
//    public void startLoginProcess(final String user, final char[] password, final String server) throws IOException {
    public void startLoginProcess() throws IOException {
       this.canceled = false;
       // Letrehozzuk a szalat, majd lejjebb el is inditjuk...
         Runnable runnable = new Runnable() {
           public void run() {
             try {
                 final boolean result = authenticate();
                 if (!canceled) {
                     EventQueue.invokeLater(new Runnable() {
                        public void run() {
                           if (result) {
                              fireLogonProcessSucceeded(new LogonProcessEvent(LogonProcessService.this));  
                           }
                           else {
                              fireLogonProcessFailed(new LogonProcessEvent(LogonProcessService.this)); 
                           }
                        } 
                     });
                 }
                 

             } catch(final ConnectException failed) {
                 SwingUtilities.invokeLater(new Runnable() {
                     public void run() {
                         fireLogonProcessFailed(new LogonProcessEvent(LogonProcessService.this, failed));
                     }
                 });
             } catch(final IOException failed) {
                 SwingUtilities.invokeLater(new Runnable() {
                     public void run() {
                         fireLogonProcessFailed(new LogonProcessEvent(LogonProcessService.this, failed));
                     }
                 });
             }
           }  
         };  
         this.loginThread = getConnectionThread(runnable) ; 
         this.loginThread.start();
         fireLogonProcessStarted(new LogonProcessEvent(this));
    }
    
     
    /**
     * Adds a <strong>LogonProcessListener</strong> to the list of listeners
     * 
     * 
     * 
     * @param listener listener
     */
    public void addLogonProcessListener(LogonProcessListener listener) {
        listenerList.add(listener);
    }
    
    /**
     * Removes a <strong>LogonProcessListener</strong> from the list of listeners
     * 
     * 
     * 
     * @param listener listener
     */
    public void removeLogonProcessListener(LogonProcessListener listener) {
        listenerList.remove(listener);
    }
    
    void fireLogonProcessStarted(final LogonProcessEvent source) {
        Iterator iter = listenerList.iterator();
        while (iter.hasNext()) {
            LogonProcessListener listener = (LogonProcessListener) iter.next();
            listener.logonProcessStarted(source);
        }
    }
    
    void fireLogonProcessSucceeded(final LogonProcessEvent source) {
        Iterator iter = listenerList.iterator();
        while (iter.hasNext()) {
            LogonProcessListener listener = (LogonProcessListener) iter.next();
            listener.logonProcessSucceeded(source);
        }
    }
    
    void fireLogonProcessFailed(final LogonProcessEvent source) {
        Iterator iter = listenerList.iterator();
        while (iter.hasNext()) {
            LogonProcessListener listener = (LogonProcessListener) iter.next();
            listener.logonProcessFailed(source);
        }
    }
    
    void fireLogonProcessCanceled(final LogonProcessEvent source) {
        Iterator iter = listenerList.iterator();
        while (iter.hasNext()) {
            LogonProcessListener listener = (LogonProcessListener) iter.next();
            listener.logonProcessCanceled(source);
        }
    }
 
 
   public Thread getLoginThread() {
      return this.loginThread;
   }

   public void setLoginThread(Thread thread) {
      this.loginThread = thread;
   }

   public boolean isCanceled() {
      return this.canceled;
   }
}