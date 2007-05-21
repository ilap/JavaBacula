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

import java.awt.Component;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 * Provides a menu for custom commands. Initially invisible, it extends JMenu
 * to become visible if menu items are added.
 *
 * @author Jeffrey M. Thompson
 * @version v0.03
 *
 * @since v0.03
 */
public class CustomMenu extends JMenu
{
    /**
     * Construct this object.
     *
     * @since v0.03
     */
    public CustomMenu(  )
    {
        super(  );
        init(  );
    }

    /**
     * Construct this object with the given parameter.
     *
     * @since v0.03
     */
    public CustomMenu( Action action )
    {
        super( action );
        init(  );
    }

    /**
     * Construct this object with the given parameter.
     *
     * @since v0.03
     */
    public CustomMenu( String text )
    {
        super( text );
        init(  );
    }

    /**
     * Construct this object with the given parameters.
     *
     * @since v0.03
     */
    public CustomMenu( String text, boolean b )
    {
        super( text, b );
        init(  );
    }

    /**
     * �reates a new menu item attached to the specified Action object and
     * appends it to the end of this menu.
     *
     * @since v0.03
     */
    public JMenuItem add( Action action )
    {
        JMenuItem answer = super.add( action );
        checkVisibility(  );

        return answer;
    }

    /**
     * Appends a component to the end of this menu.
     *
     * @since v0.03
     */
    public Component add( Component component )
    {
        Component answer = super.add( component );
        checkVisibility(  );

        return answer;
    }

    /**
     * Adds the specified component to this container at the given position.
     *
     * @since v0.03
     */
    public Component add( Component component, int index )
    {
        Component answer = super.add( component, index );
        checkVisibility(  );

        return answer;
    }

    /**
     * Appends a menu item to the end of this menu.
     *
     * @since v0.03
     */
    public JMenuItem add( JMenuItem menuItem )
    {
        JMenuItem answer = super.add( menuItem );
        checkVisibility(  );

        return answer;
    }

    /**
     * Creates a new menu item with the specified text and appends  it to the
     * end of this menu.
     *
     * @since v0.03
     */
    public JMenuItem add( String text )
    {
        JMenuItem answer = super.add( text );
        checkVisibility(  );

        return answer;
    }

    /**
     * Check the visibility of this menu.
     *
     * @since v0.03
     */
    protected void checkVisibility(  )
    {
        setVisible( getMenuComponentCount(  ) > 0 );
    }

    /**
     * Initialize this object.
     *
     * @since v0.03
     */
    protected void init(  )
    {
        setVisible( false );
    }
}
