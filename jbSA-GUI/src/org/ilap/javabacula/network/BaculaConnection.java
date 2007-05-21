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

/**
 * Session thread, does interaction between native bacula routines and JAVA
 *
 * @author root
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.reflect.*;

import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.util.StringList;
import org.ilap.javabacula.model.BaculaSystem;
import org.ilap.javabacula.model.BaculaObject;

public class BaculaConnection extends BaculaObject implements Runnable {

    // Class constants, variables (order: public, protected, package, private)
    //////////////////////////////////////////////////////////////////////////
    private static final String LIBRARY_NAME = "JavaBacula";

    // Instance variables (order: public, protected, package, private)
    //////////////////////////////////////////////////////////////////////////
    /** Peer Instance on JNI */
    private PipedInputStream    pipe;
    private BufferedReader      cmdReader;
    private PrintStream         cmdWriter;
    
    private volatile boolean    wantExit = false;
    private volatile long       peer;
    private Thread          baculaChannel  = null; 
    
    private BaculaSystem    system;
    
    private String          workingDir = ".";
    private int             directorType = 0;
    private int             debugLevel = 0;
    private String threadName;
    
    
    private Boolean dataIsReceived = false;
    private StringList stringList;
    private String stringResult;
 
    // Constructor class then instances
    static {
        try {
            // *** FIXME *** You must add -Djava.library.path=${java.library.path}:/path/to/jar to find shared library 
            System.loadLibrary(LIBRARY_NAME);
        } catch (Error e) {
            System.out.println("ERROR: on load Library \"" + LIBRARY_NAME + "\"!");
            e.printStackTrace();
            // *** FIXME *** 
            System.exit(-1);
        }
    }
       
    // JNI Methods
    /* 
     * Create the peer instance and retrieve the instance ID,
     * that is need for the thread communication
    */
    private native long     jniCreateChannel(int directorType, String workingDir, 
                                        int debugLevel);
    private native void     jniDestroyChannel(long peer);
   
    private native boolean  jniConnect(long peer, String host, int port);
    private native void     jniDisConnect(long peer);

    private native boolean  jniLogin(long peer, String login, String password);
    private native boolean  jniReConnect(long peer);
    
    private native void     jniSendCommand(long peer, String command);
    private native void     jniSendSignal(long peer, int signal);
    private native int      jniReceiveMessage(long peer);
    private native String   jniReplyOfCommand(long peer);

    private native int      jniGetReplyLength(long peer);
    private native String   jniSignalToString(long peer);
    private native boolean  jniIsBnetStopped(long peer);
   
    // Instance methods 
    ////////////////////////////////////////////////////////////////////////////

    // Constructor
    public BaculaConnection() throws IOException {
       try {
            pipe = new PipedInputStream();
            cmdWriter = new PrintStream(new PipedOutputStream(pipe), true);
            cmdReader = new BufferedReader(new InputStreamReader(pipe));
        } catch (IOException e) {
            ;
        }
    }
   
    public BaculaConnection(BaculaSystem system) throws IOException {        
        this();
        
        if (initializeChannel(system)) {
            if (!connect()) {
                throw new ConnectException();
            } else if (!login()) {
                throw new SocketException();
            }
        } else {
            throw new IOException(); 
        }
        startChannel();
    }
    
    public boolean initializeChannel(BaculaSystem system) throws IOException {
        if (this.peer != 0) return true; 

        this.system = system;
        
        try {
            this.peer = createChannel(this.directorType, this.workingDir, this.debugLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.peer != 0;
    }
    
    public void startChannel () {
        if (this.baculaChannel == null) {
            this.baculaChannel = new Thread(this);
            threadName = baculaChannel.toString();
        }
        this.baculaChannel.start();
    }
    
    protected void finalize () {
       // shutdown();
    }
    
    /**
     * This method do everything for the 
     */
    public void run() {
        wantExit = false;
        try{
            handleSession();
        }
        catch(IOException e) {
            e.printStackTrace();
        };
        BaculaConstants.LOGGER.debug("After handlesession" + threadName);

        shutdown();
        BaculaConstants.LOGGER.debug("After Shutdown"+ threadName);
    } // run()

    synchronized void shutdown() {
        destroyChannel();
        baculaChannel = null;
    }

    public long createChannel(int directorType, String workingDir, int debugLevel) {
        return jniCreateChannel(directorType, workingDir, debugLevel);   
    }

    public void destroyChannel() {
        if (peer != 0)
            jniDestroyChannel(peer);
        peer = 0;
    }
    
    public boolean  connect() {
        return connect(this.system.getDirAddress(), this.system.getDirPort());
    }
   
    public boolean  connect(String host, int port) {
        if (this.peer != 0)
            return jniConnect(this.peer, host, port);
        else
            return false;
    }
    
    public void disConnect() {
        if (this.peer != 0)
            jniDisConnect(this.peer);
    }

    public void closeConnection() {
        wantExit = true;
        this.setDataIsReceived(true);
        this.stringResult = null;
        this.cmdWriter.println(".quit");
        this.cmdWriter.flush();
//        this.cmdWriter.close();
//         try { this.cmdReader.close();} catch(IOException e){;}
//        disConnect();
//        shutdown();
    }

    public boolean  login() {
        return login(this.system.getLoginName(), this.system.getPassword());
    }


    public boolean login(String login, String password) {
        if (this.peer != 0)
           return jniLogin(this.peer, login, password);
        else
            return false;
    }
    
    
    void sendCommand(String command) {
        if (this.peer != 0)
            jniSendCommand(this.peer, command);
    }

    void sendNewCommand(String command) {
        if (this.peer != 0) 
            cmdWriter.println(command);
    }

    public synchronized StringList getCommandResultList(String command) {
        if (this.peer == 0)
            return null;
        setDataIsReceived(false);
        cmdWriter.println(command);
        //cwriter.flush();
        // blocking while results are not received;
        //waitForResult();
        while (!isDataIsReceived()) {
            try {Thread.sleep(300);} catch (InterruptedException e) {;};
        }
        return stringList;
    }
    
    public synchronized String getCommandResult(String command) {
        if (this.peer == 0)
            return null;
        setDataIsReceived(false);
        cmdWriter.println(command);
        cmdWriter.flush();
        // blocking while results are not received;
        //waitForResult();
        while (!isDataIsReceived()) {
            try {Thread.sleep(300);} catch (InterruptedException e) {;};
        }
        return stringResult;
    }
    
    void sendSignal(int signal) {
        if (this.peer != 0)
           jniSendSignal(this.peer, signal);
    }

    int receiveMessage() {
        if (this.peer != 0)
           return jniReceiveMessage(this.peer);
        else
            return BaculaConstants.BNET_HARDEOF;
    }
    
    String replyOfCommand() {
        if (this.peer != 0)
           return jniReplyOfCommand(this.peer);
        else
            return null;
    }

    int getReplyLength() {
        if (this.peer != 0)
           return jniGetReplyLength(this.peer);
        else
            return BaculaConstants.BNET_HARDEOF;
    }

    String signalToString() {
        if (this.peer != 0)
           return jniSignalToString(this.peer);
        else
            return null;
    }

    boolean isBnetStopped() {
        if (this.peer != 0)
           return jniIsBnetStopped(this.peer);
        else
            return true;
    }
    
    public void handleSession() throws IOException {
        int status;
        int signal;
        String tmpStr;
        boolean wantBreak;
        
        while (!isBnetStopped() && !wantExit) {
        
            // Read the command from pipe and send to the Director
            try { 
                BaculaConstants.LOGGER.info("beforeReadLine"+ threadName);
                String str = cmdReader.readLine();
                BaculaConstants.LOGGER.info("sendCommand(" + str + ")"+ threadName);
                sendCommand(str);
            } catch (IOException e) {
                ;
            }

//          stringList = new StringList();
            stringResult = new String();
            tmpStr = new String();
            while (true) {
		if ((status = receiveMessage()) >= 0) {
                    tmpStr = replyOfCommand();
                    if (tmpStr.equals("You have messages.\n")) {
                        tmpStr = "";
                    }
                    stringResult += tmpStr;
                } else if (status == BaculaConstants.BNET_SIGNAL) {
                    signal = getReplyLength();
                    wantBreak = true;
                    
                    switch (signal) {
                        case BaculaConstants.BNET_PROMPT:  
                            String res = "BNET_PROMPT received";
                        case BaculaConstants.BNET_EOD:
                            tmpStr = replyOfCommand();
                            if (tmpStr.equals("You have messages.\n")) {
                                tmpStr = "";
                            }
                            stringResult += tmpStr;

                            setDataIsReceived(true);
                            break;
                        case BaculaConstants.BNET_HEARTBEAT :
                            sendSignal(BaculaConstants.BNET_HB_RESPONSE);
                            BaculaConstants.LOGGER
                                    .info("Heartbeat signal received, answered.");
                            wantBreak = false;
                            break;
                        default:
                            BaculaConstants.LOGGER
                                    .debug("Unexpected signal received.");
                            BaculaConstants.LOGGER.debug("Type: \""+ signal + 
                                             "\", String: " + signalToString());
                            wantBreak = false;
                    }
                    
                    if (wantBreak) break;
		} else if (isBnetStopped()) {                    
                    BaculaConstants.LOGGER.debug("Bacula is stopped! Exiting..."  + status + threadName);
                    break;         
                } else {
                    BaculaConstants.LOGGER.debug("Error signal received! Exiting..."  + status + threadName);
                    break;
		}
            } // while (true)
	}// while (!isBnetStopped())

        disConnect();
        this.cmdReader.close();
        this.cmdWriter.close();
        stringResult = null;
        setDataIsReceived(true);
    }

    public  boolean isDataIsReceived() {
        return dataIsReceived;
    }

    public void setDataIsReceived(boolean dataIsReceived) {
        this.dataIsReceived = dataIsReceived;
    }
    
    
       /** FIXME */
    public void initializeObject() {
    }
    
    /** FIXME */
    public void releaseObject() {
    }

}
