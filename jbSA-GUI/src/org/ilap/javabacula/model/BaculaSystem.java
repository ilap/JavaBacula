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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

import org.ilap.javabacula.util.BaculaConstants;

/**
 * Egy Director manager, amely vezerli a director-t es a jovoben a hozzakapcsolodo
 * egyeb klienseket (SD, FD).
 * 
 * @author ilap
 */
public class BaculaSystem  extends BaculaObject {// extends CommonDaemon {
    
    public static final String PROPNAME_DIRADDRESS     = "dirAddress";
    public static final String PROPNAME_DIRPORT        = "dirPort";

    public static final String PROPNAME_LOGINNAME   = "loginName";
    public static final String PROPNAME_PASSWORD    = "password";
    public static final String PROPNAME_LASTLOGIN   = "lastLogin";
    
    public static final String PROPNAME_CONNECTED   = "connected";
        
  //  private String  name = "Unknown";
    private String  dirAddress; /** Address or IP*/
    private Integer dirPort;
    
    private String  loginName   = "";
    private String  password    = "";
    private String  lastLogin   = "";

    // Hashed password for the connection.
    private String  hashPassword    = "";
    private boolean connected = false;


    /**
     */
    public BaculaSystem() {
        super(BaculaConstants.UNKNOWN_TYPE);
        setName("System");
        setDirPort(BaculaConstants.DIRECTOR_PORT);
    }
    
    /**
     * Create a director daemon based on the stored Bacula Systems values
     */
    public BaculaSystem(String stream) {
        super(BaculaConstants.UNKNOWN_TYPE);
       // Limit is 5. for ex. stream is "foo:bar:::
       // /wo limit is  { "foo", "bar" } so ArrayIndexOutOfBound.
       // /w limit 5 is { "foo", "bar", "", "", "" }
       String[] values = stream.split(":", 5);

       setConnected(false);
       setName(values[0]);
       setDirAddress(values[1]);
       setDirPort(new Integer(values[2]));
       setLoginName(values[3]);
       setLastLogin(values[4]);
    }

    /**
     * Returns the daemon's address. 
     * 
     * @return the daemon's address.
     */
    public String getDirAddress() {
        return dirAddress;
    }
    
    /** 
     * Sets this daemon's hostname or IP address. 
     * 
     * @param address   The daemon's hostname or IP address.
     */
    public void setDirAddress(String dirAddress) {
        String oDirAddress = getDirAddress();
        this.dirAddress = dirAddress;
        firePropertyChange(PROPNAME_DIRADDRESS, oDirAddress, this.dirAddress);
    }

    /**
     * Returns the daemon's port number. 
     * 
     * @return the daemon's port number.
     */
    public Integer getDirPort() {
        return dirPort;
    }

     /** 
     * Sets this daemon's port number. 
     * 
     * @param port   The daemon's port number.
     */
    public void setDirPort(Integer dirPort) {
        Integer oDirPort = getDirPort();
        this.dirPort = dirPort;
        firePropertyChange(PROPNAME_DIRPORT, oDirPort, this.dirPort);
    }

    
    /**
     * Returns Director's login name.
     * 
     * @return this Director's login name.
     */
    public String getLoginName() { 
        return this.loginName;
    }
   
    /**
     * Returns Director's last login time.
     * 
     * @return this Director's last login time.
     */
    public String getLastLogin() { 
        return this.lastLogin; 
    }

    /**
     * Returns Director's connection password.
     * 
     * @return this Director's connection password.
     */
    public String getPassword() { 
        return this.password; 
    }

    /**
     * Returns Director's hashed connection password.
     * 
     * @return this Director's hashed connection password.
     */
    public String getHashPassword() { 
        return this.hashPassword; 
    }
    
    /**
     * Sets this director's login name and notifies observers 
     * if the login name changed.
     * 
     * @param loginName   The director's login name to set.
     */
    public void setLoginName(String loginName) {
        Object oldValue = getLoginName();
        this.loginName = loginName;
        
        firePropertyChange(PROPNAME_LOGINNAME, oldValue, this.loginName);
    }

    /**
     * Sets the last login date of of this director and notifies observers 
     * if the last login time changed.
     * 
     * @param lastLogin   The last login time of to set.
     */
    public void setLastLogin(String lastLogin) {
        String oLastLogin = getLastLogin();
        this.lastLogin = lastLogin;

        firePropertyChange(PROPNAME_LASTLOGIN, oLastLogin, this.lastLogin);
    }
    
    /**
     * Sets the director's connection password and notifies observers 
     * if the password changed.
     * 
     * @param password   The actual password for the director's connection to set.
     */
    public void setPassword(String password) {
        String oPassword = getPassword();
        this.password = password;

        
        try {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update(this.password.getBytes());
            
            BigInteger hash = new BigInteger( 1, md.digest() );
            this.hashPassword = hash.toString(16);
        } catch (NoSuchAlgorithmException ns) {
            ns.printStackTrace();
        }

        firePropertyChange(PROPNAME_PASSWORD, oPassword, this.password);
    }
    
    
    /**
     * Answers whether this Bacula system is connected to a director or not.
     * 
     * @return true if this Bacula system is connected, false if not
     */
    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Sets this Systems's conection property and notifies observers 
     * about changes. 
     * 
     * @param alive   true to indicate that this Bacula system is connected 
     * to a Director
     */
    public void setConnected(boolean connected) {
        boolean oConnected = isConnected();
        this.connected = connected;
        firePropertyChange(PROPNAME_CONNECTED, oConnected, this.connected);
    }

    
    public String toString() {
        return getName() ;
    }        
    
       /** FIXME */
    public void initializeObject() {
    }
    
    /** FIXME */
    public void releaseObject() {
    }
}
