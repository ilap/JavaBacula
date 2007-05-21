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

package org.ilap.javabacula.ui.views;

import java.io.*;
import java.util.*;
import java.text.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;

import org.ilap.javabacula.ui.*;
import org.ilap.javabacula.ui.util.*;
import org.ilap.javabacula.util.*;
import org.ilap.javabacula.network.*;
import org.ilap.javabacula.ui.model.*;
import org.ilap.javabacula.model.*;
import org.ilap.javabacula.ui.model.BaculaFile;
import org.ilap.javabacula.ui.model.BaculaFileTableModel;
import org.ilap.javabacula.ui.model.BaculaTableCellRenderer;
import org.ilap.javabacula.ui.model.IBaculaFileStore;
import org.ilap.javabacula.ui.model.RestoreTreeNode;
      
/**
 *
 * @author  ilap
 */
public class RestoreView extends BaculaView implements IBaculaFileStore {
    
    private boolean initialized = false;
    private boolean selectable    = false;

    // if in restore CLI or not
    private volatile boolean inRestoreMode   = false;
    private volatile boolean inPrompt   = false;

    private BaculaConnection connection;

    private SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private volatile boolean treeIsActiveComponent = true;
    /**
     * Creates new form RestoreView
     */
    public RestoreView() {
        initComponents();
        initializeView();
        setupTable();
    }
    
    public void initializeView() { 
        updateEnablement();
        if (initialized) {
            return; 
        }
                
        // Bind clients to Combobox
        SelectionInList sel = new SelectionInList(ClientManager.getInstance().getManagedObjects());                
        clientComboBox.setModel(new ComboBoxAdapter(sel));

        // Bind filesets to Combobox
        sel = new SelectionInList(FileSetManager.getInstance().getManagedObjects());                
        fileSetComboBox.setModel(new ComboBoxAdapter(sel));        
        

        // Initialize date spinner
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, -50);
        Date startDate = cal.getTime();
        cal.add(Calendar.YEAR, 100);
        Date endDate = cal.getTime();
        SpinnerModel dateModel = new SpinnerDateModel(now, startDate, endDate, Calendar.YEAR);

        timeSpinner.setModel(dateModel);
        
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerDateModel source = (SpinnerDateModel)e.getSource();
                System.out.println("The value is: " + source.getDate());
            }
        };
        
        dateModel.addChangeListener(listener);                

        // Setup tree & table
        // Setting default tree icons
        UIManager.put("Tree.leafIicon", TreeFileIcons.getLeafIcon());
        UIManager.put("Tree.closedIcon", TreeFileIcons.getClosedIcon());
        UIManager.put("Tree.openIcon", TreeFileIcons.getOpenIcon());

        restoreTree.setCellRenderer(new BaculaFileTreeCellRenderer());
        //restoreTree.setRootVisible(false);
        
        DefaultTreeModel treeModel = new DefaultTreeModel(new RestoreTreeNode(this, new BaculaFile()));
        restoreTree.setModel(treeModel);
        
         

        BaculaFile rootFile = (BaculaFile)((RestoreTreeNode)treeModel.getRoot()).getUserObject();
        BaculaFileTableModel tableModel = 
                new BaculaFileTableModel(rootFile);
        restoreTable.setModel(tableModel);
        restoreTable.getColumn(0).setCellRenderer(new BaculaTableCellRenderer());

        // Beautify table
        restoreTable.setShowVerticalLines(false);
        restoreTable.setIntercellSpacing(new Dimension(0, 2));
        restoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        restoreTable.getColumn("Size").setMaxWidth(60);
        restoreTable.getColumn("Size").setMinWidth(60);
        
        restoreTable.getSelectionModel().addListSelectionListener(restoreTable);
        
        // Update only one tree instance       
        restoreTree.addTreeSelectionListener(new BaculaFileTreeListener(tableModel));
        initialized = true;
   }
    
    public void setupTable() {
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                BaculaFile bf = null;
                int row = restoreTable.getSelectedRow();
                if (row >= 0) {
                    bf = (BaculaFile) restoreTable.getValueAt(row,0);
                }
                updateActiveComponent(restoreTable, bf);
            }
        };
        restoreTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new org.jdesktop.swingx.JXPanel();
        jSeparator1 = new javax.swing.JSeparator();
        relocateField = new javax.swing.JTextField();
        relocateLabel = new javax.swing.JLabel();
        replaceLabel = new javax.swing.JLabel();
        replaceComboBox = new javax.swing.JComboBox();
        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        coolShadowPanel1 = new org.ilap.swing.components.CoolShadowPanel();
        buildTreeButton = new org.ilap.swing.components.CoolButton();
        startRestoreButton = new org.ilap.swing.components.CoolButton();
        searchFilesButton = new org.ilap.swing.components.CoolButton();
        cancelProcessButton = new org.ilap.swing.components.CoolButton();
        jXPanel2 = new org.jdesktop.swingx.JXPanel();
        clientComboBox = new javax.swing.JComboBox();
        fileSetComboBox = new javax.swing.JComboBox();
        fileSetLabel = new javax.swing.JLabel();
        clientLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        uIFSplitPane1 = new com.jgoodies.uif_lite.component.UIFSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        restoreTree = new org.jdesktop.swingx.JXTree();
        jXPanel3 = new org.jdesktop.swingx.JXPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        restoreTable = new org.ilap.swing.components.CoolTable();
        markButton = new javax.swing.JButton();
        unmarkButton = new javax.swing.JButton();
        markAllButton = new javax.swing.JButton();
        restoreInfoLabel = new javax.swing.JLabel();
        unmarkAllButton = new javax.swing.JButton();
        timeSpinner = new javax.swing.JSpinner();

        buttonPanel.setBackground(new java.awt.Color(235, 235, 235));

        jSeparator1.setForeground(new java.awt.Color(210, 210, 210));

        relocateLabel.setText("Relocate to:");

        replaceLabel.setText("Replace policy:");

        replaceComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Allways", "If newer", "If older", "Never" }));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(relocateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(relocateField, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(replaceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(replaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(relocateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(replaceLabel)
                    .addComponent(relocateLabel)
                    .addComponent(replaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jXPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jXPanel1Layout = new javax.swing.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );
        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        buildTreeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/release_h22.png"))); // NOI18N
        buildTreeButton.setText("Build");
        buildTreeButton.setSwapColor(true);
        buildTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildTreeButtonActionPerformed(evt);
            }
        });

        startRestoreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/label_h22.png"))); // NOI18N
        startRestoreButton.setText("Start");
        startRestoreButton.setSwapColor(true);
        startRestoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startRestoreButtonActionPerformed(evt);
            }
        });

        searchFilesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/release_h22.png"))); // NOI18N
        searchFilesButton.setText("Search");
        searchFilesButton.setSwapColor(true);

        cancelProcessButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ilap/javabacula/ui/resources/icons/label_h22.png"))); // NOI18N
        cancelProcessButton.setText("Cancel");
        cancelProcessButton.setSwapColor(true);
        cancelProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelProcessButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout coolShadowPanel1Layout = new javax.swing.GroupLayout(coolShadowPanel1);
        coolShadowPanel1.setLayout(coolShadowPanel1Layout);
        coolShadowPanel1Layout.setHorizontalGroup(
            coolShadowPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coolShadowPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buildTreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchFilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startRestoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(394, Short.MAX_VALUE))
        );
        coolShadowPanel1Layout.setVerticalGroup(
            coolShadowPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coolShadowPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(searchFilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(startRestoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cancelProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(buildTreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jXPanel2.setBackground(new java.awt.Color(255, 255, 255));

        clientComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clientComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        fileSetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fileSetComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        fileSetLabel.setText("FileSet:");

        clientLabel.setText("Client:");

        timeLabel.setText("Before:");

        uIFSplitPane1.setBorder(null);
        uIFSplitPane1.setDividerLocation(175);
        uIFSplitPane1.setDividerSize(3);
        uIFSplitPane1.setMinimumSize(new java.awt.Dimension(175, 21));
        uIFSplitPane1.setOpaque(false);

        restoreTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restoreTreeMouseClicked(evt);
            }
        });
        restoreTree.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                restoreTreeFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(restoreTree);

        uIFSplitPane1.setLeftComponent(jScrollPane1);

        jXPanel3.setOpaque(false);

        restoreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        restoreTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                restoreTreeFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(restoreTable);

        markButton.setText("Mark");
        markButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markButtonActionPerformed(evt);
            }
        });

        unmarkButton.setText("Unmark");
        unmarkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markButtonActionPerformed(evt);
            }
        });

        markAllButton.setText("Mark All");
        markAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markButtonActionPerformed(evt);
            }
        });

        restoreInfoLabel.setText("0 files selected; 0 bytes");

        unmarkAllButton.setText("Unmark All");
        unmarkAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXPanel3Layout = new javax.swing.GroupLayout(jXPanel3);
        jXPanel3.setLayout(jXPanel3Layout);
        jXPanel3Layout.setHorizontalGroup(
            jXPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel3Layout.createSequentialGroup()
                .addComponent(markButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unmarkButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(markAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unmarkAllButton)
                .addContainerGap(159, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
            .addGroup(jXPanel3Layout.createSequentialGroup()
                .addComponent(restoreInfoLabel)
                .addContainerGap())
        );
        jXPanel3Layout.setVerticalGroup(
            jXPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(markButton)
                    .addComponent(markAllButton)
                    .addComponent(unmarkAllButton)
                    .addComponent(unmarkButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(restoreInfoLabel)
                .addGap(7, 7, 7))
        );

        uIFSplitPane1.setRightComponent(jXPanel3);

        timeSpinner.setMaximumSize(new java.awt.Dimension(140, 22));
        timeSpinner.setMinimumSize(new java.awt.Dimension(140, 22));
        timeSpinner.setPreferredSize(new java.awt.Dimension(140, 22));

        javax.swing.GroupLayout jXPanel2Layout = new javax.swing.GroupLayout(jXPanel2);
        jXPanel2.setLayout(jXPanel2Layout);
        jXPanel2Layout.setHorizontalGroup(
            jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientLabel)
                .addGap(1, 1, 1)
                .addComponent(clientComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileSetLabel)
                .addGap(2, 2, 2)
                .addComponent(fileSetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeLabel)
                .addGap(4, 4, 4)
                .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addComponent(uIFSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );
        jXPanel2Layout.setVerticalGroup(
            jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientLabel)
                    .addComponent(fileSetLabel)
                    .addComponent(fileSetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeLabel)
                    .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uIFSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(coolShadowPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(coolShadowPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
                        .addComponent(jXPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void startRestoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startRestoreButtonActionPerformed
    if (!isInRestoreMode()) {
        showDialog("We are not in the Restore Mode! Click to Cancel button to leave");
        return;
    }
    final String IDWHERE   = "9";
    final String IDREPLACE = "10";
    
    String command, result;
    
    command =  "done";        
    result = doCommand(command);
    
    if ((result == null) || (result.startsWith("No "))) {                    
        showDialog("No files/Directories to restore!");
        return;
    } if (result.startsWith("Bootstrap ")) {             
        setInPrompt(true);
        
        String STRID = relocateField.getText();
        if (!STRID.isEmpty()) {
            if (!STRID.startsWith("/")) { // Check prefix
                STRID = "/" + STRID;                
            }
            result = doCommand("mod");
            result = doCommand(IDWHERE);
            result = doCommand(STRID);
        }
        
        int ID = replaceComboBox.getSelectedIndex() + 1;
        if (ID != 1) { // Allways            
            result = doCommand("mod");
            result = doCommand(IDREPLACE);
            result = doCommand("" + ID);            
        }        
        result = doCommand("yes");
        if (result.startsWith("Job queued")) {
            setInPrompt(false);          
            restoreInfoLabel.setText("0 files selected; 0 bytes");
            showDialog(result);
        }
    }        

    setInRestoreMode(false);
    setSelectable(true);
}//GEN-LAST:event_startRestoreButtonActionPerformed

private void restoreTreeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_restoreTreeFocusGained
}//GEN-LAST:event_restoreTreeFocusGained

    private void markButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markButtonActionPerformed
        boolean mark;
        BaculaFile baculaFile = null;
        Object node;
        JButton jbtn = (JButton) evt.getSource();
                
        if ((jbtn == markAllButton) || (jbtn == unmarkAllButton)) {
            RestoreTreeNode treeNode = (RestoreTreeNode) restoreTree.getModel().getRoot();;
            baculaFile = (BaculaFile)treeNode.getUserObject();                
        } else if (treeIsActiveComponent) {
            TreePath path = restoreTree.getSelectionPath();
            if (path != null) {              
                node = path.getLastPathComponent();
                if ((node != null) && (node instanceof RestoreTreeNode)) {
                    RestoreTreeNode treeNode = (RestoreTreeNode) node;
                    baculaFile = (BaculaFile)treeNode.getUserObject(); 
                }
            }
            
        } else {
            baculaFile = (BaculaFile) restoreTable.getValueAt(restoreTable.getSelectedRow(),0);
        }
                          
        if (baculaFile == null) {
            System.out.println("RestoreView.markButtonActionPerformed: Bacula file is NULL");
            return;
        }

        doMark(baculaFile, !baculaFile.isMarked());    
        
        updateMarkButtonsEnablement(baculaFile);
        
        restoreTree.validate();
        restoreTree.repaint();
            
        restoreTable.validate();
        restoreTable.repaint();

    }//GEN-LAST:event_markButtonActionPerformed

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
// TODO add your handling code here:        
        //JComboBox cb = (JComboBox)evt.getSource();
        //BaculaObject bo = (BaculaObject)cb.getSelectedItem();
        BaculaObject o1, o2;
        String client;
        String fileset;
        o1 = (BaculaObject)clientComboBox.getSelectedItem();
        o2 = (BaculaObject)fileSetComboBox.getSelectedItem();
        
        if ((o1 != null) && (o2 != null)) {
            client = o1.toString();
            fileset = o2.toString();            
        } else {
            return;
        }
        
        String command = new String().format(".backups client=\"%s\" fileset=\"%s\"",
                client, fileset);
        System.out.println(command);
        
        String result = doCommand(command);
        boolean sel = result.startsWith("|");
        setSelectable(sel);
    }//GEN-LAST:event_comboBoxActionPerformed

    private void restoreTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restoreTreeMouseClicked
   /* Swing is littlebit buggy. Of course my application too.:)
        
        if (SwingUtilities.isLeftMouseButton(evt)) {
            String ipath = "NONE";
            String iname = "NONE";
            String imark = "NONE";
            //System.out.println("MOUSE CLICKED: ");        
//            JTree t = (JTree)evt.getSource();
            int x = evt.getX();
            int y = evt.getY();
            int row = restoreTree.getRowForLocation(x, y);
            TreePath  path = restoreTree.getPathForRow(row);

            //TreePath path = restoreTree.getSelectionPath();
            if (path != null) {
                
                Object node = path.getLastPathComponent();
                if ((node != null) && (node instanceof RestoreTreeNode)) {
                    RestoreTreeNode treeNode = (RestoreTreeNode) node;
                    BaculaFile baculaFile = (BaculaFile)treeNode.getUserObject();
                    ipath = baculaFile.getPath();
                    iname = baculaFile.getName();
                    imark = baculaFile.isMarked()?"TRUE":"FALSE";
                    if (evt.getClickCount() == 2) {
                        System.out.println("Mouse (double)clicked: " + ipath +
                            " NAME: " + iname + " MARKED: " +
                            imark);               //((DefaultTreeModel)t.getModel()).nodeStructureChanged((TreeNode)node);
                        //doMark(node);;
                    }
                }
            }           
        }*/
    }//GEN-LAST:event_restoreTreeMouseClicked

    private void cancelProcessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelProcessButtonActionPerformed
        if (isInRestoreMode()) {            
            String result = doCommand("quit");
            setInRestoreMode(!result.startsWith("Restore not done."));
            setSelectable(true);
        } else {
            setSelectable(false);           
        }
    }//GEN-LAST:event_cancelProcessButtonActionPerformed

    private void buildTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildTreeButtonActionPerformed
        String client, fileset, before;
        client = clientComboBox.getSelectedItem().toString();
        fileset = fileSetComboBox.getSelectedItem().toString();
        
        Date date = ((SpinnerDateModel) (timeSpinner.getModel())).getDate();
        before = simpleDateFormat.format(date);
        String command = new String().format("restore client=\"%s\" fileset=\"%s\" before=\"%s\" select",
                client, fileset, before);
        System.out.println(command);
        
        String result = doCommand(command);
        if ((result == null) || (result.startsWith("No "))) {            
            setInRestoreMode(false);
            setSelectable(false);
        } else {
            DefaultTreeModel treeModel = new DefaultTreeModel(new RestoreTreeNode(this, new BaculaFile()));
            restoreTree.setModel(treeModel);
            setInRestoreMode(true);            
            setSelectable(true);
        }        
    }//GEN-LAST:event_buildTreeButtonActionPerformed

    private void mainTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_mainTree1ValueChanged
    }//GEN-LAST:event_mainTree1ValueChanged

    private void mainTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_mainTreeValueChanged
    }//GEN-LAST:event_mainTreeValueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.ilap.swing.components.CoolButton buildTreeButton;
    private org.jdesktop.swingx.JXPanel buttonPanel;
    private org.ilap.swing.components.CoolButton cancelProcessButton;
    private javax.swing.JComboBox clientComboBox;
    private javax.swing.JLabel clientLabel;
    private org.ilap.swing.components.CoolShadowPanel coolShadowPanel1;
    private javax.swing.JComboBox fileSetComboBox;
    private javax.swing.JLabel fileSetLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    private org.jdesktop.swingx.JXPanel jXPanel2;
    private org.jdesktop.swingx.JXPanel jXPanel3;
    private javax.swing.JButton markAllButton;
    private javax.swing.JButton markButton;
    private javax.swing.JTextField relocateField;
    private javax.swing.JLabel relocateLabel;
    private javax.swing.JComboBox replaceComboBox;
    private javax.swing.JLabel replaceLabel;
    private javax.swing.JLabel restoreInfoLabel;
    private org.ilap.swing.components.CoolTable restoreTable;
    private org.jdesktop.swingx.JXTree restoreTree;
    private org.ilap.swing.components.CoolButton searchFilesButton;
    private org.ilap.swing.components.CoolButton startRestoreButton;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JSpinner timeSpinner;
    private com.jgoodies.uif_lite.component.UIFSplitPane uIFSplitPane1;
    private javax.swing.JButton unmarkAllButton;
    private javax.swing.JButton unmarkButton;
    // End of variables declaration//GEN-END:variables
    
    private synchronized void doMark(BaculaFile baculaFile, boolean mark) {
        String oldpwd, result, command;
        if (!isInRestoreMode() || (baculaFile == null)) {
            return;
        }
                
        command =  mark?"mark ":"unmark ";        

        oldpwd = doCommand("pwd");
        result = doCommand("cd " + baculaFile.getPath());  
        
        if (baculaFile.getParent() == null) {
            command += "*"; // root                         
        } else {
            command += baculaFile.getName();            
        }
        System.out.println("COMMADN: " + command);
        result = doCommand(command);
        baculaFile.markChildrens(mark);

        result = doCommand("estimate");        
        restoreInfoLabel.setText(result.replaceFirst("\\$ $", ""));
    }
    
    public void uninitializeView() {
        if (getConnection() != null) {
            if (isInRestoreMode()) {
                doCommand("quit");
            }
            ConnectionManager.getInstance().releaseConnection(getConnection());
            setConnection(null);
        }        
    }
    
    public void doOnEnter() {
        if (getConnection() == null) {
            setConnection(ConnectionManager.getInstance().reserveConnection());
        }
        
        if ((clientComboBox.getItemCount() > 0) && 
                (clientComboBox.getSelectedIndex() < 0)) {
            clientComboBox.setSelectedIndex(0);             
        }
        if ((fileSetComboBox.getItemCount() > 0) && 
                (fileSetComboBox.getSelectedIndex() < 0)) {
            fileSetComboBox.setSelectedIndex(0);            
        }
    }
    
    public void doOnLeave() {
    }

    public synchronized BaculaConnection getConnection() {
        return connection;
    }

    public  synchronized void setConnection(BaculaConnection connection) {
        this.connection = connection;
    }
    
    protected synchronized String getCommanResult(String command) {
        return connection.getCommandResult(command);
    }
    
    
    /**
     * TreeListener for change table datas
     */
    protected class BaculaFileTreeListener implements TreeSelectionListener {
        BaculaFileTableModel model;

        public BaculaFileTreeListener(BaculaFileTableModel model) {
            this.model = model;
        }
        
        public void valueChanged(TreeSelectionEvent evt) {
            RestoreTreeNode rtn = (RestoreTreeNode)evt.getPath().getLastPathComponent();
            BaculaFile  bf = (BaculaFile)rtn.getUserObject();
            if ( bf.isDirectory() ) {
                rtn.getChildCount();
                model.setDirectory(bf);
            }
            else {
                model.setDirectory(null);
            }
            updateActiveComponent(restoreTree, bf);
        }
    }
    
    public synchronized String doCommand(String command) {
        if (getConnection() == null) {
            return "";
        }
        return connection.getCommandResult(command);
    }

    public synchronized Vector getChildItems(Object parent) {
        BaculaFile bf = (BaculaFile) parent;
        if (!bf.isDirectory()) {
            return new Vector();
        }
        
        String command = "cd " + bf.getPath() + bf.getName();

        String result = doCommand(command);
        result = doCommand(".dir");
        System.out.println("RestoreView.getChildItem: " + command + "; .dir");
        String line;
        
        if ((result == null) || result.startsWith("Node")) {
            return new Vector();
        }
        result = result.replaceFirst("\\$ $", "");

        StringTokenizer st = new StringTokenizer(result, "\n");
        Vector<BaculaFile> tvector = bf.getChildren();
        Vector<BaculaFile> vector = null;
        
        if (tvector == null) {
            vector = new Vector(st.countTokens());
        } else {
            vector = tvector;
        }
        
        int count = 0;        
        while (st.hasMoreTokens()) {            
            line = st.nextToken();

            if (tvector == null) { // Initialize children            
                BaculaFile tbf = new BaculaFile(bf, line);
                if (tbf != null) {
                    vector.add(tbf);
                }
            } else { // refresh children            
                BaculaFile tbf = vector.get(count);
               // XXXXXXXXXX tbf.updateFields(line);
            }           
            count++;
        }
        bf.setChildren(vector);
        return vector;
    }              
    
    private synchronized void updateEnablement() {
        // Stage 1 (build button end others)
        this.buildTreeButton.setEnabled(!isInRestoreMode() && isSelectable());
        this.searchFilesButton.setEnabled(isInRestoreMode());
        this.startRestoreButton.setEnabled(isInRestoreMode());
        this.cancelProcessButton.setEnabled(isInRestoreMode() || isSelectable());
        
        //
        this.clientComboBox.setEnabled(!isInRestoreMode());
        this.fileSetComboBox.setEnabled(!isInRestoreMode());
        this.timeSpinner.setEnabled(!isInRestoreMode());
        
        this.restoreTree.setEnabled(isInRestoreMode());
        this.restoreTable.setEnabled(isInRestoreMode());
        this.markButton.setEnabled(false);
        this.unmarkButton.setEnabled(false);
        this.markAllButton.setEnabled(isInRestoreMode());
        this.unmarkAllButton.setEnabled(isInRestoreMode());
        
        this.relocateField.setEnabled(isInRestoreMode());
        this.replaceComboBox.setEnabled(isInRestoreMode());
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
        updateEnablement();
    }

    public boolean isInRestoreMode() {
        return inRestoreMode;
    }

    public void setInRestoreMode(boolean inRestoreMode) {
        this.inRestoreMode = inRestoreMode;
        updateEnablement();
    }

    public boolean isInPrompt() {
        return inPrompt;
    }

    public void setInPrompt(boolean inPrompt) {
        this.inPrompt = inPrompt;
    }
    
    private synchronized void updateActiveComponent(Object object, 
                                                        BaculaFile baculaFile) {
        treeIsActiveComponent =  object instanceof JTree;    
        updateMarkButtonsEnablement(baculaFile);
    }
    
    private synchronized void updateMarkButtonsEnablement(BaculaFile baculaFile) {
        RestoreTreeNode treeNode = (RestoreTreeNode) restoreTree.getModel().getRoot();;
        BaculaFile root = (BaculaFile)treeNode.getUserObject();                

        markAllButton.setEnabled(!root.isMarked());
        unmarkAllButton.setEnabled(!markAllButton.isEnabled());
        if (baculaFile == null) {
            return;
        }
        markButton.setEnabled(!baculaFile.isMarked());
        unmarkButton.setEnabled(!markButton.isEnabled());
    }            
    
    private  void showDialog(String message) {
        JOptionPane.showMessageDialog (this, message,
                                        "Restore - View",
                                        JOptionPane.INFORMATION_MESSAGE);
    }
}
