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

import org.ilap.javabacula.AbstractTreeModel;
import org.ilap.javabacula.ui.model.IBaculaFileStore;
import org.ilap.javabacula.ui.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.util.Vector;
import org.ilap.javabacula.ui.model.BaculaFile;
import org.ilap.javabacula.network.*;

/**
 *
 * @author ilap
 */
public class BaculaFileTreeModel extends AbstractTreeModel 
        implements Serializable {
    private BaculaFile root = null;
    
    
    public BaculaFileTreeModel() {
    }

    public BaculaFileTreeModel(IBaculaFileStore store, BaculaFile root) {
        this.root = root;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        BaculaFile directory = (BaculaFile)parent;
        Vector<BaculaFile> children = directory.getChildren();
        return children.get(index);
    }

    public int getChildCount(Object parent) {
        BaculaFile baculaFile = (BaculaFile)parent;
        if (baculaFile.isDirectory() ) {
            Vector<BaculaFile> children = baculaFile.getChildren();
            return children.size();
        } else {
            return 0;
        }
    }

    public boolean isLeaf(Object node) {
        return !((BaculaFile)node).isDirectory();
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        BaculaFile directory = (BaculaFile)parent;
        Vector<BaculaFile> children = directory.getChildren();
        return children.indexOf(child);
    }
}
