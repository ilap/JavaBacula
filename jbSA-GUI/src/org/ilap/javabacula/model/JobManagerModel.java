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

import javax.swing.*;

import java.util.StringTokenizer;
import java.text.NumberFormat;
import java.text.ParseException;

import com.jgoodies.binding.list.ArrayListModel;
import org.ilap.javabacula.network.BaculaConnection;
import org.ilap.javabacula.network.ConnectionManager;
import org.ilap.javabacula.util.NumberParser;

/**
 *
 * @author ilap
 */
public class JobManagerModel  extends BaculaObjectManagerModel {
    
    /** Creates a new instance of JobManagerModel */
    public JobManagerModel(JobManager jobManager) {
        super(jobManager);
        initModel();
    }
    

    private void initModel() {        
    }

    protected ArrayListModel parseShowCommand(String result) {
        Job job = null;
        String line;
        ArrayListModel lm = new ArrayListModel();

        // Return an empty list
        if (result == null) {
            return lm;
        }
        
        boolean otherToken = false;
        StringTokenizer st = new StringTokenizer(result, "\n");
        while (st.hasMoreTokens()) {
            
            line = st.nextToken();
            if (line.startsWith("Job:")) {
                otherToken = false;
                line = line.replaceAll("^Job:[ ]+", "");
                job = (Job) this.getBaculaManager().createBaculaItem();

                lm.add(job);
            } else if (line.startsWith("  -->")) {
                otherToken = true;
                continue;
            }
            
            if ((job !=null) && (!otherToken)) {
                String[] tokens = parseLine(line, "=");
                for (int i=0; i<tokens.length; i+=2) {
                    // isEven
                    if ((i%2 == 0)) {
                        if (tokens[i].equals("name")) {
                            job.setName(tokens[i+1]);
                        } else if (tokens[i].equals("JobType")) {
                            job.setJobType(new Integer(tokens[i+1]));
                        } else if (tokens[i].equals("level")) {
                            job.setLevel(tokens[i+1]);
                        } else if (tokens[i].equals("Priority")) {
                            job.setPriority(new Integer(tokens[i+1]));
                        } else if (tokens[i].equals("Enabled")) {
                            job.setEnabled(tokens[i+1] == "1");
                        } else if (tokens[i].equals("MaxJobs")) {
                            job.setMaxJobs(NumberParser.parseLong(tokens[i+1]));
                        } else if (tokens[i].equals("Resched")) {
                            job.setReschedule(tokens[i+1] == "1");
                        } else if (tokens[i].equals("Times")) {
                            Number n;
                            int num;
                            try {
                                n = NumberFormat.getNumberInstance().parse(tokens[i+1]);
                                num = n.intValue();
                            } catch (ParseException pe) {
                                num = 0;
                            }
                            job.setTimes(num);
                        } else if (tokens[i].equals("Interval")) {
                            Number n;
                            int num;
                            try {
                                n = NumberFormat.getNumberInstance().parse(tokens[i+1]);
                                num = n.intValue();
                            } catch (ParseException pe) {
                                num = 0;
                            }
                            job.setInterval(num);
                        } else if (tokens[i].equals("Spool")) {
                            job.setSpool(tokens[i+1] == "1");
                        } else if (tokens[i].equals("WritePartAfterJob")) {
                            job.setWritePartAfterJob(tokens[i+1] == "1");
                        }
                    } // Even or odd
                } // for tokens...
            }
        }
        return lm;
    }

    
    public void loadData() {
        reloadByCommand("show job");
    }

    public void startModel() {
        loadData();
    }
            
    public void stopModel() {
    }

    public int compare(Object one, Object two) {
        String str = ((BaculaObject)two).getName();
        return ((BaculaObject)one).getName().equals(str)?0:1;
    }

    public void doNew() {
    }    
    
    public void doEdit() {
        String jobName = getSelectedItem().getName();
        String message = "Do you want run job (" + jobName + ") with default parameters?";
        int result = JOptionPane.showConfirmDialog(null, message, 
                                        "Run Job", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION) {
            return;
        }

        BaculaConnection conn = ConnectionManager.getInstance().reserveConnection();
        if (conn == null) {
            showDialog("No available connections. Try later.");
            return;
        }
        String command = "run job=\"" + jobName + "\" yes";
        String str = conn.getCommandResult(command);
    
        if (str != null) { 
            showDialog(str);
        }          
        ConnectionManager.getInstance().releaseConnection(conn);
    }
    
    public void doDelete() {}
    public void doRefresh() {}

    private  void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message,
                                        "Job - View",
                                        JOptionPane.INFORMATION_MESSAGE);
    }
}