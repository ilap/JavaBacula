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

import org.ilap.javabacula.util.BaculaConstants;

import java.util.Vector;

/**
 *
 * @author ilap
 */

public class ClientDaemon extends CommonDaemon {
    
    public static final String PROPNAME_JOBRETENTION   = "jobRetention";
    public static final String PROPNAME_FILERETENTION   = "fileRetention";
    public static final String PROPNAME_AUTOPRUNE       = "autoPrune";
    public static final String PROPNAME_CATALOG         = "catalog";
    
    private String jobRetention = new String();
    private String fileRetention = new String();
    private Boolean autoPrune = new Boolean(false);
    private String   catalog = new String();   
    
    private Vector objects = new Vector();


    public ClientDaemon() {
        super(BaculaConstants.CLIENT_TYPE);
    }    
  
    public void statusReceived(String status) {
        
    }

    public String getJobRetention() {
        return jobRetention;
    }

    public void setJobRetention(String jobRetention) {
        String oJobRetention = getJobRetention();
        this.jobRetention = jobRetention;
        firePropertyChange(PROPNAME_JOBRETENTION, oJobRetention, this.jobRetention);
    }

    public String getFileRetention() {
        return fileRetention;
    }

    public void setFileRetention(String fileRetention) {
        String oFileRetention = getFileRetention();
        this.fileRetention = fileRetention;
        firePropertyChange(PROPNAME_FILERETENTION, oFileRetention, this.fileRetention);
    }

    public boolean isAutoPrune() {
        return autoPrune;
    }

    public void setAutoPrune(boolean autoPrune) {
        Boolean oAutoPrune = isAutoPrune();
        this.autoPrune = autoPrune;
        firePropertyChange(PROPNAME_AUTOPRUNE, oAutoPrune, this.autoPrune);
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        String oCatalog = getCatalog();
        this.catalog = catalog;
        firePropertyChange(PROPNAME_CATALOG, oCatalog, this.catalog);
    }
}
