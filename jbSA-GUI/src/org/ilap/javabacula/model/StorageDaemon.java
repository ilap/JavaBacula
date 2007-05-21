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

import java.util.Vector;

import java.util.*;

import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.network.IStatusListener;
import org.ilap.javabacula.parsers.*;


/**
 * @author ilap
 */
public class StorageDaemon extends CommonDaemon {

    public static final String PROPNAME_DEVICENAME  = "deviceName";
    public static final String PROPNAME_MEDIATYPE   = "mediaType";
    public static final String PROPNAME_STORAGEID   = "storageId";
    public static final String PROPNAME_AUTOCHANGER = "autoChanger";
    
    private String deviceName;
    private String mediaType;
    private Integer storageId;
    private boolean autoChanger = false;

    
    /** List storage's devices */
    List deviceList = new Vector();
    
    private StatusParser statusParser;
    
    
    /**
     */
    public StorageDaemon() {
        super(BaculaConstants.STORAGE_TYPE);
        statusParser = new StatusParser((CommonDaemon)StorageDaemon.this);
    }

    public void statusReceived(String status) {
        BaculaConstants.LOGGER.debug("STATUS STRING: " + status);        
        statusParser.parse(status);
    }
    
    public synchronized StorageDevice getDeviceByName(String name) {
        StorageDevice dev = null;
        for (Object p : deviceList.toArray()) {
            if (((StorageDevice)p).getName().equals(name)) {
                dev = (StorageDevice)p;
                break;
            }
        }
        return dev;
    }
    
    public synchronized void addDevice(IStatusListener l) {
	deviceList.add(l);
    }

    public synchronized void removeDevice(IStatusListener l) {
	deviceList.remove(l);
    }
    
    public synchronized Vector getDevices() {
        return (Vector) deviceList;
    }
    
    
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        String oDeviceName = getDeviceName();
        this.deviceName = deviceName;
        firePropertyChange(PROPNAME_DEVICENAME, oDeviceName, this.deviceName);
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        String oMediaType = getMediaType();
        this.mediaType = mediaType;
        firePropertyChange(PROPNAME_MEDIATYPE, oMediaType, this.mediaType);
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        Integer oStorageId = getStorageId();
        this.storageId = storageId;
        firePropertyChange(PROPNAME_STORAGEID, oStorageId, this.storageId);
    }
    
    public boolean isAutoChanger() {
        return autoChanger;
    }

    public void setAutochanger(boolean autoChanger) {
        boolean oAutoChanger = isAutoChanger();
        this.autoChanger = autoChanger;
        firePropertyChange(PROPNAME_AUTOCHANGER, oAutoChanger, this.autoChanger);
    }

}
