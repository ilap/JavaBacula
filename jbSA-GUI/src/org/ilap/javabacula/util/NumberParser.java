/* JavaBacula -- Java frontend of the Bacula® - The Network Backup Solution
 * Copyright (C) 20026-2007 by Pal DOROGI
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Contact: Pal DOROGI
 * mailto:pal.dorogi@gmail.com
 *
 * $Id$
 */


package org.ilap.javabacula.util;

import java.text.NumberFormat;

/**
 *
 * @author ilap
 */
public class NumberParser {
    
    /** No more instances */
    private NumberParser() {
    }
    
    public static Long parseLong(String str) {
        try {
            NumberFormat nf = NumberFormat.getInstance(java.util.Locale.ENGLISH);
            Number num = nf.parse(str);
            return num.longValue();
        } catch (Exception e) {
            return null;
        }
    }    
}
