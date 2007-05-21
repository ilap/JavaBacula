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
import java.util.Vector;

/**
 *
 * @author ilap
 */
public class Volume extends BaculaObject {
    /*
     *MediaId | VolumeName | VolStatus | Enabled | VolBytes | VolFiles | VolRetention | Recycle | Slot | InChanger | MediaType | LastWritten
     */
    
    public static final String PROPNAME_MEDIAID         = "mediaId";    //0
    public static final String PROPNAME_VOLSTATUS       = "volStatus";  //1
    public static final String PROPNAME_POOL            = "pool";       //2
    public static final String PROPNAME_ENABLED         = "enabled";    //3
    public static final String PROPNAME_VOLBYTES        = "volBytes";   //4
    public static final String PROPNAME_VOLFILES        = "volFiles";   //5
    public static final String PROPNAME_VOLRETENTION    = "volRetention";//6
    public static final String PROPNAME_RECYCLE         = "recycle";    //7
    public static final String PROPNAME_SLOT            = "slot";       //8
    public static final String PROPNAME_INCHANGER       = "inChanger";  //9
    public static final String PROPNAME_MEDIATYPE       = "mediaType";  //10
    public static final String PROPNAME_LASTWRITTEN     = "lastWritten";//11

    private Integer mediaId;
    private String volStatus;
    private Pool pool;
    private boolean enabled;
    private Integer volBytes;
    private Integer volFiles;
    private Integer volRetention;
    private boolean recycle;
    private Integer slot;
    private boolean inChanger;  
    private String mediaType;
    private Date lastWritten;
    
    
    /** Creates a new instance of Volume */    
    public Volume() {
    }
    

    /** FIXME */
    public void initializeObject() {}
    public void releaseObject() {}

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getVolBytes() {
        return volBytes;
    }

    public void setVolBytes(Integer volBytes) {
        this.volBytes = volBytes;
    }

    public Integer getVolFiles() {
        return volFiles;
    }

    public void setVolFiles(Integer volFiles) {
        this.volFiles = volFiles;
    }

    public Integer getVolRetention() {
        return volRetention;
    }

    public void setVolRetention(Integer volRetention) {
        this.volRetention = volRetention;
    }

    public boolean isRecycle() {
        return recycle;
    }

    public void setRecycle(boolean recycle) {
        this.recycle = recycle;
    }

    public boolean isInChanger() {
        return inChanger;
    }

    public void setInChanger(boolean inChanger) {
        this.inChanger = inChanger;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Date getLastWritten() {
        return lastWritten;
    }

    public void setLastWritten(Date lastWritten) {
        this.lastWritten = lastWritten;
    }

    public String getVolStatus() {
        return volStatus;
    }

    public void setVolStatus(String volStatus) {
        this.volStatus = volStatus;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

}
