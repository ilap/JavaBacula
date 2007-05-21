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

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.ilap.javabacula.ui.util.TreeFileIcons;
/**
 *
 * @author ilap
 */
public class BaculaFileTreeCellRenderer extends DefaultTreeCellRenderer {
 
    public BaculaFileTreeCellRenderer() {
    }
 
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, 
                                                           leaf, row, hasFocus);
 
        if (value instanceof RestoreTreeNode) {
            Object obj = ((RestoreTreeNode) value).getUserObject();
            BaculaFile bf = (BaculaFile) obj;
            if (bf != null) {
                setIcon(TreeFileIcons.getTreeFileIcon(bf, expanded));
            } else {
                if (expanded) {                        
                    setIcon(TreeFileIcons.getOpenIcon());
                } else {
                    setIcon(TreeFileIcons.getClosedIcon());
                }
            }         
        }
        return this;
    }
}
