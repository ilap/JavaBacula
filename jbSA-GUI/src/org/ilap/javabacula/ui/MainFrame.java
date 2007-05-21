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

package org.ilap.javabacula.ui;
// ***  FIXME ***

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Paint;
import java.awt.BorderLayout;
import java.awt.GradientPaint;

import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXPanel;

import javax.swing.Action;
/**
 *
 * @author ilap
 */
public class MainFrame extends JXPanel {

    private HeaderPanel     headerPanel;
    
    public MainFrame() {
        this(null, new String("MainFrame"));
    }

    /**
     * Constructs a <code>SimpleInternalFrame</code> with the specified title.
     * 
     * @param title       the initial title
     */
    public MainFrame(String title) {
        this(null, title);
    }
    
    /**
     * Constructs a <code>SimpleInternalFrame</code> with the specified 
     * icon, and title.
     * 
     * @param icon        the initial icon
     * @param title       the initial title
     */
    public MainFrame(Icon icon, String title) {        
        super(new BorderLayout());
        headerPanel = new HeaderPanel(null, title);
        add(headerPanel, BorderLayout.NORTH);
        setBorder( new LineBorder(new Color(160, 160, 160), 1, true));
        //headerPanel.get
    }

    /**
     * Returns the frame's icon.
     * 
     * @return the frame's icon
     */
    public Icon getFrameIcon() {
        // **FIXME*** return titleLabel.getIcon();
        if (headerPanel != null) {
            return headerPanel.getIcon();
        } else {
            return null;
        }
    }
    
    /**
     * Sets a new frame icon.
     * 
     * @param newIcon   the icon to be set
     */
    public void setFrameIcon(Icon icon) {
        if (headerPanel != null) {
            Icon oicon = getFrameIcon();
            headerPanel.setIcon(icon);
            firePropertyChange("icon", oicon, icon);
        }    
    }
    
    /**
     * Returns the frame's title text.
     * 
     * @return String   the current title text
     */
    public String getText() {
        if (headerPanel != null) {
            return headerPanel.getText();
        } else {
            return null;
        }
    }
    
    
    /**
     * Sets a new title text.
     * 
     * @param newText  the title text tp be set
     */
    public void setTitle(String text) {
        String otext = getText();
        headerPanel.setText(text);
        firePropertyChange("title", otext, text);
    }
    
    /**
     * Returns the content - null, if none has been set.
     * 
     * @return the current content
     */
    public Component getContent() {
        return hasContent() ? getComponent(1) : null;
    }
    
    
    /**
     * Sets a new panel content; replaces any existing content, if existing.
     * 
     * @param newContent   the panel's new content
     */
    public void setContent(Component newContent) {
        Component oldContent = getContent();
        if (hasContent()) {
            remove(oldContent);
        }
        add(newContent, BorderLayout.CENTER);
        firePropertyChange("FrameContent", oldContent, newContent);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isOpaque()) {
            return;
        }
        Color control = UIManager.getColor("control");
        int width  = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;

        Paint storedPaint = g2.getPaint();
        g2.setPaint(new GradientPaint(0, 0, Color.white, width, height, control));
        g2.fillRect(0, 0, width, height);
        g2.setPaint(storedPaint);
    }    


    /**
     * Updates the UI. In addition to the superclass behavior, we need
     * to update the header component.
     */
    public void updateUI() {
        super.updateUI();
        // **FIXME*** if (titleLabel != null) {
        // **FIXME***     updateHeader();
        // **FIXME*** }
    }

    public void setButtonAction(int id, Action a) {
        headerPanel.setButtonAction(id, a);
    }

    // Helper Code **********************************************************

    /**
     * Checks and answers if the panel has a content component set.
     * 
     * @return true if the panel has a content, false if it's empty
     */
    private boolean hasContent() {
        return getComponentCount() > 1;
    }
    
    /**
     * Determines and answers the header's text foreground color.
     * Tries to lookup a special color from the L&amp;F.
     * In case it is absent, it uses the standard internal frame forground.
     * 
     * @param selected   true to lookup the active color, false for the inactive
     * @return the color of the foreground text
     */
    protected Color getTextForeground(boolean selected) {
        Color c =
            UIManager.getColor(
                selected
                    ? "SimpleInternalFrame.activeTitleForeground"
                    : "SimpleInternalFrame.inactiveTitleForeground");
        if (c != null) {
            return c;
        }
        return UIManager.getColor(
            selected 
                ? "InternalFrame.activeTitleForeground" 
                : "Label.foreground");

    }

    /**
     * Determines and answers the header's background color.
     * Tries to lookup a special color from the L&amp;F.
     * In case it is absent, it uses the standard internal frame background.
     * 
     * @return the color of the header's background
     */
    protected Color getHeaderBackground() {
        Color c =
            UIManager.getColor("SimpleInternalFrame.activeTitleBackground");
        if (c != null)
            return c;
       c = UIManager.getColor("InternalFrame.activeTitleGradient");
        return c != null
            ? c
            : UIManager.getColor("InternalFrame.activeTitleBackground");
    }
    
    public void setHeaderVisible(boolean visible) {
        headerPanel.setHeaderVisible(visible);
    }

    
}