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

import java.util.StringTokenizer;
import java.text.ParseException;
import java.util.Date;
import java.text.*;
import org.ilap.javabacula.util.NumberParser;

import com.jgoodies.binding.list.ArrayListModel;

/**
 *
 * @author ilap
 */
public class ActivatedJobManagerModel extends BaculaObjectManagerModel {
    private volatile ActivatedJobMonitor jobMonitor;
    private Thread thread;
    private String[] fields;

    /** Creates a new instance of ActivatedJobManagerModel 
     * @param activatedJobManager 
     */
    public ActivatedJobManagerModel(ActivatedJobManager activatedJobManager) {
        super(activatedJobManager);
        jobMonitor = new ActivatedJobMonitor();
    }
        
    protected synchronized Job getJob(String idx) {
        ArrayListModel jobs = (ArrayListModel) JobManager.getInstance().getManagedObjects();

        Job job = null;
        Job tjob = null;
        for (int i=0; i < jobs.getSize(); i++) {
            tjob = (Job) jobs.getElementAt(i);
            if (tjob.getName().equals(idx)) {
                job = tjob;
            }
        }
        return job;
    }
    
        
    /*
     *MediaId | VolumeName | VolStatus | Enabled | VolBytes | VolFiles | VolRetention | Recycle | Slot | InChanger | MediaType | LastWritten
     */
    protected ArrayListModel parseShowCommand(String result) {
        boolean isHeader = true;
        ActivatedJob activatedJob = null;
        String line;        
        ArrayListModel lm = new ArrayListModel();
        Job job = null;
        
        // Return an empty list
        if (result == null) {
            return lm;
        }   
        
        isHeader = true;
        String jobName;
        
        StringTokenizer st = new StringTokenizer(result, "\n");
        while (st.hasMoreTokens()) {            
            line = st.nextToken();
            if (line.startsWith("+")) { // separator
                continue;
            } else if (line.startsWith("|")) {
                if (isHeader) { // Parse fileds
                    isHeader = false;
                    if (fields == null) {
                        fields = parseTableLine(line, "|");
                    }
                } else { // Parse rows
                    //JobId | Name | StartTime| Type | Level | JobFiles | JobBytes | JobStatus |    

                    String[] tokens = parseTableLine(line, "|");

                    activatedJob = (ActivatedJob) this.getBaculaManager().createBaculaItem();

                    activatedJob.setJobID(NumberParser.parseLong(tokens[0].trim()));
                    jobName = tokens[1].trim();
                    activatedJob.setName(jobName);
                    job = getJob(jobName);
                    activatedJob.setJob(job);
                    Date date = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = sdf.parse(tokens[2].trim()); 
                    } catch(ParseException pe) {
                        
                    }
                    activatedJob.setStartTime(date);

                    activatedJob.setJobType(tokens[3].trim());
                    activatedJob.setBackupLevel(tokens[4].trim());
                    activatedJob.setJobFiles(NumberParser.parseLong(tokens[5].trim()));
                    activatedJob.setJobBytes(NumberParser.parseLong(tokens[6].trim()));
                    activatedJob.setJobStatus(tokens[7].trim());
                    lm.add(activatedJob);
                }
            }
        }
        return lm;
    }
    
    public void startModel() {
        synchronized(jobMonitor) {
            if (!jobMonitor.isAlive()) {
                jobMonitor.startMonitor();
            }
        }
    }

    public void stopModel() {
        if (!jobMonitor.isAlive()) {
            return;
        }
        jobMonitor.requestStop();
    }

    public int compare(Object one, Object two) {
        long id = ((ActivatedJob)two).getJobID();
        int result;
        
        if (((ActivatedJob)one).getJobID() == id) {
            result = 0;
            updateObject(one, two);
        } else {
            result = 1;
        }
        return result;
    }

    private void updateObject(Object oldOne, Object newOne) {
        ActivatedJob oldJob = (ActivatedJob) oldOne;
        ActivatedJob newJob = (ActivatedJob) newOne;
        oldJob.setStartTime(newJob.getStartTime());
        oldJob.setJobType(newJob.getJobType());                
        oldJob.setBackupLevel(newJob.getBackupLevel());                
        oldJob.setJobFiles(newJob.getJobFiles());                
        oldJob.setJobBytes(newJob.getJobBytes());                
        oldJob.setJobStatus(newJob.getJobStatus());                
     }

    public void doNew() {}    
    public void doEdit() {}
    public void doDelete() {}
    public void doRefresh() {}
    
    
    /**
     *
     * @author ilap
     */
    public class ActivatedJobMonitor extends Monitor {
    
        public ActivatedJobMonitor() {
            super(5);
        }

        public void handleSession() {
            String result ;
            result = getConnection().getCommandResult("list jobs");
            reloadByData(result);
        }
    }
}
