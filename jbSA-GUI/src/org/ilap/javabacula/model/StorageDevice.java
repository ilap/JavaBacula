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
import org.ilap.javabacula.network.IStatusListener;

/**
 *
 * @author ilap
 */
public class StorageDevice extends BaculaObject implements IStatusListener {

    public static final String PROPNAME_PARENTJUKEBOX   = "parentJukeBox";
    public static final String PROPNAME_DEVICENAME  = "deviceName";
    public static final String PROPNAME_DEVICEPATH  = "devicePath";
    public static final String PROPNAME_MEDIATYPE   = "mediaType";
    public static final String PROPNAME_STORAGEID   = "storageId";
    public static final String PROPNAME_VOLUME      = "volume";
    public static final String PROPNAME_POOL        = "pool";
    public static final String PROPNAME_MSG         = "message";
        
    private StorageDaemon  parentJukeBox;          
    private String  deviceName;          
    private String  devicePath;          
    private String  mediaType;          
    private int     storageId;
    private String  volume;          
    private String  pool;          
    private String  message;          

    
    /** Creates a new instance of StorgeDevice */
    public StorageDevice() {
        super(BaculaConstants.DEVICE_TYPE);
    }

    public StorageDevice(String name) {
        this();
        setName(name);
    }
        
    /** FIXME */
    public void releaseObject() {
    }
    
    public void initializeObject() {
    }

    public StorageDaemon getParentJukeBox() {
        return parentJukeBox;
    }

    public void setParentJukeBox(StorageDaemon parentJukeBox) {
        StorageDaemon oParentJukeBox = getParentJukeBox();
        this.parentJukeBox = parentJukeBox;
        firePropertyChange(PROPNAME_PARENTJUKEBOX, oParentJukeBox, this.parentJukeBox);
    }

    public String getDevicePath() {
        return devicePath;
    }

    public void setDevicePath(String devicePath) {
        String oDevicePath = getDevicePath();
        this.devicePath = devicePath;
        firePropertyChange(PROPNAME_DEVICEPATH, oDevicePath, this.devicePath);
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

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        int oStorageId = getStorageId();
        this.storageId = storageId;
        firePropertyChange(PROPNAME_STORAGEID, oStorageId, this.storageId);
    }     

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        String oVolume = getVolume();
        this.volume = volume;
        firePropertyChange(PROPNAME_VOLUME, oVolume, this.volume);
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        String oPool = getPool();
        this.pool = pool;
        firePropertyChange(PROPNAME_POOL, oPool, this.pool);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        String oMessage = getMessage();
        this.message = message;
        firePropertyChange(PROPNAME_MSG, oMessage, this.message);
    }
    
    public void statusReceived(String status) {
        setMessage(status);
        BaculaConstants.LOGGER.debug("Status received in " + this.toString());        
    }

}
