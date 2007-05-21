/*
 *  JavaBacula -- Java frontend of the Bacula® - The Network Backup Solution
 *  Copyright (C) 2007 by Pal DOROGI
 * 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by the
 *  Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will bfe useful,
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

import org.ilap.javabacula.network.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Date;
import java.text.*;
/**
 *
 * @author ilap
 */

public class BaculaFile {
    
    private String permission = "";
    private int  links = 0;    
    private String owner = "";
    private String group = "";
    private long size = 0;
    private BaculaFile parent;
    
    private final SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date mtime = null;


        
    private Mark mark = Mark.NOTMARKED;
    private String path = "";
    private String name = "/";
    
    private boolean directory = true;
    private BaculaConnection conn = null;

    private Vector<BaculaFile> children = null;

    public BaculaFile() {
    }
    
    public BaculaFile(String stream) {
        updateFields(stream);
    }
    
    public BaculaFile(BaculaFile parent, String stream) {
        this(stream);
        this.parent = parent;
    }
    

    /**
     * ----------,0,root,root,0,1970-01-01 01:00:00,+,/opt/Shares/
     */
    public synchronized void updateFields(String line) {
        String[] fields = line.split(",", 8);

        this.permission = fields[0];
        this.links      = new Integer(fields[1]);
        this.owner      = fields[2];
        this.group      = fields[3];
        this.size       = new Long(fields[4]);
        try {
            this.setMtime(simpleDateFormat.parse(fields[5]));
        } catch(ParseException pe) {
            this.setMtime(null);
        }
        
        this.mark = Mark.NOTMARKED;
        if (fields[6].equals("*")) {
            this.mark = Mark.FULLMARKED;
        } else if (fields[6].equals("+")) {
            this.mark = Mark.PARTMARKED;            
        }
        this.directory = fields[7].endsWith("/");
        setName(fields[7]);
    }

    private synchronized void setName(String fullPath) {

        if (fullPath.equals("/")) {
            return;
        }
        
        if (isDirectory() && !fullPath.equals("/")) {
            fullPath = fullPath.replaceFirst("/$", "");
        }

        name = fullPath.replaceAll(".*/","");
        path = fullPath.substring(0, fullPath.length() - name.length());
    }
    
    public String getPath() {
        return path;        
    }
    
    public void setPath(String path) {
        this.path = path;
    }
        
    public String getName() {
        return name;
    }
    
    public long getSize() {
        return size;
    }

    public Vector getChildren() {
        return children;
    }

    public synchronized void setChildren(Vector vector) {
        children = vector;
    }

    public boolean isDirectory() {
        return directory;
    }
    
    public synchronized void setMarked(Mark mark) {
        this.mark = mark;
    }

    public synchronized Mark getMarked() {
        return mark;
    }

    public synchronized void markParent() {
        Mark tmark = getAllChildrenMarks();
        setMarked(tmark);
        if (parent == null) {
            return;
        }        
        parent.markParent();
    }
    
    private Mark getAllChildrenMarks() {
        boolean haveFull, havePart, haveNot;
        haveFull = false;
        havePart = false;
        haveNot = false;
        
        Mark mark;
        
        mark = Mark.NOTMARKED;
        for (Object o: children) {
            mark = ((BaculaFile)o).getMarked();
            switch(mark) {
                case PARTMARKED:
                    havePart = true;
                    break;
                case FULLMARKED:
                    haveFull = true;
                    break;
                default :
                    haveNot = true;                    
            }
        }
        
        if (!haveNot && !havePart && haveFull) {
            mark = Mark.FULLMARKED;
        } else if (haveNot && !havePart && !haveFull) {
            mark = Mark.NOTMARKED;            
        } else {
            mark = Mark.PARTMARKED;
        }
        return mark;
    }
    
    public synchronized void markChildrens(boolean mark) {
        if (mark) {
            markAllChildren(Mark.FULLMARKED);
            if (parent != null) {
                parent.markParent();                        
            }
        } else {
            markAllChildren(Mark.NOTMARKED);            
            if (parent != null) {
                parent.markParent();                        
            }
        }
        
    }
    
    private synchronized void markAllChildren(Mark mark) {
        setMarked(mark);        
        if (children == null) {
            return;
        }
        
        for (Object o: children) {
            ((BaculaFile)o).markAllChildren(mark);
        }
    }

    public boolean isMarked() {
        return mark == Mark.FULLMARKED;
    }
    
    public String toString() { return name; }
    public int getChildCount() { return children.size(); }
    
    public BaculaFile getChildAt(int i) {
        return (BaculaFile)children.elementAt(i);
    }
    
    public int getIndexOfChild(BaculaFile kid) {
        return children.indexOf(kid);
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public BaculaFile getParent() {
        return parent;
    }
    
    /**
     * 
     */
    public enum Mark {
        NOTMARKED,
        PARTMARKED,
        FULLMARKED;
    }
}

