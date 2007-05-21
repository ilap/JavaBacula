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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.table.TableCellRenderer;

/**
 */
public class CoolTableHeaderRenderer extends JComponent implements TableCellRenderer {    
    	/**
	 * The start color of the gradient
	 */
	private Color startColor = Color.white;

	/**
	 * The end color of the gradient
	 */
	private Color endColor = UIManager.getColor("control");

	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	protected JTable table;
	protected Object value;
	protected boolean isSelected;
	protected boolean hasFocus;
	protected int row;
	protected int column;
        private int justification;
                
        public CoolTableHeaderRenderer() {
            this.justification = LEFT;
        }

	public CoolTableHeaderRenderer(int justification) {
            this.justification = justification;
	}

	public void setJustification(int justification)	{
            this.justification = justification;
	}

        public int getJustification() {
            return this.justification;
	}

	public String getText(Object object) {
            return object.toString();
	}

       	public void paint(Graphics g) {
            
            Graphics2D g2 = (Graphics2D) g;
            Dimension dim = getSize();

            Insets inset = getInsets();
            int w = dim.width - (inset.left + inset.right);
            int h = dim.height - (inset.top + inset.bottom);

            // Paint background
            g2.setPaint(new GradientPaint(0, inset.top, startColor, 0, h, endColor));
            g2.fillRect(inset.left, inset.top, w, h);
                
            // Paint separator
            boolean isLast = column == table.getColumnCount() - 1;
            if (!isLast) {
            	g2.setColor(Color.BLACK);
		g2.drawLine(w-1, 4, w-1, h-4);
            }

            String text = getText(value);
            if (text != null) {
            	Rectangle2D rectangle = g.getFontMetrics()
                                                   .getStringBounds(text, g);

		int x;
		
                if (justification == LEFT) {
                    x = 5;
		} else	{
                    x = h -((int)rectangle.getWidth()+5);
		}
                        
		int y = h - (h-(int)rectangle.getHeight()) / 2 - 
                                               g.getFontMetrics().getDescent();

		g.setColor(Color.BLACK);
		g.drawString(text, x, y);
            }
	}

	public Dimension getPreferredSize() {
		Dimension dimension = super.getPreferredSize();
		dimension.height = getFont().getSize() + 6;
		return dimension;
	}

	public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
            this.table = table;
            this.value = value;
            this.isSelected = isSelected;
            this.hasFocus = hasFocus;
            this.row = row;
            this.column = column;

            setFont(table.getFont());

            return this;
	}

	public void validate() {}

	public void revalidate() {}

	protected void firePropertyChange(String propertyName, 
                                            Object oldValue, Object newValue) {}

	public void firePropertyChange(String propertyName, 
                                        boolean oldValue, boolean newValue) {}
}