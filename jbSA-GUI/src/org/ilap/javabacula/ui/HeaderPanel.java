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

import javax.swing.Action;
import javax.swing.Icon;

import org.jdesktop.swingx.JXPanel;

import org.ilap.swing.components.CoolButton;

/**
 *
 * @author  ilap
 */
public class HeaderPanel extends JXPanel {
    

    private Object[] _buttons = null;

    /**
     * Creates a new HeaderPanel
     */
    public HeaderPanel() {
        this(null, "HeaderPanel");
        headerToolBar.setVisible(false);
        updateUI();
    }
    
    /**
     * Creates a new header panel with an icon and title text
     * 
     * @param icon The header icon, it can be null or empty
     * @param text The header title, it can be null or empty
     */
    public HeaderPanel (Icon icon, String text) {
        initComponents();
        
        if (icon != null) {
            headerTitle.setIcon(icon);
        }
        if (text != null) {
            headerTitle.setText(text);        
        }
        
        _buttons = new Object[] { addButton, editButton, removeButton, refreshButton };

   }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new org.ilap.swing.components.SimpleGradientPanel();
        headerTitle = new javax.swing.JLabel();
        headerToolBar = new org.ilap.swing.components.CoolToolBar();
        addButton = new org.ilap.swing.components.CoolButton();
        editButton = new org.ilap.swing.components.CoolButton();
        removeButton = new org.ilap.swing.components.CoolButton();
        refreshButton = new org.ilap.swing.components.CoolButton();
        jSeparator2 = new javax.swing.JSeparator();

        setName("baculaManagementPanel"); // NOI18N

        headerPanel.setMaximumSize(new java.awt.Dimension(1200, 30));
        headerPanel.setMinimumSize(new java.awt.Dimension(100, 30));
        headerPanel.setOpaque(false);

        headerTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/bacmgmt_h22.png"))); // NOI18N
        headerTitle.setText("Director: bkpsrv");
        headerTitle.setMaximumSize(new java.awt.Dimension(150, 30));
        headerTitle.setMinimumSize(new java.awt.Dimension(90, 30));
        headerTitle.setPreferredSize(new java.awt.Dimension(150, 30));

        org.jdesktop.layout.GroupLayout headerPanelLayout = new org.jdesktop.layout.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(headerPanelLayout.createSequentialGroup()
                .add(5, 5, 5)
                .add(headerTitle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 216, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(312, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, headerPanelLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(headerTitle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        headerToolBar.setFloatable(false);
        headerToolBar.setRollover(true);
        headerToolBar.setDoubleBuffered(true);
        headerToolBar.setFocusable(false);
        headerToolBar.setMaximumSize(new java.awt.Dimension(2000, 24));
        headerToolBar.setMinimumSize(new java.awt.Dimension(95, 24));
        headerToolBar.setName("mainBar"); // NOI18N
        headerToolBar.setPreferredSize(new java.awt.Dimension(186, 24));
        headerToolBar.setRequestFocusEnabled(false);
        headerToolBar.setVerifyInputWhenFocusTarget(false);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/toolbar/stb_ok.png"))); // NOI18N
        addButton.setToolTipText("New");
        addButton.setMinimumSize(new java.awt.Dimension(22, 22));
        addButton.setSwapColor(true);
        headerToolBar.add(addButton);

        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/toolbar/stb_refresh.png"))); // NOI18N
        editButton.setToolTipText("Modify");
        editButton.setMinimumSize(new java.awt.Dimension(22, 22));
        editButton.setSwapColor(true);
        headerToolBar.add(editButton);

        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/toolbar/stb_cancel.png"))); // NOI18N
        removeButton.setToolTipText("Delete");
        removeButton.setMinimumSize(new java.awt.Dimension(22, 22));
        removeButton.setSwapColor(true);
        headerToolBar.add(removeButton);

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/toolbar/stb_refresh.png"))); // NOI18N
        refreshButton.setToolTipText("Refresh");
        refreshButton.setMaximumSize(new java.awt.Dimension(22, 22));
        refreshButton.setMinimumSize(new java.awt.Dimension(22, 22));
        refreshButton.setSwapColor(true);
        headerToolBar.add(refreshButton);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(5, 40));
        jSeparator2.setMinimumSize(new java.awt.Dimension(3, 20));
        headerToolBar.add(jSeparator2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(headerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(headerToolBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(headerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(headerToolBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Returns the HeaderPanel's icon.
     * 
     * @return the HeaderPanel's icon
     */
    public Icon getIcon() {
        return headerTitle.getIcon();
    }
    

    /**
     * Sets a new HeaderPanel icon.
     * 
     * @param icon   the icon to be set
     */
    public void setIcon(Icon icon) {
        Icon oicon = headerTitle.getIcon();
        headerTitle.setIcon(icon);
        firePropertyChange("HeaderIcon", oicon, icon);
    }
    

    /**
     * Returns the HeaderPanel's title text.
     * 
     * @return String   the current title text
     */
    public String getText() {
        return headerTitle.getText();
    }
    
    public void setHeaderVisible(boolean visible) {
        headerToolBar.setVisible(visible);
    }
    
    /**
     * Sets a HeaderPanel title text.
     * 
     * @param text  the HeaderPanel title text to be set
     */
    public void setText(String text) {
        String otext = headerTitle.getText();
        headerTitle.setText(text);
        firePropertyChange("HeaderText", otext, text);
    }    
    
    public void setButtonAction(int id, Action a) {    
        CoolButton btn = (CoolButton)_buttons[id];
        btn.setAction(a);
        btn.setText(null);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.ilap.swing.components.CoolButton addButton;
    private org.ilap.swing.components.CoolButton editButton;
    private org.ilap.swing.components.SimpleGradientPanel headerPanel;
    private javax.swing.JLabel headerTitle;
    private org.ilap.swing.components.CoolToolBar headerToolBar;
    private javax.swing.JSeparator jSeparator2;
    private org.ilap.swing.components.CoolButton refreshButton;
    private org.ilap.swing.components.CoolButton removeButton;
    // End of variables declaration//GEN-END:variables
}
