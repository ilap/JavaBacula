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
import com.jgoodies.binding.list.ArrayListModel;
import org.ilap.javabacula.util.NumberParser;

/**
 *
 * @author ilap
 */
public class PoolManagerModel  extends BaculaObjectManagerModel {
    
    /** Creates a new instance of PoolManagerModel */
    public PoolManagerModel(PoolManager PoolManager) {
        super(PoolManager);
        initModel();
    }
    

    private void initModel() {        
    }

    protected ArrayListModel parseShowCommand(String result) {
        Pool pool = null;
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
            if (line.startsWith("Pool:")) {
                otherToken = false;
                line = line.replaceAll("^Pool:[ ]+", "");
                pool = (Pool) this.getBaculaManager().createBaculaItem();

                lm.add(pool);
            } else if (line.startsWith("  -->")) {
                otherToken = true;
                continue;
            }
            
            if ((pool !=null) && (!otherToken)) {
                String[] tokens = parseLine(line, "=");
                for (int i=0; i<tokens.length; i+=2) {
                    // isEven
                    if ((i%2 == 0)) {
                        if (tokens[i].equals("name")) {
                            pool.setName(tokens[i+1]);
                        } else if (tokens[i].equals("PoolType")) {
                            pool.setPoolType(tokens[i+1]);
                        } else if (tokens[i].equals("use_cat")) {
                            pool.setUseCat(tokens[i+1] == "1");
                        } else if (tokens[i].equals("use_once")) {
                            pool.setUseOnce(tokens[i+1] == "1");
                        } else if (tokens[i].equals("cat_files")) {
                            pool.setCatFiles(NumberParser.parseLong (tokens[i+1]));
                        } else if (tokens[i].equals("max_vols")) {
                            pool.setMaxVols(NumberParser.parseLong (tokens[i+1]));
                        } else if (tokens[i].equals("auto_prune")) {
                            pool.setAutoPrune(tokens[i+1] == "1");
                        } else if (tokens[i].equals("VolRetention")) {
                            pool.setVolRetention(tokens[i+1]);
                        } else if (tokens[i].equals("VolUse")) {
                            pool.setVolUse(tokens[i+1]);
                        } else if (tokens[i].equals("Recycle")) {
                            pool.setRecycle(tokens[i+1] == "1");
                        } else if (tokens[i].equals("LabelFormat")) {
                            pool.setLabelFormat(tokens[i+1]);
                        } else if (tokens[i].equals("CleaningPrefix")) {
                            pool.setCleaningPrefix(tokens[i+1]);
                        } else if (tokens[i].equals("LabelType")) {
                            pool.setLabelType(new Integer (tokens[i+1]));
                        } else if (tokens[i].equals("RecycleOldest")) {
                            pool.setRecycleOldest(tokens[i+1] == "1");
                        } else if (tokens[i].equals("PurgeOldest")) {
                            pool.setPurgeOldest(tokens[i+1] == "1");
                        } else if (tokens[i].equals("MaxVolJobs")) {
                            pool.setMaxVolJobs(NumberParser.parseLong (tokens[i+1]));
                        } else if (tokens[i].equals("MaxVolFiles")) {
                            pool.setMaxVolFiles(NumberParser.parseLong (tokens[i+1]));
                        } else if (tokens[i].equals("MigTime")) {
                            pool.setMigTime(tokens[i+1]);
                        } else if (tokens[i].equals("MigHiBytes")) {
                            pool.setMigHiBytes(NumberParser.parseLong (tokens[i+1]));
                        } else if (tokens[i].equals("MigLoBytes")) {
                            pool.setMigLoBytes(NumberParser.parseLong (tokens[i+1]));
                        }
                    } // Even or odd
                } // for tokens...
            }
        }
        return lm;
    }

    public void startModel() {
        reloadByCommand("show pool");
    }
           
    public void stopModel() {
        // Do nothing;
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
