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
/**
 *
 * @author ilap
 */
public class ActivatedJob extends BaculaObject {
    //JobId | Name | StartTime| Type | Level | JobFiles | JobBytes | JobStatus |    
    public static final String PROPNAME_JOBID       = "jobID";      //0
    public static final String PROPNAME_JOB         = "job";        //1
    public static final String PROPNAME_STARTTIME   = "startTime";  //2
    public static final String PROPNAME_JOBTYPE     = "jobType";    //3
    public static final String PROPNAME_BACKUPLEVEL = "backupLevel";   //4
    public static final String PROPNAME_JOBFILES    = "jobFiles";   //5
    public static final String PROPNAME_JOBBYTES    = "jobBytes";   //6
    public static final String PROPNAME_JOBSTATUS   = "jobStatus";  //7

    private long    jobID;
    private Job     job;
    private Date    startTime;
    private String  jobType;
    private String  backupLevel;
    private long    jobFiles;
    private long    jobBytes;
    private String  jobStatus;

    public ActivatedJob() {
    }


    public long getJobID() {
        return jobID;
    }

    public void setJobID(long jobID) {
        long oJobID = getJobID();
        this.jobID = jobID;
        firePropertyChange(PROPNAME_STARTTIME, oJobID, this.jobID);
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }        

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        Date oStartTime = getStartTime();
        this.startTime = startTime;
        firePropertyChange(PROPNAME_STARTTIME, oStartTime, this.startTime);
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        String oJobType = getJobType();
        this.jobType = jobType;
        firePropertyChange(PROPNAME_JOBTYPE, oJobType, this.jobType);
    }

    public String getBackupLevel() {
        return backupLevel;
    }

    public void setBackupLevel(String backupLevel) {
        String oBackupLevel = getBackupLevel();
        this.backupLevel = backupLevel;
        firePropertyChange(PROPNAME_BACKUPLEVEL, oBackupLevel, this.backupLevel);
    }

    public long getJobFiles() {
        return jobFiles;
    }

    public void setJobFiles(long jobFiles) {
        long oJobFiles = getJobFiles();
        this.jobFiles = jobFiles;
        firePropertyChange(PROPNAME_STARTTIME, oJobFiles, this.jobFiles);
    }

    public long getJobBytes() {
        return jobBytes;
    }

    public void setJobBytes(long jobBytes) {
        long oJobBytes = getJobBytes();
        this.jobBytes = jobBytes;
        firePropertyChange(PROPNAME_STARTTIME, oJobBytes, this.jobBytes);
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        String oJobStatus = getJobStatus();
        this.jobStatus = jobStatus;
        firePropertyChange(PROPNAME_STARTTIME, oJobStatus, this.jobStatus);
    }

    /** FIXME */
    public void initializeObject() {
    }
    
    /** FIXME */
    public void releaseObject() {
    }

}
