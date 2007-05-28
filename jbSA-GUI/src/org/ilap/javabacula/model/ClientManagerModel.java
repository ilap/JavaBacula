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
import org.ilap.javabacula.network.*;
import java.util.StringTokenizer;

import com.jgoodies.binding.list.ArrayListModel;


/**
 *
 * @author ilap
 */
public class ClientManagerModel extends BaculaObjectManagerModel {
    
    /** Creates a new instance of ClientManagerModel */
    public ClientManagerModel(ClientManager clientManager) {
        super(clientManager);
        initModel();
    }
    

    private void initModel() {        
    }
        
    protected ArrayListModel parseShowCommand(String result) {
        ClientDaemon cld = null;
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
            if (line.startsWith("Client:")) {
                otherToken = false;
                line = line.replaceAll("^Client:[ ]+", "");
                cld= (ClientDaemon) this.getBaculaManager().createBaculaItem();

                lm.add(cld);
            } else if (line.startsWith("  -->")) {
                otherToken = true;
                continue;
            }
            
            if ((cld !=null) && (!otherToken)) {
                String[] tokens = parseLine(line, "=");
                for (int i=0; i<tokens.length; i+=2) {
                    // isEven
                    if ((i%2 == 0)) {
                        if (tokens[i].equals("name")) {
                            cld.setName(tokens[i+1]);
                        } else if (tokens[i].equals("address")) {
                            cld.setAddress(tokens[i+1]);
                        } else if (tokens[i].equals("FDport")) {
                            cld.setPort(new Integer(tokens[i+1]));
                        } else if (tokens[i].equals("MaxJobs")) {
                            cld.setMaxJobs(new Long(tokens[i+1]));
                        } else if (tokens[i].equals("JobRetention")) {
                            cld.setJobRetention(tokens[i+1]);
                        } else if (tokens[i].equals("FileRetention")) {
                            cld.setFileRetention(tokens[i+1]);
                        } else if (tokens[i].equals("AutoPrune")) {
                            cld.setAutoPrune(tokens[i+1] == "1");
                        }
                    }
                }
            }
        }
        return lm;
    }

    public void startModel() {
        reloadByCommand("show clients");
    }

    public void stopModel() {
        // Do nothig;
    }
        
    public int compare(Object one, Object two) {
        String str = ((BaculaObject)two).getName();
        return ((BaculaObject)one).getName().equals(str)?0:1;
    }

    public void doNew() {}    
    public void doEdit() {}
    public void doDelete() {} 
    public void doRefresh() {}

}
