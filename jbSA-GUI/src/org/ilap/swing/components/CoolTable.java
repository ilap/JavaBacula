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

package org.ilap.swing.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.plaf.ComponentUI;
import org.jdesktop.swingx.JXTable;

import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
//import org.jdesktop.swingx.action.AbstractActionExt;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
//import org.jdesktop.swingx.decorator.Filter;
//import org.jdesktop.swingx.decorator.FilterPipeline;
//import org.jdesktop.swingx.decorator.Highlighter;
//import org.jdesktop.swingx.decorator.HighlighterPipeline;
//import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.RolloverHighlighter;
import org.jdesktop.swingx.decorator.PatternHighlighter;
//import org.jdesktop.swingx.decorator.Sorter;
import javax.swing.table.*;



/**
 *
 * @author ilap
 */
public class CoolTable extends JXTable {
    /**
     * The begin and end color of the gradient
     */
    private Color beginColor = Color.white;
    private Color endColor = UIManager.getColor("control");

    /** 
     * Create a new instance of CoolTabler 
     */
    public CoolTable() {
        
        super();
        tableHeader.setDefaultRenderer(new CoolTableHeaderRenderer());
  	
        this.beautifyComponent();
        
        Color floralWhiteBrighter = new Color(255, 250, 240);
        
        setHighlighters(new HighlighterPipeline(
            new Highlighter[]{ 
                 new AlternateRowHighlighter(Color.white, floralWhiteBrighter, null)
            }
        ));
        getHighlighters().addHighlighter(new RolloverHighlighter(floralWhiteBrighter.darker(), Color.WHITE ));
    }
    
    /**
     * Create a CoolTable with the given begin and end colors.
     * 
     * 
     * @param beginColor The begin color of gradient.
     * @param endColor The end color of gradient.
     */
    public CoolTable(Color beginColor, Color endColor) {
        super();
	this.beginColor = beginColor;
	this.endColor   = endColor;
    }
    
    
     /*public void setTableHeader(JTableHeader h) {
        //unconfigureEnclosingScrollPane();

        if (h != null) {
           ;// h.setDefaultRenderer(new CoolTableHeaderRenderer());
        }
        super.setTableHeader(h);

        //configureEnclosingScrollPane();
    }
*/

    public void beautifyComponent() {
        // TODO
        setColumnControlVisible(true);
        setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);//.AUTO_RESIZE_OFF);
        setRolloverEnabled(true);
        packTable(0);
    }
    
    /**
     * Get the begin color of the gradient
     */
    public Color getBeginColor() {
	return this.beginColor;
    }

    /**
     * Set the begin color.
     *
     * @param color The color to use
     */
    public void setBeginColor(Color color) {
	
        Color oldColor = this.beginColor;
        this.beginColor = color;
	
        super.firePropertyChange("beginColor", oldColor, this.beginColor);
	repaint();
    }

    /**
     * Get the end color of the gradient.
     */
    public Color getEndColor() {
        return this.endColor;
    }

    /**
     * Set the end color to use.
     *
     * @param color The color to use.
     */
    public void setEndColor(Color color) {
        
        Color oldColor = this.endColor;
        this.endColor = color;
        
	super.firePropertyChange("endColor", oldColor, this.endColor);
	repaint();
        
    }       
 
    /*public boolean getScrollableTracksViewportHeight() {
        
        return getPreferredSize().height < getParent().getHeight();
    }

    public boolean getScrollableTracksViewportWidth() {
        return getPreferredSize().width < getParent().getWidth();
    }
    
    
    
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = super.getPreferredScrollableViewportSize();
        return new Dimension(Math.min(getPreferredSize().width, size.width), size.height);
    } */       
     
}
