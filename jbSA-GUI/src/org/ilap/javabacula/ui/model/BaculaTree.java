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

import org.ilap.javabacula.ui.model.SystemsTreeRenderer;
import org.ilap.javabacula.ui.model.BaculaTreeNode;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.Enumeration;

import org.ilap.javabacula.ui.util.BaculaIcons;

/**
 *
 * @author ilap
 */
public class BaculaTree {
    
    protected BaculaTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;

    /**
     * Creates a new instance of BaculaTree
     */
    public BaculaTree(JTree tree) {
        this.tree = tree;
        initializeTree();
    }
    
    public void setModel(TreeModel newModel) {
        tree.setModel(newModel);
    }
    
    public void initializeTree(Object root) {

        this.rootNode = new BaculaTreeNode(root);
        treeModel = new DefaultTreeModel(this.rootNode);
        setModel(treeModel);
        treeModel.nodeChanged(this.rootNode);
        tree.repaint();
    }

    private void initializeTree() {
        
        // Load icons to the Tree
        tree.putClientProperty("JTree.icons",       BaculaIcons.getInstance().getIcons());
        
        // Setting up the renderer of the Tree
        tree.setCellRenderer(new SystemsTreeRenderer());

        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
    }    

    
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
    
        if (currentSelection != null) {
            BaculaTreeNode currentNode = (BaculaTreeNode)
            (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
        
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }
    
    public BaculaTreeNode addObject(Object child) {
        BaculaTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath ==  null) {
            parentNode = rootNode;
        } else {
            parentNode = (BaculaTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public BaculaTreeNode addObject(BaculaTreeNode parent,
                                                Object child) {
        return addObject(parent, child, false);
    }

    public BaculaTreeNode addObject(BaculaTreeNode parent,
                        Object child, boolean shouldBeVisible) {

        BaculaTreeNode childNode = new BaculaTreeNode(child);

        if (parent ==  null) {
            parent = rootNode;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public JTree getTree() {
        return tree;
    }
    
    public void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
        expandAll(tree, new TreePath(root), expand);
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
   
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }      
}
