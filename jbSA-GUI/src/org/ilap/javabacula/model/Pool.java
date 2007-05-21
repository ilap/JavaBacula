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
 *
 *
 * @author ilap
 */
public class Pool extends BaculaObject {
        
    public static final String PROPNAME_POOLTYPE        = "poolType";
    public static final String PROPNAME_USECAT          = "useCat";
    public static final String PROPNAME_USEONCE         = "useOnce";
    public static final String PROPNAME_CATFILES        = "catFiles";
    public static final String PROPNAME_MAXVOLS         = "maxVols";
    public static final String PROPNAME_AUTOPRUNE       = "autoPrune";
    public static final String PROPNAME_VOLRETENTION    = "volRetention";
    public static final String PROPNAME_VOLUSE          = "volUse";
    public static final String PROPNAME_RECYCLE         = "recycle";
    public static final String PROPNAME_LABELFORMAT     = "labelFormat";
    public static final String PROPNAME_LABELTYPE       = "labelType";
    public static final String PROPNAME_CLEANINGPREFIX  = "cleaningPrefix";
    public static final String PROPNAME_RECYCLEOLDEST   = "recycleOldest";
    public static final String PROPNAME_PURGEOLDEST     = "purgeOldest";
    public static final String PROPNAME_MAXVOLJOBS      = "maxVolJobs";
    public static final String PROPNAME_MAXVOLFILES     = "maxVolFiles";
    public static final String PROPNAME_MIGTIME         = "migTime";
    public static final String PROPNAME_MIGHIBYTES      = "migHiBytes";
    public static final String PROPNAME_MIGLOBYTES      = "migLoBytes";        
 
    /* Pool: name=DevelDatas PoolType=Backup
     *      use_cat=1 use_once=0 cat_files=1
     *     max_vols=0 auto_prune=1 VolRetention=1 year 
     *     VolUse=0 secs recycle=1 LabelFormat=*None*
     *     CleaningPrefix=*None* LabelType=0
     *     RecyleOldest=0 PurgeOldest=0 MaxVolJobs=0 MaxVolFiles=0
     *     MigTime=0 secs MigHiBytes=0 MigLoBytes=0
     */
 
    private String  poolType;    
    private boolean useCat;
    private boolean useOnce;
    private Integer catFiles;
    private Integer maxVols;
    private boolean autoPrune;
    private String  volRetention;
    private String  volUse;
    private boolean recycle;
    private String  labelFormat;
    private Integer labelType;
    private String  cleaningPrefix;
    private boolean recycleOldest;
    private boolean purgeOldest;
    private Integer maxVolJobs;
    private Integer maxVolFiles;
    private String  migTime;
    private Integer migHiBytes;
    private Integer migLoBytes;

        /** Creates a new instance of Pool */
    public Pool() {
    }        
    
    public String getPoolType() {
        return poolType;
    }

    public void setPoolType(String poolType) {
        String oPoolType = getPoolType();
        this.poolType = poolType;
        firePropertyChange(PROPNAME_POOLTYPE, oPoolType, this.poolType);
    }

    public boolean isUseCat() {
        return useCat;
    }

    public void setUseCat(boolean useCat) {
        boolean oUseCat = isUseCat();
        this.useCat = useCat;
        firePropertyChange(PROPNAME_USECAT, oUseCat, this.useCat);
    }

    public boolean isUseOnce() {
        return useOnce;
    }

    public void setUseOnce(boolean useOnce) {
        boolean oUseOnce = isUseOnce();
        this.useOnce = useOnce;
        firePropertyChange(PROPNAME_USEONCE, oUseOnce, this.useOnce);
    }

    public Integer getCatFiles() {
        return catFiles;
    }

    public void setCatFiles(Integer catFiles) {
        Integer oCatFiles = getCatFiles();
        this.catFiles = catFiles;
        firePropertyChange(PROPNAME_CATFILES, oCatFiles, this.catFiles);
    }

    public Integer getMaxVols() {
        return maxVols;
    }

    public void setMaxVols(Integer maxVols) {
        Integer oMaxVols = getMaxVols();
        this.maxVols = maxVols;
        firePropertyChange(PROPNAME_MAXVOLS, oMaxVols, this.maxVols);
    }

    public boolean isAutoPrune() {
        return autoPrune;
    }

    public void setAutoPrune(boolean autoPrune) {
        boolean oAutoPrune = isAutoPrune();
        this.autoPrune = autoPrune;
        firePropertyChange(PROPNAME_AUTOPRUNE, oAutoPrune, this.autoPrune);
    }

    public String getVolRetention() {
        return volRetention;
    }

    public void setVolRetention(String volRetention) {
        String oVolRetention = getVolRetention();
        this.volRetention = volRetention;
        firePropertyChange(PROPNAME_VOLRETENTION, oVolRetention, this.volRetention);
    }

    public String getVolUse() {
        return volUse;
    }

    public void setVolUse(String volUse) {
        String oVolUse = getVolUse();
        this.volUse = volUse;
        firePropertyChange(PROPNAME_VOLUSE, oVolUse, this.volUse);
    }

    public boolean isRecycle() {
        return recycle;
    }

    public void setRecycle(boolean recycle) {
        boolean oRecycle = isRecycle();
        this.recycle = recycle;
        firePropertyChange(PROPNAME_RECYCLE, oRecycle, this.recycle);
    }

    public String getLabelFormat() {
        return labelFormat;
    }

    public void setLabelFormat(String labelFormat) {
        String oLabelFormat = getLabelFormat();
        this.labelFormat = labelFormat;
        firePropertyChange(PROPNAME_LABELFORMAT, oLabelFormat, this.labelFormat);
    }

    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        Integer oLabelType = getLabelType();
        this.labelType = labelType;
        firePropertyChange(PROPNAME_LABELTYPE, oLabelType, this.labelType);
    }

    public String getCleaningPrefix() {
        return cleaningPrefix;
    }

    public void setCleaningPrefix(String cleaningPrefix) {
        String oCleaningPrefix = getCleaningPrefix();
        this.cleaningPrefix = cleaningPrefix;
        firePropertyChange(PROPNAME_CLEANINGPREFIX, oCleaningPrefix, this.cleaningPrefix);
    }

    public boolean isRecycleOldest() {
        return recycleOldest;
    }

    public void setRecycleOldest(boolean recycleOldest) {
        boolean oRecycleOldest = isRecycleOldest();
        this.recycleOldest = recycleOldest;
        firePropertyChange(PROPNAME_RECYCLEOLDEST, oRecycleOldest, this.recycleOldest);
    }

    public boolean isPurgeOldest() {
        return purgeOldest;
    }

    public void setPurgeOldest(boolean purgeOldest) {
        boolean oPurgeOldest = isPurgeOldest();
        this.purgeOldest = purgeOldest;
        firePropertyChange(PROPNAME_PURGEOLDEST, oPurgeOldest, this.purgeOldest);
    }

    public Integer getMaxVolJobs() {
        return maxVolJobs;
    }

    public void setMaxVolJobs(Integer maxVolJobs) {
        Integer oMaxVolJobs = getMaxVolJobs();
        this.maxVolJobs = maxVolJobs;
        firePropertyChange(PROPNAME_MAXVOLJOBS, oMaxVolJobs, this.maxVolJobs);
    }

    public Integer getMaxVolFiles() {
        return maxVolFiles;
    }

    public void setMaxVolFiles(Integer maxVolFiles) {
        Integer oMaxVolFiles = getMaxVolFiles();
        this.maxVolFiles = maxVolFiles;
        firePropertyChange(PROPNAME_MAXVOLFILES, oMaxVolFiles, this.maxVolFiles);
    }

    public String getMigTime() {
        return migTime;
    }

    public void setMigTime(String migTime) {
        String oMigTime = getMigTime();
        this.migTime = migTime;
        firePropertyChange(PROPNAME_MIGTIME, oMigTime, this.migTime);
    }

    public Integer getMigHiBytes() {
        return migHiBytes;
    }

    public void setMigHiBytes(Integer migHiBytes) {
        Integer oMigHiBytes = getMigHiBytes();
        this.migHiBytes = migHiBytes;
        firePropertyChange(PROPNAME_MIGHIBYTES, oMigHiBytes, this.migHiBytes);
    }

    public Integer getMigLoBytes() {
        return migLoBytes;
    }

    public void setMigLoBytes(Integer migLoBytes) {
        Integer oMigLoBytes = getMigLoBytes();
        this.migLoBytes = migLoBytes;
        firePropertyChange(PROPNAME_MIGLOBYTES, oMigLoBytes, this.migLoBytes);
    }
    
    /** FIXME */
    public void initializeObject() {}
    public void releaseObject() {}
}
