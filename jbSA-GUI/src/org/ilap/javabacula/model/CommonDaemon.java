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

import java.util.Date;
import java.lang.*;

import org.ilap.javabacula.parsers.*;
import org.ilap.javabacula.network.IStatusListener;

/**
 * Describes a commond bacula daemon and provides bound bean properties.
 * 
 * @author  Pal Dorogi
 * @version $Revision$
 */
public abstract class CommonDaemon extends BaculaObject implements IStatusListener {  
        private StatusParser statusParser;

    
    /**
     * Properties for the Common Bacula class with those values:
     * 1. DIRECTOR
     * status director:
     * Automatically selected Catalog: MyCatalog
     * Using Catalog "MyCatalog"
     * srv Version: 2.0.0 (04 January 2007) i686-pc-linux-gnu redhat (Bordeaux)
     * Daemon started 07-feb-07 11:35, 0 Jobs run since started.
     * Heap: bytes=37,044 max_bytes=37,099 bufs=304 max_bufs=305
     * 
     * show director:
     * Director: name=bkpsrv MaxJobs=20 FDtimeout=1800 SDtimeout=1800
     *   query_file=/opt/bacula/scripts/query.sql
     *  --> Messages: name=Daemon
     *       mailcmd=/opt/bacula/sbin/bsmtp -h localhost -f "(Bacula) %r" -s "Bacula daemon message" %r
     *
     *
     * 2. Storage
     * status storage:
     * str Version: 2.0.0 (04 January 2007) i686-pc-linux-gnu redhat (Bordeaux)
     * Daemon started 07-feb-07 11:35, 0 Jobs run since started.
     * Heap: bytes=14,782 max_bytes=79,182 bufs=68 max_bufs=70
     *
     * show storage=
     * Storage: name=FileStorage address=bkpsrv SDport=9103 MaxJobs=100
     *  DeviceName=FileStorage MediaType=File StorageId=1
     *
     * 3. Client
     * status client:
     * cln Version: 2.0.0 (04 January 2007)  i686-pc-linux-gnu redhat (Bordeaux)
     * Daemon started 07-feb-07 11:35, 0 Jobs run since started.
     * Heap: bytes=8,792 max_bytes=8,911 bufs=49 max_bufs=50
     * Sizeof: boffset_t=8 size_t=4 debug=0 trace=0
     * 
     * show client=
     * 
     * Client: name=bkpsrv address=bkpsrv FDport=9102 MaxJobs=100
     *      JobRetention=6 months  FileRetention=1 month  AutoPrune=1
     *    --> Catalog: name=MyCatalog address=*None* DBport=0 db_name=bacula
     *           db_user=bacula MutliDBConn=0
     *
     */
    public static final String PROPNAME_ADDRESS     = "address";
    public static final String PROPNAME_PORT        = "port";
    public static final String PROPNAME_VERSION     = "version";
    public static final String PROPNAME_BUILDDATE   = "buildDate";
    public static final String PROPNAME_HOSTOS      = "hostOS";
    public static final String PROPNAME_DISTNAME    = "distName";
    public static final String PROPNAME_DISTVERSION = "distVersion";
    
    public static final String PROPNAME_STARTED     = "started";
    public static final String PROPNAME_JOBSSINCE   = "jobsSince";
    
    public static final String PROPNAME_MAXJOBS     = "maxJobs";
    
    // Properties' name
    public static final String PROPNAME_ALIVE       = "alive";

    
    
    
    protected String address    = new String(); /** Address or IP*/
    protected Integer port      = new Integer(0);
    protected String version    = new String();
    protected Date   buildDate;
    protected String hostOS     = new String();
    protected String distName   = new String();
    protected String distVersion    = new String();
    
    protected Date  started;
    protected Integer jobsSince = new Integer(0);     
    protected Integer maxJobs   = new Integer(0);    
    
    private boolean alive = false;


    /** Creates a new instance of CommonDaemon 
     * @param type 
     */
    public CommonDaemon(int type) {        
        super(type);
        statusParser = new StatusParser((CommonDaemon)this);
    }    
    
    public void parseStatus(String status) {
        statusParser.parse(status);
    }

    
    /**
     * Returns the daemon's address. 
     * 
     * @return the daemon's address.
     */
    public String getAddress() {
        return address;
    }
    
    /** 
     * Sets this daemon's hostname or IP address. 
     * 
     * @param address   The daemon's hostname or IP address.
     */
    public void setAddress(String address) {
        String oAddress = getAddress();
        this.address = address;
        firePropertyChange(PROPNAME_ADDRESS, oAddress, this.address);
        
    }

    /**
     * Returns the daemon's port number. 
     * 
     * @return the daemon's port number.
     */
    public Integer getPort() {
        return port;
    }

     /** 
     * Sets this daemon's port number. 
     * 
     * @param port   The daemon's port number.
     */
    public void setPort(Integer port) {
        Integer oPort = getPort();
        this.port = port;
        firePropertyChange(PROPNAME_PORT, oPort, this.port);
    }
    
    /**
     * Returns this daemon's version number. 
     * 
     * @return this daemon's version number.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets this daemon's version property and notifies observers about changes. 
     * 
     * @param version   The daemon version number to set.
     */    
    public synchronized void setVersion(String version) {
        String oVersion = getVersion();
        this.version = version;
        firePropertyChange(PROPNAME_VERSION, oVersion, this.version);
    }

    /**
     * Returns this daemon's build date. 
     * 
     * @return this daemon's build date.
     */

    public Date getBuildDate() {
        return buildDate;
    }
    
    /**
     * Sets this daemon's buildDate property and notifies observers about changes. 
     * 
     * @param buildDate   The daemon building date to set.
     */
    public synchronized void setBuildDate(Date buildDate) {
        Date odate = getBuildDate();
        this.buildDate = buildDate;
        firePropertyChange(PROPNAME_BUILDDATE, odate, this.buildDate);

    }

    /**
     * Returns this daemon's host operating system string. 
     * 
     * @return this daemon's host operating system string.
     */
    public String getHostOS() {
        return hostOS;
    }

    /**
     * Sets this daemon's hostOS property and notifies observers about changes. 
     * 
     * @param hostOS   The daemon host operating system vstring to set.
     */
    public synchronized void setHostOS(String hostOS) {
        String oHostOS = getHostOS();
        this.hostOS = hostOS;
        firePropertyChange(PROPNAME_HOSTOS, oHostOS, this.hostOS);
    }

    
    /**
     * Returns this daemon's host operating system distribution name. 
     * 
     * @return this daemon's host operating system distribution name.
     */
    public String getDistName() {
        return distName;
    }

    /**
     * Sets this daemon's distName property and notifies observers about changes. 
     * 
     * @param distName   The daemon's OS distribution name.
     */
    public synchronized void setDistName(String distName) {
        String oDistName = getDistName();
        this.distName = distName;
        firePropertyChange(PROPNAME_DISTNAME, oDistName, this.distName);
    }


    /**
     * Returns this daemon's host operating system distribution version. 
     * 
     * @return this daemon's host operating system distribution version.
     */
    public String getDistVersion() {
        return distVersion;
    }

    /**
     * Sets this daemon's distVersion property and notifies observers about changes. 
     * 
     * @param distVersion   The daemon's OS distribution number
     */
    public synchronized void setDistVersion(String distVersion) {
        String oDistVersion = getDistVersion();
        this.distVersion = distVersion;
        firePropertyChange(PROPNAME_DISTVERSION, oDistVersion, this.distVersion);
    }

    /**
     * Returns this daemon's start date and time. 
     * 
     * @return this daemon's start date and time.
     */
    public Date getStarted() {
        return started;
    }
    
    /**
     * Sets this daemon's setStarted property and notifies observers about changes. 
     * 
     * @param started   The daemon's starting date and time. 
     */
    public synchronized void setStarted(Date started) {
        Date oStarted = getStarted();
        this.started = started;
        firePropertyChange(PROPNAME_STARTED, oStarted, this.started);
    }

    
    /**
     * Returns the number of ran jobs since this daemon started. 
     * 
     * @return the number of ran jobs since this daemon started.
     */
    public Integer getJobsSince() {
        return jobsSince;
    }

    /** 
     * Sets this daemon's jobsSince property and notifies observers about changes. 
     * 
     * @param jobsSince   The number of running jobs since this daemon started.
     */
    public synchronized void setJobsSince(Integer jobsSince) {
        Integer oJobsSince = getJobsSince();
        this.jobsSince = jobsSince;
        firePropertyChange(PROPNAME_JOBSSINCE, oJobsSince, this.jobsSince);
    }

    public String toString() {
        String n = getName();
        return n==null?"Not known":n;
    }

    public Integer getMaxJobs() {
        return maxJobs;
    }

    public void setMaxJobs(Integer maxJobs) {
        Integer oMaxJobs = getMaxJobs();
        this.maxJobs = maxJobs;
        firePropertyChange(PROPNAME_MAXJOBS, oMaxJobs, this.maxJobs);

    }
  
    /**
     * Answers whether this daemon is alive or not.
     * 
     * @return true if this daemon is alive, false if not
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets this Daemon's conection property and notifies observers 
     * about changes. 
     * 
     * @param alive   true to indicate that this Daemon is alive
     */
    public void setAlive(boolean alive) {
        boolean oAlive = isAlive();
        this.alive = alive;
        firePropertyChange(PROPNAME_ALIVE, oAlive, this.alive);
    }

    public void initializeObject() {
        StatusQueueManager.getInstance().registerDaemon(this);
    }
    
    public void releaseObject() {
        StatusQueueManager.getInstance().unRegisterDaemon(this);        
    }
}
