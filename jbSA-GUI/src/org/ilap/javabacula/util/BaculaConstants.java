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

package org.ilap.javabacula.util;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;

/**
*  
 * @author ilap
 */
public class BaculaConstants {
    public static final String ID_APPLICATION   = "JavaBacula";
    public static final String ID_SYSTEMS   = "Bacula Systems";
    public static final String ID_SYSTEM    = "System";
    
    public static final String ID_DIRECTOR  = "Director";
    public static final String ID_RESTORE    = "Restore";
    public static final String ID_JOBMON    = "Job Monitor";

    public static final String ID_DEVMGMT   = "Device Management";
    public static final String ID_TAPELIBS  = "Tape Libraries";
    public static final String ID_SINGLEDEVS    = "Single Devices";
    public static final String ID_TAPELIB  = "Tape Library";
    public static final String ID_SINGLEDEV    = "Single Device";
    public static final String ID_FILESTORAGES  = "File Storages";
    
    public static final String ID_MEDIAMGMT = "Media Management";
    public static final String ID_JOBS      = "Jobs";
    public static final String ID_JOB       = "Job";
    public static final String ID_JOBO      = "Job OK";
    public static final String ID_JOBE      = "Job Error";
    public static final String ID_JOBR      = "Job Running";
    public static final String ID_JOBW      = "Job Warning";
    public static final String ID_POOLS     = "Pools";
    public static final String ID_POOL      = "Pool";
    public static final String ID_VOLUMES   = "Volumes";
    public static final String ID_VOLUME    = "Volume";
    public static final String ID_SCHEDULES = "Schedules";
    public static final String ID_SCHEDULE  = "Schedule";
    public static final String ID_FILESET   = "FileSet";
    public static final String ID_FILESETS  = "FileSets";


    public static final String ID_BACMGMT   = "Bacula Management";
    public static final String ID_CLIENTS   = "Clients";
    public static final String ID_CLIENT    = "Client";
    public static final String ID_STORAGES  = "Storages";
    public static final String ID_STORAGE   = "Storage";
    public static final String ID_DEVICE    = "Device";
    public static final String ID_CATALOGS  = "Catalogs";
    public static final String ID_CATALOG   = "Catalog";
    
    public static final String ID_MESSAGES  = "Messages";
    public static final String ID_MESSAGE   = "Message";

    public static final String ID_LOGS      = "Logs";
    public static final String ID_LOG       = "Log";
    public static final String ID_LOGE      = "Error";
    public static final String ID_LOGW      = "Warning";
    public static final String ID_LOGF      = "Failed";
    public static final String ID_CONSOLE   = "Console";
    public static final String ID_UNKNOWN   = "Unknown";
    
    public static final String ID_FOLDER            = "Folder";
    public static final String ID_FOLDER_MARKED     = "Folder Marked";
    public static final String ID_FOLDER_PARTMARKED = "Folder Partialy Marked";
    
    public static final String ID_FOLDER_OPEN            = "Folder Open";
    public static final String ID_FOLDER_OPEN_MARKED     = "Folder Open Marked";
    public static final String ID_FOLDER_OPEN_PARTMARKED = "Folder Open Partialy Marked";


    public static final String ID_FILE            = "File";
    public static final String ID_FILE_MARKED     = "File Marked";
    public static final String ID_FILE_PARTMARKED = "File Partialy Marked";
    
    public static final String ID_CONNECTED       = "Connected";
    public static final String ID_DISCONNECTED       = "Disconnected";


    /*public static final String SYSTEMS_VIEW = "SystemsView";
    public static final String MAIN_VIEW    = "MainView";
    public static final String BACKUP_VIEW  = "BackupView";
    public static final String JOBSMON_VIEW = "JobsMonitorView";
    public static final String DEVMGMT_VIEW = "DeviceManagementView";
    public static final String MEDMGMT_VIEW = "MediaManagementView";
    public static final String BACMGMT_VIEW = "BaculaManagementView";
    public static final String DEVS_VIEW = "DevicesView";
    */
    
        /* Derived from bsock.h */
    public static final int BNET_EOD        = -1;   /* End Of Data stream, new data may follow */
    public static final int BNET_EOD_POLL   = -2;   /* End Of Data and Poll (all in one) */
    public static final int BNET_STATUS     = -3;   /* Send full status */
    public static final int BNET_TERMINATE  = -4;   /* Conversation terminated, doing close() */
    public static final int BNET_POLL       = -5;   /* Poll request, I'm hanging on a read */
    public static final int BNET_HEARTBEAT  = -6;   /* Heartbeat Response requested */
    public static final int BNET_HB_RESPONSE= -7;   /* Only response permited to HB */
    public static final int BNET_PROMPT     = -8;   /* Prompt for UA */
    public static final int BNET_BTIME      = -9;   /* Send UTC btime */
    public static final int BNET_BREAK      = -10;  /* Stop current command -- ctl-c */

    /* Return status from jniReceiveMessages() */
    public static final int BNET_SIGNAL   = -1;
    public static final int BNET_HARDEOF  = -2;
    public static final int BNET_ERROR    = -3;

    
    /*
     * TLS enabling values. Value is important for comparison, ie:
     * if (tls_remote_need < BNET_TLS_REQUIRED) { ... }
     */
    public static final int BNET_TLS_NONE     = 0;           /* cannot do TLS */
    public static final int BNET_TLS_OK       = 1;           /* can do, but not required on my end */
    public static final int BNET_TLS_REQUIRED = 2;           /* TLS is required */
    /**
     * Bacula Object types:
     * Jobs, Pools, Schedules, FileSets, Messages etc.
     */
    public static final int DIRECTOR_TYPE   = 0x00000000; // Director object
    public static final int CLIENT_TYPE     = 0x00000001; // Client object
    public static final int STORAGE_TYPE    = 0x00000002; // Storage object
    public static final int DEVICE_TYPE     = 0x00000004; // Device object similar /w Storage
    public static final int JOB_TYPE        = 0x00000008; // Job object
    public static final int JOBDEFS_TYPE    = 0x00000010; // Jobdefs object
    public static final int CATALOG_TYPE    = 0x00000020; // Catalog object
    public static final int SCHEDULE_TYPE   = 0x00000040; // Schedule object
    public static final int FILESET_TYPE    = 0x00000080; // Fileset object
    public static final int POOL_TYPE       = 0x00000100; // Pool object
    public static final int CONSOLE_TYPE    = 0x00000200; // Console a.k.a users object
    public static final int MESSAGE_TYPE    = 0x00000400; // Message object
    public static final int VOLUME_TYPE     = 0x00000800; // Volume object
    public static final int SYSTEM_TYPE     = 0x00001000; // System object
    public static final int ACTIVATEDJOB_TYPE     = 0x00002000; // activated job object
    public static final int UNKNOWN_TYPE    = 0x00008000; // Unknown object

    /**
     * state of modify Bacula Object  :
     * Createable, Editable, Deleteabvle, Refreshable
     */
    public static final int NOSTATE      = 0x0; //
    public static final int CREATEABLE   = 0x1; //
    public static final int EDITABLE     = 0x2; // 
    public static final int DELETEABLE   = 0x4; // 
    public static final int REFRESHABLE  = 0x8; // 
    public static final int ALLSTATE     = 0xF; // 
    
    public static final int DIRECTOR_PORT   = 9101;
    public static final int CLIENT_PORT     = 9102;
    public static final int STORAGE_PORT    = 9103;
    
 
    public static final String JB_NAME	    = "JavaBacula";
    public static final String JB_VERSION   = "v0.2 prealpha"; 
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String JAVA_HOME    = System.getProperty("java.home");
    public static final String JAVA_NAME    = System.getProperty("java.name");
    public static final String VM_NAME      = System.getProperty("java.vm.name");
    public static final String VM_VERSION   = System.getProperty("java.vm.version");
    public static final String VM           = VM_NAME + " " + VM_VERSION;
    public static final String USER_DIR     = System.getProperty("user.dir");
    public static final String USER_HOME    = System.getProperty("user.home");
    public static final String USER_NAME    = System.getProperty("user.name");
        
    public static final String OSNAME    = System.getProperty("os.name");
    public static final String OSARCH    = System.getProperty("os.arch");
    public static final String OSVERSION = System.getProperty("os.version");
    public static final String OS        = OSNAME + " version " + OSVERSION + 
                                                        " running on " + OSARCH;
    
    
  
    public static final boolean isOSX	  = OSNAME.equalsIgnoreCase("Mac OS X");
    public static final boolean isLinux	  = OSNAME.equalsIgnoreCase("Linux");
    public static final boolean isWindows = OSNAME.equalsIgnoreCase("Windows XP");
  
    public static final String RESOURCE_PATH = "org/ilap/javabacula/ui/resources/";
    public static final String ICONS    = RESOURCE_PATH + "icons/";
    public static final String ICONS_TB = RESOURCE_PATH + "icons/toolbar/";
    public static final String IMAGES   = RESOURCE_PATH + "images/";
    public static final String IMG_SPLASH   = IMAGES +  "splash.gif";
    

    public static final String CONFIG_DIR = System.getProperty("user.dir") + 
            "/jbSA.config";

  
    /**
     * Generate default properties
     */
    public static final String ID_JB_NAME      = "javabacula.name";
    public static final String ID_JB_VERSION   = "javabacula.version";

    public static final String ID_SPLASH        = "javabacula.init.splash";
    public static final String ID_STARTUP_DIR   = "javabacula.defaults.user.dir";

    public static final String ID_WINDOW_LEFT   = "javabacula.window.left";
    public static final String ID_WINDOW_TOP    = "javabacula.window.top";
    public static final String ID_WINDOW_WIDTH  = "javabacula.window.width";
    public static final String ID_WINDOW_HEIGHT = "javabacula.window.height";
    
    public static final String ID_CONF_DIR      = "javabacula.director";

    // Header panels button ID
    public static final int ID_ADD_BTN      = 0;
    public static final int ID_EDIT_BTN     = 1;
    public static final int ID_REMOVE_BTN   = 2;
    public static final int ID_REFRESH_BTN  = 3;
    
    /**
     * Table header columns
     */

    public static final String [] SYS_COLS = 
                                      {"System Name", "Director Address", "Director Port", "Last login", "Logged as"};
    
    public static final Logger LOGGER = Logger.getRootLogger();
    
    private static HashMap jobType      = new HashMap();
    private static HashMap jobStatus    = new HashMap();
    private static HashMap backupLevel  = new HashMap();
    
    static {
        
                
        backupLevel.put("F", "Full backup");
        backupLevel.put("I", "Incremental backup");
        backupLevel.put("D", "Differential backup");
        backupLevel.put("C", "Verify from catalog");
        backupLevel.put("V", "Verify init db");
        backupLevel.put("O", "Verify volume to catalog");
        backupLevel.put("d", "Verify disk to catalog");
        backupLevel.put("A", "Verify data on volume");
        backupLevel.put("B", "Base job");
        backupLevel.put("R", "Restore or admin job");
                
        jobType.put("B", "Backup");
        jobType.put("V", "Verify");
        jobType.put("R", "Restore");
        jobType.put("C", "Console");
        jobType.put("I", "Internal system Job");
        jobType.put("D", "Admin");
        jobType.put("Y", "Copy");
        jobType.put("M", "Migration");

        
        jobStatus.put("A","Canceled by user");
        jobStatus.put("B","Blocked");
        jobStatus.put("C","Created, but not running");
        jobStatus.put("c","Waiting for client resource");
        jobStatus.put("D","Verify differences");
        jobStatus.put("d","Waiting for maximum jobs");
        jobStatus.put("E","Terminated in error");
        jobStatus.put("e","Non-fatal error");
        jobStatus.put("f","fatal error");
        jobStatus.put("F","Waiting on File Daemon");
        jobStatus.put("j","Waiting for job resource");
        jobStatus.put("M","Waiting for mount");
        jobStatus.put("m","Waiting for new media");
        jobStatus.put("p","Waiting for higher priority jobs to finish");
        jobStatus.put("R","Running");
        jobStatus.put("S","Scan");
        jobStatus.put("s","Waiting for storage resource");
        jobStatus.put("T","Terminated normally");
        jobStatus.put("t","Waiting for start time");

        
    }
    
    // No more Instances:)
    private BaculaConstants() {
    }
    
    public static String GETBACKUPLEVEL(String id) {
        return (String)backupLevel.get(id);
    }

    public static String GETJOBTYPE(String id) {
        return (String)jobType.get(id);
    }
    public static String GETJOBSTATUS(String id) {
        return (String)jobStatus.get(id);
    }

}
