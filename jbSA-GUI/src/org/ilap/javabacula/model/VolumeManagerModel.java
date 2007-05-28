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
import java.text.ParseException;
import java.util.Date;
import java.text.*;

//import javax.swing.L
/**
 *
 * @author ilap
 */
public class VolumeManagerModel extends BaculaObjectManagerModel {
    
    private boolean initialized = false;
    
   // private volatile Pool pool;
    private String[] fields;

    /** Creates a new instance of VolumeManagerModel 
     * @param volumeManager 
     */
    public VolumeManagerModel(VolumeManager volumeManager) {
        super(volumeManager);
        initModel();
    }
    
    private void initModel() {        
        if (!initialized) {
            startModel();
            initialized = true;            
        }
    }
    
    protected synchronized Pool getPool(String idx) {
        ArrayListModel pools = (ArrayListModel) PoolManager.getInstance().getManagedObjects();

        Pool pool = null;
        Pool tpool = null;
        for (int i=0; i < pools.getSize(); i++) {
            tpool = (Pool) pools.getElementAt(i);
            if (tpool.getName().equals(idx)) {
                pool = tpool;
            }
        }
        return pool;
    }
    
        
    /*
     *MediaId | VolumeName | VolStatus | Enabled | VolBytes | VolFiles | VolRetention | Recycle | Slot | InChanger | MediaType | LastWritten
     */
    protected ArrayListModel parseShowCommand(String result) {
        boolean isHeader = true;
        Volume volume = null;
        String line;        
        ArrayListModel lm = new ArrayListModel();
        Pool pool = null;
        
        // Return an empty list
        if (result == null) {
            return lm;
        }                
        
        StringTokenizer st = new StringTokenizer(result, "\n");
        while (st.hasMoreTokens()) {            
            line = st.nextToken();
            if (line.startsWith("Pool:")) {
                String poolName  = line.replaceAll("^Pool:[ ]+", "");
                pool = getPool(poolName);
                isHeader = true;
            } else if (line.startsWith("+")) { // separator
                continue;
            } else if (line.startsWith("|")) {
                if (isHeader) { // Parse fileds
                    isHeader = false;
                    if (fields == null) {
                        fields = parseTableLine(line, "|");
                    }
                } else { // Parse rows
                    String[] tokens = parseTableLine(line, "|");

                    volume = (Volume) this.getBaculaManager().createBaculaItem();

                    volume.setMediaId(new Long(tokens[0].trim()));
                    volume.setName(tokens[1].trim());
                    volume.setVolStatus(tokens[2].trim());
                    volume.setPool(pool);
                    volume.setEnabled(tokens[3].trim() == "1");
                    volume.setVolBytes(new Long(tokens[4].trim()));
                    volume.setVolFiles(new Long(tokens[5].trim()));
                    volume.setVolRetention(new Long(tokens[6].trim()));
                    volume.setRecycle(tokens[7].trim() == "1");
                    volume.setSlot(new Integer(tokens[8].trim()));
                    volume.setInChanger(tokens[9].trim() == "1");
                    volume.setMediaType(tokens[10].trim());
                    
                    Date date = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = sdf.parse(tokens[11].trim()); 
                    } catch(ParseException pe) {
                        
                    }
                    volume.setLastWritten(date);
                    lm.add(volume);
                }
            }
        }
        return lm;
    }
    
    public void startModel() {        
        reloadByCommand("list volumes");
    }
    
    public void stopModel() {
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
