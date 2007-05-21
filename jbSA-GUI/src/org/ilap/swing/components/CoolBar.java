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

import org.jdesktop.swingx.JXPanel;
import com.jgoodies.looks.plastic.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ilap
 */
public class CoolBar extends JXPanel {
    
    /**
     * The begin and end color of the gradient
     */
    private Color _beginColor = Color.white;
    private Color _endColor = UIManager.getColor("control");

    /** 
     * Create a new instance of GrandientMenuBar 
     */
    public CoolBar() {
	super();
    }

    /**
     * Create a CoolMenuBar with the given begin and end colors.
     * 
     * 
     * @param beginColor The begin color of gradient.
     * @param endColor The end color of gradient.
     */
    public CoolBar(Color beginColor, Color endColor) {
        super();
	_beginColor = beginColor;
	_endColor   = endColor;
    }
    public void beautifyComponent() {
        // TODO
    }
    /**
     * Get the begin color of the gradient
     */
    public Color getBeginColor() {
	return _beginColor;
    }

    /**
     * Set the begin color.
     *
     * @param color The color to use
     */
    public void setBeginColor(Color color) {
	
        Color oldColor = _beginColor;
        _beginColor = color;
	
        super.firePropertyChange("beginColor", oldColor, _beginColor);
	repaint();
    }

    /**
     * Get the end color of the gradient.
     */
    public Color getEndColor() {
        return _endColor;
    }
   
    /**
     * Set the end color to use.
     *
     * @param color The color to use.
     */
    public void setEndColor(Color color) {
        
        Color oldColor = _endColor;
        _endColor = color;
        
	super.firePropertyChange("endColor", oldColor, _endColor);
	repaint();
    }

        
    public void paintComponent(Graphics g) {

        Dimension dim = getSize();
	Insets inset = getInsets();
	Graphics2D g2D = (Graphics2D) g;

        int w = dim.width - (inset.left + inset.right);
	int h = dim.height - (inset.top + inset.bottom);
        int x = inset.left;
        int y = inset.top;
        
	g2D.setPaint(new GradientPaint(0, y, _beginColor, 0, dim.height, _endColor));
	g2D.fillRect(x, y-1, w, h+1);
    }
}
