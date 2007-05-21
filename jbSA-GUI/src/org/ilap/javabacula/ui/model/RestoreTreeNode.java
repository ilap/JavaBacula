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

package org.ilap.javabacula.ui.model;

import org.ilap.javabacula.ui.model.BaculaFile;
import org.ilap.javabacula.ui.model.IBaculaFileStore;
import org.ilap.javabacula.ui.*;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

import org.ilap.javabacula.network.*;
import org.ilap.javabacula.model.*;
/**
 *
 * @author ilap
 */
public class RestoreTreeNode extends DefaultMutableTreeNode {
    private boolean inProgress = false;
    private boolean loaded = false;
    private boolean refresh = false;
         
    // Implements the getChildItems from the store (View or Model)
    // store = RestoreView
    private IBaculaFileStore store;	
	
   /**
    * Creates a new instance of RestoreTreeNode
    */
    public RestoreTreeNode(IBaculaFileStore store, BaculaFile node) {
        super(node);
        this.store = store;
    }	
	
    public void setUserObject(BaculaFile userObject) {            
        super.setUserObject(userObject);
    }
	
    public BaculaFile getUserObject() {            
        return (BaculaFile) super.getUserObject();
    }   
    
    public int getChildCount() {   
        if(!loaded && !inProgress) {        
            loadChildren();            
        }        
        int result = super.getChildCount();	
        return  result;
    }
        
    private synchronized void loadChildren() {    
        if (loaded && !refresh) {
            return;
        }
        inProgress = true;        
        BaculaFile baculaFile = getUserObject();        
        Vector<BaculaFile> children = store.getChildItems(baculaFile);        
                
        if(children != null) {        
            for(int i = 0; i < children.size(); i++) {            
                BaculaFile child = children.elementAt(i);                
                add(new RestoreTreeNode(store, child));                
            }            
        }        
        loaded = true;
        inProgress = false;	
    }
        
    public boolean isLeaf() {    
        return !getUserObject().isDirectory();        
    }    
}
