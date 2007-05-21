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


/**
 *prompt# show catalog
 *    Catalog: name=MyCatalog address=*None* DBport=0 db_name=bacula
 *     db_user=bacula MutliDBConn=0
 *
 * @author ilap
 */
public class Catalog extends BaculaObject {
    
    public static final String PROPNAME_ADDRESS     = "address";
    public static final String PROPNAME_DBPORT      = "dbPort";
    public static final String PROPNAME_DBNAME      = "dbName";
    public static final String PROPNAME_DBUSER      = "dbUser";
    public static final String PROPNAME_MULTIDBCONN = "multiDbConn";

    private String  address; 
    private Integer dbPort; 
    private String  dbName; 
    private String  dbUser; 
    private boolean multiDbConn; 
    
    /** Creates a new instance of Catalog */
    public Catalog() {
    }
    
    /**
     * Returns the address of catalog. 
     * 
     * @return the address of catalog.
     */
    public String getAddress() {
        return address;
    }
    
    /** 
     * Sets this hostname or IP address of catalog. 
     * 
     * @param address   The hostname or IP address of catalog.
     */
    public void setAddress(String address) {
        String oAddress = getAddress();
        this.address = address;
        firePropertyChange(PROPNAME_ADDRESS, oAddress, this.address);
        
    }

    /**
     * Returns the port number of catalog. 
     * 
     * @return the port number of catalog.
     */
    public Integer getDbPort() {
        return dbPort;
    }

     /** 
     * Sets this port number of catalog. 
     * 
     * @param port   The port number of catalog.
     */
    public void setDbPort(Integer dbPort) {
        Integer oDbPort = getDbPort();
        this.dbPort = dbPort;
        firePropertyChange(PROPNAME_DBPORT, oDbPort, this.dbPort);
    }

    /**
     * Returns name of this bacula object. 
     * 
     * @return name of this bacula object.
     */
    public String getDbName() {
        return dbName;
    }

    public synchronized void setDbName(String dbName) {
        String oDbName = getDbName();
        this.dbName = dbName;
        firePropertyChange(PROPNAME_DBNAME, oDbName, this.dbName);
    }

    public String getDbUser() {
        return dbUser;
    }

    public synchronized void setDbUser(String dbUser) {
        String oDbUser = getDbUser();
        this.dbUser = dbUser;
        firePropertyChange(PROPNAME_DBUSER, oDbUser, this.dbUser);
    }

    public boolean isMultiDbConn() {
        return multiDbConn;
    }

    public void setMultiDbConn(boolean multiDbConn) {
        boolean oMultiDbConn = isMultiDbConn();
        this.multiDbConn = multiDbConn;
        firePropertyChange(PROPNAME_MULTIDBCONN, oMultiDbConn, this.multiDbConn);
    }
    
    /** FIXME */
    public void initializeObject() {
    }
    
    /** FIXME */
    public void releaseObject() {
    }

}
