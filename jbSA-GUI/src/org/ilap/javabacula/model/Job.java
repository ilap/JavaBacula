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
 * Job: name=FileSystems JobType=66 level=Incremental Priority=10 Enabled=1
 *    MaxJobs=1 Resched=0 Times=0 Interval=1,800 Spool=0 WritePartAfterJob=1
 *
 * @author ilap
 */
public class Job extends BaculaObject {
    
    public static final String PROPNAME_JOBTYPE     = "jobType";
    public static final String PROPNAME_LEVEL       = "level";
    public static final String PROPNAME_PRIORITY    = "priority";
    public static final String PROPNAME_ENABLED     = "enabled";
    public static final String PROPNAME_MAXJOBS     = "maxJobs";
    public static final String PROPNAME_RESCHEDULE  = "reschedule";
    public static final String PROPNAME_TIMES       = "times";
    public static final String PROPNAME_INTERVAL    = "interval";
    public static final String PROPNAME_SPOOL       = "spool";
    public static final String PROPNAME_WRITEPARTAFTERJOB   = "writePartAfterJob";

    private Integer jobType;
    private String level;
    private Integer priority;
    private boolean enabled;
    private Long maxJobs;
    private boolean reschedule;
    private Integer times;
    private Integer interval;
    private boolean spool;
    private boolean writePartAfterJob;
    
    
    /** Creates a new instance of Job */
    public Job() {
    }
    
    
    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        Integer oJobType = getJobType();
        this.jobType = jobType;
        firePropertyChange(PROPNAME_JOBTYPE, oJobType, this.jobType);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        String oLevel = getLevel();
        this.level = level;
        firePropertyChange(PROPNAME_LEVEL, oLevel, this.level);
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        Integer oPriority = getPriority();
        this.priority = priority;
        firePropertyChange(PROPNAME_PRIORITY, oPriority, this.priority);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        boolean oEnabled = isEnabled();
        this.enabled = enabled;
        firePropertyChange(PROPNAME_ENABLED, oEnabled, this.enabled);
    }

    public Long getMaxJobs() {
        return maxJobs;
    }

    public void setMaxJobs(Long maxJobs) {
        Long oMaxJobs = getMaxJobs();
        this.maxJobs = maxJobs;
        firePropertyChange(PROPNAME_MAXJOBS, oMaxJobs, this.maxJobs);
    }

    public boolean isReschedule() {
        return reschedule;
    }

    public void setReschedule(boolean reschedule) {
        boolean oReschedule = isReschedule();
        this.reschedule = reschedule;
        firePropertyChange(PROPNAME_RESCHEDULE, oReschedule, this.reschedule);
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        Integer oTimes = getTimes();
        this.times = times;
        firePropertyChange(PROPNAME_TIMES, oTimes, this.times);
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        Integer oInterval = getInterval();
        this.interval = interval;
        firePropertyChange(PROPNAME_INTERVAL, oInterval, this.interval);
    }

    public boolean isSpool() {
        return spool;
    }

    public void setSpool(boolean spool) {
        boolean oSpool = isSpool();
        this.spool = spool;
        firePropertyChange(PROPNAME_SPOOL, oSpool, this.spool);
    }

    public boolean isWritePartAfterJob() {
        return writePartAfterJob;
    }

    public void setWritePartAfterJob(boolean writePartAfterJob) {
        boolean oWritePartAfterJob = isWritePartAfterJob();
        this.writePartAfterJob = writePartAfterJob;
        firePropertyChange(PROPNAME_WRITEPARTAFTERJOB, oWritePartAfterJob, this.writePartAfterJob);
    }
    
    /** FIXME */
    public void initializeObject() {
    }
    
    /** FIXME */
    public void releaseObject() {
    }
}
