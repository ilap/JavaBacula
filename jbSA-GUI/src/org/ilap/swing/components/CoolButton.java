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
import javax.swing.plaf.basic.BasicButtonUI;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.geom.RoundRectangle2D;
import org.jdesktop.swingx.painter.TextPainter;
import java.awt.geom.Point2D;
import com.jhlabs.image.ShadowFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.RGBAdjustFilter;
import com.jhlabs.image.BlurFilter;

import org.jdesktop.swingx.painter.ImageEffect;
        
import org.jdesktop.swingx.border.DropShadowBorder;
import org.ilap.swing.components.util.ShadowFactory;

import java.awt.image.PixelGrabber;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;
import java.awt.image.ConvolveOp;
import java.awt.image.AffineTransformOp;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class CoolButton extends JButton implements Serializable {

    private static final float ADISABLED = 0.25f;
    private static final float ANORMAL   = 0.8f;
    private static final float ASELECTED = 0.8f;
    private float _alpha    = ANORMAL;
    private boolean _colorsIsSwaped = false;
    
    private BufferedImage   _bimage;
    private BufferedImage   _brollover;
    private BufferedImage   _bclicked;
    
    private Icon _defaultIcon;

    public CoolButton() {
        super();
      //  ImageIcon img = new ImageIcon(getClass()
      //      .getResource("/org/ilap/swing.components.images/help.png"));
      //  setIcon(img);
        
        // Overwrite abstract methods for set the Alpha channel to buttons
        this.addMouseListener( new MouseListener(){
            public void mouseClicked    (MouseEvent e){}
            public void mouseExited     (MouseEvent e){ _alpha = ANORMAL;}
            public void mouseEntered    (MouseEvent e){ _alpha = ASELECTED;}
            public void mousePressed    (MouseEvent e){}
            public void mouseReleased   (MouseEvent e){}
        });
        
        butifyComponent();
    }

        /**
     * Set the begin color.
     *
     * @param swapit color The color to use
     */
    public void setSwapColor(boolean swapit) {
        
        BufferedImage timg;
        boolean  oldState = _colorsIsSwaped;
        _colorsIsSwaped = swapit;

        if (oldState != _colorsIsSwaped) {
            timg = _brollover;
            _brollover = _bclicked;
            _bclicked = timg;
        
            setRolloverIcon(new ImageIcon(_brollover));
            setPressedIcon(new ImageIcon(_bclicked));
        }
        
        super.firePropertyChange("swapColor", oldState, _colorsIsSwaped);
	repaint();
    }

    /**
     * Get the end color of the gradient.
     */
    public boolean getSwapColor() {
        return _colorsIsSwaped;
    }

    public void butifyComponent() {
        setIconTextGap(0);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setMargin(new Insets(0,0,0,0));

        setBorderPainted(false);
        setFocusPainted(false);
        setDoubleBuffered(true);
        setBorder(null);
        setFont(new java.awt.Font("Verdana", Font.PLAIN, 8));
        setForeground(Color.BLACK);
        //*** FIXME *** setText("init");
        setMinimumSize(new Dimension(45, 45));
        setPreferredSize(new Dimension(45, 45));
        setOpaque(false);
    }

    public static boolean hasAlpha(Image image) {
        
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            ;
        }
        return pg.getColorModel().hasAlpha();
    }
    
    
    public static BufferedImage createBufferedImage(ImageIcon image) {
        
        Image img   = image.getImage();
        int w       = img.getWidth(null);
        int h       = img.getHeight(null);
        
        BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        // Copy image to buffered image
        Graphics g = bimg.createGraphics();
    
        // Paint the image onto the buffered image  
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bimg;
    }

    
     public void setIcon(Icon defaultIcon) {
        
         if (defaultIcon != null) {
             _defaultIcon = defaultIcon;
         }
        // Create copies of image
        _bimage = createBufferedImage((ImageIcon)_defaultIcon);
        _brollover = createBufferedImage((ImageIcon)_defaultIcon);
        _bclicked = createBufferedImage((ImageIcon)_defaultIcon);

        GrayscaleFilter gray = new GrayscaleFilter();
        RGBAdjustFilter rgb = new RGBAdjustFilter();
        rgb.setRFactor(-0.2f);
        rgb.setGFactor(-0.2f);
        rgb.setBFactor(0.8f);

        // Create grayed normal icon
        _bimage = gray.filter(_brollover, _bimage);
        // Create blue colored rollover icon
        _brollover = rgb.filter(_bimage, _brollover);
        
        super.setIcon(new ImageIcon(_bimage));
        setRolloverIcon(new ImageIcon(_brollover));
        setPressedIcon(new ImageIcon(_bclicked));
       
        // Set the coolButtonUI to change background repaint
        setUI(new coolButtonUI());

    }

    /**
     * Create a Basic ButtonUI for overwrite the L&F settings
     */
    private static class coolButtonUI extends BasicButtonUI {
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
        }
    }
   
    /** 
     * Overwrite setEnabled fuction to change Alpha of buttons
     * while changing its state (rollover)
     */
    public void setEnabled(boolean value) {
        
        if (value) { 
            _alpha = ANORMAL;
        } else {
            _alpha = ADISABLED;   
        }
        super.setEnabled(value);
    }
    
    /** 
     * Overwrite paintComponent to antialising fonts and
     * set the Alpha channel to the coolButton intstances
     */
    public void paintComponent(Graphics g) {
        
        Graphics2D g2D = (Graphics2D)g.create();
        g2D.setRenderingHint(   RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        RoundRectangle2D.Float r2D = new RoundRectangle2D
                   .Float(0, 0, getWidth(), getHeight(), 20, 20);

        g2D.clip(r2D);
        g2D.setPaint(Color.GRAY);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _alpha));
        
        super.paintComponent (g2D);
    }
}
