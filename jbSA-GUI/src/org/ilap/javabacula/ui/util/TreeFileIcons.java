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

package org.ilap.javabacula.ui.util;

import org.ilap.javabacula.ui.model.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import org.ilap.javabacula.ui.model.BaculaFile;
import org.ilap.javabacula.util.BaculaConstants;
import org.ilap.javabacula.ui.util.BaculaIcons;


/**
 *
 * @author ilap
 */
public class TreeFileIcons {
        static ImageIcon folder;
        static ImageIcon folderM;
        static ImageIcon folderP;
        static ImageIcon folderO;
        static ImageIcon folderOM;
        static ImageIcon folderOP;
        static ImageIcon file;
        static ImageIcon fileM;
        static ImageIcon fileP;
        
        static {
            folder      = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FOLDER);
            folderM     = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FOLDER_MARKED);
            folderP     = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FOLDER_PARTMARKED);
            folderO     = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FOLDER_OPEN);
            folderOM    = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FOLDER_OPEN_MARKED);
            folderOP   = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FOLDER_OPEN_PARTMARKED);
            file        = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FILE);
            fileM       = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FILE_MARKED);
            fileP      = (ImageIcon)BaculaIcons.getInstance()
                                .getIcon(BaculaConstants.ID_FILE_PARTMARKED);
            
        }
 
        // No more instances
        private TreeFileIcons() {
        }
 
        /**
         * 
         * @param baculaFile 
         * @param expanded 
         * @return 
         */
        public static Icon getTreeFileIcon(BaculaFile baculaFile, boolean expanded) {
            Icon icon;
            
            BaculaFile.Mark mark = baculaFile.getMarked();
            icon = null;
            
            switch (mark) {
                case PARTMARKED:
                if (baculaFile.isDirectory()) {
                    if (expanded) {
                        icon = folderOP;
                    } else {
                        icon = folderP;
                    }
                } else {
                    icon = fileP;
                }
                break;
                case FULLMARKED:
                    if (baculaFile.isDirectory()) {
                        if (expanded) {
                            icon = folderOM;
                        } else {
                            icon = folderM;
                        }
                    } else {
                        icon = fileM;
                    }
                    break;
                default:
                    if (baculaFile.isDirectory()) {
                        if (expanded) {
                            icon = folderO;
                        } else {
                            icon = folder;
                        }
                    } else {
                        icon = file;
                    }
            } //switch
            
            return icon;
        } // setFileIcon
        
    public static Icon getLeafIcon() {
        return file;
    }

    public static Icon getClosedIcon() {
        return folder;
    }
    
    public static Icon getOpenIcon() {
        return folderO;
    }
}
