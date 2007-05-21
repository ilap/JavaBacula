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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JViewport;

import org.jdesktop.swingx.JXTable;

import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.RolloverHighlighter;


//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.TableColumnModelEvent;
//import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.JTableHeader;
//import javax.swing.table.TableCellEditor;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.Component;
import org.ilap.javabacula.util.BaculaConstants;

/**
 *
 * @author ilap
 */
public class JXCoolTable extends JXTable {
        public enum SongState {
            PLAYING, STOPPED, PAUSED, ERROR
        }
    
        //public JXCoolTable(DefaultTableModel model)  {
        public JXCoolTable() {

        //setModel(model);
        //setFieldNames(new String[]{"state", "title", "album", "artist", "rating", "genre", "url"});
        //this.set
        
        //setGridColor(new java.awt.Color(255, 255, 255));
        setShowGrid(false);
        //this.setS
        setIntercellSpacing(new java.awt.Dimension(0, 0));

        setOpaque(false);

        //setShowHorizontalLines(false);

        setShowVerticalLines(false);


        //setColumnControlVisible(true);
        
        setSortable(true);
        
        Color floralWhiteBrighter = new Color(235, 230, 220);

        setHighlighters(new HighlighterPipeline(
            new Highlighter[]{ 
                 new AlternateRowHighlighter(Color.white, floralWhiteBrighter, null)
                //new AlternateRowHighlighter(UIManager.getColor("Table.selectionBackground").brighter(), floralWhiteBrighter, null, true)
            }
        ));
        getHighlighters().addHighlighter(new RolloverHighlighter(floralWhiteBrighter.darker(), Color.WHITE ));
        setRolloverEnabled(true);


    }


   public void setColumnModel(javax.swing.table.TableColumnModel columnModel) {

        super.setColumnModel(columnModel);

        //attempt to attach the proper renderers
        //String a = columnModel.getColumn(1).getHeaderValue().toString();
        Integer a = columnModel.getColumnCount();
        System.out.println("CModell: " + a );
        try {

            if (columnModel.getColumnIndex("state") >= 0) {

                columnModel.getColumn(columnModel.getColumnIndex("state")).setCellRenderer(new PlayingRenderer());

            }

        } catch (Exception e) {

            //do nothing

        }

        try {

            if (columnModel.getColumnIndex("Humidity") >= 0) {

                columnModel.getColumn(columnModel.getColumnIndex("Humidity")).setCellRenderer(new RatingRenderer());

            }

        } catch (Exception e) {

            //do nothing

        }

        try {

            if (columnModel.getColumnIndex("url") >= 0) {

                columnModel.getColumn(columnModel.getColumnIndex("url")).setCellRenderer(new UrlRenderer());

            }

        } catch (Exception e) {

            //do nothing

        }

    }



    protected void configureEnclosingScrollPane() {

        super.configureEnclosingScrollPane();

        //make the viewport non-opaque

        Container p = getParent();

        if (p instanceof JViewport) {
           ((JViewport)p).setOpaque(true);
           ((JViewport)p).setBackground(Color.WHITE);
        }

    }

    

    public static class RatingRenderer extends DefaultTableCellRenderer {

        private ImageIcon starOn = new ImageIcon(getClass().getResource(BaculaConstants.ICONS + "director.png"));

        private ImageIcon starOff = new ImageIcon(getClass().getResource(BaculaConstants.ICONS + "director.png"));

        

        public Component getTableCellRendererComponent(JTable table, Object value,

                                                boolean isSelected, boolean hasFocus, 

                                                int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Integer rating;

            if (value == null) {

                rating = 0;

            } else {

                rating = (Integer) value;

            }

            label.setText(null);

            label.setIcon(getRatingImage(rating));

            return label;

        }



        private Icon getRatingImage(int rating) {

            int starWidth = starOn.getIconWidth();

            int starHeight = starOff.getIconHeight();

            rating /= 20;

            BufferedImage image = new BufferedImage(starWidth * 5, starHeight,

                                                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = image.createGraphics();

            for (int i = 0; i < rating; i++) {

                g2.drawImage(starOn.getImage(), i * starWidth, 0, null);

            }

            for (int i = rating; i < 5; i++) {

                g2.drawImage(starOff.getImage(), i * starWidth, 0, null);

            }

            return new ImageIcon(image);

        }

    }

    

    public static class UrlRenderer extends DefaultTableCellRenderer {        

        public Component getTableCellRendererComponent(JTable table, Object value,

                                                boolean isSelected, boolean hasFocus, 

                                                int row, int column) {

            //change the "value" to be the trimmed version of the text
            
            String path = "";
            
            if (value != null) {

                path = value.toString();

                path = path.substring(0, path.lastIndexOf("/"));

                path = path.substring(path.lastIndexOf("/") + 1);

                path = path.replaceAll("\\%20", " ");
                
            }

            JLabel label = (JLabel) super.getTableCellRendererComponent(table, path, isSelected, hasFocus, row, column);

            return label;

        }

    }

    

    public static class PlayingRenderer extends DefaultTableCellRenderer {

        private ImageIcon playingImg = new ImageIcon(getClass().getResource(BaculaConstants.ICONS + "director.png"));
        private ImageIcon pausedImg = new ImageIcon(getClass().getResource(BaculaConstants.ICONS + "director.png"));
        private ImageIcon errorImg = new ImageIcon(getClass().getResource(BaculaConstants.ICONS + "director.png"));

        

        public Component getTableCellRendererComponent(JTable table, Object value,

                                                boolean isSelected, boolean hasFocus, 

                                                int row, int column) {

            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            label.setText("");

            label.setIcon(null);

            if (value != null && value instanceof SongState) {

                if (value == SongState.PAUSED) {

                    label.setIcon(pausedImg);

                } else if (value == SongState.PLAYING) {

                    label.setIcon(playingImg);

                } else if (value == SongState.ERROR) {

                    label.setIcon(errorImg);

                }

            }

            label.setAlignmentX(0.5f);

            return label;

        }

    }    
}
