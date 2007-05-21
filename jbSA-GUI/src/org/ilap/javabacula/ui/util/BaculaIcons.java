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

package org.ilap.javabacula.ui.util;

import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
public class BaculaIcons {
    private Hashtable icons = new Hashtable();

    private static BaculaIcons instance = null;
    
    private BaculaIcons() {
        // no more instances
        makeIcons();
    }

    public static BaculaIcons getInstance() {
        if (instance == null) {
            instance = new BaculaIcons();
        }
        return instance;
    }
    
    public void finalize() {
        instance = null;
        icons = null;
    }
    
    public Hashtable getIcons() {
        return this.icons;
    }

    public Icon getIcon(String key) {
        return (Icon)(this.icons.get(key));
    }
        
    public void makeIcons() {
        
        this.icons.put(BaculaConstants.ID_SYSTEMS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/system_h16.png")));
        this.icons.put(BaculaConstants.ID_SYSTEM, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/director_h16.png")));
        this.icons.put(BaculaConstants.ID_DIRECTOR, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/director_h16.png")));
        
        this.icons.put(BaculaConstants.ID_RESTORE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/bkpsrvcs_h16.png")));
        this.icons.put(BaculaConstants.ID_JOBMON, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/jobmgmt_h16.png")));
        this.icons.put(BaculaConstants.ID_DEVMGMT, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/devmgmt_h16.png")));
        this.icons.put(BaculaConstants.ID_TAPELIBS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tapelibraries_h16.png")));
        this.icons.put(BaculaConstants.ID_TAPELIB, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tapelibrary_h16.png")));
        this.icons.put(BaculaConstants.ID_FILESTORAGES, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/diskstorages_h16.png")));
        this.icons.put(BaculaConstants.ID_SINGLEDEV, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/singledevice_h16.png")));
        this.icons.put(BaculaConstants.ID_SINGLEDEVS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/singledevices_h16.png")));
        this.icons.put(BaculaConstants.ID_MEDIAMGMT, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/mediamgmt_h16.png")));
        
        this.icons.put(BaculaConstants.ID_JOBS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/jobs_h16.png")));
        this.icons.put(BaculaConstants.ID_JOB, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/job_h16.png")));
        this.icons.put(BaculaConstants.ID_JOBO, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/actions/job_ok_h16.png")));
        this.icons.put(BaculaConstants.ID_JOBW, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/actions/job_warning_h16.png")));
        this.icons.put(BaculaConstants.ID_JOBE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/actions/job_error_h16.png")));
        this.icons.put(BaculaConstants.ID_JOBR, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/actions/job_running_h16.png")));

        
        this.icons.put(BaculaConstants.ID_POOLS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/pools_h16.png")));
        this.icons.put(BaculaConstants.ID_POOL, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/pool_h16.png")));
        this.icons.put(BaculaConstants.ID_SCHEDULES, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/schedules_h16.png")));
        this.icons.put(BaculaConstants.ID_SCHEDULE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/schedule_h16.png")));
        this.icons.put(BaculaConstants.ID_VOLUMES, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/volumes_h16.png")));
        this.icons.put(BaculaConstants.ID_VOLUME, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/volumes_h16.png")));
        
        this.icons.put(BaculaConstants.ID_FILESET, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/fileset_h16.png")));
        this.icons.put(BaculaConstants.ID_FILESETS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/fileset_h16.png")));

        this.icons.put(BaculaConstants.ID_BACMGMT, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/bacmgmt_h16.png")));
        this.icons.put(BaculaConstants.ID_CLIENTS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/clients_h16.png")));
        this.icons.put(BaculaConstants.ID_CLIENT, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/client_h16.png")));
        this.icons.put(BaculaConstants.ID_STORAGES, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/storageds_h16.png")));
        this.icons.put(BaculaConstants.ID_STORAGE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/storaged_h16.png")));
        this.icons.put(BaculaConstants.ID_DEVICE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/device_h16.png")));
        this.icons.put(BaculaConstants.ID_CATALOGS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/catalogsg_h16.png")));
        this.icons.put(BaculaConstants.ID_CATALOG, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/catalog_h16.png")));
        this.icons.put(BaculaConstants.ID_MESSAGES, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_MESSAGE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_LOGS, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_LOGE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_LOGW, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_LOGF, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_LOG, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));
        this.icons.put(BaculaConstants.ID_CONSOLE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/console_h16.png")));
        this.icons.put(BaculaConstants.ID_UNKNOWN, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/unknown_h16.png")));

        this.icons.put(BaculaConstants.ID_FOLDER, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/folder.png")));
        this.icons.put(BaculaConstants.ID_FOLDER_MARKED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/folder_marked.png")));
        this.icons.put(BaculaConstants.ID_FOLDER_PARTMARKED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/folder_partmarked.png")));
       
        this.icons.put(BaculaConstants.ID_FOLDER_OPEN, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/folder_open.png")));
        this.icons.put(BaculaConstants.ID_FOLDER_OPEN_MARKED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/folder_open_marked.png")));
        this.icons.put(BaculaConstants.ID_FOLDER_OPEN_PARTMARKED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/folder_open_partmarked.png")));

        this.icons.put(BaculaConstants.ID_FILE, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/file.png")));
        this.icons.put(BaculaConstants.ID_FILE_MARKED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/file_marked.png")));
        this.icons.put(BaculaConstants.ID_FILE_PARTMARKED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/tree/file_partmarked.png")));
        this.icons.put(BaculaConstants.ID_APPLICATION, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/application.png")));

        this.icons.put(BaculaConstants.ID_CONNECTED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/actions/connect_h16.png")));
        this.icons.put(BaculaConstants.ID_DISCONNECTED, 
                new ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/actions/disconnect_h16.png")));
    }
    
}
