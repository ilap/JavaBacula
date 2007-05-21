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
 * *prompt# show catalog
 *    Catalog: name=MyCatalog address=*None* DBport=0 db_name=bacula
 *     db_user=bacula MutliDBConn=0
 *
 * @author ilap
 */
public class CatalogManagerModel extends BaculaObjectManagerModel {
    
    /** Creates a new instance of CatalogManagerModel */
    public CatalogManagerModel(CatalogManager catalogManager) {
        super(catalogManager);
        initModel();
    }
    

    private void initModel() {        
    }    
    
    protected ArrayListModel parseShowCommand(String result) {
        Catalog cat = null;
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
            if (line.startsWith("Catalog:")) {
                otherToken = false;
                line = line.replaceAll("^Catalog:[ ]+", "");
                cat = (Catalog) this.getBaculaManager().createBaculaItem();

                lm.add(cat);
            } else if (line.startsWith("  --> Catalog:")) {
                otherToken = true;
                // Check if this catalog already exist on some manager
                continue;
            }
            
            if ((cat !=null) && (!otherToken)) {
                String[] tokens = parseLine(line, "=");
                for (int i=0; i<tokens.length; i+=2) {
                    // isEven
                    if ((i%2 == 0)) {
                        if (tokens[i].equals("name")) {
                            cat.setName(tokens[i+1]);
                        } else if (tokens[i].equals("address")) {
                            cat.setAddress(tokens[i+1]);
                        } else if (tokens[i].equals("DBport")) {
                            cat.setDbPort(new Integer(tokens[i+1]));
                        } else if (tokens[i].equals("db_name")) {
                            cat.setDbName(tokens[i+1]);
                        } else if (tokens[i].equals("db_user")) {
                            cat.setDbUser(tokens[i+1]);
                        } else if (tokens[i].equals("MultiDBCOnn")) {
                            cat.setMultiDbConn(tokens[i+1] == "1");
                        }
                    } // Even or odd
                } // for tokens...
            }
        }
        return lm;
    }


    public void startModel() {
        reloadByCommand("show catalog");
    }
    
    public void stopModel() {
        // Do nothing
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
