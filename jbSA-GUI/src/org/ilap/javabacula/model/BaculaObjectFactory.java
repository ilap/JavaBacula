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

import org.ilap.javabacula.util.BaculaConstants;
/**
 *
 * @author ilap
 */
public abstract class BaculaObjectFactory {

    public BaculaObjectFactory() {
    }
   
    
    public synchronized BaculaObject createBaculaItem(int type) {
        BaculaObject object = null;
        switch (type) {
            // Daemons
            case BaculaConstants.DIRECTOR_TYPE:
                object = new DirectorDaemon();
                break;
            case BaculaConstants.STORAGE_TYPE:
                object = new StorageDaemon();
                break;
            case BaculaConstants.CLIENT_TYPE:
                object = new ClientDaemon();
                break;
            // Jobs end others
            case BaculaConstants.CATALOG_TYPE:
                object = new Catalog();
                break;
            case BaculaConstants.POOL_TYPE:
                object = new Pool();
                break;
            case BaculaConstants.FILESET_TYPE:
                object = new FileSet();
                break;
            case BaculaConstants.SCHEDULE_TYPE:
                object = new Schedule();
                break;
            case BaculaConstants.JOB_TYPE:
                object = new Job();
                break;
            case BaculaConstants.VOLUME_TYPE:
                object = new Volume();
                break;
            case BaculaConstants.SYSTEM_TYPE:
                object = new BaculaSystem();
                break;
            case BaculaConstants.ACTIVATEDJOB_TYPE:
                object = new ActivatedJob();
                break;
            default:
            object = null;
        }
        return object;
    }
    
    public abstract BaculaObject createBaculaItem();

}
