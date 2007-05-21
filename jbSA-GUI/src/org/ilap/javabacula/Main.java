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

package org.ilap.javabacula;

import java.io.*;
import java.util.Properties;
import java.awt.Color;

import javax.swing.*;         


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jgoodies.looks.plastic.*;
import com.jgoodies.looks.plastic.theme.*;

import org.ilap.javabacula.config.Configuration;
import org.ilap.javabacula.ui.*;

/**
 *
 * @author ilap
 */
public class Main {
    private static final Logger LOGGER =  Logger.getRootLogger();
  
    public static final String PR_MULTI_INSTANCE = "MULTI_INSTANCE";
    public static final String DEF_LOG_CONF =
        "org/ilap/javabacula/ui/resources/configs/default.lcf";
    
    /**
     * We do a basic Log4j initialization.
     * It must be done before any other static initializers. 
     *
     * If we do not set the commandline to
     * -Dlog4j.configuration=org/ilap/javabacula/ui/resources/configs/full.lcf
     * then the default config resource will be used.
     *
     */
    static {
        
        /**
         * If the config resource is not set in the command line
         * we will use the default logging config resource
         * The default logfile is ${user.dir}/jbSA.log and
         * for the full logging is ${user.dir}/jbSA_debug.log
         */ 
        if (System.getProperty("log4j.configuration") == null) {

            Properties  p  = new Properties();
            InputStream is = null;
            try {
                is =  ClassLoader.getSystemResourceAsStream(DEF_LOG_CONF);

                if (is != null) {
                    p.load(is);
                }
            } catch (IOException io) {
                io.printStackTrace();
                System.exit(-1);
            }

            PropertyConfigurator.configure(p);

            /**
             * Turn off logging if something wrong
             */
            if (is == null) {
                BasicConfigurator.configure();
                Logger.getRootLogger().getLoggerRepository()
            	    .setThreshold(Level.OFF);
            }
        }
        
        /**
         * Load the Configurations from ${user.dir}/jbSA.config file or if it 
         * does not exist, we use default parameters from 
         * org/ilap/javabacula/ui/resources/configs/default.properties resource
         * file. Keep consistent those two files.
         *
         * P.S.: This must be initialize after Logger because Configuration
         * class use it.
         */
         Configuration.load();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Phase: 0 
        // Sets the great L&F from jgoodies
        
        Color c = (Color)UIManager.get("Table.selectionBackground");
        PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
        try {
            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // Change selected color of Table and Tree components, because I hate the
        // dark blue.
        UIManager.put("Table.selectionBackground", c);
        UIManager.put("Tree.selectionBackground", c);
        UIManager.put("Table.selectionForeground", new Color(0, 0, 0));
        UIManager.put("Tree.selectionForeground", new Color(0, 0, 0));

        // Phase: 1
        // Create splash if needed
        LOGGER.info("Starting application...");
       
        /**
         * Initialization processes
         */
        MainWindow mainwindow =  new MainWindow();
        mainwindow.setVisible(true);
                
    }    
}
