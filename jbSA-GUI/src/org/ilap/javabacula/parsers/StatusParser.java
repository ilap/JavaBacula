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

package org.ilap.javabacula.parsers;

import java.text.*;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.Locale;


import org.ilap.javabacula.model.*;

/**
 *
 * @author ilap
 */
public class StatusParser {
    CommonDaemon daemon;
    volatile String parseString = null;
    volatile String currentSection = null;

    
  
    /** Creates a new instance of StatusParser 
     * @param daemon type of Daemon
     */
    public StatusParser(CommonDaemon daemon) {
        this.daemon = daemon;        
    }
    
    public StatusParser(CommonDaemon daemon, String parseString) {
        this(daemon);
        this.parseString = parseString;
    }
    
    public void parse(String parseString) {
        if (daemon == null) {
            return;
        }
        this.parseString = parseString;
        parseString();
    }

    public void parse() {
        if ((parseString == null) || parseString.isEmpty()) {
            return;
        } else {
            parse(parseString);
        }
    }
    
    /**
     *  prompt# status storage=L25-stsrv
        Connecting to Storage daemon L25-stsrv at stsrv:19103

        teszt-stsrv Version: 2.0.0 (04 January 2007) i686-pc-linux-gnu redhat (Bordeaux)
        Daemon started 24-Apr-07 09:42, 0 Jobs run since started.
        Heap: bytes=31,049 max_bytes=123,843 bufs=120 max_bufs=131

        Running Jobs:
        No Jobs running.
        ====

        Jobs waiting to reserve a drive:
        ====

        Terminated Jobs:
        JobId  Level    Files      Bytes   Status   Finished        Name 
        ===================================================================
            3  Full          0         0   Cancel   06-Apr-07 15:50 FileSystems
            4  Full          0         0   Cancel   06-Apr-07 15:55 FileSystems
            5  Full          0         0   Cancel   06-Apr-07 16:02 FileSystems
            6  Full          0         0   Cancel   06-Apr-07 16:12 FileSystems
            7  Full          0         0   Cancel   06-Apr-07 16:19 FileSystems
            8  Full     14,840    292.6 M  OK       06-Apr-07 16:28 FileSystems
           10  Full          0         0   Cancel   19-Apr-07 20:20 FileSystems
        ====

        Device status:
        Autochanger "L25" with devices:
        "L25-0" (/opt/bacula/filestorages1/drive0)
        "L25-1" (/opt/bacula/filestorages1/drive1)
        "L25-2" (/opt/bacula/filestorages1/drive2)
        "L25-3" (/opt/bacula/filestorages1/drive3)
        Device "L25-0" (/opt/bacula/filestorages1/drive0) is not open.
        Slot 3 is loaded in drive 0.
        Device "L25-1" (/opt/bacula/filestorages1/drive1) is not open.
        Drive 1 status unknown.
        Device "L25-2" (/opt/bacula/filestorages1/drive2) is not open.
        Drive 2 status unknown.
        Device "L25-3" (/opt/bacula/filestorages1/drive3) is not open.
        Drive 3 status unknown.
        Device "DVD-WRITER" is not open or does not exist.
        ====

        In Use Volume status:
        ====

     */
    private void parseString() {
        if (parseString == null) {
            return;
        }
        
        // parse Header information: name, version, jobs etc.
        String tstr = parseHeader(this.parseString);
        
        if (daemon instanceof DirectorDaemon) {
            tstr = parseScheduledJobs(tstr);
        }
        tstr = parseRunningJobs(tstr);
        if (daemon instanceof StorageDaemon) {
            tstr = parseWaitingToReserve(tstr);
        }
        tstr = parseTerminatedJobs(tstr);
        if (daemon instanceof StorageDaemon) {
            tstr = parseDevicesStatus(tstr);
            tstr = parseInUseVolumeStatus(tstr);
        }            
    }

    private String setNextSection(String string) {
        final String sep = "\n====\n";
        int pos = string.indexOf(sep);
        if (pos < 0) {
            return "";
        }
        currentSection = string.substring(0, pos);
        String result = string.substring(pos+sep.length(), string.length());
        
        return result;
    }
    
    private String parseInUseVolumeStatus(String result) {
        String line; 
        String tstr;
        
        
        if (!result.startsWith("\nIn Use Volume status:\n")) {
            return result;            
        }
        
        result = setNextSection(result);               
        StringTokenizer st = new StringTokenizer(currentSection, "\n");

        while (st.hasMoreTokens()) {            
            line = st.nextToken();
        } // while
                        
        return result;
    }


    /**
Connecting to Storage daemon L25-stsrv at stsrv:19103

stsrv Version: 2.0.0 (04 January 2007) i686-pc-linux-gnu redhat (Bordeaux)
Daemon started 17-May-07 21:08, 0 Jobs run since started.
 Heap: bytes=167,886 max_bytes=196,846 bufs=158 max_bufs=160

Running Jobs:
Writing: Full Backup job FileSystems JobId=38 Volume="FST987"
    pool="Default" device=""L25-0" (/opt/bacula/filestorages1/drive0)"
    Files=3,002 Bytes=9,797,375 Bytes/sec=376,822
    FDReadSeqNo=19,678 in_msg=13143 out_msg=5 fd=5
====

Jobs waiting to reserve a drive:
====

Terminated Jobs:
 JobId  Level    Files      Bytes   Status   Finished        Name 
===================================================================
    28  Full      2,423    6.566 M  OK       16-May-07 17:03 FileSystems
    29  Incr          0         0   OK       16-May-07 17:36 FileSystems
    30  Full      2,423    6.566 M  OK       16-May-07 17:38 FileSystems
    31  Full      2,423    6.566 M  OK       16-May-07 18:01 FileSystems
    32  Full      2,423    6.566 M  OK       16-May-07 18:02 FileSystems
    33                0         0   OK       17-May-07 16:54 RestoreFiles
    34                0         0   OK       17-May-07 17:18 RestoreFiles
    35                0         0   OK       17-May-07 17:34 RestoreFiles
    36                0         0   OK       17-May-07 17:38 RestoreFiles
    37                0         0   OK       17-May-07 17:40 RestoreFiles
====

Device status:
Autochanger "L25" with devices:
   "L25-0" (/opt/bacula/filestorages1/drive0)
   "L25-1" (/opt/bacula/filestorages1/drive1)
   "L25-2" (/opt/bacula/filestorages1/drive2)
   "L25-3" (/opt/bacula/filestorages1/drive3)
Device "L25-0" (/opt/bacula/filestorages1/drive0) is mounted with Volume="FST987" Pool="Default"
    Slot 13 is loaded in drive 0.
    Total Bytes=36,402,378 Blocks=566 Bytes/block=64,315
    Positioned at File=0 Block=36,402,377
Device "L25-1" (/opt/bacula/filestorages1/drive1) is not open.
    Drive 1 status unknown.
Device "L25-2" (/opt/bacula/filestorages1/drive2) is not open.
    Drive 2 status unknown.
Device "L25-3" (/opt/bacula/filestorages1/drive3) is not open.
    Drive 3 status unknown.
Device "DVD-WRITER" is not open or does not exist.
====

In Use Volume status:
FST987 on device "L25-0" (/opt/bacula/filestorages1/drive0)
====

     */
    //Device "L25-0" (/opt/bacula/filestorages1/drive0) is mounted with Volume="FST987" Pool="Default"
    //Device "L25-1" (/opt/bacula/filestorages1/drive1) is not open.
    //Device "DVD-WRITER" is not open or does not exist.

    private String getStatus(String line) {
        String result;
        result = line.replaceFirst("^Device.* is ", "");
        result = result.replaceFirst(".\\$", "");
        result = result.replaceFirst("mounted.*$", "mounted");
        return result;   
    }
    
    private String getVolume(String line) {
        String result;
        if (line.indexOf(" Volume=") < 0) {
                return "";
        }
        result = line.replaceFirst("^.* Volume=\"", "");
        result = result.replaceFirst("\".*", "");
        return result;   
    }

    private String getPool(String line) {
        String result;
        if (line.indexOf(" Pool=") < 0) {
                return "";
        }
        result = line.replaceFirst("^.* Pool=\"", "");
        result = result.replaceFirst("\".*", "");
        return result;   
    }
    
    private boolean isAutoChanger(String line) {
        return line.startsWith("Autochanger \"");
    }

    private boolean isDevice(String line) {
        return line.startsWith("Device \"");
    }

    private String getDevicePath(String line) {
        String result;
        if (line.indexOf("(") < 0) {
                return "";
        }
        result = line.replaceFirst("^.*\\(", "");
        result = result.replaceFirst("\\).*", "");
        return result;   
    }
    
    private String getDeviceName(String line, boolean notJustOnDevices) {
        
        String result = "";

        // Autochanger "L25" with devices:
        // Device "L25-0" (/opt/bacula/filestorages1/drive0) is mounted with Volume="FST987" Pool="Default"
        // Device "L25-1" (/opt/bacula/filestorages1/drive1) is not open.
        if (isAutoChanger(line) || isDevice(line) || notJustOnDevices) {
            result = line.replaceFirst("^[^\"]*\"", "");
            result = result.replaceFirst("\".*", "");
        } 
        
        return result;
        
    }    
    
    private void setDeviceInfos(StorageDevice device, String line) {
        if (!isDevice(line)) {
            return;
        }
        
        device.setDeviceName(getDeviceName(line, false)); 
        device.setDevicePath(getDevicePath(line)); 
        device.setPool(getPool(line)); 
        device.setVolume(getVolume(line)); 
        device.setMessage(getStatus(line));
        
    }
    
    private String parseDevicesStatus(String result) {
        final String AUB = "^Autochanger ";
        final String AUE = " with-devices:\n";
        final String DEV = "^Device ";
        String line; 
        String tstr;

        if (!result.startsWith("\nDevice status:\n")) {
            return result;            
        }

        result = setNextSection(result);               
        StringTokenizer st = new StringTokenizer(currentSection, "\n");

        // To trash "Device Status:"
        line = st.nextToken();
        StorageDaemon sd = (StorageDaemon) daemon;
        
        String storageName = sd.getDeviceName();

        boolean isProcessed = false;                
        boolean isAutoChanger = false;
        boolean doNotReadNextToken = false;
        
        // FIXME: dont have consistency Check for already exist devices.
        while (!isProcessed && st.hasMoreTokens()) {            
            if (doNotReadNextToken) {
                doNotReadNextToken = false;
            } else {
                line = st.nextToken();                
            }
            if (line.isEmpty()) {
                continue;
            } 
            String deviceName = getDeviceName(line, false);
            if (deviceName.equals(storageName)) { // We found it
                if (isAutoChanger(line)) {
                    String tdeviceName;
                    line = st.nextToken();
                    while (!isDevice(line) && !isAutoChanger(line) && st.hasMoreTokens()) {
                        tdeviceName = getDeviceName(line, true);
                        StorageDevice dev = sd.getDeviceByName(tdeviceName);
                        if (dev == null) {
                            dev = new StorageDevice(tdeviceName);
                            sd.addDevice(dev);
                        }
                        dev.setDevicePath(getDevicePath(line)); 
                        dev.setParentJukeBox(sd);
                        dev.setStorageId(sd.getStorageId());
                        line = st.nextToken();
                    }
                    doNotReadNextToken = true;
                    isAutoChanger = true;
                } else if (isDevice(line)) {
                    StorageDevice dev =sd.getDeviceByName(deviceName); 
                    if (dev == null) {
                        dev = new StorageDevice(deviceName);
                        sd.addDevice(dev);
                    }
                    setDeviceInfos(dev, line);
                    dev.setParentJukeBox(null);
                    dev.setStorageId(sd.getStorageId());
                    isProcessed = true;
                }                
                sd.setAutochanger(isAutoChanger);
            } else if (isDevice(line) && sd.isAutoChanger()) {
                deviceName = getDeviceName(line, false);
                StorageDevice dev = sd.getDeviceByName(deviceName); 
                
                if (dev == null) {
                    // BUG: It must be in the device list
                     continue;
                }
                setDeviceInfos(dev, line);
                dev.setParentJukeBox(sd);
                dev.setStorageId(sd.getStorageId());

                // parse device informations
                line = st.nextToken();
                while (!isDevice(line) && st.hasMoreTokens()) {
                    line = st.nextToken();
                    // FIXME: Not Implemented yet
                }            
                doNotReadNextToken = true;
            }                
        } // while
                        
        return result;
    }

    private String parseTerminatedJobs(String result) {
        String line; 
        String tstr;
        
       
        if (!result.startsWith("\nTerminated Jobs:\n")) {
            return result;            
        }
        
        result = setNextSection(result);               
        StringTokenizer st = new StringTokenizer(currentSection, "\n");

        // *** FIXME ****
        // Not Implementet at the moment
        return result;
        /*while (st.hasMoreTokens()) {            
            line = st.nextToken();
        } // while
                        
        return result;*/
    }

    private String parseRunningJobs(String result) {
        String line; 
        String tstr;
                
        if (!result.startsWith("\nRunning Jobs:\n")) {
            return result;            
        }
        
        result = setNextSection(result);               
        StringTokenizer st = new StringTokenizer(currentSection, "\n");

        // *** FIXME ****
        // Not Implementet at the moment
        return result;
        // Storage has "Jobs waiting a reserve drive:\n" section also.
        /*while (st.hasMoreTokens()) {            
            line = st.nextToken();
        } // while
                        
        return result;*/
    }

    private String parseWaitingToReserve(String result) {
        String line; 
        String tstr;
        
        
        if (!result.startsWith("\nJobs waiting to reserve a drive:\n")) {
            return result;            
        }
        
        result = setNextSection(result);               
        StringTokenizer st = new StringTokenizer(currentSection, "\n");

        // *** FIXME ****
        // Not Implementet at the moment
        return result;
        /*while (st.hasMoreTokens()) {            
            line = st.nextToken();
        } // while
                        
        return result;*/    }

    private String parseScheduledJobs(String result) {
        String line; 
        String tstr;
        
        
        if (!result.startsWith("\nScheduled Jobs:\n")) {
            return result;            
        }
        
        result = setNextSection(result);               
        StringTokenizer st = new StringTokenizer(currentSection, "\n");

        // *** FIXME ****
        // Not Implementet at the moment
        return result;
        /*while (st.hasMoreTokens()) {            
            line = st.nextToken();
        } // while
                        
        return result;*/
    }

    
    private String parseHeader(String result) {
        String line; 
        String res;
        String tstr;
        int pos;
        
        if (daemon instanceof DirectorDaemon) {
            pos = result.indexOf("\nScheduled Jobs:\n");            
        } else {
            pos = result.indexOf("\nRunning Jobs:\n");                        
        }
        
        if (pos < 0) { // error on parse header
            return "";            
        }
        tstr = result.substring(0, pos);
        result = result.substring(pos, result.length());
        StringTokenizer st = new StringTokenizer(tstr, "\n");
        
        // *** FIXME *** Set the local to English 
        Locale.setDefault(Locale.ENGLISH);
        
        while (st.hasMoreTokens()) {            
            line = st.nextToken();
            
            String tline = line;
            line = line.replaceAll("^.*Version:", "Version:");
            if (line.startsWith("Version:")) { // 
                // teszt-stsrv Version: 2.0.0 (04 January 2007) i686-pc-linux-gnu redhat (Bordeaux)
                tline = tline.replaceAll("[ ]*Version:.*", "");            
                daemon.setAddress(tline);

                line = line.replaceAll("^Version:[ ]+", "");
                String t = line.replaceAll("[ ].*","");
                line = line.substring(t.length() + 1);
                daemon.setVersion(t);
                
                line = line.replaceFirst("\\(", "");
                t = line.replaceFirst("\\)[ ].*","");                
                line = line.substring(t.length() + 1);
                Date date = null;
                SimpleDateFormat sdf;
                try {
                    sdf = new SimpleDateFormat("dd MMMM yyyy");
                    date = sdf.parse(t); 
                } catch(ParseException pe) {
                        
                }
 
                daemon.setBuildDate(date);

        
                line = line.replaceAll("^[ ]+", "");
                t = line.replaceAll("[ ].*","");
                line = line.substring(t.length() + 1);
                daemon.setHostOS(t);
                
                line = line.replaceAll("^[ ]+", "");
                t = line.replaceAll("[ ].*","");
                line = line.substring(t.length() + 1);
                daemon.setDistName(t);

                line = line.replaceFirst("\\(", "");
                t = line.replaceFirst("\\).*","");                
                daemon.setDistVersion(t);
                                
                //daemon.
            } else if (line.startsWith("Daemon started")) {
            // Daemon started 24-Apr-07 09:42, 0 Jobs run since started.
                line = line.replaceAll("^Daemon started[ ]+", "");
                String t = line.replaceAll(", .*","");
                line = line.substring(t.length() + 1);
             
                Date date = null;
                SimpleDateFormat sdf;
                try {
                    sdf = new SimpleDateFormat("dd-MMM-yy HH:mm");
                    date = sdf.parse(t); 
                } catch(ParseException pe) {
                        
                }
 
                daemon.setStarted(date);
                
                line = line.replaceFirst(" ","");                
                t = line.replaceAll("[ ].*","");                
                line = line.substring(t.length() + 1); 
                
                daemon.setJobsSince(new Long(t));
            } else if (line.startsWith(" Heap")) {
                //  Heap: bytes=31,049 max_bytes=123,843 bufs=120 max_bufs=131
                // *** FIXME *** Not implemented at the moment
                break;
            }
        } // while

        return result;
    }
}
