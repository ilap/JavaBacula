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


import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.jdesktop.swingx.JavaBean;
        
import org.jdesktop.swingx.border.DropShadowBorder;
import javax.swing.border.CompoundBorder;
//import org.ilap.swing.components.util.ShadowFactory;

public class CoolShadowPanel extends DropShadowPanel implements PropertyChangeListener {
    
    private Color _firstColor = Color.white;
    private Color _lastColor = UIManager.getColor("control");




    public CoolShadowPanel() {
        super();
 
        try {
            //setBorder(new DropShadowBorder());
            
            DropShadowBorder shadow =
            new DropShadowBorder(Color.BLACK, 0,5, .3f, 29,
                false, false, true, false);
            setBorder(new CompoundBorder(shadow, getBorder()));
            
        } catch (Exception e) {
            ;
        }
        
        butifyComponent();
    }

    /**
     * Create a CoolShadowPanel with the given start and end colors.
     *
     * @param firstColor The start color for the gradient.
     * @param lastColor   The end color for the gradient.
     */
    public CoolShadowPanel(Color firstColor, Color lastColor) {
        super();
	_firstColor = firstColor;
	_lastColor = lastColor;
    }
    
    
    /**
     * Get the last color of the gradient.
     */
    public Color getLastColor() {
        return _lastColor;
    }

    /**
     * Set the last color to use.
     *
     * @param color The color to use.
     */
    public void setLastColor(Color color) {
	Color oldColor = _lastColor;
	_lastColor = color;
	super.firePropertyChange("_lastColor", oldColor, _lastColor);
	repaint();
    }

    /**
     * Get the start color of the gradient
     */
    public Color getFirstColor() {
        return _firstColor;
    }

    /**
     * Set the starting color.
     *
     * @param color The color to use
     */
    public void setFirstColor(Color color) {
        Color oldColor = _firstColor;
	_firstColor = color;
	super.firePropertyChange("_firstColor", oldColor, _firstColor);
	repaint();
    }
    
    public void butifyComponent() {

    }

        
    public void paintComponent(Graphics g) {
 		Dimension dim = getSize();
		Graphics2D g2 = (Graphics2D) g;
		Insets inset = getInsets();
		int vWidth = dim.width - (inset.left + inset.right);
		int vHeight = dim.height - (inset.top + inset.bottom);

		// paintHorizontalGradient(g2, inset.left, inset.top, vWidth, vHeight, dim.width);
		paintVerticalGradient(g2, inset.left, inset.top, vWidth, vHeight, dim.height);
	}

	/**
	 * Paints a vertical gradient background from the start color to the end color.
	 */
	private void paintVerticalGradient(Graphics2D g2, int x, int y, int w, int h, int height)
	{
		g2.setPaint(new GradientPaint(0, y, _firstColor, 0, height, _lastColor));
		g2.fillRect(x, y, w, h);
	}

	/**
	 * Paints a horizontal gradient background from the start color to the end color.
	 */
	private void paintHorizontalGradient(Graphics2D g2, int x, int y, int w, int h, int width)
	{
		g2.setPaint(new GradientPaint(x, 0, _firstColor, width, 0, _lastColor));
		g2.fillRect(x, y, w, h);
	}
}

